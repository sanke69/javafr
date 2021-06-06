package fr.javafx.stage.impl.goodies;

import fr.javafx.lang.enums.StageAnchor;
import fr.javafx.scene.control.raster.RasterViewportControl;
import fr.javafx.stage.StageExt;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

public class RasterSpy extends StageExt {

	RasterViewportControl control;
	
	public RasterSpy() {
		super(StageStyle.DECORATED, StageAnchor.SCREEN_TOP_RIGHT);
		setScene(new Scene(getRasterControl()));

		control = (RasterViewportControl) getScene().getRoot().getChildrenUnmodifiable().get(0);
		
		show();
	}
	
	public void updateRaster(Image _img) {
		control.setRaster(_img);
	}

	public static Pane getRasterControl() {
		BorderPane     			xrasterContainer = new BorderPane(); 
		RasterViewportControl 	xrasterView      = new RasterViewportControl();
		
		xrasterContainer.setCenter(xrasterView);
		
		return xrasterContainer;
	}

}
