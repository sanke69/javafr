package fr.javafx.beans;

import fr.javafx.lang.FxRefreshable;
import javafx.beans.value.ChangeListener;

@Deprecated
public abstract class AbstractBeanFx {
	public static final double MIN_FPS = 1;
	public static final double MAX_FPS = 60;

	public static ChangeListener<? super Number> fpsListener(FxRefreshable.WithFPS _refreshable){
		return (_obs, _old, _new) -> {
			if(_new.intValue() < MIN_FPS) _refreshable.preferredFpsProperty().set(MIN_FPS);
			if(_new.intValue() > MAX_FPS) _refreshable.preferredFpsProperty().set(MAX_FPS);
		};
	}
/*
	public DoubleProperty 				fpsProperty();

	// Refresh Trigger
	public ReadOnlyBooleanProperty 		refreshRequestedProperty();
	public void    						requestRefresh();
	public boolean 						refreshRequested();
	public void 						refreshDone();

	// Refresh Action
	public void    						refresh();

	// Refresh Events
	public void 						fireEvent(RefreshEvent _refreshEvent);
*/
	// Refresh Listener
	// * On FX
	//   EventHandler<RefreshEvent> _refreshHandler;
	//   super.addEventHandler(RefreshEvent.OPTIONS_ALL, _refreshHandler);

//	public void    						setOnRefresh(EventHandler<RefreshEvent> _refreshHandler);
//	public EventHandler<RefreshEvent>	getOnRefresh();


}
