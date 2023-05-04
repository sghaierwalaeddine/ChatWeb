package EntityBean;

import EntityBean.Message;
import EntityBean.Utilisateur;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-12-29T18:24:57")
@StaticMetamodel(Conversation.class)
public class Conversation_ { 

    public static volatile SingularAttribute<Conversation, Utilisateur> user1;
    public static volatile SingularAttribute<Conversation, Utilisateur> user2;
    public static volatile SingularAttribute<Conversation, Integer> conversationid;
    public static volatile CollectionAttribute<Conversation, Message> messageCollection;

}