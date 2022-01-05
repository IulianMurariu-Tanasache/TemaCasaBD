package ProiectBD.SQL;

import ProiectBD.DataClasses.AdminClasses.Echipa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

//Implementarea clasei abstracte SQLConnection pentru tabela ECHIPE
public class EchipaSQL extends SQLConnection<Echipa>{

    @Override
    public String getColumns()
    {
        return "id_echipa,nume,tara";
    }

    @Override
    public void Insert(Echipa last) throws SQLException {
        //insert ultima
        pstm = con.prepareStatement("INSERT INTO ECHIPE(" + getColumns() + ") VALUES (?, ?, ?)");
        pstm.setString(1, last.getId_echipa());
        pstm.setString(2, last.getNume());
        pstm.setString(3, last.getTara());

        pstm.executeUpdate();
    }

    @Override
    public void Update(Echipa toUpdate, Echipa newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE ECHIPE SET nume=?, tara=? WHERE id_echipa=?");

        //pstm.setString(1, newItem.getId_echipa());
        pstm.setString(1, newItem.getNume());
        pstm.setString(2, newItem.getTara());
        pstm.setString(3, toUpdate.getId_echipa());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Echipa toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM ECHIPE WHERE id_echipa=?");

        pstm.setString(1,toDelete.getId_echipa());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Echipa> Select() throws SQLException {

        ObservableList<Echipa> dataEchipe = FXCollections.observableArrayList();
        ResultSet rs = stm.executeQuery("SELECT " + getColumns() + " FROM ECHIPE");

        while(rs.next())
        {
            dataEchipe.add(new Echipa(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return dataEchipe;
    }


}
