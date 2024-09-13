public class LivreEmprunts {
    private String titre;
    private String date;
    private String statut;

    public LivreEmprunts(String titre,String date,String statut)
    {
        this.titre=titre;
        this.date=date;
        this.statut=statut;
    }

    public String getTitre() {
        return titre;
    }

    public String getDate() {
        return date;
    }

    public String getStatut() {
        return statut;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
