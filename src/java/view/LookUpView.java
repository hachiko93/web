/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author student1
 */
public class LookUpView {

    public static String getView(String name) {
        String view = name;
        if (name.equalsIgnoreCase("chat")) {
            view = "/chat.jsp";
        }
        if (name.equalsIgnoreCase("login")) {
            view = "/login.jsp";
        }
        return view;
    }
}
