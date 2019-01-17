package main.preprocess.parameters;

public interface OperationParameter<T> {

	T get();

	void set(T value);
}
