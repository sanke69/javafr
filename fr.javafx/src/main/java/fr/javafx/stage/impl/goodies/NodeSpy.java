package fr.javafx.stage.impl.goodies;

import java.util.Locale;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class NodeSpy extends Stage {

	private Label	posLabel, dimLabel, scaleLabel;
	private Label	x, y;
	private Label	w, h;
	private Label	sx, sy;

	public NodeSpy(Region _node) {
		super();

		posLabel   = new Label("POSITION");
		dimLabel   = new Label("DIMENSION");
		scaleLabel = new Label("SCALE");

		x  = new Label();
		y  = new Label();
		w  = new Label();
		h  = new Label();
		sx = new Label();
		sy = new Label();

		VBox posFrame   = new VBox(posLabel, x, y);
		VBox dimFrame   = new VBox(dimLabel, w, h);
		VBox scaleFrame = new VBox(scaleLabel, sx, sy);

		HBox mainFrame  = new HBox(posFrame, new Separator(), dimFrame, new Separator(), scaleFrame);

		setScene(new Scene(mainFrame));
		sizeToScene();
		show();

		Locale locale  = new Locale("fr", "FR");
		x.textProperty().bind(Bindings.format(locale, "%,.0f", _node.translateXProperty()));
		y.textProperty().bind(Bindings.format(locale, "%,.0f", _node.translateYProperty()));

		w.textProperty().bind(Bindings.format(locale, "%,.0f", _node.widthProperty()));
		h.textProperty().bind(Bindings.format(locale, "%,.0f", _node.heightProperty()));

		sx.textProperty().bind(Bindings.format(locale, "%,.0f", _node.scaleXProperty()));
		sy.textProperty().bind(Bindings.format(locale, "%,.0f", _node.scaleYProperty()));
	}

	public NodeSpy(Rectangle _r) {
		super();

		posLabel   = new Label("POSITION");
		dimLabel   = new Label("DIMENSION");
		scaleLabel = new Label("SCALE");

		x  = new Label();
		y  = new Label();
		w  = new Label();
		h  = new Label();
		sx = new Label();
		sy = new Label();

		VBox posFrame   = new VBox(posLabel, x, y);
		VBox dimFrame   = new VBox(dimLabel, w, h);
		VBox scaleFrame = new VBox(scaleLabel, sx, sy);

		HBox mainFrame  = new HBox(posFrame, new Separator(), dimFrame, new Separator(), scaleFrame);

		setScene(new Scene(mainFrame));
		sizeToScene();
		show();

		Locale locale  = new Locale("fr", "FR");
		x.textProperty().bind(Bindings.format(locale, "%,.0f", _r.translateXProperty()));
		y.textProperty().bind(Bindings.format(locale, "%,.0f", _r.translateYProperty()));

		w.textProperty().bind(Bindings.format(locale, "%,.0f", _r.widthProperty()));
		h.textProperty().bind(Bindings.format(locale, "%,.0f", _r.heightProperty()));

		sx.textProperty().bind(Bindings.format(locale, "%,.0f", _r.scaleXProperty()));
		sy.textProperty().bind(Bindings.format(locale, "%,.0f", _r.scaleYProperty()));
	}

}
