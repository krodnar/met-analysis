package main.preprocess.operations;

import main.preprocess.OperationType;
import main.preprocess.parameters.DoubleParameter;
import main.preprocess.parameters.IntParameter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveThresholdOperation extends AbstractOperation<AdaptiveThresholdOperation> {

    private IntParameter blockSize;
    private IntParameter adaptiveMethod;
    private IntParameter thresholdType;
    private DoubleParameter subConstant;

    public AdaptiveThresholdOperation() {
		blockSize = new IntParameter(20, "block");
		blockSize.setListener((parameter, oldValue, newValue) -> fireOnChange());

		adaptiveMethod = new IntParameter(Imgproc.ADAPTIVE_THRESH_MEAN_C, "method");
		adaptiveMethod.setListener((parameter, oldValue, newValue) -> fireOnChange());

		thresholdType = new IntParameter(Imgproc.THRESH_BINARY, "type");
		thresholdType.setListener((parameter, oldValue, newValue) -> fireOnChange());

		subConstant = new DoubleParameter(0d, "constant");
		subConstant.setListener((parameter, oldValue, newValue) -> fireOnChange());
	}

    public AdaptiveThresholdOperation(AdaptiveThresholdOperation operation) {
		this();
		this.adaptiveMethod.set(operation.adaptiveMethod.get());
		this.thresholdType.set(operation.thresholdType.get());
		this.blockSize.set(operation.blockSize.get());
		this.subConstant.set(operation.subConstant.get());
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.adaptiveThreshold(src, dst, 255, adaptiveMethod.get(), thresholdType.get(), blockSize.get(), subConstant.get());
    }

	@Override
	public OperationType getType() {
		return OperationType.THRESHOLD;
	}

    @Override
    protected void scaleParameters(double coefficient) {
        blockSize.scale(coefficient);
    }

    @Override
    protected void unscaleParameters(double coefficient) {
        blockSize.unscale();
    }

    @Override
    public AdaptiveThresholdOperation copy() {
        return new AdaptiveThresholdOperation(this);
    }

    public int getAdaptiveMethod() {
        return adaptiveMethod.get();
    }

    public void setAdaptiveMethod(int adaptiveMethod) {
        this.adaptiveMethod.set(adaptiveMethod);
    }

    public int getThresholdType() {
        return thresholdType.get();
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType.set(thresholdType);
    }

    public int getBlockSize() {
        return blockSize.get();
    }

    public void setBlockSize(int blockSize) {
        this.blockSize.set(blockSize);
    }

    public double getSubConstant() {
        return subConstant.get();
    }

    public void setSubConstant(double subConstant) {
        this.subConstant.set(subConstant);
    }
}
