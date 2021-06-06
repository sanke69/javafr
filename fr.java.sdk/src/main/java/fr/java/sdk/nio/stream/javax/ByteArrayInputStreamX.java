package fr.java.sdk.nio.stream.javax;

import java.io.ByteArrayInputStream;

import fr.java.nio.stream.InputStreamX;

public class ByteArrayInputStreamX extends ByteArrayInputStream implements InputStreamX {

	public ByteArrayInputStreamX(byte[] _buffer) {
        super(_buffer);
    }
    public ByteArrayInputStreamX(byte[] _buffer, int _offset, int _length) {
        super(_buffer, _offset, _length);
    }
   
}
