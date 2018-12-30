package main.preprocess;

import main.preprocess.operations.PreprocessorOperation;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static main.preprocess.OperationType.*;

public class ImagePreprocessor {

	private Mat sourceMat;
	private List<Mat> matrices = new LinkedList<>();
	private List<PreprocessorOperation> operations = new LinkedList<>();

	private int lastOperationIndex = 0;

	private boolean scaled;
	private Mat unscaledMat;
	private List<PreprocessorOperation> unscaledOperations = new LinkedList<>();

	private List<OperationType> operationsOrder = new ArrayList<>(Arrays.asList(
			GRAYSCALE,
			CLAHE,
			THRESHOLD,
			MORPHOLOGY,
			BLUR
	));

	public ImagePreprocessor() {
		this(null, false);
	}

	public ImagePreprocessor(Mat sourceMat) {
		this(sourceMat, true);
	}

	public ImagePreprocessor(Mat sourceMat, boolean process) {
		this.sourceMat = sourceMat;

		init();

		if (process) {
			fullProcess();
		}
	}

	private void init() {
		for (int i = 0; i < operationsOrder.size(); i++) {
			matrices.add(new Mat());
			operations.add(i, operationsOrder.get(i).createOperation(i));
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
		for (int i = operationIndex; i < operationsOrder.size(); i++) {
			applyOperation(i);
		}
	}

	public void applyOperationsFrom(PreprocessorOperation operation) {
		applyOperationsFrom(operation.getIndex());
	}

	public void applyOperationFromTo(int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			applyOperation(i);
		}
	}

	public void applyOperationsFromTo(PreprocessorOperation startOperation, PreprocessorOperation endOperation) {
		applyOperationFromTo(startOperation.getIndex(), endOperation.getIndex());
	}

	public void applySingleOperation(int operationIndex) {
		applyOperation(operationIndex);
	}

	public void applySingleOperation(PreprocessorOperation operation) {
		applySingleOperation(operation.getIndex());
	}

	private void applyOperation(int operationIndex) {
		if (sourceMat == null) {
			throw new IllegalStateException("No source image is provided.");
		}

		if (operationIndex < 0 || operationIndex >= operations.size()) {
			throw new IllegalArgumentException(String.format("Operation index %d is out of range", operationIndex));
		}

		getOperation(operationIndex).apply(getMat(operationIndex - 1), getMat(operationIndex));
		lastOperationIndex = operationIndex;
	}

	public void scale(double value) {
		if (scaled) {
			throw new IllegalStateException("Image preprocessor is already scaled.");
		}

		unscaledMat = sourceMat.clone();
		unscaledOperations.clear();
		Imgproc.resize(sourceMat, sourceMat, new Size(), value, value);

		for (PreprocessorOperation operation : operations) {
			unscaledOperations.add(operation.copy());
			operation.scale(value);
		}

		scaled = true;
	}

	public void unscale() {
		sourceMat = unscaledMat.clone();
		operations = new LinkedList<>(unscaledOperations);

		scaled = false;
	}

	public boolean isScaled() {
		return scaled;
	}

	public boolean isReady() {
		return sourceMat != null;
	}

	public void addOperation(int index, OperationType operationType) {
		operations.add(index, operationType.createOperation(index));
		matrices.add(index, new Mat());

		for (int i = index; i < operations.size(); i++) {
			operations.get(i).setIndex(i);
		}

		applyOperationsFrom(index);
	}

	public void removeOperation(int index) {
		operations.remove(index);
		matrices.remove(index);

		for (int i = index; i < operations.size(); i++) {
			operations.get(i).setIndex(i);
		}

		applyOperationsFrom(index);
	}

	public void setOperationsOrder(List<OperationType> operationsOrder) {
		setOperationsOrder(operationsOrder, true);
	}

	public void setOperationsOrder(List<OperationType> operationsOrder, boolean process) {
		this.operationsOrder = operationsOrder;
		operations.clear();
		matrices.clear();
		init();

		if (process) {
			fullProcess();
		}
	}

	public Mat getMat(int operationIndex) {
		if (operationIndex == -1) {
			return sourceMat.clone();
		}

		return matrices.get(operationIndex);
	}

	public Mat getMat(PreprocessorOperation operation) {
		return getMat(operation.getIndex());
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

	public List<OperationType> getOperationsOrder() {
		return operationsOrder;
	}

	public Mat getProcessedMat() {
		return matrices.get(matrices.size() - 1).clone();
	}
}
