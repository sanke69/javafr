package fr.javafx.scene.control.indicator;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import fr.javafx.behavior.Visual;

public class FxSpeedOMeterVisualDefault implements Visual<FxSpeedOMeter> {
	private final static double ZERO_ANGLE  = (- 0.2) * Math.PI;
	private final static double RANGE_ANGLE = 1.5 * Math.PI;
	private final static double MIN_SPEED = 0;
	private final static double MAX_SPEED = 200;

	private final FxSpeedOMeter control;

	private Group  rootNode;
	
	public FxSpeedOMeterVisualDefault(FxSpeedOMeter _control) {
		super();
		control = _control;
		hookEventHandler();
	}

	@Override
	public FxSpeedOMeter getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		if(rootNode == null) {
			rootNode = new Group();
			redraw();
		}
		return rootNode;
	}

	protected void redraw() {
		Canvas c = new Canvas(control.getPrefWidth(), control.getPrefHeight()); 
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        drawBG(gc);
        drawHand(gc);
		this.rootNode.getChildren().setAll(c);
	}

	private void drawBG(GraphicsContext gc) {
		double 	W = getSkinnable().getPrefWidth(),
				H = getSkinnable().getPrefHeight();

		double cx = W / 2;
		double cy = H / 2;
		double nmark = (MAX_SPEED - MIN_SPEED) / 10;

    	gc.setFill(Color.BLACK);
    	gc.fillOval(0, 0, W, H);
    	gc.setFill(Color.WHITE);

    	int rx = (int) (0.9 * (((double) W) / 2.0));
    	int ry = (int) (0.9 * (((double) H) / 2.0));
    	gc.fillOval(cx - rx, cy - ry, 2 * rx, 2 * ry);

    	gc.setFill(Color.GRAY);
    	for (int i = 0; i < nmark; i++) {
    		double SpeedMarker = (double) i;
    		double angle = ZERO_ANGLE - RANGE_ANGLE * ((double) (SpeedMarker / nmark));
    		double rx0, ry0, rx1, ry1;
    		int     x0,  y0,  x1,  y1;

    		if(i % 5 == 0) {
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

    		gc.setLineWidth(2.0f);
    		gc.strokeLine(x0, y0, x1, y1);
    	}
	}
	
	private void drawHand(GraphicsContext gc) {
		double 	W = getSkinnable().getPrefWidth(),
				H = getSkinnable().getPrefHeight();

		double cx = W / 2;
		double cy = H / 2;

	    gc.setFill(Color.BLACK);

	    double Needle_angle = getRadFromSpeed(control.getValue());

	    int BigAxeX = (int) (0.8 * (((double) W) / 2.0));
	    int BigAxeY = (int) (0.8 * (((double) H) / 2.0));
	    int SmallAxeX = (int) (0.05 * (((double) W) / 2.0));
	    int SmallAxeY = (int) (0.05 * (((double) H) / 2.0));

	    int Nx = (int) ((double) BigAxeX * Math.sin(Needle_angle) + (double) cx);
	    int Ny = (int) ((double) BigAxeY * Math.cos(Needle_angle) + (double) cy);
	    int Sx = (int) ((double) SmallAxeX * Math.sin(Needle_angle - Math.PI) + (double) cx);
	    int Sy = (int) ((double) SmallAxeX * Math.cos(Needle_angle - Math.PI) + (double) cy);
	    int Ex = (int) ((double) SmallAxeX * Math.sin(Needle_angle - Math.PI/2.0) + (double) cx);
	    int Ey = (int) ((double) SmallAxeY * Math.cos(Needle_angle - Math.PI/2.0) + (double) cy);
	    int Wx = (int) ((double) SmallAxeX * Math.sin(Needle_angle + Math.PI/2.0) + (double) cx);
	    int Wy = (int) ((double) SmallAxeY * Math.cos(Needle_angle + Math.PI/2.0) + (double) cy);

	    gc.setFill(Color.RED);
	    gc.fillPolygon(new double[] { Nx, Ex, Sx, Wx }, new double[] { Ny, Ey, Sy, Wy }, 4);

	}

	private double getRadFromSpeed(double _speed) {
		if(_speed < MIN_SPEED)
			return ZERO_ANGLE;
		if(_speed > MAX_SPEED)
			return ZERO_ANGLE - RANGE_ANGLE;

		double RAD = ZERO_ANGLE - RANGE_ANGLE * _speed / (MAX_SPEED - MIN_SPEED);
		return RAD;
	}

	private void hookEventHandler() {
		control.addEventHandler(FxSpeedOMeter.EVENT_TYPE_CHANGE_VALUE, (e) -> redraw());
	}

}
