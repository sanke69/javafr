package fr.java.time.tools;

import java.time.Instant;

import fr.java.time.Time;

public interface TimeWatch extends Time.IntervalConversion {

	public Instant tic();
	public Instant toc();

}
