package SQL;

import DataClasses.Jucator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Implementarea clasei abstracte SQLConnection pentru tabela JUCATORI
public class JucatorSQL extends SQLConnection<Jucator>{

    @Override
    public String getColumns()
    {
        return "nume,prenume,tara,echipa,goluri,cart_galben,cart_rosu";
    }

    @Override
    public void Insert(Jucator last) throws SQLException {

        pstm = con.prepareStatement("INSERT INTO JUCATORI(" + getColumns() + ") VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstm.setString(1, last.getNume());
        pstm.setString(2, last.getPrenume());
        pstm.setString(3, last.getTara());
        pstm.setString(4, last.getEchipa());
        pstm.setInt(5, last.getGoluri());
        pstm.setInt(6, last.getGalben());
        pstm.setInt(7, last.getRosu());

        pstm.executeUpdate();
        //commit la final cand dau close?
        //con.commit();
    }

    @Override
    public void Update(Jucator toUpdate, Jucator newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE JUCATORI SET nume=?, prenume=?, tara=?, echipa=?, goluri=?,cart_galben=?, cart_rosu=? WHERE nume=? AND prenume=?");

        pstm.setString(1, newItem.getNume());
        pstm.setString(2, newItem.getPrenume());
        pstm.setString(3, newItem.getTara());
        pstm.setString(4, newItem.getEchipa());
        pstm.setInt(5, newItem.getGoluri());
        pstm.setInt(6, newItem.getGalben());
        pstm.setInt(7, newItem.getRosu());
        pstm.setString(8, toUpdate.getNume());
        pstm.setString(9, toUpdate.getPrenume());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Jucator toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM JUCATORI WHERE nume=? AND prenume=?");

        pstm.setString(1,toDelete.getNume());
        pstm.setString(2,toDelete.getPrenume());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Jucator> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Jucator> dataJucatori = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select " + getColumns() + " from JUCATORI");
        while(rs.next())
        {
            dataJucatori.add(new Jucator(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
        }
        return dataJucatori;
    }
}
