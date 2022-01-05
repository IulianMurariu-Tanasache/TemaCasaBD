package ProiectBD.SQL;

import ProiectBD.DataClasses.AdminClasses.Stadion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Implementarea clasei abstracte SQLConnection pentru tabela STADIOANE
public class StadionSQL extends SQLConnection<Stadion>{

    @Override
    public String getColumns()
    {
        return "id_echipa,nume,locatie,locuri_totale,pret_bilet";
    }

    @Override
    public void Insert(Stadion last) throws SQLException {
        //insert ultima
        pstm = con.prepareStatement("INSERT INTO STADIOANE(" + getColumns() + ") VALUES ((SELECT id_echipa FROM ECHIPE WHERE nume=?), ?, ?, ?, ?)");
        pstm.setString(1, last.getId_echipa());
        pstm.setString(2, last.getNume());
        pstm.setString(3, last.getLocatie());
        pstm.setInt(4, last.getLocuri_totale());
        pstm.setObject(5,last.getPret_bilet(), java.sql.Types.INTEGER);

        pstm.executeUpdate();
        //commit la final cand dau close?
        //con.commit();
    }

    @Override
    public void Update(Stadion toUpdate, Stadion newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE STADIOANE SET id_echipa=(SELECT id_echipa FROM ECHIPE WHERE nume=?), nume=?, locatie=?, locuri_totale=?, pret_bilet=? WHERE id_echipa=?");

        //pstm.setString(1, newItem.getId_echipa());
        pstm.setString(1, newItem.getNume());
        pstm.setString(2, newItem.getLocatie());
        pstm.setInt(3, newItem.getLocuri_totale());
        pstm.setInt(4,newItem.getPret_bilet());
        pstm.setString(5, toUpdate.getId_echipa());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Stadion toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM STADIOANE WHERE id_echipa=?");

        pstm.setString(1,toDelete.getId_echipa());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Stadion> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Stadion> dataStadion = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select " + getColumns() + " from STADIOANE");
        while(rs.next())
        {
           dataStadion.add(new Stadion(rs.getString(1), rs.getString(2),  rs.getString(3), rs.getInt(4), (BigDecimal) rs.getObject(5)));
        }
        return dataStadion;
    }
}
