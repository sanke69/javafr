package fr.java.timeline;

import java.util.Set;

public interface TimeLineFusioner<V> extends TimeLineRecord<V> {

	public <eTL extends TimeLineRecord<? extends V>> void 	add(eTL _timeLine);
	public <eTL extends TimeLineRecord<? extends V>> void 	addAll(Set<eTL> _timeLines);

	public <eTL extends TimeLineRecord<? extends V>> long[] getCommonIntervalFor(Set<eTL> _timeLines);

}
