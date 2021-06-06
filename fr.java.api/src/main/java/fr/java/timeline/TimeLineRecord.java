package fr.java.timeline;

import fr.java.lang.properties.Timestamp;
import fr.java.media.Media;

public interface TimeLineRecord<V> extends TimeLine<V>, Media.Record<V> {

	public Timestamp 	getTimestamp(long _index);
	public V 			getValue(long _index);

}
