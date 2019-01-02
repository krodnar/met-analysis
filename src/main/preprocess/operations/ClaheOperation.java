package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

public class ClaheOperation extends AbstractOperation<ClaheOperation> {

    public ClaheOperation() {
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

    @Override
    public void scale(double value) {
        double height = clahe.getTilesGridSize().height;
        Size scaledSize = new Size(height * value, height * value);
        setTilesSize(scaledSize);
    }

    @Override
    public ClaheOperation copy() {
        ClaheOperation operation = new ClaheOperation();
        operation.setTilesSize(getTilesSize());
        operation.setClipLimit(getClipLimit());
        return operation;
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
