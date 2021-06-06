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
 * @file     AbstractPlayer.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.sdk.player;

import java.io.IOException;
import java.util.Optional;

import fr.java.lang.enums.state.ServiceState;
import fr.java.player.Player;
import fr.java.player.PlayerListener;
import fr.java.sdk.patterns.service.InterruptableServiceAdapter;

public abstract class PlayerAdapter<T> extends InterruptableServiceAdapter implements Player<T> {
	private double framePerSecond, playRate;

	public PlayerAdapter(boolean _isInterruptable) {
		this(_isInterruptable, 25d);
	}
	public PlayerAdapter(boolean _isInterruptable, double _fps) {
		super(_isInterruptable);

		framePerSecond  = _fps;
		playRate		= 1.0;

		setState(ServiceState.setting);
	}

	protected abstract Optional<T> 	readNextFrame() throws IOException;

	@Override
	public final void 				play() {
		start();
	}

	@Override
	public void						setPlayRate(double _ratio) {
		playRate = _ratio;
	}
	@Override
	public double					getPlayRate() {
		return playRate;
	}

	@Override
	public void						setFramePerSecond(double _fps) {
		if(_fps <= 0)
			throw new IllegalArgumentException("FPS must be > 0");
		framePerSecond = _fps;
	}
	@Override
	public double					getFramePerSecond() {
		return framePerSecond;
	}

	@Override
	public void 					registerListener(PlayerListener _listener) {
		getListenerList().add(PlayerListener.class, _listener);
	}
	@Override
	public void 					unregisterListener(PlayerListener _listener) {
		getListenerList().remove(PlayerListener.class, _listener);
	}

}
