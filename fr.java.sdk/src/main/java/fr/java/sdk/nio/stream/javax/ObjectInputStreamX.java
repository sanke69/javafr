package fr.java.sdk.nio.stream.javax;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import fr.java.nio.data.objects.ObjectInputX;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.StreamX;

public class ObjectInputStreamX extends ObjectInputStream implements InputStreamX, ObjectInputX {

	public ObjectInputStreamX(InputStream _in) throws IOException {
		super(_in);
	}
	public ObjectInputStreamX(InputStreamX _in) throws IOException {
		super(StreamX.asStreamJava(_in));
	}

	protected ObjectInputStreamX() throws IOException, SecurityException {
		super();
	}

}
