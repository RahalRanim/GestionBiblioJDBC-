import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private int idR;
    private int idU;
    private int idL;
    private String date_res;
    private String statut; // en attente , confirmée

    public Reservation(int idR, int idU, int idL, String date_res, String statut) {
        this.idR = idR;
        this.idU = idU;
        this.idL = idL;
        this.date_res = date_res;
        this.statut = statut;
    }

    // Getters
    public int getIdR() {
        return idR;
    }

    public int getIdU() {
        return idU;
    }

    public int getIdL() {
        return idL;
    }

    public String getDate_res() {
        return date_res;
    }

    public String getStatut() {
        return statut;
    }

    // Setters
    public void setIdR(int idR) {
        this.idR = idR;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setIdL(int idL) {
        this.idL = idL;
    }

    public void setDate_res(String date_res) {
        this.date_res = date_res;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public static void reserve_livre(int idu, int idl) {
        try {
            Connection connection = ConnectionManager.getConnection();

            String query = "INSERT INTO reservation(id_utilisateur, id_livre, date_reservation, statut2) VALUES (?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idu);
                statement.setInt(2, idl);

                LocalDate dateAujourdhui = LocalDate.now();
                statement.setString(3, dateAujourdhui.format(DateTimeFormatter.ISO_DATE));

                statement.setString(4, "En Attente");

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int premier_reserv(int idl) {
        int idPremiereReservation = -1; 
        try {
            Connection connection = ConnectionManager.getConnection();

            String query = "SELECT id_reservation FROM reservation WHERE id_livre = ? ORDER BY date_reservation ASC LIMIT 1;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idl);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idPremiereReservation = resultSet.getInt("id_reservation");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idPremiereReservation;
    }
    public static void reservation_confirme(int idReservation) {
        try {
            Connection connection = ConnectionManager.getConnection();
            
            // Mettre à jour la réservation
            String updateQuery = "UPDATE reservation SET statut2 = 'confirmé' WHERE id_reservation = ? AND statut2 = 'en attente';";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, idReservation);
                updateStatement.executeUpdate();
            }
            
            // Récupérer les informations nécessaires de la réservation
            String selectQuery = "SELECT id_utilisateur, id_livre FROM reservation WHERE id_reservation = ?;";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setInt(1, idReservation);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int idUtilisateur = resultSet.getInt("id_utilisateur");
                        int idLivre = resultSet.getInt("id_livre");
    
                        // Insérer une nouvelle entrée dans la table emprunt
                        String insertQuery = "INSERT INTO emprunt (id_utilisateur, id_livre, date_emprunt, date_retour, statut) VALUES (?, ?, ?, ?, ?);";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            // Utiliser la date courante et ajouter 20 jours pour date_retour
                            LocalDate dateEmprunt = LocalDate.now();
                            LocalDate dateRetour = dateEmprunt.plusDays(20);
    
                            insertStatement.setInt(1, idUtilisateur);
                            insertStatement.setInt(2, idLivre);
                            insertStatement.setDate(3, Date.valueOf(dateEmprunt));
                            insertStatement.setDate(4, Date.valueOf(dateRetour));
                            insertStatement.setString(5, "en cours");
    
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
