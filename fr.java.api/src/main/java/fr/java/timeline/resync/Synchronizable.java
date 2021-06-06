package fr.java.timeline.resync;

import fr.java.patterns.timeable.Timestampable;
import fr.java.timeline.TimeLine;

public interface Synchronizable {

	public default boolean 		hasSynchronizer() { return getSynchronizer() != null; }

	public Synchronizer<?>		getSynchronizer();
	public <R extends TimeLine<? extends Timestampable>> 
		   void    				setSynchronizer(Synchronizer<R> _reference);

	public default double 		getTimeCorrection() { return hasSynchronizer() ? getSynchronizer().getDeltaTime() : 0L; }
}
