package fr.java.sdk.time.tools;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

import fr.java.time.Time;

public final class TimeIntervalImpl  implements Time.Interval {
    private final Period   period;
    private final Duration duration;

    public TimeIntervalImpl(Duration _duration) {
    	duration = _duration;
    	period   = null;
    }
    public TimeIntervalImpl(Period _period) {
    	duration = null;
    	period   = _period;
    }
    public TimeIntervalImpl(Period _period, Duration _duration) {
    	duration = _duration;
    	period   = _period;
    }

	@Override
	public Instant addTo(Instant _instant) {
		return period != null ?
							Time.Interval.addTo(_instant, period)
							:
							Time.Interval.addTo(_instant, duration);
	}
	@Override
	public Period period() {
		return period;
	}
	@Override
	public Duration duration() {
		return duration;
	}

}
