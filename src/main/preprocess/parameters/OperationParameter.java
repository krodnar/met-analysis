package main.preprocess.parameters;

public interface OperationParameter<T> {

	T getValue();

	void setValue(T value);
}
