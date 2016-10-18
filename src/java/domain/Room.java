/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.websocket.CloseReason;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Hachiko
 */
@Entity
@Table(name = "room")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "Room.findByRoomID", query = "SELECT r FROM Room r WHERE r.roomID = :roomID"),
    @NamedQuery(name = "Room.findByName", query = "SELECT r FROM Room r WHERE r.name = :name")})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roomID")
    private Integer roomID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @JoinTable(name = "user_room", joinColumns = {
        @JoinColumn(name = "roomID", referencedColumnName = "roomID")}, inverseJoinColumns = {
        @JoinColumn(name = "email", referencedColumnName = "email")})
    @ManyToMany
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "in1")
    private List<Message> messageList;

    public Room() {
    }

    public Room(Integer roomID) {
        this.roomID = roomID;
    }

    public Room(Integer roomID, String name) {
        this.roomID = roomID;
        this.name = name;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<Message> getMessageList() {
        if (messageList == null) {
            messageList = new ArrayList<>();
        }
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public synchronized void sendMessage(Message message, User sender) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = null;
        try {
            jsonMessage = mapper.writeValueAsString(message);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        for (User user : userList) {
            if (user.getSession().isOpen() && !user.equals(sender)) {
                user.getSession().getAsyncRemote().sendText(jsonMessage);
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomID != null ? roomID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomID == null && other.roomID != null) || (this.roomID != null && !this.roomID.equals(other.roomID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        Room room = this;
        try {
            return mapper.writeValueAsString(room);
        } catch (IOException ex) {
            ex.printStackTrace();
            return "nece";
        }
    }

    public void addUserToRoom(User user) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
    }
}
