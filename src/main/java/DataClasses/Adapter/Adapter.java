package DataClasses.Adapter;

//Interfata pentru a adapta creerea unei noi inregistrari specifica fiecarei tabele
public interface Adapter<T> {
    T createObject(String[] parameters);
}
