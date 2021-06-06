package fr.java.sdk.nio.buffer.circular;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.BufferOverflowException;

import fr.java.nio.buffer.BufferX;

public class CircularCharBufferX extends CircularBufferBaseX {

	protected class ReaderView extends Reader {

		@Override
		public boolean 	markSupported() {
			return true;
		}
		@Override
		public void 	mark(int readAheadLimit) throws IOException {
			synchronized (CircularCharBufferX.this) {
				if (readerClosed)
					throw new IOException("Reader has been closed; cannot mark a closed Reader.");
				if (buffer.length - 1 <= readAheadLimit)
					throw new IOException("Cannot mark stream, readAheadLimit bigger than buffer size.");
				markLength   = readAheadLimit;
				markPosition = readPosition;
			}
		}

		@Override
		public boolean 	ready() throws IOException {
			synchronized (CircularCharBufferX.this) {
				if (readerClosed)
					throw new IOException("Reader has been closed, it is not ready.");
				return (remaining() > 0);
			}
		}

		@Override
		public int 		read() throws IOException {
			while (true) {
				synchronized (CircularCharBufferX.this) {
					if (readerClosed)
						throw new IOException("Reader has been closed; cannot read from a closed Reader.");
					int available = remaining();
					if (available > 0) {
						int result = buffer[readPosition] & 0xffff;
						readPosition++;
						if (readPosition == buffer.length) {
							readPosition = 0;
						}
						ensureMark();
						return result;
					} else if (writerClosed) {
						return -1;
					}
				}
				try {
					Thread.sleep(100);
				} catch (Exception x) {
					throw new IOException("Blocking read operation interrupted.");
				}
			}
		}
		@Override
		public int 		read(char[] cbuf) throws IOException {
			return read(cbuf, 0, cbuf.length);
		}
		@Override
		public int 		read(char[] cbuf, int off, int len) throws IOException {
			while (true) {
				synchronized (CircularCharBufferX.this) {
					if (readerClosed)
						throw new IOException("Reader has been closed; cannot read from a closed Reader.");
					int available = remaining();
					if (available > 0) {
						int length = Math.min(len, available);
						int firstLen = Math.min(length, buffer.length - readPosition);
						int secondLen = length - firstLen;
						System.arraycopy(buffer, readPosition, cbuf, off, firstLen);
						if (secondLen > 0) {
							System.arraycopy(buffer, 0, cbuf, off + firstLen, secondLen);
							readPosition = secondLen;
						} else {
							readPosition += length;
						}
						if (readPosition == buffer.length) {
							readPosition = 0;
						}
						ensureMark();
						return length;
					} else if (writerClosed) {
						return -1;
					}
				}
				try {
					Thread.sleep(100);
				} catch (Exception x) {
					throw new IOException("Blocking read operation interrupted.");
				}
			}
		}

		@Override
		public long 	skip(long n) throws IOException, IllegalArgumentException {
			while (true) {
				synchronized (CircularCharBufferX.this) {
					if (readerClosed)
						throw new IOException("Reader has been closed; cannot skip characters on a closed Reader.");
					int available = remaining();
					if (available > 0) {
						int length = Math.min((int) n, available);
						int firstLen = Math.min(length, buffer.length - readPosition);
						int secondLen = length - firstLen;
						if (secondLen > 0) {
							readPosition = secondLen;
						} else {
							readPosition += length;
						}
						if (readPosition == buffer.length) {
							readPosition = 0;
						}
						ensureMark();
						return length;
					} else if (writerClosed) {
						return 0;
					}
				}
				try {
					Thread.sleep(100);
				} catch (Exception x) {
					throw new IOException("Blocking read operation interrupted.");
				}
			}
		}

		@Override
		public void 	reset() throws IOException {
			synchronized (CircularCharBufferX.this) {
				if (readerClosed)
					throw new IOException("Reader has been closed; cannot reset a closed Reader.");
				readPosition = markPosition;
			}
		}

		@Override
		public void 	close() throws IOException {
			synchronized (CircularCharBufferX.this) {
				readerClosed = true;
			}
		}

	}
	protected class WriterView extends Writer {

