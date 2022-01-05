module ProiectBD {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires com.oracle.database.jdbc;

    opens ProiectBD to javafx.fxml;
    exports ProiectBD;
    exports ProiectBD.Controllers;
    exports ProiectBD.DataClasses.Adapter;
    opens ProiectBD.DataClasses.Adapter to javafx.fxml;
    opens ProiectBD.Controllers to javafx.fxml;
    exports ProiectBD.SQL;
    exports ProiectBD.DataClasses.User;
    opens ProiectBD.DataClasses.User to javafx.fxml;
    exports ProiectBD.DataClasses.AdminClasses;
    opens ProiectBD.DataClasses.AdminClasses to javafx.fxml;


}