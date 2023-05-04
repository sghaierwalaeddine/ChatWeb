/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import EntityBean.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rached
 */
@Local
public interface UtilisateurFacadeLocal {

    void create(Utilisateur utilisateur);

    void edit(Utilisateur utilisateur);

    void remove(Utilisateur utilisateur);

    Utilisateur find(Object id);

    List<Utilisateur> findAll();

    List<Utilisateur> findRange(int[] range);

    int count();
    
    String verifUser(String name, String pass);
    
    String getFriends(int id);
    
    String getCurrentUser(int id);
    
    String recherche(String text);
    
}
