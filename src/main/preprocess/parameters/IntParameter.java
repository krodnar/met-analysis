package main.preprocess.parameters;

public class IntParameter extends AbstractParameter<Integer> {

	public IntParameter() {
		value = 0;
	}

	public IntParameter(Integer value) {
		super(value);
	}

	public IntParameter(IntParameter parameter) {
		this.value = parameter.value;
	}

	@Override
	protected void scaleValue(double scaleCoefficient) {
		value = (int) (value * scaleCoefficient);
	}
}
