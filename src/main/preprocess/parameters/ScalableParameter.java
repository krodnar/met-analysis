package main.preprocess.parameters;

public interface ScalableParameter<T> extends OperationParameter<T> {

	void scale(double coefficient);

	void unscale();

	double getScaling();

	boolean isScaled();
}
