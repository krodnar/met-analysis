package main.preprocess.parameters;

public class OddIntParameter extends IntParameter {

	public OddIntParameter() {
	}

	public OddIntParameter(Integer value) {
		super(value);
	}

	public OddIntParameter(IntParameter parameter) {
		super(parameter);
	}

	@Override
	protected Integer scaleValue(Integer value, double coefficient) {
		value = super.scaleValue(value, coefficient);

		if (value % 2 != 1) {
			value += 1;
		}

		return value;
	}
}
