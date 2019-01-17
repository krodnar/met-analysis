package main.preprocess.parameters;

public class DoubleParameter extends BaseScalableParameter<Double> {

	public DoubleParameter() {
		set(0d);
	}

	public DoubleParameter(Double value) {
		super(value);
	}

	public DoubleParameter(Double value, String name) {
		super(value, name);
	}

	@Override
	protected Double scaleValue(Double value, double coefficient) {
		return value * coefficient;
	}
}
