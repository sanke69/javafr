package fr.java.data;

import java.util.function.Consumer;

import fr.java.patterns.loadable.Loadable;
import fr.java.patterns.priority.Priority;

public interface Data<COORD, TYPE> {

	public interface Async<COORD, TYPE> extends Data<COORD, TYPE>, Loadable {

		// Indicates if collected data is  stable enough in time to be stored locally
		public default boolean  isPersistent() { return true; }

		public Priority 		getPriority();

		public void 			addUniqueLoadedListener(Consumer<TYPE> _consumer);
		@Deprecated
		public void 			fireLoadedEvent(final TYPE _value);

	}

	public COORD 			getCoordinates();
	public TYPE  			getContent();//TODO:: throws Exception;

}
