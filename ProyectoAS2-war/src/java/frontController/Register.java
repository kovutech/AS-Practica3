/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.object.User;
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
public class Register extends FrontCommand {

    @Override
    public void process() {
        try {
            addUser();
            forward("/register.jsp");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addUser() {
        if (request.getParameter("register") != null) {
            HttpSession session = request.getSession(true);
            UserBean userList = (UserBean) session.getAttribute("userList");
            userList.addUser(request.getParameter("user"), new User(request.getParameter("user"), request.getParameter("pass"), request.getParameter("email")));
            session.setAttribute("userList", userList);
            session.removeAttribute("user");
            try {
                forward("/index.jsp");
            } catch (ServletException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
