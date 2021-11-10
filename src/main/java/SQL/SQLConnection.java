package SQL;

import javafx.collections.ObservableList;

import java.sql.*;

//Clasa abstracta care se ocupa cu crearea unei conexiuni SQL, si comenzi specifice SQL pentru fiecare tabela
public abstract class SQLConnection<T> {

    public static Connection con;
    public static Statement stm;
    public static PreparedStatement pstm;

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

    public abstract ObservableList<T> Select() throws SQLException;

}