		@Override
		public void 	write(char[] cbuf) throws IOException {
			write(cbuf, 0, cbuf.length);
		}
		@Override
		public void 	write(char[] cbuf, int off, int len) throws IOException {
			while (len > 0) {
				synchronized (CircularCharBufferX.this) {
					if (writerClosed)
						throw new IOException("Writer has been closed; cannot write to a closed Writer.");
					if (readerClosed)
						throw new IOException("Buffer closed by Reader; cannot write to a closed buffer.");
					int spaceLeft = remaining();
					while (infinite && spaceLeft < len) {
						resize();
						spaceLeft = remaining();
					}
					if (!blockingWrite && spaceLeft < len)
						throw new BufferOverflowException();
					int realLen = Math.min(len, spaceLeft);
					int firstLen = Math.min(realLen, buffer.length - writePosition);
					int secondLen = Math.min(realLen - firstLen, buffer.length - markPosition - 1);
					int written = firstLen + secondLen;
					if (firstLen > 0) {
						System.arraycopy(cbuf, off, buffer, writePosition, firstLen);
					}
					if (secondLen > 0) {
						System.arraycopy(cbuf, off + firstLen, buffer, 0, secondLen);
						writePosition = secondLen;
					} else {
						writePosition += written;
					}
					if (writePosition == buffer.length) {
						writePosition = 0;
					}
					off += written;
					len -= written;
				}
				if (len > 0) {
					try {
						Thread.sleep(100);
					} catch (Exception x) {
						throw new IOException("Waiting for available space in buffer interrupted.");
					}
				}
			}
		}
		@Override
		public void 	write(int c) throws IOException {
			boolean written = false;
			while (!written) {
				synchronized (CircularCharBufferX.this) {
					if (writerClosed)
						throw new IOException("Writer has been closed; cannot write to a closed Writer.");
					if (readerClosed)
						throw new IOException("Buffer closed by Reader; cannot write to a closed buffer.");
					int spaceLeft = remaining();
					while (infinite && spaceLeft < 1) {
						resize();
						spaceLeft = remaining();
					}
					if (!blockingWrite && spaceLeft < 1)
						throw new BufferOverflowException();
					if (spaceLeft > 0) {
						buffer[writePosition] = (char) (c & 0xffff);
						writePosition++;
						if (writePosition == buffer.length) {
							writePosition = 0;
						}
						written = true;
					}
				}
				if (!written) {
					try {
						Thread.sleep(100);
					} catch (Exception x) {
						throw new IOException("Waiting for available space in buffer interrupted.");
					}
				}
			}
		}
		@Override
		public void 	write(String str) throws IOException {
			write(str, 0, str.length());
		}
		@Override
		public void 	write(String str, int off, int len) throws IOException {
			while (len > 0) {
				synchronized (CircularCharBufferX.this) {
					if (writerClosed)
						throw new IOException("Writer has been closed; cannot write to a closed Writer.");
					if (readerClosed)
						throw new IOException("Buffer closed by Reader; cannot write to a closed buffer.");
					int spaceLeft = remaining();
					while (infinite && spaceLeft < len) {
						resize();
						spaceLeft = remaining();
					}
					if (!blockingWrite && spaceLeft < len)
						throw new BufferOverflowException();
					int realLen = Math.min(len, spaceLeft);
					int firstLen = Math.min(realLen, buffer.length - writePosition);
					int secondLen = Math.min(realLen - firstLen, buffer.length - markPosition - 1);
					int written = firstLen + secondLen;
					for (int i = 0; i < firstLen; i++) {
						buffer[writePosition + i] = str.charAt(off + i);
					}
					if (secondLen > 0) {
						for (int i = 0; i < secondLen; i++) {
							buffer[i] = str.charAt(off + firstLen + i);
						}
						writePosition = secondLen;
					} else {
						writePosition += written;
					}
					if (writePosition == buffer.length) {
						writePosition = 0;
					}
					off += written;
					len -= written;
				}
				if (len > 0) {
					try {
						Thread.sleep(100);
					} catch (Exception x) {
						throw new IOException("Waiting for available space in buffer interrupted.");
					}
				}
			}
		}

		@Override
		public void 	flush() throws IOException {
			synchronized (CircularCharBufferX.this) {
				if (writerClosed)
					throw new IOException("Writer has been closed; cannot flush a closed Writer.");
				if (readerClosed)
					throw new IOException("Buffer closed by Reader; cannot flush.");
			}
			// this method needs to do nothing
		}

		@Override
		public void 	close() throws IOException {
			synchronized (CircularCharBufferX.this) {
				if (!writerClosed) {
					flush();
				}
				writerClosed = true;
			}
		}

	}

	protected char[] 		buffer;

	protected Reader 		reader       = new ReaderView();
	protected boolean 		readerClosed = false;
	protected Writer 		writer       = new WriterView();
	protected boolean 		writerClosed = false;

	public CircularCharBufferX() {
		this(DEFAULT_SIZE, true);
	}
	public CircularCharBufferX(int _size) {
		this(_size, true);
	}
	public CircularCharBufferX(boolean _blockingWrite) {
		this(DEFAULT_SIZE, _blockingWrite);
	}
	public CircularCharBufferX(int _size, boolean _blockingWrite) {
		super(_blockingWrite, _size == INFINITE_SIZE);
		buffer = new char[_size == INFINITE_SIZE ? DEFAULT_SIZE : _size];
	}

	public Writer getWriter() {
		return writer;
	}
	public Reader getReader() {
		return reader;
	}

	@Override
	protected int capacity_impl() {
		return buffer.length;
	}

	@Override
	public BufferX clear() {
		synchronized (this) {
			super.clear();
			readerClosed  = false;
			writerClosed  = false;
		}
		return this;
	}

	private void resize() {
		char[] newBuffer = new char[buffer.length * 2];
		int marked = marked();
		int available = remaining();
		if (markPosition <= writePosition) {
			// any space between the mark and
			// the first write needs to be saved.
			// In this case it is all in one piece.
			int length = writePosition - markPosition;
			System.arraycopy(buffer, markPosition, newBuffer, 0, length);
		} else {
			int length1 = buffer.length - markPosition;
			System.arraycopy(buffer, markPosition, newBuffer, 0, length1);
			int length2 = writePosition;
			System.arraycopy(buffer, 0, newBuffer, length1, length2);
		}
		buffer = newBuffer;
		markPosition = 0;
		readPosition = marked;
		writePosition = marked + available;
	}

	private int marked() {
		if (markPosition <= readPosition)
			return (readPosition - markPosition);
		return (buffer.length - (markPosition - readPosition));
	}

	private void ensureMark() {
		if (marked() > markLength) {
			markPosition = readPosition;
			markLength = 0;
		}
	}


}