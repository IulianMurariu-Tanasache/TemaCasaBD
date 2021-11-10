package Interface;

import DataClasses.Adapter.EchipaAdapter;
import DataClasses.Adapter.JucatorAdapter;
import DataClasses.Adapter.MeciAdapter;
import DataClasses.Adapter.StadionAdapter;
import DataClasses.Table;
import SQL.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    /*
    TO DO:
    - Verificare fiecare parametru pentru introducerea corecta a datelor (ex: Nume si Prenume fara numere)
    - PK, FK, relatii
    - Tratarea erorirlor
     */

    //Tabelele din UI
    @FXML
    public TableView<?> _tableEchipe;
    @FXML
    public TableView<?> _tableMeciuri;
    @FXML
    public TableView<?> _tableJucatori;
    @FXML
    public TableView<?> _tableStadioane;

    //Tab-urile care schimba intre echipe
    @FXML
    public Tab tabEchipe;
    @FXML
    public Tab tabJucatori;
    @FXML
    public Tab tabMeciuri;
    @FXML
    public Tab tabStadioane;

    @FXML
    public AnchorPane popupMenu; //Meniu popup folosit pentru adaugat/schimbat date intr-o inregistrare

    public ArrayList<TextField> fields; //Lista de field-uri de completat in meniul de popup cand se adauga/modifica

    //Tabele care cuprind partea vizuala, lista de date si adaptor specific
    public Table tableEchipe;
    public Table tableJucatori;
    public Table tableMeciuri;
    public Table tableStadioane;
    public Table currTable; // Tabelul la care ne uitam in momentul curent

    //valori pentru aranjarea in UI a field-urilor de completat
    public final int offsetTextfieldX = 20;
    public final int offsetTextfieldY = 35;
    public final int textfieldWidth = 250;
    public final int textfieldHeight = 35;
    public final int textfieldSpaceY = 25;

    //Boolean pentru a stii daca se adauga sau se modifica
    public boolean toAdd = true;

    //Functie de initializare a UI-ului
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SQLConnection.makeSQLConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        setTables();
        fields = new ArrayList<>();
        currTable = tableEchipe;

        //Atunci cand se schimba tab-ul, se schimba si tabelul curent
        tabEchipe.setOnSelectionChanged(e -> changeTable(tableEchipe));
        tabJucatori.setOnSelectionChanged(e -> changeTable(tableJucatori));
        tabMeciuri.setOnSelectionChanged(e -> changeTable(tableMeciuri));
        tabStadioane.setOnSelectionChanged(e -> changeTable(tableStadioane));

        PopupMenu(false);
    }

    /*
    Functie de initializare a unui tabel
    baseClass - clasa de date dupa care se modeleaza tabelul si coloanele sale
     */
    private void setTable(String baseClass, TableView<?> table) throws ClassNotFoundException {
        int i = 0;
        //Pentru fiecare membru al clasei de baza, se creeaza o coloana in tabel
        for(Field f : Class.forName("DataClasses." + baseClass).getDeclaredFields()) {
            TableColumn c = table.getColumns().get(i);
            c.setStyle("-fx-alignment: CENTER;");
            c.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            ++i;
        }
    }

    //Functie care initializeaza fiecare tabel
    public void setTables() {
        try {
            setTable("Echipa",_tableEchipe);
            setTable("Jucator",_tableJucatori);
            setTable("Meci",_tableMeciuri);
            setTable("Stadion",_tableStadioane);

            tableEchipe = new Table(_tableEchipe, new EchipaAdapter(), new EchipaSQL());
            tableJucatori = new Table(_tableJucatori, new JucatorAdapter(), new JucatorSQL());
            tableMeciuri = new Table(_tableMeciuri, new MeciAdapter(), new MeciSQL());
            tableStadioane = new Table(_tableStadioane, new StadionAdapter(), new StadionSQL());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Functie folosita pentru a schimba tabela curenta
    public void changeTable(Table tab) {
        currTable = tab;
    }

    //Functie pentru butonul de adaugare
    public void OnAdd() {
        toAdd = true;
        PopupMenu(true);
    }

    //Functie pentru butonul de modificare
    public void OnEdit() {
        toAdd = false;
        PopupMenu(true);
    }

    //Functie pentru butonul de stergere
    public void OnDelete() {
        try {
            currTable.getSql().Delete(currTable.getSelected());
            currTable.setList(currTable.getSql().Select());
            currTable.getTable().setItems(currTable.getList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Functie pentru creare alerta
    public void Alerta(String title, String content) {
        PopupMenu(false);
        //Alerta utilizatorul de eroare
        Alert badType = new Alert(Alert.AlertType.WARNING);
        badType.setTitle(title);
        badType.setContentText(content);
        badType.show();
    }

    //Functie pentru butonul de enter pentru adaugarea/modificarea in tabel
    public void OnEnter() {
        String[] param = new String[fields.size()]; // parametrii noii inregistrari
        for (int i = 0; i < fields.size(); ++i) {
            param[i] = fields.get(i).getText();
        }
        //AAdaugarea unui element nou
        if (toAdd) {
            try {
                currTable.getList().add(currTable.getAdaptor().createObject(param));
                currTable.getTable().getItems().set(currTable.getList().size() - 1, currTable.getList().get(currTable.getList().size() - 1));
                currTable.getSql().Insert(currTable.getList().get(currTable.getList().size() - 1));
                currTable.setList(currTable.getSql().Select());
                currTable.getTable().setItems(currTable.getList());
            } catch (SQLException e) {
                Alerta("SQL Error", "A aparut o eroare la baza de date! ");
                return;
            } catch (NumberFormatException e) {
                Alerta("Bad Type", "A fost introdus o valoare necorespunzatoare !");
                return;
            }
        } else {
            try {
                currTable.getSql().Update(currTable.getList().get(currTable.getList().indexOf(currTable.getSelected())), currTable.getAdaptor().createObject(param));
                currTable.setList(currTable.getSql().Select());
                currTable.getTable().setItems(currTable.getList());
            } catch (SQLException e) {
                Alerta("SQL Error", "A aparut o eroare la baza de date! ");
                return;
            } catch (NumberFormatException e) {
                Alerta("Bad Type", "A fost introdus o valoare necorespunzatoare !");
                return;
            }
        }
        PopupMenu(false);
    }

    //Functie pentru butonul de cancel
    public void OnCancel() {
        PopupMenu(false);
    }

    //Functie pentru meniul de popup
    public void PopupMenu(boolean visible) {
        if(visible && !toAdd && currTable.getSelected() == null) {
            Alerta("None Selected","Nu a fost selectata niciun rand din tabela !");
            return;
        }

        popupMenu.setDisable(!visible);
        popupMenu.setVisible(visible);
        int size = currTable.getTable().getColumns().size();

        if(visible) {
            //creearea field-urilor de completat
            for(int i = 0; i < size; ++i) {
                TextField field = new TextField();
                field.setPrefWidth(textfieldWidth);
                field.setPrefHeight(textfieldHeight);
                field.setLayoutY(offsetTextfieldY + (i / 3) * (textfieldHeight + textfieldSpaceY));
                field.setPromptText(((TableColumn)currTable.getTable().getColumns().get(i)).getText()); // hint text ce coloana reprezinta

                if(i == 6)
                    field.setLayoutX(offsetTextfieldX + textfieldWidth); // daca e la maxim, il pun pe mijloc sa arate frumos
                else
                    field.setLayoutX(offsetTextfieldX + (i % 3) * textfieldWidth);

                if(!toAdd) {
                    field.setText(String.valueOf(((TableColumn)currTable.getTable().getColumns().get(i)).getCellObservableValue(currTable.getSelected()).getValue())); // completez field-urile pentru obiectul care exista deja in tabela, cand se modifica
                }

                fields.add(field);
                popupMenu.getChildren().add(field);
            }
        }
        else {
            for(TextField field : fields) {
                popupMenu.getChildren().remove(field); // stergerea field-urilor cand se inchide meniul
            }
            fields.clear();
        }
    }

}