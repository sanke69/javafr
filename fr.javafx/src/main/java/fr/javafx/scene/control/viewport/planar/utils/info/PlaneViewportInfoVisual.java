package fr.javafx.scene.control.viewport.planar.utils.info;

import java.text.DecimalFormat;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.BoundingBoxes;
import fr.java.sdk.math.Coordinates;
import fr.java.sdk.math.Points;
import fr.javafx.behavior.Visual;
import fr.javafx.scene.control.viewport.utils.info.ViewportInfoVisual;
import javafx.scene.control.Label;

public class PlaneViewportInfoVisual<PVP extends PlaneViewportInfoControl> extends ViewportInfoVisual<PVP> implements Visual<PVP> {
	protected Label modelCorners;

	protected Label modelBounds;
	protected Label modelBoundsVisible;

	protected Label viewBounds;
	protected Label viewBoundsVisible;
	protected Label viewScale;
	protected Label viewAnchor;

	protected Label window;
	protected Label windowBounds;
	protected Label windowBoundsInView;

	protected Label cursorInModel;
	protected Label cursorInView;
	protected Label cursorInWindow;

	public PlaneViewportInfoVisual(PVP _skinnable) {
		super(_skinnable);

		modelCorners             = modelPane.addCategory("- Corners:");

		modelBounds              = modelPane.addCategory("Bounds:");
		modelBoundsVisible       = modelPane.addCategory("- Visible:");

		viewBounds               = viewPane.addCategory("Bounds:");
		viewBoundsVisible        = viewPane.addCategory("- Visible:");
		viewScale                = viewPane.addCategory("Scale:");
		viewAnchor               = viewPane.addCategory("Anchor:");

		window                   = windowPane.addCategory("Class:");
		windowBounds             = windowPane.addCategory("Bounds:");
		windowBoundsInView       = windowPane.addCategory("In View:");

		cursorInModel            = cursorPane.addCategory("in Model:");
		cursorInView             = cursorPane.addCategory("in View:");
		cursorInWindow           = cursorPane.addCategory("in Window:");
	}

	public void refreshAll(Point2D _cursor) {
		super.refreshAll(_cursor);
		setAllUndef();

		DecimalFormat df = new DecimalFormat("#.#");
		DecimalFormat sf = new DecimalFormat("#.####");

		Viewport.TwoDims<?, Coordinate.TwoDims> viewport   = (Viewport.TwoDims<?, Coordinate.TwoDims>) skinnable.getViewport();

		boolean                inModel    = viewport.scaledModelOverWindow();
		
		BoundingBox.TwoDims    bounds     = viewport.getModelBounds();
		Dimension.TwoDims      window     = viewport.getWindow();

		Point2D			       anchor     = Points.of( viewport.getViewAnchor() ).times(-1);
		Point2D			       zero       = Points.of( 0, 0 );
		Point2D			       dimensions = Points.of( window.getWidth(), window.getHeight() );

		Coordinate.TwoDims     m_TopLeft     = viewport.viewInModel( inModel ? anchor : zero );
		Coordinate.TwoDims     m_BottomRight = viewport.viewInModel( inModel ? anchor.plus(dimensions) : dimensions);

		Coordinate.TwoDims     topleft     = m_TopLeft; //Coordinates.clamp(m_TopLeft, bounds);
		Coordinate.TwoDims     bottomright = m_BottomRight; //Coordinates.clamp(m_BottomRight, bounds);


		BoundingBox.TwoDims    model     = viewport.getModelBounds(true);
		modelCorners . setText(model + "\t\t" + topleft.toString(df) + " / " + bottomright.toString(df));
		
		

		Coordinate.TwoDims     v_TopLeft     = anchor;
		Coordinate.TwoDims     v_BottomRight = anchor.plus(window.getWidth(), window.getHeight());


		Point2D.Editable fmTL = null;

		Coordinate.TwoDims     b_TopLeft     = (Coordinate.TwoDims) viewport.viewInBounds(
												viewport.scaledModelOverWindow() ? 
													anchor
													: 
													Coordinates.of(0, 0));
		Coordinate.TwoDims     b_BottomRight = (Coordinate.TwoDims) viewport.viewInBounds(
												viewport.scaledModelOverWindow() ? 
													anchor.plus(window.getWidth(), window.getHeight()) 
													: 
													Coordinates.of(bounds.getWidth(), bounds.getHeight()));
		BoundingBox.TwoDims    b_Visible     = BoundingBoxes.of(b_TopLeft, b_BottomRight);


		modelBounds        		 . setText(bounds.toString(df));
		modelBoundsVisible		 . setText(b_Visible.toString(df));

//		modelBoundsInView		 . setText(visModel.toString(df) + "\t" + anchor.toString(df) + "/" + bounds.toString(df));
/*
		modelVisibleBounds		 . setText(PlaneViewports.getVisibleModelBounds       ( viewport ).toString(df));
		modelBoundsInView		 . setText(PlaneViewports.getModelBoundsInView        ( viewport ).toString(df));
		modelVisibleBoundsInView . setText(PlaneViewports.getVisibleModelBoundsInView ( viewport ).toString(df));

		viewVisibleBounds		 . setText(PlaneViewports.getVisibleViewBounds        ( viewport ).toString(df));
		windowBoundsInView		 . setText(PlaneViewports.getWindowBoundsInView       ( viewport ).toString(df));
*/
		viewScale				 . setText("x" + sf.format(viewport.getViewScale()));
		viewAnchor				 . setText(Coordinates.scaled(viewport.getViewAnchor(), viewport.getViewScale()).toString(df));

		Coordinate.TwoDims     cInWindow  = _cursor;
		Coordinate.TwoDims     cInView    = viewport.windowInView(_cursor);
		Coordinate.TwoDims     cInModel   = viewport.windowInModel(_cursor);

		Coordinate.TwoDims     mInModel   = cInModel;
		Coordinate.TwoDims     mInView    = viewport.modelInView(cInModel);
		Coordinate.TwoDims     mInWindow  = viewport.modelInWindow(cInModel);

		cursorInWindow	         . setText(cInWindow.toString(df) + " - " + mInWindow);
		cursorInView	         . setText(cInView.toString(df) + " - " + mInView);
		cursorInModel	         . setText(cInModel.toString(df));
		
		

		if(viewport.getWindow() != null) {
			windowBounds	.	setText(viewport.getWindow().toString(df));
		}

//		modelVisibleBounds			.	setText("only available in 2D");
//		modelBoundsInView			.	setText("only available in 2D");
//		modelVisibleBoundsInView	.	setText("only available in 2D");

		viewBounds					.	setText(viewport.getViewBounds().toString(df));
	}
	public void setAllUndef() {
		modelBoundsVisible		 . setText("<undef>");
//		modelBoundsInView		 . setText("<undef>");
//		modelVisibleBoundsInView . setText("<undef>");

		viewBoundsVisible		 . setText("<undef>");
		windowBoundsInView		 . setText("<undef>");

		viewScale				 . setText("<undef>");
		viewAnchor				 . setText("<undef>");

		cursorInView			 . setText("<undef>");

		cursorInModel			 . setText("<undef>");
	}

}
