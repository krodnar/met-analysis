package main.preprocess.parameters;

public abstract class AbstractParameter<T> implements OperationParameter<T> {

	protected T value;
	private double scaleCoefficient = 1;

	public AbstractParameter() {

	}

	public AbstractParameter(T value) {
		this.value = value;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
		scaleValue(scaleCoefficient);
	}

	@Override
	public void scale(double coefficient) {
		this.scaleCoefficient *= coefficient;
		scaleValue(coefficient);
	}

	@Override
	public void unscale() {
		unscaleValue(scaleCoefficient);
		this.scaleCoefficient = 1;
	}

	@Override
	public double getScaling() {
		return scaleCoefficient;
	}

	protected void unscaleValue(double coefficient) {
		scaleValue(1 / coefficient);
	}

	protected abstract void scaleValue(double coefficient);
}
