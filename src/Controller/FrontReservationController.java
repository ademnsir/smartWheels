/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//adem
package Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entities.Reservation;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.Reservation_Service;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FrontReservationController implements Initializable {
  public static final String ACCOUNT_SID = "AC577d98d4a3529117634d2665ff71cb4d";
  public static final String AUTH_TOKEN = "bf8dc2397230c108b876eb25a3c46b4e";
    @FXML
    private AnchorPane contentArea;
    @FXML
    private Pane left;
    @FXML
    private Button back;
 
    @FXML
    private Label marquelabel1;
    @FXML
    private Label marquelabel2;
    @FXML
    private Label marquelabel3;
    @FXML
    private Label couleurlabel1;
    @FXML
    private Label couleurlabel2;
    @FXML
    private Label couleurlabel3;
    @FXML
    private Label vitesselabel1;
    @FXML
    private Label vitesselabel2;
    @FXML
    private Label vitesselabel3;
 

    /**
     * Initializes the controller class.
     */ 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//Affichage des donneés de la base  sur  le front
           String selectQuery = "SELECT marque ,couleur,vitesse_max FROM vehicule WHERE id = ?";
        try {
           PreparedStatement ps= MyConnexion.getIstance().getCnx().prepareStatement(selectQuery);
            for (int i = 1; i <= 6; i++) {
                ps.setInt(1, i); // id du véhicule à récupérer
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    String marque = resultSet.getString("marque");
                    String couleur = resultSet.getString("couleur");
                    String vitesse_max = resultSet.getString("vitesse_max");
                    if (i == 1) {
                        marquelabel1.setText("Marque : " + marque);
                        couleurlabel1.setText("couleur : " + couleur);
                        vitesselabel1.setText("vitesseMax: " + vitesse_max);
                    } else if (i == 2) {
                        marquelabel2.setText("Marque : " + marque);
                         couleurlabel2.setText("couleur : " + couleur);
                         vitesselabel2.setText("vitesseMax : " + vitesse_max);
                    } else if (i == 3) {
                        marquelabel3.setText("Marque : " + marque);
                        couleurlabel3.setText("couleur : " + couleur);
                        vitesselabel3.setText("vitesseMax: " + vitesse_max);
                    }
                }
                
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }    
          

    }    

    //bouton go back to gestion des reservations
    @FXML
    private void back(ActionEvent event) throws IOException {
         try{
         Parent root = FXMLLoader.load(getClass().getResource("/gui/GestionDesReservations.fxml"));  
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
    private void ajouter(ActionEvent event) {
     
  
     // Création de l'alerte
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Saisie de plusieurs champs");
alert.setHeaderText(null);
alert.setContentText("Veuillez saisir les informations :");

// Création des champs de saisie
TextField champId = new TextField();
DatePicker champDateDebut = new DatePicker();
champDateDebut.setPromptText("YYYY-MM-DD");
DatePicker champDateFin = new DatePicker();
champDateFin.setPromptText("YYYY-MM-DD");


// Création du GridPane pour les champs de saisie
GridPane grid = new GridPane();
grid.setPrefSize(300, 300);
grid.setStyle("-fx-background-color: #6F5CC2;");
grid.setHgap(10);
grid.setVgap(10);

grid.add(new Label("Identifiant:"), 0, 0);
grid.add(champId, 1, 0);
grid.add(new Label("Date de début:"), 0, 1);
grid.add(champDateDebut, 1, 1);
grid.add(new Label("Date de fin:"), 0, 2);
grid.add(champDateFin, 1, 2);

// Ajout du GridPane à l'alerte
alert.getDialogPane().setContent(grid);

// Affichage de l'alerte et récupération des valeurs saisies
Optional<ButtonType> result = alert.showAndWait();
if (result.isPresent() && result.get() == ButtonType.OK) {
    // Vérification de la validité des dates
    LocalDate dateDebut = champDateDebut.getValue();
    LocalDate dateFin = champDateFin.getValue();
    if (dateDebut == null || dateFin == null || dateDebut.isAfter(dateFin)) {
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Erreur de saisie");
        al.setHeaderText(null);
        al.setContentText("Les dates saisies sont invalides!");
        al.showAndWait();
        return;
        
    }
    
    Reservation r = new Reservation(Integer.parseInt(champId.getText()), dateDebut.toString(), dateFin.toString());
    Reservation_Service a = new Reservation_Service();
    a.ajouter_reservation(r);

    Alert al = new Alert(Alert.AlertType.INFORMATION);
    al.setTitle("Réservation effectuée");
    al.setHeaderText(null);
    al.setContentText("Votre réservation a été faite");
    al.showAndWait();
    
    
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
      new com.twilio.type.PhoneNumber("+21652510089"),
      new com.twilio.type.PhoneNumber("+12708195429"),
      "Votre réservation a été faite de "+champDateDebut.getValue()+ " jusqu à "+champDateFin.getValue())

    .create();

    System.out.println(message.getSid());
    
}

    }
    
    


}
