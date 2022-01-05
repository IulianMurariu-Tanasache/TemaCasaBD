package ProiectBD.DataClasses.AdminClasses;

import java.math.BigDecimal;

//Clasa care reprezinta o inregistrare in tabela Stadioane
public class Stadion {

    private String id_echipa;
    private String nume;
    private String locatie;
    private int locuri_totale;
    private Integer pret_bilet;

    public Stadion(String id_echipa, String nume, String locatie, Integer locuri_totale, Integer pret_bilet) {
        this.id_echipa = id_echipa;
        this.nume = nume;
        this.locatie = locatie;
        this.locuri_totale = locuri_totale;
        this.pret_bilet = pret_bilet;
    }

    public Stadion(String id_echipa, String nume, String locatie, int locuri_totale, BigDecimal pret_bilet) {
        this.id_echipa = id_echipa;
        this.nume = nume;
        this.locatie = locatie;
        this.locuri_totale = locuri_totale;
        this.pret_bilet = pret_bilet == null ? null : pret_bilet.intValue();
    }

    public String getId_echipa() {
        return id_echipa;
    }

    public String getNume() {
        return nume;
    }

    public String getLocatie() {
        return locatie;
    }

    public int getLocuri_totale() {
        return locuri_totale;
    }

    public Integer getPret_bilet() {
        return pret_bilet;
    }
}
