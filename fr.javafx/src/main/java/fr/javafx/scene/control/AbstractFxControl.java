package fr.javafx.scene.control;

import fr.javafx.lang.FxRefreshable;
import fr.javafx.lang.FxRefresher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;

public abstract class AbstractFxControl extends Control implements FxRefreshable {
	protected final BooleanProperty refreshRequested;

	public AbstractFxControl() {
		super();
		refreshRequested = new SimpleBooleanProperty(false);

		FxRefresher.registerControl(this);
	}

	@Override
	public void 					requestRefresh() {
		refreshRequested.set(true);
	}

	@Override
	public boolean 					isRefreshRequested() {
		return refreshRequested.get();
	}

	@Override
	public BooleanProperty 			refreshRequestedProperty() {
		return refreshRequested;
	}
}
