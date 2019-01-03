package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveThresholdOperation extends AbstractOperation<AdaptiveThresholdOperation> {

    private int adaptiveMethod = Imgproc.ADAPTIVE_THRESH_MEAN_C;
    private int thresholdType = Imgproc.THRESH_BINARY;
    private int blockSize;
    private double subConstant;

    public AdaptiveThresholdOperation() {
    }

    public AdaptiveThresholdOperation(AdaptiveThresholdOperation operation) {
        this.adaptiveMethod = operation.adaptiveMethod;
        this.thresholdType = operation.thresholdType;
        this.blockSize = operation.blockSize;
        this.subConstant = operation.subConstant;
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.adaptiveThreshold(src, dst, 255, adaptiveMethod, thresholdType, blockSize, subConstant);
    }

	@Override
	public OperationType getType() {
		return OperationType.THRESHOLD;
	}

    @Override
    protected void scaleParameters(double value) {
        scaleBlockSize(value);
    }

    private void scaleBlockSize(double value) {
        blockSize *= value;
    }

    @Override
    public AdaptiveThresholdOperation copy() {
        return new AdaptiveThresholdOperation(this);
    }

    public int getAdaptiveMethod() {
        return adaptiveMethod;
    }

    public void setAdaptiveMethod(int adaptiveMethod) {
        this.adaptiveMethod = adaptiveMethod;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
        scaleBlockSize(getScaleValue());
    }

    public double getSubConstant() {
        return subConstant;
    }

    public void setSubConstant(double subConstant) {
        this.subConstant = subConstant;
    }
}
