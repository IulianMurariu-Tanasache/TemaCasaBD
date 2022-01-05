package ProiectBD.DataClasses.Adapter;
import ProiectBD.DataClasses.AdminClasses.Jucator;
import ProiectBD.SQL.BadFormatException;

import static java.util.Map.entry;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Implementarea interfetei Adapter pentru tabela Jucatori
public class JucatorAdapter extends Adapter<Jucator>{

    final private Map<String,String[]> fk = Map.ofEntries(
            entry("echipa_id",new String[]{"nume", "ECHIPE"})
    );

    @Override
    public Jucator createObject(String[] parameters) throws BadFormatException {
        String nume = parameters[0];
        String prenume = parameters[1];
        String tara = parameters[2];
        String data = parameters[3];
        String poz = parameters[4];
        String echipa = parameters[5]; // fk

        Matcher numeMatcher = p.matcher(nume);
        Matcher prenumeMatcher = p.matcher(prenume);
        Matcher taraMatcher = p.matcher(tara);
        Matcher dataMatcher = dataP.matcher(data);
        Pattern pozP = Pattern.compile("([a-zA-Z]{1,3})");
        Matcher pozMatcher = pozP.matcher(poz);

        System.out.println(data);

        if(numeMatcher.find() || prenumeMatcher.find() || taraMatcher.find() || !pozMatcher.matches())
            throw new BadFormatException("Un nume are caractere ilegale!");
        else if(!dataMatcher.matches())
            throw new BadFormatException("Data introdusa incorect! Formatul este DD.MM.YYYY");

        return new Jucator(nume, prenume, tara, echipa, data, poz);
    }

    @Override
    public Map<String, String[]> getFKs() {
        return fk;
    }

}
