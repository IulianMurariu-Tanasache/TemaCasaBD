package ProiectBD.SQL;

import ProiectBD.DataClasses.AdminClasses.Jucator;
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
        return "id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz";
    }

    @Override
    public void Insert(Jucator last) throws SQLException {
        //generare id_jucator PK

        pstm = con.prepareStatement("INSERT INTO JUCATORI(" + getColumns() + ") VALUES (?, ?, ?, ?, (SELECT id_echipa FROM ECHIPE WHERE nume=?), TO_DATE(?,'DD.MM.YYYY'), ?)");
        pstm.setString(1, last.getId_jucator());
        pstm.setString(2, last.getNume());
        pstm.setString(3, last.getPrenume());
        pstm.setString(4, last.getTara());
        pstm.setString(5, last.getEchipa_id());
        pstm.setString(6, last.getData_nastere());
        pstm.setString(7, last.getPoz());

        pstm.executeUpdate();
        //commit la final cand dau close?
        //con.commit();
    }

    @Override
    public void Update(Jucator toUpdate, Jucator newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE JUCATORI SET nume=?, prenume=?, tara=?, echipa_id=(SELECT id_echipa FROM ECHIPE WHERE nume=?), data_nastere=TO_DATE(?,'DD.MM.YYYY'),poz=? WHERE id_jucator = ?");

        //pstm.setString(1, newItem.getId_jucator());
        pstm.setString(1, newItem.getNume());
        pstm.setString(2, newItem.getPrenume());
        pstm.setString(3, newItem.getTara());
        pstm.setString(4, newItem.getEchipa_id());
        pstm.setString(5, newItem.getData_nastere());
        pstm.setString(6, newItem.getPoz());
        pstm.setString(7, toUpdate.getId_jucator());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Jucator toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM JUCATORI WHERE id_jucator=?");

        pstm.setString(1,toDelete.getId_jucator());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Jucator> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Jucator> dataJucatori = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select id_jucator,nume,prenume,tara,echipa_id,TO_CHAR(data_nastere,'DD.MM.YYYY'),poz from JUCATORI");
        while(rs.next())
        {
            Jucator nou = new Jucator(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7));
            dataJucatori.add(nou);

        }
        return dataJucatori;
    }
}
