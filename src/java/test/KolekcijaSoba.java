/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import domain.Message;
import domain.Room;
import domain.TextMessage;
import domain.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hachiko
 */
public class KolekcijaSoba {
    private static KolekcijaSoba instance;
    private List<Room> rooms;

    private KolekcijaSoba() {
        rooms = new ArrayList<>();
        
        Room r1 = new Room(1, "Soba1");

        Message m = new Message();
        m.setReceived(new Date());
        m.setContent(new TextMessage("Lorem ipsum dolorem sunt"));
        User u1 = new User("admin@admin.com","admin", "admin");
        User u2 = new User("ana_93_bg@hotmail.com", "ana", "ana");
        m.setFrom1(u1);
        List<Message> lm = new ArrayList<>();
        lm.add(m);
        r1.setMessageList(lm);
        r1.getUserList().add(u1);
        r1.getUserList().add(u2);
        rooms.add(r1);
        
        Room r2 = new Room(2, "Soba2");

        Message m1 = new Message();
        m1.setReceived(new Date());
        m1.setContent(new TextMessage("Lorem ipsum dolorem sunt proba proba"));
        m1.setFrom1(u2);
        List<Message> lm1 = new ArrayList<>();
        lm1.add(m1);
        r2.getUserList().add(u1);
        r2.getUserList().add(u2);
        r2.getUserList().add(new User("proba@proba.com", "proba", "proba"));
        r2.setMessageList(lm1);
        rooms.add(r2);
    }
    
    public static KolekcijaSoba getInstance(){
        if(instance == null) instance = new KolekcijaSoba();
        return instance;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public Room getRoom (int id){
        for (Room room : rooms) {
            if(room.getRoomID() == id) return room;
        }
        return null;
    }

    public void refreshUsersInRooms(User user) {
        for (Room room : rooms) {
            for (User u : room.getUserList()) {
                if(user.equals(u)) u.setSession(user.getSession());
            }
        }
    }
    
}
