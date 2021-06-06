package fr.javafx.temp.graphics.fx.application;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import fr.java.utils.LocalFiles;
import fr.javafx.temp.graphics.fx.api.ribbon.RibbonBar2;
import fr.javafx.temp.graphics.fx.application.module.ModuleInterface;
import fr.javafx.temp.graphics.fx.application.module.ModuleManager;
import fr.javafx.temp.graphics.fx.application.module.ModuleRibbonPaneBase;
import fr.javafx.temp.windows.skins.FloatingWindowSkin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

public class ApplicationBase extends Application implements ApplicationInterface {
	static String windowTitle      = "SP-Web Heavy Client \u03B1";
	static double windowPrefWidth  = 1280, 
				  windowPrefHeight = 768;
	
	static final KeyCombination FScombination = KeyCodeCombination.keyCombination("Alt+Enter");

	private Stage     stage;
	private Scene     scene;

	private RibbonBar2 ribbon;
	private Pane      canvas, desktop;
	private Region    currentPane;

	public ApplicationBase() {
		this(windowTitle, windowPrefWidth, windowPrefHeight);
	}
	public ApplicationBase(String _name) {
		this(windowTitle = _name, windowPrefWidth, windowPrefHeight);
	}
	public ApplicationBase(String _name, double _w, double _h) {
		super();
		setInstance(this);
		ModuleManager.registerApplication(this);

		windowTitle      = _name;
		windowPrefWidth  = _w;
		windowPrefHeight = _h;

		createMainScene();
		createMainStage();
	}
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		waitForInstance();
		Platform.setImplicitExit(true);
	}

	public void createMainStage() {
		stage = new Stage();
//		stage.initStyle		(StageStyle.DECORATED);
		stage.setTitle		(windowTitle);
//    	stage.sizeToScene	();

    	stage.setMinWidth	(windowPrefWidth);
    	stage.setMinHeight	(windowPrefHeight);
    	stage.setWidth		(windowPrefWidth);
    	stage.setHeight		(windowPrefHeight);
    	stage.setMaxWidth	(Double.MAX_VALUE);
    	stage.setMaxHeight	(Double.MAX_VALUE);

		stage.setScene(scene);
		stage.show();

		BooleanProperty pDown = new SimpleBooleanProperty(false);
		scene.setOnKeyPressed((e) -> {
			if(FScombination.match(e))
				pDown.set(true);
			if(pDown.get() && FScombination.match(e)) {
				pDown.set(false);
				stage.setFullScreen(true);
			}
		});
		scene.setOnKeyReleased((e) -> {
            if(e.getCode() == KeyCode.ESCAPE) {
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            }
		});

		canvas.prefWidthProperty()  . bind(stage.widthProperty());
		canvas.prefHeightProperty() . bind(stage.heightProperty().add(- ribbon.heightProperty().doubleValue()));
	}

	public void createMainScene() {
		ribbon  = new RibbonBar2();

		desktop = new Pane();
//		desktopPane.setStyle("-fx-background-color: green;");

		canvas  = new Pane();
//		canvas.setStyle("-fx-background-color: red;");

		canvas.setPrefSize(windowPrefWidth, windowPrefHeight);
		canvas.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		desktop.minWidthProperty()   . bind(canvas.widthProperty());
		desktop.minHeightProperty()  . bind(canvas.heightProperty());
		desktop.maxWidthProperty()   . bind(canvas.widthProperty());
		desktop.maxHeightProperty()  . bind(canvas.heightProperty());
		desktop.prefWidthProperty()  . bind(canvas.widthProperty());
		desktop.prefHeightProperty() . bind(canvas.heightProperty());

		canvas.getChildren().add(currentPane = desktop);

		reloadModules();

		BorderPane layout = new BorderPane();
		layout.setId	("eCompanion");
		layout.setTop	(ribbon);
		layout.setCenter(canvas);

		String cssPath  = LocalFiles.getURLForResource(ApplicationBase.class, "ApplicationBase.css").toExternalForm();
		String css2Path = LocalFiles.getURLForResource(ModuleRibbonPaneBase.class, "ModuleRibbonPaneBase.css").toExternalForm();

		scene = new Scene(layout);
		scene.getStylesheets().addAll(cssPath, css2Path);
	}

	@Override
    public void resetStage() {
    	instance().stage.hide();
    	instance().stage.setTitle		(windowTitle);
//		instance().stage.initStyle		(StageStyle.DECORATED);
    	instance().stage.setScene		(instance().scene);
    	instance().stage.sizeToScene	();
    	instance().stage.setMinWidth	(windowPrefWidth);
    	instance().stage.setMinHeight	(windowPrefHeight);
    	instance().stage.setWidth		(windowPrefWidth);
    	instance().stage.setHeight		(windowPrefHeight);
    	instance().stage.setMaxWidth	(Double.MAX_VALUE);
    	instance().stage.setMaxHeight	(Double.MAX_VALUE);
    	instance().stage.show();

//    	instance().stage.setFullScreen(true);
//    	instance().stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }
    
	@Override
	public void reloadModules() {
		Platform.runLater(() -> {
		Collection<ModuleInterface> c = ModuleManager.getModules();
		if(c != null)
			for(ModuleInterface mod : c)
				if(!mod.isTabAlreadyAdded())
					if(mod.hasTab())
						ribbon.getTabs().add(mod.getTab(true));
		});
	}
	
	@Override
	public RibbonBar2 getRibbonBar() {
		return ribbon;
	}
	
	@Override
	public Stage getStage() {
		return stage;
	}
	@Override
	public Scene getScene() {
		return scene;
	}


	@Override
	public Pane getMainCanvas() {
		return canvas;
	}
	@Override
	public Pane getDesktopPane() {
		return desktop;
	}

	@Override
	public void addWindow(FloatingWindowSkin _window) {
		instance().getDesktopPane().getChildren().add(_window);
	}
	@Override
	public void removeWindow(FloatingWindowSkin _window) {
		instance().getDesktopPane().getChildren().remove(_window);
	}

	@Override
	public void switchCanvas(Region _content) {
		canvas.getChildren().clear();
		
		if(_content == null)
			canvas.getChildren().add(currentPane = desktop);
		else
			canvas.getChildren().add(currentPane = _content);
		
		currentPane.setFocusTraversable(true);
		currentPane.requestFocus();
	}
	@Override
	public void switchToDesktopCanvas() {
		switchCanvas(null);
	}

	public static final CountDownLatch latch    = new CountDownLatch(1);
    public static ApplicationBase      instance = null;
   
    public static ApplicationBase instance() {
    	if(instance == null)
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    	return instance;
    }

    public static ApplicationBase waitForInstance() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public void setInstance(ApplicationBase _inst) {
        instance = _inst;
        latch.countDown();
    }

}
