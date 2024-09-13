import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprunt {
    private int idE;
    private int idU;
    private int idL;
    private String date_emp;
    private String date_ret;
    private String statut; // en cours , terminé , en retard
    public Emprunt(int idE,int idU,int idL,String date_emp,String date_ret,String statut)
    {
        this.idE=idE;
        this.idU=idE;
        this.idL=idL;
        this.date_emp=date_emp;
        this.date_ret=date_ret;
        this.statut=statut;
    }
    // Getters
    public int getIdE() {
        return idE;
    }

    public int getIdU() {
        return idU;
    }

    public int getIdL() {
        return idL;
    }

    public String getDate_emp() {
        return date_emp;
    }

    public String getDate_ret() {
        return date_ret;
    }

    public String getStatut() {
        return statut;
    }

    // Setters
    public void setIdE(int idE) {
        this.idE = idE;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setIdL(int idL) {
        this.idL = idL;
    }

    public void setDate_emp(String date_emp) {
        this.date_emp = date_emp;
    }

    public void setDate_ret(String date_ret) {
        this.date_ret = date_ret;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public static void affiche_emp()
    {
        try {
            Connection connection = ConnectionManager.getConnection();

            String query = "select titre, date_emprunt,statut from emprunt e,livre l where e.id_livre=l.id_livre;";
            try(PreparedStatement statement=connection.prepareStatement(query))
            {
                try(ResultSet resultset=statement.executeQuery())
                {
                    while (resultset.next()) {
                    
                        String titre = resultset.getString("titre");
                        String date = resultset.getString("date_emprunt");
                        String statut = resultset.getString("statut");
                        System.out.println("Titre: " + titre + ", | Date: " + date + ", | Statut: " + statut);
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void rappels()
    {
        Connection connection = ConnectionManager.getConnection();
        String query = "SELECT utilisateur.nom , utilisateur.prenom FROM emprunt JOIN utilisateur ON emprunt.id_utilisateur = utilisateur.id_utilisateur WHERE emprunt.date_retour < NOW()";
        try(PreparedStatement statement=connection.prepareStatement(query))
            {
                try(ResultSet resultset=statement.executeQuery())
                {
                    if (resultset.next()) {
                    
                        String nom = resultset.getString(1);
                        String pr = resultset.getString(2);
                        System.out.println(nom+" "+pr+" a déjà reçu un mail ");

                        while (resultset.next()) {
                            nom = resultset.getString(1);
                            pr = resultset.getString(2);
                            System.out.println(nom+" "+pr+" a déjà reçu un mail ");

                        }
                    }
                    else
                    {
                        System.out.println("pas de retard ! jawek behi");
                    }
                }
            }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static boolean Rech_liv_emp(int idLivre) {
        try {
            Connection connection = ConnectionManager.getConnection();

            // Préparer la requête SQL pour vérifier si le livre est déjà emprunté
            String query = "SELECT * FROM emprunt WHERE id_livre = "+idLivre+" AND statut='en cours';";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                try (ResultSet resultset = statement.executeQuery()) {
                    return resultset.next(); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int Rech_liv_emp_util(int idLivre,int idUtil) {
        int ide=-1;
        try {
            Connection connection = ConnectionManager.getConnection();

            String query = "SELECT id_emprunt FROM emprunt WHERE id_livre = "+idLivre+" AND id_utilisateur ="+idUtil+";";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                try (ResultSet resultset = statement.executeQuery()) {
                    if( resultset.next()){ide=resultset.getInt(1);} 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ide;
    }
    public static boolean emprunt_livre(int idLivre, int idUtilisateur) {
        try {
            Connection connection = ConnectionManager.getConnection();

            String query = "INSERT INTO emprunt(id_utilisateur,id_livre,date_emprunt,date_retour,statut) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, idUtilisateur);
                statement.setInt(2, idLivre);

                LocalDate dateAujourdhui = LocalDate.now();
                statement.setString(3, dateAujourdhui.format(DateTimeFormatter.ISO_DATE));

                // Date d'aujourd'hui + 20 jours
                LocalDate dateRetour = dateAujourdhui.plusDays(20);
                statement.setString(4, dateRetour.format(DateTimeFormatter.ISO_DATE));

                statement.setString(5, "En cours");

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    return true;  
                } else {
                    return false;  
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static boolean retour_livre(int idLivre, int idUtilisateur) {
        try {
            Connection connection = ConnectionManager.getConnection();
    
            // Vérifier si l'utilisateur a emprunté le livre
            String checkQuery = "SELECT * FROM emprunt WHERE id_livre = ? AND id_utilisateur = ? AND statut = 'En cours';";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, idLivre);
                checkStatement.setInt(2, idUtilisateur);
    
                try (ResultSet checkResultSet = checkStatement.executeQuery()) {
                    if (checkResultSet.next()) {
                        LocalDate dateRetour = LocalDate.parse(checkResultSet.getString("date_retour"));
    
                        LocalDate dateAujourdhui = LocalDate.now();
                        String nvs = (dateAujourdhui.isAfter(dateRetour) || dateAujourdhui.isEqual(dateRetour)) ? "En retard" : "Terminé";
    
                        String updateQuery = "UPDATE emprunt SET statut = ? WHERE id_livre = ? AND id_utilisateur = ? AND statut = 'En cours';";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, nvs);
                            updateStatement.setInt(2, idLivre);
                            updateStatement.setInt(3, idUtilisateur);
    
                            int rowsAffected = updateStatement.executeUpdate();
    
                            if (rowsAffected > 0) {
                                return true; 
                            } else {
                                return false;  
                            }
                        }
                    } else {
                        return false; 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  
        }
    }
    
}
