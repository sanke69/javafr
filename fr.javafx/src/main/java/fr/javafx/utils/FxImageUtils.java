package fr.javafx.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

public class FxImageUtils {

	public static void saveToFile(Image _img, String _format, Path _path) throws IOException {
		ImageIO.write(fromFXImage​(_img, null), _format, _path.toFile());
	}

	public static void saveToFileRAW(Image _img, Path _path) throws IOException {
		int         width  = (int) _img.getWidth();
		int         height = (int) _img.getHeight();
		PixelReader reader = _img.getPixelReader();
		byte[]      buffer = new byte[width * height * 4];
		WritablePixelFormat<ByteBuffer> format = PixelFormat.getByteBgraInstance();
		reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
		try {
		    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(_path.toFile()));
		    for(int count = 0; count < buffer.length; count += 4) {
		        out.write(buffer[count + 2]);
		        out.write(buffer[count + 1]);
		        out.write(buffer[count]);
		        out.write(buffer[count + 3]);
		    }
		    out.flush();
		    out.close();
		} catch(IOException e) {
		    e.printStackTrace();
		}
	}

	public static Image toFXImage(BufferedImage _bimg,  WritableImage _wimg) {
		return SwingFXUtils.toFXImage(_bimg, _wimg);
	}

	public static BufferedImage fromFXImage​(Image _img, BufferedImage _bimg) {
		return SwingFXUtils.fromFXImage(_img, _bimg);
	}
	
	

	public static javafx.scene.image.Image toFX(BufferedImage _img, boolean _fast) {
		return _fast ? toFXImage(_img, null) : toFX(_img);
	}
	public static javafx.scene.image.Image toFX(BufferedImage _img) { 
        WritableImage wr = null;
        if (_img != null) {
            wr = new WritableImage(_img.getWidth(), _img.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < _img.getWidth(); x++) {
                for (int y = 0; y < _img.getHeight(); y++) {
                    pw.setArgb(x, y, _img.getRGB(x, y));
                }
            }
        }
        return wr;
	}
	

	public static BufferedImage fromFX(javafx.scene.image.Image _image) {
		return fromFXImage​(_image, null);
	}

}
