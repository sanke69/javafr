package fr.javafx.stage.impl.splash;

import javafx.application.Platform;

public interface SplashScreen {

	public boolean 	isVisible();
	public boolean 	isHidden();

	public void 	show();
	public void 	hide();
	public void 	dispose();

	public default void splash(int _delayMs) {
		show();

		new Thread(() -> {
			try {
				Thread.sleep(_delayMs);
			} catch (InterruptedException e) { }
			
			Platform.runLater(this::dispose);

		}).start();
	}

}
