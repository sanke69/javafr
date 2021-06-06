package fr.javafx.sdk.controls.media.skins;

import java.awt.image.BufferedImage;

import fr.java.media.image.Image;
import fr.javafx.sdk.controls.media.MediaViewer;
import fr.javafx.utils.FxImageUtils;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.image.ImageView;

public class MediaViewerDefaultSkin extends ImageView implements Skin<MediaViewer> {
	private final MediaViewer control;

	public MediaViewerDefaultSkin(MediaViewer _control) {
		super();
		control = _control;

		setPreserveRatio(true);
		setFitWidth(1280);
		setFitHeight(720);
		resize(1280,  720);
		setStyle("-fx-background-color: blue;");

		fitWidthProperty().bind(getSkinnable().prefWidthProperty());
		fitHeightProperty().bind(getSkinnable().prefHeightProperty());

		getSkinnable().playableProperty().addListener((_obs, _old, _new) -> {
			if(_new != null && _new instanceof Image) {
				Image mediaImage = (Image) _new;
				if(mediaImage.data() instanceof BufferedImage)
					imageProperty().set( FxImageUtils.toFXImage((BufferedImage) mediaImage.data(), null) );
			}
		});
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
