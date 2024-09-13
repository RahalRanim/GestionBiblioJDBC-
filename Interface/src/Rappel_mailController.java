import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Rappel_mailController {

    @FXML
    private TableColumn<Utilisateur, String> nom;

    @FXML
    private TableColumn<Utilisateur, String> prenom;

    @FXML
    private TableView<Utilisateur> table;

    public void initialize() {
        nom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
        prenom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));

        chargerDonnees();
    }
    private void chargerDonnees() {
        try{
            Connection connection = ConnectionManager.getConnection();
            String query = "SELECT utilisateur.nom, utilisateur.prenom FROM emprunt JOIN utilisateur ON emprunt.id_utilisateur = utilisateur.id_utilisateur WHERE emprunt.date_retour < NOW() AND statut='en cours'";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String Nom = resultSet.getString("nom");
                        String Prenom = resultSet.getString("prenom");
                        Utilisateur u = new Utilisateur(Nom, Prenom);
                        table.getItems().add(u);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeFn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Biblio.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu de choix");
            stage.setScene(new Scene(root));

            Stage currentStage = (Stage) table.getScene().getWindow();
            currentStage.close();

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du menu de choix.");
        }
    }

}

