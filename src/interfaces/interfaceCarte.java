/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.collections.ObservableList;
import entities.Carte_fidelite;
import entities.Utilisateur;

/**
 *
 * @author aziz
 */
public interface interfaceCarte <T> {
     public void ajouter(T c);
     public void modifier(T c);
     public void Supprimer(int id);
     public ObservableList<String> GetAllIdUser();
     public ObservableList<Carte_fidelite> afficher();
     public boolean CheckCarteById(int id) ;
    
}
