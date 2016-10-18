/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import test.OnlineUsersCollection;

/**
 *
 * @author student1
 */
public class ActionLogout implements Action{

    @Override
    public String processRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        
        return "login";
    }
    
}
