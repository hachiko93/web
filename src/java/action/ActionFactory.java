/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

/**
 *
 * @author student1
 */
public class ActionFactory {

    public static Action createAction(String path) {
        Action action = null;
        if (path.equalsIgnoreCase("/login")) {
            action = new ActionLogin();
        }
        if (path.equalsIgnoreCase("/logout")) {
            action = new ActionLogout();
        }
        if(path.equalsIgnoreCase("/register")){
            action = new ActionRegister();
        }
        if(path.equalsIgnoreCase("/forgot_password")){
            action = new ActionForgotPassword();
        }
        return action;
    }
}
