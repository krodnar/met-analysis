package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ThresholdOperation extends AbstractOperation<ThresholdOperation> {

    private int thresholdType = Imgproc.THRESH_BINARY;
    private double threshold;

    public ThresholdOperation() {
        threshold = getOptimalThresh();
    }

    public ThresholdOperation(ThresholdOperation operation) {
        this.thresholdType = operation.thresholdType;
        this.threshold = operation.threshold;
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.threshold(src, dst, threshold, 255, thresholdType);
    }

	@Override
	public OperationType getType() {
		return OperationType.THRESHOLD;
	}

    @Override
    protected void scaleParameters(double coefficient) {

    }

    @Override
    protected void unscaleParameters(double coefficient) {

    }

    @Override
    public ThresholdOperation copy() {
        return new ThresholdOperation(this);
    }

    public void setOptimalThresh() {
        threshold = getOptimalThresh();
    }

    public double getOptimalThresh() {
        return 110;
    }

    public double setOptimalThresh(Mat mat) {
        return getOptimalThresh(mat);
    }

    public double getOptimalThresh(Mat mat) {
        return 110;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
