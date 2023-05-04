/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import EntityBean.Utilisateur;
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
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {

    @PersistenceContext(unitName = "chatapp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }
    @Override
    public String verifUser(String name, String pass) {
        em = getEntityManager();
        Query req = em.createQuery("SELECT u FROM Utilisateur u WHERE u.fullname = :x and u.password = :y");
        req.setParameter("x", name);
        req.setParameter("y", pass);

        if (req.getResultList().size() > 0) {
            List<Utilisateur> list = req.getResultList();

            for (Utilisateur object : list) {
                System.out.println("<h1>" + object.getUserid() + "</h1>");
                System.out.println("<h1>" + object.getEmail() + "</h1>");
                JsonObjectBuilder obj = Json.createObjectBuilder().add("status", true).add("userId", object.getUserid());
                JsonObject jsonObject = obj.build();

                String jsonString;
                try ( Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonObject);
                    jsonString = writer.toString();

                    System.out.print(jsonString);
                    return jsonString;

                } catch (IOException ex) {
                    Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return "";
    }

    @Override
    public String getFriends(int id) {
        em = getEntityManager();

        //SELECT distinct(req.userid),req.fullname,req.img,req.conversationid FROM (SELECT * FROM Utilisateur u ,Conversation v WHERE (v.user1.userid = :x or v.user2.userid = :x ) and (v.user1.userid = u.userid or v.user2.userid = u.userid) )  req,utilisateur u WHERE req.userid<>:x
        Query req = em.createQuery("SELECT u,v.conversationid FROM Utilisateur u ,Conversation v WHERE (v.user1.userid = :x or v.user2.userid = :x ) and (v.user1.userid = u.userid or v.user2.userid = u.userid) ");
        req.setParameter("x", id);

        String finalJSON = "[";

        List<Object[]> reportDetails = req.getResultList();

        for (Object[] reportDetail : reportDetails) {
            int conv = 0;
            if (reportDetail[1] != null) {
               conv = (int) reportDetail[1];
            }
            
            Utilisateur user = (Utilisateur) reportDetail[0];
            System.out.print(user.getFullname());
            System.out.print(conv);
            String img = "";
            if (user.getImg() != null) {
                img = user.getImg();
            }
             
            JsonObjectBuilder obj = Json.createObjectBuilder().add("IdUser", user.getUserid()).add("userName", user.getFullname()).add("imgUser", img).add("room", conv);
            JsonObject jsonObject = obj.build();

            String jsonString;

            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
                finalJSON += jsonString + ",";
            } catch (IOException ex) {
                Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

            // do something with entities
        }


        return (finalJSON.substring(0, finalJSON.length()-1)+"]" );
    }
    
    @Override
    public String getCurrentUser(int id) {
        em = getEntityManager();

        Query req = em.createQuery("SELECT u FROM Utilisateur u WHERE u.userid = :x ");
        req.setParameter("x", id);

        String finalJSON = "[";

        if (req.getResultList().size() > 0) {
            List<Utilisateur> list = req.getResultList();

            for (Utilisateur object : list) {

                String img = "";
                if (object.getImg() != null) {
                    img = object.getImg();
                }

                JsonObjectBuilder obj = Json.createObjectBuilder().add("IdUser", object.getUserid()).add("userName", object.getFullname()).add("imgUser", img);
                JsonObject jsonObject = obj.build();

                String jsonString;

                try ( Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonObject);
                    jsonString = writer.toString();
                    finalJSON += jsonString + ",";
                } catch (IOException ex) {
                    Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
                }

                // do something with entities
            }
        }

        return (finalJSON.substring(0, finalJSON.length() - 1) + "]");
    }
    
 @Override
    public String recherche(String text) {
        em = getEntityManager();
        Query req = em.createQuery("SELECT u FROM Utilisateur u WHERE u.fullname LIKE :x ");
        req.setParameter("x", "%" + text + "%");
        
        String finalJSON = "[";
        

        List<Utilisateur> searchResults = req.getResultList();
        
        for (Utilisateur user : searchResults) {
            System.out.print(user.getFullname());
            String img = "";
            if (user.getImg() != null) {
                img = user.getImg();
            }
            JsonObjectBuilder obj = Json.createObjectBuilder().add("IdUser", user.getUserid()).add("userName", user.getFullname()).add("imgUser", img);
            JsonObject jsonObject = obj.build();

            String jsonString;

            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
                finalJSON += jsonString + ",";
            } catch (IOException ex) {
                Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

            // do something with entities
        }
        
    return (finalJSON.substring(0, finalJSON.length()-1)+"]" );
    }
    
}
