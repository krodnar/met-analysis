package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveThresholdOperation extends AbstractOperation {

    private int adaptiveMethod = Imgproc.ADAPTIVE_THRESH_MEAN_C;
    private int thresholdType = Imgproc.THRESH_BINARY;
    private int blockSize;
    private double subConstant;

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.adaptiveThreshold(src, dst, 255, adaptiveMethod, thresholdType, blockSize, subConstant);
    }

	@Override
	public OperationType getType() {
		return OperationType.THRESHOLD;
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
    }

    public double getSubConstant() {
        return subConstant;
    }

    public void setSubConstant(double subConstant) {
        this.subConstant = subConstant;
    }
}
