package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.preprocess.ImagePreprocessor;
import main.utils.Utils;
import main.views.ImageViewPane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ContoursController implements Initializable {

	private ImagePreprocessor preprocessor;

	private ImageView contoursView = new ImageView();
	private ImageViewPane contoursViewPane = new ImageViewPane(contoursView);

	@FXML
	private BorderPane root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contoursView.setPreserveRatio(true);
	}

	public void showContours() {
		if (!preprocessor.isReady()) {
			return;
		}

		Mat source = preprocessor.getSource();
		Mat processedMat = preprocessor.getProcessedMat();

		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();

		Imgproc.findContours(processedMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Imgproc.drawContours(source, contours, -1, new Scalar(255), 3);

		contoursView.setImage(Utils.mat2Image(source));
		root.setCenter(contoursViewPane);
	}

	public ImagePreprocessor getPreprocessor() {
		return preprocessor;
	}

	public void setPreprocessor(ImagePreprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}
}
