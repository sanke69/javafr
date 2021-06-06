package fr.java.sdk.patterns.serializable.xml;

import java.beans.XMLDecoder;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.java.nio.file.FileX;
import fr.java.nio.stream.StreamX;
import fr.java.patterns.serializable.Deserializer;
import fr.java.sdk.patterns.serializable.AbstractDeserializer;

public class XmlDeserializer extends AbstractDeserializer implements Deserializer {
	final XMLDecoder decoder;

	public XmlDeserializer(FileX.Regular _file) throws FileNotFoundException, IOException {
		super(_file);
		decoder = new XMLDecoder(StreamX.asStreamJava(_file.getInputStreamX()));
	}

	public Object readObject() throws ClassNotFoundException, IOException {
		return decoder.readObject();
	}

}
