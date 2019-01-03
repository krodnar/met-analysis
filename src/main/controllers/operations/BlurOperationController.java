package main.controllers.operations;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import main.preprocess.ImagePreprocessor;
import main.preprocess.PreprocessorOperation;
import main.preprocess.operations.BlurOperation;

public class BlurOperationController extends OperationController<BlurOperation> {

    @FXML
    private Slider kernelSizeSlider;

    public BlurOperationController(ImagePreprocessor preprocessor, PreprocessorOperation<BlurOperation> operation) {
        super(preprocessor, operation);
    }

    @Override
    public void setControlsValues(ImagePreprocessor preprocessor, BlurOperation operation) {
        int kernelSize = operation.getKernelSize();
        kernelSizeSlider.setValue(kernelSize);
    }

    @Override
    public void setControlsListeners(ImagePreprocessor preprocessor, BlurOperation operation) {
        kernelSizeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() % 2 != 1) {
                kernelSizeSlider.setValue(newValue.intValue() - 1);
                return;
            }

            operation.setKernelSize(newValue.intValue());
            applyOperation();
        }));

        kernelSizeSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            preprocessor.scale(0.25);
        });

        kernelSizeSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            preprocessor.unscale();
            applyOperation();
        });
    }
}
