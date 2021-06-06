package fr.javafx.scene.control.viewport.planar.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import fr.java.math.geometry.BoundingBox;
import fr.java.rasters.rasters.XRasterBufferedImage;
import fr.java.rasters.rasters.XRasterImageFx;
import fr.javafx.scene.control.raster.RasterViewportControl;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.scene.control.viewport.planar.implementations.tiles.TileViewportControl;
import fr.javafx.scene.control.viewport.planar.utils.minimap.PlaneViewportMinimapControl;
import fr.javafx.scene.control.viewport.utils.statusbar.ViewportStatusBar;
import fr.javafx.stage.StageExt;
import fr.javafx.utils.FxImageUtils;
import fr.media.image.utils.BufferedImages;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

@Deprecated
public class PlaneViewportStage extends StageExt {
	Scene     					scene;

	PlaneViewportControl		control;
	PlaneViewportMinimapControl minimap;
	ViewportStatusBar			status;
	Region     					topLeft;

	BorderPane 					mainPane;
	VBox       					leftPane;

	private final Function<BoundingBox.TwoDims, Paint> modelToPaint = (model) -> {
		if(model == null)
			return Color.RED;

		if(model instanceof XRasterBufferedImage) {
			XRasterBufferedImage xBImg = (XRasterBufferedImage) model;
			BufferedImage        bimg  = xBImg.getObject();
			Paint         		 paint = new ImagePattern(FxImageUtils.toFX(bimg));
			return paint;
			
		} else if(model instanceof XRasterImageFx) {
			XRasterImageFx xImg = (XRasterImageFx) model;
			Image          img  = xImg.getObject();
			Paint          paint = new ImagePattern(img);
			return paint;

		} else {
			return Color.ORANGE;
		}
	};
	
	public PlaneViewportStage(PlaneViewportControl _control) {
		super();
		control = _control;
		minimap = new PlaneViewportMinimapControl(control);
		status  = new ViewportStatusBar(control);

		build();
		initModelPaint();
		setScene(scene = new Scene(mainPane, 1280, 780));
	}
	public PlaneViewportStage(PlaneViewportControl _control, Region _custom) {
		super();
		control = _control;
		minimap  = new PlaneViewportMinimapControl(control);
		status   = new ViewportStatusBar(control);

		topLeft   = _custom;

		build();
		initModelPaint();
		setScene(scene = new Scene(mainPane, 1280, 780));
	}

	
	private void initModelPaint() {
		if(control instanceof TileViewportControl) {
			Path earth = Paths.get(System.getProperty("resources_path") + "projects/VirtualUniverse/resources/universe/milky-way/sun/Earth/Earth.png");
			try { 	Paint paint = new ImagePattern(FxImageUtils.toFX(BufferedImages.loadImage(earth)));
					minimap.setModelPaint(paint);
			} catch(IOException e) { e.printStackTrace(); }

			return ;
		}
		
		
		
		
		if(control instanceof RasterViewportControl) {
			RasterViewportControl rasterCtrl = (RasterViewportControl) control;

			if(rasterCtrl.getRaster() != null) {
				minimap.setModelPaint(modelToPaint.apply(rasterCtrl.getRaster()));

				rasterCtrl.rasterProperty().addListener(evt -> minimap.setModelPaint(modelToPaint.apply(rasterCtrl.getRaster())));
			}
			
//			FxBeanPropertyBindings.bind(rasterCtrl.rasterProperty(),    minimap.modelPaintProperty(), modelToPaint);
//			Bindings.bind(rasterCtrl.rasterProperty(),    minimap.modelPaintProperty(), modelToPaint);
			return ;
		}
	}
	
	public void build() {
		mainPane = new BorderPane();

		leftPane = new VBox();
		leftPane.setPrefWidth(320);

		if(topLeft == null)
			topLeft = new VBox();

		minimap.setPrefSize(320, 240);
		VBox.setVgrow(topLeft, Priority.ALWAYS);
		leftPane.getChildren().addAll(topLeft, minimap);
		
		control.addEventFilter(MouseEvent.MOUSE_ENTERED, (me) -> control.requestFocus());

		mainPane.setCenter (control);
		mainPane.setLeft   (leftPane);
		mainPane.setBottom (status);
		
		initStatusBar();
	}

	public void setLeftPane(Region _control) {
		leftPane.getChildren().remove(topLeft);

		if(_control != null)
			leftPane.getChildren().add(0, topLeft = _control);
	}

	private void initStatusBar() {
		status.setText("");

		Separator leftSep = new Separator(Orientation.VERTICAL);
		leftSep.setPadding(new Insets(0, 5, 0, 5));
		status.getLeftItems().add(leftSep);


		Separator rightSep = new Separator(Orientation.VERTICAL);
		rightSep.setPadding(new Insets(0, 5, 0, 5));
		status.getRightItems().add(0, rightSep);
	}

}
