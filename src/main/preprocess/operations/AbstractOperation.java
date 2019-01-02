package main.preprocess.operations;

import org.opencv.core.Mat;

public abstract class AbstractOperation<T extends ImageOperation<T>> implements ImageOperation<T> {

	@Override
	public Mat process(Mat mat) {
		Mat temp = mat.clone();
		apply(mat, temp);
		return temp;
	}
}
