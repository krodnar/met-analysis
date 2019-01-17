package main.preprocess;

import main.preprocess.operations.ImageOperation;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static main.preprocess.OperationType.*;

public class ImagePreprocessor {

	private Mat sourceMat;
	private List<PreprocessorOperation> operations = new LinkedList<>();

	private boolean scaled;
	private Mat unscaledMat;

	private List<OperationType> operationsOrder = Arrays.asList(
			GRAYSCALE,
			CLAHE,
			THRESHOLD,
			MORPHOLOGY,
			BLUR
	);

	public ImagePreprocessor() {
		this(null, false);
	}

	public ImagePreprocessor(Mat sourceMat) {
		this(sourceMat, true);
	}

	public ImagePreprocessor(Mat sourceMat, boolean process) {
		this.sourceMat = sourceMat;

		initOperations(operationsOrder);

		if (process) {
			fullProcess();
		}
	}

	private void initOperations(List<OperationType> operationTypes) {
		for (int i = 0; i < operationTypes.size(); i++) {
			operations.add(wrapOperation(operationTypes.get(i)));
		}

		for (int i = 0; i < operations.size() - 1; i++) {
			PreprocessorOperation current = operations.get(i);
			PreprocessorOperation next = operations.get(i + 1);
			current.setNext(next);
		}
	}

	public void fullProcess() {
		applyOperationsFrom(operations.get(0));
	}

	public void finishProcessing() {
		fullProcess();
	}

	public void applyOperationsFrom(PreprocessorOperation operation) {
		operation.apply();
	}

	public void scale(double value) {
		if (scaled) {
			throw new IllegalStateException("Image preprocessor is already scaled.");
		}

		unscaledMat = sourceMat.clone();
		Imgproc.resize(sourceMat, sourceMat, new Size(), value, value);

		for (PreprocessorOperation operation : operations) {
			operation.scale(value);
		}

		fullProcess();
		scaled = true;
	}

	public void unscale() {
		if (!scaled) {
			return;
		}

		sourceMat = unscaledMat.clone();

		for (PreprocessorOperation operation : operations) {
			operation.unscale();
		}

		fullProcess();
		scaled = false;
	}

	public void addOperation(int index, PreprocessorOperation operation) {
		PreprocessorOperation curr = operations.get(index);
		operation.setNext(curr);

		if (operations.size() > 1) {
			PreprocessorOperation prev = operations.get(index - 1);
			prev.setNext(operation);
		}

		operations.add(index, operation);

		applyOperationsFrom(operation);
	}

	public void removeOperation(PreprocessorOperation operation) {
		int index = operations.indexOf(operation);

		PreprocessorOperation prev = null;
		if (index > 0) {
			prev = operations.get(index - 1);
		}

		PreprocessorOperation next = null;
		if (index + 1 < operations.size()) {
			next = operations.get(index + 1);
		}

		if (prev != null) {
			prev.setNext(next);
		}

		operations.remove(operation);

		applyOperationsFrom(next);
	}

	public void setOperations(List<OperationType> operations) {
		initOperations(operations);
	}

	public Mat getMat(PreprocessorOperation operation) {
		return operation.getResult();
	}

	private <T extends ImageOperation<T>> PreprocessorOperation<T> wrapOperation(OperationType type) {
		T operation = type.getInstance();
		return new PreprocessorOperation<>(this, operation);
	}

	public List<PreprocessorOperation> getOperations() {
		return operations;
	}

	public List<OperationType> getOperationsOrder() {
		return operationsOrder;
	}

	public void setSourceMat(Mat sourceMat) {
		setSourceMat(sourceMat, true);
	}

	public void setSourceMat(Mat sourceMat, boolean process) {
		this.sourceMat = sourceMat;

		if (process) {
			fullProcess();
		}
	}

	public Mat getSource() {
		return sourceMat.clone();
	}

	public Mat getProcessedMat() {
		return operations.get(operations.size() - 1).getResult().clone();
	}

	public boolean isScaled() {
		return scaled;
	}

	public boolean isReady() {
		return sourceMat != null;
	}
}
