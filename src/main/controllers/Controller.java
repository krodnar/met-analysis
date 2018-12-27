package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Controller implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private Button button;
    @FXML
    private Slider threshSlider;
    @FXML
    private CheckBox invertCheckBox;
    @FXML
    private TextField claheClipField;
    @FXML
    private TextField claheTileSizeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StringConverter<Double> converter = new DoubleStringConverter() {
            @Override
            public Double fromString(String value) {
                if (value.isEmpty()) return 0d;
                return super.fromString(value);
            }
        };

        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String text = change.getControlNewText();
            if (text.isBlank()) {
                change.setText("0");
                change.setCaretPosition(1);
                return change;
            } else if (text.matches("[-+]?[0-9]*\\.?[0-9]*")) {
                return change;
            } else {
                return null;
            }
        };

        claheClipField.setTextFormatter(new TextFormatter<>(converter, 0d, doubleFilter));
        claheTileSizeField.setTextFormatter(new TextFormatter<>(converter, 0d, doubleFilter));

        claheClipField.textProperty().addListener((observable, oldValue, newValue) -> {
            double newClipLimit = Double.parseDouble(newValue);
        });

        claheTileSizeField.textProperty().addListener((observable, oldValue, newValue) -> {
            double newSize = Double.parseDouble(newValue);
            if (newSize == 0) {
                newSize = 1;
            }
        });
    }
}
