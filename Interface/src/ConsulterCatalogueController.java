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

public class ConsulterCatalogueController {

    @FXML
    private TableColumn<Livre, String> auteur;

    @FXML
    private TableColumn<Livre, String> genre;

    @FXML
    private TableView<Livre> table;

    @FXML
    private TableColumn<Livre, String> titre;

    public void initialize() {
        titre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre()));
        auteur.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuteur()));
        genre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenre()));

        chargerDonnees();
    }

     private void chargerDonnees() {
        try {
            Connection connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM Livre";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String LivreTitre = resultSet.getString("titre");
                        String LivreAuteur = resultSet.getString("auteur");
                        String LivreGenre = resultSet.getString("genre");
                        Livre l = new Livre(LivreTitre, LivreAuteur, LivreGenre);
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

