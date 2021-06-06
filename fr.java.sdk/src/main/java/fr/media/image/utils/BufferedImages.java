package fr.media.image.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import fr.java.lang.enums.PixelFormat;
import fr.java.lang.exceptions.NotYetImplementedException;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class BufferedImages {

	public static BufferedImage message(String _msg, int width, int height) {
		String message = _msg;

		BufferedImage notFoundImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D    graphics       = notFoundImage.createGraphics();
		int           textWidth      = graphics.getFontMetrics().stringWidth(message);
		int           textHeight     = graphics.getFontMetrics().getHeight();

		graphics.setColor(new Color(255, 0, 0));
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(new Color(240, 240, 240));
		graphics.fillRect(1, 1, width - 2, height - 2);
		graphics.setColor(Color.BLACK);
		graphics.drawString(message, (width - textWidth) / 2, (height - textHeight) / 2);

		return notFoundImage;
	}

	public static BufferedImage notFound(int width, int height) {
		String message = "Image not found";

		BufferedImage notFoundImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D    graphics       = notFoundImage.createGraphics();
		int           textWidth      = graphics.getFontMetrics().stringWidth(message);
		int           textHeight     = graphics.getFontMetrics().getHeight();

		graphics.setColor(new Color(255, 0, 0));
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(new Color(240, 240, 240));
		graphics.fillRect(1, 1, width - 2, height - 2);
		graphics.setColor(Color.BLACK);
		graphics.drawString(message, (width - textWidth) / 2, (height - textHeight) / 2);

		return notFoundImage;
	}
	
	/*
	bufferedImage = GraphicsEnvironment
		                .getLocalGraphicsEnvironment()
		                .getDefaultScreenDevice()
		                .getDefaultConfiguration()
		                .createCompatibleImage(width, height);
*/
	/**
	 * CONVERTIONS
	**/
	
	public static BufferedImage fromAWT(java.awt.Image img, int type) {
		if(img instanceof BufferedImage)
			return (BufferedImage) img;

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), type);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bimage;
	}

	/**
	 * CREATION
	**/
	public static BufferedImage createImage(int _height, int _width, int _type) {
		BufferedImage img = new BufferedImage(_width, _height, _type);
		img.createGraphics();
//		Graphics2D g = (Graphics2D) img.getGraphics();
//		g.setColor(Color.GREEN);
//		g.fillRect(0, 0, 100, 100);
		return img;
	}
	public static BufferedImage createImage(int _height, int _width, int _type, byte[] _data) {
		BufferedImage img = new BufferedImage(_width, _height, _type);
		byte[] buf = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();

		assert (_data.length >= _width * _height * imageDepth(_type));
		System.arraycopy(_data, 0, buf, 0, _width * _height * imageDepth(_type));

		return img;
	}

	public static BufferedImage createBWImage(byte[][] _data, byte _threshold, Color _c0, Color _c1) {
		int h = _data.length;
		int w = _data[0].length;
		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		WritableRaster oup = res.getRaster();
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				if(_data[y][x] < _threshold) {
					oup.setSample(x, y, 0, _c0.getRed());
					oup.setSample(x, y, 1, _c0.getGreen());
					oup.setSample(x, y, 2, _c0.getBlue());
				} else {
					oup.setSample(x, y, 0, _c1.getRed());
					oup.setSample(x, y, 1, _c1.getGreen());
					oup.setSample(x, y, 2, _c1.getBlue());
				}
		return res;
	}

	public static BufferedImage createGrayScaleImage(int _height, int _width) {
		return createImage(_width, _height, BufferedImage.TYPE_BYTE_GRAY);
	}
	public static BufferedImage createGrayScaleImage(byte[] _data, int _height, int _width) {
		if(_data == null)
			return null;
		BufferedImage res = new BufferedImage(_width, _height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster oup = res.getRaster();
		for(int y = 0; y < _height; y++)
			for(int x = 0; x < _width; x++)
				oup.setSample(x, y, 0, _data[y * _width + x]);
		return res;
	}
	public static BufferedImage createGrayScaleImage(byte[][] _data) {
		int h = _data.length;
		int w = _data[0].length;
		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster oup = res.getRaster();
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++)
				oup.setSample(x, y, 0, _data[y][x]);
		return res;
	}
	public static BufferedImage createGrayScaleImage(byte[][] _data, double _factor) {
		int h = _data.length;
		int w = _data[0].length;

		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster oup = res.getRaster();
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				oup.setSample(x, y, 0, (int) (_factor * _data[y][x]));
		return res;
	}
	public static BufferedImage createGrayScaleImage(int[][] _data) {
		return createGrayScaleImage(_data, 1.0);
	}
	public static BufferedImage createGrayScaleImage(int[][] _data, double _factor) {
		int h = _data.length;
		int w = _data[0].length;
		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster oup = res.getRaster();
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++)
				oup.setSample(x, y, 0, (_factor * _data[y][x]) * 255);
		return res;
	}
	
	public static BufferedImage createBgrImage(int _width, int _height) {
		return createImage(_height, _width, BufferedImage.TYPE_3BYTE_BGR);
	}
	public static BufferedImage createBgrImage(byte[] _data, int _depth, int _height, int _width) {
		int slice_size = _width * _height;

		BufferedImage  res = new BufferedImage(_width, _height, BufferedImage.TYPE_3BYTE_BGR);
		WritableRaster oup = res.getRaster();
		for(int y = 0; y < _height; y++) {
			for(int x = 0; x < _width; x++)
				switch(_depth) {
				case 1 : 	oup.setSample(x, y, 0, (_data[y * _width + x]) % 256);
							oup.setSample(x, y, 1, (_data[y * _width + x]) % 256);
							oup.setSample(x, y, 2, (_data[y * _width + x]) % 256);
							break;
//				case 3 :	oup.setSample(x, y, 0, (_data[y * _width + x]) % 256);
//							oup.setSample(x, y, 1, (_data[y * _width + x + slice_size]) % 256);
//							oup.setSample(x, y, 2, (_data[y * _width + x + 2 * slice_size]) % 256);
				case 3 :	oup.setSample(x, y, 0, _data[y * _width + x + 2 * slice_size]);
							oup.setSample(x, y, 1, _data[y * _width + x + slice_size]);
							oup.setSample(x, y, 2, _data[y * _width + x]);
							break;
				}
			}
		return res;
	}
	public static BufferedImage createBgrImageFromPixel(byte[] _data, int _depth, int _height, int _width) {
		BufferedImage  res = new BufferedImage(_width, _height, BufferedImage.TYPE_3BYTE_BGR);
		WritableRaster oup = res.getRaster();
		for(int y = 0; y < _height; y++) {
			for(int x = 0; x < _width; x++)
				switch(_depth) {
				case 1 : 	oup.setSample(x, y, 0, (_data[y * _width + x]) & 0xFF);
							oup.setSample(x, y, 1, (_data[y * _width + x]) & 0xFF);
							oup.setSample(x, y, 2, (_data[y * _width + x]) & 0xFF);
							break;
				case 3 :	oup.setSample(x, y, 0, (_data[3 * (y * _width + x) + 0]) & 0xFF);
							oup.setSample(x, y, 1, (_data[3 * (y * _width + x) + 1]) & 0xFF);
							oup.setSample(x, y, 2, (_data[3 * (y * _width + x) + 2]) & 0xFF);
							break;
				}
			}
		return res;
	}
	public static BufferedImage createBgrImage(byte[][][] _data) {
		return createBgrImage(_data, 1.0);
	}
	public static BufferedImage createBgrImage(byte[][][] _data, double _factor) {
		int d = _data.length;
		int h = _data[0].length;
		int w = _data[0][0].length;

		BufferedImage  res = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		WritableRaster oup = res.getRaster();
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++) {
				switch(d) {
				case 1 : 	oup.setSample(x, y, 0, (_factor * _data[0][y][x]) % 256);
							oup.setSample(x, y, 1, (_factor * _data[0][y][x]) % 256);
							oup.setSample(x, y, 2, (_factor * _data[0][y][x]) % 256);
							break;
				case 3 :	oup.setSample(x, y, 0, (_factor * _data[0][y][x]) % 256);
							oup.setSample(x, y, 1, (_factor * _data[1][y][x]) % 256);
							oup.setSample(x, y, 2, (_factor * _data[2][y][x]) % 256);
							break;
				}
			}
		return res;
	}
	public static BufferedImage createBgrImage(int[][][] _data) {
		return createBgrImage(_data, 1.0);
	}
	public static BufferedImage createBgrImage(int[][][] _data, double _factor) {
//		int d = _data.length;
		int h = _data[0].length;
		int w = _data[0][0].length;

		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		WritableRaster oup = res.getRaster();
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++) {
				oup.setSample(x, y, 0, (_factor * _data[0][y][x]) % 256);
				oup.setSample(x, y, 1, (_factor * _data[1][y][x]) % 256);
				oup.setSample(x, y, 2, (_factor * _data[2][y][x]) % 256);
			}
		return res;
	}

	public static BufferedImage createAbgrImage(int _width, int _height) {
		return createImage(_width, _height, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	/**
	 * LOAD / SAVE
	**/
	public static BufferedImage loadImage(Path _path) throws IOException {
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(_path.toFile());
		} catch(Exception e) {
//			e.printStackTrace();
//			System.out.println(_path);
			throw e;
//			bimg = message("File not found", 320, 240);
		}
		return bimg;
	}
	public static BufferedImage loadImage(InputStream _stream) throws IOException {
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(_stream);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return bimg;
	}

	public static void 			saveImage(BufferedImage _image, String _ext, Path _path) throws IOException {
		//		System.out.println(Arrays.toString(ImageIO.getReaderFormatNames()));
		ImageIO.write(_image, _ext, _path.toFile());
	}

	/**
	 * COLOR SPACE CONVERSION
	**/
	public static BufferedImage convertToMonochromatic	(BufferedImage image) {
		if(image.getType() == BufferedImage.TYPE_BYTE_BINARY)
			return image;
		return convertColorSpace(image, BufferedImage.TYPE_BYTE_BINARY);
	}
	public static BufferedImage convertToGrayscale		(BufferedImage image) {
		if(image.getType() == BufferedImage.TYPE_BYTE_GRAY)
			return image;
		return convertColorSpace(image, BufferedImage.TYPE_BYTE_GRAY);
	}
	public static BufferedImage convertToBGR			(BufferedImage image) {
		if(image.getType() == BufferedImage.TYPE_3BYTE_BGR)
			return image;
		return convertColorSpace(image, BufferedImage.TYPE_3BYTE_BGR);
	}
	public static BufferedImage convertColorSpace		(BufferedImage image, int newType) {
		BufferedImage new_image = new BufferedImage(image.getWidth(), image.getHeight(), newType);

		ColorConvertOp xformOp = new ColorConvertOp(null);
		xformOp.filter(image, new_image);

		return new_image;
	}

	public static ByteBuffer getPixelBuffer(BufferedImage _img, PixelFormat _pxlFmt) {
		switch(_img.getType()) {
		case /*  0 */ BufferedImage.TYPE_CUSTOM 		: throw new NotYetImplementedException();

		case /*  1 */ BufferedImage.TYPE_INT_RGB 		: return ByteBuffer.wrap(fromIRGB(_img, _pxlFmt));
		case /*  4 */ BufferedImage.TYPE_INT_BGR 		: return ByteBuffer.wrap(fromIBGR(_img, _pxlFmt));
		case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: return ByteBuffer.wrap(fromIARGB(_img, _pxlFmt));
		case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: throw new NotYetImplementedException();

		case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: return ByteBuffer.wrap(fromBB(_img, _pxlFmt));
		case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: return ByteBuffer.wrap(fromBG(_img, _pxlFmt));
		case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: return ByteBuffer.wrap(fromBI(_img, _pxlFmt));
		case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: return ByteBuffer.wrap(fromBBGR(_img, _pxlFmt));
		case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: return ByteBuffer.wrap(fromBABGR(_img, _pxlFmt));
		case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : throw new NotYetImplementedException();

		case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: throw new NotYetImplementedException();
		case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : throw new NotYetImplementedException();
		case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : throw new NotYetImplementedException();


		default											: throw new NotYetImplementedException();
		}

	}

	private static byte[] fromBI(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		byte[]  src = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
		
//		SampleModel     cm  = _img.getSampleModel();
		IndexColorModel icm =(IndexColorModel)_img.getColorModel();

//		int nbPixels = src.length;
		
		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i)
									buffer[j * w + i] = (byte) (icm.getAlpha(src[id++]));
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (255);
									buffer[2 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i)]     = (byte) (icm.getRed(src[id]));
									buffer[3 * (j * w + i) + 1] = (byte) (icm.getGreen(src[id]));
									buffer[3 * (j * w + i) + 2] = (byte) (icm.getBlue(src[id++]));
								}
							return buffer;
		case PXF_BGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i) + 2] = (byte) (icm.getBlue(src[id]));
									buffer[3 * (j * w + i) + 1] = (byte) (icm.getGreen(src[id]));
									buffer[3 * (j * w + i)]     = (byte) (icm.getRed(src[id++]));
								}
							return buffer;

		case PXF_RGBA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (icm.getRed(src[id]));
									buffer[4 * (j * w + i) + 1] = (byte) (icm.getGreen(src[id]));
									buffer[4 * (j * w + i) + 2] = (byte) (icm.getBlue(src[id]));
									buffer[4 * (j * w + i) + 3] = (byte) (icm.getAlpha(src[id++]));
								}
							return buffer;
		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 2] = (byte) (icm.getBlue(src[id]));
									buffer[4 * (j * w + i) + 1] = (byte) (icm.getGreen(src[id]));
									buffer[4 * (j * w + i)]     = (byte) (icm.getRed(src[id]));
									buffer[4 * (j * w + i) + 3] = (byte) (icm.getAlpha(src[id++]));
								}
							return buffer;

		case PXF_ARGB8:for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (icm.getAlpha(src[id]));
									buffer[4 * (j * w + i) + 1] = (byte) (icm.getRed(src[id]));
									buffer[4 * (j * w + i) + 2] = (byte) (icm.getGreen(src[id]));
									buffer[4 * (j * w + i) + 3] = (byte) (icm.getBlue(src[id++]));
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (icm.getAlpha(src[id]));
									buffer[4 * (j * w + i) + 3] = (byte) (icm.getBlue(src[id]));
									buffer[4 * (j * w + i) + 2] = (byte) (icm.getGreen(src[id]));
									buffer[4 * (j * w + i) + 1] = (byte) (icm.getRed(src[id++]));
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromBB(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		ColorModel model = _img.getColorModel();
		int 	   w   = _img.getWidth(),
				   h   = _img.getHeight();
		byte[]     src = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();

		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i)
									buffer[j * w + i] = (byte) (src[id++]);
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (255);
									buffer[2 * (j * w + i + 1)] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i)]     = (byte) (src[id]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGBA8:	
			for(int i = 0; i < h * w / 8; ++i) {
				int k = 0;
				int bit0 = (src[i] >> 0) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit0);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit0);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit0);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit0);

				k = 1;
				int bit1 = (src[i] >> 1) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit1);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit1);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit1);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit1);

				k = 2;
				int bit2 = (src[i] >> 2) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit2);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit2);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit2);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit2);

				k = 3;
				int bit3 = (src[i] >> 3) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit3);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit3);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit3);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit3);

				k = 4;
				int bit4 = (src[i] >> 4) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit4);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit4);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit4);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit4);

				k = 5;
				int bit5 = (src[i] >> 5) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit5);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit5);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit5);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit5);

				k = 6;
				int bit6 = (src[i] >> 6) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit6);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit6);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit6);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit6);

				k = 7;
				int bit7 = (src[i] >> 7) & 1;
				buffer[32 * i + 4 * k    ] = (byte) model.getRed   (bit7);
				buffer[32 * i + 4 * k + 1] = (byte) model.getGreen (bit7);
				buffer[32 * i + 4 * k + 2] = (byte) model.getBlue  (bit7);
				buffer[32 * i + 4 * k + 3] = (byte) model.getAlpha (bit7);
				
