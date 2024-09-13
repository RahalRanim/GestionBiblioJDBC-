import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class EmprunterContole {

    @FXML
    private TextField idl;

    @FXML
    private TextField idu;


    @FXML
    void emprunter(ActionEvent event) {
        String l = idl.getText();
        String u =idu.getText(); 
        try {
            int liv = Integer.parseInt(l);
            int idu = Integer.parseInt(u);
            boolean test = Emprunt.Rech_liv_emp(liv);
                if (test) {
                    // Le livre est déjà emprunté
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Livre déjà emprunté");
                    alert.setHeaderText("Le livre est déjà emprunté");
                    alert.setContentText("Voulez-vous faire une réservation ?");

                    ButtonType buttonTypeOui = new ButtonType("Oui");
                    ButtonType buttonTypeNon = new ButtonType("Non");

                    alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeOui) {
                            Reservation.reserve_livre(idu, liv);
                            Alert successAlert = new Alert(AlertType.INFORMATION);
                            successAlert.setTitle("Réservation réussi");
                            successAlert.setHeaderText("La réservation du livre s'est effectué avec succès");
                            successAlert.showAndWait();
                        }
                    });
            } else {
                test=Emprunt.emprunt_livre(liv, idu);
                if (test) {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Emprunt réussi");
                    successAlert.setHeaderText("L'emprunt du livre s'est effectué avec succès");
                    successAlert.showAndWait();
                }
                else
                {
                    Alert failureAlert = new Alert(AlertType.INFORMATION);
                    failureAlert.setTitle("Échec de l'emprunt");
                    failureAlert.setHeaderText("L'emprunt du livre a échoué");
                    failureAlert.setContentText("Veuillez réessayer ou contacter le personnel de la bibliothèque.");
                    failureAlert.showAndWait();
                }
            
            }
        }
        catch (NumberFormatException e) {
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

