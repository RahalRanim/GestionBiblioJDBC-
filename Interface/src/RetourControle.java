import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RetourControle {

    @FXML
    private TextField idl;

    @FXML
    private TextField idu;

    @FXML
    void retour(ActionEvent event) {
        String l = idl.getText();
        String u =idu.getText(); 
        try {
            int liv = Integer.parseInt(l);
            int util = Integer.parseInt(u);
            boolean test=Emprunt.retour_livre(liv, util);
            if (test) {
                int prs=Reservation.premier_reserv(liv);
                Reservation.reservation_confirme(prs);
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Retour réussi");
                successAlert.setHeaderText("Le retour du livre s'est effectué avec succès");
                successAlert.showAndWait();
            }
            else
            {
                Alert failureAlert = new Alert(AlertType.INFORMATION);
                failureAlert.setTitle("Échec de retour");
                failureAlert.setHeaderText("Le retour du livre a échoué");
                failureAlert.setContentText("Veuillez réessayer ou contacter le personnel de la bibliothèque.");
                failureAlert.showAndWait();
            }
            

        } catch (NumberFormatException e) {
            Alert failureAlert = new Alert(AlertType.INFORMATION);
            failureAlert.setTitle("Erreur");
            failureAlert.setHeaderText("L'id doit être une valeur numérique.");
            failureAlert.showAndWait();
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

            Stage currentStage = (Stage) idl.getScene().getWindow();
            currentStage.close();

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }

}