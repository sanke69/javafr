package fr.javafx.stage.impl;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DummyStage extends Stage {

	public DummyStage() {
		super();

	    initStyle(StageStyle.UNDECORATED);

	    setMinWidth(1);
	    setMinHeight(1);
	    setMaxWidth(1);
	    setMaxHeight(1);
	    setX(Double.MAX_VALUE);
	    show();
	}

}
