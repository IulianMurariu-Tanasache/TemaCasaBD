package ProiectBD.DataClasses.Adapter;


import ProiectBD.DataClasses.AdminClasses.Echipa;
import ProiectBD.SQL.BadFormatException;

import java.util.Map;
import java.util.regex.Matcher;

//Implementarea interfetei Adapter pentru tabela Echipe
public class EchipaAdapter extends Adapter<Echipa>{

    final private Map<String,String[]> fk = Map.ofEntries(
    );

    @Override
    public Echipa createObject(String[] parameters) throws BadFormatException {
        String nume = parameters[0];
        String tara = parameters[1];
        Matcher numeMatcher = p.matcher(nume);
        Matcher taraMatcher = p.matcher(tara);
        if(numeMatcher.find() || taraMatcher.find()) {
            throw new BadFormatException("Un nume are caractere ilegale!");
        }

        return new Echipa(nume, tara);
    }

    @Override
    public Map<String, String[]> getFKs() {
        return fk;
    }
}
