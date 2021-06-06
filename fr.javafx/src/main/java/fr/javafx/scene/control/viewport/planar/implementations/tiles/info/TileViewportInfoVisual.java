package fr.javafx.scene.control.viewport.planar.implementations.tiles.info;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProvider;
import fr.java.patterns.tileable.TileViewport;
import fr.java.sdk.math.Points;
import fr.javafx.scene.control.viewport.planar.utils.info.PlaneViewportInfoVisual;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class TileViewportInfoVisual extends PlaneViewportInfoVisual<TileViewportInfoControl> {

	protected CategoryPane providerPane;

	protected Label providerLevelRange;
	protected Label providerLevelCurrent;
	protected Label providerMapDimension;
	protected Label providerTileDomain;

	protected Label viewportMap;

	protected Label cursorInMap;
	protected Label cursorInTile;

	public TileViewportInfoVisual(TileViewportInfoControl _skinnable) {
		super(_skinnable);
		build();
	}

	@Override
	public TileViewportInfoControl getSkinnable() {
		return (TileViewportInfoControl) skinnable;
	}

	private void build() {
		providerPane         = new CategoryPane("Provider Info");
		providerLevelRange   = providerPane.addCategory("Level Range:");
		providerLevelCurrent = providerPane.addCategory(" - Current Level:");
		providerMapDimension = providerPane.addCategory("Map Bounds:");
		providerTileDomain   = providerPane.addCategory(" - Tile Domain:");

		viewportMap          = modelPane.addCategory("Map:");
		
		cursorInMap  = cursorPane.addCategory("Map:");
		cursorInTile = cursorPane.addCategory("Tile:");
		
		
		pane.getChildren().add(1, providerPane);
	}

/*
	@Override
	public Node 		getNode() {
		if(pane != null)
			return pane;
		
		pane = new VBox(buildTileProviderInfo(), 
						buildViewportInfo(),  
						buildModelInfo(), 
						buildWindowInfo(), 
						buildViewInfo(), 
						buildCursorInfo());
		return pane;
	}
*/
	public void 		refreshAll(Point2D _cursor) {
		super.refreshAll(_cursor);

 		TileViewport<?, ?> 	viewport 	= getSkinnable().getTileViewport();
		TileProvider<?> 	provider 	= getSkinnable().getTileProvider();
		
		int					level       = viewport.getMapLevel();

		final DecimalFormat scaleFormat = new DecimalFormat("#.#####");
		final DecimalFormat df 			= new DecimalFormat("#.##");
		final NumberFormat 	nf          = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);

		Platform.runLater(() -> {});
		providerLevelRange			.	setText(provider.minLevel() + " - " + provider.maxLevel());
		providerLevelCurrent		.	setText("" + viewport.getMapLevel());
		providerMapDimension		.	setText(provider.mapSize(level) + "x" + provider.mapSize(level));
		providerTileDomain			.	setText(provider.tileCount(level) + "x" + provider.tileCount(level) + "@" + provider.tileSize(level));

		viewportMap					.	setText(viewport.getMapBounds().toString(df));

//		viewScale			.	setText("x" + scaleFormat.format(viewport.getViewScale()) + " (x" + scaleFormat.format(viewport.getScaleForMapLevel()) + ")");
		viewScale			.	setText("x" + scaleFormat.format(viewport.getViewScale()) + " (x" + scaleFormat.format(viewport.getMapScale()) + ")");

		Point2D 		  cursor   = Points.of(_cursor.getX(), _cursor.getY());
//		Coordinate.Planar cInView  = viewport.windowInView(cursor);
		Coordinate.TwoDims cInMap   = viewport.windowInMap(cursor, level);
		TileCoordinate    cInTile  = viewport.windowInTile(cursor, level);
//		Coordinate.Planar cInModel = viewport.windowInModel(cursor);
//		Object cInModel = viewport.windowInModel(cursor);

//		cursorInWindow	.setText(_cursor.toString(df));
//		cursorInView	.setText(cInView == null ? Points.NaN2.toString() : cInView.toString(df));
		cursorInMap		.setText(cInMap == null ? Points.NaN2.toString() : cInMap.toString(df));
		cursorInTile	.setText(cInTile == null ? Points.NaN2.toString() : cInTile.toString(df));
//		cursorInModel	.setText(cInModel == null ? Points.NaN2.toString() : cInModel.toString(df));
//		cursorInModel	.setText(cInModel == null ? Points.NaN2.toString() : cInModel.toString());
	}

}
