package main.preprocess.operations;

import main.preprocess.OperationType;
import main.preprocess.parameters.SizeParameter;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MorphologicalOperation extends AbstractOperation<MorphologicalOperation> {

    private SizeParameter crossSize = new SizeParameter();
    private SizeParameter ellipseSize = new SizeParameter();

    private Mat kernelCross;
    private Mat kernelEllipse;

    public MorphologicalOperation() {
        setEllipseSize(new Size(18, 18));
        setCrossSize(new Size(30, 30));
    }

    public MorphologicalOperation(MorphologicalOperation operation) {
        this.crossSize = new SizeParameter(operation.crossSize);
        this.ellipseSize = new SizeParameter(operation.ellipseSize);
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
    protected void scaleParameters(double coefficient) {
        crossSize.scale(coefficient);
        ellipseSize.scale(coefficient);
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize.getValue());
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize.getValue());
    }

    @Override
    protected void unscaleParameters(double coefficient) {
        crossSize.unscale();
        ellipseSize.unscale();
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize.getValue());
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize.getValue());
    }

    @Override
    public MorphologicalOperation copy() {
        return new MorphologicalOperation(this);
    }

    public void setEllipseSize(Size size) {
        ellipseSize.setValue(size);
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize.getValue());
    }

    public void setCrossSize(Size size) {
        crossSize.setValue(size);
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize.getValue());
    }

    public Size getCrossSize() {
        return crossSize.getValue();
    }

    public Size getEllipseSize() {
        return ellipseSize.getValue();
    }
}
