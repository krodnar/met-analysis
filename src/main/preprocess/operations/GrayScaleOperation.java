package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class GrayScaleOperation extends AbstractOperation {

    public GrayScaleOperation() {
    }

    public GrayScaleOperation(int index) {
        super(index);
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
    }

	@Override
	public OperationType getType() {
		return OperationType.GRAYSCALE;
	}
}
