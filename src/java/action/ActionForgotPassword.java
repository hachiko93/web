/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author student1
 */
public class ActionForgotPassword implements Action {

    @Override
    public String processRequest(HttpServletRequest request) {
        String page = null;
        
        String email = request.getParameter("emailF");
        String nickname = request.getParameter("nicknameF");
//        EntityManagerFactory emf
//                = Persistence.createEntityManagerFactory("WebPoslovniPartnerPU");
//        EntityManager em = emf.createEntityManager();
        //List<Mesto> listaMesta = em.createQuery("SELECT m FROM Mesto m ORDER BY m.naziv").getResultList();
//        em.close();
//        emf.close();
        //request.setAttribute("kolekcijaMesta", listaMesta);
        System.out.println("Ovde");
        request.setAttribute("success", "Reset link was sent to Your email address");
        page = "login";
        return page;
    }

}
