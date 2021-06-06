package fr.javafx.scene.control.terminal;

import java.io.IOException;
import java.nio.charset.Charset;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.utils.PrintStreamX;

import fr.javafx.scene.control.terminal.FxTerminal.TerminalVisual;
import fr.javafx.scene.control.terminal.io.TextAreaControlStreams;

// see TextFlow
public class FxTerminalVisualDefault extends BorderPane implements TerminalVisual {
	private final FxTerminal	control;

	private TextArea				textArea;
	private TextAreaControlStreams 	textAreaStreams;

	public FxTerminalVisualDefault(FxTerminal _control) {
		this(_control, Charset.defaultCharset());
	}
	public FxTerminalVisualDefault(FxTerminal _control, Charset charset) {
		super();
		control = _control;

		build();
		buildContextMenu();

//		KeyBindingUtils.installEmacsKeyBinding(textArea);
	}

	@Override
	public FxTerminal 	getSkinnable() {
		return control;
	}
	@Override
	public Node 			getNode() {
		return this;
	}
	@Override
	public void 			dispose() {
		;
	}

	@Override
	public InputStreamX		getIn() {
		return textAreaStreams.getIn();
	}
	@Override
	public PrintStreamX		getOut() {
		return textAreaStreams.getOut();
	}
	@Override
	public PrintStreamX		getErr() {
		return textAreaStreams.getErr();
	}

	private void 			build() {
		textArea = new TextArea();
		textArea.setWrapText(true);

		textAreaStreams = new TextAreaControlStreams(textArea, Charset.defaultCharset());

		setCenter(textArea);
	}
	private void 			buildContextMenu() {
		final ContextMenu menu = new ContextMenu();

		menu.getItems().add(createContextMenuItem("Clear console", ae -> {
			try {
				textAreaStreams.clear();
				textArea.clear();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}));
		menu.getItems().add(createContextMenuItem("Select All", ae -> textArea.selectAll()));
		menu.getItems().add(createContextMenuItem("Copy",       ae -> textArea.copy()));
		menu.getItems().add(createContextMenuItem("Attach",     ae -> getSkinnable().attach()));
		menu.getItems().add(createContextMenuItem("Detach",     ae -> getSkinnable().detach()));

		textArea.setContextMenu(menu);
	}

	private MenuItem 		createContextMenuItem(String name, EventHandler<ActionEvent> a) {
		final MenuItem menuItem = new MenuItem(name);
		menuItem.setOnAction(a);
		return menuItem;
	}

}
