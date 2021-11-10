package DataClasses.Adapter;

import DataClasses.Jucator;

//Implementarea interfetei Adapter pentru tabela Jucatori
public class JucatorAdapter implements Adapter<Jucator>{

    @Override
    public Jucator createObject(String[] parameters) {
        String nume = parameters[0];
        String prenume = parameters[1];
        String tara = parameters[2];
        String echipa = parameters[3];
        int gol = Integer.parseInt(parameters[4]);
        int galben = Integer.parseInt(parameters[5]);
        int ros = Integer.parseInt(parameters[6]);
        return new Jucator(nume, prenume, tara, echipa, gol, galben, ros);
    }

}
