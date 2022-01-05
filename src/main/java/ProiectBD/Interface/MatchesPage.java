package ProiectBD.Interface;

import ProiectBD.DataClasses.User.MeciExtra;
import ProiectBD.SQL.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchesPage{
    private DatePicker startCalendar;
    private DatePicker endCalendar;
    private ListView matchesList;
    private ListView datesList;

    public MatchesPage(DatePicker sc, DatePicker ec, ListView l, ListView d){
        startCalendar = sc;
        endCalendar = ec;
        matchesList = l;
        datesList = d;

        startCalendar.valueProperty().addListener((ov, oldval, newval) -> {
            try {
                ObservableList<MeciExtra> dataMeci = Select();
                matchesList.setItems(dataMeci);
                datesList.setItems(dataMeci);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        endCalendar.valueProperty().addListener((ov, oldval, newval) -> {
            try {
                ObservableList<MeciExtra> dataMeci = Select();
                matchesList.setItems(dataMeci);
                datesList.setItems(dataMeci);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        matchesList.setCellFactory(param -> new ListCell<MeciExtra>() {
            @Override
            protected void updateItem(MeciExtra item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getDisplay() == null) {
                    setText(null);
                } else {
                    setText(item.getDisplay());
                }
            }
        });

        datesList.setCellFactory(param -> new ListCell<MeciExtra>() {
            @Override
            protected void updateItem(MeciExtra item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getData() == null) {
                    setText(null);
                } else {
                    setText(item.getData());
                }
            }
        });

        matchesList.getSelectionModel().selectedItemProperty().addListener((ov, oldval, newval) -> {
           datesList.getSelectionModel().select(newval);

        });

        datesList.getSelectionModel().selectedItemProperty().addListener((ov, oldval, newval) -> {
            matchesList.getSelectionModel().select(newval);

        });

        try {
            ObservableList<MeciExtra> dataMeci = Select();
            matchesList.setItems(dataMeci);
            datesList.setItems(dataMeci);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeTabMatches(){
    }

    public ObservableList<MeciExtra> Select() throws SQLException {
        Statement stm = SQLConnection.getCon().createStatement();
        ObservableList<MeciExtra> dataMeci = FXCollections.observableArrayList();

        String minDate = startCalendar.getValue() == null ? "0001-01-01" : startCalendar.getValue().toString();
        String maxDate = endCalendar.getValue() == null ? "9999-12-31" : endCalendar.getValue().toString();

        String sqlStatement = "SELECT e1.nume, m.scor_e1, m.scor_e2, e2.nume, TO_CHAR(data_meci,'DD.MM.YYYY'), m.acasa, m.echipa1_id, m.echipa2_id, m.id_meci " +
                              "FROM MECIURI m, ECHIPE e1, ECHIPE e2 " +
                              "WHERE m.echipa1_id=e1.id_echipa AND " +
                                    "m.echipa2_id=e2.id_echipa AND " +
                                    "m.data_meci BETWEEN TO_DATE('" + minDate +"','YYYY-MM-DD') AND TO_DATE('" + maxDate + "','YYYY-MM-DD')" +
                              "ORDER BY data_meci";


        ResultSet rs = stm.executeQuery(sqlStatement);

        while(rs.next())
        {
            dataMeci.add(new MeciExtra(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
        }
        return dataMeci;
    }
}
