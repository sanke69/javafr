package fr.java.sdk.nio;

import java.io.IOException;

import fr.java.nio.Seekable;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.pipes.PipedInputStreamX;
import fr.java.nio.stream.pipes.PipedOutputStreamX;
import fr.java.nio.stream.utils.InputStreamReaderX;
import fr.java.sdk.nio.stream.DefaultPipedInputStreamX;
import fr.java.sdk.nio.stream.DefaultPipedOutputStreamX;
import fr.java.sdk.nio.stream.PeekableInputStreamX;
import fr.java.sdk.nio.stream.PushbackInputStreamX;
import fr.java.sdk.nio.stream.SeekableInputStreamX;
import fr.java.sdk.nio.stream.util.PipeableInputStreamReaderX;
import fr.java.sdk.nio.stream.util.SeekableInputStreamReaderX;

public class StreamsX {

	public static final class Input {

		public static final class Pushback {

			public static PushbackInputStreamX wrap(InputStreamX _isx) {
				return new PushbackInputStreamX(_isx);
			}

		}
		
		public static final class Peekable {

			public static PeekableInputStreamX wrap(InputStreamX _isx) {
				return new PeekableInputStreamX(_isx);
			}

		}

		public static final class Seekable {

			public static SeekableInputStreamX wrap(InputStreamX _isx) {
				return new SeekableInputStreamX(_isx);
			}

		}

	}
	public static final class Output {

	}

	public static final class Pipe {

		public static PipedInputStreamX newInput() {
			return new DefaultPipedInputStreamX();
		}
		public static PipedOutputStreamX newOutput() {
			return new DefaultPipedOutputStreamX();
		}

		public static PipedInputStreamX newConnectedWith(PipedOutputStreamX _out) throws IOException {
			return new DefaultPipedInputStreamX(_out);
		}
		public static PipedOutputStreamX newConnectedWith(PipedInputStreamX _in) throws IOException {
			return new DefaultPipedOutputStreamX(_in);
		}

	}
	
	public static final class Reader {

		@SuppressWarnings("unchecked")
		public static <S extends InputStreamReaderX & Seekable> S newSeekable(InputStreamX _in) {
			return (S) new SeekableInputStreamReaderX(_in);
		}
		@SuppressWarnings("unchecked")
		public static <S extends InputStreamReaderX & Seekable> S newSeekable(InputStreamX _in, int _capacity) {
			return (S) new SeekableInputStreamReaderX(_in, _capacity);
		}
		@SuppressWarnings("unchecked")
		public static <S extends InputStreamReaderX & Seekable> S newPipable(InputStreamX _in) {
			return (S) new PipeableInputStreamReaderX(_in);
		}

	}
	
	
}
