package fr.javafx.xtra;

import fr.javafx.lang.enums.StageAnchor;
import fr.javafx.stage.StageExt;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

public abstract class SystrayMenuAdapter extends StageExt implements Systray.Menu {

	public SystrayMenuAdapter() {
		super(StageStyle.UNDECORATED, StageAnchor.SCREEN_BOTTOM_RIGHT);
		setAlwaysOnTop(true);
	}
	public SystrayMenuAdapter(Pane _pane) {
		super(StageStyle.UNDECORATED, StageAnchor.SCREEN_BOTTOM_RIGHT);
		setScene(new Scene(_pane));
		setAlwaysOnTop(true);
	}

	public void toggleShow() {
		Runnable toggleMenu = () -> {
			if(isShowing()) {
				hide();
			} else {
				show();
	            toFront();
			}
		};

		if(Platform.isFxApplicationThread())
			toggleMenu.run();
		else
			Platform.runLater( toggleMenu );
	}

}
