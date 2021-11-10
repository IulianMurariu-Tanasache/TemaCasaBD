package DataClasses;

//Clasa care reprezinta o inregistrare in tabela Jucatori
public class Jucator {

    private String nume;
    private String prenume;
    private String tara;
    private String echipa;
    private int goluri;
    private int galben;
    private int rosu;

    public Jucator(String nume, String prenume, String tara, String echipa, int goluri, int galben, int rosu){
        this.echipa = echipa;
        this.galben = galben;
        this.nume = nume;
        this.tara = tara;
        this.prenume = prenume;
        this.goluri = goluri;
        this.rosu = rosu;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTara() {
        return tara;
    }

    public String getEchipa() {
        return echipa;
    }

    public int getGoluri() {
        return goluri;
    }

    public int getGalben() {
        return galben;
    }

    public int getRosu() {
        return rosu;
    }

}
