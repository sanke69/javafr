package fr.java.sdk.file.archive.multi;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import fr.java.file.archive.Archive;
import fr.java.file.archive.ArchiveMulti;
import fr.java.sdk.nio.file.FileObject;

public class ArchiveZip implements ArchiveMulti {
	Path           path;
	ZipFile        zipFile = null;
	ZipInputStream zis     = null;

	public ArchiveZip(Path _path) {
		super();
		path = _path;
	}
	public ArchiveZip(InputStream _stream) {
		super();
		zis = new ZipInputStream(_stream);
	}

	@Override
	public Path 				getPath() {
		return path;
	}
	@Override
	public InputStream 			getStream() throws IOException {
		if(zis != null)
			return zis;
		if(getPath() != null)
			return new FileInputStream(getPath().toFile());
		return null;
	}

	public void open() throws IOException {
		if(zipFile == null)
			zipFile = new ZipFile(path.toString());
	}
	@Override
	public void close() throws IOException {
		if(zipFile != null)
			zipFile.close();
		zipFile = null;
	}

	@Override
	public Iterator<Entry> iterator() {
		if(zis == null)
			try {
				zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(path.toFile())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		return new Iterator<Entry>() {
			ZipEntry ze = null;

			@Override
			public boolean hasNext() {
				try {
					return (ze = zis.getNextEntry()) != null;
				} catch (IOException e) {
					return false;
				}
			}

			@Override
			public Entry next() {
				return new Entry() {

					@Override
					public String getName() {
						return ze.getName();
					}

					@Override
					public InputStream getContent() throws IOException {
						ByteArrayOutputStream out  = new ByteArrayOutputStream();

						byte[] buffer = new byte[32_768];
						int    read   = 0;
//						while((read = zis.read(buffer)) != -1)
//							out.write(buffer, 0, read);
						while((read = zis.read(buffer)) > 0)
							out.write(buffer,  0, read);

						return new ByteArrayInputStream(out.toByteArray());
					}

					@Override
					public boolean isFile() {
						return !ze.isDirectory();
					}

					@Override
					public boolean isDirectory() {
						return ze.isDirectory();
					}
					
				};
			}
			
		};
	}

	@Override
	public Collection<String> 	getEntryNames() throws IOException {
		try(ZipFile zipFile = new ZipFile(path.toString())) {
			return zipFile.stream()
							.map(ZipEntry::getName)
							.collect(Collectors.toList());
		}
	}

	public InputStream			getEntry(String _entryName) throws IOException {
		try(ZipFile zipFile = new ZipFile(path.toString())) {
			ZipEntry    entry = zipFile.getEntry(_entryName);
			InputStream is    = zipFile.getInputStream(entry);

			ByteArrayOutputStream out  = new ByteArrayOutputStream();

			byte[] buffer = new byte[32_768];
			int    read   = 0;
			while((read = is.read(buffer)) > 0)
				out.write(buffer,  0, read);
			
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	@Override
	public boolean 				extract(String _entryName, Path _dest) throws IOException {
		try(ZipFile zipFile = new ZipFile(path.toString())) {
			ZipEntry entry = zipFile.getEntry(_entryName);

			InputStream  in  = zipFile.getInputStream(entry);
			OutputStream out = new FileOutputStream(_dest.toFile());

			byte[] buffer = new byte[Archive.BUFFER_SIZE];
			int len;
			while((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.close();
			return false;
		}
	}

	@Override
	public boolean 				extractAll(Path _destFolder) throws IOException {
		byte[] buffer = new byte[Archive.BUFFER_SIZE];

		try(ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(path.toFile())))) {
			ZipEntry ze = null;

			while((ze = zis.getNextEntry()) != null) {
				Path entryPath = _destFolder.resolve(ze.getName());

				if(ze.isDirectory())
					new FileObject(entryPath).mkdir(true);
				else {
					try(OutputStream out = new FileOutputStream(path.toFile())) {
						int read;
						while((read = zis.read(buffer)) != -1)
							out.write(buffer, 0, read);
					}
				}
			}
		}

		return true;
	}

}
