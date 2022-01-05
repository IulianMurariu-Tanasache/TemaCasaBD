package ProiectBD.DataClasses.Adapter;

import ProiectBD.DataClasses.AdminClasses.Meci_Stat;
import ProiectBD.SQL.BadFormatException;

import java.util.Map;
import java.util.regex.Matcher;

import static java.util.Map.entry;

//Implementarea interfetei Adapter pentru tabela Meciuri
public class Meci_StatAdapter extends Adapter<Meci_Stat>{

    final private Map<String,String[]> fk = Map.ofEntries(
            entry("id_meci",new String[]{"echipa1_id || ' - ' || echipa2_id","MECIURI"}),
            entry("id_jucator",new String[]{"nume || ' ' || prenume","JUCATORI"})
    );

    @Override
    public Meci_Stat createObject(String[] parameters) throws BadFormatException {
        Integer gol = parseInt(parameters[0]); //null
        Integer asist = parseInt(parameters[1]); //null
        Integer cart = parseInt(parameters[2]); //null
        String moment = parameters[3];
        String id_meci = parameters[4]; //fk
        String jucator = parameters[5]; //fk

        Matcher momentMatcher = momentP.matcher(moment);
        if(!momentMatcher.matches())
            throw new BadFormatException("Timp introdus incorect! Formatul este HH:MM:SS");

        return new Meci_Stat(id_meci,jucator,gol,asist,cart,moment);
    }

    @Override
    public Map<String, String[]> getFKs() {
        return fk;
    }

}
