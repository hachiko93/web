/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import domain.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import test.KolekcijaSoba;
import test.OnlineUsersCollection;

/**
 *
 * @author student1
 */
public class ActionLogin implements Action {

    @Override
    public String processRequest(HttpServletRequest request) {
        String page = null;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
//        EntityManagerFactory emf
//                = Persistence.createEntityManagerFactory("webPU");
//        EntityManager em = emf.createEntityManager();
//        User user = em.find(User.class, email);
//        HttpSession session = request.getSession(true);
//        if (user == null) {
//            page = "login";
//            session.setAttribute("error", "Error while logging in");
//        } else {
//            page = "chat";
//            session.setAttribute("user", user);
//            session.setAttribute("error", null);
//        }
//        em.close();
//        emf.close();
        
        if ((("ana_93_bg@hotmail.com").equals(email) && ("ana").equals(password)) || (("admin@admin.com").equals(email) && ("admin").equals(password))) {
            page = "chat";
        } else {
            page = "login";
        }
        request.setAttribute("error", "Error while logging in");

        request.setAttribute("roomList", KolekcijaSoba.getInstance().getRooms());

        User u = new User(email);
        u.setPassword(password);
        u.setRoomList(KolekcijaSoba.getInstance().getRooms());
        request.setAttribute("user", u);
        
        OnlineUsersCollection.getInstance().add(u);
        
        return page;
    }

}
