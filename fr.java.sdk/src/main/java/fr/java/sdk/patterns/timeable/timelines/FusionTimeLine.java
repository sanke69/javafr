package fr.java.sdk.patterns.timeable.timelines;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import fr.java.lang.properties.Timestamp;
import fr.java.sdk.patterns.timeable.Timestamps;
import fr.java.timeline.TimeLine;
import fr.java.timeline.TimeLineFusioner;
import fr.java.timeline.TimeLineRecord;

public class FusionTimeLine<V> implements TimeLineFusioner<V> {

//	static record TimeLineEntry<V>(Timestamp timestamp, TimeLineRecord<V> timeline, Long index) {}
	static class TimeLineEntry<V> {
		Timestamp timestamp; TimeLineRecord<V> timeline; Long index;

		public TimeLineEntry(Timestamp _timestamp, TimeLineRecord<V> _timeline, Long _index) { timestamp = _timestamp; timeline = _timeline; index = _index; }

		Timestamp         timestamp() { return timestamp; }
		TimeLineRecord<V> timeline()  { return timeline; }
		Long              index()     { return index; }

	}
	static class  TimeList<V>  extends ArrayList<TimeLineRecord<V>> {
		private static final long serialVersionUID = 8014531326244213435L;
	}
	static class  TimeArray<V> extends ArrayList<TimeLineEntry<V>> {
		private static final long serialVersionUID = 8014531326244213435L;		
	}

	TimeList<V> 			timelines;

	TimeArray<V>			timeline;
	long 					timelineIndex;
	Long					timelineStart, timelineStop;

	public FusionTimeLine() {
		super();

		timelines = new TimeList<V>();
		timeline  = new TimeArray<V>();

		timelineIndex = 0L;
	}
	public FusionTimeLine(Set<TimeLineRecord<V>> _timelines) {
		this();

		for(TimeLineRecord<V> tl : _timelines)
			timelines.add(tl);

		build();
	}

	@Override
	public URI 						getURI() { 
		return URI.create("application:in memory - created in app");
	}

	@Override
	public Timestamp 				getCurrentTimestamp() {
		return timeline.get((int) timelineIndex).timestamp();
	}
	@Override
	public Timestamp 				getNextTimestamp() {
		return timelineIndex + 1 < timeline.size() - 1 ? timeline.get((int) timelineIndex + 1).timestamp() : null;
	}

	@Override
	public V			 			getCurrentValue() {
		return timeline.get((int) timelineIndex).timeline().getCurrentValue();
	}
	@Override
	public Optional<V> 				getNextValue() {
		TimeLineRecord<V>	nextTimeLine;

		if(timelineIndex > timelineStop)
			return Optional.empty();

		TimeLineEntry<V> getCurrent = timeline.get((int) timelineIndex);
		if(getCurrent != null) {
			nextTimeLine = getCurrent.timeline();
			nextTimeLine.setIndex(getCurrent.index());

			Optional<V> nextTime = (Optional<V>) nextTimeLine.getNextValue();

			timelineIndex++;
			return nextTime;
		}

		return Optional.empty();
	}

	@Override
	public Timestamp 				getTimestamp(long _index) {
		if(_index < 0 || _index > timeline.size() - 1)
			throw new IllegalArgumentException();
		return timeline.get((int) _index).timestamp();
	}
	@Override
	public V 						getValue(long _index) {
		if(_index < 0 || _index > timeline.size() - 1)
			throw new IllegalArgumentException();

		TimeLineRecord<V> the_timeline = timeline.get((int) _index).timeline();
		Long              the_index    = timeline.get((int) _index).index();
		
		return the_timeline.getValue(the_index);
	}
	@Override
	public V 						getFrame(long _index) {
		return getValue(_index);
	}

