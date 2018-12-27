package main.controllers.operations;

import javafx.fxml.Initializable;
import main.preprocess.ImagePreprocessor;
import main.preprocess.operations.PreprocessorOperation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class OperationController<T extends PreprocessorOperation> implements Initializable {

	private ImagePreprocessor preprocessor;
	private T operation;
	private ArrayList<OperationControlsListener> listeners = new ArrayList<>();

	public OperationController(ImagePreprocessor preprocessor, T operation) {
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
		preprocessor.applySingleOperation(operation);
		fireOnApply(operation);
	}

	private void fireOnApply(PreprocessorOperation operation) {
		for (OperationControlsListener listener : listeners) {
			listener.onOperationApply(this, operation);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setControlsValues(operation);
		setControlsListeners(operation);
	}

	public abstract void setControlsValues(T operation);

	public abstract void setControlsListeners(T operation);

	public interface OperationControlsListener {

		void onOperationApply(OperationController controller, PreprocessorOperation operation);
	}
}
