package main.preprocess.operations;

import org.opencv.core.Mat;

public abstract class AbstractOperation<T extends ImageOperation<T>> implements ImageOperation<T> {

	private boolean scaled;
	private double scaleCoefficient = 1;

	@Override
	public Mat process(Mat mat) {
		Mat temp = mat.clone();
		apply(mat, temp);
		return temp;
	}

	@Override
	public void scale(double coefficient) {
		if (scaled) {
			return;
		}

		scaleCoefficient = coefficient;
		scaled = true;
		scaleParameters(coefficient);
	}

	@Override
	public void unscale() {
		if (!scaled) {
			return;
		}

		unscaleParameters(scaleCoefficient);
		scaleCoefficient = 1;
		scaled = false;
	}

	@Override
	public double getScaling() {
		return scaleCoefficient;
	}

	@Override
	public boolean isScaled() {
		return scaled;
	}

	protected abstract void scaleParameters(double coefficient);

	protected abstract void unscaleParameters(double coefficient);
}
