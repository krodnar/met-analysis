package main.preprocess.operations;

import org.opencv.core.Mat;

public abstract class AbstractOperation<T extends PreprocessorOperation<T>> implements PreprocessorOperation<T> {

	private int index = -1;

	public AbstractOperation() {
	}

	public AbstractOperation(int index) {
		this.index = index;
	}

	public boolean isQueued() {
		return index != -1;
	}

	@Override
	public Mat process(Mat mat) {
		Mat temp = mat.clone();
		apply(mat, temp);
		return temp;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}
}
