/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.EnumSet;
import java.util.Locale;

public interface Time {
	public static final ZoneId   	SYSTEM_ZONEID      = ZoneId.systemDefault();
	public static final ZoneOffset  SYSTEM_ZONEOFFSET  = ZoneOffset.UTC;
	public static final Locale  	SYSTEM_LOCALE      = Locale.ENGLISH; 

	public static final ZoneId   	UTC_ZONEID         = ZoneId.of("UCT");
	public static final ZoneOffset  UTC_ZONEOFFSET     = ZoneOffset.UTC;
	public static final Locale  	UTC_LOCALE         = Locale.ENGLISH;

	public static final ZoneId   	AUTHOR_ZONEID      = ZoneId.of("Europe/Paris");
	public static final ZoneOffset  AUTHOR_ZONEOFFSET  = ZoneOffset.of("+01:00");
	public static final Locale  	AUTHOR_LOCALE      = Locale.FRENCH; 

	public static final ZoneId   	DEFAULT_ZONEID     = AUTHOR_ZONEID;
	public static final ZoneOffset  DEFAULT_ZONEOFFSET = AUTHOR_ZONEOFFSET;
	public static final Locale  	DEFAULT_LOCALE     = AUTHOR_LOCALE;

	public interface   Interval {

		public Period   		period();
		public Duration 		duration();

		public default long 	years()   { return period().getYears(); }
		public default int  	months()  { return period().getMonths(); }
		public default int  	days()    { return period().getDays(); }
		public default int  	hours()   { return duration().toHoursPart(); }   // { return (int) ((double) duration().getSeconds() / 3600d); }
		public default int  	minutes() { return duration().toMinutesPart(); } // { return (int) ((double) duration().getSeconds() / 60d) - 60 * hours(); }
		public default int  	seconds() { return duration().toSecondsPart(); } // { return (int) ((double) duration().getSeconds() / 3600d) - 3600 * hours() - 60 * minutes(); }
		public default int 		millis()  { return duration().toMillisPart(); }  // { return 0; }

	    public default Period 	normalize(Period _period) {
	    	if( _period.getDays() > 31 || _period.getDays() < - 31 )
	    		throw new RuntimeException();

	    	int extraYears = _period.getMonths() % 12;

	    	return Period.of(_period.getYears() + extraYears, _period.getMonths() - 12 * extraYears, _period.getYears());
	    }

	    public default Instant 	addTo(Instant _instant) {
	    	return addTo(addTo(_instant, period()), duration());
	    }

		public static Instant   addTo(Instant _instant, Duration _duration) {
			return _instant.plusMillis(_duration.toMillis());
		}
		public static Instant   addTo(Instant _instant, Period _period) {
			LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
			ldt = ldt.plusYears(_period.getYears());
			ldt = ldt.plusMonths(_period.getMonths());
			ldt = ldt.plusDays(_period.getDays());
	
			return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
		}
	
	}

	public interface   IntervalConversion {

		public long    getInNanoseconds();
		public long    getInMicroseconds();
		public long    getInMilliseconds();
		public long    getInSeconds();
		public long    getInMinutes();
		public long    getInHours();
		public long    getInDays();
		public long    getInYears();

	}

	public static enum Unit {
		MILLENIUM,
		CENTURY,
		DECADE,
		YEAR, 
		MONTH, 
		WEEK, 
		DAY, 
		HOUR, 
		MINUTE, 
		SECOND,
		MILLISECOND,
		NANOSECOND;
		
		public Unit upper() {
			int i = ordinal();
			i = i - 1 > 0 ? i - 1 : i;
			return values()[i];
		}
		public Unit lower() {
			int i = ordinal();
			i = i + 1 < values().length ? i + 1 : i;
			return values()[i];
		}
		
	}

