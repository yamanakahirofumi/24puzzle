module com.example.puzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens com.example.puzzle to javafx.fxml;
    opens com.example.puzzle.controller to javafx.fxml;
    exports com.example.puzzle;
}
