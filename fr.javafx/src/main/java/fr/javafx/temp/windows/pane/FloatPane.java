package fr.javafx.temp.windows.pane;

import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.MutableWindowPane;
import fr.javafx.temp.windows.components.WindowProperties.Position;
import fr.javafx.temp.windows.skins.FloatingWindowSkin;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class FloatPane extends MutableWindowPane {

	public void addWindow(FloatingWindowSkin _window) {
		getChildren().add(_window);
	}
	public void removeWindow(FloatingWindowSkin _window) {
		getChildren().remove(_window);
	}

	public void attach(MutableWindow _window) {
		FloatingWindowSkin window = (FloatingWindowSkin) _window.getSkin();
		
		StackPane.setAlignment(window, Pos.TOP_LEFT);
		
		getChildren().add(window);
	}
	public void detach(MutableWindow _window) {
		getChildren().remove(_window);
	}

	public void dock(DockPane _dockPane, Position _dockPos) {
		_dockPane.dock(this,  _dockPos);
	}

}
