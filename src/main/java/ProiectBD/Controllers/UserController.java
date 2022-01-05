package ProiectBD.Controllers;

import ProiectBD.DataClasses.User.MeciExtra;
import ProiectBD.DataClasses.User.Player;
import ProiectBD.DataClasses.User.Stadion;
import ProiectBD.DataClasses.User.Team;
import ProiectBD.HelloApplication;
import ProiectBD.Interface.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TabPane bigTabPane;
    @FXML
    private Tab tabPlayers;
    @FXML
    private Tab tabMatches;
    @FXML
    private Tab tabLeagues;
    @FXML
    private Tab tabTeams;
    @FXML
    private Tab tabMatch;
    @FXML
    private Tab tabStadiums;
    @FXML
    private Tab tabTeam;
    @FXML
    private AnchorPane stadionUp;

    //stadium page
    @FXML
    private Text stadionName;
    @FXML
    private Text locationText;
    @FXML
    private Text teamText;
    @FXML
    private Text capacityText;
    @FXML
    private Text priceText;

    //matches
    @FXML
    private DatePicker startCalendar;
    @FXML
    private DatePicker endCalendar;
    @FXML
    private ListView matchesList;
    @FXML
    private ListView dateMatchesList;

    //match
    @FXML
    private Text matchText;
    @FXML
    private Text team1Text;
    @FXML
    private Text team2Text;
    @FXML
    private Text stadionMatchText;
    @FXML
    private ListView timelineView;
    @FXML
    private ListView team1View;
    @FXML
    private ListView team2View;
    @FXML
    private Text dateMatchText;

    //playersPage
    @FXML
    private TableView tablePlayers;

    //leaguePage
    @FXML
    private TableView leagueTable;

    //teamPage
    @FXML
    private TableView jucatoriTable;
    @FXML
    private ListView statsListTeam;
    @FXML
    private ListView matchesListTeam;
    @FXML
    private Text textTeam;
    @FXML
    private Text stadionTeamText;

    //searchbars
    @FXML
    private TextField searchTeam;
    @FXML
    private TextField searchPlayer;

    private TableView currTable = null;
    private MeciExtra selectedMatch = null;

    class function implements Callable
    {
        @Override
        public void viewStadion(Stadion currStadion) {
            stadionUp.setVisible(true);
            stadionUp.setDisable(false);
            try {
                stadionName.setText(currStadion.getName());
                locationText.setText(currStadion.getLoc());
                teamText.setText(currStadion.getTeam());
                capacityText.setText(String.valueOf(currStadion.getCapacity()));
                priceText.setText(currStadion.getPrice() + "€");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void switchToAdmin(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin_interface.fxml"));
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1024, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MatchesPage matches = new MatchesPage(startCalendar, endCalendar, matchesList, dateMatchesList);
        tabMatches.setOnSelectionChanged(e -> matches.changeTabMatches());
        matches.changeTabMatches();

        MatchPage match = new MatchPage(matchText,team1Text,team2Text,timelineView,team1View, team2View, stadionMatchText, dateMatchText, new function());
        tabMatch.setOnSelectionChanged(e -> match.changeTabMatches(selectedMatch));

        tabPlayers.setOnSelectionChanged(e -> currTable = tablePlayers);
        tabLeagues.setOnSelectionChanged(e -> currTable = leagueTable);

        PlayersPage players = new PlayersPage(tablePlayers);
        LeaguePage league = new LeaguePage(leagueTable);

        tabMatch.setDisable(true);
        tabTeam.setDisable(true);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("View Match");
        menuItem1.setOnAction((event) -> {
            selectedMatch = ((MeciExtra)matchesList.getSelectionModel().getSelectedItem());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(selectedMatch.getData());
            try {
                if(!sdf.parse(selectedMatch.getData()).before(sdf2.parse(String.valueOf(LocalDate.now())))) {
                    selectedMatch = null;
                    tabMatch.setDisable(true);
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tabMatch.setDisable(false);
            bigTabPane.getSelectionModel().select(4);
        });
        contextMenu.getItems().add(menuItem1);
        matchesList.setContextMenu(contextMenu);

        FilteredList<Team> filteredListTeams = new FilteredList<>(leagueTable.getItems(), b -> true);
        searchTeam.textProperty().addListener(((obsV, oldV, newV) ->
        {
            filteredListTeams.setPredicate(Team -> {

                if(newV.isEmpty() || newV.isBlank() || newV.equals(""))
                {
                    return true;
                }

                String keyword = newV.toLowerCase();
                if(Team.getName().toLowerCase().contains(keyword))
                    return true;
                return false;
            });

            leagueTable.setItems(filteredListTeams);
        }));

        FilteredList<Player> filteredListPlayers = new FilteredList<>(tablePlayers.getItems(), b -> true);
        searchPlayer.textProperty().addListener(((obsV, oldV, newV) ->
        {
            filteredListPlayers.setPredicate(Player -> {

                if(newV.isEmpty() || newV.isBlank() || newV.equals(""))
                {
                    return true;
                }

                String keyword = newV.toLowerCase();
                if(Player.getName().toLowerCase().contains(keyword) || Player.getTeam().toLowerCase().contains(keyword))
                    return true;
                return false;
            });

            tablePlayers.setItems(filteredListPlayers);
        }));
    }

    public void setScrolling()
    {
        Node n1 = dateMatchesList.lookup(".scroll-bar");
        if (n1 instanceof ScrollBar) {
            final ScrollBar bar1 = (ScrollBar) n1;
            Node n2 = matchesList.lookup(".scroll-bar");
            if (n2 instanceof ScrollBar) {
                final ScrollBar bar2 = (ScrollBar) n2;
                bar1.valueProperty().bindBidirectional(bar2.valueProperty());
            }
        }
    }

    public void viewTeam(){
        String name = "";
        if(currTable.getSelectionModel().getSelectedItem() instanceof Player)
            name = ((Player)currTable.getSelectionModel().getSelectedItem()).getTeam();
        else
            name = ((Team)currTable.getSelectionModel().getSelectedItem()).getName();
        TeamPage page = new TeamPage(name, statsListTeam, matchesListTeam, textTeam, jucatoriTable, stadionTeamText, new function());
        tabTeam.setDisable(false);
        bigTabPane.getSelectionModel().select(3);
    }

    public void viewTeamStadion() throws SQLException {
        String name = "";
        name = ((Team)currTable.getSelectionModel().getSelectedItem()).getName();
        (new function()).viewStadion(new Stadion(name, false));
    }


    public void closeStadion() {
        stadionUp.setVisible(false);
        stadionUp.setDisable(true);
    }
}