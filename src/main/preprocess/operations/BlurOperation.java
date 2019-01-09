package main.preprocess.operations;

import main.preprocess.OperationType;
import main.preprocess.parameters.OddIntParameter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BlurOperation extends AbstractOperation<BlurOperation> {

	private OddIntParameter kernelSize = new OddIntParameter(51);

	public BlurOperation() {
	}

	public BlurOperation(BlurOperation operation) {
		this.kernelSize = operation.kernelSize;
	}

	@Override
	public void apply(Mat src, Mat dst) {
		Imgproc.medianBlur(src, dst, kernelSize.getValue());
	}

	@Override
	public OperationType getType() {
		return OperationType.BLUR;
	}

	@Override
	protected void scaleParameters(double coefficient) {
		kernelSize.scale(coefficient);
	}

	@Override
	protected void unscaleParameters(double coefficient) {
		kernelSize.unscale();
	}

	@Override
	public BlurOperation copy() {
		return new BlurOperation(this);
	}

	public int getKernelSize() {
		return kernelSize.getValue();
	}

	public void setKernelSize(int kernelSize) {
		this.kernelSize.setValue(kernelSize);
	}
}