//				System.out.println(32 * i + 4 * k + 3);
			}
							return buffer;

		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;

		case PXF_ARGB8:for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromBG(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		byte[]  src = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();

		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i)
									buffer[j * w + i] = (byte) (src[id++]);
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (255);
									buffer[2 * (j * w + i + 1)] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i)]     = (byte) (src[id]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGBA8:for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;

		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;

		case PXF_ARGB8:for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromBBGR(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		byte[]  src = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
		
		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i)
									buffer[j * w + i] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (255);
									buffer[2 * (j * w + i + 1)] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i + 2)] = (byte) (src[id++]);
									buffer[3 * (j * w + i + 1)] = (byte) (src[id++]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGR8:	System.arraycopy(src, 0, buffer, 0, src.length);

		case PXF_RGBA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;
		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;

		case PXF_ARGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromBABGR(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		byte[]  src = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
		
		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[j * w + i] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
									id++;
								}
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (src[id++]);
									buffer[2 * (j * w + i) + 1] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									id++;
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									id++;
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGBA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_ARGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromIRGB(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		int[]   src = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
		
		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i)
									buffer[j * w + i] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (255);
									buffer[2 * (j * w + i) + 1] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
								}
							return buffer;

		case PXF_RGB8:	System.arraycopy(src, 0, buffer, 0, 3 * w * h);
							return buffer;
		case PXF_BGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGBA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id] >> 16);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id] >>  8);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
									id++;
								}
							return buffer;
		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;

		case PXF_ARGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromIBGR(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		int[]   src = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
		
		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i)
									buffer[j * w + i] = (byte) (0.114 * src[id++] + 0.587 * src[id++] + 0.299 * src[id++]);
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (255);
									buffer[2 * (j * w + i) + 1] = (byte) (0.114 * src[id++] + 0.587 * src[id++] + 0.299 * src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGR8:	System.arraycopy(src, 0, buffer, 0, 3 * w * h);
							return buffer;

		case PXF_RGBA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;
		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (255);
								}
							return buffer;

		case PXF_ARGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (255);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}
	public static byte[] fromIARGB(BufferedImage _img, PixelFormat _pxlFmt) {
		byte[] buffer = new byte[_pxlFmt.nbChannels() * _img.getHeight() * _img.getWidth()];

		int 	w   = _img.getWidth(),
				h   = _img.getHeight();
		int[]   src = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
		
		int id = 0;
		switch(_pxlFmt) {
		case PXF_L8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[j * w + i] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
									id++;
								}
							return buffer;

		case PXF_AL8:		for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[2 * (j * w + i)]     = (byte) (src[id++]);
									buffer[2 * (j * w + i) + 1] = (byte) (0.299 * src[id++] + 0.587 * src[id++] + 0.114 * src[id++]);
								}
							return buffer;

		case PXF_RGB8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									id++;
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									id++;
									buffer[3 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[3 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[3 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;

		case PXF_RGBA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
								}
							return buffer;
		case PXF_BGRA8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
								}
							return buffer;

		case PXF_ARGB8:	System.arraycopy(src, 0, buffer, 0, src.length);
		case PXF_ABGR8:	for(int j = 0; j < h; ++j)
								for(int i = 0; i < w; ++i) {
									buffer[4 * (j * w + i)]     = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 3] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 2] = (byte) (src[id++]);
									buffer[4 * (j * w + i) + 1] = (byte) (src[id++]);
								}
							return buffer;

		case PXF_YUV8:
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:			throw new NotYetImplementedException();
		}
	}


	
	
