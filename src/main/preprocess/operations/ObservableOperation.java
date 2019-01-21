package main.preprocess.operations;

public interface ObservableOperation<T extends ImageOperation> extends ImageOperation {

	void addObserver(OperationObserver observer);

	void removeObserver(OperationObserver observer);
}
