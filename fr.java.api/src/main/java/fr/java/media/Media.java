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
package fr.java.media;

import java.net.URI;
import java.util.Optional;

public interface Media<T> {

	public interface Location {

		public URI 					getURI();
//		public default URI 			getURI() { return URI.create("application:in memory"); }

	}

	public interface Infos extends Location {

		public Optional<Long>		duration();

//		public Optional<List<Long>>	marks();

		public Optional<Long>		frameCount();
		public Optional<Long>		frameIndex();
		public Optional<Long>		frameStart();
		public Optional<Long>		frameStop();


	}
	public interface Record<T> extends Media.Infos {

		public T 					getFrame(long _index);

		public boolean				setIndex(long _index);
		public boolean				setStart(long _frame);
		public boolean				setStop(long _frame);
		public boolean				setInterval(long _start, long _stop);

		public boolean				increaseIndex();

	}

	public T 				data();
	public MediaFormat 		format();

}
