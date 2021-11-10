module all.proiectbd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires java.sql;
    requires com.oracle.database.jdbc;

    opens Interface to javafx.fxml;
    exports Interface;
    exports DataClasses;
    exports SQL;
    opens DataClasses to javafx.fxml;
    exports DataClasses.Adapter;
    opens DataClasses.Adapter to javafx.fxml;

}