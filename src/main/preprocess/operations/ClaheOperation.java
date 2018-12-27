package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

public class ClaheOperation extends AbstractOperation {

    public ClaheOperation() {
    }

    public ClaheOperation(int index) {
        super(index);
    }

    private final CLAHE clahe = Imgproc.createCLAHE(2, new Size(8, 8));

    @Override
    public void apply(Mat src, Mat dst) {
        clahe.apply(src, dst);
    }

	@Override
	public OperationType getType() {
		return OperationType.CLAHE;
	}

	public void setTilesSize(Size tilesSize) {
        clahe.setTilesGridSize(tilesSize);
    }

    public void setClipLimit(double clipLimit) {
        clahe.setClipLimit(clipLimit);
    }

    public Size getTilesSize() {
        return clahe.getTilesGridSize();
    }

    public double getClipLimit() {
        return clahe.getClipLimit();
    }
}
