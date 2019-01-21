package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;

public interface ImageOperation {

	void apply(Mat src, Mat dst);

	Mat process(Mat mat);

	OperationType getType();

	void scale(double coefficient);

	void unscale();

	double getScaling();

	boolean isScaled();
}
