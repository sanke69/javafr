package fr.javafx.scene.control.terminal.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.locks.ReentrantLock;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.StreamX;
import fr.java.nio.stream.std.UserStdStreamX;
import fr.java.nio.stream.utils.PrintStreamX;
import fr.java.utils.strings.StringBuffers;
import javafx.application.Platform;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TextAreaControlStreams implements UserStdStreamX {
	private static final String returnCarrier = "\n";

	final class PipedControlInputStream  implements InputStreamX {
		private final PipedInputStream	input;
		private final PipedOutputStream	backBuffer;

		public PipedControlInputStream() {
			super();

			try {
				input = new PipedInputStream(backBuffer = new PipedOutputStream());
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public int 	available() 										throws IOException {
			return input.available();
		}

		@Override
		public int 	read() 												throws IOException {
			return input.read();
		}
		@Override
		public int 	read(final byte[] b) 								throws IOException {
			return input.read(b);
		}
		@Override
		public int 	read(final byte[] b, final int off, final int len) 	throws IOException {
			return input.read(b, off, len);
		}

		@Override
		public long skip(long n) throws IOException {
			return input.skip(n);
		}

		@Override
		public boolean markSupported() {
			return false;
		}
		@Override
		public void mark(int readlimit) {
			
		}
		@Override
		public void reset() throws IOException {
			
		}

		@Override
		public void close() 											throws IOException {
			input.close();
		}

		public void write(int b) 										throws IOException {
			backBuffer.write(b);
		}
	    public void write(byte b[]) 									throws IOException {
	    	backBuffer.write(b, 0, b.length);
	    }
	    public void write(byte b[], int off, int len) 					throws IOException {
	    	backBuffer.write(b, off, len);	    	
	    }
		public void flush()												throws IOException {
			backBuffer.flush();
		}

	}
	final class PipedControlOutputStream implements OutputStreamX {
		private final PrintStreamX			printer;
		private final ByteArrayOutputStream	backBuffer;
		private final ReentrantLock			flushLock;

		PipedControlOutputStream() {
			super();
			flushLock  = new ReentrantLock();
			backBuffer = new ByteArrayOutputStream();
			try {
				printer = StreamX.asStreamX(new PrintStream(StreamX.asStreamJava(this), true, Charset.defaultCharset().name()));
			} catch(UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public synchronized void write(int b) {
			synchronized(backBuffer) {
				backBuffer.write(b);
			}
		}
		@Override
	    public void write(byte b[]) {
			synchronized(backBuffer) {
	    	backBuffer.write(b, 0, b.length);
			}
	    }
	    public void write(byte b[], int off, int len) {
			synchronized(backBuffer) {
	    	backBuffer.write(b, off, len);	  
			}
	    }

		@Override
		public void flush() {
			Platform.runLater(onOutputFlush(backBuffer));
		}
		@Override
		public void close() {
			flush();
		}

	}
	final class PipedControlErrorStream  implements OutputStreamX {
		private final PrintStreamX			printer;
		private final ByteArrayOutputStream	backBuffer;

		PipedControlErrorStream() {
			super();

			backBuffer = new ByteArrayOutputStream();
			try {
				printer = StreamX.asStreamX(new PrintStream(StreamX.asStreamJava(this), true, Charset.defaultCharset().name()));
			} catch(UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void write(int b) {
			backBuffer.write(b);
		}
		@Override
	    public void write(byte b[]) {
	    	backBuffer.write(b, 0, b.length);
	    }
	    public void write(byte b[], int off, int len) {
	    	backBuffer.write(b, off, len);	    	
	    }

		@Override
		public void flush() {
			Platform.runLater(onErrorFlush(backBuffer));
		}
		@Override
		public void close() {
			flush();
		}

	}

	private final Charset					charset;
	private final CharsetDecoder			decoder;

	private final TextInputControl			control;

	private final PipedControlInputStream	inputStream;
	private final PipedControlOutputStream	outputStream;
	private final PipedControlErrorStream	errorStream;

	private StringBuilder					lastLine;	// always contains the last printed buffer without user inputs
	private StringBuilder					userInput;				// always contains the current user entry
	private int								lastLineBreakIndex = 0;

	public TextAreaControlStreams(final TextInputControl _textInputControl, Charset _charset) {
		super();
		charset 		= _charset;
		decoder 		= _charset.newDecoder();

		control      	= _textInputControl;
		inputStream  	= new PipedControlInputStream();
		outputStream 	= new PipedControlOutputStream();
		errorStream 	= new PipedControlErrorStream();

		userInput = new StringBuilder();
		extractLastLine();

		control.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(!keyPressed(e.getCode()))
				if(control.getCaretPosition() <= lastLineBreakIndex)
					e.consume();
		});
		control.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if(control.getCaretPosition() < lastLineBreakIndex)
				control.positionCaret(lastLineBreakIndex);
			if(e.getCharacter() != null) {
				// see. https://unicode-search.net/unicode-namesearch.pl?term=DELETE
				System.out.println( "\\u" + Integer.toHexString(e.getCharacter().charAt(0) | 0x10000).substring(1) );
				if(e.getCharacter() != "\u0008" && e.getCharacter() != "\u2326" && e.getCharacter() != "\u232B" && e.getCharacter() != "\u2421" && e.getCharacter() != "\u2425") {
					userInput.append(e.getCharacter());
					control.appendText(e.getCharacter());
					e.consume();
				} else {
					userInput.deleteCharAt(userInput.length() - 1);
					control.deleteText(control.getText().length() - 1, control.getText().length() - 1);
				}
				System.out.println("char=" + e.getCharacter() + "\t" + userInput.toString() + "\t" + e.getCode());
			}
		});
		
		control.textProperty().addListener((_obs, _old, _new) -> {
			final String lastLine = control.getText(lastLineBreakIndex, control.getLength());
//			System.out.println("user input= " + lastLine + " [" + lastLineBreakIndex + ", " + control.getLength() + "]");
		});
	}

	public Charset      getCharset() {
		return charset;
	}

	public InputStreamX getIn() {
		return inputStream;
	}
	public PrintStreamX	getOut() {
		return outputStream.printer;
	}
	public PrintStreamX	getErr() {
		return errorStream.printer;
	}

	public void 		clear() throws IOException {
		lastLineBreakIndex = 0;

		inputStream  . backBuffer.flush();
		outputStream . backBuffer.reset();
		errorStream  . backBuffer.reset();
	}

	/**
	 * USER INPUT MANAGEMENT METHODS
	**/
	protected boolean	keyPressed(KeyCode _code) {
		switch(_code) {
		case ENTER 		: enterKeyPressed(); return true;
		case HOME 		: 
		case PAGE_UP 	: control.positionCaret(lastLineBreakIndex); return true;
		case END 		: 
		case PAGE_DOWN 	: control.positionCaret(control.getLength()); return true;
		default			: ;
		}

		return false;
	}
	protected void 		enterKeyPressed() {
		try {
			control.positionCaret(control.getLength());

			final String 		userInput       = control.getText(lastLineBreakIndex, control.getLength());
			final ByteBuffer 	userInputBuffer = getCharset().encode(userInput + returnCarrier);

			lastLineBreakIndex = control.getLength();

			inputStream.write(userInputBuffer.array(), 0, userInputBuffer.remaining());
			inputStream.flush();
		} catch(IOException e) {
			if("Read end dead".equals(e.getMessage()))
				return;

			throw new RuntimeException(e);
		}
	}

	/**
	 * MESSAGE OUTPUT MANAGEMENT METHODS
	 */
	protected Runnable 	onOutputFlush(ByteArrayOutputStream _outputStream) {
		return () -> {
			synchronized(_outputStream) {
			try {
				if((_outputStream.size() == 0))
					return ;

				final ByteBuffer byteBuffer = ByteBuffer.wrap(_outputStream.toByteArray());
				final CharBuffer charBuffer = decoder.decode(byteBuffer);

				System.out.println(endWithCarriageReturn());
				
				try {
					control.appendText(charBuffer.toString());
					control.positionCaret(control.getLength());
				} finally {
					_outputStream.reset();
					lastLineBreakIndex = control.getLength();
				}
			} catch(CharacterCodingException e) {
				throw new RuntimeException(e);
			}
			}
		};
	}

	/**
	 * MESSAGE ERROR MANAGEMENT METHODS
	 */
	protected Runnable 	onErrorFlush(ByteArrayOutputStream _errorStream) {
		return () -> {
			if(_errorStream.size() == 0)
				return ;

			try {
				final ByteBuffer byteBuffer = ByteBuffer.wrap(_errorStream.toByteArray());
				final CharBuffer charBuffer = decoder.decode(byteBuffer);

				control.appendText("ERROR::" + charBuffer.toString());
				control.positionCaret(control.getLength());

			} catch(CharacterCodingException e) {
				throw new RuntimeException(e);
			} finally {
				_errorStream.reset();
				lastLineBreakIndex = control.getLength();
			}
		};
	}

	private void updateUserInput() {
		; 
	}

	private boolean endWithCarriageReturn() {
		int lastCR = control.getText().lastIndexOf("\n");
		if(lastCR != -1)
			if(lastCR == control.getText().length() - 1)
				return true;
		return false;
	}
	private void extractLastLine() {
		int lastCR = control.getText().lastIndexOf("\n");
		if(lastCR != -1)
			if(lastCR != control.getText().length()) {
				lastLine = new StringBuilder(StringBuffers.getLastLine(control.getText()));
			}
	}
}
