import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Utilisateur {
    private int idU;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;
    private String role;
    
    Utilisateur(int idU,String nom,String prenom,String login,String pwd,String role)
    {
        this.idU=idU;
        this.nom=nom;
        this.prenom=prenom;
        this.login=login;
        this.pwd=pwd;
        this.role=role;
    }
    public Utilisateur() {
    }
    public Utilisateur(String nom,String prenom)
    {
        this.nom=nom;
        this.prenom=prenom;
    }
    // Getters
    public int getIdU() {
        return idU;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public static int retourId(String login,String password)
    {
        int id=-1;
        try
        {
            Connection connection = ConnectionManager.getConnection();
            String sql="SELECT id_utilisateur FROM utilisateur WHERE login='"+login+"' AND pwd='"+password+"'";
            try(PreparedStatement statement=connection.prepareStatement(sql))
            {
                try(ResultSet resultset=statement.executeQuery())
                {
                    if(resultset.next())
                    {
                        id=resultset.getInt(1);
                    }
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return id;
    }
    public boolean test_utilisateur(String login,String pwd)
    {
        try
        {
            Connection connection = ConnectionManager.getConnection();
            String sql="SELECT * FROM utilisateur WHERE login='"+login+"' AND pwd='"+pwd+"'";
            try(PreparedStatement statement=connection.prepareStatement(sql))
            {
                try(ResultSet resultset=statement.executeQuery())
                {
                    return resultset.next();
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static String role_utilisateur(String login,String pwd)
    {
        String str=null;
        try
        {
            Connection connection = ConnectionManager.getConnection();
            String sql="SELECT rolee FROM utilisateur WHERE login='"+login+"' AND pwd='"+pwd+"'";
            try(PreparedStatement statement=connection.prepareStatement(sql))
            {
                try(ResultSet resultset=statement.executeQuery())
                {
                    if (resultset.next()) {

                        str = resultset.getString("rolee");
                        
                    }
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return str;
        
    }

    public static int getIdUtilisateur(String login) {
        int idUtilisateur = -1; 
        try {
            Connection connection = ConnectionManager.getConnection();

            String query = "SELECT id_utilisateur FROM utilisateur WHERE login = '"+login+"';";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idUtilisateur = resultSet.getInt("id_utilisateur");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUtilisateur;
    }
}
