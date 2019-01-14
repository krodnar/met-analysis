package main.preprocess.parameters;

import org.opencv.core.Size;

public class SizeParameter extends BaseScalableParameter<Size> {

	public SizeParameter() {
		super(new Size());
	}

	public SizeParameter(Size value) {
		super(value);
	}

	public SizeParameter(SizeParameter parameter) {
		setValue(parameter.getValue().clone());
	}

	@Override
	protected Size scaleValue(Size value, double coefficient) {
		value.width *= coefficient;
		value.height *= coefficient;

		return value;
	}
}
