package fr.javafx.temp.graphics.fx.api.charts.skins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

import fr.javafx.temp.graphics.fx.api.charts.ChartControl;
import fr.javafx.temp.graphics.fx.api.charts.ChartControl.Curve;

public class DefaultPlotterDotsSkin implements Skin<ChartControl> {
	private final ChartControl control;

	private StackPane			root;
	private Axes				axes;
	private Map<String, Plot>	plots;

	public DefaultPlotterDotsSkin(ChartControl _control) {
		super();
		control = _control;
		
		plots = new HashMap<String, Plot>();

		control.curvesProperty().addListener((_obs, _old, _new) -> {
			rebuildCurves();
		});

	}

	@Override
	public ChartControl getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		if(root == null) {
			root = new StackPane();
			root.setPadding(new Insets(20));
			root.setStyle("-fx-background-color: rgb(35, 39, 50);");
			buildAxes();
		}
		return root;
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	private void buildAxes() {
		root.getChildren().clear();

		root.getChildren().add(axes = new Axes(400, 300,
				getSkinnable().xMin(), getSkinnable().xMax(), getSkinnable().xMajorTicks(),
				getSkinnable().yMin(), getSkinnable().yMax(), getSkinnable().yMajorTicks()));

		for(Map.Entry<String, Curve> e : getSkinnable().curves().entrySet()) {
			plots.put(e.getKey(), new Plot(e.getValue(), axes));
			root.getChildren().add(plots.get(e.getKey()));
		}
	}

	private void rebuildCurves() {
		List<String> existingCurve = new ArrayList<String>();

		for(Map.Entry<String, Curve> e : getSkinnable().curves().entrySet()) {
			existingCurve.add(e.getKey());
			if(!plots.containsKey(e.getKey())) {
				plots.put(e.getKey(), new Plot(e.getValue(), axes));
				root.getChildren().add(plots.get(e.getKey()));
			}
		}
		
		for(Map.Entry<String, Plot> e : plots.entrySet()) {
			if(!existingCurve.contains(e.getKey())) {
				root.getChildren().remove(e.getValue());
				plots.remove(e.getKey());
			}
		}
		
	}

	class Axes extends Pane {
		private NumberAxis	xAxis;
		private NumberAxis	yAxis;

		public Axes(int width, int height, double xLow, double xHi, double xTickUnit, double yLow, double yHi, double yTickUnit) {
			setPrefSize(width, height);
			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
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

		public Plot(Curve _c, Axes _axes) {
			this(_c.getX(), _c.getY(), _axes);
		}
		public Plot(Double[] _x, Double[] _y, Axes _axes) {
			assert (_y.length > _x.length);

			for(int i = 0; i < _x.length; ++i) {
				Circle path = new Circle(mapX(_x[i], _axes), mapY(_y[i], _axes), 0.5);
				path.setClip(new Rectangle(0, 0, _axes.getPrefWidth(), _axes.getPrefHeight()));
				path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
				path.setStrokeWidth(2);
				getChildren().add(path);
			}

			setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
			setPrefSize(_axes.getPrefWidth(), _axes.getPrefHeight());
			setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
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
