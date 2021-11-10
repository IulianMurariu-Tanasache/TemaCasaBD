package DataClasses.Adapter;

import DataClasses.Stadion;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//Implementarea interfetei Adapter pentru tabela Stadioane
public class StadionAdapter implements Adapter<Stadion>{

    @Override
    public Stadion createObject(String[] parameters) {
        String nume = parameters[0];
        String locatie = parameters[1];
        int loc = Integer.parseInt(parameters[2]);
        String data = parameters[3];
        //data = Date.valueOf(parameters[3]);
        String meci = parameters[4];
        return new Stadion(nume, locatie, loc, data, meci);
    }

}
