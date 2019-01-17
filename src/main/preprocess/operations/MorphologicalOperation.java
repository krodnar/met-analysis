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
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize.get());
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize.get());
    }

    @Override
    protected void unscaleParameters(double coefficient) {
        crossSize.unscale();
        ellipseSize.unscale();
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize.get());
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize.get());
    }

    @Override
    public MorphologicalOperation copy() {
        return new MorphologicalOperation(this);
    }

    public void setEllipseSize(Size size) {
        ellipseSize.set(size);
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize.get());
    }

    public void setCrossSize(Size size) {
        crossSize.set(size);
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize.get());
    }

    public Size getCrossSize() {
        return crossSize.get();
    }

    public Size getEllipseSize() {
        return ellipseSize.get();
    }
}
