package fr.javafx.scene.control.terminal;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.StreamX;
import fr.java.nio.stream.std.UserStdStreamX;
import fr.java.nio.stream.utils.PrintStreamX;

import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.behavior.Visual;

public class FxTerminal extends Control implements UserStdStreamX {
	public static final String defaultCss = FxTerminal.class.getResource("Terminal.css").toString();

	public interface TerminalVisual extends Visual<FxTerminal>, UserStdStreamX {
	    public InputStreamX	getIn();
	    public PrintStreamX	getOut();
	    public PrintStreamX	getErr();
	}

	private final BooleanProperty 					attachedToSystemIO;
	private final ChangeListener<? super Skin<?>> 	skinListener;

	private InputStreamX 							in;
	private PrintStreamX 							out, err;

	public FxTerminal() {
		this(Charset.defaultCharset(), false);
	}
	public FxTerminal(Charset _charset) {
		this(_charset, false);
	}
	public FxTerminal(boolean _attachSystemIO) {
		this(Charset.defaultCharset(), _attachSystemIO);
	}
	public FxTerminal(Charset _charset, boolean _attachSystemIO) {
		super();

		attachedToSystemIO = new SimpleBooleanProperty(_attachSystemIO);

		skinListener       = (_obs, _old, _new) -> {
			if(_new == null) {
				in  = null;
				out = null;
				err = null;
				detach();
				return ;
			}

			if(_new instanceof AdvancedSkin) {
				TerminalVisual visual = (TerminalVisual) ((AdvancedSkin<?>) _new).getVisual();
				in  = visual.getIn();
				out = visual.getOut();
				err = visual.getErr();

				if(attachedToSystemIO.get())
					attach();
			}

		};

		getStylesheets(). add(defaultCss);
		getStyleClass() . add("terminalFX");

		skinProperty()  . addListener(skinListener);
	}

	@Override
	protected Skin<? extends FxTerminal> createDefaultSkin() {
		return new AdvancedSkin<FxTerminal>(this, FxTerminalVisualDefault::new);
	}

	public InputStreamX getIn() {
		return in;
	}
	public PrintStreamX getOut() {
		return out;
	}
	public PrintStreamX getErr() {
		return err;
	}
	
	public void attach() {
		System.setOut(StreamX.asStreamJava( getOut() ));
		System.setErr(StreamX.asStreamJava( getErr() ));
	}
	public void detach() {
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.out), 128), true));
		System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.err), 128), true));
	}

}
