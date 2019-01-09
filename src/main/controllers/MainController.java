package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import main.preprocess.ImagePreprocessor;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	private final FileChooser fileChooser = new FileChooser();

	private OperationsController operationsController;
	private ContoursController contoursController;

	private ImagePreprocessor preprocessor = new ImagePreprocessor();

	@FXML
	private MenuItem openMenuItem;
	@FXML
	private MenuItem resetMenuItem;
	@FXML
	private TabPane phasesTabs;
	@FXML
	private Tab operationsTab;
	@FXML
	private Tab contoursTab;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initFileChooser();
		initTabs();

		contoursTab.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
			if (isSelected) {
				contoursController.showContours();
			}
		});

		openMenuItem.setOnAction((event -> openImage()));
		resetMenuItem.setOnAction((event -> initTabs()));
	}

	public void openImage() {
		File file = fileChooser.showOpenDialog(null);

		if (file == null) {
			return;
		}

		Mat sourceMat = Imgcodecs.imread(file.getAbsolutePath());

		if (sourceMat.empty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Файл поврежден или этот формат не поддерживается");
			alert.setTitle("Ошибка");
			alert.setHeaderText("Ошибка при открытии изображения");
			alert.showAndWait();
			return;
		}

		preprocessor.setSourceMat(sourceMat);
		operationsController.setSource(sourceMat);
	}

	private void initFileChooser() {
		fileChooser.setTitle("Открыть изображение");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
	}

	private void initTabs() {
		try {
			operationsController = initTab(getClass().getResource("../views/operationsTab.fxml"), operationsTab);
			operationsController.setPreprocessor(preprocessor);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			contoursController = initTab(getClass().getResource("../views/contoursTab.fxml"), contoursTab);
			contoursController.setPreprocessor(preprocessor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private <T> T initTab(URL location, Tab tab) throws IOException {
		FXMLLoader loader = new FXMLLoader(location);

		Parent pane = loader.load();
		tab.setContent(pane);

		return loader.getController();
	}
}
