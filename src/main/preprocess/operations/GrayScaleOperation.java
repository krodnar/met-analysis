package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class GrayScaleOperation extends AbstractOperation<GrayScaleOperation> {

    public GrayScaleOperation() {
    }

	public GrayScaleOperation(GrayScaleOperation operation) {
	}

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
    }

	@Override
	public OperationType getType() {
		return OperationType.GRAYSCALE;
	}

	@Override
	protected void scaleParameters(double coefficient) {

	}

	@Override
	protected void unscaleParameters(double coefficient) {

	}

	@Override
	public GrayScaleOperation copy() {
		return new GrayScaleOperation(this);
	}
}
