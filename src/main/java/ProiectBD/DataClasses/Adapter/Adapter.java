package ProiectBD.DataClasses.Adapter;

import ProiectBD.SQL.BadFormatException;

import java.util.Map;
import java.util.regex.Pattern;

//Interfata pentru a adapta creerea unei noi inregistrari specifica fiecarei tabele
public abstract class Adapter<T> {

    protected Pattern p = Pattern.compile("[^a-z\\-A-Z]+");
    protected Pattern momentP = Pattern.compile("(([0-5][0-9])\\:([0-5][0-9])\\:([0-5][0-9]))");
    protected Pattern dataP = Pattern.compile("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.([0-3][0-9]{3})");

    abstract public T createObject(String[] parameters) throws BadFormatException;
    abstract public Map<String,String[]> getFKs();
    protected Integer parseInt(String s) throws NumberFormatException
    {
        if(s.equals(""))
            return null;
        else
            return Integer.parseInt(s);
    }
}
