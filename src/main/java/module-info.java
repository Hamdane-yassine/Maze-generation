/* doesn't work with source level 1.8:
module com.mycompany.mazegeneration {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.mazegeneration to javafx.fxml;
    exports com.mycompany.mazegeneration;
}
*/
