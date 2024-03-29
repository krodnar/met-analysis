package main.preprocess.operations;

import main.preprocess.OperationType;
import org.opencv.core.Mat;

public interface PreprocessorOperation {

	void apply(Mat src, Mat dst);

	Mat process(Mat mat);

	int getIndex();

	void setIndex(int index);

	OperationType getType();
}
