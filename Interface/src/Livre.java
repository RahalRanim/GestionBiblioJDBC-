public class Livre {
    private int idL;
    private String titre;
    private String auteur;
    private String genre;
    private int dispo;

    public Livre(int idL, String titre, String auteur, String genre, int dispo) {
        this.idL = idL;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.dispo = dispo;
    }
    public Livre(String titre,String auteur,String genre)
    {
        this.titre=titre;
        this.auteur=auteur;
        this.genre=genre;
    }

    // Getters
    public int getIdL() {
        return idL;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getGenre() {
        return genre;
    }

    public int getDispo() {
        return dispo;
    }

    // Setters
    public void setIdL(int idL) {
        this.idL = idL;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }
    

    

}
