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
 * @file     VideoFrame.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.media.video.buffer;

import java.awt.image.BufferedImage;
import java.time.Duration;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.media.image.Image;
import fr.java.media.sound.buffer.SoundChunk;

public interface VideoFrame<IMG> {

	public interface Awt    extends VideoFrame<BufferedImage> {
		
	}
	public interface Fx     extends VideoFrame<javafx.scene.image.Image> {
		
	}

	public static VideoFrame.Awt 		of(BufferedImage _image) {
		return new VideoFrame.Awt() {

			@Override
			public long 				timestamp() {
				return 0L;
			}
			@Override
			public SoundChunk 			audio() {
				return SoundChunk.of(null,  Duration.ZERO, null);
			}
			@Override
			public Image<BufferedImage> image() {
				return Image.of(_image);
			}

			public VideoFrame<BufferedImage> clone() {
				throw new NotYetImplementedException();
			}

		};
	}
	public static VideoFrame.Awt 		of(long _timestamp, BufferedImage _image) {
		return new VideoFrame.Awt() {

			@Override
			public long 				timestamp() {
				return _timestamp;
			}
			@Override
			public SoundChunk 			audio() {
				return SoundChunk.of(null,  Duration.ZERO, null);
			}
			@Override
			public Image<BufferedImage> image() {
				return Image.of(_image);
			}

			public VideoFrame<BufferedImage> clone() {
				throw new NotYetImplementedException();
			}

		};
	}
	public static VideoFrame.Fx 		of(long _timestamp, javafx.scene.image.Image _image) {
		return new VideoFrame.Fx() {

			@Override
			public long 				timestamp() {
				return _timestamp;
			}
			@Override
			public SoundChunk 			audio() {
				return SoundChunk.of(null,  Duration.ZERO, null);
			}
			@Override
			public Image<javafx.scene.image.Image> image() {
				return Image.of(_image);
			}

			public VideoFrame<javafx.scene.image.Image> clone() {
				throw new NotYetImplementedException();
			}

		};
	}
	public static VideoFrame.Fx 		of(javafx.scene.image.Image _image) {
		return new VideoFrame.Fx() {

			@Override
			public long 				timestamp() {
				return 0L;
			}
			@Override
			public SoundChunk 			audio() {
				return SoundChunk.of(null,  Duration.ZERO, null);
			}
			@Override
			public Image<javafx.scene.image.Image> image() {
				return Image.of(_image);
			}

			public VideoFrame<javafx.scene.image.Image> clone() {
				throw new NotYetImplementedException();
			}

		};
	}

	public long       timestamp();
	public SoundChunk audio();
	public Image<IMG> image();

}
