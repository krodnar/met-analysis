package main.preprocess;

import main.preprocess.operations.*;

public enum OperationType {

	GRAYSCALE("Grayscale"){
		@Override
		public <T extends ImageOperation<T>> T getInstance() {
			return (T) new GrayScaleOperation();
		}
	},
	CLAHE("CLAHE"){
		@Override
		public <T extends ImageOperation<T>> T getInstance() {
			return (T) new ClaheOperation();
		}
	},
	ADAPTIVE_THRESHOLD("Adaptive Threshold"){
		@Override
		public <T extends ImageOperation<T>> T getInstance() {
			return (T) new AdaptiveThresholdOperation();
		}
	},
	THRESHOLD("Threshold"){
		@Override
		public <T extends ImageOperation<T>> T getInstance() {
			return (T) new ThresholdOperation();
		}
	},
	MORPHOLOGY("Morphological"){
		@Override
		public <T extends ImageOperation<T>> T getInstance() {
			return (T) new MorphologicalOperation();
		}
	},
	BLUR("Blur"){
		@Override
		public <T extends ImageOperation<T>> T getInstance() {
			return (T) new BlurOperation();
		}
	};

	private String name;

	OperationType(String name) {
		this.name = name;
	}

	public abstract <T extends ImageOperation<T>> T getInstance();

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
