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
 * @file     TimeDrivenPlayer.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.sdk.player.players;

import java.io.IOException;
import java.util.Optional;

import fr.java.lang.enums.state.ServiceState;
import fr.java.patterns.timeable.TimeEvent;
import fr.java.patterns.timeable.TimeListener;
import fr.java.sdk.events.Events;
import fr.java.sdk.patterns.timeable.Timestamps;
import fr.java.sdk.player.PlayerAdapter;

public abstract class TimeDrivenPlayer<T> extends PlayerAdapter<T> implements TimeListener {
	private long    last_update;
	private boolean isPlaying;

	public TimeDrivenPlayer() {
		super(true);

		last_update = -1;
		isPlaying   = false;
	}

	@Override
	protected boolean tryStart() throws Exception {
		setState(ServiceState.starting);
		isPlaying = true;
		setState(ServiceState.started);
		return true;
	}
	@Override
	protected boolean tryPause() throws Exception {
		setState(ServiceState.pausing);
		isPlaying = false;
		setState(ServiceState.paused);
		return true;
	}
	@Override
	protected boolean tryResume() throws Exception {
		setState(ServiceState.starting);
		isPlaying = true;
		setState(ServiceState.started);
		return true;
	}
	@Override
	protected boolean tryStop() throws Exception {
		setState(ServiceState.stopping);
		isPlaying = false;
		setState(ServiceState.stopped);
		return true;
	}

	@Override
	public void onTimeEvent(TimeEvent _event) {
		if(!isPlaying)
			return ;

		double dt = 1e3 / getFramePerSecond();
		long   t  = _event.getTimestamp().toEpochMilli();

		if(t - last_update >= dt) {

			Optional<? extends T> frame;
			try {
				frame = readNextFrame();
				if(frame.isPresent())
					fire(Events.newPlayerEvent(this, frame.get(), Timestamps.of(t)));
				else
					stop();

			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
