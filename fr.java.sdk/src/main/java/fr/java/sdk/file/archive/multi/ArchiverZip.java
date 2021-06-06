package fr.java.sdk.file.archive.multi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.java.file.archive.Archive;
import fr.java.sdk.lang.tuples.SimplePair;

public class ArchiverZip extends AbstractArchiver {

	public ArchiverZip() {
		super();
	}

	@Override
	public Archive toArchive() throws IOException {
		List<SimplePair<String, Path>> fileList = orderedContentList();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream       zos  = new ZipOutputStream(baos);
		
		for(SimplePair<String, Path> fe : fileList) {
			String entry = fe.getFirst();
			Path   file  = fe.getSecond();

			if(file.toFile().isDirectory() && file.toFile().listFiles().length == 0)
				zip_addEmptyDirectory(entry, zos);
			else
				zip_addFile(file, entry, zos);
		}

		for(Map.Entry<String, InputStream> se : streamMap().entrySet()) {
			String      entry = se.getKey();
			InputStream is    = se.getValue();

			zip_addStream(is, entry, zos);
		}

		return new ArchiveZip(new ByteArrayInputStream(baos.toByteArray()));
	}

	@Override
	public void writeTo(Path _dst) throws IOException {
		List<SimplePair<String, Path>> fileList = orderedContentList();

		try(FileOutputStream fos = new FileOutputStream(_dst.toFile())) {	// TODO:: maybe already closed with zos...
			try(ZipOutputStream zos = new ZipOutputStream(fos)) {
				for(SimplePair<String, Path> fe : fileList) {
					String entry = fe.getFirst();
					Path   file  = fe.getSecond();

					if(file.toFile().isDirectory() && file.toFile().listFiles().length == 0)
						zip_addEmptyDirectory(entry, zos);
					else
						zip_addFile(file, entry, zos);
				}

				for(Map.Entry<String, InputStream> se : streamMap().entrySet()) {
					String      entry = se.getKey();
					InputStream is    = se.getValue();

					zip_addStream(is, entry, zos);
				}
			}
		}
	}

	private void zip_addFile(Path _from, String _to, ZipOutputStream _zos) throws IOException {
		_zos.putNextEntry(new ZipEntry(_to));

		try(FileInputStream fis = new FileInputStream(_from.toFile())) {
			byte[] bytes  = new byte[65_535];
			int    length = 0;
			while((length = fis.read(bytes)) >= 0)
				_zos.write(bytes, 0, length);
		}
		
		_zos.closeEntry();
	}
	private void zip_addEmptyDirectory(String _to, ZipOutputStream _zos) throws IOException {
		ZipEntry dirEntry = new ZipEntry(_to);
		dirEntry.setMethod(0);
		dirEntry.setSize(0);
		dirEntry.setCompressedSize(0);
		dirEntry.setCrc(0);

		_zos.putNextEntry(dirEntry);
	}
	private void zip_addStream(InputStream _stream, String _to, ZipOutputStream _zos) throws IOException {
		_zos.putNextEntry(new ZipEntry(_to));

		byte[] bytes  = new byte[65_535];
		int    length = 0;
		while((length = _stream.read(bytes)) >= 0)
			_zos.write(bytes, 0, length);

		_zos.closeEntry();
	}

}
