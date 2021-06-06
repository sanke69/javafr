package fr.java.sdk.patterns.timeable.timelines;

import java.net.URI;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.Timestamp;
import fr.java.sdk.patterns.timeable.Timestamps;
import fr.java.sdk.player.RecordAdapter;
import fr.java.timeline.TimeLine;
import fr.java.timeline.TimeLineRecord;

public abstract class FixedStepTimeLineRecord<V> extends RecordAdapter<V> implements TimeLineRecord<V> {
	private Timestamp startTime, currentTime;
	private long      timeStep;

	public FixedStepTimeLineRecord() {
		super();

		startTime	= Timestamps.snk();
		timeStep	= 40;

		setFrameCount( Timestamps.now().delta(Timestamps.snk()) / timeStep );
	}
	public FixedStepTimeLineRecord(long _step_ms, long _step_count) {
		this();

		startTime	= Timestamps.now();
		timeStep	= _step_ms;

		setFrameCount( _step_count );
	}
	public FixedStepTimeLineRecord(long _step_ms, long _start_ms, long _stop_ms) {
		this();

		startTime	= Timestamps.of(_start_ms);
		timeStep	= _step_ms;

		setFrameCount( (_stop_ms - _start_ms) / _step_ms );
	}
	public FixedStepTimeLineRecord(long _step_ms, Timestamp _start, Timestamp _stop) {
		this();

		startTime	= _start;
		timeStep	= _step_ms;

		setFrameCount( _stop.delta(_start) / _step_ms );
	}
	protected FixedStepTimeLineRecord(final FixedStepTimeLineRecord<V> _source) {
		super();

		startTime	= _source.startTime;
		timeStep	= _source.timeStep;

		setFrameCount( _source.frameCount().get() );
	}

	@Override
	public URI		 				getURI() {
		return URI.create("application:in memory");
	}

	@Override
	public Timestamp 				getCurrentTimestamp() {
		return currentTime;
	}
	@Override
	public Timestamp 				getNextTimestamp() {
		currentTime = startTime.plus(frameIndex().get() * timeStep);
		increaseIndex();
		return currentTime;
	}

	@Override
	public Timestamp 				getTimestamp(long _index) {
		return startTime.plus(_index * timeStep);
	}
	@Override
	public V 						getFrame(long _index) {
		return getValue(_index);
	}

	@Override
	public TimeLine<V> 				clone() {
		throw new NotYetImplementedException();
	}

}
