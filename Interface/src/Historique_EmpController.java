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

public class Historique_EmpController {

    @FXML
    private TableColumn<LivreEmprunts, String> date;

    @FXML
    private TableColumn<LivreEmprunts, String> statut;

    @FXML
    private TableView<LivreEmprunts> table;

    @FXML
    private TableColumn<LivreEmprunts, String> titre;
    public void initialize() {
        titre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre()));
        date.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        statut.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatut()));

        chargerDonnees();
    }
    private void chargerDonnees() {
        try {
            Connection connection = ConnectionManager.getConnection();
            String query = "SELECT l.titre, e.date_emprunt, e.statut FROM emprunt e JOIN livre l ON e.id_livre = l.id_livre;";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String Titre = resultSet.getString("titre");
                        String DateE = resultSet.getString("date_emprunt");
                        String Statut = resultSet.getString("statut");
                        LivreEmprunts le = new LivreEmprunts(Titre, DateE, Statut);
                        table.getItems().add(le);
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