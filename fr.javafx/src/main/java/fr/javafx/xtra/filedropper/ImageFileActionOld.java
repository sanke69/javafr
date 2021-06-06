package fr.javafx.xtra.filedropper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import fr.javafx.scene.control.overlay.drags.FileDropControl;
import fr.javafx.scene.control.raster.RasterViewportControl;
import fr.javafx.stage.StageExt;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageFileActionOld implements FileDropControl.Action {
	public static final List<String>		extensions	= Arrays.asList(".png", ".jpg");

	public StringProperty					titleProperty;
	public ObjectProperty<BufferedImage> 	imageProperty;

	Stage 									imageStage;
	RasterViewportControl					imageViewer;

	public ImageFileActionOld() {
		super();
		titleProperty = new SimpleStringProperty("img[fmt=?]://???");
		imageProperty = new SimpleObjectProperty<BufferedImage>(this, "image");

		imageStage = createImageViewer();

		imageStage.titleProperty().bind(titleProperty);

//		imageViewerStage.show();
		imageStage.setAlwaysOnTop(true);
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
		BufferedImage img;
		try {
			img = ImageIO.read(new FileInputStream(t.toFile()));
			setRaster(img);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

		System.out.println(t);
	}

	public 		Stage 	createImageViewer() {
		StageExt imageViewerStage = new StageExt(StageStyle.DECORATED
//										 , StageAnchor.SCREEN_BOTTOM_RIGHT 
//										 , Screens.getLeftestScreen()
										 );

		imageViewerStage.setTitle("Data Viewer");
		imageViewerStage.setScene(new Scene( imageViewer = new RasterViewportControl() ));
		imageViewerStage.setSize(800, 600);
		imageViewerStage.setX(100);
		imageViewerStage.setY(100);

		return imageViewerStage;
	}

	public void setRaster(Object _o) {
		imageViewer.setRaster(_o);
		imageStage.show();
		imageStage.setAlwaysOnTop(true);
	}

}
