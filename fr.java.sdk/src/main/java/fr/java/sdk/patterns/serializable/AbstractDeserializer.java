package fr.java.sdk.patterns.serializable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import fr.java.nio.file.FileX;
import fr.java.nio.stream.InputStreamX;
import fr.java.patterns.serializable.Deserializer;

public abstract class AbstractDeserializer implements Deserializer {

	public AbstractDeserializer(InputStream _os) throws IOException {
		super();
	}
	public AbstractDeserializer(InputStreamX _osx) throws IOException {
		super();
	}

	public AbstractDeserializer(FileX.Regular _file) throws FileNotFoundException, IOException {
		super();
	}

}
