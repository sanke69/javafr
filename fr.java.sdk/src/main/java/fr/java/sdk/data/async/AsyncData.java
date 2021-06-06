package fr.java.sdk.data.async;

import java.beans.PropertyChangeListener;
import java.lang.ref.SoftReference;
import java.util.function.Consumer;

import fr.java.beans.impl.AbstractBean;
import fr.java.data.Data;
import fr.java.data.provider.DataProvider;
import fr.java.patterns.priority.DefaultPriority;
import fr.java.patterns.priority.Priority;

public class AsyncData<COORD, TYPE> extends AbstractBean implements Data.Async<COORD, TYPE> {
	private static final long serialVersionUID = 717166845252371135L;

	private static final long  MAX_LOAD_ATTEMPTS = 1;
	public static final String LOADED            = "loaded";

	protected final DataProvider.Async<COORD, TYPE> 	provider;

	protected final COORD								coords;
	protected       SoftReference<TYPE>					data     	   = new SoftReference<TYPE>(null);

	protected Priority									priority 	   = DefaultPriority.High;

	private boolean										isLoading	   = false;
	private boolean										isLoaded	   = false;

	private int 										loadAttempts   = 0;
	private Throwable 									error;

	private PropertyChangeListener 						uniqueListener = null;

	
	public AsyncData(COORD _coords, DataProvider.Async<COORD, TYPE> _provider) {
		super();
		coords   = _coords;
		provider = _provider;
	}

	@Override
	public void load() throws Exception {
		if(data.get() == null && loadAttempts < MAX_LOAD_ATTEMPTS) {
			setLoaded(false);
			provider.startLoading(this);
		}
	}

	public void 				setLoading(boolean _isLoading) {
		isLoading = _isLoading;
	}
	@Override
	public boolean 				isLoading() {
		return isLoading;
	}

	public synchronized void 	setLoaded(boolean _loaded) {
		boolean old = isLoaded();
		isLoaded = _loaded;
		firePropertyChange(LOADED, old, isLoaded());
	}
	@Override
	public synchronized boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public boolean 				hasError() {
		return error != null;
	}

	public void 				setPriority(DefaultPriority _priority) {
		priority = _priority;
	}
	@Override
	public Priority 			getPriority() {
		return priority;
	}

	public COORD 				getCoordinates() {
		return coords;
	}

	public void 				setContent(TYPE _content) {
		data = new SoftReference<TYPE>(_content);
	}
	public TYPE 				getContent() {
		TYPE img = data.get();

		if(img == null && !isLoading())
			provider.startLoading(this);
		else if(hasError()) {
			System.err.println("error");
		}

		return img;
	}

	public void 				addUniqueLoadedListener(PropertyChangeListener _listener) {
		if (uniqueListener != null/* && uniqueListener != _listener*/) {
			removeListener(LOADED, uniqueListener);
			uniqueListener = null;
		}
	
		if (uniqueListener != _listener) {
			uniqueListener = _listener;
			addListener(LOADED, uniqueListener);
		}
	}
	public void 				addUniqueLoadedListener(Consumer<TYPE> _consumer) {
		if (uniqueListener != null) {
			removeListener(LOADED, uniqueListener);
			uniqueListener = null;
		}

		if (_consumer != null) {
			PropertyChangeListener listener = (chg) -> { _consumer.accept((TYPE) chg.getNewValue()); };

			addListener("LOADED", uniqueListener = listener);
		}
	}

	public void 				fireLoadedEvent(final TYPE _value) {
		firePropertyChange("LOADED", null, _value);
//		if(!EventQueue.isDispatchThread())
//			SwingUtilities.invokeLater(() -> firePropertyChange("LOADED", null, _value));
	}

	public void 				setError(Throwable _error) 	{ 
		error = _error; 
	}

	public Throwable 			getError() 					{ return error; }
	public Throwable 			getUnrecoverableError() 	{ return error; }
	public Throwable 			getLoadingError() 			{ return error; }

}
