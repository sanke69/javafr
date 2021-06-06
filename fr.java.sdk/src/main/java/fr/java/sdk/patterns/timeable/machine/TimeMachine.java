package fr.java.sdk.patterns.timeable.machine;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import fr.java.events.EventPublisher;
import fr.java.lang.enums.state.ServiceState;
import fr.java.lang.properties.Timestamp;
import fr.java.patterns.timeable.TimeEvent;
import fr.java.patterns.timeable.TimeListener;
import fr.java.sdk.events.Events;
import fr.java.sdk.patterns.timeable.timelines.FixedStepTimeLine;
import fr.java.sdk.patterns.timeable.timelines.FusionTimeLine;
import fr.java.sdk.patterns.timeable.timelines.RealTimeLine;
import fr.java.sdk.player.PlayerAdapter;
import fr.java.timeline.TimeLine;

public class TimeMachine<V> extends PlayerAdapter<V> implements EventPublisher {
	private Thread   	playingThread;
	private TimeLine<V> timeline;

	public TimeMachine() {
		super(true);
		setFPS(60);

		playingThread = null;
	}
	public TimeMachine(TimeLine<V> _timeline) {
		super(true);
		setFPS(1);
		timeline = _timeline;
		playingThread = null;
	}
	public TimeMachine(TimeLine<V> _timeline, double _fps) {
		super(true);
		setFPS(_fps);
		timeline = _timeline;
		playingThread = null;
	}

	@Override
	protected Optional<V> 	readNextFrame() throws IOException {
/*
		TimeLine<V> tl = null;
		
		if(tl instanceof RealTimeLine<V> rtl)
			return rtl.getNextValue();
		if(tl instanceof FixedStepTimeLine<V> fstl)
			return fstl.getNextValue();
		if(tl instanceof FusionTimeLine<V> ftl)
			return ftl.getNextValue();
*/
		return timeline.getNextValue();
	}

	@Override
	public boolean 			tryStart() {
		Consumer<TimeLine<V>> realTimeSleep     = (realtime) -> {
			try {
				if(getFPS() > 0) {
					long dt = (long) (1000. / getFPS());
					Thread.sleep(dt);
				}
			} catch (InterruptedException e) {
				setState(ServiceState.stopping);
				playingThread = null;
			}
		};
		Consumer<TimeLine<V>> relativeTimeSleep = (relative) -> {
			try {
				if(getFPS() > 0) {
					long dt = (long) (1000. / (getPlayRate() * getFPS()));
					Thread.sleep(dt);
				}
			} catch (InterruptedException e) {
				setState(ServiceState.stopping);
				playingThread = null;
			}
		};
		Consumer<TimeLine<V>> recordTimeSleep   = (record) -> {
			try {
				if(record.getNextTimestamp() != null) {
					long deltaTime = (long) ((record.getNextTimestamp().delta(record.getCurrentTimestamp())) / getPlayRate());
					if(deltaTime > 0)
						Thread.sleep(deltaTime);
				} else
					throw new InterruptedException("End of TimeLine");
			} catch(InterruptedException e) {
				setState(ServiceState.stopping);
				playingThread = null;
			}
		};

		final Consumer<TimeLine<V>> machineSleep = 	(timeline instanceof RealTimeLine)     	? realTimeSleep :
													(timeline instanceof FusionTimeLine)	? recordTimeSleep : 
													(timeline instanceof FixedStepTimeLine) ? relativeTimeSleep :recordTimeSleep;

		if(playingThread == null) {
			playingThread = new Thread(() -> {
                try {
					setState(ServiceState.starting);
					fire(Events.newTimeEvent(this, TimeEvent.Type.START, timeline.getNextTimestamp(), timeline.getNextValue().get()));
					setState(ServiceState.started);
	
					while(!isStopped()) {
						synchronized (this) {
							while(isPaused())
								wait();
						}

						Timestamp   time  = timeline.getNextTimestamp();
						Optional<V> value = timeline.getNextValue();
						if(value.isPresent())
							fire(Events.newTimeEvent(this, TimeEvent.Type.NEW_TIME, time, value));
						else
							setState(ServiceState.stopping);

//						machineSleep.accept(timeline);
						relativeTimeSleep.accept(null);
					}
	
					setState(ServiceState.stopping);
					fire(Events.newTimeEvent(this, TimeEvent.Type.STOP, timeline.getNextTimestamp(), timeline.getNextValue().get()));
					playingThread = null;
					setState(ServiceState.stopped);

				} catch(InterruptedException e) {
					setState(ServiceState.stopping);
					playingThread = null;
				}
			});
			playingThread.start();
		}
		
		return true;
	}
	@Override
	public boolean 			tryPause() {
		setState(ServiceState.pausing);
		setState(ServiceState.paused);
		return true;
	}
	@Override
	public boolean 			tryResume() {
		setState(ServiceState.starting);
		if(playingThread != null) {
			synchronized(this) {
	             notifyAll();
	        }
			setState(ServiceState.started);
			return true;
		} else {
			setState(ServiceState.unavailable);
			return false;
		}
	}
	@Override
	public boolean 			tryStop() {
		if(playingThread != null) {
			playingThread.interrupt();
			playingThread = null;
			return true;
		}
		return false;
	}

	public void 			fire(TimeEvent _evt) {
		if(getListeners(TimeListener.class) == null)
			return ;

		for(TimeListener l : getListeners(TimeListener.class))
			l.onTimeEvent(_evt);
	}

	public void 			registerListener(TimeListener _listener) {
		getListenerList().add(TimeListener.class, _listener);
	}
	public void 			unregisterListener(TimeListener _listener) {
		getListenerList().remove(TimeListener.class, _listener);
	}

}
