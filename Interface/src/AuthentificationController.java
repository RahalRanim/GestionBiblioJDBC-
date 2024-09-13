import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AuthentificationController {
    
    @FXML
    private TextField userlogin;

    @FXML
    private TextField userpwd;

    @FXML
    void Authentifier(ActionEvent event) {
        String login = userlogin.getText();
        String password = userpwd.getText();
        Utilisateur u = new Utilisateur(0, "", "", login, password, "");
        if (u.test_utilisateur(login, password)) {
            String role = Utilisateur.role_utilisateur(login, password);
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Bienvenue");
            successAlert.setHeaderText("Votre rôle est "+role);
            successAlert.showAndWait();
            if (role.equals("etudiant") || role.equals("enseignant")) {
                openChoixEtudEns();
            } else {
                openChoixBib();
            }
        } else {
            Alert failureAlert = new Alert(AlertType.INFORMATION);
            failureAlert.setTitle("Échec d'authentification");
            failureAlert.setHeaderText("Authentification invalide");
            failureAlert.setContentText("Vous n'existez pas dans la base de données de la bibliothèque.");
            failureAlert.showAndWait();
        }
    }
    private void openChoixBib()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Biblio.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Menu de choix");
            stage.setScene(new Scene(root));
    
            stage.show();
            userlogin.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }
    private void openChoixEtudEns() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Etud_Ens.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Menu de choix");
            stage.setScene(new Scene(root));
    
            stage.show();
            userlogin.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }
    
}
