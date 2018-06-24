/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.stateful.ClientBean;
import com.as.practica2.object.Client;
import com.as.practica2.stateful.UserBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class Main extends FrontCommand {

    public void addClient() {
        if (request.getParameter("addClient") != null) {
            HttpSession session = request.getSession(true);
            ClientBean clientList = (ClientBean) session.getAttribute("clientList");
            clientList.addClient(new Client(request.getParameter("dni"), request.getParameter("nombre"), request.getParameter("apellido"), request.getParameter("telefono")), (String) session.getAttribute("user"));
            session.setAttribute("clientList", clientList);
        }
    }

    public void deleteClient() {

        if (request.getParameter("deleteClient") != null) {
            HttpSession session = request.getSession(true);
            ClientBean clientList = (ClientBean) session.getAttribute("clientList");
            clientList.deleteClient(request.getParameter("deleteClient"), (String) session.getAttribute("user"));
            session.setAttribute("clientList", clientList);
        }
    }

    public boolean checkUser() {
        if (request.getParameter("login") != null) {
            HttpSession session = request.getSession(true);
            UserBean userList = (UserBean) session.getAttribute("userList");
            if (!userList.checkUserLogin(request.getParameter("user"), request.getParameter("pass"))) {
                session.removeAttribute("user");
                return false;
            }
        }
        return true;
    }

    @Override
    public void process() {
        try {
            addClient();
            deleteClient();
            if (checkUser()) {
                forward("/main.jsp");
            } else {
                forward("/index.jsp");
            }

        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
