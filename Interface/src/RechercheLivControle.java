import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RechercheLivControle {

    @FXML
    private Button chercher;

    @FXML
    private TextField titre;

    @FXML
    void chercher(ActionEvent event) {
        String t=titre.getText();
        openAffiche(t);
        
    }
    private void openAffiche(String t) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficher_livre.fxml"));
            Parent root = loader.load();
    
            Stage stage = new Stage();
            stage.setTitle("Afficher Détails");
            stage.setScene(new Scene(root));
            Afficher_LivreController afficherLivreController = loader.getController();
            afficherLivreController.initialize(t);
            stage.show();
            titre.getScene().getWindow().hide();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture de l'interface.");
        }
    }
    @FXML
    void closeFn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Etud_Ens.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu de choix");
            stage.setScene(new Scene(root));

            Stage currentStage = (Stage) titre.getScene().getWindow();
            currentStage.close();

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }

}