package fr.java.sdk.patterns.serializable.bin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import fr.java.nio.data.objects.ObjectOutputX;
import fr.java.nio.file.FileX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.sdk.nio.stream.javax.ObjectOutputStreamX;
import fr.java.sdk.patterns.serializable.AbstractSerializer;

public class BinSerializer extends AbstractSerializer {
	final ObjectOutputX oox;

	public BinSerializer(FileX.Regular _file) throws FileNotFoundException, IOException {
		super(_file);
		oox = new ObjectOutputStreamX(_file.getOutputStreamX());
	}
	public BinSerializer(OutputStream _os) throws IOException {
		super(_os);
		oox = new ObjectOutputStreamX(_os);
	}
	public BinSerializer(OutputStreamX _osx) throws IOException {
		super(_osx);
		oox = new ObjectOutputStreamX(_osx);
	}

	public void writeObject(Object obj) throws IOException {
		oox.writeObject(obj);
	}

	public void close() throws IOException {
		oox.flush();
		oox.close();
	}

}
