package fr.java.sdk.file.archive.multi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import fr.java.file.archive.Archive;
import fr.java.file.archive.ArchiveMulti;
import fr.java.sdk.file.archive.multi.tar.TarEntry;
import fr.java.sdk.file.archive.multi.tar.TarInputStream;

public class ArchiveTar implements ArchiveMulti, Iterable<Archive.Entry> {
	Path 			path;
	TarInputStream 	tis = null;

	public ArchiveTar(Path _path) {
		super();
		path = _path;
	}
	public ArchiveTar(InputStream _stream) {
		super();
		tis = new TarInputStream(_stream);
	}

	@Override
	public Path 				getPath() {
		return path;
	}
	@Override
	public InputStream 			getStream() throws IOException {
		if(tis != null)
			return tis;
		if(getPath() != null)
			return new FileInputStream(getPath().toFile());
		return null;
	}

	public void 				open() throws IOException {
		if(tis == null)
			tis = new TarInputStream(new FileInputStream(getPath().toString()));
	}
	@Override
	public void 				close() throws IOException {
		if(tis != null)
			tis.close();
		tis = null;
	}

	@Override
	public Iterator<Entry> 		iterator() {
		return new Iterator<Entry>() {
			boolean  requestIncrement = true;
			TarEntry nextEntry;

			@Override
			public boolean hasNext() {
				if(requestIncrement) {
					try {
						if(tis == null)
							open();
						nextEntry = tis.getNextEntry();
					} catch(IOException e) {
						nextEntry = null;
					}
					requestIncrement = false;
				}
				return nextEntry != null;
			}

			@Override
			public Entry   next() {
				if(!hasNext())
					return null;

				requestIncrement = true;
				return new Entry() {

					@Override
					public String 		getName() {
						return nextEntry.getName();
					}

					@Override
					public InputStream 	getContent() throws IOException {
						ByteArrayOutputStream out  = new ByteArrayOutputStream();

						byte[] buffer = new byte[32_768];
						int    read   = 0;
						while((read = tis.read(buffer)) > 0)
							out.write(buffer,  0, read);

						return new ByteArrayInputStream(out.toByteArray());
					}

					@Override
					public boolean 		isFile() {
						return !nextEntry.isDirectory();
					}

					@Override
					public boolean 		isDirectory() {
						return nextEntry.isDirectory();
					}};
			}

		};
	}

	@Override
	public Collection<String> 	getEntryNames() throws IOException {
		try(TarInputStream tis = new TarInputStream(new FileInputStream(getPath().toString()))) {
			Collection<String> result = new ArrayList<String>();

			TarEntry entry;
			while((entry = tis.getNextEntry()) != null)
				result.add(entry.getName());

			return result;
		}
	}

	@Override
	public InputStream 			getEntry(String _entryName) throws IOException {
		try(TarInputStream tis = new TarInputStream(new FileInputStream(getPath().toString()))) {
			TarEntry tarEntry = null;
			while((tarEntry = tis.getNextEntry()) != null) {
				if(tarEntry.getName().compareToIgnoreCase(_entryName) == 0) {
					ByteArrayOutputStream out  = new ByteArrayOutputStream();

					byte[] buffer = new byte[32_768];
					int    read   = 0;
					while((read = tis.read(buffer)) > 0)
						out.write(buffer,  0, read);
					
					return new ByteArrayInputStream(out.toByteArray());
				}
			}
		}
		
		return null;
	}

	@Override
	public boolean 				extract(String _entryName, Path _destName) throws IOException {
		try(TarInputStream tis = new TarInputStream(new FileInputStream(getPath().toString()))) {
			TarEntry tarEntry = null;
			while((tarEntry = tis.getNextEntry()) != null) {
				if(tarEntry.getName().compareToIgnoreCase(_entryName) == 0) {
					_destName.getParent().toFile().mkdirs();

					try(FileOutputStream fos = new FileOutputStream(_destName.toFile())) {
						byte[] buffer = new byte[Archive.BUFFER_SIZE];
						int    read = 0;
						while((read = tis.read(buffer)) >= 0)
							fos.write(buffer, 0, read);

						return true;
					}
				}
			}
		}
		
		return false;
	}
	@Override
	public boolean 				extractAll(Path _destFolder) throws IOException {
		byte[] buffer = new byte[Archive.BUFFER_SIZE];

		try(TarInputStream tis = new TarInputStream(new FileInputStream(getPath().toString()))) {
			TarEntry tarEntry = null;
			while((tarEntry = tis.getNextEntry()) != null) {
				Path entryName = _destFolder.resolve(tarEntry.getName());
				
				entryName.getParent().toFile().mkdirs();

				try(FileOutputStream fos = new FileOutputStream(entryName.toFile())) {
					int    read = 0;
					while((read = tis.read(buffer)) >= 0)
						fos.write(buffer, 0, read);
				}
			}
		}
		
		return true;
	}

}
