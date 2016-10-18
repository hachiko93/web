/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import domain.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import domain.Message;
import domain.Room;

/**
 *
 * @author Hachiko
 */
public class DatabaseBroker {

    private EntityManagerFactory emf;
    private static DatabaseBroker instance;

    private DatabaseBroker() {
        emf = Persistence.createEntityManagerFactory("ChatApplicationPU");
    }

    public static DatabaseBroker getInstance() {
        if (instance == null) {
            instance = new DatabaseBroker();
        }
        return instance;
    }

    public User findUser(String email) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, email);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public Room findRoom(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Room room = em.find(Room.class, id);
        em.getTransaction().commit();
        em.close();
        return room;
    }

    public void addUserToRoom(User user, Room room) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.merge(room);
        em.getTransaction().commit();
        em.close();
    }

    public void addNewRoom(Room room) throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Room db_room = em.find(Room.class, room);
        if (db_room == null) {
            em.persist(room);
            em.getTransaction().commit();
            em.close();
        } else {
            em.getTransaction().rollback();
            em.close();
            throw new Exception("Room already exists!");
        }

    }

    public List<Message> getMessagesForRoom(Room room) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Message> messages = em.createQuery("SELECT m FROM Message m WHERE m.in =:roomID").setParameter("roomID", room.getRoomID()).getResultList();
        em.getTransaction().commit();
        em.close();
        return messages;
    }

    public void addMessageToRoom(Message chatMessage) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(chatMessage);
        em.getTransaction().commit();
        em.close();

    }

    public void deleteUserFromRoom(User user, Room room) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        //em.createQuery("DELETE FROM userinroom WHERE User=user AND Room=room");
        em.getTransaction().commit();
        em.close();
    }

    public void addNewUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }
    
}
