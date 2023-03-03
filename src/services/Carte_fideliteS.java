/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.print.DocFlavor;
import Controller.Gestion_UtilisateurController;
import interfaces.interfaceCarte;
import entities.Carte_fidelite;
import entities.Utilisateur;
import utils.MyConnection;

/**
 *
 * @author aziz
 */
public class Carte_fideliteS implements interfaceCarte<Carte_fidelite>{
     public void ajouter(Carte_fidelite c)
    {
        try {      
        String sql="insert into carte_fidelite (id,points) values (?,?)";
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(sql);
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
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement(sql);
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
            PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("delete from carte_fidelite where id=?");
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
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select * from carte_fidelite");
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
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select id from utilisateur");
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
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select id from carte_fidelite");
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
        PreparedStatement ps=MyConnection.getInstance().getCnx().prepareStatement("select id from carte_fidelite where id=?");
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
