package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ThresholdOperation extends AbstractOperation {

    private int thresholdType = Imgproc.THRESH_BINARY;
    private double threshold;

    public ThresholdOperation() {
        threshold = getOptimalThresh();
    }

    public ThresholdOperation(int index) {
        super(index);
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.threshold(src, dst, threshold, 255, thresholdType);
    }

	@Override
	public OperationType getType() {
		return OperationType.THRESHOLD;
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