	public static enum Periodicity implements Interval {
		EveryNanosecond		(Duration.ofNanos(1)),
		EveryMillisecond	(Duration.ofMillis(1)),
		EverySecond			(Duration.ofSeconds(1)),
		EveryMinute			(Duration.ofMinutes(1)),
		EveryHour			(Duration.ofHours(1)),
		EveryDay			(Period.ofDays(1)),
		EveryWeek			(Period.ofDays(7)),
		EveryMonth			(Period.ofMonths(1)),
		EveryYear			(Period.ofYears(1)),
		EveryDecade			(Period.ofYears(10)),
		EveryCentury		(Period.ofYears(100)),
		EveryMillennium		(Period.ofYears(1000)),
	
	
		HOURLY(Duration.ofHours(1)), DAILY(Period.ofDays(1)), MONTHLY(Period.ofMonths(1)), YEARLY(Period.ofYears(1)),
	
	
		Quinquennially 		(Period.ofYears(5)), 		// Once per 5 years 		​1⁄5 per year
		Quadriennially 		(Period.ofYears(4)), 		// Once per 4 years 		​1⁄4 per year
		Triennially 		(Period.ofYears(3)), 		// Once per 3 years 		​1⁄3 per year
		Biennially 			(Period.ofYears(2)), 		// Once per 2 years 		​1⁄2 per year
		Annually 			(Period.ofYears(1)), 		// Once per year 			1 per year
		Yearly 				(Period.ofYears(1)), 		// Once per year 			1 per year
		Semiannually		(Period.ofMonths(6)), 		// Twice per year 			2 per year
		Biannually 			(Period.ofMonths(6)), 		// Twice per year 			2 per year
		Triannually 		(Period.ofMonths(4)), 		// Thrice per year 			3 per year
		Quarterly 			(Period.ofMonths(3)), 		// Every quarter 			4 per year
		Bimonthly 			(Period.ofMonths(2)), 		// Every 2 months 			6 per year
		SemiQuarterly 		(Period.ofMonths(2)), 		// Twice per quarter 		8 per year
		Monthly 			(Period.ofMonths(1)), 		// Every month 				12 per year
		Biweekly 			(Period.ofDays(14)),  		//
		Fortnightly 		(Period.ofDays(14)),  		// Every two weeks 			26 per year
		Weekly 				(Period.ofDays(7)),   		// Every week 				52 per year
		Daily 				(Period.ofDays(1)),			// Once per business day 	Varies
		Hourly				(Duration.ofHours(1)), 		
		; 
	
		Duration duration;
		Period   period;
	
		public static final EnumSet<Periodicity> iterator = EnumSet.of(Quinquennially,
				                                                      Quadriennially,
				                                                      Triennially,
				                                                      Biennially,
				                                                      Annually,
				                                                      Semiannually,
				                                                      Triannually,
				                                                      Quarterly,
				                                                      Bimonthly,
				                                                      Monthly,
				                                                      Biweekly,
				                                                      Weekly,
				                                                      Daily,
				                                                      Hourly);
	
		private Periodicity(Duration _duration) {
			duration = _duration;
			period   = null;
		}
		private Periodicity(Period _period) {
			duration = null;
			period   = _period;
		}

		@Override
		public Period period() {
			return period;
		}
		@Override
		public Duration duration() {
			return duration;
		}

	    public Instant 			addTo(Instant _instant) {
			return period != null ?
								Interval.addTo(_instant, period)
								:
								Interval.addTo(_instant, duration);
	    }

	}

	
	
