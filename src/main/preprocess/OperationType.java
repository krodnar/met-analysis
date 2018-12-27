package main.preprocess;

import main.preprocess.operations.*;

public enum OperationType {

	GRAYSCALE("Grayscale"),
	CLAHE("CLAHE"),
	ADAPTIVE_THRESHOLD("Adaptive Threshold"),
	THRESHOLD("Threshold"),
	MORPHOLOGY("Morphological"),
	BLUR("Blur");

	private String name;

	OperationType(String name) {
		this.name = name;
	}

	public static PreprocessorOperation createOperation(OperationType operationType) {
		switch (operationType) {

			case GRAYSCALE:
				return new GrayScaleOperation();
			case CLAHE:
				return new ClaheOperation();
			case ADAPTIVE_THRESHOLD:
				return new AdaptiveThresholdOperation();
			case THRESHOLD:
				return new ThresholdOperation();
			case MORPHOLOGY:
				return new MorphologicalOperation();
			case BLUR:
				return new BlurOperation();
			default:
				return null;
		}
	}

	public PreprocessorOperation createOperation(int index) {
		PreprocessorOperation operation = OperationType.createOperation(this);
		if (operation != null) {
			operation.setIndex(index);
		}
		return operation;
	}

	public PreprocessorOperation createOperation() {
		return OperationType.createOperation(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
