package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;

public interface ImageOperation<T extends ImageOperation<T>> {

	void apply(Mat src, Mat dst);

	Mat process(Mat mat);

	OperationType getType();

	void scale(double value);

	void unscale();

	double getScaleValue();

	boolean isScaled();

	T copy();
}
