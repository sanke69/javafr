package fr.java.sdk.patterns.serializable.xml;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.java.nio.file.FileX;
import fr.java.nio.stream.StreamX;
import fr.java.sdk.patterns.serializable.AbstractSerializer;

public class XmlSerializer extends AbstractSerializer {
	final XMLEncoder encoder;

	public XmlSerializer(FileX.Regular _file) throws FileNotFoundException, IOException {
		super(_file);
		encoder = new XMLEncoder(StreamX.asStreamJava(_file.getOutputStreamX()));
	}

	public void writeObject(Object obj) throws IOException {
		encoder.writeObject(obj);
	}

	public void close() throws IOException {
		encoder.flush();
		encoder.close();
	}

}
