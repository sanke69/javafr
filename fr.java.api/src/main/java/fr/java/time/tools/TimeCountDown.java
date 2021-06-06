package fr.java.time.tools;

import fr.java.time.Time;

public interface TimeCountDown extends Time.Interval {

	public boolean isReached();

}
