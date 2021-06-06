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
 * @file     SoundInfo.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.media.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import fr.java.media.sound.SoundFormat;

public class SoundInfos {

	static class SoundInfoImpl extends AudioFormat implements SoundFormat {
	
		public SoundInfoImpl(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian) {
			super(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		}
	
		@Override
		public Type getType() {
			return Type.TimeSerie;
		}
	
		@Override
		public int getBitsPerSample() {
			return super.getSampleSizeInBits();
		}
	
		@Override
		public int getChannelCount() {
			return super.getChannels();
		}
	
		@Override
		public boolean isSignedSample() {
			return true;
		}
	
	}

	public static enum Type { TimeSerie, FourierSerie, OtherSerie }

	public static SoundFormat mono() {
		return new SoundInfoImpl(8000, 16, 1, true, true);
	}
	public static SoundFormat stereo() {
		return new SoundInfoImpl(44100f, 16, 2, true, true);
	}

	public static final AudioFormat audioFormat(SoundFormat _nfo) {
		if(_nfo instanceof AudioFormat)
			return (AudioFormat) _nfo;
		return new AudioFormat(_nfo.getSampleRate(), _nfo.getBitsPerSample(), _nfo.getChannelCount(), _nfo.isSignedSample(), _nfo.isBigEndian());
	}

	public static class CaptureParams {
		public static final AudioFormat   Sphinx4  = new AudioFormat(16000, 16, 1, true, false); //	16 khz, 16 bit, mono, little-endian
		public static final AudioFormat   HD       = new AudioFormat(44100, 16, 2, true, false);
		public static final AudioFormat   Voice    = new AudioFormat(44100, 16, 1, true, false);

		public static final CaptureParams AIFC     = new CaptureParams(AudioFileFormat.Type.AIFC, Voice); 
		public static final CaptureParams AIFF     = new CaptureParams(AudioFileFormat.Type.AIFF, Voice);
		public static final CaptureParams AU       = new CaptureParams(AudioFileFormat.Type.AU,   Voice);
		public static final CaptureParams SND      = new CaptureParams(AudioFileFormat.Type.SND,  Voice);
		public static final CaptureParams WAVE     = new CaptureParams(AudioFileFormat.Type.WAVE, Voice);

		public static final CaptureParams SphinxIV = new CaptureParams(AudioFileFormat.Type.WAVE, Sphinx4);

		public AudioFileFormat.Type type;
		public AudioFormat          format;

		CaptureParams(AudioFileFormat.Type _t, AudioFormat _f) {
			type   = _t;
			format = _f;
		}
		
		public AudioFileFormat.Type toType() {
			return type;
		}
		public AudioFormat toFormat() {
			return format;
		}

	}

}
