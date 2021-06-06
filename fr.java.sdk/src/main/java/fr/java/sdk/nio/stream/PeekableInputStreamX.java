package fr.java.sdk.nio.stream;

import java.io.IOException;
import java.io.InputStream;

import fr.java.nio.Peekable;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.StreamX;

public class PeekableInputStreamX implements Peekable, InputStreamX {
	private static final int DEFAULT_PEEK_SIZE = 16; 

	private InputStreamX stream;
	private byte 		 peekBytes[];
	private int 		 peekLength;

	public PeekableInputStreamX(InputStreamX _isx) {
		super();
		stream     = _isx;
		peekBytes  = new byte[DEFAULT_PEEK_SIZE];
		peekLength = 0;
	}
	public PeekableInputStreamX(InputStream _isx) {
		super();
		stream     = StreamX.asStreamX(_isx);
		peekBytes  = new byte[DEFAULT_PEEK_SIZE];
		peekLength = 0;
	}

	protected byte[] getPeekBuffer() {
		return peekBytes;
	}
	protected int    getPeekLength() {
		return peekLength;
	}

	@Override
	public int 		peek() throws IOException {
		return peek(0);
	}
	protected int 	peek(int depth) throws IOException {
		if (peekBytes.length <= depth) {
			byte temp[] = new byte[depth + 10];
			for (int i = 0; i < peekBytes.length; i++) {
				temp[i] = peekBytes[i];
			}
			peekBytes = temp;
		}

		if (depth >= peekLength) {
			int offset = peekLength;
			int length = (depth - peekLength) + 1;
			int lengthRead = stream.read(peekBytes, offset, length);

			if (lengthRead == -1)
				return -1;

			peekLength = depth + 1;
		}

		return peekBytes[depth];
	}
	@Override
	public int 		peek(byte[] _b) throws IOException {
		peek(_b.length);
		System.arraycopy(peekBytes, 0, _b, 0, _b.length);
		return _b.length;
	}
	@Override
	public int 		peek(byte[] _b, int _off, int _len) throws IOException {
		peek(_len - _off);
		System.arraycopy(peekBytes, 0, _b, _off, _len);
		return _len - _off;
	}

	@Override
	public int 		available() throws IOException {
		return stream.available();
	}

	@Override
	public int 		read() throws IOException {
		if (peekLength == 0)
			return stream.read();

		int result = peekBytes[0];
		this.peekLength--;
		System.arraycopy(peekBytes, 1, peekBytes, 0, peekLength - 1);

//		for (int i = 0; i < this.peekLength; i++)
//			peekBytes[i] = peekBytes[i + 1];

		return result;
	}
	@Override
	public int 		read(byte[] b) throws IOException {
		return stream.read(b);
	}
	@Override
	public int 		read(byte[] b, int off, int len) throws IOException {
		return stream.read(b, off, len);
	}

	@Override
	public long 	skip(long n) throws IOException {
		return stream.skip(n);
	}

	@Override
	public boolean 	markSupported() {
		return stream.markSupported();
	}
	@Override
	public void 	mark(int readlimit) {
		stream.mark(readlimit);
	}
	@Override
	public void 	reset() throws IOException {
		stream.reset();
	}

	@Override
	public void 	close() throws IOException {
		stream.close();
	}


}
