package main.preprocess;

import main.preprocess.operations.GrayScaleOperation;
import main.preprocess.operations.ImageOperation;
import org.opencv.core.Mat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static main.preprocess.OperationType.*;

public class ImagePreprocessor {

	private Mat sourceMat;
	private List<PreprocessorOperation> operations = new LinkedList<>();

	private List<OperationType> operationsOrder = Arrays.asList(
			GRAYSCALE,
			CLAHE,
			THRESHOLD,
			MORPHOLOGY,
			BLUR
	);

	public ImagePreprocessor() {
		initOperations(operationsOrder);
	}

	public ImagePreprocessor(Mat sourceMat) {
		this(sourceMat, true);
	}

	public ImagePreprocessor(Mat sourceMat, boolean process) {
		this.sourceMat = sourceMat;

		initOperations(operationsOrder);
		operations.get(0).setSource(sourceMat.clone());

		if (process) {
			process();
		}
	}

	private void initOperations(List<OperationType> operationTypes) {
		for (int i = 0; i < operationTypes.size(); i++) {
			operations.add(wrapOperation(operationTypes.get(i)));
		}

		for (int i = 0; i < operations.size() - 1; i++) {
			PreprocessorOperation current = operations.get(i);
			PreprocessorOperation next = operations.get(i + 1);
			current.setChild(next);
		}
	}

	public void reprocess() {
		PreprocessorOperation operation = operations.get(0);
		operation.setProcessed(false);
		operation.apply();
	}

	public void process() {
		operations.get(0).apply();
	}

	public void addOperation(int index, PreprocessorOperation operation) {
		PreprocessorOperation curr = operations.get(index);
		operation.setChild(curr);

		if (operations.size() > 1) {
			PreprocessorOperation prev = operations.get(index - 1);
			prev.setChild(operation);
		}

		operations.add(index, operation);

		operation.setProcessed(false);
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
			next.setProcessed(false);
		}

		if (prev != null) {
			prev.setChild(next);
		}

		operations.remove(operation);
	}

	public void setOperations(List<OperationType> operations) {
		initOperations(operations);
	}

	private <T extends ImageOperation<T>> PreprocessorOperation<T> wrapOperation(OperationType type) {
		T operation = type.getInstance();
		return new PreprocessorOperation<>(operation);
	}

	public List<PreprocessorOperation> getOperations() {
		return operations;
	}

	public void setSourceMat(Mat sourceMat) {
		setSourceMat(sourceMat, true);
	}

	public void setSourceMat(Mat sourceMat, boolean process) {
		this.sourceMat = sourceMat;
		operations.get(0).setSource(sourceMat.clone());

		if (process) {
			process();
		}
	}

	public Mat getSource() {
		return sourceMat.clone();
	}

	public Mat getProcessedMat() {
		return operations.get(operations.size() - 1).getResult().clone();
	}

	public boolean isReady() {
		return sourceMat != null;
	}
}
