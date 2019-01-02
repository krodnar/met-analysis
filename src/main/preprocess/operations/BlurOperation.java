package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BlurOperation extends AbstractOperation<BlurOperation> {

    private int kernelSize = 51;

    public BlurOperation() {
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.medianBlur(src, dst, kernelSize);
    }

	@Override
	public OperationType getType() {
		return OperationType.BLUR;
	}

	@Override
	public void scale(double value) {
		kernelSize *= value;

		if (kernelSize % 2 != 1) {
			kernelSize += 1;
		}
	}

	@Override
	public BlurOperation copy() {
		BlurOperation operation = new BlurOperation();
		operation.kernelSize = kernelSize;
		return operation;
	}

	public int getKernelSize() {
        return kernelSize;
    }

    public void setKernelSize(int kernelSize) {
        this.kernelSize = kernelSize;
    }
}
