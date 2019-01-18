package main.controllers.operations;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import main.preprocess.ImagePreprocessor;
import main.preprocess.PreprocessorOperation;
import main.preprocess.operations.ThresholdOperation;
import org.opencv.imgproc.Imgproc;

public class ThresholdOperationController extends OperationController<ThresholdOperation> {

	@FXML
	private Slider thresholdSlider;
	@FXML
	private CheckBox invertCheckbox;

	public ThresholdOperationController(PreprocessorOperation<ThresholdOperation> operation) {
		super(operation);
	}

	@Override
	public void setControlsValues(PreprocessorOperation preprocessorOperation, ThresholdOperation operation) {
		double threshold = operation.getThreshold();
		thresholdSlider.setValue(threshold);

		if (operation.getThresholdType() == Imgproc.THRESH_BINARY) {
			invertCheckbox.selectedProperty().setValue(false);
		} else if (operation.getThresholdType() == Imgproc.THRESH_BINARY_INV) {
			invertCheckbox.selectedProperty().setValue(true);
		}
	}

	@Override
	public void setControlsListeners(PreprocessorOperation preprocessorOperation, ThresholdOperation operation) {
		thresholdSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
			operation.setThreshold(newValue.doubleValue());
			applyOperation();
		}));

		invertCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
			if (newValue) {
				operation.setThresholdType(Imgproc.THRESH_BINARY_INV);
			} else {
				operation.setThresholdType(Imgproc.THRESH_BINARY);
			}

			applyOperation();
		}));
	}
}
