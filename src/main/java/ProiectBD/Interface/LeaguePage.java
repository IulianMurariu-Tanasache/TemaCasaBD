package ProiectBD.Interface;

import ProiectBD.DataClasses.User.Team;
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

public class LeaguePage {

    private TableView<Team> _table;

    public LeaguePage(TableView table){
        _table = table;
        try {
            int i = 0;
            for(Field f : Class.forName("ProiectBD.DataClasses.User.Team").getDeclaredFields()) {
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

    public ObservableList<Team> Select() throws SQLException {
        ObservableList<Team> dataTeam = FXCollections.observableArrayList();

        String sqlStatement="SELECT nume, tara, wins*3+draws \"points\", wins, draws, defeats, games, CASE WHEN games=0 THEN 0 ELSE goals/games END goalavg\n" +
                "FROM ECHIPE e,\n" +
                "    (SELECT e.id_echipa id, NVL(SUM(\n" +
                "        CASE WHEN scor_e1 > scor_e2 AND e.id_echipa=m.echipa1_id THEN 1\n" +
                "             WHEN scor_e1 < scor_e2 AND e.id_echipa=m.echipa2_id THEN 1\n" +
                "             ELSE 0 END\n" +
                "    ),0) wins, NVL(SUM(\n" +
                "        CASE WHEN scor_e1=scor_e2 THEN 1 ELSE 0 END\n" +
                "    ),0) draws, NVL(SUM(\n" +
                "        CASE WHEN scor_e1 < scor_e2 AND e.id_echipa=m.echipa1_id THEN 1\n" +
                "             WHEN scor_e1 > scor_e2 AND e.id_echipa=m.echipa2_id THEN 1\n" +
                "             ELSE 0 END\n" +
                "    ),0) defeats\n" +
                "    FROM ECHIPE e, MECIURI m\n" +
                "    WHERE ((e.id_echipa=m.echipa1_id) OR (e.id_echipa=m.echipa2_id)) AND m.data_meci <= SYSDATE\n" +
                "    GROUP BY e.id_echipa) t1,\n" +
                "    (SELECT e.id_echipa id, NVL(SUM(goluri),0) goals, NVL(COUNT(DISTINCT ms.id_meci),0) games\n" +
                "    FROM ECHIPE e, JUCATORI j, MECI_STAT ms\n" +
                "    WHERE e.id_echipa=j.echipa_id AND\n" +
                "          j.id_jucator=ms.jucator(+)\n" +
                "    GROUP BY e.id_echipa) t2\n" +
                "WHERE e.id_echipa = t1.id AND\n" +
                "      e.id_echipa = t2.id\n" +
                "ORDER BY wins*3+draws DESC, goalavg DESC";

        PreparedStatement pst = SQLConnection.getCon().prepareStatement(sqlStatement);
        ResultSet rs = pst.executeQuery();

        int i = 1;
        while(rs.next())
        {
            dataTeam.add(new Team(i++,rs.getString(1),rs.getString(2),rs.getInt(3), rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7), rs.getInt(8)));
        }
        return dataTeam;
    }
}
