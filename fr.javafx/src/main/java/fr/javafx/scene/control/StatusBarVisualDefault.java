package fr.javafx.scene.control;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Skin;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class StatusBarVisualDefault implements Skin<StatusBar> {
	private final StatusBar control;

	private AnchorPane 	pane;

	private HBox 		leftBox;
	private HBox 		centerBox;
	private HBox 		rightBox;

	private Label 		label;
	private ProgressBar progressBar;

	private final ChangeListener<? super Number> updateLeftAnchor = (_obs, _old, _new) -> {
		double leftWidth = leftBox.prefWidth(0);
		AnchorPane.setLeftAnchor(label, leftWidth);
	};
	private final ChangeListener<? super Number> updateCenterAnchor = (_obs, _old, _new) -> {
		double nodeWidth = centerBox.prefWidth(0);
		double paneWidth = pane.getWidth();
		double padding = (paneWidth - nodeWidth) / 2.0;

		AnchorPane.setLeftAnchor(centerBox, padding);
		AnchorPane.setRightAnchor(centerBox, padding);
	};
	private final ChangeListener<? super Number> updateRightAnchor = (_obs, _old, _new) -> {
		double rightWidth = rightBox.prefWidth(0);
		AnchorPane.setRightAnchor(progressBar, rightWidth);
	};

	InvalidationListener listener = (_obs) -> {
		double nodeWidth = centerBox.prefWidth(0);
		double paneWidth = pane.getWidth();
		double padding = (paneWidth - nodeWidth) / 2.0;

		AnchorPane.setLeftAnchor(centerBox, padding);
		AnchorPane.setRightAnchor(centerBox, padding);
	};

	public StatusBarVisualDefault(StatusBar statusBar) {
		super();
		control = statusBar;

		build();
		populate();
		addListeners();
		initLayout();
	}

	@Override
	public StatusBar getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		forceLayout();
		return pane;
	}

	@Override
	public void dispose() {
		;
	}

	private void build() {
		pane 		= new AnchorPane();

		leftBox 	= new HBox();
		centerBox 	= new HBox();
		rightBox 	= new HBox();

		progressBar = new ProgressBar();
		label 		= new Label();

		pane.getChildren().addAll(leftBox, label, centerBox, progressBar, rightBox);

		leftBox.getStyleClass().add("left-items");
		centerBox.getStyleClass().add("middle-items");
		rightBox.getStyleClass().add("right-items");

		progressBar.progressProperty().bind(control.progressProperty());
		progressBar.visibleProperty().bind(Bindings.notEqual(0, control.progressProperty()));

//		label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		label.textProperty().bind(control.textProperty());
		label.graphicProperty().bind(control.graphicProperty());
		label.getStyleClass().add("status-label");
	}
	private void populate() {
		leftBox		.getChildren().setAll(getSkinnable().getLeftItems());
		centerBox	.getChildren().setAll(getSkinnable().getCenterItems());
		rightBox	.getChildren().setAll(getSkinnable().getRightItems());
	}
	private void addListeners() {
		control		.getLeftItems()		.addListener((Observable evt) -> leftBox.getChildren().setAll(getSkinnable().getLeftItems()));
		control		.getCenterItems()	.addListener((Observable evt) -> centerBox.getChildren().setAll(getSkinnable().getCenterItems()));
		control		.getRightItems()	.addListener((Observable evt) -> rightBox.getChildren().setAll(getSkinnable().getRightItems()));

		pane		.widthProperty()	.addListener(updateCenterAnchor);
		leftBox		.widthProperty()	.addListener(updateLeftAnchor);
		centerBox	.widthProperty()	.addListener(updateCenterAnchor);
		rightBox	.widthProperty()	.addListener(updateRightAnchor);
	}
	private void initLayout() {
		AnchorPane.setLeftAnchor	(leftBox, 0.0);

		double padding = 0;// (pane.getWidth() - centerBox.prefWidth(0)) / 2.0;
		AnchorPane.setLeftAnchor	(centerBox, padding);
		AnchorPane.setRightAnchor	(centerBox, padding);
		
		AnchorPane.setRightAnchor	(rightBox, 0.0);
	}

	private void forceLayout() {
		double padding = 0;//(pane.getWidth() - centerBox.prefWidth(0)) / 2.0;
		AnchorPane.setLeftAnchor	(centerBox, padding);
		AnchorPane.setRightAnchor	(centerBox, padding);
	}

}