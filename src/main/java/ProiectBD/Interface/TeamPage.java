package ProiectBD.Interface;

import ProiectBD.DataClasses.User.JucatorCuPutineChestii;
import ProiectBD.DataClasses.User.Stadion;
import ProiectBD.SQL.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamPage {
    private ListView stats;
    private ListView matches;
    private Text textName;
    private Text stadionTeamText;
    private TableView jucatori;

    public TeamPage(String name, ListView stats, ListView matches, Text textName, TableView jucatori, Text stadionTeamText, Callable viewStadion) {
        this.stats = stats;
        this.matches = matches;
        this.textName = textName;
        this.jucatori = jucatori;
        this.stadionTeamText = stadionTeamText;
        this.textName.setText(name);
        try {
            stats.getItems().clear();
            matches.getItems().clear();
            SetStats(stats, name);
            setMatches(matches,name);
            SetStadion(name);
            stadionTeamText.setOnMouseClicked(e -> {
                try {
                    viewStadion.viewStadion(new Stadion(stadionTeamText.getText()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int i = 0;
            for(Field f : Class.forName("ProiectBD.DataClasses.User.JucatorCuPutineChestii").getDeclaredFields()) {
                TableColumn c = (TableColumn) jucatori.getColumns().get(i);
                c.setStyle("-fx-alignment: CENTER;");
                c.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                ++i;
            }
            jucatori.setItems(setJucatori(name));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void SetStats(ListView list, String echipa) throws SQLException {
        String sqlStatement="SELECT tara, wins*3+draws \"points\", wins, draws, defeats, goals, games, rosu, galben \n" +
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
                "          j.id_jucator=ms.jucator\n" +
                "    GROUP BY e.id_echipa) t2,\n" +
                "    (SELECT e.id_echipa id, NVL(SUM(\n" +
                "        CASE WHEN cart=2 THEN 1\n" +
                "             ELSE 0 END\n" +
                "    ),0) rosu, NVL(SUM(\n" +
                "        CASE WHEN cart=1 THEN 1 ELSE 0 END\n" +
                "    ),0) galben\n" +
                "    FROM ECHIPE e, MECI_STAT m, JUCATORI j\n" +
                "    WHERE ((e.id_echipa=j.echipa_id) AND (j.id_jucator=m.jucator)) AND m.moment <= SYSDATE\n" +
                "    GROUP BY e.id_echipa) t3\n" +
                "WHERE e.id_echipa = t1.id AND\n" +
                "     e.id_echipa = t2.id AND\n" +
                "     e.id_echipa=t3.id AND\n" +
                "     e.nume=?";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, echipa);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.getItems().addAll("Tara: " + rs.getString(1), "Puncte: " + rs.getInt(2), "Victorii: " + rs.getInt(3), "Egaluri: " + rs.getInt(4), "Infrangeri: " + + rs.getInt(5),"Goluri: " + + rs.getInt(6), "Jocuri jucate: " + + rs.getInt(7), "Gol Averaj: " + rs.getInt(6) * 1.0f / rs.getInt(7), "Cartonase Rosii: " + rs.getInt(8), "Cartonase Galbene: " + rs.getInt(9));
        }
    }

    private ObservableList<JucatorCuPutineChestii> setJucatori(String echipa) throws SQLException {
        String sqlStatement = "SELECT j.nume || ' ' || j.prenume , j.poz\n" +
                "FROM JUCATORI j, ECHIPE e\n" +
                "WHERE j.echipa_id=e.id_echipa AND\n" +
                "      e.nume=?\n" +
                "ORDER BY j.nume || ' ' || j.prenume";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, echipa);
        ResultSet rs = pstm.executeQuery();
        ObservableList<JucatorCuPutineChestii> data = FXCollections.observableArrayList();
        while(rs.next()) {
            data.add(new JucatorCuPutineChestii(rs.getString(1),rs.getString(2)));
        }
        return data;
    }

    private void setMatches(ListView list, String echipa) throws SQLException {
        String sqlStatement = "SELECT e1.nume, e2.nume, TO_CHAR(m.data_meci,'DD.MM.YYYY')\n" +
                "FROM MECIURI m, ECHIPE e, ECHIPE e1, ECHIPE e2\n" +
                "WHERE (e.id_echipa=m.echipa1_id OR e.id_echipa=m.echipa2_id) AND\n" +
                "      e1.id_echipa=m.echipa1_id AND\n" +
                "      e2.id_echipa=m.echipa2_id AND\n" +
                "      e.nume = ?\n" +
                "ORDER BY m.data_meci";
        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, echipa);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.getItems().addAll(rs.getString(1) + "  -  " + rs.getString(2) + " " + rs.getString(3));
        }
    }

    private void SetStadion(String echipa) throws SQLException {
        String sqlStatement = "SELECT st.nume FROM STADIOANE st, ECHIPE e WHERE e.id_echipa=st.id_echipa AND e.nume=?";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, echipa);
        ResultSet rs = pstm.executeQuery();

        while(rs.next())
        {
            stadionTeamText.setText(rs.getString(1));
        }
    }
}
