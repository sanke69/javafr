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
 * @file     Player.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.player;

import javax.naming.OperationNotSupportedException;

import fr.java.patterns.service.Service;

public interface Player<T> extends Service.Interruptable {

	public void 			play()  throws Exception;
	public default void 	start() throws Exception { play(); }

	public default void 	pause()  throws Exception { throw new OperationNotSupportedException("Operation not permitted"); }
	public default void 	resume() throws Exception { throw new OperationNotSupportedException("Operation not permitted"); }

	public double 			getPlayRate();						// Default value: 1.0
	public void   			setPlayRate(double _ratio);

	public double			getFramePerSecond();
	public void   			setFramePerSecond(double _framePerSecond);

	public default double	getFPS() { return getFramePerSecond(); }
	public default void		setFPS(double _framePerSecond) { setFramePerSecond(_framePerSecond); }

	public default void 	fire(PlayerEvent _evt) {
		if(getListeners(PlayerListener.class) == null)
			return ;

		for(PlayerListener l : getListeners(PlayerListener.class))
			l.onPlayerEvent(_evt);
	}

	public void 			registerListener(PlayerListener _listener);
	public void 			unregisterListener(PlayerListener _listener);

}
