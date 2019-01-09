package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.controllers.operations.*;
import main.preprocess.ImagePreprocessor;
import main.preprocess.PreprocessorOperation;
import main.utils.Utils;
import main.views.ImageViewPane;
import main.views.OperationListCell;
import org.opencv.core.Mat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OperationsController implements Initializable, OperationController.OperationControlsListener {

	private ImagePreprocessor preprocessor = new ImagePreprocessor();

	private ImageView imageView = new ImageView();
	private ImageViewPane imageViewPane = new ImageViewPane(imageView);

	@FXML
	private ListView<PreprocessorOperation> operationsList;
	@FXML
	private BorderPane operationContainer;
	@FXML
	private HBox controlsContainer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.setPreserveRatio(true);

		initList();
		populateList(preprocessor);
	}

	private void populateList(ImagePreprocessor preprocessor) {
		ObservableList<PreprocessorOperation> observableList = FXCollections.observableArrayList();
		observableList.setAll(preprocessor.getOperations());
		operationsList.setItems(observableList);
		operationsList.setCellFactory(listView -> new OperationListCell());
	}

	private void initList() {
		operationsList.getSelectionModel().selectedItemProperty().addListener((observable, oldOperation, newOperation) -> {
			if (!preprocessor.isReady()) {
				return;
			}

			if (oldOperation != null) {
				preprocessor.applyOperationsFrom(oldOperation);
			}

			Mat imageMat = preprocessor.getMat(newOperation);
			imageView.setImage(Utils.mat2Image(imageMat));

			setCurrentOperation(newOperation);
		});
	}

	private void setCurrentOperation(PreprocessorOperation operation) {
		controlsContainer.getChildren().clear();

		FXMLLoader loader = new FXMLLoader();
		OperationController controller = null;

		switch (operation.getType()) {

			case GRAYSCALE:
				return;
			case CLAHE:
				loader.setLocation(getClass().getResource("../views/claheOperation.fxml"));
				controller = new ClaheOperationController(preprocessor, operation);
				break;
			// TODO: 25.12.2018 add adaptive threshold controls
			// case ADAPTIVE_THRESHOLD:
			// 	loader.setLocation(getClass().getResource("../views/adaptiveThresholdOperation.fxml"));
			// 	controller = new AdaptiveThresholdOperationController(preprocessor, (newOperation));
			// 	break;
			case THRESHOLD:
				loader.setLocation(getClass().getResource("../views/thresholdOperation.fxml"));
				controller = new ThresholdOperationController(preprocessor, operation);
				break;
			case MORPHOLOGY:
				loader.setLocation(getClass().getResource("../views/morphologicalOperation.fxml"));
				controller = new MorphologicalOperationController(preprocessor, operation);
				break;
			case BLUR:
				loader.setLocation(getClass().getResource("../views/blurOperation.fxml"));
				controller = new BlurOperationController(preprocessor, operation);
				break;
			default:
				return;
		}

		if (controller == null) {
			return;
		}

		controller.addListener(this);
		loader.setController(controller);
		Parent controls = null;

		try {
			controls = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		controlsContainer.getChildren().add(controls);
	}

	public void setSource(Mat sourceMat) {
		imageView.setImage(Utils.mat2Image(sourceMat));
		operationContainer.setCenter(imageViewPane);
		operationsList.getSelectionModel().select(0);
	}

	@Override
	public void onOperationApply(OperationController controller, PreprocessorOperation operation) {
		Mat imageMat = operation.getResult();
		imageView.setImage(Utils.mat2Image(imageMat));
	}

	public ImagePreprocessor getPreprocessor() {
		return preprocessor;
	}

	public void setPreprocessor(ImagePreprocessor preprocessor) {
		this.preprocessor = preprocessor;
		populateList(preprocessor);
	}

	public Mat getProcessedMat() {
		return preprocessor.getProcessedMat();
	}

	public Image getProcessedImage() {
		return Utils.mat2Image(preprocessor.getProcessedMat());
	}
}
