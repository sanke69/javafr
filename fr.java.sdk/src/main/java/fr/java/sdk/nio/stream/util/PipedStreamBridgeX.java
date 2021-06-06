package fr.java.sdk.nio.stream.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.pipes.PipedInputStreamX;
import fr.java.nio.stream.pipes.PipedOutputStreamX;
import fr.java.nio.stream.utils.StreamBridgeX;
import fr.java.sdk.nio.StreamsX;

public abstract class PipedStreamBridgeX implements StreamBridgeX, Runnable {
	public static final int DEFAULT_WAIT_TIME = 25;
	private static int threadInitNumber;
	private static synchronized int nextThreadNum() { return threadInitNumber++; }

	Thread				processing;
	InputStreamX        source;
	PipedInputStreamX	internal;
	PipedOutputStreamX	destination;

	public static InputStreamX create(InputStreamX _src, BiConsumer<InputStreamX, OutputStreamX> _process) throws IOException {
		return new PipedStreamBridgeX(_src) {
			public void process(InputStreamX _in, OutputStreamX _out) {
				_process.accept(_in, _out);
			}
		};
	}
	@SafeVarargs
	public static InputStreamX create(InputStreamX _src, Consumer<ByteBuffer>... _transformations) {
		try {
			return new PipedStreamBridgeX(_src) {
				List<Consumer<ByteBuffer>> transforms;

				{
					if(_transformations != null)
						transforms = new ArrayList<Consumer<ByteBuffer>>(Arrays.asList(_transformations));
					else
						transforms = new ArrayList<Consumer<ByteBuffer>>();
				}

				@Override
				public void process(InputStreamX _in, OutputStreamX _out) throws IOException {
					byte[] bytes = new byte[65_635];
					ByteBuffer buffer = ByteBuffer.wrap(bytes);

					int readBytes = -1;
					while((readBytes = _in.read(bytes)) != -1) {
						buffer = ByteBuffer.wrap(bytes);
						buffer.rewind();
						buffer.limit(readBytes);

						for(Consumer<ByteBuffer> p : transforms)
							p.accept(buffer);

						_out.write(bytes, 0, buffer.remaining());
						_out.flush();
					}

				}

			};
		} catch(IOException e) {
			e.printStackTrace();
			return _src;
		}
	}

	protected PipedStreamBridgeX(final InputStreamX _src) throws IOException {
		super();
		source      = _src;
		internal 	= StreamsX.Pipe.newInput();
		destination = StreamsX.Pipe.newConnectedWith(internal);

		new Thread("StreamBridge-" + nextThreadNum()) {
			public void run() {
				try {
					process(_src, destination);
				} catch(IOException e) {
					System.err.println("StreamBridge " + getName());
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void run() {
		while(true) {
			try {

				process(source, destination);
				Thread.sleep(DEFAULT_WAIT_TIME);

			} catch(IOException e) {
				e.printStackTrace();
			} catch(InterruptedException e) {
				e.printStackTrace();
				return ;
			}
		}
	}
	
	public int 		available() throws IOException {
		return internal.available();
	}

	public int 		read() throws IOException {
		return internal.read();
	}
	public int 		read(byte[] b) throws IOException {
		return internal.read(b);
	}
	public int 		read(byte[] b, int off, int len) throws IOException {
		return internal.read(b, off, len);
	}

	public long 	skip(long n) throws IOException {
		return internal.skip(n);
	}

	public boolean 	markSupported() {
		return internal.markSupported();
	}
	public void 	mark(int readlimit) {
		internal.mark(readlimit);
	}
	public void 	reset() throws IOException {
		internal.reset();
	}

	public void 	close() throws IOException {
		internal.close();
	}

}
