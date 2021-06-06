package fr.javafx.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Collection;
import java.util.stream.Collectors;

import fr.javafx.io.screen.Screens;

public class FxStageUtils {
	static final KeyCombination FScombination = KeyCodeCombination.keyCombination("Alt+Enter");

	public static final Insets cinamonDefaultInset = new Insets(27, 0, 0, 0);

	public static Insets insets() {
		return cinamonDefaultInset;
	}

	public static final void autoFitStageTo(final Stage _stage, final Region _region) {
		final double stageBorderWidth  = insets().getLeft() + insets().getRight();
		final double stageBorderHeight = insets().getTop() + insets().getBottom();
		
		_stage.setResizable(false);
		_stage.setWidth(_region.getWidth() + stageBorderWidth);
		_stage.setHeight(_region.getHeight() + stageBorderHeight);
		_region.boundsInParentProperty().addListener((_bounds, _old, _new) -> {
			_stage.setWidth(_new.getWidth() + stageBorderWidth);
			_stage.setHeight(_new.getHeight() + stageBorderHeight);
			Screens.centerOnScreen(_stage, Screens.getActiveScreen());
		});
	}
	public static final void autoFitStage(final Stage _stage) {
		final double stageBorderWidth  = insets().getLeft() + insets().getRight();
		final double stageBorderHeight = insets().getTop() + insets().getBottom();
		
		if(_stage.getScene() == null || _stage.getScene().getRoot() == null)
			throw new IllegalArgumentException();

		Parent parent = _stage.getScene().getRoot();
		Bounds bounds = _stage.getScene().getRoot().getBoundsInParent();

		_stage.setResizable(false);
		_stage.setWidth(bounds.getWidth() + stageBorderWidth);
		_stage.setHeight(bounds.getHeight() + stageBorderHeight);
		parent.boundsInParentProperty().addListener((_bounds, _old, _new) -> {
			System.out.println("AUTOFIT " + _new.getWidth() + "x" + _new.getHeight());
			_stage.setWidth(_new.getWidth() + stageBorderWidth);
			_stage.setHeight(_new.getHeight() + stageBorderHeight);
		});
	}

	public static final void setMultiScreenMaximized(Stage _stage) {
		// Get current screen of the stage      
		ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(_stage.getX(), _stage.getY(), _stage.getWidth(), _stage.getHeight()));

		// Change stage properties
		Rectangle2D bounds = screens.get(0).getVisualBounds();
		_stage.setX(bounds.getMinX());
		_stage.setY(bounds.getMinY());
		_stage.setWidth(bounds.getWidth());
		_stage.setHeight(bounds.getHeight());
	}

	public static void enableFullScreen(Control _target, Stage _stage) {
		_target.setOnMouseClicked((mouseEvent) -> {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				if(mouseEvent.getClickCount() == 2) {
					if(_stage.isFullScreen()) {
						_stage.setFullScreen(false);
						_target.prefHeightProperty().unbind();
					} else {
						_stage.setFullScreen(true);
						_target.prefWidthProperty().bind(_stage.widthProperty());
					}
				}
			}
		});
	}
	public static void enableFullScreen(Stage _stage) {
		BooleanProperty pDown = new SimpleBooleanProperty(false);
		_stage.getScene().setOnKeyPressed((e) -> {
			if (FScombination.match(e))
				pDown.set(true);
			if (pDown.get() && FScombination.match(e)) {
				pDown.set(false);
				_stage.setFullScreen(true);
			}
		});
		_stage.getScene().setOnKeyReleased((e) -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
				_stage.setX((primScreenBounds.getWidth() - _stage.getWidth()) / 2);
				_stage.setY((primScreenBounds.getHeight() - _stage.getHeight()) / 2);
			}
		});
	}

	public static Collection<Stage> getAllStages() {
		return getAllStages_JDK11();
	}
/*
	private static Collection<Stage> getAllStages_JDK8() {
		Set<Stage> stages = new HashSet<Stage>();

		Iterator<Window> iw = Window.impl_getWindows();
		Window w;
		while((w = iw.next()) != null) {
			Stage s;

			if(w instanceof Stage)
				stages.add((Stage) w);
			else
				break;
		}
		
		return stages;
	}
*/
	private static Collection<Stage> getAllStages_JDK11() {
		return Window.getWindows().stream().filter(w -> w instanceof Stage).map(w -> (Stage) w).collect(Collectors.toSet());
	}

}
