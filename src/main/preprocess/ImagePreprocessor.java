package main.preprocess;

import javafx.scene.image.Image;
import main.preprocess.operations.PreprocessorOperation;
import main.utils.Utils;
import org.opencv.core.Mat;

import java.util.*;

import static main.preprocess.OperationType.*;

public class ImagePreprocessor {

	private Mat sourceMat;
	private List<Mat> matrices = new LinkedList<>();
	private List<PreprocessorOperation> operations = new LinkedList<>();

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

	public Image getImage(int operationIndex) {
		return Utils.mat2Image(getMat(operationIndex));
	}

	public Image getImage(PreprocessorOperation operation) {
		return getImage(operation.getIndex());
	}

	public Image getSourceImage() {
		return Utils.mat2Image(getSource());
	}

	public List<OperationType> getOperationsOrder() {
		return operationsOrder;
	}

	public Image getProcessedImage() {
		return Utils.mat2Image(getProcessedMat());
	}

	public Mat getProcessedMat() {
		return matrices.get(matrices.size() - 1).clone();
	}
}
