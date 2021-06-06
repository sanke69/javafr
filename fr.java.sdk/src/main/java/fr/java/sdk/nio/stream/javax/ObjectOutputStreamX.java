package fr.java.sdk.nio.stream.javax;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import fr.java.nio.data.objects.ObjectOutputX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.StreamX;

public class ObjectOutputStreamX extends ObjectOutputStream implements OutputStreamX, ObjectOutputX {

	public ObjectOutputStreamX(OutputStream _out) throws IOException {
		super(_out);
	}
	public ObjectOutputStreamX(OutputStreamX _out) throws IOException {
		super(StreamX.asStreamJava(_out));
	}

	protected ObjectOutputStreamX() throws IOException, SecurityException {
		super();
	}

}
