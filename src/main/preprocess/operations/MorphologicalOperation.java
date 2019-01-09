package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MorphologicalOperation extends AbstractOperation<MorphologicalOperation> {

    private Size crossSize;
    private Size ellipseSize;

    private Mat kernelCross;
    private Mat kernelEllipse;

    public MorphologicalOperation() {
        setEllipseSize(new Size(18, 18));
        setCrossSize(new Size(30, 30));
    }

    public MorphologicalOperation(MorphologicalOperation operation) {
        this.crossSize = operation.crossSize.clone();
        this.ellipseSize = operation.ellipseSize.clone();
        this.kernelCross = operation.kernelCross.clone();
        this.kernelEllipse = operation.kernelEllipse.clone();
    }

    @Override
    public void apply(Mat src, Mat dst) {
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_ERODE, kernelEllipse);
        Imgproc.morphologyEx(dst, dst, Imgproc.MORPH_DILATE, kernelCross);
    }

	@Override
	public OperationType getType() {
		return OperationType.MORPHOLOGY;
	}

    @Override
    protected void scaleParameters(double value) {
        scaleCrossSize(value);
        scaleEllipseSize(value);
    }

    private void scaleEllipseSize(double value) {
        ellipseSize.width = ellipseSize.width * value;
        ellipseSize.height = ellipseSize.height * value;
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize);
    }

    private void scaleCrossSize(double value) {
        crossSize.width = crossSize.width * value;
        crossSize.height = crossSize.height * value;
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize);
    }

    @Override
    public MorphologicalOperation copy() {
        return new MorphologicalOperation(this);
    }

    public void setEllipseSize(Size size) {
        ellipseSize = size;
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize);
        scaleEllipseSize(getScaleValue());
    }

    public void setCrossSize(Size size) {
        crossSize = size;
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize);
        scaleCrossSize(getScaleValue());
    }

    public Size getCrossSize() {
        return crossSize;
    }

    public Size getEllipseSize() {
        return ellipseSize;
    }
}
