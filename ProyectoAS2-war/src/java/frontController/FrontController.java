package frontController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.as.practica2.singleton.StadisticsBean;
import com.as.practica2.singleton.LogBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
@WebServlet(urlPatterns = {"/FrontController"})
public class FrontController extends HttpServlet {

    @EJB
    LogBean log;

    @EJB
    StadisticsBean estadisticas;

    private FrontCommand getCommand(HttpServletRequest req) {
        try {
            FrontCommand f = (FrontCommand) getCommandClass(req).newInstance();
            return f;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Class getCommandClass(HttpServletRequest req) {
        Class result;
        final String command = "frontController." + (String) req.getParameter("command");
        try {
            result = Class.forName(command);
        } catch (ClassNotFoundException e) {
            result = UnknownCommand.class;
        }
        return result;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.addFuntion("processRequest");

        estadisticas.addComponent("processRequest");

        HttpSession session = request.getSession(true);
        if (session.getAttribute("user") == null) {
            String userName = request.getParameter("user");
            session.setAttribute("user", userName);
        }

        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
