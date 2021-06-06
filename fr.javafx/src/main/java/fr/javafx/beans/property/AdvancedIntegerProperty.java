package fr.javafx.beans.property;

import java.util.Locale;

import fr.javafx.beans.binding.CustomBindings;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableIntegerValue;

public class AdvancedIntegerProperty implements Property<Number>, NumberExpression, WritableIntegerValue {
	private ObjectProperty<Integer> value;

    public AdvancedIntegerProperty() {
        this(null, "");
    }
    public AdvancedIntegerProperty(Integer initialValue) {
        this(null, "", initialValue);
    }
    public AdvancedIntegerProperty(Object bean, String name) {
        super();
        value  = new SimpleObjectProperty<Integer>(bean, name, 0);
    }
    public AdvancedIntegerProperty(Object bean, String name, Integer initialValue) {
        super();
        value  = new SimpleObjectProperty<Integer>(bean, name, initialValue);
    }

	public Object 			getBean() {
		return value.getBean();
	}
	public String 			getName() {
		return value.getName();
	}

	public void 			setValue(Number _value) {
		if(_value != null)
			value.setValue(_value.intValue());
		else
			value.setValue(null);
	}
	public Integer 			getValue() {
		return value.getValue();
	}

	protected ObjectProperty<Integer> asObjectProperty() {
		return value;
	}

	public void 			set(int _value) {
		value.set(_value);
	}
	public int 				get() {
		return value.get();
	}

	@Override
	public int 				intValue() {
		return value.get();
	}
	@Override
	public long 			longValue() {
		return value.get().longValue();
	}
	@Override
	public float 			floatValue() {
		return value.get().floatValue();
	}
	@Override
	public double 			doubleValue() {
		return value.get().doubleValue();
	}

	public StringBinding 	asString() {
		return value.asString();
	}
	public StringBinding 	asString(String format) {
		return value.asString(format);
	}
	public StringBinding 	asString(Locale locale, String format) {
		return value.asString(locale, format);
	}

	public void 			addListener(InvalidationListener listener) {
		value.addListener(listener);
	}
	public void 			removeListener(InvalidationListener listener) {
		value.removeListener(listener);
	}

	public void 			addListener(ChangeListener<? super Number> listener) {
		value.addListener(listener);
	}
	public void 			removeListener(ChangeListener<? super Number> listener) {
		value.removeListener(listener);
	}

	public boolean 			isBound() {
		return value.isBound();
	}

	public void 			bind(ObservableValue<? extends Number> newObservable) {
		ObjectBinding<Integer> binder = Bindings.createObjectBinding(
				() ->  newObservable.getValue().intValue(), newObservable
		);
		value.bind(binder);
		
//		IntegerProperty integerProperty = IntegerProperty.integerProperty(newObservable);
//		Bindings.bindBidirectional(value, newObservable);
//		value.bind(newObservable);
	}
	public void 			bind(IntegerProperty newObservable) {
		IntegerProperty integerProperty = IntegerProperty.integerProperty(value);
		integerProperty.bind(newObservable.asObject());
	}
	@Override
	public void 			unbind() {
		value.unbind();
	}

	public void 			bindBidirectional(Property<Number> other) {
		CustomBindings.bindBidirectional(this, other, This -> This, Other -> Other.intValue());
	}
	@Override
	public void 			unbindBidirectional(Property<Number> other) {
		CustomBindings.unbindBidirectional(this, other);
	}
	

	@Override
	public NumberBinding 	negate() {
		return Bindings.multiply(-1, this);
	}

	@Override
	public NumberBinding 	add(ObservableNumberValue other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.add(other, this);
	}
	@Override
	public NumberBinding 	add(double other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.add(other, this);
	}
	@Override
	public NumberBinding 	add(float other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.add(other, this);
	}
	@Override
	public NumberBinding 	add(long other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.add(other, this);
	}
	@Override
	public NumberBinding 	add(int other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.add(other, this);
	}

	@Override
	public NumberBinding 	subtract(ObservableNumberValue other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.subtract(other, this);
	}
	@Override
	public NumberBinding 	subtract(double other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.subtract(other, this);
	}
	@Override
	public NumberBinding 	subtract(float other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.subtract(other, this);
	}
	@Override
	public NumberBinding 	subtract(long other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.subtract(other, this);
	}
	@Override
	public NumberBinding 	subtract(int other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.subtract(other, this);
	}

	@Override
	public NumberBinding 	multiply(ObservableNumberValue other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.multiply(other, this);
	}
	@Override
	public NumberBinding 	multiply(double other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.multiply(other, this);
	}
	@Override
	public NumberBinding 	multiply(float other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.multiply(other, this);
	}
	@Override
	public NumberBinding 	multiply(long other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.multiply(other, this);
	}
	@Override
	public NumberBinding 	multiply(int other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.multiply(other, this);
	}

	@Override
	public NumberBinding 	divide(ObservableNumberValue other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.divide(other, this);
	}
	@Override
	public NumberBinding 	divide(double other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.divide(other, this);
	}
	@Override
	public NumberBinding 	divide(float other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.divide(other, this);
	}
	@Override
	public NumberBinding 	divide(long other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.divide(other, this);
	}
	@Override
	public NumberBinding 	divide(int other) {
		if(getValue() == null)
			throw new NullPointerException();

		return Bindings.divide(other, this);
	}


	public BooleanBinding 	isNull() {
		return value.isNull();
	}
	public BooleanBinding 	isNotNull() {
		return value.isNotNull();
	}