/*
	public static final IIORegistry registry;

	static {
		ImageIO.scanForPlugins();
		registry = IIORegistry.getDefaultInstance();
	}

	public static void registerNewReader(ImageReaderSpi _spi) {
		registry.registerServiceProvider(_spi);
	}
	public static void registerNewWriter(ImageWriterSpi _spi) {
		registry.registerServiceProvider(_spi);
	}
*/
	
	
	
	
	

	private void updateImage(WritableImage wr, BufferedImage image) {
	    if (image != null) {
	        PixelWriter pw = wr.getPixelWriter();
	        for (int x = 0; x < image.getWidth(); x++) {
	            for (int y = 0; y < image.getHeight(); y++) {
	                pw.setArgb(x, y, image.getRGB(x, y));
	            }
	        }
	    }
	}

	/*
	public void asImageFx() {
		WritableImage wimg = new WritableImage();

		 public Image getJavaFXImage(byte[] rawPixels, int width, int height) {
			    ByteArrayOutputStream out = new ByteArrayOutputStream();
			    try {
			        ImageIO.write((RenderedImage) createBufferedImage(rawPixels, width, height), "png", out);
			        out.flush();
			        } catch (IOException ex) {
			            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
			        }
			    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			    return new javafx.scene.image.Image(in);
			    }
		 
	}
	*/
	/*
	public void resizeCanvas(Image _dst, Image imageSource, int newWidth, int newHeight, int offsetX, int offsetY) {
		int sourceWidth = (int) imageSource.getWidth();
		int sourceHeight = (int) imageSource.getHeight();

		// No work needed here...
		if (sourceWidth == newWidth && sourceHeight == newHeight)
			return imageSource;

		WritableImage outputImage = new WritableImage(newWidth, newHeight);
		PixelReader pixelReader = imageSource.getPixelReader();
		PixelWriter pixelWriter = outputImage.getPixelWriter();
		WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();

		int[] buffer = new int[sourceWidth * sourceHeight];
		pixelReader.getPixels(0, 0, sourceWidth, sourceHeight, format, buffer, 0, sourceWidth);
		pixelWriter.setPixels(offsetX, offsetY, sourceWidth, sourceHeight, format, buffer, 0, sourceWidth);

		return outputImage;
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * FLATENNING
	**/
	
	public static int[]     asIntVector(BufferedImage image) {
		int w = image.getWidth(), h = image.getHeight();
		int[] ret = new int[h * w];
		if(image.getRaster().getNumDataElements() == 1) {
			Raster raster = image.getRaster();
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					ret[i + j * w] = raster.getSample(j, i, 0);
				}
			}
		} else {
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					ret[i + j * w] = image.getRGB(j, i);
				}
			}
		}
		return ret;
	}
	public static int[][]   asIntMatrix(BufferedImage image) {
		int w = image.getWidth(), h = image.getHeight();
		int[][] ret = new int[h][w];
		if(image.getRaster().getNumDataElements() == 1) {
			Raster raster = image.getRaster();
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					ret[i][j] = raster.getSample(j, i, 0);
				}
			}
		} else {
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					ret[i][j] = image.getRGB(j, i);
				}
			}
		}
		return ret;
	}
	public static int[][][] asIntSett(BufferedImage _img) {
		int w = _img.getWidth(), h = _img.getHeight();
		int bands = _img.getSampleModel().getNumBands();
		int[][][] ret = new int[bands][h][w];
		byte[] pixels = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();

		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				for(int k = 0; k < bands; k++) {
					if(k >= bands)
						break;
					ret[k][i][j] = pixels[bands * w * i + bands * j + k];
				}
			}
		}
		return ret;
	}

	public static BufferedImage[] split(BufferedImage _image, int _cols, int _rows) {
		int w = _image.getWidth() / _cols;
		int h = _image.getHeight() / _rows;
		int num = 0;
		BufferedImage imgs[] = new BufferedImage[w * h];
		for(int y = 0; y < _rows; y++) {
			for(int x = 0; x < _cols; x++) {
				imgs[num] = new BufferedImage(w, h, _image.getType());
				Graphics2D g = imgs[num].createGraphics();
				g.drawImage(_image, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
				g.dispose();
				num++;
			}
		}
		return imgs;
	}

	public static BufferedImage centerCropIfNeeded(BufferedImage img) {
		int x = 0;
		int y = 0;
		int height = img.getHeight();
		int width = img.getWidth();
		int diff = Math.abs(width - height) / 2;

		if(width > height) {
			x = diff;
			width = width - diff;
		} else if(height > width) {
			y = diff;
			height = height - diff;
		}
		return img.getSubimage(x, y, width, height);
	}

	public static BufferedImage scalingIfNeed(BufferedImage image, int _height, int _width) {
		BufferedImage resized = new BufferedImage(_width, _height, image.getType());
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, _width, _height, 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		return resized;
	}
	
	public static BufferedImage scalingIfNeed(BufferedImage image, int _height, int _width, int _type, boolean needAlpha) {
		if(_height > 0 && _width > 0 && (image.getHeight() != _height || image.getWidth() != _width)) {
			java.awt.Image scaled = image.getScaledInstance(_width, _height, java.awt.Image.SCALE_SMOOTH);

			if(needAlpha && image.getColorModel().hasAlpha() && _type == BufferedImage.TYPE_4BYTE_ABGR) {
				return BufferedImages.fromAWT(scaled, BufferedImage.TYPE_4BYTE_ABGR);
			} else {
				if(_type == BufferedImage.TYPE_BYTE_GRAY)
					return BufferedImages.fromAWT(scaled, BufferedImage.TYPE_BYTE_GRAY);
				else
					return BufferedImages.fromAWT(scaled, BufferedImage.TYPE_3BYTE_BGR);
			}
		} else {
			if(image.getType() == BufferedImage.TYPE_4BYTE_ABGR || image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
				return image;
			} else if(needAlpha && image.getColorModel().hasAlpha() && _type == BufferedImage.TYPE_4BYTE_ABGR) {
				return BufferedImages.fromAWT(image, BufferedImage.TYPE_4BYTE_ABGR);
			} else {
				if(_type == BufferedImage.TYPE_BYTE_GRAY)
					return BufferedImages.fromAWT(image, BufferedImage.TYPE_BYTE_GRAY);
				else
					return BufferedImages.fromAWT(image, BufferedImage.TYPE_3BYTE_BGR);
			}
		}
	}

	public static BufferedImage resize(BufferedImage _image, int _w, int _h) {
		int w = _image.getWidth();
		int h = _image.getHeight();

		BufferedImage dimg = new BufferedImage(_w, _h, _image.getType());

		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(_image, 0, 0, _w, _h, 0, 0, w, h, null);
		g.dispose();

		return dimg;
	}

	public static BufferedImage crop(BufferedImage _image, int _x, int _y, int _w, int _h) {
		int w = _image.getWidth();
		int h = _image.getHeight();

		BufferedImage dimg = new BufferedImage(_w, _h, _image.getType());

		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(_image, 0, 0, _w, _h, _x, _y, w, h, null);
		g.dispose();

		return dimg;
	}

	public static BufferedImage rotate(BufferedImage _image, int _angle) {
		int w = _image.getWidth();
		int h = _image.getHeight();

		BufferedImage dimg = new BufferedImage(w, h, _image.getType());

		Graphics2D g = dimg.createGraphics();
		g.rotate(Math.toRadians(_angle), w / 2, h / 2);
		g.drawImage(_image, null, 0, 0);

		return dimg;
	}

	public static BufferedImage inverseColor(BufferedImage _image) {
		BufferedImage inverseImg = new BufferedImage(_image.getWidth(), _image.getHeight(), _image.getType());

		Graphics2D g2d = inverseImg.createGraphics();
		g2d.drawImage(_image, null, 0, 0);
		g2d.dispose();

		for(int i = 0; i < inverseImg.getWidth(); ++i) {
			for(int j = 0; j < inverseImg.getHeight(); ++j) {
				Color pixelcolor = new Color(inverseImg.getRGB(i, j));

				int r = pixelcolor.getRed();
				int g = pixelcolor.getGreen();
				int b = pixelcolor.getBlue();

				r = Math.abs(r - 255);
				g = Math.abs(g - 255);
				b = Math.abs(b - 255);

				int rgb = new Color(r, g, b).getRGB();

				inverseImg.setRGB(i, j, rgb);
			}
		}

		return inverseImg;
	}

	public static BufferedImage superpose(BufferedImage im, BufferedImage map) {
		return superpose(im, map, 1.0f);
	}
	public static BufferedImage superpose(BufferedImage im, BufferedImage map, float alpha) {
		BufferedImage dest = new BufferedImage(im.getWidth(), im.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D destG = dest.createGraphics();
		destG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
		destG.drawImage(im, 0, 0, null);
		destG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		destG.drawImage(map, 0, 0, null);
		return dest;
	}

	public static BufferedImage crush(BufferedImage bg, BufferedImage fg) {
		int w = bg.getWidth();
		int h = bg.getHeight();
		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = res.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
		g2d.drawImage(bg, 0, 0, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g2d.drawImage(fg, 0, 0, null);
		return res;
	}

	public static BufferedImage hConcatGrayScale(BufferedImage im1, BufferedImage im2) {
		int w1 = im1.getWidth();
		int h1 = im1.getHeight();
		int w2 = im2.getWidth();
		int h2 = im2.getHeight();
		int w = w1 + w2;
		int h = Math.max(h1, h2);
		BufferedImage imGray = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);

		Graphics2D g2d = imGray.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, w, h);
		Graphics g = imGray.getGraphics();
		g.drawImage(im1, 0, 0, null);

		WritableRaster oup = imGray.getRaster();
		WritableRaster inp = im2.copyData(null);
		for(int x = 0; x < w2; x++)
			for(int y = 0; y < h2; y++)
				oup.setSample(x + w1, y, 0, inp.getSample(x, y, 0));
		return imGray;
	}
	public static BufferedImage vConcatGrayScale(BufferedImage im1, BufferedImage im2) {
		int w1 = im1.getWidth();
		int h1 = im1.getHeight();
		int w2 = im2.getWidth();
		int h2 = im2.getHeight();
		int w = Math.max(w1, w2);
		int h = h1 + h2;
		BufferedImage imGray = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);

		Graphics2D g2d = imGray.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, w, h);
		Graphics g = imGray.getGraphics();
		g.drawImage(im1, 0, 0, null);

		WritableRaster oup = imGray.getRaster();
		WritableRaster inp = im2.copyData(null);
		for(int x = 0; x < w2; x++)
			for(int y = 0; y < h2; y++)
				oup.setSample(x, y + h1, 0, inp.getSample(x, y, 0));
		return imGray;
	}

	public static BufferedImage hConcatRGB(BufferedImage im1, BufferedImage im2) {
		int w1 = im1.getWidth();
		int h1 = im1.getHeight();
		int w2 = im2.getWidth();
		int h2 = im2.getHeight();
		int w = w1 + w2;
		int h = Math.max(h1, h2);
		BufferedImage imRGB = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = imRGB.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, w, h);
		Graphics g = imRGB.getGraphics();
		g.drawImage(im1, 0, 0, null);

		WritableRaster oup = imRGB.getRaster();
		WritableRaster inp = im2.copyData(null);
		for(int x = 0; x < w2; x++)
			for(int y = 0; y < h2; y++)
				for(int c = 0; c < 3; c++)
					oup.setSample(x + w1, y, c, inp.getSample(x, y, c));
		return imRGB;
	}

	public static BufferedImage framed(BufferedImage im, int _w) {
		int w = im.getWidth() + _w * 2;
		int h = im.getHeight() + _w * 2;
		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		;
		WritableRaster oup = res.getRaster();
		WritableRaster inp = im.getRaster();
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++)
				oup.setSample(x, y, 0, 0);
		for(int x = 0 + _w; x < w - _w; x++)
			for(int y = 0 + _w; y < h - _w; y++) {
				int gray = inp.getSample(x - _w, y - _w, 0);
				oup.setSample(x, y, 0, gray);
			}
		return res;
	}

	public static BufferedImage translucentImage(BufferedImage _image, float _transperancy) {
		BufferedImage aimg = new BufferedImage(_image.getWidth(), _image.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D g = aimg.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _transperancy));
		g.drawImage(_image, null, 0, 0);
		g.dispose();
		return aimg;
	}

	public static BufferedImage translucentImage(BufferedImage _image, float _transperancy, Color _color) {
		BufferedImage dimg = new BufferedImage(_image.getWidth(), _image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dimg.createGraphics();
		g.setComposite(AlphaComposite.Src);
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// _transperancy));
		g.drawImage(_image, null, 0, 0);
		g.dispose();

		for(int i = 0; i < dimg.getHeight(); i++) {
			for(int j = 0; j < dimg.getWidth(); j++) {
				if(dimg.getRGB(j, i) == _color.getRGB()) {
					dimg.setRGB(j, i, 0x8F1C1C);
				}
			}
		}

		return dimg;
	}

	public int[] getBoundingBoxStroke(BufferedImage strk, Color corFundo) {
		//BufferedImage strk = this.drawPanel.getStrokeImage();
		int w = strk.getWidth();
		int h = strk.getHeight();
		//procura canto esq superior e dir inferior do stroke
		int minx = -1, maxx = -1, miny = -1, maxy = -1;
		boolean empty = true;
		WritableRaster inp = strk.copyData(null);
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				//if(strk.getRGB(x, y) != 0xffffff) {
				if(inp.getSample(x, y, 0) != corFundo.getRed() ||
						inp.getSample(x, y, 1) != corFundo.getGreen() ||
						inp.getSample(x, y, 2) != corFundo.getBlue()) {
					empty = false;
					if(x < minx || minx == -1)
						minx = x;
					if(x > maxx || maxx == -1)
						maxx = x;
					if(y < miny || miny == -1)
						miny = y;
					if(y > maxy || maxy == -1)
						maxy = y;
				}
		int topx = minx, topy = miny, botx = maxx, boty = maxy;
		int[] res = null;
		if(!empty) {
			res = new int[4];
			res[0] = topx;
			res[1] = topy;
			res[2] = botx;
			res[3] = boty;
		}
		return res;
	}

	public static BufferedImage horizontalFlip(BufferedImage _image) {
		int w = _image.getWidth();
		int h = _image.getHeight();

		BufferedImage dimg = new BufferedImage(w, h, _image.getType());

		Graphics2D g = dimg.createGraphics();
		g.drawImage(_image, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();

		return dimg;
	}

	public static BufferedImage verticalFlip(BufferedImage _image) {
		int w = _image.getWidth();
		int h = _image.getHeight();

		BufferedImage dimg = new BufferedImage(w, h, _image.getType());

		Graphics2D g = dimg.createGraphics();
		g.drawImage(_image, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();

		return dimg;
	}

	public static byte[] getBytes(BufferedImage _image) {
		// byte[] pixels = (byte[])imgBuffer.getRaster().getDataElements(0, 0, imgBuffer.getWidth(), imgBuffer.getHeight(), null);
		return ((DataBufferByte) _image.getData().getDataBuffer()).getData();
	}
	public static byte[] getBytesForChannel(BufferedImage _image, int _i) { //int channel[] = { 24, 16, 8, 0 };
		// http://stackoverflow.com/questions/2615522/java-bufferedimage-getting-red-green-and-blue-individually
		int w = _image.getWidth();
		int h = _image.getHeight();

		byte[] srcBuffer = ((DataBufferByte) _image.getData().getDataBuffer()).getData();
		byte[] dstBuffer = new byte[w * h];

		for(int i = 0; i < w * h; i++)
			dstBuffer[i] = srcBuffer[_i * i];

		return dstBuffer;
	}

	public static int imageDepth(int _bufferedImageType) {
		switch (_bufferedImageType) {
		case BufferedImage.TYPE_3BYTE_BGR:
			return 3;
		case BufferedImage.TYPE_4BYTE_ABGR:
			return 4;
		case BufferedImage.TYPE_BYTE_BINARY:
			return 1;
		case BufferedImage.TYPE_BYTE_GRAY:
			return 1;
		case BufferedImage.TYPE_BYTE_INDEXED:
			return 1;
		case BufferedImage.TYPE_INT_ARGB:
			return 4;
		case BufferedImage.TYPE_INT_BGR:
			return 4;
		case BufferedImage.TYPE_INT_RGB:
			return 4;
		default:
			return 4;
		}
	}
	/* TODO:: OpenCV Integration !!!
		public static Image mat2Img(Mat frame) {
			MatOfByte buffer = new MatOfByte();
			Highgui.imencode(".png", frame, buffer);
			return new Image(new ByteArrayInputStream(buffer.toArray()));
		}
		public static Mat img2Mat(Image image) {
			PixelReader pixelReader = image.getPixelReader();
	
			int width  = (int) image.getWidth();
			int height = (int) image.getHeight();
	
			Mat mat = new Mat((int) height, (int) width, CvType.CV_8UC3);
	
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					Color pixel = pixelReader.getColor(x, y);
					mat.put(y, x, (byte) (pixel.getRed() * 255) & 0xFF, (byte) (pixel.getGreen() * 255) & 0xFF, (byte) (pixel.getBlue() * 255) & 0xFF);
				}
			}
	
			return mat;
		}
	*/
	private static boolean isHeadless() {
		return GraphicsEnvironment.isHeadless();
	}

	private static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}

	public static BufferedImage createCompatibleImage(InputStream in) throws IOException {
		BufferedImage image = ImageIO.read(in);
		if(image == null)
			return null;
		return toCompatibleImage(image);
	}

	public static BufferedImage toCompatibleImage(BufferedImage image) {
		if(isHeadless())
			return image;

		if(image.getColorModel().equals(getGraphicsConfiguration().getColorModel()))
			return image;

		BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics g = compatibleImage.getGraphics();

		try {
			g.drawImage(image, 0, 0, null);
		} finally {
			g.dispose();
		}

		return compatibleImage;
	}
	


	public static BufferedImage copy(BufferedImage img) {
		// SEE:: http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
		ColorModel cm = img.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = img.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}


	

}
