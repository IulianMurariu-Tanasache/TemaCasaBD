package ProiectBD.DataClasses.AdminClasses;

import ProiectBD.DataClasses.Adapter.Adapter;
import ProiectBD.SQL.SQLConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.SQLException;

//Clasa pentru incapsularea unei tabele din UI, lista ei de inregistrari si adaptorul specific
public class Table {
    private TableView table; //tabela din UI
    private ObservableList list; //lista elementelor dint tabela
    private Adapter adaptor; //Adaptor pentru creare de obiecte
    private SQLConnection sql; //Clasa care se ocupa de comenzi SQL
    private String name;

    public Table(TableView table, Adapter adapter, SQLConnection sql) {
        this.table = table;
        this.adaptor = adapter;
        this.sql = sql;
        //this.name = nume;

        try {
            list = sql.Select();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(list);
    }

    public SQLConnection getSql() {
        return sql;
    }

    public Adapter getAdaptor() {
        return adaptor;
    }

    public TableView getTable() {
        return table;
    }

    public ObservableList getList() {
        return list;
    }

    public void setList(ObservableList list) {
        this.list = list;
    }

    public Object getSelected() {
        return table.getSelectionModel().getSelectedItem();
    }

    public String getName() {
        return name;
    }
}
