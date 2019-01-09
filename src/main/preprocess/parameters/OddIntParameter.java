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
	protected void scaleValue(double scaleCoefficient) {
		super.scaleValue(scaleCoefficient);

		if (value % 2 != 1) {
			value += 1;
		}
	}
}
