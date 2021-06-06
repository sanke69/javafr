package fr.javafx.beans.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.java.beans.impl.DoubleBeanProperty;
import fr.java.beans.impl.ReadOnlyDoubleBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.beans.properties.listeners.BeanPropertyChangeListener;
import fr.java.beans.utils.BeanProperties;
import fr.java.lang.tuples.Pair;
import fr.java.sdk.lang.Tuples;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;

public class BPNumberBindings extends BeanProperties {
	private static final Map<Pair<ReadOnlyBeanProperty<?>, WritableValue<?>>, BeanPropertyChangeListener<?>>   bindings2FX;
	private static final Map<Pair<ObservableValue<?>,      BeanProperty<?>>,  ChangeListener<?>>               bindings2BP;
	
	static {
		bindings2FX = new HashMap<>();
		bindings2BP = new HashMap<>();
	}

    public static <T> void 		bind(ReadOnlyDoubleBeanProperty propertyA, Property<Number> propertyB) {
    	addFlaggedChangeListenerToFx(propertyA, propertyB, t -> t);
    }
    public static <A ,B> void 	bind(ReadOnlyBeanProperty<A> propertyA, Property<B> propertyB, Function<A, B> updateB) {
    	addFlaggedChangeListenerToFx(propertyA, propertyB, updateB);
    }
    public static <A ,B> void 	unbind(ReadOnlyBeanProperty<A> propertyA, Property<B> propertyB) {
    	bindings2FX.remove(Tuples.of(propertyA, propertyB));
    }

    public static <T> void 		bind(Property<Number> propertyA, DoubleBeanProperty propertyB) {
        addFlaggedChangeListenerToBP(propertyA, propertyB, t -> t.doubleValue());
    }
    public static <A ,B> void 	bind(Property<A> propertyA, BeanProperty<B> propertyB, Function<A, B> updateB) {
        addFlaggedChangeListenerToBP(propertyA, propertyB, updateB);
    }
    public static <A ,B> void 	unbind(Property<A> propertyA, BeanProperty<B> propertyB) {
    	bindings2BP.remove(Tuples.of(propertyA, propertyB));
    }

    public static <T> void 		bindBidirectional(DoubleBeanProperty propertyA, Property<Number> propertyB) {
    	addFlaggedChangeListenerToFx(propertyA, propertyB, t -> t);
    	addFlaggedChangeListenerToBP(propertyB, propertyA, t -> t.doubleValue());
    }
    public static <A ,B> void 	bindBidirectional(BeanProperty<A> propertyA, Property<B> propertyB, Function<A,B> updateB, Function<B,A> updateA) {
    	addFlaggedChangeListenerToFx(propertyA, propertyB, updateB);
    	addFlaggedChangeListenerToBP(propertyB, propertyA, updateA);
    }
    public static <A ,B> void 	unbindBidirectional(BeanProperty<A> propertyA, Property<B> propertyB) {
    	bindings2FX.remove(Tuples.of(propertyA, propertyB));
    	bindings2BP.remove(Tuples.of(propertyB, propertyA));
    }
    
    public static <T> void 		bindBidirectional(Property<Number> propertyA, DoubleBeanProperty propertyB) {
    	addFlaggedChangeListenerToBP(propertyA, propertyB, t -> t.doubleValue());
    	addFlaggedChangeListenerToFx(propertyB, propertyA, t -> t);
    }
    public static <A ,B> void 	bindBidirectional(Property<A> propertyA, BeanProperty<B> propertyB, Function<A,B> updateB, Function<B,A> updateA) {
    	addFlaggedChangeListenerToBP(propertyA, propertyB, updateB);
    	addFlaggedChangeListenerToFx(propertyB, propertyA, updateA);
    }
    public static <A ,B> void 	unbindBidirectional(Property<A> propertyA, BeanProperty<B> propertyB) {
    	bindings2FX.remove(Tuples.of(propertyA, propertyB));
    	bindings2BP.remove(Tuples.of(propertyB, propertyA));
    }

    private static <X,Y> void 	addFlaggedChangeListenerToFx(ReadOnlyBeanProperty<X> propertyX, Property<Y> propertyY, Function<X,Y> updateY) {
    	BeanPropertyChangeListener<X> cl = new BeanPropertyChangeListener<X>() {
            private boolean alreadyCalled = false;

			@Override
			public void propertyChange(Object _property, X _old, X _new) {
                if(alreadyCalled) return;
                try {
                    alreadyCalled = true;
                    propertyY.setValue(updateY.apply(_new));
                }
                finally {alreadyCalled = false; }
			}
        };

        bindings2FX.put(Tuples.of(propertyX, propertyY), cl);

        propertyX.addListener(cl);
    }
    private static <X,Y> void 	addFlaggedChangeListenerToBP(Property<X> propertyX, BeanProperty<Y> propertyY, Function<X,Y> updateY) {
    	ChangeListener<X> cl = new ChangeListener<X>() {
            private boolean alreadyCalled = false;

			@Override
			public void changed(ObservableValue<? extends X> _src, X _old, X _new) {
                if(alreadyCalled) return;
                try {
                    alreadyCalled = true;
                    propertyY.set(updateY.apply(_new));
                }
                finally {alreadyCalled = false; }
			}

        };

        bindings2BP.put(Tuples.of(propertyX, propertyY), cl);

        propertyX.addListener(cl);
    }

}