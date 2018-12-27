package main.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OperationView {

    @FXML
    private VBox container;
    @FXML
    private Label label;

    public OperationView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("operationView.fxml"));
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) {
        label.setText(text);
    }

    public VBox getContainer() {
        return container;
    }
}
