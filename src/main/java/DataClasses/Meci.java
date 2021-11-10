package DataClasses;

import java.sql.Date;

//Clasa care reprezinta o inregistrare in tabela Meciuri
public class Meci {

    private int nr;
    private String echipa1;
    private String echipa2;
    private Date data;
    private String rezultat;
    private String stadion;
    private int locuriLibere;

    public Meci(int nr, String echipa1, String echipa2, String data, String rezultat, String stadion, int locuriLibere) {
        this.nr = nr;
        this.echipa1 = echipa1;
        this.echipa2 = echipa2;
        this.data = Date.valueOf(data);
        this.rezultat = rezultat;
        this.stadion = stadion;
        this.locuriLibere = locuriLibere;
    }

    public Meci(int nr, String echipa1, String echipa2, Date data, String rezultat, String stadion, int locuriLibere) {
        this.nr = nr;
        this.echipa1 = echipa1;
        this.echipa2 = echipa2;
        this.data = data;
        this.rezultat = rezultat;
        this.stadion = stadion;
        this.locuriLibere = locuriLibere;
    }

    public int getNr() {
        return nr;
    }

    public String getEchipa1() {
        return echipa1;
    }

    public String getEchipa2() {
        return echipa2;
    }

    public String getData() {
        return data.toString();
    }

    public String getRezultat() {
        return rezultat;
    }

    public String getStadion() {
        return stadion;
    }

    public int getLocuriLibere() {
        return locuriLibere;
    }


}
