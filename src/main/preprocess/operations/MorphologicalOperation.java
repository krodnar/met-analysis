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

    public MorphologicalOperation(int index) {
        super(index);
        setEllipseSize(new Size(18, 18));
        setCrossSize(new Size(30, 30));
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
    public MorphologicalOperation copy() {
        MorphologicalOperation operation = new MorphologicalOperation();
        operation.crossSize = crossSize;
        operation.ellipseSize = ellipseSize;
        operation.kernelCross = kernelCross;
        operation.kernelEllipse = kernelEllipse;
        return operation;
    }

    public void setEllipseSize(Size size) {
        ellipseSize = size;
        kernelEllipse = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, ellipseSize);
    }

    public void setCrossSize(Size size) {
        crossSize = size;
        kernelCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, crossSize);
    }

    public Size getCrossSize() {
        return crossSize;
    }

    public Size getEllipseSize() {
        return ellipseSize;
    }
}
