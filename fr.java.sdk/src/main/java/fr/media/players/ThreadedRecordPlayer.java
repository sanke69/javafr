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
 * @file     ThreadedPlayer.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.media.players;

import java.io.IOException;
import java.util.Optional;

import fr.java.lang.enums.state.ServiceState;
import fr.java.sdk.events.Events;
import fr.java.sdk.patterns.timeable.Timestamps;
import fr.media.RecordPlayerAdapter;

public abstract class ThreadedRecordPlayer<T> extends RecordPlayerAdapter<T> implements Runnable {
	private Thread playingThread;

	public ThreadedRecordPlayer() {
		this(false, 25d);
	}
	public ThreadedRecordPlayer(boolean _isInterruptable) {
		this(_isInterruptable, 25d);
	}
	public ThreadedRecordPlayer(boolean _isInterruptable, double _fps) {
		super(_isInterruptable, _fps);

		playingThread = null;
	}

	@Override
	protected boolean 	tryStart() throws Exception {
		if(!isReady() && !isStopped() && playingThread != null)
			return false;

		media().setIndex(media().frameStart().orElse(0L));
		playingThread = new Thread(this);
		playingThread.start();

		return true;
	}
	@Override
	protected boolean 	tryPause() throws Exception {
		return playingThread != null;
	}
	@Override
	protected boolean 	tryResume() throws Exception {
		return playingThread != null;
	}
	@Override
	protected boolean 	tryStop() throws Exception {
		playingThread.interrupt();
		playingThread = null;
		return true;
	}

	@Override
	public void 		run() {
	    long thisTime  = System.currentTimeMillis();
		long lastTime  = System.currentTimeMillis();
		long pauseTime = (long) (1e3 / (getPlayRate() * getFPS()));

		while(!isStopped() && !isFaulted() && !Thread.currentThread().isInterrupted()) {

			try {
			    thisTime = System.currentTimeMillis();

				if(!isPaused()) {
					if(media().frameStop().isPresent() && media().frameIndex().get() > media().frameStop().get()) {
						setState(ServiceState.stopping);
						playingThread.interrupt();
					}

					Optional<? extends T> frame = readNextFrame();
					if(frame.isPresent())
						fire(Events.newPlayerEvent(this, frame.get(), Timestamps.of(thisTime), media().frameIndex().get()));

					else {
						setState(ServiceState.stopping);
						playingThread.interrupt();
					}
				}

				pauseTime = (long) ((1e3 - (thisTime - lastTime)) / (getPlayRate() * getFPS()));
		        lastTime  = thisTime;
				Thread.sleep(pauseTime > 0 ? pauseTime : 0);

			} catch (InterruptedException e) {
				setState(ServiceState.stopping);
			} catch (IOException e) {
				setState(ServiceState.faulted);
			}

		}

		fire(Events.newPlayerEvent(this, Optional.empty()));
		stop();
	}

}
