package fr.java.sdk.patterns.serializable.bin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import fr.java.nio.data.objects.ObjectInputX;
import fr.java.nio.file.FileX;
import fr.java.nio.stream.InputStreamX;
import fr.java.patterns.serializable.Deserializer;
import fr.java.sdk.nio.stream.javax.ObjectInputStreamX;
import fr.java.sdk.patterns.serializable.AbstractDeserializer;

public class BinDeserializer extends AbstractDeserializer implements Deserializer {
	protected ObjectInputX oix;

	public BinDeserializer(InputStream _os) throws IOException {
		super(_os);
		oix = new ObjectInputStreamX(_os);
	}
	public BinDeserializer(InputStreamX _osx) throws IOException {
		super(_osx);
		oix = new ObjectInputStreamX(_osx);
	}
	public BinDeserializer(FileX.Regular _file) throws FileNotFoundException, IOException {
		super(_file);
		oix = new ObjectInputStreamX(_file.getInputStreamX());
	}

	public Object readObject() throws ClassNotFoundException, IOException {
		return oix.readObject();
	}

}
