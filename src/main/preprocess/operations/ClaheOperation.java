package main.preprocess.operations;

import main.preprocess.OperationType;
import main.preprocess.parameters.SizeParameter;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

public class ClaheOperation extends AbstractOperation<ClaheOperation> {

    private CLAHE clahe;
    private SizeParameter tileSize;

    public ClaheOperation() {
        tileSize = new SizeParameter(new Size(8, 8));
        clahe = Imgproc.createCLAHE(2, tileSize.get());
    }

    public ClaheOperation(ClaheOperation operation) {
        this.clahe = Imgproc.createCLAHE(operation.getClipLimit(), operation.getTilesSize().clone());
    }

    @Override
    public void apply(Mat src, Mat dst) {
        clahe.apply(src, dst);
    }

	@Override
	public OperationType getType() {
		return OperationType.CLAHE;
	}

    @Override
    protected void scaleParameters(double coefficient) {
        tileSize.scale(coefficient);
        clahe.setTilesGridSize(tileSize.get());
    }

    @Override
    protected void unscaleParameters(double coefficient) {
        tileSize.unscale();
        clahe.setTilesGridSize(tileSize.get());
    }

    @Override
    public ClaheOperation copy() {
        return new ClaheOperation(this);
    }

    public void setTilesSize(Size tilesSize) {
        this.tileSize.set(tilesSize);
        clahe.setTilesGridSize(this.tileSize.get());
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
