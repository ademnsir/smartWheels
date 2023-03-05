/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.TransportYasmine.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class Myconnection {
     String url="jdbc:mysql://localhost:3306/transport";
    String login="root";
    String pwd="";
    private Connection cnx;
    private static Myconnection instance;

    public Myconnection() {
        try {
         cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }
    
    public static Myconnection getInstance(){
        if(instance == null){
            instance = new Myconnection();
        }
         return instance;
    }
    
}
