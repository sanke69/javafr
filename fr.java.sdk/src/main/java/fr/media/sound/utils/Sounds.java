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
 * @file     Sounds.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.media.sound.utils;

import java.nio.ByteBuffer;
import java.time.Duration;

import fr.java.media.sound.SoundFormat;
import fr.java.media.sound.buffer.SoundChunk;

public class Sounds {

	public static final SoundChunk fromBytes(byte[] _bytes, SoundFormat _nfo) {
		return new SoundChunk() {

			@Override
			public Duration 	getDuration() {
				return Duration.ofMillis(_bytes.length);
			}

			@Override
			public ByteBuffer 	getBuffer() {
				return ByteBuffer.wrap(_bytes);
			}

			@Override
			public SoundFormat info() {
				return _nfo;
			}

		};
	}

}
