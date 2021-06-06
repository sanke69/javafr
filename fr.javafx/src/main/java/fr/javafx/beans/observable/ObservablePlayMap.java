package fr.javafx.beans.observable;

import fr.java.player.PlayMap;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;

public interface ObservablePlayMap<T, P> extends Observable, PlayMap<T, P> {

	public void addListener(MapChangeListener<? super T, ? super P> listener);
	public void removeListener(MapChangeListener<? super T, ? super P> listener);

}
