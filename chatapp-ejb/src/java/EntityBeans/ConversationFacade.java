/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import EntityBean.Conversation;
import EntityBean.Message;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Rached
 */
@Stateless
public class ConversationFacade extends AbstractFacade<Conversation> implements ConversationFacadeLocal {

    @PersistenceContext(unitName = "chatapp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConversationFacade() {
        super(Conversation.class);
    }
    @Override
    public String getConversation(int idconv) {
        em = getEntityManager();

        Query req = em.createQuery("select m from Message m where m.convid.conversationid = :x order by m.sentat");
        req.setParameter("x", idconv);
        String finalJSON = "[";
        //listes des messages (finalJSON fih status w list hethi)
        //List<String> messageJSON = new ArrayList<String>();
        //Json array builder
        //JsonArray array = (JsonArray) Json.createArrayBuilder();

        if (req.getResultList().size() > 0) {
            List<Message> tousLesMessages = req.getResultList();

            for (Message object : tousLesMessages) {
                JsonObjectBuilder msg = Json.createObjectBuilder().add("sender", object.getSender().getUserid()).add("text", object.getText()).add("sentat", object.getSentat().toString());

                JsonObject jsonMsgObject = msg.build();

                String jsonString;

                try ( Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonMsgObject);
                    jsonString = writer.toString();
                    finalJSON += jsonString + ",";
                } catch (IOException ex) {
                    Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
                //array.add(jsonMsgObject);
                /*
                String jsonMsgtoString;
                try ( Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonMsgObject);
                    jsonMsgtoString = writer.toString();
                    messageJSON.add(jsonMsgtoString);
                    messageJSON.add(",");
                } catch (IOException ex) {

                }*/
            }
            //tawa JSON fih status w list des messages
            //JsonObjectBuilder obj;
            //obj = Json.createObjectBuilder().add("status", "true").add("message", array);
            //JsonObject jsonObject = obj.build();
        }
        return (finalJSON.substring(0, finalJSON.length() - 1) + "]");
    }

    @Override
    public int getIdConversation(int user1, int user2) {
        em = getEntityManager();

        Query req = em.createQuery("select c from Conversation c where ( c.user1.userid = :x and c.user2.userid = :y ) or ( c.user1.userid = :y and c.user2.userid = :x ) ");
        req.setParameter("x", user1);
        req.setParameter("y", user2);

        if (req.getResultList().size() > 0) {
            List<Conversation> tousLesConv = req.getResultList();

            for (Conversation object : tousLesConv) {

                return object.getConversationid();
            }
            //tawa JSON fih status w list des messages
            //JsonObjectBuilder obj;
            //obj = Json.createObjectBuilder().add("status", "true").add("message", array);
            //JsonObject jsonObject = obj.build();
        }
        return 0;
    }
    
}