	public static LocalDateTime of(Instant _instant) {
		return LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEOFFSET);
	}
	public static LocalDate 	date(Instant _instant) {
		return LocalDate.ofInstant(_instant, Time.DEFAULT_ZONEOFFSET);
	}
	public static LocalTime 	time(Instant _instant) {
		return LocalTime.ofInstant(_instant, Time.DEFAULT_ZONEOFFSET);
	}

	public static Period  		between(Instant _from, Instant _to) {
		LocalDateTime from = LocalDateTime.ofInstant(_from, Time.DEFAULT_ZONEID);
		LocalDateTime to   = LocalDateTime.ofInstant(_to, Time.DEFAULT_ZONEID);

		return Period.between(from.toLocalDate(), to.toLocalDate());
	}
	
	public static String 		toString(Instant _instant) {
		DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
			                     .withLocale( Time.DEFAULT_LOCALE )
			                     .withZone( Time.DEFAULT_ZONEID );

		return formatter.format( _instant );
	}

	public static boolean 		isGreater(Duration _test, Instant _start, Instant _stop) {
		return _test.toMillis() > _stop.toEpochMilli() - _start.toEpochMilli();
	}

	
	
	public static Instant 		of(int _year, int _month, int _day) {
		return LocalDate.of(_year, _month, _day).atStartOfDay().toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		of(int _year, int _month, int _day, int _hour, int _minute, int _second, int _millis) {
		return LocalDate.of(_year, _month, _day).atTime(_hour, _minute, _second, (int) (_millis / 1e3)).toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		of(int year, MonthDay _dayOfYear) {
		return LocalDate.of(year, _dayOfYear.getMonth(), _dayOfYear.getDayOfMonth()).atStartOfDay().toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		of(int year, MonthDay _dayOfYear, LocalTime instant_time) {
		return LocalDate.of(year, _dayOfYear.getMonth(), _dayOfYear.getDayOfMonth()).atTime(instant_time).toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		of(LocalDateTime _datetime) {
		return _datetime.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		of(LocalDate _date) {
		return _date.atStartOfDay().toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		of(LocalDate _date, LocalTime _time) {
		return LocalDateTime.of(_date, _time).toInstant(Time.DEFAULT_ZONEOFFSET);
	}

	public static Instant 		add(Instant _instant, Duration _during) {
		return _instant.plusMillis(_during.toMillis());
	}
	public static Instant 		add(Instant _instant, Period _period) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusYears(_period.getYears());
		ldt = ldt.plusMonths(_period.getMonths());
		ldt = ldt.plusDays(_period.getDays());

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		add(Instant _instant, Interval _interval) {
		return _interval.addTo(_instant);
		/*
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, DEFAULT_ZONEID);
//		ldt = ldt.plusYears(_period.getYears());
//		ldt = ldt.plusMonths(_period.getMonths());
//		ldt = ldt.plusDays(_period.getDays());

		return ldt.toInstant(DEFAULT_ZONEOFFSET);
		*/
	}
	public static Instant 		add(Instant _instant, long _value, Time.Unit _unit) {
		switch(_unit) {
		case MILLENIUM   : return addYears		(_instant, 1000 * _value);
		case CENTURY     : return addYears		(_instant, 100 * _value);
		case DECADE      : return addYears		(_instant, 10 * _value);
		case YEAR        : return addYears		(_instant, _value);
		case MONTH       : return addMonths		(_instant, _value);
		case WEEK        : return addDays		(_instant, 7 * _value);
		case DAY         : return addDays		(_instant, _value);
		case HOUR        : return addHours		(_instant, _value);
		case MINUTE      : return addMinutes	(_instant, _value);
		case SECOND      : return addSeconds	(_instant, _value);
		case MILLISECOND : return addMillis		(_instant, _value);
		case NANOSECOND  : return addNanos		(_instant, _value);		
		};
		return null;
/* TODO:: Restore Java15 Features
		return switch(_unit) {
		case MILLENIUM   -> addYears	(_instant, 1000 * _value);
		case CENTURY     -> addYears	(_instant, 100 * _value);
		case DECADE      -> addYears	(_instant, 10 * _value);
		case YEAR        -> addYears	(_instant, _value);
		case MONTH       -> addMonths	(_instant, _value);
		case WEEK        -> addDays		(_instant, 7 * _value);
		case DAY         -> addDays		(_instant, _value);
		case HOUR        -> addHours	(_instant, _value);
		case MINUTE      -> addMinutes	(_instant, _value);
		case SECOND      -> addSeconds	(_instant, _value);
		case MILLISECOND -> addMillis	(_instant, _value);
		case NANOSECOND  -> addNanos	(_instant, _value);		
		};
*/
	}

	public static Instant 		add(Instant _instant, int _years, int _months, int _days) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusYears  (_years);
		ldt = ldt.plusMonths (_months);
		ldt = ldt.plusDays   (_days);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}

	public static Instant 		addYears(Instant _instant, long _years) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusYears  (_years);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		addMonths(Instant _instant, long _months) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusMonths (_months);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		addDays(Instant _instant, long _delta) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusDays   (_delta);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		addHours(Instant _instant, long _hours) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusHours  (_hours);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		addMinutes(Instant _instant, long _minutes) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusMinutes   (_minutes);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		addSeconds(Instant _instant, long _seconds) {
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		ldt = ldt.plusSeconds   (_seconds);

		return ldt.toInstant(Time.DEFAULT_ZONEOFFSET);
	}
	public static Instant 		addMillis(Instant _instant, long _millis) {
		return _instant.plusMillis(_millis);
/*
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, DEFAULT_ZONEID);
		ldt = ldt.plusNanos((long) (1e3 * _millis));

		return ldt.toInstant(DEFAULT_ZONEOFFSET);
*/
	}
	public static Instant 		addNanos(Instant _instant, long _nanos) {
		return _instant.plusNanos(_nanos);
/*
		LocalDateTime ldt = LocalDateTime.ofInstant(_instant, DEFAULT_ZONEID);
		ldt = ldt.plusNanos(_nanos);

		return ldt.toInstant(DEFAULT_ZONEOFFSET);
*/
	}

	public static Instant 		asStartOfYear(Instant _instant) {
		LocalDateTime ldt = of(_instant);
		return of(ldt.getYear(), 1, 1, 0, 0, 0, 0);
	}
	public static Instant 		asStartOfMonth(Instant _instant) {
		LocalDateTime ldt = of(_instant);
		return of(ldt.getYear(), ldt.getMonthValue(), 1, 0, 0, 0, 0);
	}
	public static Instant 		asStartOfWeek(Instant _instant) {
		LocalDateTime ldt = of(_instant);
					  ldt = ldt.minusDays(ldt.getDayOfWeek().getValue() - 1);

		return of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), 0, 0, 0, 0);
	}
	public static Instant 		asStartOfDay(Instant _instant) {
		LocalDateTime ldt = of(_instant);
		return of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), 0, 0, 0, 0);
	}
	public static Instant 		asStartOfHour(Instant _instant) {
		LocalDateTime ldt = of(_instant);
		return of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), 0, 0, 0);
	}
	public static Instant 		asStartOfMinute(Instant _instant) {
		LocalDateTime ldt = of(_instant);
		return of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), 0, 0);
	}
	public static Instant 		asStartOfSecond(Instant _instant) {
		LocalDateTime ldt = of(_instant);
		return of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond(), 0);
	}

	public static Instant 		previousMonth(Instant _instant) {
		return of(date(_instant).minusMonths(1));
	}
	public static Instant 		previousMonth(Instant _instant, int _dayOfMonth, LocalTime _hour) {
		return of(date(_instant).minusMonths(1).atTime(_hour));
	}
	public static Instant 		nextMonth(Instant _instant) {
		return of(date(_instant).plusMonths(1));
	}
	public static Instant 		nextMonth(Instant _instant, int _dayOfMonth, LocalTime _hour) {
		return of(date(_instant).plusMonths(1).atTime(_hour));
	}

	public static Instant 		previousDayOfMonth(Instant _instant, int _dayOfMonth) {
		LocalDate     instant_date     = date(_instant);

		boolean prevMonth = instant_date.getDayOfMonth() < _dayOfMonth ? true : false;
		boolean prevYear  = instant_date.getMonthValue() + (prevMonth ? -1 : 0) < 1 ? false : true;

		LocalDate nextDayOfMonth = LocalDate.of(prevYear ? instant_date.getYear() - 1 : instant_date.getYear(), 
												prevYear ? 12 : instant_date.getMonthValue() + (prevMonth ? -1 : 0), 
												_dayOfMonth);
		
		return of(nextDayOfMonth);
	}
	public static Instant 		previousDayOfMonth(Instant _instant, int _dayOfMonth, LocalTime _hour) {
		LocalDate     instant_date     = date(_instant);

		boolean prevMonth = instant_date.getDayOfMonth() < _dayOfMonth ? true : false;
		boolean prevYear  = instant_date.getMonthValue() + (prevMonth ? -1 : 0) < 1 ? true : false;

		LocalDate nextDayOfMonth = LocalDate.of(prevYear ? instant_date.getYear() - 1 : instant_date.getYear(), 
												prevYear ? 12 : instant_date.getMonthValue() + (prevMonth ? -1 : 0), 
												_dayOfMonth);
		
		return of(nextDayOfMonth, _hour);
	}
	public static Instant 		nextDayOfMonth(Instant _instant, int _dayOfMonth) {
		LocalDate     instant_date     = date(_instant);

		boolean nextMonth = instant_date.getDayOfMonth() < _dayOfMonth ? false : true;
		boolean nextYear  = instant_date.getMonthValue() + (nextMonth ? 1 : 0) > 12 ? true : false;

		LocalDate nextDayOfMonth = LocalDate.of(nextYear ? instant_date.getYear() + 1 : instant_date.getYear(), 
												nextYear ? 1 : instant_date.getMonthValue() + (nextMonth ? 1 : 0), 
												_dayOfMonth);
		
		return of(nextDayOfMonth);
	}
	public static Instant 		nextDayOfMonth(Instant _instant, int _dayOfMonth, LocalTime _hour) {
		LocalDateTime instant_datetime = LocalDateTime.ofInstant(_instant, Time.DEFAULT_ZONEID);
		LocalDate     instant_date     = date(_instant);
		LocalTime     instant_time     = time(_instant);
//		LocalTime     instant_time     = _instant.atZone(DEFAULT_ZONEID);
		
		boolean nextMonth = instant_date.getDayOfMonth() < _dayOfMonth ? false : true;
		boolean nextYear  = instant_date.getMonthValue() + (nextMonth ? 1 : 0) > 12 ? true : false;

		LocalDate nextDayOfMonth = LocalDate.of(nextYear ? instant_date.getYear() + 1 : instant_date.getYear(), 
												nextYear ? 1 : instant_date.getMonthValue() + (nextMonth ? 1 : 0), 
												_dayOfMonth);
		
		return of(nextDayOfMonth, _hour);
	}

	public static Instant 		previousDayOfYear(Instant _instant, MonthDay _dayOfYear) {
		LocalDate     instant_date     = date(_instant);
		MonthDay      instant_day      = MonthDay.of(instant_date.getMonth(), instant_date.getDayOfMonth());

		if(instant_day.isAfter(_dayOfYear) || instant_day.equals(_dayOfYear))
			return of(instant_date.getYear(), _dayOfYear);
		else
			return of(instant_date.getYear() - 1, _dayOfYear);
	}
	public static Instant 		previousDayOfYear(Instant _instant, MonthDay _dayOfYear, LocalTime _hour) {
		LocalDate     instant_date     = date(_instant);
		MonthDay      instant_day      = MonthDay.of(instant_date.getMonth(), instant_date.getDayOfMonth());

		if(instant_day.isAfter(_dayOfYear))
			return of(instant_date.getYear(), _dayOfYear, _hour);
		else
			return of(instant_date.getYear() - 1, _dayOfYear, _hour);
	}
	public static Instant 		nextDayOfYear(Instant _instant, MonthDay _dayOfYear) {
		LocalDate     instant_date     = date(_instant);
		MonthDay      instant_day      = MonthDay.of(instant_date.getMonth(), instant_date.getDayOfMonth());

		if(instant_day.isBefore(_dayOfYear))
			return of(instant_date.getYear(), _dayOfYear);
		else
			return of(instant_date.getYear() + 1, _dayOfYear);
	}
	public static Instant 		nextDayOfYear(Instant _instant, MonthDay _dayOfYear, LocalTime _hour) {
		LocalDate     instant_date     = date(_instant);
		MonthDay      instant_day      = MonthDay.of(instant_date.getMonth(), instant_date.getDayOfMonth());

		if(instant_day.isBefore(_dayOfYear))
			return of(instant_date.getYear(), _dayOfYear, _hour);
		else
			return of(instant_date.getYear() + 1, _dayOfYear, _hour);
	}

}
