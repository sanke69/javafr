package fr.java.rasters.rasters;

import java.nio.IntBuffer;
import java.text.NumberFormat;
import java.util.function.Function;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.raster.XRaster;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

public class XRasterImageFx implements XRaster {
	private static final long serialVersionUID = 999L;

	static {
		XRaster.Collection.registerNewSupplier(javafx.scene.image.Image.class, 			(Function<javafx.scene.image.Image, XRaster>) 			XRasterImageFx::new);
		XRaster.Collection.registerNewSupplier(javafx.scene.image.WritableImage.class, 	(Function<javafx.scene.image.WritableImage, XRaster>) 	XRasterImageFx::new);
	}

	Image image;
	int[] buffer;

	public XRasterImageFx(Image _i) {
		image = _i;

		buffer = new int[(int) (image.getWidth() * image.getHeight())];
		refresh();
	}

	@Override 
	public void refresh() {
		PixelReader pr = image.getPixelReader();
		WritablePixelFormat<IntBuffer> wf = PixelFormat.getIntArgbInstance();

		pr.getPixels(0, 0, (int) image.getWidth(), (int) image.getHeight(), wf, buffer, 0, (int) image.getWidth());
	}

	@Override public Image 	getObject() 							{ return image; }
	@Override public double getWidth()  							{ return (int) image.getWidth(); }
	@Override public double	getHeight() 							{ return (int) image.getHeight(); }
	@Override public int	getDepth()  							{ return 3; }

	@Override public byte 	getValue(int _i, int _j, int _k) 		{ return (byte) ((buffer[(int) (_j * getWidth() + _i)] >> (_k * 8)) & 0xff); }

	@Override public byte   getLuminance(int _i, int _j)  			{ return (byte) ((buffer[(int) (_j * getWidth() + _i)]      ) & 0xff); }

	          public byte   getAlpha(int _i, int _j) 				{ return (byte) ((buffer[(int) (_j * getWidth() + _i)] >> 24) & 0xff); }
	@Override public byte   getRed(int _i, int _j)   				{ return (byte) ((buffer[(int) (_j * getWidth() + _i)] >> 16) & 0xff); }
	@Override public byte   getGreen(int _i, int _j) 				{ return (byte) ((buffer[(int) (_j * getWidth() + _i)] >>  8) & 0xff); }
	@Override public byte   getBlue(int _i, int _j)  				{ return (byte) ((buffer[(int) (_j * getWidth() + _i)]      ) & 0xff); }

	@Override
	public double 			getX() {
		return 0;
	}
	@Override
	public double 			getY() {
		return 0;
	}

	@Override
	public boolean 			isEmpty() {
		return getWidth() != 0 && getHeight() != 0;
	}

	@Override
	public boolean 			contains(Coordinate _pt) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 			contains(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			intersects(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}
	
	@Override
	public String 			toString(NumberFormat _nf) {
		return "XRasterImageFx:: " + getWidth() + "x" + getHeight();
	}

}
