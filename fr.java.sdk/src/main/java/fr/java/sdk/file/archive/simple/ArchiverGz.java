package fr.java.sdk.file.archive.simple;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

import fr.java.file.archive.ArchiverSimple;

public class ArchiverGz implements ArchiverSimple {

	@Override
	public void compress(Path _file, Path _dst) throws IOException {
		try(GZIPOutputStream stream = new GZIPOutputStream(new FileOutputStream(_dst.toFile()))) {
			FileInputStream fis  = new FileInputStream(_file.toFile());

			byte[] buffer = new byte[4096];
			int    read;
			while((read = fis.read(buffer)) > 0)
				stream.write(buffer, 0, read);

			fis.close();
		}
	}

	@Override
	public void compress(InputStream _stream, Path _dst) throws IOException {
		try(GZIPOutputStream stream = new GZIPOutputStream(new FileOutputStream(_dst.toFile()))) {
			byte[] buffer = new byte[4096];
			int    read;
			while((read = _stream.read(buffer)) > 0)
				stream.write(buffer, 0, read);
		}
	}
/*
	@Override
	public void build(Path _src, Path _dst) throws IOException {
		try(GZIPOutputStream stream = new GZIPOutputStream(new FileOutputStream(path.toFile()))) {
			FileOutputStream fos  = new FileOutputStream(_dst.toFile());

			byte[] buffer = new byte[4096];
			int    read;
			while((read = stream.write(buffer)) > 0)
				fos.write(buffer, 0, read);

			fos.close();
		}
	}
*/
}
