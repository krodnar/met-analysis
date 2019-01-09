package main.preprocess.parameters;

import org.opencv.core.Size;

public class SizeParameter extends AbstractParameter<Size> {

	public SizeParameter() {
		super(new Size());
	}

	public SizeParameter(Size value) {
		super(value);
	}

	public SizeParameter(SizeParameter parameter) {
		this.value = parameter.value.clone();
	}

	@Override
	protected void scaleValue(double scaleCoefficient) {
		value.width *= scaleCoefficient;
		value.height *= scaleCoefficient;
	}
}
