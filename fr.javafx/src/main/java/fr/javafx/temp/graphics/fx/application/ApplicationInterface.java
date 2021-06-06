package fr.javafx.temp.graphics.fx.application;

import fr.javafx.temp.graphics.fx.api.ribbon.RibbonBar2;
import fr.javafx.temp.windows.skins.FloatingWindowSkin;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public interface ApplicationInterface {

	public void resetStage();
	public void reloadModules();

	public Stage     getStage();
	public Scene     getScene();
	public RibbonBar2 getRibbonBar();

	public Region    getMainCanvas();
	public Pane      getDesktopPane();

	public void      switchCanvas(Region _pane);
	public void      switchToDesktopCanvas();

	public void      addWindow(FloatingWindowSkin _window);
	public void      removeWindow(FloatingWindowSkin _window);

}
