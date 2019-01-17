package main.preprocess.operations;

public interface ObservableOperation<T extends ImageOperation<T>> extends ImageOperation<T> {

	void addObserver(OperationObserver observer);

	void removeObserver(OperationObserver observer);
}
