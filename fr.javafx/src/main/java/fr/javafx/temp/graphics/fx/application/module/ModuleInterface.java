package fr.javafx.temp.graphics.fx.application.module;

import javafx.scene.layout.Pane;

import fr.javafx.temp.graphics.fx.api.ribbon.RibbonInterface;
import fr.javafx.temp.graphics.fx.application.ApplicationInterface;

public interface ModuleInterface extends RibbonInterface {

	// Internal use only
	public void configureRibbonPanes(ApplicationInterface _application);
	// ---

	public boolean         isAvailable();
	public boolean         hasTab();

	public String          getDisplayedName();

	public Pane[]          getRibbonPanels();

}
