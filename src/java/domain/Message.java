/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 *
 * @author Hachiko
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMessageID", query = "SELECT m FROM Message m WHERE m.messageID = :messageID"),
    @NamedQuery(name = "Message.findByMessage", query = "SELECT m FROM Message m WHERE m.message = :message"),
    @NamedQuery(name = "Message.findByReceived", query = "SELECT m FROM Message m WHERE m.received = :received")})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "messageID")
    private Integer messageID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "message")
    private Content content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "received")
    @Temporal(TemporalType.TIMESTAMP)
    private Date received;
    @JoinColumn(name = "from", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private User from1;
    @JoinColumn(name = "in", referencedColumnName = "roomID")
    @ManyToOne(optional = false)
    private Room in1;
    @Transient
    private MessageType type;

    public Message() {
    }

    public Message(Integer messageID) {
        this.messageID = messageID;
    }

    public Message(Integer messageID, Content message, Date received) {
        this.messageID = messageID;
        this.content = message;
        this.received = received;
    }

    public Integer getMessageID() {
        return messageID;
    }

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public User getFrom1() {
        return from1;
    }

    public void setFrom1(User from1) {
        this.from1 = from1;
    }

    public Room getIn1() {
        return in1;
    }

    public void setIn1(Room in1) {
        this.in1 = in1;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    
    public String getFormatedDate(){
        return new SimpleDateFormat("dd.MM.yyyy hh:mm").format(received);
    }
    
    public String getFormatedTime(){
        return new SimpleDateFormat("hh:mm").format(received);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageID != null ? messageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageID == null && other.messageID != null) || (this.messageID != null && !this.messageID.equals(other.messageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Message[ messageID=" + messageID + " ]";
    }
    
}
