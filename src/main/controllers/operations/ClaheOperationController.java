package main.controllers.operations;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.preprocess.PreprocessorOperation;
import main.preprocess.operations.ClaheOperation;
import main.preprocess.ImagePreprocessor;
import org.opencv.core.Size;

public class ClaheOperationController extends OperationController<ClaheOperation> {

	@FXML
	private Slider clipLimitSlider;

	@FXML
	private Slider tileSizeSlider;

	public ClaheOperationController(ImagePreprocessor preprocessor, PreprocessorOperation<ClaheOperation> operation) {
		super(preprocessor, operation);
	}

	@Override
	public void setControlsValues(ClaheOperation operation) {
		double clipLimit = operation.getClipLimit();
		clipLimitSlider.setValue(clipLimit);

		double tileSize = operation.getTilesSize().height;
		tileSizeSlider.setValue(tileSize);
	}

	@Override
	public void setControlsListeners(ClaheOperation operation) {
		clipLimitSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
			operation.setClipLimit(newValue.doubleValue());
			applyOperation();
		}));

		tileSizeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
			Size newSize = new Size(newValue.doubleValue(), newValue.doubleValue());
			operation.setTilesSize(newSize);
			applyOperation();
		}));
	}
}
