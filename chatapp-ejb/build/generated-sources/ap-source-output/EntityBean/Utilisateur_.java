package EntityBean;

import EntityBean.Conversation;
import EntityBean.Message;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-12-29T18:24:57")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ { 

    public static volatile SingularAttribute<Utilisateur, String> password;
    public static volatile SingularAttribute<Utilisateur, String> img;
    public static volatile CollectionAttribute<Utilisateur, Conversation> conversationCollection1;
    public static volatile CollectionAttribute<Utilisateur, Message> messageCollection;
    public static volatile CollectionAttribute<Utilisateur, Conversation> conversationCollection;
    public static volatile SingularAttribute<Utilisateur, String> fullname;
    public static volatile SingularAttribute<Utilisateur, Integer> userid;
    public static volatile SingularAttribute<Utilisateur, String> email;

}