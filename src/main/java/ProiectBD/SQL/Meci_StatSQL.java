package ProiectBD.SQL;

import ProiectBD.DataClasses.AdminClasses.Meci_Stat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;

//Implementarea clasei abstracte SQLConnection pentru tabela MECI_STAT
public class Meci_StatSQL extends SQLConnection<Meci_Stat>{

    @Override
    public String getColumns()
    {
        return "id_meci,jucator,goluri,asist,cart,moment";
    }

    @Override
    public void Insert(Meci_Stat last) throws SQLException {
        //insert ultima
        String sqlStatement =
                "INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat) " +
                "SELECT ?,?,?,?,?, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),? " +
                "FROM( SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(?/60)) || ':' || TO_CHAR(?) || ':' || ? t " +
                "      FROM MECIURI WHERE MECIURI.id_meci=? )";

        pstm = con.prepareStatement(sqlStatement);
        pstm.setString(1, last.getId_meci());
        pstm.setString(2, last.getId_jucator());
        pstm.setObject(3, last.getGoluri(), java.sql.Types.INTEGER);
        pstm.setObject(4, last.getAsist(), java.sql.Types.INTEGER);
        pstm.setObject(5,last.getCart(), java.sql.Types.INTEGER);
        pstm.setString(6,last.getId_stat());
        pstm.setInt(7,Integer.parseInt(last.getMoment().substring(0,last.getMoment().indexOf(':'))));
        pstm.setInt(8,Integer.parseInt(last.getMoment().substring(0,last.getMoment().indexOf(':'))) % 60);
        pstm.setString(9,last.getMoment().substring(last.getMoment().indexOf(':') + 1));
        pstm.setString(10,last.getId_meci());

        System.out.println(pstm.executeUpdate());
    }

    @Override
    public void Update(Meci_Stat toUpdate, Meci_Stat newItem) throws SQLException {
        String sqlStatement =
                "UPDATE MECI_STAT SET id_meci=?,jucator=?,goluri=?,asist=?,cart=?,moment=( " +
                "   SELECT to_date(d || t, 'DD.MM.YYYY HH24:MI:SS') " +
                "   FROM( SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || ? t" +
                "         FROM MECIURI WHERE MECIURI.id_meci=? )),id_stat=?" +
                "WHERE id_stat=?";

        pstm = con.prepareStatement(sqlStatement);

        pstm.setString(1, newItem.getId_meci());
        pstm.setString(2, newItem.getId_jucator());
        pstm.setInt(3, newItem.getGoluri());
        pstm.setInt(4, newItem.getAsist());
        pstm.setInt(5,newItem.getCart());
        pstm.setString(6, newItem.getMoment());
        pstm.setString(7, newItem.getId_meci());
        pstm.setString(8, newItem.getId_stat());
        pstm.setString(9, toUpdate.getId_stat());

        pstm.executeUpdate();
    }

    @Override
    public void Delete(Meci_Stat toDelete) throws SQLException {
        pstm = con.prepareStatement("DELETE FROM MECI_STAT WHERE id_stat=?");

        pstm.setString(1, toDelete.getId_stat());

        pstm.executeUpdate();
    }

    @Override
    public ObservableList<Meci_Stat> Select() throws SQLException {
        Statement stm = con.createStatement();
        ObservableList<Meci_Stat> dataMeci_Stat = FXCollections.observableArrayList();

        ResultSet rs = stm.executeQuery("select id_meci,jucator,goluri,asist,cart,TO_CHAR(moment,'HH24:MI:SS') from MECI_STAT");
        while(rs.next())
        {
            dataMeci_Stat.add(new Meci_Stat(rs.getString(1),rs.getString(2),  (BigDecimal) rs.getObject(3), (BigDecimal) rs.getObject(4), (BigDecimal) rs.getObject(5),rs.getString(6)));
        }
        return dataMeci_Stat;
    }

}
