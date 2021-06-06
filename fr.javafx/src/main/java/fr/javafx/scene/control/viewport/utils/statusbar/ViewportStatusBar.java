package fr.javafx.scene.control.viewport.utils.statusbar;

import fr.javafx.scene.control.StatusBar;
import fr.javafx.scene.control.viewport.ViewportControl;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ViewportStatusBar extends StatusBar {
	ViewportControl control;

	Label              leftInfo;
	Label              centerInfo;
	Label              rightInfo;

	public ViewportStatusBar(ViewportControl _control) {
		super();
		control = _control;

		build();
		addListeners();
	}

	private void build() {
		if(leftInfo == null)
			leftInfo = new Label("LEFT");
		if(centerInfo == null)
			centerInfo = new Label("CENTER");
		if(rightInfo == null)
			rightInfo = new Label("RIGHT");

		HBox separator = new HBox();
		separator.setPrefWidth(250);

		getLeftItems().clear();
		getLeftItems().add(leftInfo);
//		getLeftItems().add(separator);

		getCenterItems().clear();
		getCenterItems().add(centerInfo);
//		getCenterItems().add(separator);

		getRightItems().clear();
		getRightItems().add(rightInfo);
	}
	
	public void addListeners() {
		control.addEventFilter(MouseEvent.MOUSE_MOVED, (me) -> {
			leftInfo.setText((int) me.getX() + "x" + (int) me.getY());
//			centerInfo.setText(control.getViewport().toString());
//			rightInfo.setText(ViewportModels.getCursorInfo(control, Points.of(me.getX(), me.getY())));
		});

//		viewport.viewportProperty().addListener((_obs, _prop, _old, _new) -> centerInfo.setText(viewport.getViewport().toString()));
	}
}
