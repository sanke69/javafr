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
 * @file     Image.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.media.image;

import java.awt.image.BufferedImage;

import fr.java.lang.enums.PixelFormat;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.ByteTensor;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.tensor.FloatTensor;
import fr.java.math.algebra.tensor.IntTensor;
import fr.java.math.algebra.tensor.LongTensor;
import fr.java.math.algebra.tensor.ShortTensor;
import fr.java.media.Media;

public interface Image<T> extends Media<T> {

	public T 			data();
	public ImageFormat	format();

	public static interface Awt			extends Image<BufferedImage> { }
	public static interface Fx			extends Image<javafx.scene.image.Image> { }
	public static interface Tensor		extends Image<fr.java.math.algebra.NumberTensor> { }

	public static Image.Awt 			of(final BufferedImage _content) {
		return new Image.Awt() {

			@Override
			public BufferedImage data() {
				return _content;
			}

			public ImageFormat	 format() {
				return new ImageFormat() {

					@Override
					public PixelFormat getPixelFormat() {
						return null;
					}

					@Override
					public int getWidth() {
						return _content.getWidth();
					}

					@Override
					public int getHeight() {
						return _content.getWidth();
					}

					@Override
					public int getDepth() {
						return 0;
					}

				};
			}

		};
	}
	public static Image.Fx 				of(final javafx.scene.image.Image _content) {
		return new Image.Fx() {

			@Override
			public javafx.scene.image.Image data() {
				return _content;
			}

			public ImageFormat	 			format() {
				return new ImageFormat() {

					@Override
					public PixelFormat getPixelFormat() {
						throw new NotYetImplementedException();
					}

					@Override
					public int getWidth() {
						return (int) _content.getWidth();
					}

					@Override
					public int getHeight() {
						return (int) _content.getWidth();
					}

					@Override
					public int getDepth() {
						return 0;
					}

				};
			}

		};
	}
	@SuppressWarnings("unchecked")
	public static <T extends NumberTensor> Image<T>	of(final T _content, final ImageFormat _format) {
		if(_content instanceof ByteTensor)
			return (Image<T>) ofByte((ByteTensor) _content, _format);
		if(_content instanceof ShortTensor)
			return (Image<T>) ofShort((ShortTensor) _content, _format);
		if(_content instanceof IntTensor)
			return (Image<T>) ofInt((IntTensor) _content, _format);
		if(_content instanceof LongTensor)
			return (Image<T>) ofLong((LongTensor) _content, _format);
		if(_content instanceof FloatTensor)
			return (Image<T>) ofFloat((FloatTensor) _content, _format);
		if(_content instanceof DoubleTensor)
			return (Image<T>) ofDouble((DoubleTensor) _content, _format);

		return (Image<T>) new Image.Tensor() {

			@Override
			public fr.java.math.algebra.NumberTensor	data() {
				return _content;
			}
			public ImageFormat					format() {
				return _format;
			}

		};
	}

	public static Image<ByteTensor>		ofByte(final ByteTensor _content, final ImageFormat _format) {
		return new Image<ByteTensor>() {

			@Override
			public ByteTensor	data() {
				return _content;
			}
			public ImageFormat	format() {
				return _format;
			}

		};
	}
	public static Image<ShortTensor>	ofShort(final ShortTensor _content, final ImageFormat _format) {
		return new Image<ShortTensor>() {

			@Override
			public ShortTensor	data() {
				return _content;
			}
			public ImageFormat	format() {
				return _format;
			}

		};
	}
	public static Image<IntTensor>		ofInt(final IntTensor _content, final ImageFormat _format) {
		return new Image<IntTensor>() {

			@Override
			public IntTensor	data() {
				return _content;
			}
			public ImageFormat	format() {
				return _format;
			}

		};
	}
	public static Image<LongTensor>		ofLong(final LongTensor _content, final ImageFormat _format) {
		return new Image<LongTensor>() {

			@Override
			public LongTensor	data() {
				return _content;
			}
			public ImageFormat	format() {
				return _format;
			}

		};
	}
	public static Image<FloatTensor>	ofFloat(final FloatTensor _content, final ImageFormat _format) {
		return new Image<FloatTensor>() {

			@Override
			public FloatTensor	data() {
				return _content;
			}
			public ImageFormat	format() {
				return _format;
			}

		};
	}
	public static Image<DoubleTensor>	ofDouble(final DoubleTensor _content, final ImageFormat _format) {
		return new Image<DoubleTensor>() {

			@Override
			public DoubleTensor	data() {
				return _content;
			}
			public ImageFormat	format() {
				return _format;
			}

		};
	}

}
