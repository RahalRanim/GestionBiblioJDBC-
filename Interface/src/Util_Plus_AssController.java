import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Util_Plus_AssController {

    @FXML
    private TableColumn<Util_Plus_Ass, String> nom;

    @FXML
    private TableColumn<Util_Plus_Ass, String> prenom;

    @FXML
    private TableColumn<Util_Plus_Ass, Number> nbe;

    @FXML
    private TableView<Util_Plus_Ass> table;

    public void initialize() {
        nom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
        prenom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));
        nbe.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNbe()));

        chargerDonnees();
    }

    private void chargerDonnees() {
        try {
            Connection connection = ConnectionManager.getConnection();
            String query = "SELECT utilisateur.nom, utilisateur.prenom, COUNT(emprunt.id_emprunt) AS nombre_emprunts " +
                    "FROM utilisateur " +
                    "JOIN emprunt ON utilisateur.id_utilisateur = emprunt.id_utilisateur " +
                    "GROUP BY utilisateur.id_utilisateur " +
                    "ORDER BY nombre_emprunts DESC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String utilisateurNom = resultSet.getString("nom");
                    String utilisateurPrenom = resultSet.getString("prenom");
                    int nombreEmprunts = resultSet.getInt("nombre_emprunts");

                    Util_Plus_Ass utilisateur = new Util_Plus_Ass(utilisateurNom, utilisateurPrenom, nombreEmprunts);
                    table.getItems().add(utilisateur);
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
