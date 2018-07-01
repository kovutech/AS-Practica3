/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.sbEntity.UserFacade;
import com.as.practica2.entity.User;
import com.as.practica2.singleton.LogBean;
import com.as.practica2.singleton.StadisticsBean;
import com.as.practica2.stateful.ClientBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class Register extends FrontCommand {

    private LogBean log;
    private StadisticsBean stadistics;

    @Override
    public void process() {
        try {
            if (addUser()) {
                forward("/index.jsp");
            } else {
                forward("/register.jsp");
            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addUser() {
        try {
            HttpSession session = request.getSession(true);
            UserFacade userFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/UserFacade");
            if (request.getParameter("register") != null) {
                List<User> users = userFacade.findAll();
                userFacade.addTrace("ninguno", "findAll");
                for (User user : users) {
                    if (user.getName().equals(request.getParameter("user"))) {
                        return false;
                    }
                }
                userFacade.create(new User(null, request.getParameter("user"), request.getParameter("pass"), request.getParameter("email")));
                userFacade.addTrace("ninguno", "create");
                session.removeAttribute("user");
                return true;
            }
        } catch (NamingException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
