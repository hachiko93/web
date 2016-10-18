/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Hachiko
 */
public class ActionRegister implements Action{

    @Override
    public String processRequest(HttpServletRequest request) {
        String strana = null;
        String email = request.getParameter("emailR");
        String password = request.getParameter("passwordR");
        String nickname = request.getParameter("nickname");
        
        if(email.equals("admin@admin.com") || email.equals("ana_93_bg@hotmail.com"))
        request.setAttribute("success", "Successfully registrated");
        else request.setAttribute("error", "Error while registrating");
        strana = "login";
        return strana;
    }
    
}
