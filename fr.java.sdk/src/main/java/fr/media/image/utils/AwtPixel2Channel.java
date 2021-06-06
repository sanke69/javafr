package fr.media.image.utils;

import java.awt.image.ColorModel;
import java.util.function.Function;

public interface AwtPixel2Channel {

	public interface ByteBinary {

		static byte[]   toByte		(byte[] _awtBuf, int _awtHeight, int _awtWidth, byte _low, byte _high) {
			byte[] buffer = new byte[_awtHeight * _awtWidth];

			for(int i = 0, j = 0; i < _awtHeight * _awtWidth; i += 8, j++) {
				buffer[i]     = ((_awtBuf[j] >> 0) & 1) > 0 ? _high : _low;
				buffer[i + 1] = ((_awtBuf[j] >> 1) & 1) > 0 ? _high : _low;
				buffer[i + 2] = ((_awtBuf[j] >> 2) & 1) > 0 ? _high : _low;
				buffer[i + 3] = ((_awtBuf[j] >> 3) & 1) > 0 ? _high : _low;
				buffer[i + 4] = ((_awtBuf[j] >> 4) & 1) > 0 ? _high : _low;
				buffer[i + 5] = ((_awtBuf[j] >> 5) & 1) > 0 ? _high : _low;
				buffer[i + 6] = ((_awtBuf[j] >> 6) & 1) > 0 ? _high : _low;
				buffer[i + 7] = ((_awtBuf[j] >> 7) & 1) > 0 ? _high : _low;
			}

			return buffer;
		}
		static short[]  toShort		(byte[] _awtBuf, int _awtHeight, int _awtWidth, short _low, short _high) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			for(int i = 0, j = 0; i < _awtHeight * _awtWidth; i += 8, j++) {
				buffer[i]     = ((_awtBuf[j] >> 0) & 1) > 0 ? _high : _low;
				buffer[i + 1] = ((_awtBuf[j] >> 1) & 1) > 0 ? _high : _low;
				buffer[i + 2] = ((_awtBuf[j] >> 2) & 1) > 0 ? _high : _low;
				buffer[i + 3] = ((_awtBuf[j] >> 3) & 1) > 0 ? _high : _low;
				buffer[i + 4] = ((_awtBuf[j] >> 4) & 1) > 0 ? _high : _low;
				buffer[i + 5] = ((_awtBuf[j] >> 5) & 1) > 0 ? _high : _low;
				buffer[i + 6] = ((_awtBuf[j] >> 6) & 1) > 0 ? _high : _low;
				buffer[i + 7] = ((_awtBuf[j] >> 7) & 1) > 0 ? _high : _low;
			}

			return buffer;
		}
		static int[]    toInt		(byte[] _awtBuf, int _awtHeight, int _awtWidth, int _low, int _high) {
			int[] buffer = new int[_awtHeight * _awtWidth];

			for(int i = 0, j = 0; i < _awtHeight * _awtWidth; i += 8, j++) {
				buffer[i]     = ((_awtBuf[j] >> 0) & 1) > 0 ? _high : _low;
				buffer[i + 1] = ((_awtBuf[j] >> 1) & 1) > 0 ? _high : _low;
				buffer[i + 2] = ((_awtBuf[j] >> 2) & 1) > 0 ? _high : _low;
				buffer[i + 3] = ((_awtBuf[j] >> 3) & 1) > 0 ? _high : _low;
				buffer[i + 4] = ((_awtBuf[j] >> 4) & 1) > 0 ? _high : _low;
				buffer[i + 5] = ((_awtBuf[j] >> 5) & 1) > 0 ? _high : _low;
				buffer[i + 6] = ((_awtBuf[j] >> 6) & 1) > 0 ? _high : _low;
				buffer[i + 7] = ((_awtBuf[j] >> 7) & 1) > 0 ? _high : _low;
			}

			return buffer;
		}
		static long[]   toLong		(byte[] _awtBuf, int _awtHeight, int _awtWidth, long _low, long _high) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			for(int i = 0, j = 0; i < _awtHeight * _awtWidth; i += 8, j++) {
				buffer[i]     = ((_awtBuf[j] >> 0) & 1) > 0 ? _high : _low;
				buffer[i + 1] = ((_awtBuf[j] >> 1) & 1) > 0 ? _high : _low;
				buffer[i + 2] = ((_awtBuf[j] >> 2) & 1) > 0 ? _high : _low;
				buffer[i + 3] = ((_awtBuf[j] >> 3) & 1) > 0 ? _high : _low;
				buffer[i + 4] = ((_awtBuf[j] >> 4) & 1) > 0 ? _high : _low;
				buffer[i + 5] = ((_awtBuf[j] >> 5) & 1) > 0 ? _high : _low;
				buffer[i + 6] = ((_awtBuf[j] >> 6) & 1) > 0 ? _high : _low;
				buffer[i + 7] = ((_awtBuf[j] >> 7) & 1) > 0 ? _high : _low;
			}

