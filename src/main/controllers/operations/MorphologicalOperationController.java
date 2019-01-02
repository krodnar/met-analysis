package main.controllers.operations;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.preprocess.ImagePreprocessor;
import main.preprocess.PreprocessorOperation;
import main.preprocess.operations.MorphologicalOperation;
import org.opencv.core.Size;

public class MorphologicalOperationController extends OperationController<MorphologicalOperation> {

    @FXML
    private Slider crossSizeSlider;

    @FXML
    private Slider ellipseSizeSlider;

    public MorphologicalOperationController(ImagePreprocessor preprocessor, PreprocessorOperation<MorphologicalOperation> operation) {
        super(preprocessor, operation);
    }

    @Override
    public void setControlsValues(MorphologicalOperation operation) {
        double crossSize = operation.getCrossSize().height;
        crossSizeSlider.setValue(crossSize);

        double ellipseSize = operation.getEllipseSize().height;
        ellipseSizeSlider.setValue(ellipseSize);
    }

    @Override
    public void setControlsListeners(MorphologicalOperation operation) {
        crossSizeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            Size newSize = new Size(newValue.doubleValue(), newValue.doubleValue());
            operation.setCrossSize(newSize);
            applyOperation();
        }));

        ellipseSizeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            Size newSize = new Size(newValue.doubleValue(), newValue.doubleValue());
            operation.setEllipseSize(newSize);
            applyOperation();
        }));
    }
}
