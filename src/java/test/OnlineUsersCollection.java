/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import domain.Room;
import domain.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author Hachiko
 */
public class OnlineUsersCollection {

    private static OnlineUsersCollection instance;
    private List<User> onlineUsers;

    private OnlineUsersCollection() {
        onlineUsers = new ArrayList<>();
    }

    public static OnlineUsersCollection getInstance() {
        if (instance == null) {
            instance = new OnlineUsersCollection();
        }
        return instance;
    }

    public List<User> getOnlineUsers() {
        return onlineUsers;
    }

    public void add(User user) {
        onlineUsers.add(user);
    }

    public void remove(Session session) {
        Iterator<User> i = onlineUsers.iterator();
        while (i.hasNext()) {
            User user = i.next();
            if (user.getSession().equals(session)) {
                i.remove();
            }
        }
    }
    
    public User getUser(String email){
        for (User user : onlineUsers) {
            if(user.getEmail().equals(email)) return user;
        }
        return null;
    }
    
}
