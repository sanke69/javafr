package fr.java.timeline;

import java.util.Optional;

import fr.java.lang.properties.Timestamp;

public interface TimeLine<V> {

	public Timestamp 	getCurrentTimestamp();
	public Timestamp 	getNextTimestamp();

	public V 			getCurrentValue();
	public Optional<V>  getNextValue();
	
	public TimeLine<V> clone();

}
