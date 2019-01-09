package main.preprocess.operations;

import org.opencv.core.Mat;

public abstract class AbstractOperation<T extends ImageOperation<T>> implements ImageOperation<T> {

	private boolean scaled;
	private double scaleValue = 1;

	@Override
	public Mat process(Mat mat) {
		Mat temp = mat.clone();
		apply(mat, temp);
		return temp;
	}

	@Override
	public void scale(double value) {
		scaleValue = value;
		scaled = true;
		scaleParameters(value);
	}

	protected abstract void scaleParameters(double value);

	@Override
	public void unscale() {
		scale(1 / scaleValue);
		scaleValue = 1;
		scaled = false;
	}

	@Override
	public double getScaleValue() {
		return scaleValue;
	}

	@Override
	public boolean isScaled() {
		return scaled;
	}
}
