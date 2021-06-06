package fr.java.sdk.nio.stream.util;

import java.io.IOException;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.pipes.PipedInputStreamX;
import fr.java.nio.stream.pipes.PipedOutputStreamX;
import fr.java.nio.stream.utils.OutputStreamWriterX;
import fr.java.sdk.nio.stream.DefaultPipedInputStreamX;
import fr.java.sdk.nio.stream.DefaultPipedOutputStreamX;

public class PipeableOutputStreamWriterX implements OutputStreamWriterX, Runnable {
	public static final int bufferCapacity = 25_000;
	public static final int packetCapacity =  1_500;

	byte[]					packet = new byte[packetCapacity];

	final InputStreamX 		src;

	PipedInputStreamX  		in;
	PipedOutputStreamX 		out;

	int 					period;

	public PipeableOutputStreamWriterX(InputStreamX _source) {
		this(_source, 25);
	}
	public PipeableOutputStreamWriterX(InputStreamX _source, int _period) {
		super();
		src    = _source;
		period = _period;
	}

	@Override
	public void 				run() {
		if(src == null)
			throw new IllegalArgumentException();

		try {
			in  = new DefaultPipedInputStreamX(bufferCapacity);
			out = new DefaultPipedOutputStreamX(in);

			while(true) {
				long startTime = System.nanoTime();

				while(src.available() > 0) {
					int nbBytes = src.read(packet);
					out.write(packet, 0, nbBytes);
				}

				long timeToSleep = period - (int) ((System.nanoTime() - startTime) / 1e6);
				Thread.sleep(timeToSleep > 0 ? timeToSleep : 0);
			}

		} catch (IOException _e) {
			_e.printStackTrace();
		} catch (InterruptedException _e) {
			_e.printStackTrace();
		} catch (Exception _e) {
			_e.printStackTrace();
		} finally {
		    try { if (in  != null)  in.close(); } catch(IOException e) {}
		    try { if (out != null) out.close(); } catch(IOException e) {}
		}
	}

	@Override
	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void write(String str, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
