package fr.java.sdk.time;

import java.time.Duration;
import java.time.Period;

import fr.java.sdk.time.tools.TimeIntervalImpl;
import fr.java.sdk.time.tools.TimeWatchImpl;
import fr.java.time.Time;
import fr.java.time.tools.TimeWatch;

public final class TimeUtil {

	public static final Time.Interval 	newInterval(Duration _duration) {
		return new TimeIntervalImpl(_duration);
	}
	public static final Time.Interval 	newInterval(Period _period) {
		return new TimeIntervalImpl(_period);
	}
	public static final Time.Interval 	newInterval(Period _period, Duration _duration) {
		return new TimeIntervalImpl(_period, _duration);
	}

	public static TimeWatch 			newTimeWatch() {
		return new TimeWatchImpl();
	}

}
