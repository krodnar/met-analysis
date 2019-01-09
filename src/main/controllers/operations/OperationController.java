package main.controllers.operations;

import javafx.fxml.Initializable;
import main.preprocess.ImagePreprocessor;
import main.preprocess.PreprocessorOperation;
import main.preprocess.operations.ImageOperation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class OperationController<T extends ImageOperation<T>> implements Initializable {

	private ImagePreprocessor preprocessor;
	private PreprocessorOperation<T> operation;
	private ArrayList<OperationControlsListener> listeners = new ArrayList<>();

	public OperationController(ImagePreprocessor preprocessor, PreprocessorOperation<T> operation) {
		this.preprocessor = preprocessor;
		this.operation = operation;
	}

	public void addListener(OperationControlsListener listener) {
		listeners.add(listener);
	}

	public void removeListener(OperationControlsListener listener) {
		listeners.remove(listener);
	}

	public void applyOperation() {
		operation.apply();
		fireOnApply();
	}

	private void fireOnApply() {
		for (OperationControlsListener listener : listeners) {
			listener.onOperationApply(this, operation);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setControlsValues(preprocessor, operation.getImageOperation());
		setControlsListeners(preprocessor, operation.getImageOperation());
	}

	public abstract void setControlsValues(ImagePreprocessor preprocessor, T operation);

	public abstract void setControlsListeners(ImagePreprocessor preprocessor, T operation);

	public interface OperationControlsListener {

		void onOperationApply(OperationController controller, PreprocessorOperation operation);
	}
}
