package DataClasses.Adapter;

import DataClasses.Meci;

//Implementarea interfetei Adapter pentru tabela Meciuri
public class MeciAdapter implements Adapter<Meci>{

    @Override
    public Meci createObject(String[] parameters) {
        int nr = Integer.parseInt(parameters[0]);
        String echipa1 = parameters[1];
        String echipa2 = parameters[2];
        String data = parameters[3];
        String rez = parameters[4];
        String stadion = parameters[5];
        int loc = Integer.parseInt(parameters[6]);
        return new Meci(nr, echipa1, echipa2, data, rez, stadion, loc);
    }

}
