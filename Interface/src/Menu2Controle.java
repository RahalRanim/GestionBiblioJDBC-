
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Menu2Controle {

    @FXML
    private TextField choix;

    @FXML
    void choixbib(ActionEvent event) {
        String c = choix.getText();
        try {
            int nb = Integer.parseInt(c);
            switch (nb) {
                case 1:
                    openlivre();
                    break;
                case 2:
                    openutilisateur();
                    break;
                case 3:
                    openRappel();
                    break;
                default:
                    Alert failureAlert = new Alert(AlertType.INFORMATION);
                    failureAlert.setTitle("Choix invalide");
                    failureAlert.setHeaderText(null);
                    failureAlert.setContentText("Veuillez saisir un nombre entre 1 et 3 comme l'indique dans le menu.");
                    failureAlert.showAndWait();
                    break;
            }
        } catch (NumberFormatException e) {
            Alert failureAlert = new Alert(AlertType.INFORMATION);
            failureAlert.setTitle("Choix invalide");
            failureAlert.setHeaderText(null);
            failureAlert.setContentText("Veuillez saisir un nombre SVP !!! ");
            failureAlert.showAndWait();
        } 
    }
    private void openlivre() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("livre_plus_emp.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Plus empruntés");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture.");
        }
    }

    private void openutilisateur() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("util_plus_assidus.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Plus empruntés");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture");
        }
    }
    private void openRappel() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rappel_mail.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Rappel");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture.");
        }
    }

}
