package fr.javafx.xtra.filedropper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import fr.filedropper.FileDropper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ImageFileAction implements FileDropper.Action {
	public static final List<String>		extensions	= Arrays.asList(".bmp", ".png", ".jpg");

	private ObjectProperty<BufferedImage> 	imageProperty;
	private Consumer<BufferedImage> 		onNewImage;

	public ImageFileAction() {
		super();
		imageProperty = new SimpleObjectProperty<BufferedImage>(this, "image");
	}
	public ImageFileAction(Consumer<BufferedImage> _onNewImage) {
		super();
		imageProperty = new SimpleObjectProperty<BufferedImage>(this, "image");
		onNewImage    = _onNewImage;
	}

	@Override
	public boolean test(Path t) {
		File file = t.toFile();

		if(file.getName().lastIndexOf('.') == -1)
			return false;

		String extension = file.getName().substring(file.getName().lastIndexOf('.'));

		return extensions.contains(extension);
	}

	@Override
	public void accept(Path t) {
		try {
			BufferedImage img = ImageIO.read(new FileInputStream(t.toFile()));

			if(img != null) {
				imageProperty.set(img);

				if(onNewImage != null)
					onNewImage.accept(img);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
