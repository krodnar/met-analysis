package main.controllers.operations;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.preprocess.operations.BlurOperation;
import main.preprocess.ImagePreprocessor;

public class BlurOperationController extends OperationController<BlurOperation> {

    @FXML
    private Slider kernelSizeSlider;

    public BlurOperationController(ImagePreprocessor preprocessor, BlurOperation operation) {
        super(preprocessor, operation);
    }

    @Override
    public void setControlsValues(BlurOperation operation) {
        int kernelSize = operation.getKernelSize();
        kernelSizeSlider.setValue(kernelSize);
    }

    @Override
    public void setControlsListeners(BlurOperation operation) {
        kernelSizeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() % 2 != 1) {
                kernelSizeSlider.setValue(newValue.intValue() - 1);
                return;
            }

            operation.setKernelSize(newValue.intValue());
            applyOperation();
        }));
    }
}