	@Override
	public BooleanBinding 	isEqualTo(ObservableNumberValue other) {
		return Bindings.equal(other, this);
	}
	@Override
	public BooleanBinding 	isEqualTo(ObservableNumberValue other, double epsilon) {
		return Bindings.greaterThanOrEqual(other.doubleValue() - epsilon, this).or(Bindings.lessThanOrEqual(other.doubleValue() + epsilon, this));
	}
	@Override
	public BooleanBinding 	isEqualTo(double other, double epsilon) {
		return Bindings.greaterThanOrEqual(other - epsilon, this).or(Bindings.lessThanOrEqual(other + epsilon, this));
	}
	@Override
	public BooleanBinding 	isEqualTo(float other, double epsilon) {
		return Bindings.greaterThanOrEqual(other - epsilon, this).or(Bindings.lessThanOrEqual(other + epsilon, this));
	}
	@Override
	public BooleanBinding 	isEqualTo(long other) {
		return Bindings.equal(other, this);
	}
	@Override
	public BooleanBinding 	isEqualTo(long other, double epsilon) {
		return Bindings.greaterThanOrEqual(other - epsilon, this).or(Bindings.lessThanOrEqual(other + epsilon, this));
	}
	@Override
	public BooleanBinding 	isEqualTo(int other) {
		return Bindings.equal(other, this);
	}
	@Override
	public BooleanBinding 	isEqualTo(int other, double epsilon) {
		return Bindings.greaterThanOrEqual(other - epsilon, this).or(Bindings.lessThanOrEqual(other + epsilon, this));
	}

	@Override
	public BooleanBinding 	isNotEqualTo(ObservableNumberValue other) {
		return Bindings.notEqual(other, this);
	}
	@Override
	public BooleanBinding 	isNotEqualTo(ObservableNumberValue other, double epsilon) {
		return Bindings.lessThan(other.doubleValue() - epsilon, this).or(Bindings.greaterThan(other.doubleValue() + epsilon, this));
	}
	@Override
	public BooleanBinding 	isNotEqualTo(double other, double epsilon) {
		return Bindings.lessThan(other - epsilon, this).or(Bindings.greaterThan(other + epsilon, this));
	}
	@Override
	public BooleanBinding 	isNotEqualTo(float other, double epsilon) {
		return Bindings.lessThan(other - epsilon, this).or(Bindings.greaterThan(other + epsilon, this));
	}
	@Override
	public BooleanBinding 	isNotEqualTo(long other) {
		return isEqualTo(other).not();
	}
	@Override
	public BooleanBinding 	isNotEqualTo(long other, double epsilon) {
		return Bindings.lessThan(other - epsilon, this).or(Bindings.greaterThan(other + epsilon, this));
	}
	@Override
	public BooleanBinding 	isNotEqualTo(int other) {
		return isEqualTo(other).not();
	}
	@Override
	public BooleanBinding 	isNotEqualTo(int other, double epsilon) {
		return Bindings.lessThan(other - epsilon, this).or(Bindings.greaterThan(other + epsilon, this));
	}

	@Override
	public BooleanBinding 	greaterThan(ObservableNumberValue other) {
		return Bindings.greaterThan(other, this);
	}
	@Override
	public BooleanBinding 	greaterThan(double other) {
		return Bindings.greaterThan(other, this);
	}
	@Override
	public BooleanBinding 	greaterThan(float other) {
		return Bindings.greaterThan(other, this);
	}
	@Override
	public BooleanBinding 	greaterThan(long other) {
		return Bindings.greaterThan(other, this);
	}
	@Override
	public BooleanBinding 	greaterThan(int other) {
		return Bindings.greaterThan(other, this);
	}

	@Override
	public BooleanBinding 	lessThan(ObservableNumberValue other) {
		return Bindings.lessThan(other, this);
	}
	@Override
	public BooleanBinding 	lessThan(double other) {
		return Bindings.lessThan(other, this);
	}
	@Override
	public BooleanBinding 	lessThan(float other) {
		return Bindings.lessThan(other, this);
	}
	@Override
	public BooleanBinding 	lessThan(long other) {
		return Bindings.lessThan(other, this);
	}
	@Override
	public BooleanBinding 	lessThan(int other) {
		return Bindings.lessThan(other, this);
	}

	@Override
	public BooleanBinding	greaterThanOrEqualTo(ObservableNumberValue other) {
		return Bindings.greaterThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	greaterThanOrEqualTo(double other) {
		return Bindings.greaterThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	greaterThanOrEqualTo(float other) {
		return Bindings.greaterThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	greaterThanOrEqualTo(long other) {
		return Bindings.greaterThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	greaterThanOrEqualTo(int other) {
		return Bindings.greaterThanOrEqual(other, this);
	}

	@Override
	public BooleanBinding 	lessThanOrEqualTo(ObservableNumberValue other) {
		return Bindings.lessThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	lessThanOrEqualTo(double other) {
		return Bindings.lessThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	lessThanOrEqualTo(float other) {
		return Bindings.lessThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	lessThanOrEqualTo(long other) {
		return Bindings.lessThanOrEqual(other, this);
	}
	@Override
	public BooleanBinding 	lessThanOrEqualTo(int other) {
		return Bindings.lessThanOrEqual(other, this);
	}

	public String 			toString() {
		return value.toString();
	}

	public int 				hashCode() {
		return value.hashCode();
	}

	public boolean 			equals(Object obj) {
		return value.equals(obj);
	}

}
