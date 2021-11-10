package DataClasses;

import java.sql.Date;

//Clasa care reprezinta o inregistrare in tabela Stadioane
public class Stadion {

    private String nume;
    private String locatie;
    private int locuriTotale;
    private Date data;
    private String meci;

    public Stadion(String nume, String locatie, int locuriTotale, String data, String meci) {
        this.nume = nume;
        this.locatie = locatie;
        this.locuriTotale = locuriTotale;
        this.data = Date.valueOf(data);
        this.meci = meci;
    }

    public Stadion(String nume, String locatie, int locuriTotale, Date data, String meci) {
        this.nume = nume;
        this.locatie = locatie;
        this.locuriTotale = locuriTotale;
        this.data = data;
        this.meci = meci;
    }

    public String getNume() {
        return nume;
    }

    public String getLocatie() {
        return locatie;
    }

    public int getLocuriTotale() {
        return locuriTotale;
    }

    public String getData() {
        return data.toString();
    }

    public String getMeci() {
        return meci;
    }


}
