public class Livre_Plus_Emp {
    private String titre;
    private int nbe;

    public Livre_Plus_Emp(String titre, int nbe) {
        this.titre = titre;
        this.nbe = nbe;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNbe() {
        return nbe;
    }

    public void setNbe(int nbe) {
        this.nbe = nbe;
    }
}
