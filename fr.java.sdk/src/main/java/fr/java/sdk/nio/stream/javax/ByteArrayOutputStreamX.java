package fr.java.sdk.nio.stream.javax;

import java.io.ByteArrayOutputStream;

import fr.java.nio.stream.OutputStreamX;

public class ByteArrayOutputStreamX extends ByteArrayOutputStream implements OutputStreamX {

	public ByteArrayOutputStreamX() {
        super();
    }
    public ByteArrayOutputStreamX(int _capacity) {
        super(_capacity);
    }
   
}
