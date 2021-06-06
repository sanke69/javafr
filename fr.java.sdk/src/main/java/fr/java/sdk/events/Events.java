package fr.java.sdk.events;

import java.util.Optional;

import fr.java.events.EventListenerList;
import fr.java.lang.enums.State;
import fr.java.lang.properties.Timestamp;
import fr.java.patterns.stateable.StateEvent;
import fr.java.patterns.stateable.Stateable;
import fr.java.patterns.timeable.TimeEvent;
import fr.java.player.Player;
import fr.java.player.PlayerEvent;
import fr.java.sdk.patterns.timeable.Timestamps;

public class Events {

	public static EventListenerList newListenerList() {
		return new EventListenerListImpl();
	}

	public static class StateEventBase extends EventBase implements StateEvent {
		private static final long serialVersionUID = 7578433779532097659L;
	
		Stateable source;
		Timestamp timestamp;
		State     oldStatus, newStatus;
	
		public StateEventBase(Stateable _source, State _old, State _new) {
			super(_source);
			source    = _source;
			timestamp = Timestamps.now();
			oldStatus = _old;
			newStatus = _new;
		}
		public StateEventBase(Stateable _source, State _old, State _new, Timestamp  _timestamp) {
			super(_source);
			source    = _source;
			timestamp = _timestamp;
			oldStatus = _old;
			newStatus = _new;
		}
	
		@Override
		public Stateable 	getSource() {
			return source;
		}
	
		public Timestamp 	getTimestamp() {
			return timestamp;
		}
	
		public State 		getState() {
			return newStatus;
		}
		public State 		getPrevious() {
			return oldStatus;
		}
	
	}

	public static class TimeEventBase extends EventBase implements TimeEvent {
		private static final long serialVersionUID = -4758453822069486336L;
	
		private Type   		type;
		private Timestamp 	timestamp;
		private Object 		value;

		public TimeEventBase(Object _source, Type _type, Timestamp _timestamp) {
			super(_source);
			source    = _source;
			type      = _type;
			timestamp = _timestamp;
		}
		public TimeEventBase(Object _source, Type _type, Timestamp _timestamp, Object _value) {
			super(_source);
			source    = _source;
			type      = _type;
			timestamp = _timestamp;
			value     = _value instanceof Optional ? ((Optional<?>) _value).get() : _value;
		}
		public TimeEventBase(Object _source, Timestamp _timestamp) {
			this(_source, Type.NEW_TIME, _timestamp);
		}
		public TimeEventBase(Object _source, Timestamp _timestamp, Object _value) {
			this(_source, Type.NEW_TIME, _timestamp, _value);
		}

		@Override
		public Type 			getType() {
			return type;
		}
		@Override
		public Timestamp 		getTimestamp() {
			return timestamp;
		}
		@Override
		public Optional<Object> getValue() {
			return Optional.ofNullable(value);
		}
	
	}

	public static class PlayerEventBase<T> extends EventBase implements PlayerEvent {
		private static final long serialVersionUID = 4132173470764719095L;

		Timestamp  	timestamp  = Timestamps.of(-1);
		long 		frameIndex = -1;
		Optional<T>	frame;
	
		public PlayerEventBase(Player<T> _source, T _frame) {
			super(_source);
			timestamp = Timestamps.now();
			frame     = Optional.ofNullable(_frame);
		}
		public PlayerEventBase(Player<T> _source, Optional<T> _frame) {
			super(_source);
			timestamp = Timestamps.now();
			frame     = _frame;
		}
		public PlayerEventBase(Player<T> _source, T _frame, Timestamp _timestamp) {
			super(_source);
			timestamp = _timestamp;
			frame     = Optional.ofNullable(_frame);
		}
		public PlayerEventBase(Player<T> _source, Optional<T> _frame, Timestamp _timestamp) {
			super(_source);
			timestamp = _timestamp;
			frame     = _frame;
		}
		public PlayerEventBase(Player<T> _source, T _frame, Timestamp _timestamp, long _frameIndex) {
			super(_source);
			timestamp  = _timestamp;
			frameIndex = _frameIndex;
			frame      = Optional.ofNullable(_frame);
		}
		public PlayerEventBase(Player<T> _source, Optional<T> _frame, Timestamp _timestamp, long _frameIndex) {
			super(_source);
			timestamp  = _timestamp;
			frameIndex = _frameIndex;
			frame      = _frame;
		}
	
		@SuppressWarnings("unchecked")
		@Override
		public Player<T> 	getSource() {
			return (Player<T>) super.getSource();
		}

		@Override
		public Timestamp 	getTimestamp() {
			return timestamp;
		}

		@Override
		public Long 		getFrameIndex() {
			return frameIndex;
		}

		@Override
		public Optional<T>	getFrame() {
			return frame;
		}
	
	};



	public static StateEvent 		newStateEvent(Stateable _source, State _old, State _new) {
		return new StateEventBase(_source, _old, _new);
	}
	public static StateEvent 		newStatusEvent(Stateable _source, State _old, State _new, Timestamp  _timestamp) {
		return new StateEventBase(_source, _old, _new,  _timestamp);
	}

	public static <T> PlayerEvent newPlayerEvent(Player<T> _source, T _frame) {
		return new PlayerEventBase<T>(_source, _frame);
	}
	public static <T> PlayerEvent newPlayerEvent(Player<T> _source, Optional<T> _frame) {
		return new PlayerEventBase<T>(_source, _frame);
	}
	public static <T> PlayerEvent newPlayerEvent(Player<T> _source, T _frame, Timestamp _timestamp) {
		return new PlayerEventBase<T>(_source, _frame, _timestamp);
	}
	public static <T> PlayerEvent newPlayerEvent(Player<T> _source, Optional<T> _frame, Timestamp _timestamp) {
		return new PlayerEventBase<T>(_source, _frame, _timestamp);
	}
	public static <T> PlayerEvent newPlayerEvent(Player<T> _source, T _frame, Timestamp _timestamp, long _frameIndex) {
		return new PlayerEventBase<T>(_source, _frame, _timestamp, _frameIndex);
	}
	public static <T> PlayerEvent newPlayerEvent(Player<T> _source, Optional<T> _frame, Timestamp _timestamp, long _frameIndex) {
		return new PlayerEventBase<T>(_source, _frame, _timestamp, _frameIndex);
	}

	public static TimeEvent 		 newTimeEvent(Object _source, TimeEvent.Type _type, Timestamp _timestamp) {
		return new TimeEventBase(_source, _type, _timestamp);
	}
	public static TimeEvent 		 newTimeEvent(Object _source, TimeEvent.Type _type, Timestamp _timestamp, Object _value) {
		return new TimeEventBase(_source, _type, _timestamp, _value);
	}
}
