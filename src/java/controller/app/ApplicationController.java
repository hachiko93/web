/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.app;

import action.Action;
import action.ActionFactory;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author student1
 */
public class ApplicationController {

    public String processRequest(String path, HttpServletRequest request) {
        Action action = ActionFactory.createAction(path);
        return action.processRequest(request);

    }

}
