package fr.java.sdk.patterns.timeable.timelines;

import fr.java.lang.properties.Timestamp;
import fr.java.sdk.patterns.timeable.Timestamps;
import fr.java.timeline.TimeLine;

public abstract class FixedStepTimeLine<V> implements TimeLine<V> {
	private Timestamp currentTime;
	private long      timeStep, timeIter = 0;

	public FixedStepTimeLine() {
		super();

		currentTime    		= Timestamps.epoch();
		timeStep       		= 40;
	}
	public FixedStepTimeLine(long _step_ms) {
		this();

		timeStep       = _step_ms;
	}
	public FixedStepTimeLine(long _step_ms, long _start_ms) {
		this();

		currentTime    = Timestamps.of(_start_ms);
		timeStep       = _step_ms;
	}
	public FixedStepTimeLine(long _step_ms, Timestamp _start) {
		this();

		currentTime    = _start;
		timeStep       = _step_ms;
	}
	protected FixedStepTimeLine(final FixedStepTimeLine<V> _source) {
		super();

		currentTime    = _source.currentTime;
		timeStep       = _source.timeStep;
	}

	@Override
	public Timestamp 				getCurrentTimestamp() {
		return currentTime.plus(timeIter * timeStep);
	}
	@Override
	public Timestamp 				getNextTimestamp() {
		return currentTime.plus(timeIter++ * timeStep);
	}

	@Override
	public FixedStepTimeLine<V> 	clone() {
		throw new IllegalAccessError();
//		return new FixedStepTimeLine(this);
	}

}
