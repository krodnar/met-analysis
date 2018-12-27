package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BlurOperation extends AbstractOperation {

    private int kernelSize = 51;

    public BlurOperation() {
    }

    public BlurOperation(int index) {
        super(index);
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.medianBlur(src, dst, kernelSize);
    }

	@Override
	public OperationType getType() {
		return OperationType.BLUR;
	}

	public int getKernelSize() {
        return kernelSize;
    }

    public void setKernelSize(int kernelSize) {
        this.kernelSize = kernelSize;
    }
}
