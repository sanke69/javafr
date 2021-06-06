package fr.java.sdk.patterns.timeable;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Times {
	private static final ZoneId     		prefZoneId     	= ZoneId.of("Europe/Paris");
	private static final ZoneOffset 		prefZoneOffset 	= prefZoneId.getRules().getOffset(Instant.now());

	public static final ZoneId 				localZoneID     = ZoneId.systemDefault();
	public static final ZoneOffset			localZoneOffset = ZoneOffset.of("+1");

	public static final DateTimeFormatter 	formatSQL 		= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter 	formatDate 		= DateTimeFormatter.ofPattern("yyyy/MM/dd");
	public static final DateTimeFormatter 	formatDateTime1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	public static final DateTimeFormatter 	formatDateTime2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public static final String sqlFormat = "yyyy-MM-dd HH:mm:ss";

	public static final Instant 		toInstant(Long _epoch_ms) {
		if(_epoch_ms == null)
			return null;
		return toInstant((long) _epoch_ms);
	}
	public static final Instant 		toInstant(long _epoch_ms) {
		return Instant.ofEpochMilli(_epoch_ms);
	}
	public static final Instant 		toInstant(LocalDateTime _ldt) {
		return toInstant(_ldt, prefZoneOffset);
	}
	public static final Instant 		toInstant(LocalDateTime _ldt, ZoneOffset _zoneOffset) {
		if(_ldt == null)
			return null;
		return _ldt.toInstant(_zoneOffset);
	}

	public static final LocalDateTime 	toLocalDateTime(Long _timestamp) {
		if(_timestamp == null)
			return null;
		return toLocalDateTime((long) _timestamp);
	}
	public static final LocalDateTime 	toLocalDateTime(long _timestamp) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(_timestamp), prefZoneId);
	}
	public static final LocalDateTime 	toLocalDateTime(Instant _instant) {
		if(_instant == null)
			return null;
		return LocalDateTime.ofInstant(_instant, prefZoneId);
	}
	public static final LocalDateTime 	toLocalDateTime(Instant _instant, ZoneId _zoneId) {
		if(_instant == null)
			return null;
		return LocalDateTime.ofInstant(_instant, _zoneId);
	}

	public static final LocalDate 		toLocalDate(Long _timestamp) {
		if(_timestamp == null)
			return null;
		return toLocalDateTime(_timestamp).toLocalDate();
	}
	public static final LocalDate 		toLocalDate(long _timestamp) {
		return toLocalDateTime(_timestamp).toLocalDate();
	}
	public static final LocalDate 		toLocalDate(Instant _instant) {
		if(_instant == null)
			return null;
		return toLocalDateTime(_instant).toLocalDate();
	}
	public static final LocalDate 		toLocalDate(Instant _instant, ZoneId _zoneId) {
		if(_instant == null)
			return null;
		return toLocalDateTime(_instant, _zoneId != null ? _zoneId : prefZoneId).toLocalDate();
	}

	public static final LocalTime 		toLocalTime(Instant _instant) {
		if(_instant == null)
			return null;
		return toLocalDateTime(_instant).toLocalTime();
	}
	public static final LocalTime 		toLocalTime(Instant _instant, ZoneId _zoneId) {
		if(_instant == null)
			return null;
		return toLocalDateTime(_instant, _zoneId != null ? _zoneId : prefZoneId).toLocalTime();
	}

	public static final LocalDateTime 	parse(String _date) {
		try {
			return LocalDateTime.parse(_date, formatDateTime1);
		} catch(DateTimeParseException first) {
			try {
				return LocalDateTime.parse(_date, formatDateTime2);
			} catch(DateTimeParseException second) {
				try {
					return LocalDate.parse(_date, formatDate).atStartOfDay();
				} catch(DateTimeParseException third) {
					try {
						return LocalDateTime.parse(_date);
					} catch(DateTimeParseException fourth) {
						fourth.printStackTrace();
						return null;
					}
				}
			}
		}
	}

	public static final String        	toString(LocalDateTime _date) {
		return _date.format(formatDateTime2);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static final long toEpochMilli(Instant _instant) {
		return _instant.toEpochMilli();
	}
	public static final long toEpochSecond(Instant _instant) {
		return _instant.getEpochSecond();
	}
	public static final long getLongFromLocalDateTime(LocalDateTime _datetime) {
		return _datetime.toEpochSecond(prefZoneOffset);
	}
	public static final long getLongFromLocalDate(LocalDate _date) {
		return _date.atTime(0, 0).toEpochSecond(prefZoneOffset);
	}
	public static final long getLongFromLocalDate(LocalDate _date, LocalTime _time) {
		return _date.atTime(_time).toEpochSecond(prefZoneOffset);
	}

	
	
	
	
	
	
	
	
	
	public static long buildLDT() {
		return LocalDate
				    .of(2001, 4, 27)
				    .atTime(5, 55, 0)
				    .atZone(ZoneId.of("Europe/Paris"))
				    .toEpochSecond();
	}


//	public static final ZoneId 				localZoneID     = ZoneId.of("Europe/Paris");
//															  ZoneId.of(ZoneId.SHORT_IDS.get("PST"))
//	public static final ZoneOffset			localZoneOffset = ZoneOffset.from(ZonedDateTime.now(localZoneID));

	private static final SimpleDateFormat	GMT	= new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat	PAR	= new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat	SYD	= new SimpleDateFormat("yyyy-MM-dd");
	static {
		GMT.setTimeZone(TimeZone.getTimeZone("GMT"));
		PAR.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		SYD.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
	}
	
	
	
	public static String hashTag() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
		
		try {
		    return LocalDateTime.ofInstant(Instant.now(), localZoneOffset).format(format);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted with pattern!%n", Instant.now(), "yyyyMMdd.HHmmss");
		    throw exc;
		}
	}
	public static String hashDay() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		try {
		    return LocalDateTime.ofInstant(Instant.now(), localZoneOffset).format(format);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted with pattern!%n", Instant.now(), "yyyyMMdd");
		    throw exc;
		}
	}


	/**
	 * Get an Instant from local parameters: year, month, day, hour, minutes, seconds
	 * 
	 * @return an Instant
	 */
	public static final Instant instant(int _y, int _m, int _d, int _H, int _M, int _S) {
		return LocalDateTime.of(_y, _m, _d, _H, _M, _S).toInstant(localZoneOffset);
	}

	/**
	 * Get an Instant from string at local zone
	 * 
	 * @return the corresponding Instant
	 */
	public static final Instant parse(String _date, String _format) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(_format);
		
		try {
			return LocalDateTime.parse(_date, format).toInstant(localZoneOffset);
		} catch (DateTimeParseException e) {
			try {
				return LocalDate.parse(_date, format).atStartOfDay().toInstant(localZoneOffset);
			} catch (DateTimeParseException e2) {
			    throw e2;
			}
		}
	}

	/**
	 * Return a string from Instant using format _format
	 * @param _instant
	 */
	public static final String format(Instant _instant, String _format) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(_format);
		
		try {
		    return LocalDateTime.ofInstant(_instant, localZoneOffset).format(format);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted with pattern!%n", _instant, _format);
		    throw exc;
		}
	}
