/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.sbEntity.ClientFacade;
import com.as.practica2.sbEntity.UserFacade;
import com.as.practica2.entity.Client;
import com.as.practica2.entity.User;
import java.io.IOException;
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
public class Main extends FrontCommand {

    public void addClient() {
        if (request.getParameter("addClient") != null) {
            try {
                HttpSession session = request.getSession(true);
                User user = (User) session.getAttribute("user");
                ClientFacade clientFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientFacade");
               // clientFacade.create(new Client(null, user.getIdUser().intValue(), request.getParameter("identification"), request.getParameter("name"), request.getParameter("surName"), request.getParameter("telephone")));
            } catch (NamingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
//                userClientFacade.create(new Client(null,,request.getParameter("name"),request.getParameter("surName"),request.getParameter("telephone")));

//userClientFacade.create(new Client(null,Integer.parseInt(request.getParameter("identification")),request.getParameter("name"),request.getParameter("surName"),request.getParameter("telephone")));
        }
    }

    public void deleteClient() {
        /*if (request.getParameter("deleteClient") != null) {
            try {
                ClientFacade clientFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientFacade");
                Client client = clientFacade.findByIdentification(Integer.parseInt(request.getParameter("deleteClient")));
                if (client != null) {
                    clientFacade.remove(client);
                }
            } catch (NamingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }

    public boolean checkUser() {
        if (request.getParameter("login") != null) {
            try {
                HttpSession session = request.getSession(true);
                UserFacade userFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/UserFacade");
                User user = userFacade.findByName(request.getParameter("user"));
                if (user != null) {
                    String pass = user.getPass();
                    if (!pass.equals(request.getParameter("pass"))) {
                        session.removeAttribute("user");
                        return false;
                    }
                } else {
                    session.removeAttribute("user");
                    return false;
                }
                session.setAttribute("user", user);
                //session.setAttribute("userId", user.getIdUser());
            } catch (NamingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
