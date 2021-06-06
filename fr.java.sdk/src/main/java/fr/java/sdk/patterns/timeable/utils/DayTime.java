package fr.java.sdk.patterns.timeable.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Deprecated
public class DayTime {
	public int hour, minute, second;

	public DayTime() {
		hour = minute = second = 0;
	}
	public DayTime(int _hour) {
		this();
		hour = _hour;
	}
	public DayTime(int _hour, int _minute) {
		this();
		hour = _hour;
		minute = _minute;
	}
	public DayTime(int _hour, int _minute, int _second) {
		this();
		hour = _hour;
		minute = _minute;
		second = _second;
	}
	
	public LocalDateTime toLocalDateTime() {
		LocalTime     midnight         = LocalTime.MIDNIGHT;
	    LocalDate     today            = LocalDate.now(ZoneId.of("Europe/Paris"));
	    LocalDateTime todayMidnight    = LocalDateTime.of(today, midnight);
//	    LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);

		return todayMidnight.plusHours(hour).plusMinutes(minute).plusSeconds(second);
	}

}
