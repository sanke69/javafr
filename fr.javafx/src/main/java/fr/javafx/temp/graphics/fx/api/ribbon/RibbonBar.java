package fr.javafx.temp.graphics.fx.api.ribbon;

import java.io.InputStream;
import java.net.URL;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RibbonBar extends VBox {

	public RibbonBar() {
		super();

//		URL cssURL  = FileHelper.getURLForResource(RibbonBar.class, "RibbonBar.css");
		URL cssURL  = RibbonBar.class.getResource("RibbonBar.css");
		if(cssURL != null) {
			String cssPath = cssURL.toExternalForm();
			getStylesheets().add(cssPath);
		}

		setAlignment(Pos.CENTER);
		getStyleClass().add("toolbarContainer");
		
		setPrefHeight(100.0);
	}

/*
	public final Pane getDesktop() {
		return mainApplication.getDesktopPane();
	}
	public final void addToDesktop(InternalWindow _window) {
		mainApplication.addWindow(_window);
	}
*/
	protected static GridPane buildButtonGroup(String _label, String _tooltip, int _nbEntry) {
		GridPane layout = new GridPane();
		layout.setGridLinesVisible(false);
		layout.setHgap(5);

		Label label = new Label(_label);
		label.getStyleClass().add("ribbonLabel");
		label.setTooltip(new Tooltip(_tooltip));

		VBox vbox = new VBox();
		VBox.setVgrow(label, Priority.ALWAYS);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setStyle("-fx-padding: 5 0 0 0");
		vbox.getChildren().add(label);

		layout.add(vbox, 0, 2, _nbEntry, 1);

		return layout;
	}

	protected static Button buildButton(String _label, String _tooltip, InputStream _img_SVG, double _width, double _height) {
		Image image = new Image(_img_SVG);
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(_width);
		iv.setFitHeight(_height);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);

		Button btn = new Button(_label);
		btn.setContentDisplay(ContentDisplay.TOP);
		btn.setGraphic(iv);
		btn.getStyleClass().add("ribbonToggleButton");
		if(_tooltip.length() > 0)
			btn.setTooltip(new Tooltip(_tooltip));

		return btn;
	}
	protected static Button buildButton(String _label, String _tooltip, InputStream _img_SVG, double _width) {
		return buildButton(_label, _tooltip, _img_SVG, _width, _width);
	}
	protected static Button buildButton(String _label, String _tooltip, InputStream _img_SVG) {
		return buildButton(_label, _tooltip, _img_SVG, 48, 48);
	}

	protected static ToggleButton buildFirstToggleButton(String _label, String _tooltip, InputStream _img_SVG, double _width, double _height) {
		Image image = new Image(_img_SVG);
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(_width);
		iv.setFitHeight(_height);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);

		ToggleButton btn = new ToggleButton(_label);
		btn.setContentDisplay(ContentDisplay.TOP);
		btn.setGraphic(iv);
		btn.getStyleClass().add("ribbonToggleButton");
		if(_tooltip.length() > 0)
			btn.setTooltip(new Tooltip(_tooltip));

        btn.getStyleClass().add("leftToggleButton");

        return btn;
	}
	protected static ToggleButton buildMiddleToggleButton(String _label, String _tooltip, InputStream _img_SVG, double _width, double _height) {
		Image image = new Image(_img_SVG);
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(_width);
		iv.setFitHeight(_height);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);

		ToggleButton btn = new ToggleButton(_label);
		btn.setContentDisplay(ContentDisplay.TOP);
		btn.setGraphic(iv);
		btn.getStyleClass().add("ribbonToggleButton");
		if(_tooltip.length() > 0)
			btn.setTooltip(new Tooltip(_tooltip));

		btn.getStyleClass().add("middleToggleButton");

        return btn;
	}
	protected static ToggleButton buildLastToggleButton(String _label, String _tooltip, InputStream _img_SVG, double _width, double _height) {
		Image image = new Image(_img_SVG);
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(_width);
		iv.setFitHeight(_height);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);

		ToggleButton btn = new ToggleButton(_label);
		btn.setContentDisplay(ContentDisplay.TOP);
		btn.setGraphic(iv);
		btn.getStyleClass().add("ribbonToggleButton");
		if(_tooltip.length() > 0)
			btn.setTooltip(new Tooltip(_tooltip));

		btn.getStyleClass().add("rightToggleButton");

        return btn;
	}

}
