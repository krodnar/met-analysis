package main.preprocess.parameters;

public abstract class BaseScalableParameter<T> extends BaseParameter<T> implements ScalableParameter<T> {

	private double scaleCoefficient = 1;

	public BaseScalableParameter() {
		this(null, DEFAULT_NAME);
	}

	public BaseScalableParameter(T value) {
		this(value, DEFAULT_NAME);
	}

	public BaseScalableParameter(T value, String name) {
		super(value, name);
	}

	@Override
	public void setValue(T value) {
		if (isScaled()) {
			value = scaleValue(value, scaleCoefficient);
		}

		super.setValue(value);
	}

	@Override
	public void scale(double coefficient) {
		if (coefficient == 1) {
			return;
		}

		this.scaleCoefficient *= coefficient;
		T scaledValue = scaleValue(getValue(), coefficient);
		super.setValue(scaledValue);
	}

	@Override
	public void unscale() {
		if (!isScaled()) {
			return;
		}

		T unscaledValue = unscaleValue(getValue(), scaleCoefficient);
		this.scaleCoefficient = 1;
		super.setValue(unscaledValue);
	}

	@Override
	public double getScaling() {
		return scaleCoefficient;
	}

	@Override
	public boolean isScaled() {
		return scaleCoefficient != 1;
	}

	protected T unscaleValue(T value, double coefficient) {
		return scaleValue(value, 1 / coefficient);
	}

	protected abstract T scaleValue(T value, double coefficient);
}
