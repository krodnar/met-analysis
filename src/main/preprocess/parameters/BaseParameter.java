package main.preprocess.parameters;

import javafx.beans.property.SimpleObjectProperty;

public class BaseParameter<T> implements OperationParameter<T> {

	public static final String DEFAULT_NAME = "";

	private SimpleObjectProperty<T> value;
	private String name;

	private ParameterListener<T> listener;

	public BaseParameter(T value, String name) {
		this.value = new SimpleObjectProperty<>(value);
		this.name = name;

		this.value.addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && listener != null) {
				listener.onParameterChange(this, oldValue, newValue);
			}
		});
	}

	@Override
	public T get() {
		return value.get();
	}

	@Override
	public void set(T value) {
		this.value.set(value);
	}

	public void setListener(ParameterListener<T> listener) {
		this.listener = listener;
	}

	public void removeListener() {
		this.listener = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public interface ParameterListener<T> {

		void onParameterChange(OperationParameter<T> parameter, T oldValue, T newValue);
	}
}
