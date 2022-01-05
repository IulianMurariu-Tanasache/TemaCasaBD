package ProiectBD.DataClasses.Adapter;

import ProiectBD.DataClasses.AdminClasses.Meci;
import ProiectBD.SQL.BadFormatException;

import java.util.Map;
import java.util.regex.Matcher;

import static java.util.Map.entry;

//Implementarea interfetei Adapter pentru tabela Meciuri
public class MeciAdapter extends Adapter<Meci>{

    final private Map<String,String[]> fk = Map.ofEntries(
            entry("Echipa 1",new String[]{"nume","ECHIPE"}),
            entry("Echipa 2",new String[]{"nume","ECHIPE"}),
            entry("Acasa",new String[]{"nume","ECHIPE"})
    );

    @Override
    public Meci createObject(String[] parameters) throws BadFormatException {
        String data = parameters[0];
        Integer scor1 = parseInt(parameters[1]); //null
        Integer scor2 = parseInt(parameters[2]); //null
        String echipa1 = parameters[3]; //fk
        String echipa2 = parameters[4]; //fk
        String stadion = parameters[5]; //fk

        Matcher dataMatcher = dataP.matcher(data);
        if(!dataMatcher.matches())
            throw new BadFormatException("Data introdusa incorect! Formatul este DD.MM.YYYY");

        return new Meci(echipa1, echipa2, data, scor1, scor2, stadion);
    }

    @Override
    public Map<String, String[]> getFKs() {
        return fk;
    }

}
