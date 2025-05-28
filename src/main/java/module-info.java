module OOPProject1{
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bloodBank to javafx.fxml;
    exports com.bloodBank;
}