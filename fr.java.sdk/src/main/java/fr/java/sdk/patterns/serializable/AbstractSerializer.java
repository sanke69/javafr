package fr.java.sdk.patterns.serializable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import fr.java.nio.data.objects.ObjectOutputX;
import fr.java.nio.file.FileX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.patterns.serializable.Serializer;
import fr.java.sdk.nio.stream.javax.ObjectOutputStreamX;

public abstract class AbstractSerializer implements Serializer {

	protected ObjectOutputX oox;

	public AbstractSerializer(FileX.Regular _file) throws FileNotFoundException, IOException {
		super();
		oox = new ObjectOutputStreamX(_file.getOutputStreamX());
	}
	public AbstractSerializer(OutputStream _os) throws IOException {
		super();
		oox = new ObjectOutputStreamX(_os);
	}
	public AbstractSerializer(OutputStreamX _osx) throws IOException {
		super();
		oox = new ObjectOutputStreamX(_osx);
	}

}
