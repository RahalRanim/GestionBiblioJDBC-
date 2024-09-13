import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Livre_Plus_EmpController implements Initializable {

    @FXML
    private TableColumn<Livre_Plus_Emp, Integer> nbe;

    @FXML
    private TableView<Livre_Plus_Emp> table;

    @FXML
    private TableColumn<Livre_Plus_Emp, String> titre;

     ObservableList<Livre_Plus_Emp> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        nbe.setCellValueFactory(new PropertyValueFactory<>("nbe"));
        afficher();
    }

    private void afficher()
    {
        try{
            Connection connection = ConnectionManager.getConnection();
            String query = "select livre.titre, count(emprunt.id_livre) As Nombre_emprunts from livre , emprunt where emprunt.id_livre=livre.id_livre group by livre.titre order by Nombre_emprunts desc";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString(1);
                    int nbe = resultSet.getInt(2);
                    list.add(new Livre_Plus_Emp(title, nbe));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(list);
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
