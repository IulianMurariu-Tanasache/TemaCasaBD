package SQL;

import DataClasses.Echipa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

//Implementarea clasei abstracte SQLConnection pentru tabela ECHIPE
public class EchipaSQL extends SQLConnection<Echipa>{

    @Override
    public String getColumns()
    {
        return "nume,tara,puncte,victorii,infrangeri,egaluri,golavg";
    }

    @Override
    public void Insert(Echipa last) throws SQLException {
        //insert ultima
        pstm = con.prepareStatement("INSERT INTO ECHIPE(" + getColumns() + ") VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstm.setString(1, last.getNume());
        pstm.setString(2, last.getTara());
        pstm.setInt(3, last.getPuncte());
        pstm.setInt(4, last.getVictorii());
        pstm.setInt(5, last.getInfrangeri());
        pstm.setInt(6, last.getEgaluri());
        pstm.setInt(7, last.getGolavg());

        pstm.executeUpdate();
    }

    @Override
    public void Update(Echipa toUpdate, Echipa newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE ECHIPE SET nume=?, tara=?, puncte=?, victorii=?, infrangeri=?, egaluri=?, golavg=? WHERE nume=?");

        pstm.setString(1, newItem.getNume());
        pstm.setString(2, newItem.getTara());
        pstm.setInt(3, newItem.getPuncte());
        pstm.setInt(4, newItem.getVictorii());
        pstm.setInt(5, newItem.getInfrangeri());
        pstm.setInt(6, newItem.getEgaluri());
        pstm.setInt(7, newItem.getGolavg());
        pstm.setString(8, toUpdate.getNume());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Echipa toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM ECHIPE WHERE nume=?");

        pstm.setString(1,toDelete.getNume());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Echipa> Select() throws SQLException {

        ObservableList<Echipa> dataEchipe = FXCollections.observableArrayList();
        ResultSet rs = stm.executeQuery("SELECT " + getColumns() + " FROM ECHIPE");

        while(rs.next())
        {
            dataEchipe.add(new Echipa(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
        }
        return dataEchipe;
    }


}
