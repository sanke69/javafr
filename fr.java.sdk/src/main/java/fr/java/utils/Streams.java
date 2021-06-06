package fr.java.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Streams {

	public static void copyStream(final InputStream in, final OutputStream out, final int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int len;
		while((len = in.read(buf)) != -1)
			out.write(buf, 0, len);
	}

	public static ByteBuffer readFully(InputStream _is) throws IOException {
		byte[] bytes = new byte[_is.available()];
		int needRead = _is.available(), read = 0, pos = 0;
		while(needRead > 0) {
			read      = _is.read(bytes, pos, bytes.length - pos);
			pos      += read;
			needRead -= read;
		}
		return ByteBuffer.wrap(bytes);
	}

	public static String toString(InputStream _stream) throws IOException {
		byte[] bytes = new byte[_stream.available()];
		_stream.read(bytes);
		return new String(bytes);
	}

	public static byte[] getBytes(InputStream _is) throws IOException {
		byte[] data   = new byte[_is.available()];
		int    length = _is.available(), 
			   offset = 0, 
			   read   = 0;

		while((read = _is.read(data, offset, length - offset)) > 0)
			offset += read;
		
		return data;
	}

    public static byte[] toByteArray(InputStream _stream) throws IOException {
        return toByteArray(_stream, 1024 * 8);
    }
    public static byte[] toByteArray(InputStream _stream, int _arrayLength) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[_arrayLength];
            int count;
            while ((count = _stream.read(buffer)) > 0) {
                baos.write(buffer, 0, count);
            }

            return baos.toByteArray();
        } finally {
        	_stream.close();
        }
    }

}
