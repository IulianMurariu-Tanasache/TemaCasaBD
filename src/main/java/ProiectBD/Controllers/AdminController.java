package ProiectBD.Controllers;

import ProiectBD.DataClasses.Adapter.*;
import ProiectBD.DataClasses.AdminClasses.Table;
import ProiectBD.HelloApplication;
import ProiectBD.SQL.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    //Tabelele din UI
    @FXML
    public TableView<?> _tableEchipe;
    @FXML
    public TableView<?> _tableMeciuri;
    @FXML
    public TableView<?> _tableJucatori;
    @FXML
    public TableView<?> _tableStadioane;
    @FXML
    public TableView<?> _tableMeci_Stat;

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
    public Tab tabMeci_Stat;

    @FXML
    public Button enterButton;
    @FXML
    public Button cancelButton;
    @FXML
    public AnchorPane popupMenu; //Meniu popup folosit pentru adaugat/schimbat date intr-o inregistrare

    public ArrayList<TextField> fields; //Lista de field-uri de completat in meniul de popup cand se adauga/modifica
    public ArrayList<ChoiceBox<String>> dropdowns; //

    //Tabele care cuprind partea vizuala, lista de date si adaptor specific
    public Table tableEchipe;
    public Table tableJucatori;
    public Table tableMeciuri;
    public Table tableStadioane;
    public Table tableMeci_Stat;
    public Table currTable; // Tabelul la care ne uitam in momentul curent

    //valori pentru aranjarea in UI a field-urilor de completat
    public final int offsetTextfieldX = 20;
    public final int offsetTextfieldY = 35;
    public final int textfieldWidth = 250;
    public final int textfieldHeight = 35;
    public final int textfieldSpaceY = 25;

    //Boolean pentru a stii daca se adauga sau se modifica
    public boolean toAdd = true;
    public String teamRegistered;
    enum RegisterStep{
        NONE,
        TEAM,
        PLAYER,
        STADION
    };
    RegisterStep register = RegisterStep.NONE;

    //Functie de initializare a UI-ului
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SQLConnection.getCon().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTables();
        fields = new ArrayList<>();
        dropdowns = new ArrayList<>();
        currTable = tableEchipe;

        //Atunci cand se schimba tab-ul, se schimba si tabelul curent
        tabEchipe.setOnSelectionChanged(e -> changeTable(tableEchipe));
        tabJucatori.setOnSelectionChanged(e -> changeTable(tableJucatori));
        tabMeciuri.setOnSelectionChanged(e -> changeTable(tableMeciuri));
        tabStadioane.setOnSelectionChanged(e -> changeTable(tableStadioane));
        tabMeci_Stat.setOnSelectionChanged(e -> changeTable(tableMeci_Stat));

        PopupMenu(false);
    }

    @FXML
    protected void switchToUser(ActionEvent event){
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new_interface.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1024, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("User");
        stage.setScene(scene);
        stage.show();
    }

    /*
    Functie de initializare a unui tabel
    baseClass - clasa de date dupa care se modeleaza tabelul si coloanele sale
     */
    private void setTable(String baseClass, TableView<?> table) throws ClassNotFoundException {
        int i = 0;
        //Pentru fiecare membru al clasei de baza, se creeaza o coloana in tabel
        for(Field f : Class.forName("ProiectBD.DataClasses.AdminClasses." + baseClass).getDeclaredFields()) {
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
            setTable("Meci_Stat",_tableMeci_Stat);

            tableEchipe = new Table(_tableEchipe, new EchipaAdapter(), new EchipaSQL());
            tableJucatori = new Table(_tableJucatori, new JucatorAdapter(), new JucatorSQL());
            tableMeciuri = new Table(_tableMeciuri, new MeciAdapter(), new MeciSQL());
            tableStadioane = new Table(_tableStadioane, new StadionAdapter(), new StadionSQL());
            tableMeci_Stat = new Table(_tableMeci_Stat, new Meci_StatAdapter(), new Meci_StatSQL());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Functie folosita pentru a schimba tabela curenta
    public void changeTable(Table tab) {
        if(popupMenu.isVisible())
            return;
        currTable = tab;
        try {
            currTable.setList(currTable.getSql().Select());
            currTable.getTable().setItems(currTable.getList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void Alerta(String title, String content, Exception e) {
        PopupMenu(false);
        //Alerta utilizatorul de eroare
        Alert badType = new Alert(Alert.AlertType.WARNING);
        badType.setTitle(title);
        String msg = content + '\n' + (e != null ? e.getMessage() : "");
        Text label = new Text(msg);
        label.setWrappingWidth(200);
        if (e != null) {
            System.out.println(e.getMessage());
        }
        badType.getDialogPane().setContent(label);
        badType.show();
    }

    public void setAutoCommitOn()
    {
        try {
            SQLConnection.getCon().rollback();
            System.out.println("rolled back");
            SQLConnection.getCon().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Functie pentru butonul de enter pentru adaugarea/modificarea in tabel
    public void OnEnter() {
        String[] param = new String[fields.size() + dropdowns.size()]; // parametrii noii inregistrari
        for (int i = 0; i < fields.size(); ++i) {
            param[i] = fields.get(i).getText();
        }

        for (int i = fields.size(); i < fields.size() + dropdowns.size(); ++i) {
            param[i] = dropdowns.get(i - fields.size()).getValue();
        }
        //Adaugarea unui element nou
        if (toAdd) {
            try {
                currTable.getSql().Insert(currTable.getAdaptor().createObject(param));
                currTable.setList(currTable.getSql().Select());
                currTable.getTable().setItems(currTable.getList());
            } catch (SQLException e) {
                setAutoCommitOn();
                Alerta("SQL Error", "A aparut o eroare la baza de date! ", e);
            } catch (NumberFormatException e) {
                setAutoCommitOn();
                Alerta("Bad Type", "A fost introdus o valoare necorespunzatoare !", e);
                return;
            } catch(NullPointerException e){
                setAutoCommitOn();
                Alerta("Null error", "Completati toate campurile!", e);
                return;
            } catch (BadFormatException e) {
                setAutoCommitOn();
                Alerta("Bad Format", "", e);
                return;
            }
        } else {
            try {
                currTable.getSql().Update(currTable.getList().get(currTable.getList().indexOf(currTable.getSelected())), currTable.getAdaptor().createObject(param));
                currTable.setList(currTable.getSql().Select());
                currTable.getTable().setItems(currTable.getList());
            } catch (SQLException e) {
                setAutoCommitOn();
                Alerta("SQL Error", "A aparut o eroare la baza de date! ", e);
            } catch (NumberFormatException e) {
                setAutoCommitOn();
                Alerta("Bad Type", "A fost introdus o valoare necorespunzatoare !", e);
                return;
            } catch(NullPointerException e){
                setAutoCommitOn();
                Alerta("Null error", "Completati toate campurile!", e);
                return;
            } catch (BadFormatException e) {
                setAutoCommitOn();
                Alerta("Bad Format", "", e);
                return;
            }
        }
        PopupMenu(false);
        if(register == RegisterStep.TEAM)
        {
            register = RegisterStep.PLAYER;
            teamRegistered = param[0];
            addPlayersToTeam();
        }
        else if(register == RegisterStep.PLAYER)
        {
            addPlayersToTeam();
        }
        else if(register == RegisterStep.STADION)
        {
            try {
                SQLConnection.getCon().commit();
                SQLConnection.getCon().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("autocommit on");
            register = RegisterStep.NONE;
        }
    }

    //Functie pentru butonul de cancel
    public void OnCancel() {
        PopupMenu(false);
        if(register != RegisterStep.NONE)
            setAutoCommitOn();
    }

    //Functie pentru meniul de popup
    public void PopupMenu(boolean visible) {
        if(visible && !toAdd && currTable.getSelected() == null) {
            Alerta("None Selected","Nu a fost selectata niciun rand din tabela !", null);
            return;
        }

        popupMenu.setDisable(!visible);
        popupMenu.setVisible(visible);
        int size = currTable.getTable().getColumns().size();
        int index = 0;
        if(visible) {
            popupMenu.getChildren().clear();
            popupMenu.getChildren().add(enterButton);
            popupMenu.getChildren().add(cancelButton);
            dropdowns.clear();
            //creearea field-urilor de completat
            for(int i = 0; i < size; ++i) {
                String col = ((TableColumn)currTable.getTable().getColumns().get(i)).getText();
                //System.out.println(col);
                if((currTable.getAdaptor() instanceof MeciAdapter ||currTable.getAdaptor() instanceof EchipaAdapter || currTable.getAdaptor() instanceof JucatorAdapter) && col.contains("id_") || (currTable.getAdaptor() instanceof Meci_StatAdapter && col.equals("id_stat"))) // trebuie facut calumea :))
                    continue;
                if(currTable.getAdaptor().getFKs().containsKey(col)) {
                    ChoiceBox<String> drop = new ChoiceBox<>();
                    try {
                        String[] par = (String[]) currTable.getAdaptor().getFKs().get(col);
                        drop.getItems().addAll(currTable.getSql().getFKs(par[0],par[1]));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    drop.setPrefWidth(textfieldWidth);
                    drop.setPrefHeight(textfieldHeight);
                    drop.setLayoutY(offsetTextfieldY + (index / 3) * (textfieldHeight + textfieldSpaceY));
                    //drop.setPromptText(col); // hint text ce coloana reprezinta

                    if (index == 6)
                        drop.setLayoutX(offsetTextfieldX + textfieldWidth); // daca e la maxim, il pun pe mijloc sa arate frumos
                    else
                        drop.setLayoutX(offsetTextfieldX + (index++ % 3) * textfieldWidth);

                    dropdowns.add(drop);
                    popupMenu.getChildren().add(drop);

                } else {
                    TextField field = new TextField();
                    field.setPrefWidth(textfieldWidth);
                    field.setPrefHeight(textfieldHeight);
                    field.setLayoutY(offsetTextfieldY + (index / 3) * (textfieldHeight + textfieldSpaceY));
                    field.setPromptText(col); // hint text ce coloana reprezinta

                    if (index == 6)
                        field.setLayoutX(offsetTextfieldX + textfieldWidth); // daca e la maxim, il pun pe mijloc sa arate frumos
                    else
                        field.setLayoutX(offsetTextfieldX + (index++ % 3) * textfieldWidth);

                    if (!toAdd) {
                        field.setText(String.valueOf(((TableColumn) currTable.getTable().getColumns().get(i)).getCellObservableValue(currTable.getSelected()).getValue())); // completez field-urile pentru obiectul care exista deja in tabela, cand se modifica
                    }

                    fields.add(field);
                    popupMenu.getChildren().add(field);
                }
            }
        }
        else {
            for(TextField field : fields) {
                popupMenu.getChildren().remove(field); // stergerea field-urilor cand se inchide meniul
            }
            fields.clear();
        }
    }

    public void registerTeam() throws SQLException {
        try {
            SQLConnection.getCon().setAutoCommit(false);
            System.out.println("autocommit off");
            register = RegisterStep.TEAM;
            toAdd = true;
            PopupMenu(true);
        }
         catch (Exception e) {
            SQLConnection.getCon().rollback();
            System.out.println("rolled back");
            e.printStackTrace();
        }
    }

    public void addPlayersToTeam()
    {
        //adaugare continua de jucatori
        currTable = tableJucatori;
        toAdd = true;
        PopupMenu(true);
        Button doneButton = new Button();
        doneButton.setText("Done adding players");
        doneButton.setLayoutX(300);
        doneButton.setLayoutY(143);
        doneButton.setPrefHeight(50);
        doneButton.setPrefWidth(150);
        doneButton.setOnAction(e ->
        {
            PopupMenu(false);
            register = RegisterStep.STADION;
            currTable = tableStadioane;
            toAdd = true;
            PopupMenu(true);
            dropdowns.get(0).getSelectionModel().select(teamRegistered);
            dropdowns.get(0).setDisable(true);
        });
        popupMenu.getChildren().add(doneButton);
        dropdowns.get(0).getSelectionModel().select(teamRegistered);
        dropdowns.get(0).setDisable(true);
    }
}
