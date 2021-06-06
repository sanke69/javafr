package fr.javafx.sdk.controls.media.skins;

import java.awt.image.BufferedImage;

import fr.java.media.image.Image;
import fr.javafx.scene.control.raster.RasterViewportControl;
import fr.javafx.sdk.controls.media.MediaViewer;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;
@Deprecated
public class MediaViewerXRasterSkin extends RasterViewportControl implements Skin<MediaViewer> {
	private final MediaViewer control;

	ChangeListener<Image>  lastImageListener;
	ChangeListener<Number> lastCurrentTimeListener;

	public MediaViewerXRasterSkin(MediaViewer _control) {
		super();
		control = _control;

		_control.playableProperty().addListener((_obs, _old, _new) -> {
			if(_new != null && _new instanceof Image) {
				Image mediaImage = (Image) _new;
				if(mediaImage.data() instanceof BufferedImage)
					setRaster(mediaImage.data());
			}
//			setRaster(_new.imageProperty().get());
/*
			if(_old != null && lastImageListener != null) {
				_new.imageProperty().removeListener(lastImageListener);
//				_new.currentFrameProperty().removeListener(lastCurrentTimeListener);
			}

			if(_new != null) {
				_new.imageProperty().addListener(lastImageListener = (_img, _oldImg, _newImg) -> setRaster(_newImg));
//				_new.currentFrameProperty().addListener(lastCurrentTimeListener = (_obsTime, _oldTime, _newTime) -> requestRefresh());
			}
*/
		});

		autofitContentToControl(true);

	//	setRepositionable(true);
	}

	@Override
	public MediaViewer getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {		
	}

}
