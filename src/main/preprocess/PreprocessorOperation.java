package main.preprocess;

import main.preprocess.operations.ImageOperation;
import main.preprocess.operations.ObservableOperation;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PreprocessorOperation<T extends ImageOperation<T>> {

	private PreprocessorOperation parent;
	private PreprocessorOperation child;
	private T operation;

	private Mat sourceMat;
	private Mat unscaledSource = new Mat();
	private Mat resultMat = new Mat();

	private boolean scaled = false;
	private boolean processed = false;

	public PreprocessorOperation(T operation) {
		this.operation = operation;

		if (operation instanceof ObservableOperation) {
			((ObservableOperation) this.operation).addObserver(op -> setProcessed(false));
		}
	}

	public void process() {
		if (isProcessed()) {
			return;
		}

		operation.apply(sourceMat, resultMat);

		if (child != null) {
			child.setSource(resultMat);
		}

		processed = true;
	}

	public void apply() {
		process();
		if (child != null) {
			child.apply();
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

		unscaledSource = sourceMat.clone();
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

	public T getImageOperation() {
		return operation;
	}

	public void setOperation(T operation) {
		this.operation = operation;
	}

	public boolean isScaled() {
		return scaled;
	}

	public PreprocessorOperation getChild() {
		return child;
	}

	public void setChild(PreprocessorOperation child) {
		this.child = child;

		if (child != null) {
			child.parent = this;
			child.setSource(resultMat);
		}
	}

	public PreprocessorOperation getParent() {
		return parent;
	}

	public void setParent(PreprocessorOperation parent) {
		this.parent = parent;

		if (parent != null) {
			parent.child = this;
		}
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;

		if (!processed && child != null) {
			child.setProcessed(false);
		}
	}
}
