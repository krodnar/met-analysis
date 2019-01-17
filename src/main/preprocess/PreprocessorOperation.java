package main.preprocess;

import main.preprocess.operations.ImageOperation;
import main.preprocess.operations.ObservableOperation;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PreprocessorOperation<T extends ImageOperation<T>> {

	private ImagePreprocessor preprocessor;
	private PreprocessorOperation next;
	private T operation;

	private Mat sourceMat;
	private Mat unscaledSource = new Mat();
	private Mat resultMat = new Mat();

	private boolean scaled = false;
	private boolean processed = false;

	public PreprocessorOperation(ImagePreprocessor preprocessor, T operation) {
		this.preprocessor = preprocessor;
		this.operation = operation;

		if (operation instanceof ObservableOperation) {
			((ObservableOperation) this.operation).addObserver(op -> setProcessed(false));
		}
	}

	public void process() {
		if (isProcessed()) {
			return;
		}

		operation.apply(getSource(), resultMat);

		if (next != null) {
			next.setSource(resultMat);
		}

		processed = true;
		System.out.println("Applied " + operation.getType().toString());
	}

	public void apply() {
		process();
		if (next != null) {
			next.apply();
		}
	}

	public void setSource(Mat source) {
		this.sourceMat = source;
		setProcessed(false);
	}

	public Mat getResult() {
		return resultMat;
	}

	public boolean scale(double value) {
		if (scaled) {
			return false;
		}

		unscaledSource = getSource().clone();
		Imgproc.resize(sourceMat, sourceMat, new Size(), value, value);

		operation.scale(value);
		scaled = true;
		return true;
	}

	public boolean unscale() {
		if (!scaled) {
			return false;
		}

		sourceMat = unscaledSource.clone();
		unscaledSource.release();

		operation.unscale();
		scaled = false;
		return true;
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

	public boolean isScaled() {
		return scaled;
	}

	public PreprocessorOperation getNext() {
		return next;
	}

	public void setNext(PreprocessorOperation next) {
		this.next = next;
		next.setSource(resultMat);
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;

		if (!processed && next != null) {
			next.setProcessed(false);
		}
	}

	private Mat getSource() {
		return sourceMat == null ? preprocessor.getSource() : sourceMat;
	}
}
