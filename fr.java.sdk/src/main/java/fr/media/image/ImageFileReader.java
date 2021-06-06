package fr.media.image;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class ImageFileReader<T> {
	private static final Function<Path, BufferedImage> 	frameSupplierAwt = p -> {
		try {
			return ImageIO.read(new FileInputStream(p.toFile()));
		} catch (IOException e) { e.printStackTrace(); }
		return null;
	};
	private static final Function<Path, Image> 			frameSupplierFx  = p -> {
		try {
			return new Image(new FileInputStream(p.toFile()));
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		return null;
	};

	public static class Awt extends ImageFileReader<BufferedImage> {
	
		Awt(Path _path) {
			super(_path, frameSupplierAwt);
		}

	}
	public static class Fx  extends ImageFileReader<Image> {
	
		Fx(Path _path) {
			super(_path, frameSupplierFx);
		}

	}

	Function<Path, T> 	bufferSupplier;
	T 					buffer;

	public static ImageFileReader.Awt 	newAwt(Path _path) {
		return new Awt(_path);
	}
	public static ImageFileReader.Fx	newFx(Path _path) {
		return new Fx(_path);
	}

	ImageFileReader(Path _path, Function<Path, T> _frameSupplier) {
		super();
		bufferSupplier = _frameSupplier;
		buffer         = _frameSupplier.apply(_path);
	}

}
