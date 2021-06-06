package fr.java.sdk.patterns.timeable.timelines;

import fr.java.lang.properties.Timestamp;
import fr.java.sdk.patterns.timeable.Timestamps;
import fr.java.timeline.TimeLine;

public abstract class RealTimeLine<V> implements TimeLine<V> {

	public RealTimeLine() {
		super();
	}

	@Override
	public Timestamp 				getCurrentTimestamp() {
		return Timestamps.now();
	}
	@Override
	public Timestamp 				getNextTimestamp() {
		return Timestamps.now();
	}

	@Override
	public RealTimeLine<V> 			clone() {
		throw new IllegalAccessError();
//		return new RealTimeLine();
	}

}
