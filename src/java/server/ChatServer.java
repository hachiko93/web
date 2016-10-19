/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domain.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import domain.Message;
import domain.MessageType;
import domain.Picture;
import org.codehaus.jackson.map.ObjectMapper;
import domain.Room;
import domain.TextMessage;
import java.util.ArrayList;
import java.util.Date;
import test.KolekcijaSoba;
import test.OnlineUsersCollection;

/**
 *
 * @author Hachiko
 */
@ServerEndpoint("/chat")
public class ChatServer {

    private Logger log = Logger.getLogger(ChatServer.class.getSimpleName());
    private User user;
    private Room room;

    @OnMessage
    public void onMessage(String messageJson, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message chatMessage = null;
            try {
                chatMessage = mapper.readValue(messageJson, Message.class);
            } catch (IOException e) {
                e.printStackTrace();
                String message = "Badly formatted message";
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, message));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    log.severe(ex.getMessage());
                }
            };

            user = OnlineUsersCollection.getInstance().getUser(chatMessage.getFrom1().getEmail());
            if (chatMessage.getIn1().getRoomID() != null) {
                room = KolekcijaSoba.getInstance().getRoom(chatMessage.getIn1().getRoomID());
            }

            if (chatMessage.getType() == MessageType.LOGIN) {
                user.setSession(session);
                KolekcijaSoba.getInstance().refreshUsersInRooms(user);
            }
            if (chatMessage.getType() == MessageType.ADD_USER_TO_ROOM) {
                //sesija ako je online
                room.addUserToRoom(user);
                user.getSession().getAsyncRemote().sendText(mapper.writeValueAsString(chatMessage));
                chatMessage.setContent(new TextMessage(user.getNickname() + " was added to the room"));
                room.sendMessage(chatMessage, user);
            }
            if (chatMessage.getType() == MessageType.CREATE_ROOM) {
                //ime sobe je user1-user2 u slucaju privatnog chat-a
                room.setMessageList(new ArrayList<>());
                //baza
                //room = uzme iz baze zbog id-a
                chatMessage.setReceived(new Date());
                //user.setSession(session);
                room.sendMessage(chatMessage, null);
//                if (chatMessage.getIn1().getName().contains("@")) {
//                    User user = new User(chatMessage.getIn1().getName());
//                    chatMessage.getIn1().setName(chatMessage.getFrom1().getEmail());
//                    OnlineUsersCollection.getInstance().addRoomOnlineUser(user, chatMessage.getIn1());
//                }
            }
            if (chatMessage.getType() == MessageType.DELETE_ROOM) {
                Message m = new Message();
                m.setContent(new TextMessage(chatMessage.getFrom1() + "has removed himself from the room"));
                room.sendMessage(m, user);
                //DatabaseBroker.getInstance().deleteUserFromRoom(user, room);
            }
            if (chatMessage.getType() == MessageType.SEND_MESSAGE) {
                chatMessage.setReceived(new Date());
                room.sendMessage(chatMessage, user);
                //DatabaseBroker.getInstance().addMessageToRoom(chatMessage);
            }
            if (chatMessage.getType() == MessageType.SEND_PICTURE) {
                chatMessage.setReceived(new Date());
                room.sendMessage(chatMessage, user);
            }
            if (chatMessage.getType() == MessageType.SEND_FILE) {
                chatMessage.setReceived(new Date());
                room.sendMessage(chatMessage, user);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " has opened a connection");

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
        OnlineUsersCollection.getInstance().remove(session);
    }

}
