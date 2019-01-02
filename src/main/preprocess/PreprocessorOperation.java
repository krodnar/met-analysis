package main.preprocess;

import main.preprocess.operations.ImageOperation;
import org.opencv.core.Mat;

public class PreprocessorOperation<T extends ImageOperation<T>> {

	private ImagePreprocessor preprocessor;
	private T operation;
	private T unscaledOperation;

	private Mat resultMat = new Mat();

	private int index = -1;
	private boolean scaled;

	public PreprocessorOperation(ImagePreprocessor preprocessor, T operation) {
		this.preprocessor = preprocessor;
		this.operation = operation;
	}

	public PreprocessorOperation(int index, ImagePreprocessor preprocessor, T operation) {
		this.preprocessor = preprocessor;
		this.operation = operation;
		this.index = index;
	}

	public void apply() {
		PreprocessorOperation prev = previous();

		if (prev == null) {
			operation.apply(preprocessor.getSource(), resultMat);
		} else {
			operation.apply(prev.getResult(), resultMat);
		}
	}

	public Mat getResult() {
		return resultMat;
	}

	public boolean scale(double value) {
		if (scaled) {
			return false;
		}

		unscaledOperation = operation.copy();
		operation.scale(value);
		scaled = true;
		return true;
	}

	public boolean unscale() {
		if (!scaled) {
			return false;
		}

		operation = unscaledOperation;
		scaled = false;
		return true;
	}

	private PreprocessorOperation previous() {
		if (index <= 0) {
			return null;
		}

		return preprocessor.getOperation(index - 1);
	}

	public OperationType getType() {
		return operation.getType();
	}

	public ImagePreprocessor getPreprocessor() {
		return preprocessor;
	}

	public T getImageOperation() {
		return operation;
	}

	public void setOperation(T operation) {
		this.operation = operation;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isScaled() {
		return scaled;
	}
}
