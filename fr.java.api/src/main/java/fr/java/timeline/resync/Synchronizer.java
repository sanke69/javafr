package fr.java.timeline.resync;

import fr.java.patterns.timeable.Timestampable;
import fr.java.timeline.TimeLine;

public interface Synchronizer<REF extends TimeLine<? extends Timestampable>> {

	public default boolean  hasReference() { return getReference() != null; }

	public REF  	getReference();
	public void 	setReference(REF _reference);

	public long getDeltaTime();

}