			return buffer;
		}
		static float[]  toFloat		(byte[] _awtBuf, int _awtHeight, int _awtWidth, float _low, float _high) {
			float[] buffer = new float[_awtHeight * _awtWidth];
	
			for(int i = 0, j = 0; i < _awtHeight * _awtWidth; i += 8, j++) {
				buffer[i]     = ((_awtBuf[j] >> 0) & 1) > 0 ? _high : _low;
				buffer[i + 1] = ((_awtBuf[j] >> 1) & 1) > 0 ? _high : _low;
				buffer[i + 2] = ((_awtBuf[j] >> 2) & 1) > 0 ? _high : _low;
				buffer[i + 3] = ((_awtBuf[j] >> 3) & 1) > 0 ? _high : _low;
				buffer[i + 4] = ((_awtBuf[j] >> 4) & 1) > 0 ? _high : _low;
				buffer[i + 5] = ((_awtBuf[j] >> 5) & 1) > 0 ? _high : _low;
				buffer[i + 6] = ((_awtBuf[j] >> 6) & 1) > 0 ? _high : _low;
				buffer[i + 7] = ((_awtBuf[j] >> 7) & 1) > 0 ? _high : _low;
			}
	
			return buffer;
		}
		static double[] toDouble	(byte[] _awtBuf, int _awtHeight, int _awtWidth, double _low, double _high) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			for(int i = 0, j = 0; i < _awtHeight * _awtWidth; i += 8, j++) {
				buffer[i]     = ((_awtBuf[j] >> 0) & 1) > 0 ? _high : _low;
				buffer[i + 1] = ((_awtBuf[j] >> 1) & 1) > 0 ? _high : _low;
				buffer[i + 2] = ((_awtBuf[j] >> 2) & 1) > 0 ? _high : _low;
				buffer[i + 3] = ((_awtBuf[j] >> 3) & 1) > 0 ? _high : _low;
				buffer[i + 4] = ((_awtBuf[j] >> 4) & 1) > 0 ? _high : _low;
				buffer[i + 5] = ((_awtBuf[j] >> 5) & 1) > 0 ? _high : _low;
				buffer[i + 6] = ((_awtBuf[j] >> 6) & 1) > 0 ? _high : _low;
				buffer[i + 7] = ((_awtBuf[j] >> 7) & 1) > 0 ? _high : _low;
			}

			return buffer;
		}

	}
	public interface ByteGray {

		static byte[] 	toByte		(byte[] _awtBuf, int _awtHeight, int _awtWidth) {
			byte[] buffer = new byte[_awtHeight * _awtWidth];
			
			System.arraycopy(_awtBuf, 0, buffer, 0, buffer.length);

			return buffer;
		}
		static short[] 	toShort		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = _convert.apply(_awtBuf[i]);

			return buffer;
		}
		static int[] 	toInt		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert) {
			int[] buffer = new int[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = _convert.apply(_awtBuf[i]);

			return buffer;
		}
		static long[] 	toLong		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = _convert.apply(_awtBuf[i]);

			return buffer;
		}
		static float[] 	toFloat		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert) {
			float[] buffer = new float[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = _convert.apply(_awtBuf[i]);

			return buffer;
		}
		static double[] toDouble	(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = _convert.apply(_awtBuf[i]);

			return buffer;
		}

	}
	public interface ByteIndexed {

		static byte[] 	toByte		(byte[] _awtBuf, int _awtHeight, int _awtWidth, ColorModel _model) {
			byte[] buffer = new byte[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				buffer[                             i] = (byte) _model.getRed   (_awtBuf[i]);
				buffer[    _awtWidth * _awtHeight + i] = (byte) _model.getGreen (_awtBuf[i]);
				buffer[2 * _awtWidth * _awtHeight + i] = (byte) _model.getBlue  (_awtBuf[i]);
			}

			return buffer;
		}
		static short[] 	toShort		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, ColorModel _model) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				buffer[                             i] = _convert.apply((byte) _model.getRed  (_awtBuf[i]));
				buffer[    _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getGreen(_awtBuf[i]));
				buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getBlue (_awtBuf[i]));
			}

			return buffer;
		}
		static int[] 	toInt		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, ColorModel _model) {
			int[] buffer = new int[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				buffer[                             i] = _convert.apply((byte) _model.getRed  (_awtBuf[i]));
				buffer[    _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getGreen(_awtBuf[i]));
				buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getBlue (_awtBuf[i]));
			}

			return buffer;
		}
		static long[] 	toLong		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, ColorModel _model) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				buffer[                             i] = _convert.apply((byte) _model.getRed  (_awtBuf[i]));
				buffer[    _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getGreen(_awtBuf[i]));
				buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getBlue (_awtBuf[i]));
			}

			return buffer;
		}
		static float[] 	toFloat		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, ColorModel _model) {
			float[] buffer = new float[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				buffer[                             i] = _convert.apply((byte) _model.getRed  (_awtBuf[i]));
				buffer[    _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getGreen(_awtBuf[i]));
				buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getBlue (_awtBuf[i]));
			}

			return buffer;
		}
		static double[] toDouble	(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, ColorModel _model) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				buffer[                             i] = _convert.apply((byte) _model.getRed  (_awtBuf[i]));
				buffer[    _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getGreen(_awtBuf[i]));
				buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply((byte) _model.getBlue (_awtBuf[i]));
			}

			return buffer;
		}

	}
	public interface ByteBGR {

		static byte[] 	toByte		(byte[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseChannels) {
			byte[] buffer = new byte[3 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
					buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
					buffer[                             i] = _awtBuf[index++];
				} else {
					buffer[                             i] = _awtBuf[index++];
					buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
					buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
				}
			}

			return buffer;
		}
		static short[] 	toShort		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseChannels) {
			short[] buffer = new short[3 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
				} else {
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
				}
			}

			return buffer;
		}
		static int[] 	toInt		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseChannels) {
			int[] buffer = new int[3 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
				} else {
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
				}
			}

			return buffer;
		}
		static long[] 	toLong		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseChannels) {
			long[] buffer = new long[3 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
				} else {
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseChannels) {
			float[] buffer = new float[3 * _awtHeight * _awtWidth];

//			int index = 0;
			for(int i = 0, index = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
				} else {
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
				}
			}

			return buffer;
		}
		static double[] toDouble	(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseChannels) {
			double[] buffer = new double[3 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
				} else {
					buffer[                             i] = _convert.apply(_awtBuf[index++]);
					buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
				}
			}

			return buffer;
		}

	}
	public interface ByteABGR {

		static byte[] 	toByte		(byte[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			byte[] buffer = new byte[4 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _awtBuf[index++];
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
					} else {
						buffer[                             i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[                             i] = _awtBuf[index++];
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[                             i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
					}
				}
			}

			return buffer;
		}
		static short[] 	toShort		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			short[] buffer = new short[4 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static int[] 	toInt		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			int[] buffer = new int[4 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static long[] 	toLong		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			long[] buffer = new long[4 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			float[] buffer = new float[4 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static double[] toDouble	(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			double[] buffer = new double[4 * _awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}

	}
	public interface ByteABGR_PRE {

		static byte[] 	toByte		(byte[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			byte[] buffer = new byte[_awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _awtBuf[index++];
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
					} else {
						buffer[                             i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[                             i] = _awtBuf[index++];
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[                             i] = _awtBuf[index++];
						buffer[    _awtWidth * _awtHeight + i] = _awtBuf[index++];
						buffer[2 * _awtWidth * _awtHeight + i] = _awtBuf[index++];
					}
				}
			}

			return buffer;
		}
		static short[] 	toShort		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static int[] 	toInt		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			int[] buffer = new int[_awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static long[] 	toLong		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			float[] buffer = new float[_awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}
		static double[] toDouble	(byte[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			int index = 0;
			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
					} else {
						buffer[3 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[                             i] = _convert.apply(_awtBuf[index++]);
						buffer[    _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
						buffer[2 * _awtWidth * _awtHeight + i] = _convert.apply(_awtBuf[index++]);
					}
				}
			}

			return buffer;
		}

	}

	public interface UShortGray {

		static byte[] 	toByte		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			byte[] buffer = new byte[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) (_awtBuf[i]);

			return buffer;
		}
		static short[] 	toShort		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (short) (_awtBuf[i] / 255);

			return buffer;
		}
		static int[] 	toInt		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			int[] buffer = new int[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (int) (_awtBuf[i] / 255);

			return buffer;
		}
		static long[] 	toLong		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (short) (_awtBuf[i] / 255);

			return buffer;
		}
		static float[] 	toFloat		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			float[] buffer = new float[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (float) (_awtBuf[i] / 255);

			return buffer;
		}
		static double[] toDouble	(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (short) (_awtBuf[i] / 255);

			return buffer;
		}

	}
	public interface UShortRGB565 {

		static byte[] 	toByte		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			byte[] buffer = new byte[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) _awtBuf[i];

			return buffer;
		}
		static short[] 	toShort		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (short) _awtBuf[i];

			return buffer;
		}
		static int[] 	toInt		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			int[] buffer = new int[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (int) _awtBuf[i];

			return buffer;
		}
		static long[] 	toLong		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) _awtBuf[i];

			return buffer;
		}
		static float[] 	toFloat		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			float[] buffer = new float[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (float) _awtBuf[i];

			return buffer;
		}
		static double[] toDouble	(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) _awtBuf[i];

			return buffer;
		}

	}
	public interface UShortRGB555 {

		static byte[] 	toByte		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			byte[] buffer = new byte[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) _awtBuf[i];

			return buffer;
		}
		static short[] 	toShort		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			short[] buffer = new short[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (short) _awtBuf[i];

			return buffer;
		}
		static int[] 	toInt		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			int[] buffer = new int[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (int) _awtBuf[i];

			return buffer;
		}
		static long[] 	toLong		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			long[] buffer = new long[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) _awtBuf[i];

			return buffer;
		}
		static float[] 	toFloat		(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			float[] buffer = new float[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (float) _awtBuf[i];

			return buffer;
		}
		static double[] toDouble	(short[] _awtBuf, int _awtHeight, int _awtWidth) {
			double[] buffer = new double[_awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i)
				buffer[i] = (byte) _awtBuf[i];

			return buffer;
		}

	}

	public interface IntegerBGR {

		static byte[] 	toByte		(int[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseChannels) {
			byte[] buffer = new byte[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              + i] = (byte) (((int) _awtBuf[i] & 0x000000FF) >> 16);
					buffer[    _awtHeight * _awtHeight + + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
					buffer[2 * _awtHeight * _awtHeight + + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000));
				}else {
					buffer[                              + i] = (byte) (((int) _awtBuf[i] & 0x000000FF));
					buffer[    _awtHeight * _awtHeight + + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
					buffer[2 * _awtHeight * _awtHeight + + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16);
				}
			}

			return buffer;
		}
		static short[] 	toShort		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseChannels) {
			short[] buffer = new short[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				}
			}

			return buffer;
		}
		static int[] 	toInt		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseChannels) {
			int[] buffer = new int[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				}
			}

			return buffer;
		}
		static long[] 	toLong		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseChannels) {
			long[] buffer = new long[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseChannels) {
			float[] buffer = new float[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				}
			}

			return buffer;
		}
		static double[] toDouble	(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseChannels) {
			double[] buffer = new double[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				}
			}

			return buffer;
		}
		
	}
	public interface IntegerRGB {

		static byte[] 	toByte		(int[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseChannels) {
			byte[] buffer = new byte[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = (byte) (((int) _awtBuf[i] & 0x000000FF));
					buffer[    _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
					buffer[2 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16);
				} else {
					buffer[                              i] = (byte) (((int) _awtBuf[i] & 0x000000FF) >> 16);
					buffer[    _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
					buffer[2 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000));
				}
			}

			return buffer;
		}
		static short[] 	toShort		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseChannels) {
			short[] buffer = new short[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				} else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}
			}

			return buffer;
		}
		static int[] 	toInt		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseChannels) {
			int[] buffer = new int[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				} else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}
			}

			return buffer;
		}
		static long[] 	toLong		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseChannels) {
			long[] buffer = new long[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				} else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseChannels) {
			float[] buffer = new float[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				} else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}
			}

			return buffer;
		}
		static double[] toDouble	(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseChannels) {
			double[] buffer = new double[3 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_inverseChannels) {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
				} else {
					buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF) >> 16));
					buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
					buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000)));
				}
			}

			return buffer;
		}

	}
	public interface IntegerARGB {

		static byte[] 	toByte		(int[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			byte[] buffer = new byte[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = (byte) (((int) _awtBuf[i] & 0xFF000000) >> 24);
						buffer[    _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x000000FF));
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16);
					} else {
						buffer[                              i] = (byte) (((int) _awtBuf[i] & 0xFF000000) >> 24);	// Alpha
						buffer[    _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16);	// Red
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);	// Green
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x000000FF));			// Blue
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = (byte) (((int) _awtBuf[i] & 0x000000FF));
						buffer[    _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16);
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0xFF000000) >> 24);
					} else {
						buffer[                              i] = (byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16);
						buffer[    _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8);
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0x000000FF));
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) (((int) _awtBuf[i] & 0xFF000000) >> 24);
					}
				}
			}

			return buffer;
		}
		static short[] 	toShort		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			short[] buffer = new short[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));	// Alpha
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));	// Red
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));	// Green
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));			// Blue
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					}
				}
			}

			return buffer;
		}
		static int[] 	toInt		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			int[] buffer = new int[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));	// Alpha
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));	// Red
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));	// Green
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));			// Blue
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					}
				}
			}

			return buffer;
		}
		static long[] 	toLong		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			long[] buffer = new long[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));	// Alpha
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));	// Red
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));	// Green
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));			// Blue
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					}
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			float[] buffer = new float[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));	// Alpha
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));	// Red
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));	// Green
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));			// Blue
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					}
				}
			}

			return buffer;
		}
		static double[] toDouble	(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			double[] buffer = new double[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));	// Alpha
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));	// Red
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));	// Green
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));			// Blue
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					} else {
						buffer[                              i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x00FF0000) >> 16));
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x0000FF00) >>  8));
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0x000000FF)));
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) (((int) _awtBuf[i] & 0xFF000000) >> 24));
					}
				}
			}

			return buffer;
		}

	}
	public interface IntegerARGB_PRE {

		static byte[] 	toByte		(int[] _awtBuf, int _awtHeight, int _awtWidth, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			byte[] buffer = new byte[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = (byte) _awtBuf[i];
						buffer[    _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
					} else {
						buffer[                              i] = (byte) _awtBuf[i];
						buffer[    _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = (byte) _awtBuf[i];
						buffer[    _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
					} else {
						buffer[                              i] = (byte) _awtBuf[i];
						buffer[    _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[2 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
						buffer[3 * _awtHeight * _awtHeight + i] = (byte) _awtBuf[i];
					}
				}
			}

			return buffer;
		}
		static short[] 	toShort		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Short> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			short[] buffer = new short[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				}
			}

			return buffer;
		}
		static int[] 	toInt		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Integer> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			int[] buffer = new int[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				}
			}

			return buffer;
		}
		static long[] 	toLong		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Long> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			long[] buffer = new long[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				}
			}

			return buffer;
		}
		static float[] 	toFloat		(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Float> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			float[] buffer = new float[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				}
			}

			return buffer;
		}
		static double[] toDouble	(int[] _awtBuf, int _awtHeight, int _awtWidth, Function<Byte, Double> _convert, boolean _inverseColorChannels, boolean _alphaAsFirstChannel) {
			double[] buffer = new double[4 * _awtHeight * _awtWidth];

			for(int i = 0; i < _awtHeight * _awtWidth; ++i) {
				if(_alphaAsFirstChannel) {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				} else {
					if(_inverseColorChannels) {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					} else {
						buffer[                              i] = _convert.apply((byte) _awtBuf[i]);
						buffer[    _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[2 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
						buffer[3 * _awtHeight * _awtHeight + i] = _convert.apply((byte) _awtBuf[i]);
					}
				}
			}

			return buffer;
		}

	}

}
