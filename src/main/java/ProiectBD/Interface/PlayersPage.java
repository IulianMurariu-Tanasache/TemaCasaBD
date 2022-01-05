package ProiectBD.Interface;

import ProiectBD.DataClasses.User.Player;
import ProiectBD.SQL.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayersPage {

    private TableView<Player> _table;

    public PlayersPage(TableView table){
        int i = 0;
        _table = table;
        try {
            for(Field f : Class.forName("ProiectBD.DataClasses.User.Player").getDeclaredFields()) {
                TableColumn c = _table.getColumns().get(i);
                c.setStyle("-fx-alignment: CENTER;");
                c.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                ++i;
            }
            _table.setItems(Select());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Player> Select() throws SQLException {
        ObservableList<Player> dataPlayer = FXCollections.observableArrayList();

        String sqlStatement="SELECT DISTINCT jo.prenume || ' ' || jo.nume full_name, eo.nume, jo.poz, jo.tara, TRUNC((SYSDATE - jo.data_nastere)/365), gol, g.asist, galbene, rosii\n" +
                            "FROM JUCATORI jo, ECHIPE eo, MECI_STAT mso, \n" +
                            "    (SELECT id_jucator jucator, NVL(SUM(goluri),0) gol, NVL(SUM(asist),0) asist FROM MECI_STAT m, JUCATORI j WHERE m.jucator(+)=j.id_jucator GROUP BY id_jucator) g,\n" +
                            "    (SELECT id_jucator jucator, ss galbene FROM\n" +
                            "        (SELECT id_jucator, NVL(s,0) ss FROM JUCATORI, " +
                            "           (SELECT jucator ju, NVL(SUM(cart),0) s FROM MECI_STAT WHERE cart=1 GROUP BY jucator) WHERE id_jucator = ju(+))\n" +
                            "    )cg,\n" +
                            "    (SELECT jucator, ss rosii FROM\n" +
                            "        (SELECT id_jucator jucator, NVL(s,0) ss FROM JUCATORI, " +
                            "           (SELECT jucator j, NVL(SUM(cart),0)/2 s FROM MECI_STAT WHERE cart=2 GROUP BY jucator) WHERE id_jucator = j(+))\n" +
                            "    )cr\n" +
                            "WHERE jo.id_jucator=mso.jucator(+) AND\n" +
                            "      eo.id_echipa=jo.echipa_id AND\n" +
                            "      g.jucator=jo.id_jucator AND\n" +
                            "      cg.jucator=jo.id_jucator AND\n" +
                            "      cr.jucator=jo.id_jucator\n" +
                            "ORDER BY full_name";

        PreparedStatement pst = SQLConnection.getCon().prepareStatement(sqlStatement);
        ResultSet rs = pst.executeQuery();

        while(rs.next())
        {
            dataPlayer.add(new Player(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9)));
        }
        return dataPlayer;
    }

}
