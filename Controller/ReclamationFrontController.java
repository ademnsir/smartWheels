/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.Utilisateur_gestionController.l_email;
import entities.Commentaire;
import entities.Reclamation;
import entities.Utilisateur;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import services.CommentaireCrud;
import services.ReclamationCrud;
import services.UtilisateurS;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ReclamationFrontController implements Initializable {

    private TextField toField;
    private TextField subjectField;
    private TextArea messageArea;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfcontenu;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;

   private Label label;
 
    @FXML
    private TableView<Reclamation> tvreclamation;
    @FXML
    private TableColumn<Reclamation,?> colid;
    @FXML
    private TableColumn<Reclamation, String> colnom;
    @FXML
    private TableColumn<Reclamation, String> colprenom;
    @FXML
    private TableColumn<Reclamation, String> coladresse;
    @FXML
    private TableColumn<Reclamation, String> colcontenu;
  
    ReclamationCrud rc=new ReclamationCrud();
    CommentaireCrud cmntr =new CommentaireCrud();
    private List<Reclamation> listeDesReclamations;
    private TableView<Commentaire> tvcommentaire;
    private TableColumn<Commentaire,?> colpseudo;
    private TableColumn<Commentaire, String> colcommentaire;
    @FXML
    private TableColumn<Reclamation, Date> cdate;
    @FXML
    private Button retour;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
     
      showReclamation();

    }
   
    


    
        
  
public ObservableList<Reclamation>getReclamationList(){
    
     Utilisateur utilisateur=new UtilisateurS().getUserByEmail(l_email);
ObservableList<Reclamation> ReclamationList2 = FXCollections.observableArrayList();
    this.listeDesReclamations=rc.listeDesReclamations(utilisateur.getId());
ObservableList<Reclamation> ReclamationList = FXCollections.observableArrayList();

//ReclamationList.addAll(rc.listeDesReclamations());
ReclamationList.addAll(rc.listeDesReclamations(utilisateur.getId()));
return ReclamationList; 

}  
public void showReclamation(){

ObservableList<Reclamation> List =getReclamationList();
colid.setCellValueFactory(new PropertyValueFactory<>("Id"));
colnom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
colprenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
coladresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
colcontenu.setCellValueFactory(new PropertyValueFactory<>("Contenu"));
cdate.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
    System.out.println(List);
tvreclamation.setItems(List);
}


/*ajout modifier et supprimer pour la reclamation */


    @FXML
    private void insert(ActionEvent event) {
        if (ControleSaisie().length()>0){
         Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ajout Reclamation");
        alert.setContentText(ControleSaisie());
        alert.showAndWait();}
        else {
            
        Reclamation r =new Reclamation();
        r.setNom(tfnom.getText());
        r.setPrenom(tfprenom.getText());
        r.setAdresse(tfadresse.getText());
        r.setContenu(tfcontenu.getText());
        if(rc.contentExist(r)){
            Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ajout Reclamation");
        alert.setContentText("Reclamation existe deja!!");
        alert.showAndWait();
        }
        else{
            rc.ajouterEntitee(r);
            //sendMail();
  
        }
        }


    }
    @FXML
    private void update(ActionEvent event) {
             Utilisateur utilisateur=new UtilisateurS().getUserByEmail(l_email);
     
         
         Reclamation r =new Reclamation();
         Reclamation amodifier=tvreclamation.getSelectionModel().getSelectedItem();
         Date today=new Date();
        if(today.getDate()-amodifier.getDateCreation().getDate()>1){
            Alert alert2 =new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur modification");
            alert2.setContentText("vous ne pouvez pas modifier au dela de 24h!");
            alert2.showAndWait();
        }
        else{
           if (ControleSaisie().length()>0){
         Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Modifier Reclamation");
        alert.setContentText(ControleSaisie());
        alert.showAndWait();}
        else {
        int idModifier=amodifier.getId();

       // Reclamation r =new Reclamation();
        r.setNom(tfnom.getText());
        r.setPrenom(tfprenom.getText());
        r.setAdresse(tfadresse.getText());
        r.setContenu(tfcontenu.getText());
        if(rc.contentExist(r)){
            Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Modifier Reclamation");
        alert.setContentText("Reclamation existe deja!!");
        alert.showAndWait();
        }
        else{
            rc.updateReclamation(idModifier, r);
        }
        }


        }
         

    }

    private void Delete(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Confirm Deletion");
    alert.setContentText("Are you sure you want to delete the selected item?");

    ButtonType deleteButtonType = new ButtonType("Delete");
    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(deleteButtonType, cancelButtonType);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == deleteButtonType) {
        int idSup=tvreclamation.getSelectionModel().getSelectedItem().getId();
        rc.supprimerReclamation(idSup);
    } else {
        alert.close();
    }
}
private String ControleSaisie(){
String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +"[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
    String erreur="";
if(tfnom.getText().trim().isEmpty()){
erreur+="nom vide \n";}
if(tfprenom.getText().trim().isEmpty()){
erreur+="prenom vide \n";}
if(tfadresse.getText().trim().isEmpty()){
erreur+="adresse vide \n";}
if(tfcontenu.getText().trim().isEmpty()){
erreur+="contenu vide \n";}
if (!pattern.matcher(tfadresse.getText().trim()).matches()) {
            erreur+="-Inserer correct email\n";
        }

return erreur;

}

    @FXML
    private void retour(ActionEvent event) {
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
}
