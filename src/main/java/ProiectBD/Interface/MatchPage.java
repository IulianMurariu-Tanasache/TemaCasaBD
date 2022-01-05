package ProiectBD.Interface;

import ProiectBD.DataClasses.User.MeciExtra;
import ProiectBD.DataClasses.User.Stadion;
import ProiectBD.SQL.SQLConnection;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchPage {

    class AlignClass{
        private boolean left = true;
        private String txt;
        AlignClass(String t, boolean l){
            left = l;
            txt = t;
        }

        public String getTxt() {
            return txt;
        }

        public boolean isLeft() {
            return left;
        }
    }

    private Text matchText;
    private Text team1Text;
    private Text team2Text;
    private Text stadionMatchText;
    private Text dateMatchText;
    private ListView timelineView;
    private ListView team1View;
    private ListView team2View;

    public MatchPage(Text matchText, Text team1Text, Text team2Text, ListView timelineView, ListView team1View, ListView team2Veiw, Text stadionMatchText, Text dateMatchText, Callable viewStadion) {
        this.matchText = matchText;
        this.team1Text = team1Text;
        this.team2Text = team2Text;
        this.timelineView = timelineView;
        this.team1View = team1View;
        this.team2View = team2Veiw;
        this.stadionMatchText = stadionMatchText;
        this.dateMatchText = dateMatchText;

        timelineView.setCellFactory(param -> new ListCell<AlignClass>() {
            @Override
            protected void updateItem(AlignClass item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getTxt() == null) {
                    setText(null);
                } else {
                    setText(item.getTxt());
                    if(!item.isLeft())
                        this.setStyle("-fx-alignment: center-right");
                }
            }
        });

        stadionMatchText.setOnMouseClicked(e -> {
            try {
                viewStadion.viewStadion(new Stadion(stadionMatchText.getText()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void changeTabMatches(MeciExtra selected){
        if(selected == null)
            return;
        String[] parts = selected.getDisplay().split("\n");
        matchText.setText(parts[0] + "  ---  " + parts[1]);
        team1Text.setText(selected.getEchipa1_name());
        team2Text.setText(selected.getEchipa2_name());
        try {
            timelineView.getItems().clear();
            team1View.getItems().clear();
            team2View.getItems().clear();
            SetTimeline(selected);
            SetStats(team1View,selected.getId_meci(), selected.getId1());
            SetStats(team2View,selected.getId_meci(), selected.getId2());
            SetStadion(selected);
            SetDate(selected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SetTimeline(MeciExtra selected) throws SQLException {

        String sqlStatement = "SELECT TO_NUMBER(TO_CHAR(ms.moment,'HH24')) * 60 + TO_NUMBER(TO_CHAR(ms.moment,'MI')), " +
                                     "TO_NUMBER(TO_CHAR(ms.moment,'SS')), ms.goluri, ms.cart, j.nume, j.echipa_id " +
                               "FROM MECI_STAT ms, JUCATORI j " +
                               "WHERE j.id_jucator=ms.jucator AND " +
                                     "(ms.goluri > 0 OR ms.cart > 0) AND " +
                                     "ms.id_meci=? " +
                                "ORDER BY ms.moment";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, selected.getId_meci());
        ResultSet rs = pstm.executeQuery();

        while(rs.next())
        {
            String moment = rs.getInt(1) + "'" + rs.getInt(2) + "\"";
            String gol = "", ros = "", galben = "";
            String nume = rs.getString(5);
            if(rs.getInt(3) > 0)
                gol = "Gol ";
            else {
                if(rs.getInt(4) == 1)
                    galben = "Galben ";
                else
                    ros = "Rosu ";
            }
            String echipa = rs.getString(6);
            String s = "";
            if(selected.getId2().equals(echipa))
                s += nume + " " + ros + galben + gol + " - " + moment;
            else
                s += moment + " - " + ros + galben + gol + " " + nume;
            timelineView.getItems().add(new AlignClass(s,!selected.getId2().equals(echipa)));
        }
    }

    private void SetStats(ListView list, String meci, String echipa) throws SQLException {
        int gol=0;
        int rosii=0;
        int galbene=0;

        String goluri = "SELECT SUM(ms.goluri) " +
                        "FROM MECI_STAT ms, JUCATORI j " +
                        "WHERE j.id_jucator=ms.jucator AND " +
                        "      ms.id_meci=? AND " +
                        "      j.echipa_id=?";

        String galben = "SELECT NVL(SUM(galben),0) " +
                        "FROM (SELECT cart galben FROM MECI_STAT ms, JUCATORI j " +
                        "WHERE cart=1 AND j.id_jucator=ms.jucator AND ms.id_meci=? AND j.echipa_id=?)";

        String rosu = "SELECT NVL(SUM(rosu),0) " +
                "FROM (SELECT cart rosu FROM MECI_STAT ms, JUCATORI j " +
                "WHERE cart=2 AND j.id_jucator=ms.jucator AND ms.id_meci=? AND j.echipa_id=?)";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(goluri);
        pstm.setString(1, meci);
        pstm.setString(2, echipa);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            gol = rs.getInt(1);
        }

        pstm = SQLConnection.getCon().prepareStatement(galben);
        pstm.setString(1, meci);
        pstm.setString(2, echipa);
        rs = pstm.executeQuery();
        while(rs.next()) {
            galbene = rs.getInt(1);
        }

        pstm = SQLConnection.getCon().prepareStatement(rosu);
        pstm.setString(1, meci);
        pstm.setString(2, echipa);
        rs = pstm.executeQuery();
        while(rs.next()) {
            rosii = rs.getInt(1);
        }

        list.getItems().addAll("Goluri: " + gol, "Cartonase Galbene: " + galbene, "Cartonase Rosii: " + rosii / 2);
    }

    private void SetStadion(MeciExtra selected) throws SQLException {
        String sqlStatement = "SELECT st.nume FROM STADIOANE st WHERE id_echipa=?";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, selected.getAcasa_name());
        ResultSet rs = pstm.executeQuery();

        while(rs.next())
        {
            stadionMatchText.setText(rs.getString(1));
        }
    }

    private void SetDate(MeciExtra selected) throws SQLException {
        String sqlStatement = "SELECT TO_CHAR(data_meci,'DD.MM.YYYY') FROM MECIURI WHERE id_meci=?";

        PreparedStatement pstm = SQLConnection.getCon().prepareStatement(sqlStatement);
        pstm.setString(1, selected.getId_meci());
        ResultSet rs = pstm.executeQuery();

        while(rs.next())
        {
            dateMatchText.setText("Played on: " + rs.getString(1));
        }
    }

}
