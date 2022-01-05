package ProiectBD.DataClasses.AdminClasses;

import java.util.Random;

//Clasa care reprezinta o inregistrare in tabela Echipe
public class Echipa {

    private String id_echipa;
    private String nume;
    private String tara;

    public Echipa(String nume, String tara){
        this.nume = nume;
        this.tara = tara;
        id_echipa = genID();
    }

    public Echipa(String id, String nume, String tara){
        this.nume = nume;
        this.tara = tara;
        this.id_echipa = id;
    }

    public String genID(){
        return nume.substring(0,2).toLowerCase() + (new Random()).nextInt(1000);
    }

    public String getNume() { return nume; }

    public String getTara() {
        return tara;
    }

    public String getId_echipa() {
        return id_echipa;
    }
}
