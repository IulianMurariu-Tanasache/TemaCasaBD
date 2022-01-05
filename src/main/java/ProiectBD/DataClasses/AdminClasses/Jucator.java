package ProiectBD.DataClasses.AdminClasses;

//Clasa care reprezinta o inregistrare in tabela Jucatori
public class Jucator {

    private String id_jucator;
    private String nume;
    private String prenume;
    private String tara;
    private String echipa_id;
    private String data_nastere;
    private String poz;

    public Jucator(String id_jucator, String nume, String prenume, String tara, String echipa_id, String data_nastere, String poz) {
        this.id_jucator = id_jucator;
        this.nume = nume;
        this.prenume = prenume;
        this.tara = tara;
        this.echipa_id = echipa_id;
        this.data_nastere = data_nastere;
        this.poz = poz.toUpperCase();
    }

    public Jucator(String nume, String prenume, String tara, String echipa_id, String data_nastere, String poz) {
        this.nume = nume;
        this.prenume = prenume;
        this.tara = tara;
        this.echipa_id = echipa_id;
        this.data_nastere = data_nastere;
        this.poz = poz.toUpperCase();
        this.id_jucator = genId();
    }

    private String genId() {
        return "" + this.getNume().substring(0,2).toLowerCase() + this.getPrenume().substring(0,2).toLowerCase() + this.getEchipa_id().substring(0,2);
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

    public String getEchipa_id() {
        return echipa_id;
    }

    public String getData_nastere() {
        return data_nastere;
    }

    public String getId_jucator() {
        return id_jucator;
    }

    public String getPoz() {
        return poz;
    }
}
