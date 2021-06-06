package fr.javafx.windows;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;

public class WindowIcon extends Control {
	public static final WindowIcon closeIcon		(final WindowControl w) {
		WindowIcon closeIcon = new WindowIcon();
		closeIcon.getStyleClass().setAll(WindowControl.DEFAULT_STYLE_CLASS_ICON_CLOSE);
		closeIcon.setOnAction(ae -> {
			if(!w.isStaged()) w.close(); else w.getStage().close();
		});

		return closeIcon;
	}
	public static final WindowIcon maximizeIcon		(final WindowControl w) {
		WindowIcon maximizeIcon = new WindowIcon();
		maximizeIcon.getStyleClass().setAll(WindowControl.DEFAULT_STYLE_CLASS_ICON_MAXIMIZE);
		maximizeIcon.setOnAction(ae -> {
			if(w.isStaged()) w.getStage().setFullScreen(!w.getStage().isFullScreen());
		});

		return maximizeIcon;
	}
	public static final WindowIcon minimizeIcon		(final WindowControl w) {
		WindowIcon minimizeIcon = new WindowIcon();
		minimizeIcon.getStyleClass().setAll(WindowControl.DEFAULT_STYLE_CLASS_ICON_MINIMIZE);
		minimizeIcon.setOnAction(ae -> {
			w.setMinimized(!w.isMinimized());
		});

		return minimizeIcon;
	}
	public static final WindowIcon iconizeIcon		(final WindowControl w) {
		WindowIcon iconizeIcon = new WindowIcon();
		iconizeIcon.getStyleClass().setAll(WindowControl.DEFAULT_STYLE_CLASS_ICON_ICONIZE);
		iconizeIcon.setOnAction(ae -> {
			if(!w.isStaged()) w.setMinimized(!w.isMinimized()); else w.getStage().setIconified(true);
		});

		return iconizeIcon;
	}

	private final ObjectProperty<EventHandler<ActionEvent>> onActionProperty = new SimpleObjectProperty<>();

	public WindowIcon() {
		super();
		getStyleClass().setAll(WindowControl.DEFAULT_STYLE_CLASS_ICON);
	}

	public ObjectProperty<EventHandler<ActionEvent>> 	onActionProperty() {
		return onActionProperty;
	}

	public void 										setOnAction(EventHandler<ActionEvent> handler) {
		onActionProperty.set(handler);
	}
	public EventHandler<ActionEvent> 					getOnAction() {
		return onActionProperty.get();
	}

}
