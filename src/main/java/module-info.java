module com.mycompany.main {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;
    requires com.google.gson;
    opens com.mycompany.main to javafx.fxml;
    opens com.mycompany.models to javafx.base , com.google.gson;
    exports com.mycompany.main;
}

