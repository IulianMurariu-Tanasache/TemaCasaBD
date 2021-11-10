package DataClasses;

//Clasa care reprezinta o inregistrare in tabela Echipe
public class Echipa {

    private String nume;
    private String tara;
    private int puncte;
    private int victorii;
    private int infrangeri;
    private int egaluri;
    private int golavg;

    public Echipa(String nume, String tara, int puncte, int victorii, int infrangeri, int egaluri, int golavg){
        this.nume = nume;
        this.tara = tara;
        this.puncte = puncte;
        this.victorii = victorii;
        this.infrangeri = infrangeri;
        this.egaluri = egaluri;
        this.golavg = golavg;
    }

    public String getNume() { return nume; }

    public String getTara() {
        return tara;
    }

    public int getPuncte() {
        return puncte;
    }

    public int getVictorii() {
        return victorii;
    }

    public int getInfrangeri() { return infrangeri; }

    public int getEgaluri() {
        return egaluri;
    }

    public int getGolavg() {
        return golavg;
    }
}
