package fr.javafx.temp.graphics.fx.api.charts.plots;

import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class CartesianChart extends StackPane {
	Axes axes = new Axes(400, 300, -8, 8, 1, -6, 6, 1);

	public CartesianChart(double _xMin, double _xMax, double _yMin, double _yMax) {
		super();
		setPadding(new Insets(20));
		setStyle("-fx-background-color: rgb(35, 39, 50);");

		axes = new Axes(400, 300, _xMin, _xMax, 1, _yMin, _yMax, 1);
	}

	public void setAxis(double _xMin, double _xMax, double _yMin, double _yMax) {
		getChildren().remove(axes);
		axes = new Axes(400, 300, _xMin, _xMax, 1, _yMin, _yMax, 1);
		getChildren().add(axes);
	}
	
	public void addPlot(Double[] _x, Double[] _y) {
		Plot plot = new Plot(_x, _y);
		getChildren().add(plot);
	}
	public void addPlot(Function<Double, Double> _fn) {
		Plot plot = new Plot(_fn, -8, 8, 0.1, axes);
		getChildren().add(plot);
	}

	class Axes extends Pane {
		private NumberAxis xAxis;
		private NumberAxis yAxis;

		public Axes(int width, int height, double xLow, double xHi, double xTickUnit, double yLow, double yHi, double yTickUnit) {
			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			setPrefSize(width, height);
			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

			xAxis = new NumberAxis(xLow, xHi, xTickUnit);
			xAxis.setSide(Side.BOTTOM);
			xAxis.setMinorTickVisible(false);
			xAxis.setPrefWidth(width);
			xAxis.setLayoutY(height / 2);

			yAxis = new NumberAxis(yLow, yHi, yTickUnit);
			yAxis.setSide(Side.LEFT);
			yAxis.setMinorTickVisible(false);
			yAxis.setPrefHeight(height);
			yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));

			getChildren().setAll(xAxis, yAxis);
		}

		public NumberAxis getXAxis() {
			return xAxis;
		}

		public NumberAxis getYAxis() {
			return yAxis;
		}
	}

	class Plot extends Pane {
		public Plot(Function<Double, Double> f, double xMin, double xMax, double xInc, Axes axes) {
			Path path = new Path();
			path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
			path.setStrokeWidth(2);
	
			path.setClip(new Rectangle(0, 0, axes.getPrefWidth(), axes.getPrefHeight()));
	
			double x = xMin;
			double y = f.apply(x);
	
			path.getElements().add(new MoveTo(mapX(x, axes), mapY(y, axes)));
	
			x += xInc;
			while (x < xMax) {
				y = f.apply(x);
	
				path.getElements().add(new LineTo(mapX(x, axes), mapY(y, axes)));
	
				x += xInc;
			}
	
			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
	
			getChildren().add(path);
		}
		public Plot(Double[] _x, Double[] _y) {
			assert(_y.length > _x.length);

			Path path = new Path();
			path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
			path.setStrokeWidth(2);

			path.setClip(new Rectangle(0, 0, axes.getPrefWidth(), axes.getPrefHeight()));

			path.getElements().add(new MoveTo(mapX(_x[0], axes), mapY(_y[0], axes)));
			for(int i = 1; i < _x.length; ++i)
				path.getElements().add(new LineTo(mapX(_x[i], axes), mapY(_y[i], axes)));

			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

			getChildren().add(path);
		}

		private double mapX(double x, Axes axes) {
			double tx = axes.getPrefWidth() / 2;
			double sx = axes.getPrefWidth() / (axes.getXAxis().getUpperBound() - axes.getXAxis().getLowerBound());

			return x * sx + tx;
		}
		private double mapY(double y, Axes axes) {
			double ty = axes.getPrefHeight() / 2;
			double sy = axes.getPrefHeight() / (axes.getYAxis().getUpperBound() - axes.getYAxis().getLowerBound());

			return -y * sy + ty;
		}
	}
}
