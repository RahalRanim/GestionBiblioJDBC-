import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Afficher_LivreController {

    @FXML
    private TableColumn<Livre, Number> idL;

    @FXML
    private TableColumn<Livre, String> titre;

    @FXML
    private TableColumn<Livre, String> auteur;

    @FXML
    private TableColumn<Livre, String> genre;

    @FXML
    private TableColumn<Livre, Number> dispo;

    @FXML
    private TableView<Livre> table;

    public void initialize(String tit) {
        idL.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdL()));
        titre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre()));
        auteur.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuteur()));
        genre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenre()));
        dispo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDispo()));

        chargerDonnees(tit);
    }

    private void chargerDonnees(String tit) {
        try {
            Connection connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM Livre WHERE titre=?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tit);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int LivreId = resultSet.getInt("id_livre");
                        String LivreTitre = resultSet.getString("titre");
                        String LivreAuteur = resultSet.getString("auteur");
                        String LivreGenre = resultSet.getString("genre");
                        int LivreDispo = resultSet.getInt("dispo");
                        Livre l = new Livre(LivreId, LivreTitre, LivreAuteur, LivreGenre, LivreDispo);
                        table.getItems().add(l);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Etud_Ens.fxml"));
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
