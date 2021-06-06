package fr.java.sdk.file.archive.simple;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import fr.java.file.archive.ArchiveSimple;

public class ArchiveGz implements ArchiveSimple {
	Path 		    path;
	GZIPInputStream gzis = null;

	public ArchiveGz(Path _path) {
		super();
		path = _path;
	}

	@Override
	public Path 		getPath() {
		return path;
	}
	@Override
	public InputStream getStream() throws IOException {
		if(gzis == null)
			open();
		return gzis;
	}

	public void open() throws IOException {
		if(gzis == null)
			gzis = new GZIPInputStream(new FileInputStream(path.toFile()));
	}
	@Override
	public void close() throws IOException {
		if(gzis != null)
			gzis.close();
		gzis = null;
	}

	@Override
	public Iterator<Entry> iterator() {
		return new Iterator<Entry>() {
			boolean hasNext = true;

			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public Entry next() {
				hasNext = false;
				return new Entry() {

					@Override
					public boolean isDirectory() {
						return false;
					}
					@Override
					public boolean isFile() {
						return true;
					}

					@Override
					public String getName() {
						String uncompressed = path.toString();
						uncompressed = uncompressed.substring(uncompressed.lastIndexOf('/') + 1, uncompressed.lastIndexOf('.'));
						return uncompressed;
					}

					@Override
					public InputStream getContent() {
						if(gzis == null)
							try {
								open();
							} catch (IOException e) {
								return null;
							}
						return gzis;
					}

				};
			}

		};
	}

	public void 		extract() throws IOException {
		Path dest = Paths.get(path.toString().substring(0, path.toString().lastIndexOf('.')));
		extractTo(dest);
	}
	@Override
	public void 		extractTo(Path _dst) throws IOException {
		try(GZIPInputStream stream = new GZIPInputStream(new FileInputStream(path.toFile()))) {
			FileOutputStream fos  = new FileOutputStream(_dst.toFile());

			byte[] buffer = new byte[4096];
			int    read;
			while((read = stream.read(buffer)) > 0)
				fos.write(buffer, 0, read);

			fos.close();
		}
	}

	

}
