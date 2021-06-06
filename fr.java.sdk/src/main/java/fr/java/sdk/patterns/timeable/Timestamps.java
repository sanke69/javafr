package fr.java.sdk.patterns.timeable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.Timestamp;
import fr.java.lang.properties.Timestamp.FORMAT;

public final class Timestamps {

	public static final Timestamp now() {
		return new TimestampImpl(System.currentTimeMillis());
	}
	public static final Timestamp of(long _epoch_ms) {
		return new TimestampImpl(_epoch_ms);
	}
	public static final Timestamp of(String _timestamp, FORMAT _fmt) {
		return new TimestampImpl(_timestamp, _fmt);
	}
	public static final Timestamp of(Instant _instant) {
		return new TimestampImpl(_instant.toEpochMilli());
	}
	public static final Timestamp of(LocalDateTime _ldt) {
		return new TimestampImpl(_ldt.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli());
	}
	public static final Timestamp of(LocalDateTime _ldt, ZoneId _zid) {
		return new TimestampImpl(_ldt.atZone(_zid).toInstant().toEpochMilli());
	}

	public static final Timestamp epoch() {
		return new TimestampImpl(0);
	}
	public static final Timestamp endOfWorld() {
		return Timestamps.of(Long.MAX_VALUE);
	}

	public static final Timestamp snk() {
		LocalDateTime ldt = LocalDateTime.of(1981, 4, 27, 5, 55, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/Paris"));
		return new TimestampImpl(zdt.toInstant().toEpochMilli());
	}
	public static final Timestamp ina() {
		LocalDateTime ldt = LocalDateTime.of(1989, 9, 6, 0, 0, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Africa/Bamako"));
		return new TimestampImpl(zdt.toInstant().toEpochMilli());
	}

	public static class TimestampImpl implements Timestamp {
	
		public static final long S_IN_MS = 1000;
		public static final long M_IN_MS = 60 * S_IN_MS;
		public static final long H_IN_MS = 60 * M_IN_MS;
		public static final long D_IN_MS = 24 * H_IN_MS; 
		
		public long epoch_ms;
	
		public TimestampImpl(long _t_ms) {
			epoch_ms = _t_ms;
		}
		public TimestampImpl(double _t_s) {
			epoch_ms = (long) (_t_s * S_IN_MS);
		}
		public TimestampImpl(double _t, FORMAT _f) {
			epoch_ms = (long) (_t * S_IN_MS);
		}
		public TimestampImpl(String _s, FORMAT _f) {
	    	String[] timetokens = _s.split(":");
	    	if(timetokens.length == 2) {
	    		double m_in_ms = Long.valueOf(timetokens[0])   * M_IN_MS;
	    		double s_in_ms = Double.valueOf(timetokens[1]) * S_IN_MS;
	
	    		epoch_ms = (long) (m_in_ms + s_in_ms);
	    	}
	    	if(timetokens.length == 3) {
	    		double h_in_ms = Long.valueOf(timetokens[0])   * H_IN_MS;
	    		double m_in_ms = Long.valueOf(timetokens[1])   * M_IN_MS;
	    		double s_in_ms = Double.valueOf(timetokens[2]) * S_IN_MS;
	
	    		epoch_ms = (long) (h_in_ms + m_in_ms + s_in_ms);
	    	}
		}
	
		public Long delta(Timestamp _timestamp) {
			return epoch_ms - _timestamp.toEpochMilli();
		}
	
		public Timestamp plus(long _ms) {
			return Timestamps.of(epoch_ms + _ms);
		}
		public Timestamp plusEquals(long _ms) {
			epoch_ms += _ms;
			return this;
		}
	
		public Timestamp minus(long _ms) {
			return Timestamps.of(epoch_ms - _ms);
		}
		public Timestamp minusEquals(long _ms) {
			epoch_ms -= _ms;
			return this;
		}
	
		public boolean before(Timestamp _timestamp) {
			return epoch_ms - _timestamp.toEpochMilli() < 0 ? true : false;
		}
		public boolean after(Timestamp _timestamp) {
			return epoch_ms - _timestamp.toEpochMilli() > 0 ? true : false;
		}
		public boolean between(Timestamp _from, Timestamp _to) {
			return epoch_ms - _from.toEpochMilli() >= 0 && epoch_ms - _to.toEpochMilli() <= 0 ? true : false;
		}
	
		public Long toEpochMilli() {
			return epoch_ms;
		}
	
		public Timestamp clone() {
			return Timestamps.of(epoch_ms);
		}
		
	    public boolean equals(Object obj) {
	    	if(obj instanceof Integer)
	    		return epoch_ms == ((Integer) obj).longValue();
	    	if(obj instanceof Long)
	    		return epoch_ms == ((Long) obj).longValue();
	    	if(obj instanceof Timestamp)
	    		return epoch_ms == ((Timestamp) obj).toEpochMilli();
	    	
	    	throw new NotYetImplementedException();
	    }
	
		public String toString() {
	/**
			DateTimeFormatter formatter =
				    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
				                     .withLocale( Locale.FRENCH )
				                     .withZone( ZoneId.systemDefault() );
	/*/
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone( ZoneId.systemDefault() );
	/**/
			return formatter.format( Instant.ofEpochMilli(epoch_ms) );
		}
	
	}


}
