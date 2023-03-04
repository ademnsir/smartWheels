/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD
package services;
=======
package projet_transport.services;
>>>>>>> master

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.print.DocFlavor;
<<<<<<< HEAD
import Controller.Gestion_UtilisateurController;
import interfaces.interfaceCarte;
import entities.Carte_fidelite;
import entities.Utilisateur;
import utils.MyConnection;
=======
import projet_transport.controler.Gestion_UtilisateurController;
import projet_transport.interfaces.interfaceCarte;
import projet_transport.model.Carte_fidelite;
import projet_transport.model.Utilisateur;
import projet_transport.utils.MyConnexion;
>>>>>>> master

/**
 *
 * @author aziz
 */
public class Carte_fideliteS implements interfaceCarte<Carte_fidelite>{
     public void ajouter(Carte_fidelite c)
    {
        try {      
        String sql="insert into carte_fidelite (id,points) values (?,?)";
<<<<<<< HEAD
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(sql);
=======
        PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement(sql);
>>>>>>> master
        ps.setInt(1,c.getId());
        ps.setInt(2,c.getPoints());
        ps.execute();       
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
     
      public void modifier(Carte_fidelite c)
    {
       try {
        String sql="update carte_fidelite set points=? where id=?;";
<<<<<<< HEAD
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(sql);
=======
        PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement(sql);
>>>>>>> master
        ps.setInt(1,c.getPoints());
        ps.setInt(2,c.getId());
        ps.execute();
        }
        catch (Exception ex) {
        System.out.println(ex);
        }
        }
    public void Supprimer(int id) {
          try {
<<<<<<< HEAD
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("delete from carte_fidelite where id=?");
=======
            PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement("delete from carte_fidelite where id=?");
>>>>>>> master
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Gestion_UtilisateurController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Carte_fidelite> afficher() {
     
        ObservableList<Carte_fidelite> data=FXCollections.observableArrayList();
        try
        {
<<<<<<< HEAD
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select * from carte_fidelite");
=======
        PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement("select * from carte_fidelite");
>>>>>>> master
        ResultSet rs=ps.executeQuery();
            while (rs.next())
            {
            data.add(new Carte_fidelite(rs.getInt(1),rs.getInt(2)));
            }
        }
        catch(Exception ex)
        {
        System.out.println(ex);
        }
        ;
         return data;
     }    

    @Override
    public ObservableList<String> GetAllIdUser() {
        ObservableList<String> data=FXCollections.observableArrayList();
        try
        {
<<<<<<< HEAD
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select id from utilisateur");
=======
        PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement("select id from utilisateur");
>>>>>>> master
        ResultSet rs=ps.executeQuery();
            while (rs.next())
            {
            data.add(rs.getString(1));
            }
        }
        catch(Exception ex)
        {
        System.out.println(ex);
        }
        ;
         return data;
    }
    
    
    public ObservableList<String> GetAllIdCarte() {
        ObservableList<String> data=FXCollections.observableArrayList();
        try
        {
<<<<<<< HEAD
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select id from carte_fidelite");
=======
        PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement("select id from carte_fidelite");
>>>>>>> master
        ResultSet rs=ps.executeQuery();
            while (rs.next())
            {
            data.add(rs.getString(1));
            }
        }
        catch(Exception ex)
        {
        System.out.println(ex);
        }
        ;
         return data;
    }
    
    public boolean CheckCarteById(int id) {
        ObservableList<String> data=FXCollections.observableArrayList();
        try
        {
<<<<<<< HEAD
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select id from carte_fidelite where id=?");
=======
        PreparedStatement ps=MyConnexion.getInstance().getCnx().prepareStatement("select id from carte_fidelite where id=?");
>>>>>>> master
        ps.setInt(1, id);
        ResultSet rs=ps.executeQuery();
            while (rs.next())
            {
            data.add(rs.getString(1));
            }
        }
        catch(Exception ex)
        {
        System.out.println(ex);
        }
        ;
         return data.contains(String.valueOf(id));
    }
}
