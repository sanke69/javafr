package fr.javafx.beans.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.java.lang.tuples.Pair;
import fr.java.sdk.lang.Tuples;
import fr.java.sdk.lang.tuples.SimplePair;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;

@Deprecated
public class CustomBindings {
	private static final Map<Pair<ObservableValue<?>, WritableValue<?>>, ChangeListener<?>> bindings;
	
	static {
		bindings = new HashMap<>();
	}

    public static <A ,B> void bind(Property<A> propertyA, Property<B> propertyB, Function<A, B> updateB) {
        addFlaggedChangeListener(propertyA, propertyB, updateB);
    }
    public static <A ,B> void unbind(Property<A> propertyA, Property<B> propertyB) {
    	bindings.remove(Tuples.of(propertyA, propertyB));
    }

    public static <A ,B> void bindBidirectional(Property<A> propertyA, Property<B> propertyB, Function<A,B> updateB, Function<B,A> updateA) {
        addFlaggedChangeListener(propertyA, propertyB, updateB);
        addFlaggedChangeListener(propertyB, propertyA, updateA);
    }
    public static <A ,B> void unbindBidirectional(Property<A> propertyA, Property<B> propertyB) {
    	bindings.remove(Tuples.of(propertyA, propertyB));
    	bindings.remove(Tuples.of(propertyB, propertyA));
    }

    private static <X,Y> void addFlaggedChangeListener(ObservableValue<X> propertyX, WritableValue<Y> propertyY, Function<X,Y> updateY) {
    	ChangeListener<X> cl = new ChangeListener<X>() {
            private boolean alreadyCalled = false;

            @Override
            public void changed(ObservableValue<? extends X> observable, X oldValue, X newValue) {
                if(alreadyCalled) return;
                try {
                    alreadyCalled = true;
                    propertyY.setValue(updateY.apply(newValue));
                }
                finally {alreadyCalled = false; }
            }
        };

        bindings.put(Tuples.of(propertyX, propertyY), cl);

        propertyX.addListener(cl);
    }

}