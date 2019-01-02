package main.preprocess;

import main.preprocess.operations.*;
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

	private int lastOperationIndex = 0;

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
			operations.add(wrapOperation(i, operationTypes.get(i)));
		}
	}

	public void fullProcess() {
		applyOperationsFrom(0);
	}

	public void finishProcessing() {
		if (lastOperationIndex == operations.size() - 1) {
			return;
		}

		applyOperationsFrom(lastOperationIndex + 1);
	}

	public void applyOperationsFrom(int operationIndex) {
		for (int i = operationIndex; i < operations.size(); i++) {
			applyOperation(i);
		}
	}

	public void applyOperationFromTo(int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			applyOperation(i);
		}
	}

	public void applySingleOperation(int operationIndex) {
		applyOperation(operationIndex);
	}

	public void applyOperationsFrom(PreprocessorOperation operation) {
		applyOperationsFrom(operation.getIndex());
	}

	public void applyOperationFromTo(PreprocessorOperation start, PreprocessorOperation end) {
		applyOperationFromTo(start.getIndex(), end.getIndex());
	}

	public void applySingleOperation(PreprocessorOperation operation) {
		applyOperation(operation.getIndex());
	}

	private void applyOperation(int operationIndex) {
		if (sourceMat == null) {
			throw new IllegalStateException("No source image is provided.");
		}

		if (operationIndex < 0 || operationIndex >= operations.size()) {
			throw new IllegalArgumentException(String.format("Operation index %d is out of range", operationIndex));
		}

		getOperation(operationIndex).apply();
		lastOperationIndex = operationIndex;
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

	public void addOperation(int index, OperationType operationType) {
		operations.add(index, wrapOperation(index, operationType));

		for (int i = index; i < operations.size(); i++) {
			operations.get(i).setIndex(i);
		}

		applyOperationsFrom(index);
	}

	public void removeOperation(int index) {
		operations.remove(index);

		for (int i = index; i < operations.size(); i++) {
			operations.get(i).setIndex(i);
		}

		applyOperationsFrom(index);
	}

	public void setOperations(List<OperationType> operations) {
		initOperations(operations);
	}

	public Mat getMat(int operationIndex) {
		if (operationIndex == -1) {
			return sourceMat.clone();
		}

		return operations.get(operationIndex).getResult();
	}

	public Mat getMat(PreprocessorOperation operation) {
		return getMat(operation.getIndex());
	}

	private <T extends ImageOperation<T>> PreprocessorOperation<T> wrapOperation(int index, OperationType type) {
		T operation = type.getInstance();
		return new PreprocessorOperation<>(index, this, operation);
	}

	public PreprocessorOperation getOperation(int operationIndex) {
		return operations.get(operationIndex);
	}

	public <T extends PreprocessorOperation> T getOperation(int operationIndex, Class<T> clazz) {
		return clazz.cast(getOperation(operationIndex));
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