/*
	public static final Instant fromSQL(java.sql.Timestamp _sqlDateTimestamp) {		
		return new java.util.Date(_sqlDateTimestamp.getTime()).toInstant();
   }
	public static final Instant fromSQL(java.sql.Time _sqlDateTime) {		
		return new java.util.Date(_sqlDateTime.getTime()).toInstant();
   }
	public static final Instant fromSQL(java.sql.Date _sqlDate) {		
		return new java.util.Date(_sqlDate.getTime()).toInstant();
		/*
		Instant        timestamp = utilDate.toInstant();
		
		LocalDateTime date = LocalDateTime.ofInstant(timestamp, localZoneID);

		//Calendar to Instant
		Instant time = Calendar.getInstance().toInstant();
		System.out.println(time);
		//TimeZone to ZoneId
		ZoneId defaultZone = TimeZone.getDefault().toZoneId();
		System.out.println(defaultZone);
    
		//ZonedDateTime from specific Calendar
		ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
		System.out.println(gregorianCalendarDateTime);
    
		//Date API to Legacy classes
		Date dt = Date.from(Instant.now());
		System.out.println(dt);
    
		TimeZone tz = TimeZone.getTimeZone(defaultZone);
		System.out.println(tz);
    
		GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
		System.out.println(gc);
* /        
   }
	*/
	public static final String  toSQL(Instant _instant) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(sqlFormat);
		
		try {
		    return LocalDateTime.ofInstant(_instant, localZoneOffset).format(format);
		} catch (DateTimeException exc) { throw exc; }
	}


	public static final Instant plusMilli(Instant _date, long _milli) {
	    return _date.plus(_milli, ChronoField.MILLI_OF_DAY.getBaseUnit());
	}

	public static String humanReadableFormat(long _ms) {
	    return String.format("%02d:%02d:%02d", 
	    	    TimeUnit.MILLISECONDS.toHours(_ms),
	    	    TimeUnit.MILLISECONDS.toMinutes(_ms) - 
	    	    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(_ms)),
	    	    TimeUnit.MILLISECONDS.toSeconds(_ms) - 
	    	    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(_ms)));
	}
	public static String format_ms(final long _millis) {
		long ms = _millis;
		if(ms < 0)
			ms *= -1;

	    long millis  = ms - TimeUnit.MILLISECONDS.toSeconds(ms) * 1000;
	    long seconds = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms));
	    long minutes = TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms));
	    long hours   = TimeUnit.MILLISECONDS.toHours(ms)   - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(ms));
	    long days    = TimeUnit.MILLISECONDS.toDays(ms);

	    StringBuilder b = new StringBuilder();
	    if(_millis < 0)
	    	b.append("- ");
	    if(days > 0)
	    	b.append(String.valueOf(days)).append("d ");
	    b.append(hours == 0 ? "00" : hours < 10 ? String.valueOf("0" + hours) : String.valueOf(hours));
	    b.append(":");
	    b.append(minutes == 0 ? "00" : minutes < 10 ? String.valueOf("0" + minutes) : String.valueOf(minutes));
	    b.append(":");
	    b.append(seconds == 0 ? "00" : seconds < 10 ? String.valueOf("0" + seconds) :  String.valueOf(seconds));
	    b.append(".");
	    b.append(millis == 0 ? "000" : millis < 10 ? String.valueOf("00" + millis) : millis < 100 ? String.valueOf("0" + millis) :  String.valueOf(millis));
	    return b.toString(); 
	 }
	
	
	
	public static byte[] writeJulianDate(Instant _instant) {
		ByteBuffer bb = ByteBuffer.allocate(8);

		LocalDateTime date = toLocalDateTime(_instant);
		
		bb.putInt(0, julianDay(_instant));
		bb.putInt(4, date.getHour() * 60 * 60 * 1000 + date.getMinute() * 60 * 1000 + date.getSecond() * 1000);

		return bb.array();
	}

	public static int julianDay(Instant _instant) {
		LocalDateTime date = toLocalDateTime(_instant);

		int year  = date.getYear();
		int month = date.getMonthValue() + 1;
		int day   = date.getDayOfMonth();

//		double extra = (100.0 * year) + month - 190002.5;
		long l = (long) ((367.0 * year) -
				(Math.floor(7.0 * (year + Math.floor((month + 9.0) / 12.0)) / 4.0)) +
				Math.floor((275.0 * month) / 9.0) +
				day);

		// Unsigned types are too complicated they said... Only having signed ones makes it easier they said
		if(l > Integer.MAX_VALUE)
			return ~((int) l & Integer.MAX_VALUE);
		else
			return (int) (l & Integer.MAX_VALUE);
	}
	
	
	
	public static void temp() {
/*
		Date input = new Date();
		LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		Date input = new Date();
		Instant instant = input.toInstant();
		
		Date input = new Date();
		Instant instant = input.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		
		Date input = new Date();
		Instant instant = input.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		LocalDate date = zdt.toLocalDate();
*/
	}
	

	public static void main(String[] args) {
		System.out.println(toLocalDateTime(System.currentTimeMillis()));
		System.out.println(toLocalDate(System.currentTimeMillis()));

		System.out.println(toLocalDateTime(Instant.now()));
		System.out.println(toLocalDate(Instant.now()));
		System.out.println(toLocalTime(Instant.now()));

		System.out.println(toEpochSecond(Instant.now()));
		System.out.println(getLongFromLocalDate(LocalDate.now()));
		System.out.println(getLongFromLocalDateTime(LocalDateTime.now()));
	}

	
}
