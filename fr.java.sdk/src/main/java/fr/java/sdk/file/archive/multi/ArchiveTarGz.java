package fr.java.sdk.file.archive.multi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import fr.java.file.archive.ArchiveMulti;
import fr.java.sdk.file.archive.multi.tar.TarEntry;
import fr.java.sdk.file.archive.multi.tar.TarInputStream;
import fr.java.sdk.file.archive.simple.ArchiveGz;
import fr.java.sdk.patterns.visitable.SimpleTree;
import fr.java.sdk.patterns.visitable.utils.PrintIndentedVisitor;

public class ArchiveTarGz implements ArchiveMulti {
	public static final int BUFFER_SIZE = 4 * 1024 * 1024;
	Path path;
	
	public ArchiveTarGz(Path _path) {
		super();
		path = _path;
	}

	@Override
	public Path 				getPath() {
		return path;
	}

	@Override
	public void 				close() throws IOException {
		;
	}

	@Override
	public Iterator<Entry> 		iterator() {
		return null;
	}

	@Override
	public Collection<String> 	getEntryNames() throws IOException {
//		try(TarInputStream tis = new TarInputStream(new GzipCompressorInputStream(new FileInputStream(getPath().toString())))) {
		try(TarInputStream tis = new TarInputStream(new ArchiveGz(getPath()).getStream())) {
			Collection<String> result = new ArrayList<String>();

			TarEntry entry = null;
			while((entry = tis.getNextEntry()) != null)
				result.add(entry.getName());

			return result;
		}
	}

	@Override
	public InputStream 			getEntry(String _entryName) throws IOException {
//		try(TarInputStream tis = new TarInputStream(new GzipCompressorInputStream(new FileInputStream(getPath().toString())))) {
		try(TarInputStream tis = new TarInputStream(new ArchiveGz(getPath()).getStream())) {
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
//		try(TarInputStream tis = new TarInputStream(new GzipCompressorInputStream(new FileInputStream(getPath().toString())))) {
		try(TarInputStream tis = new TarInputStream(new ArchiveGz(getPath()).getStream())) {
			TarEntry tarEntry = null;
			while((tarEntry = tis.getNextEntry()) != null) {
				if(tarEntry.getName().compareToIgnoreCase(_entryName) == 0) {
					_destName.getParent().toFile().mkdirs();

					try(FileOutputStream fos = new FileOutputStream(_destName.toFile())) {
						byte[] buffer = new byte[BUFFER_SIZE];
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
		byte[] buffer = new byte[BUFFER_SIZE];

//		try(TarInputStream tis = new TarInputStream(new GzipCompressorInputStream(new FileInputStream(getPath().toString())))) {
		try(TarInputStream tis = new TarInputStream(new ArchiveGz(getPath()).getStream())) {
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
