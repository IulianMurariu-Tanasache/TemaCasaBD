package ProiectBD.SQL;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

//Clasa abstracta care se ocupa cu crearea unei conexiuni SQL, si comenzi specifice SQL pentru fiecare tabela
public abstract class SQLConnection<T> {

    protected static Connection con;
    protected static Statement stm;
    protected static PreparedStatement pstm;

    public static void makeSQLConnection() throws ClassNotFoundException, SQLException {
        //step1 load the driver class
        Class.forName("oracle.jdbc.driver.OracleDriver");

        //step2 create  the connection object
        con = DriverManager.getConnection("jdbc:oracle:thin:@bd-dc.cs.tuiasi.ro:1539:orcl","bd161","parola123");
        stm = con.createStatement();

        System.out.println("Connected!");
    }

    public static void closeSQLConenction() throws SQLException {
        con.close();
    }

    public abstract String getColumns();
    public abstract void Insert(T last) throws SQLException;
    public abstract void Update(T toUpdate, T newItem) throws SQLException;
    public abstract void Delete(T toDelete) throws SQLException;

    public ArrayList<String> getFKs(String col, String table) throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT " + col + " FROM " + table);
        ArrayList<String> rez = new ArrayList<>();

        while(rs.next())
        {
            rez.add(rs.getString(1));
        }
        return rez;
    }

    public abstract ObservableList<T> Select() throws SQLException;

    public static Connection getCon() {
        return con;
    }
}
