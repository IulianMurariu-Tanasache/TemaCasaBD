package ProiectBD.DataClasses.Adapter;

import ProiectBD.DataClasses.AdminClasses.Stadion;
import ProiectBD.SQL.BadFormatException;

import java.util.Map;
import java.util.regex.Matcher;

import static java.util.Map.entry;

//Implementarea interfetei Adapter pentru tabela Stadioane
public class StadionAdapter extends Adapter<Stadion>{

    final private Map<String,String[]> fk = Map.ofEntries(
            entry("id_echipa",new String[]{"nume","ECHIPE"})
    );

    @Override
    public Stadion createObject(String[] parameters) throws BadFormatException {
        String nume = parameters[0];
        String locatie = parameters[1];
        Integer loc = parseInt(parameters[2]); //not null
        Integer pret = parseInt(parameters[3]); //null
        String id_echipa= parameters[4]; //fk

        Matcher numeMatcher = p.matcher(nume);
        Matcher locatieMatcher = p.matcher(locatie);
        if(numeMatcher.find() || locatieMatcher.find())
            throw new BadFormatException("Un nume are caractere ilegale!");

        return new Stadion(id_echipa, nume, locatie, loc, pret);
    }

    @Override
    public Map<String, String[]> getFKs() {
        return fk;
    }

}
