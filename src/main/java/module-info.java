module com.example.puzzle {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.puzzle to javafx.fxml;
    opens com.example.puzzle.controller to javafx.fxml;
    exports com.example.puzzle;
    exports com.example.puzzle.model;
    exports com.example.puzzle.view;
    exports com.example.puzzle.controller;
    exports com.example.puzzle.service;
}
