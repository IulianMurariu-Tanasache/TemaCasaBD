package DataClasses.Adapter;

import DataClasses.Echipa;

//Implementarea interfetei Adapter pentru tabela Echipe
public class EchipaAdapter implements Adapter<Echipa>{

    @Override
    public Echipa createObject(String[] parameters) {
        String nume = parameters[0];
        String tara = parameters[1];
        try {
            int puncte = Integer.parseInt(parameters[2]);
            int victorii = Integer.parseInt(parameters[3]);
            int infrangeri = Integer.parseInt(parameters[4]);
            int egale = Integer.parseInt(parameters[5]);
            int gol = Integer.parseInt(parameters[6]);
            return new Echipa(nume, tara, puncte, victorii, infrangeri, egale, gol);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
