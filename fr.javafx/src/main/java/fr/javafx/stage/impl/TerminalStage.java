package fr.javafx.stage.impl;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.std.UserStdStreamX;
import fr.java.nio.stream.utils.PrintStreamX;

import fr.javafx.scene.control.terminal.FxTerminal;
import fr.javafx.stage.StageExt;
import javafx.scene.Scene;

public class TerminalStage extends StageExt implements UserStdStreamX {

	final FxTerminal terminal;

	public TerminalStage() {
		super();

		setTitle(getClass().getSimpleName());
		setScene(new Scene(terminal = new FxTerminal(), 800, 600));
		setOnCloseRequest(e -> System.exit(0));
		show();
	}

	@Override
	public InputStreamX  getIn() {
		return terminal.getIn();
	}
	@Override
	public OutputStreamX getOut() {
		return terminal.getOut();
	}
	@Override
	public OutputStreamX getErr() {
		return terminal.getErr();
	}

}
