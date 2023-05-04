/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import EntityBean.Conversation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rached
 */
@Local
public interface ConversationFacadeLocal {

    void create(Conversation conversation);

    void edit(Conversation conversation);

    void remove(Conversation conversation);

    Conversation find(Object id);

    List<Conversation> findAll();

    List<Conversation> findRange(int[] range);

    int count();
    
    String getConversation(int idconv);
    
    int getIdConversation(int user1, int user2);
    
}
