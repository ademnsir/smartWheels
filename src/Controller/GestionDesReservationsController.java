/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import entities.Reservation;
import entities.Vehicule;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.Reservation_Service;
import services.vehicule_Service;

/**
 * FXML Controller class
 *
 * @author user
 */
public class GestionDesReservationsController implements Initializable {

    @FXML
    private AnchorPane contentArea;
    @FXML
    private Pane left;

    @FXML
    private TableView<Reservation> tab;
    @FXML
    private Button vehicule;
    @FXML
    private Button supprimer_reservation;
    @FXML
    private Button modifier_reservation;
    @FXML
    private Button ajouter_reservation;
    @FXML
    private TableColumn<?, ?> colid_res;
    @FXML
    private TableColumn<?, ?> coldd_r;
    @FXML
    private TableColumn<?, ?> coldf_r;
    @FXML
    private TextField id_res;
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private Button front;
    @FXML
    private TableColumn<?, ?> colm;
    @FXML
    private Button statistique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab.setOnMouseClicked(event ->{
           if (event.getClickCount() == 2 && !tab.getSelectionModel().isEmpty() )

        SetAllTextField();
        });
        this.afficher();
        
    }
public void SetAllTextField()
   {
      Reservation_Service rs= new Reservation_Service(); 
     Reservation r=tab.getSelectionModel().getSelectedItem();
      id_res.setText(Integer.toString(r.getId_res()));
      
      date_debut.setValue(LOCAL_DATE(r.getDate_debut()));
      date_fin.setValue(LOCAL_DATE(r.getDate_fin()));

      
   }    



    @FXML
    private void btn_vehicule(ActionEvent event) throws IOException {
         try{
         Parent root = FXMLLoader.load(getClass().getResource("/gui/GestionDesVehicules.fxml"));  
         Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
        catch(Exception e)
        {
            System.out.println("Probleme:"+e);
        }
             
    }
    
    

    @FXML
    private void supprimer_reservation(ActionEvent event) {
        
                Reservation r= tab.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Reservation_Service rs= new Reservation_Service();   
       
        if (r!=null){
        
        alert.setTitle("Confirmation de suppression");
         alert.setHeaderText("Confiramation de suppression");
         alert.setContentText("Voulez-vous vraiment supprimer le véhicule N°" +r.getId_res());
    
        Optional<ButtonType> result = alert.showAndWait();
         // ... user chose OK
       if (result.get() == ButtonType.OK){

            rs.Supprimer_reservation(r.getId_res());
            this.afficher();
       }
    }
  else
    {
           Alert al = new Alert(AlertType.ERROR);

           al.setTitle("Error alert");
           al.setHeaderText("Vous devez selectionner au moin un id pour suprrimer");
   
            al.showAndWait();
    }
    }
    

    @FXML
    private void modifier_reservation(ActionEvent event) {
                      if(!(id_res.getText().equals("") || date_debut.getValue().equals("")  || date_fin.getValue().equals("")))
                {
                
       Reservation_Service resS=new Reservation_Service();
        
        Reservation r_enregistre=resS.GetUserById(Integer.parseInt(id_res.getText()));
        
        Reservation r= new Reservation(Integer.parseInt(id_res.getText()),date_debut.getValue().toString(),date_fin.getValue().toString());
           
                 
         if (!(r.getId_res()==(r_enregistre.getId_res()) 
               && r.getDate_debut().equals(r_enregistre.getDate_debut()) 
               && r.getDate_fin().equals(r_enregistre.getDate_fin()) 
             
              
               )
             
            )
          
         {
       Reservation_Service rs= new Reservation_Service();
        
        resS.modifier_reservation(r);   
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Modification avec succées pour la reservation N° "+r.getId_res());
        alert.showAndWait();
        this.afficher();     
         }
         else
         {
              Alert al = new Alert(AlertType.ERROR);
           al.setTitle("Error alert");
           al.setHeaderText("Vous devez Changer au moin un attribut");
            al.showAndWait();
         }
                }
                else
                {
                     Alert al = new Alert(AlertType.ERROR);
           al.setTitle("Error alert");
           al.setHeaderText("Vous devez selectionner au moin une reservation pour modifier!");
            al.showAndWait();
                }  
    }

    @FXML
    private void ajouter_reservation(ActionEvent event) {
        
             
        //if different ll vide ->ajouter 
       if(!(id_res.getText().equals("") || date_debut.getValue().equals("")  || date_fin.getValue().equals("")))
                {
          
try {
    LocalDate dateDebut = LocalDate.parse(date_debut.getValue().toString());
    LocalDate dateFin = LocalDate.parse(date_fin.getValue().toString());
    
    if (dateDebut.isAfter(dateFin)) {
        Alert alertDate = new Alert(Alert.AlertType.ERROR);
        alertDate.setTitle("Erreur de saisie");
        alertDate.setHeaderText(null);
        alertDate.setContentText("La date de début doit être antérieure à la date de fin");
        alertDate.showAndWait();
        return;
    }
    
    else {
        Reservation r = new Reservation(Integer.parseInt(id_res.getText()), dateDebut.toString(), dateFin.toString());
    Reservation_Service a = new Reservation_Service();
    a.ajouter_reservation(r);
     a.afficher_reservation();
    Alert al = new Alert(Alert.AlertType.INFORMATION);
    al.setTitle("Réservation effectuée");
    al.setHeaderText(null);
    al.setContentText("Votre réservation a été faite.");
    al.showAndWait();
    this.afficher();
   
    }
    
    // Les dates sont valides, vous pouvez créer votre objet Reservation

} catch (DateTimeParseException e) {
    Alert alertDate = new Alert(Alert.AlertType.ERROR);
    alertDate.setTitle("Erreur de saisie");
    alertDate.setHeaderText(null);
    alertDate.setContentText("Veuillez saisir des dates valides au format YYYY-MM-DD.");
    alertDate.showAndWait();
    return;
}       
                }
       
       
       else 
        {
Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Error alert");
alert.setHeaderText("Les champs de texte d'un formulaire ne doivent pas être null/vide"); 
alert.showAndWait();
        }
       
      
                }    
    
    
     public void afficher(){
 
Reservation_Service vs= new Reservation_Service(); 
         
         colid_res.setCellValueFactory(new PropertyValueFactory<>("id_res"));
         coldd_r.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
         coldf_r.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        
         colm.setCellValueFactory(new PropertyValueFactory<>("montant"));
         tab.setItems(vs.afficher_reservation());  

 }   
    
    
    
    
    public static final LocalDate LOCAL_DATE (String dateString){
        
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
        
    }

    @FXML
    private void front(ActionEvent event) throws IOException {
         try{
         Parent root = FXMLLoader.load(getClass().getResource("/gui/frontReservation.fxml"));  
         Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
        catch(Exception e)
        {
            System.out.println("Probleme:"+e);
        }
        
    }

    @FXML
    private void statistique(ActionEvent event) {
        
                try{
         Parent root = FXMLLoader.load(getClass().getResource("/gui/statistique_Reservation.fxml"));  
         Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
        catch(Exception e)
        {
            System.out.println("Probleme:"+e);
        }
    }
    
    
    
    
}
