package ProiectBD;

import ProiectBD.Controllers.UserController;
import ProiectBD.SQL.SQLConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            SQLConnection.makeSQLConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new_interface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 720);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Tema de casa BD");
        stage.setScene(scene);
        stage.show();
        fxmlLoader.<UserController>getController().setScrolling();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        SQLConnection.closeSQLConenction();
    }
}