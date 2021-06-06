package fr.javafx.scene.control.indicator;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import fr.javafx.behavior.Visual;
 
public class FxCompassVisualDefault implements Visual<FxCompass> {
	private final FxCompass control;

	private Group  rootNode;
	
	public FxCompassVisualDefault(FxCompass _control) {
		super();
		control = _control;
		hookEventHandler();
	}

	@Override
	public FxCompass 	getSkinnable() {
		return control;
	}

	@Override
	public Node 		getNode() {
		if(rootNode == null) {
			rootNode = new Group();
			refresh();
		}
		return rootNode;
	}

	public void 		refresh() {
		Canvas c = new Canvas(control.getPrefWidth(), control.getPrefHeight()); 
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        drawBackGround(gc);
        drawHand(gc);
		this.rootNode.getChildren().setAll(c);
	}

	private void 		drawBackGround(GraphicsContext gc) {
		double 	W = getSkinnable().getPrefWidth(),
				H = getSkinnable().getPrefHeight();
		
		int cx = (int) W / 2;
		int cy = (int) H / 2;
		double nmark = 8;

	    	int rx = (int) (0.9 * (((double) W) / 2.0));
	    	int ry = (int) (0.9 * (((double) H) / 2.0));

	        gc.setFill(Color.GREEN);
	    	gc.fillOval(cx - rx, cy - ry, 2 * rx, 2 * ry);

	    	for (int i = 0; i < nmark; i++) {
	    		double CardinalPoint = (double) i;
	    		double angle = 2.0 * Math.PI * (nmark - CardinalPoint) / nmark - Math.PI;
	    		double rx0, ry0, rx1, ry1;
	    		int     x0,  y0,  x1,  y1;

	    		if(i % 2 == 0) {
					rx0 = (0.55 * (((double) W) / 2.0));
					ry0 = (0.55 * (((double) H) / 2.0));
					rx1 = (0.85 * (((double) W) / 2.0));
					ry1 = (0.85 * (((double) H) / 2.0));
	    		} else {
					rx0 = (0.75 * (((double) W) / 2.0));
					ry0 = (0.75 * (((double) H) / 2.0));
					rx1 = (0.85 * (((double) W) / 2.0));
					ry1 = (0.85 * (((double) H) / 2.0));
	    		}

	    		x0 = (int) (rx0 * Math.sin(angle) + (double) cx);
	    		y0 = (int) (ry0 * Math.cos(angle) + (double) cy);
	    		x1 = (int) (rx1 * Math.sin(angle) + (double) cx);
	    		y1 = (int) (ry1 * Math.cos(angle) + (double) cy);

	    		gc.setStroke(Color.BLUE);
	    		gc.strokeLine(x0, y0, x1, y1);
	    	}
	}
	private void 		drawHand(GraphicsContext gc) {
		double 	W = getSkinnable().getPrefWidth(),
				H = getSkinnable().getPrefHeight();
	
		int cx = (int) W / 2;
		int cy = (int) H / 2;

	    gc.setFill(Color.BLACK);

	    double North_angle = 2.0 * Math.PI * (360.0 - getSkinnable().getAngle()) / 360.0 - Math.PI;
	    double West_angle = 2.0 * Math.PI * (360.0 - getSkinnable().getAngle()) / 360.0 - Math.PI / 2.0;
	    double East_angle = 2.0 * Math.PI * (360.0 - getSkinnable().getAngle()) / 360.0 + Math.PI / 2.0;
	    double South_angle = 2.0 * Math.PI * (360.0 - getSkinnable().getAngle()) / 360.0;

	    int BigAxeX = (int) (0.8 * (((double) W) / 2.0));
	    int BigAxeY = (int) (0.8 * (((double) H) / 2.0));
	    int SmallAxeX = (int) (0.2 * (((double) W) / 2.0));
	    int SmallAxeY = (int) (0.2 * (((double) H) / 2.0));

	    int Nx = (int) ((double) BigAxeX * Math.sin(North_angle) + (double) cx);
	    int Ny = (int) ((double) BigAxeY * Math.cos(North_angle) + (double) cy);
	    int Sx = (int) ((double) BigAxeX * Math.sin(South_angle) + (double) cx);
	    int Sy = (int) ((double) BigAxeY * Math.cos(South_angle) + (double) cy);
	    int Ex = (int) ((double) SmallAxeX * Math.sin(East_angle) + (double) cx);
	    int Ey = (int) ((double) SmallAxeY * Math.cos(East_angle) + (double) cy);
	    int Wx = (int) ((double) SmallAxeX * Math.sin(West_angle) + (double) cx);
	    int Wy = (int) ((double) SmallAxeY * Math.cos(West_angle) + (double) cy);

	    gc.setFill(Color.RED);
	    gc.fillPolygon(new double[] { Nx, Ex, Wx }, new double[] { Ny, Ey, Wy }, 3);
	    gc.setFill(Color.BLACK);
	    gc.fillPolygon(new double[] { Sx, Ex, Wx }, new double[] { Sy, Ey, Wy }, 3);

	}

	private void 		hookEventHandler() {
		control.addEventHandler(FxCompass.EVENT_TYPE_CHANGE_VALUE, (e) -> refresh());
		control.addEventHandler(FxCompass.EVENT_TYPE_CHANGE_NORTH, (e) -> refresh());
	}

}
