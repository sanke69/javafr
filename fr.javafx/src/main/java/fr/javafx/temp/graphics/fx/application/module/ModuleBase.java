package fr.javafx.temp.graphics.fx.application.module;

import java.util.concurrent.CountDownLatch;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import fr.javafx.temp.graphics.fx.application.ApplicationInterface;

public abstract class ModuleBase extends VBox implements ModuleInterface {
	public final CountDownLatch latch = new CountDownLatch(1);

	private ModuleRibbonPaneBase[] ribbonPanes;
	private String tabName;
	
	private boolean tabInBar = false;
	private Tab     tab = null;
	private Pane    content = null;
	
	protected ModuleBase(String _tabName, ModuleRibbonPaneBase... _ribbonPanels) {
		super();
		tabName = _tabName;
		ribbonPanes = _ribbonPanels;

		if(tab == null) {
			tab = new Tab(tabName);

			tab.setClosable(false);

	        content = new HBox();

	        content.setId("container");
	        content.setPrefHeight(90);
	        ((HBox) content).setSpacing(5);

	        if(ribbonPanes != null)
		        for(Pane p : ribbonPanes)
		        	content.getChildren().add(p);

	        tab.setContent(content);
		}
	}
	
	// Internal use only
	public void configureRibbonPanes(ApplicationInterface _application) {
		if(ribbonPanes != null)
			for(ModuleRibbonPaneBase ribbon : ribbonPanes)
				if(ribbon.getMainApplication() == null) 
					ribbon.setMainStage(_application);
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public boolean isTabAlreadyAdded() {
		return tabInBar;
	}

	@Override
	public String getDisplayedName() {
		return tabName;
	}

	@Override
	public Tab getTab(boolean _addToBar) {
		tabInBar = true;
		return getTab();
	}
	@Override
	public Tab getTab() {
        return tab;
	}

	@Override
	public ModuleRibbonPaneBase[] getRibbonPanels() {
		return ribbonPanes;
	}
	
	@Override
	public ObservableList<Node> getChildren() {
		return content.getChildren();
	}

}
