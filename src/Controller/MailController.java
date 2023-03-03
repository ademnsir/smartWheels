package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class MailController {


    @FXML
    private TextField toField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea messageArea;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField fromField;
    @FXML
    private PasswordField passwordField;

    @FXML
    void sendMail() {
        //smartwheels0@gmail.com
        //sivxbctdmnybwtli
        String fromEmail = "smartwheels0@gmail.com";
        String password = "sivxbctdmnybwtli";
        String toEmail = toField.getText();
        String subject = subjectField.getText();
        String message = messageArea.getText();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            System.out.println("Mail sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
