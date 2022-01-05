package ProiectBD.DataClasses.User;

import ProiectBD.SQL.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stadion {

    private String name;
    private String team;
    private String loc;
    private int capacity;
    private int price;

    public Stadion(String name) throws SQLException {
        this.name = name;

        String sqlStatement = "SELECT e.nume, s.locatie, e.tara, s.pret_bilet, s.locuri_totale\n" +
                "FROM STADIOANE s, ECHIPE e\n" +
                "WHERE s.id_echipa = e.id_echipa AND\n" +
                "      s.nume = ?";

        PreparedStatement pst = SQLConnection.getCon().prepareStatement(sqlStatement);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();

        while(rs.next())
        {
            this.team = rs.getString(1);
            this.loc = rs.getString(2) + ", " +  rs.getString(3);
            this.capacity =  rs.getInt(5);
            this.price = rs.getInt(4);
        }
    }

    public Stadion(String name, boolean b) throws SQLException {
        this.team = name;

        String sqlStatement = "SELECT s.nume, s.locatie, e.tara, s.pret_bilet, s.locuri_totale\n" +
                "FROM STADIOANE s, ECHIPE e\n" +
                "WHERE s.id_echipa = e.id_echipa AND\n" +
                "      e.nume = ?";

        PreparedStatement pst = SQLConnection.getCon().prepareStatement(sqlStatement);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();

        while(rs.next())
        {
            this.name = rs.getString(1);
            this.loc = rs.getString(2) + ", " +  rs.getString(3);
            this.capacity =  rs.getInt(5);
            this.price = rs.getInt(4);
        }
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getLoc() {
        return loc;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }
}
