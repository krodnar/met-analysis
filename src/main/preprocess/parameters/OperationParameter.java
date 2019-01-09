package main.preprocess.parameters;

public interface OperationParameter<T> {

	T getValue();

	void setValue(T value);

	void scale(double coefficient);

	void unscale();

	double getScaling();
}