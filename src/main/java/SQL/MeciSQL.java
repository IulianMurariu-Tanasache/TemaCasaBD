package SQL;

import DataClasses.Meci;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Implementarea clasei abstracte SQLConnection pentru tabela MECIURI
public class MeciSQL extends SQLConnection<Meci>{

    @Override
    public String getColumns()
    {
        return "id_meci,echipa1,echipa2,data,rezultat,stadion,locuri_libere";
    }

    @Override
    public void Insert(Meci last) throws SQLException {
        //insert ultima
        pstm = con.prepareStatement("INSERT INTO MECIURI(id_meci," + getColumns() + ") VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstm.setInt(1, 0);
        pstm.setString(2, last.getEchipa1());
        pstm.setString(3, last.getEchipa2());
        pstm.setDate(4, Date.valueOf(last.getData())); // sa revin sa fac DATE
        pstm.setString(5, last.getRezultat());
        pstm.setString(6, last.getStadion());
        pstm.setInt(7, last.getLocuriLibere());

        pstm.executeUpdate();
    }

    @Override
    public void Update(Meci toUpdate, Meci newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE MECIURI SET id_meci=?, echipa1=?, echipa2=?, data=?, rezultat=?, stadion=?, locuri_libere=? WHERE id_meci=?");

        pstm.setInt(1, newItem.getNr());
        pstm.setString(2, newItem.getEchipa1());
        pstm.setString(3, newItem.getEchipa2());
        pstm.setDate(4, Date.valueOf(newItem.getData()));
        pstm.setString(5, newItem.getRezultat());
        pstm.setString(6, newItem.getStadion());
        pstm.setInt(7, newItem.getLocuriLibere());
        pstm.setInt(8, toUpdate.getNr());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Meci toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM MECIURI WHERE id_meci=?");

        pstm.setInt(1,toDelete.getNr());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Meci> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Meci> dataMeci = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select " + getColumns() + " from MECIURI");
        while(rs.next())
        {
            dataMeci.add(new Meci(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4),rs.getString(5), rs.getString(6), rs.getInt(7)));
        }
        return dataMeci;
    }
}
