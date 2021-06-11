package fr.javafx.lang;

import fr.java.patterns.capabilities.Refreshable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;

public interface FxRefreshable extends Refreshable {
	
	public static ChangeListener<? super Number> fpsListener(FxRefreshable.WithFPS _refreshable){
		return (_obs, _old, _new) -> {
			if(_new.intValue() < MIN_FPS) _refreshable.preferredFpsProperty().set(MIN_FPS);
			if(_new.intValue() > MAX_FPS) _refreshable.preferredFpsProperty().set(MAX_FPS);
		};
	}

	public static interface WithFPS   extends FxRefreshable, Refreshable.WithFPS {

		public DoubleProperty 	preferredFpsProperty();

	}

	public BooleanProperty 		refreshRequestedProperty();

	// REFRESH EVENTS
	public void 				fireEvent(Event evt);

}
