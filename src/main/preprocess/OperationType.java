package main.preprocess;

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

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
