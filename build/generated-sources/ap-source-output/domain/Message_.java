package domain;

import domain.Room;
import domain.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-21T21:09:39")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, User> from1;
    public static volatile SingularAttribute<Message, Integer> messageID;
    public static volatile SingularAttribute<Message, Date> received;
    public static volatile SingularAttribute<Message, String> message;
    public static volatile SingularAttribute<Message, Room> in1;

}