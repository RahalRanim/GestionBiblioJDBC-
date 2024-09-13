import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Menu1Controle {

    @FXML
    private TextField choix;

    @FXML
    private Button entrer;

    @FXML
    void Entrer(ActionEvent event) {
        String c = choix.getText();
        try {
            int nb = Integer.parseInt(c);
            switch (nb) {
                case 1:
                    opencatalogue();
                    break;
                case 2:
                    //chercher un livre
                    openrecherche();
                    break;
                case 3:
                    //afficher la gestion des emprunts
                    openCatEmp(); 
                    break;
                case 4:
                    // Emprunter un livre
                    openEmprunt();
                    break;
                case 5:
                    // Retour d'un livre
                    openRetour();
                    break;
                default:
                    Alert failureAlert = new Alert(AlertType.INFORMATION);
                    failureAlert.setTitle("Choix invalide");
                    failureAlert.setHeaderText(null);
                    failureAlert.setContentText("Veuillez saisir un nombre entre 1 et 5 comme l'indique dans le menu.");
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
    private void openrecherche() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chercher_livre.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Recherche");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }

     private void openEmprunt() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("emprunterLivre.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Emprunter");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }

    private void openRetour() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("retourLivre.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Retour");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }
    private void opencatalogue() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("consulter_catalogue.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Emprunter");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture ");
        }
    }
    private void openCatEmp() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("historiqueEmp.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Historiques");
            stage.setScene(new Scene(root));
    
            stage.show();
            choix.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture ");
        }
    }

}