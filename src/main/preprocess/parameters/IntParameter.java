package main.preprocess.parameters;

public class IntParameter extends BaseScalableParameter<Integer> {

	public IntParameter() {
		this(0);
	}

	public IntParameter(Integer value) {
		super(value);
	}

	public IntParameter(Integer value, String name) {
		super(value, name);
	}

	public IntParameter(IntParameter parameter) {
		setValue(parameter.getValue());
	}

	@Override
	protected Integer scaleValue(Integer value, double coefficient) {
		return (int) (value * coefficient);
	}
}
