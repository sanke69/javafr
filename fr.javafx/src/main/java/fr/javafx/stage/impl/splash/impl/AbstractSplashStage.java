package fr.javafx.stage.impl.splash.impl;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import fr.javafx.io.screen.Screens;
import fr.javafx.stage.impl.splash.SplashScreen;

public abstract class AbstractSplashStage extends Stage implements SplashScreen {
	protected Scene scene = null;

	protected AbstractSplashStage(String _title, Image _icon) {
		super();

		initStyle(StageStyle.UNDECORATED);
		initStyle(StageStyle.TRANSPARENT);
//		initStyle(StageStyle.UTILITY);

		initModality(Modality.WINDOW_MODAL);

		setWidth(320);
		setHeight(240);
		setResizable(false);

		setAlwaysOnTop(true);

		setTitle(_title != null ? _title : "Panthea");

		if(_icon != null)
			getIcons().add(_icon);

		Screens.centerOnScreen(this, Screens.getActiveScreen());
	}
	public AbstractSplashStage(String _title, Image _icon, Scene _content) {
		this(_title, _icon);
		setScene(scene = _content);
	}

	@Override
	public boolean isVisible() {
		return scene != null;
	}
	@Override
	public boolean isHidden() {
		return scene == null;
	}

	@Override
	public void dispose() {
		hide();
		setScene(null);
	}

}