	@Override
	public <eTL extends TimeLineRecord<? extends V>> 
	void 							add(eTL _timeLine) {
		timelines.add((TimeLineRecord<V>) _timeLine);
	}
	@Override
	public <eTL extends TimeLineRecord<? extends V>> 
	void 							addAll(Set<eTL> _timeLines) {
		for(eTL timeLine : _timeLines)
			add(timeLine);

		build();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <eTL extends TimeLineRecord<? extends V>> 
	long[] 							getCommonIntervalFor(Set<eTL> _timeLines) {
		if(timeline == null) {
			System.err.println("You have to build the TimeLine first !!!");
			return null;
		}

		long[] interval = new long[2]; 

		Set<eTL> start = new HashSet<eTL>();
		Set<eTL> stop  = new HashSet<eTL>();

		int index = 0;
		for(TimeLineEntry e : timeline) {
			eTL tested = null;
			if(_timeLines.contains(tested = (eTL) e.timeline()) && e.index() == 0) {
				start.add(tested);
				if(start.size() == _timeLines.size())
					interval[0] = index;
			}

			if(_timeLines.contains(tested = (eTL) e.timeline()) && e.index() == e.timeline().frameCount().get() - 1) {
				stop.add(tested);
				if(stop.size() == _timeLines.size()) {
					interval[1] = index;
					return interval;
				}
			}

			index++;
		}

		return interval;
	}

	public void 					build() {
		Map<TimeLineRecord<? extends V>, Long> 
			rebuildIndexes = new HashMap<TimeLineRecord<? extends V>, Long>();
		for(TimeLineRecord<? extends V> tl : timelines)
			rebuildIndexes.put(tl, 0L);

		timeline.clear();

		TimeLineRecord<? extends V>	nextTimeLine;

		while((nextTimeLine = findNext(rebuildIndexes)) != null) {
			Long      ntlIndex     = rebuildIndexes.get(nextTimeLine);
			Timestamp ntlTimestamp = nextTimeLine.getTimestamp(ntlIndex);

			timeline.add(new TimeLineEntry<V>(ntlTimestamp, (TimeLineRecord<V>) nextTimeLine, ntlIndex));

			rebuildIndexes.put(nextTimeLine, ntlIndex + 1);
		}
	}

	@Override
	public FusionTimeLine<V> 		clone() {
		throw new IllegalAccessError("No simple solution exist for this use case");
	}
/*
	@Override
	public FusionTimeLine 			clone() {
		FusionTimeLine deepCopy = new FusionTimeLine();
		
		for(TimeLine<? extends Timestampable> timeline : timelines) {
			TimeLine<? extends Timestampable> TL = timeline.clone();
			deepCopy.timelines.add(TL);
			
			if(timeline == nextTimeLine)
				deepCopy.nextTimeLine = TL;
		}

		deepCopy.current = current.clone();
		deepCopy.next    = next.clone();
		return deepCopy;
	}
*/

	@SuppressWarnings("unchecked")
	private TimeLineRecord<V> 		findNext() {
		TimeLine<? extends V>  	nextTL = null;
		Timestamp 				nextTime, testTime;

		nextTime = Timestamps.endOfWorld();
		for(TimeLine<? extends V> tl : timelines) {
			if((testTime = tl.getNextTimestamp()) != null) {

				if(testTime.before(nextTime)) {
					nextTime = testTime;
					nextTL   = tl;
				}

			}
		}

		return (TimeLineRecord<V>) nextTL;
	}
	@SuppressWarnings("unchecked")
	private TimeLineRecord<V> 		findNext(Map<TimeLineRecord<? extends V>, Long> _buildIndexes) {
		TimeLine<? extends V> 	nextTL = null;
		Timestamp 							nextTime, testTime;

		nextTime = Timestamps.endOfWorld();
		for(TimeLineRecord<? extends V> tl : timelines) {
			if((testTime = tl.getTimestamp(_buildIndexes.get(tl))) != null) {

				if(testTime.before(nextTime) || testTime.equals(nextTime)) {
					nextTime = testTime;
					nextTL   = tl;
				}

			}
		}

		return (TimeLineRecord<V>) nextTL;
	}

	@Override
	public Optional<Long> 			duration() {
		long start = timeline.get(0).timestamp().toEpochMilli();
		long stop  = timeline.get(timeline.size()-1).timestamp().toEpochMilli();
		return Optional.of(stop - start);
	}

	@Override
	public Optional<Long> 			frameCount() {
		return Optional.of((long)timeline.size());
	}

	@Override
	public Optional<Long> 			frameIndex() {
		return Optional.of(timelineIndex);
	}
	@Override
	public boolean		 			setIndex(long _currentFrame) {
		timelineIndex = _currentFrame;
		return true;
	}
	public boolean		 			increaseIndex() {
		if(timelineIndex++ < timeline.size())
			return true;
		timelineIndex = timeline.size() - 1;
		return false;
	}

	@Override
	public Optional<Long> 			frameStart() {
		return timelineStart > 0 ? Optional.of(timelineStart) : Optional.empty();
	}
	@Override
	public boolean 					setStart(long _start) {
		if(_start < 0 || _start >= timeline.size())
			throw new IllegalArgumentException("_start is out of range [0, " + (timeline.size()-1) + "]");
		timelineStart = _start;
		return true;
	}

	@Override
	public Optional<Long> 			frameStop() {
		return timelineStop > 0 ? Optional.of(timelineStop) : Optional.empty();
	}
	@Override
	public boolean 					setStop(long _stop) {
		if(_stop < timelineStart || _stop >= timeline.size())
			throw new IllegalArgumentException("_start is out of range [0, " + (timeline.size()-1) + "]");
		timelineStop = _stop;
		return true;
	}

	@Override
	public boolean 					setInterval(long _start, long _stop) {
		if( setStart(_start) )
			return setStop(_stop);
		return false;
	}

}
