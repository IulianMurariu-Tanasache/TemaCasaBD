package ProiectBD.SQL;

import ProiectBD.DataClasses.AdminClasses.Meci;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Implementarea clasei abstracte SQLConnection pentru tabela MECIURI
public class MeciSQL extends SQLConnection<Meci>{

    @Override
    public String getColumns()
    {
        return "id_meci,echipa1_id,echipa2_id,data_meci,scor_e1,scor_e2,acasa";
    }

    @Override
    public void Insert(Meci last) throws SQLException {
        //insert ultima
        pstm = con.prepareStatement("INSERT INTO MECIURI(" + getColumns() + ") VALUES (?, (SELECT id_echipa FROM ECHIPE WHERE nume=?), (SELECT id_echipa FROM ECHIPE WHERE nume=?), TO_DATE(?,'DD.MM.YYYY'), ?, ?, (SELECT id_echipa FROM ECHIPE WHERE nume=?))");
        pstm.setString(1, last.getId_meci());
        pstm.setString(2, last.getEchipa1_id());
        pstm.setString(3, last.getEchipa2_id());
        pstm.setString(4, last.getData_meci());
        pstm.setObject(5, last.getScor_e1(), java.sql.Types.INTEGER);
        pstm.setObject(6, last.getScor_e2(), java.sql.Types.INTEGER);
        pstm.setString(7, last.getAcasa());

        pstm.executeUpdate();
    }

    @Override
    public void Update(Meci toUpdate, Meci newItem) throws SQLException {
        pstm = con.prepareStatement("UPDATE MECIURI echipa1_id=(SELECT id_echipa FROM ECHIPE WHERE nume=?), echipa2_id=(SELECT id_echipa FROM ECHIPE WHERE nume=?), data_meci=TO_DATE(?,'DD.MM.YYYY'), scor_e1=?, scor_e2=?, acasa=(SELECT id_echipa FROM ECHIPE WHERE nume=?) WHERE id_meci=?");

        //pstm.setString(1, newItem.getId_meci());
        pstm.setString(1, newItem.getEchipa1_id());
        pstm.setString(2, newItem.getEchipa2_id());
        pstm.setDate(3, Date.valueOf(newItem.getData_meci()));
        pstm.setObject(4, newItem.getScor_e1(), java.sql.Types.INTEGER);
        pstm.setObject(5, newItem.getScor_e2(), java.sql.Types.INTEGER);
        pstm.setString(6, newItem.getAcasa());
        pstm.setString(7, toUpdate.getId_meci());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Meci toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM MECIURI WHERE id_meci=?");

        pstm.setString(1,toDelete.getId_meci());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Meci> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Meci> dataMeci = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select id_meci,echipa1_id,echipa2_id,TO_CHAR(data_meci,'DD.MM.YYYY'),scor_e1,scor_e2,acasa from MECIURI");
        while(rs.next())
        {
            dataMeci.add(new Meci(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), (BigDecimal) rs.getObject(5), (BigDecimal) rs.getObject(6), rs.getString(7)));
        }
        return dataMeci;
    }
}
