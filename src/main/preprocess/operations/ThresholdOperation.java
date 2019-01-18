package main.preprocess.operations;

import main.preprocess.OperationType;
import main.preprocess.parameters.DoubleParameter;
import main.preprocess.parameters.IntParameter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ThresholdOperation extends AbstractOperation<ThresholdOperation> {

    private IntParameter thresholdType = new IntParameter(Imgproc.THRESH_BINARY_INV);
    private DoubleParameter threshold = new DoubleParameter();

    public ThresholdOperation() {
        threshold.set(getOptimalThresh());

        thresholdType.setListener((parameter, oldValue, newValue) -> fireOnChange());
        threshold.setListener((parameter, oldValue, newValue) -> fireOnChange());
    }

    public ThresholdOperation(ThresholdOperation operation) {
        this.thresholdType.set(operation.thresholdType.get());
        this.threshold.set(operation.threshold.get());
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.threshold(src, dst, threshold.get(), 255, thresholdType.get());
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

	public void setOptimalThresh() {
        threshold.set(getOptimalThresh());
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
        return thresholdType.get();
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType.set(thresholdType);
    }

    public double getThreshold() {
        return threshold.get();
    }

    public void setThreshold(double threshold) {
        this.threshold.set(threshold);
    }
}
