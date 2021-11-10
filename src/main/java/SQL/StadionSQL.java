package SQL;

import DataClasses.Stadion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Implementarea clasei abstracte SQLConnection pentru tabela STADIOANE
public class StadionSQL extends SQLConnection<Stadion>{

    @Override
    public String getColumns()
    {
        return "nume,locatie,locuri_totale,data,meci";
    }

    @Override
    public void Insert(Stadion last) throws SQLException {
        //insert ultima
        pstm = con.prepareStatement("INSERT INTO STADIOANE(" + getColumns() + ") VALUES (?, ?, ?, ?, ?)");
        pstm.setString(1, last.getNume());
        pstm.setString(2, last.getLocatie());
        pstm.setInt(3, last.getLocuriTotale());
        pstm.setDate(4, new Date(200,10, 1));
        pstm.setString(5, last.getMeci());

        pstm.executeUpdate();
        //commit la final cand dau close?
        //con.commit();
    }

    @Override
    public void Update(Stadion toUpdate, Stadion newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE STADIOANE SET nume=?, locatie=?, locuri_totale=?, data=?, meci=? WHERE nume=?");

        pstm.setString(1, newItem.getNume());
        pstm.setString(2, newItem.getLocatie());
        pstm.setInt(3, newItem.getLocuriTotale());
        pstm.setDate(4, Date.valueOf(newItem.getData()));
        pstm.setString(5, newItem.getMeci());
        pstm.setString(6, toUpdate.getNume());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Stadion toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM STADIOANE WHERE nume=?");

        pstm.setString(1,toDelete.getNume());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Stadion> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Stadion> dataStadion = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select " + getColumns() + " from STADIOANE");
        while(rs.next())
        {
           dataStadion.add(new Stadion(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDate(4), rs.getString(5)));
        }
        return dataStadion;
    }
}
