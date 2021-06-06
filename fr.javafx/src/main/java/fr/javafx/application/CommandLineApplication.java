package fr.javafx.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.stage.Stage;

import fr.javafx.stage.impl.TerminalStage;

public abstract class CommandLineApplication extends Application {
	public TerminalStage 	terminal;
	private boolean			pauseBeforeExit	= true;

	public static   void main(String[] args) {
		launch();
	}

	public CommandLineApplication() {
		super();
	}
	
	public abstract void invokeMain(String[] args);

	@Override
	public final void 			start(final Stage primaryStage) throws Exception {
		final String[] args = getParameters().getRaw().toArray(new String[0]);

		terminal = new TerminalStage();
		terminal.show();

//		System.setIn  (console.getIn());
//		System.setOut (console.getOut());
//		System.setErr (console.getErr());

		final Thread thread = new Thread(() -> {
			Throwable throwable = null;
			try {
				invokeMain(args);
			} catch(Throwable e) {
				throwable = e;
			}

			final int result = throwable == null ? 0 : 1;
			if(this.pauseBeforeExit) {
				System.out.print("Press enter key to exit.");
				try {
					new BufferedReader(new InputStreamReader(System.in)).readLine();
				} catch(IOException e) {
				}
			}
			System.exit(result);
		});
		thread.setName("Console Application Main Thread");
		thread.start();
	}

	public final TerminalStage 	getTerminalStage() {
		return terminal;
	}

}
