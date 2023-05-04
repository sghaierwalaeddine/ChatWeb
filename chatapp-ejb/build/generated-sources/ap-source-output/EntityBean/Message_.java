package EntityBean;

import EntityBean.Conversation;
import EntityBean.Utilisateur;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-12-29T18:24:57")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Conversation> convid;
    public static volatile SingularAttribute<Message, Utilisateur> sender;
    public static volatile SingularAttribute<Message, Integer> messageid;
    public static volatile SingularAttribute<Message, String> text;
    public static volatile SingularAttribute<Message, Date> sentat;

}