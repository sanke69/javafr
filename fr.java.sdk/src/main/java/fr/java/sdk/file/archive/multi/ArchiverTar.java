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

import fr.java.file.archive.Archive;
import fr.java.sdk.file.archive.multi.tar.TarEntry;
import fr.java.sdk.file.archive.multi.tar.TarOutputStream;
import fr.java.sdk.lang.tuples.SimplePair;

public class ArchiverTar extends AbstractArchiver {

	public ArchiverTar() {
		super();
	}

	@Override
	public Archive toArchive() throws IOException {
		List<SimplePair<String, Path>> fileList = orderedContentList();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		TarOutputStream       tos  = new TarOutputStream(baos);
		
		for(SimplePair<String, Path> fe : fileList) {
			String entry = fe.getFirst();
			Path   file  = fe.getSecond();

			if(file.toFile().isDirectory() && file.toFile().listFiles().length == 0)
				tar_addEmptyDirectory(entry, tos);
			else
				tar_addFile(file, entry, tos);
		}

		for(Map.Entry<String, InputStream> se : streamMap().entrySet()) {
			String      entry = se.getKey();
			InputStream is    = se.getValue();

			tar_addStream(is, entry, tos);
		}

		return new ArchiveTar(new ByteArrayInputStream(baos.toByteArray()));
	}

	@Override
	public void writeTo(Path _dst) throws IOException {
		List<SimplePair<String, Path>> fileList = orderedContentList();

		try(FileOutputStream fos = new FileOutputStream(_dst.toFile())) {	// TODO:: maybe already closed with zos...
			try(TarOutputStream zos = new TarOutputStream(fos)) {
				for(SimplePair<String, Path> fe : fileList) {
					String entry = fe.getFirst();
					Path   file  = fe.getSecond();

					if(file.toFile().isDirectory() && file.toFile().listFiles().length == 0)
						tar_addEmptyDirectory(entry, zos);
					else
						tar_addFile(file, entry, zos);
				}

				for(Map.Entry<String, InputStream> se : streamMap().entrySet()) {
					String      entry = se.getKey();
					InputStream is    = se.getValue();

					tar_addStream(is, entry, zos);
				}
			}
		}
	}

	private void tar_addFile(Path _from, String _to, TarOutputStream _tos) throws IOException {
		FileInputStream fis      = new FileInputStream(_from.toFile());
		TarEntry        tarEntry = new TarEntry(_from.toFile(), _to);

		byte[] bytes = new byte[65535];
		int length;

		_tos.putNextEntry(tarEntry);
		while((length = fis.read(bytes)) >= 0) {
			_tos.write(bytes, 0, length);
		}
//		_tos.closeEntry();

		fis.close();
	}
	private void tar_addEmptyDirectory(String _to, TarOutputStream _tos) throws IOException {
		TarEntry dirEntry = new TarEntry(null, _to);
		dirEntry.setSize(0);

		_tos.putNextEntry(dirEntry);
	}
	private void tar_addStream(InputStream _stream, String _to, TarOutputStream _tos) throws IOException {
		TarEntry tarEntry = new TarEntry(null, _to);

		byte[] bytes = new byte[65535];
		int length;

		_tos.putNextEntry(tarEntry);
		while((length = _stream.read(bytes)) >= 0)
			_tos.write(bytes, 0, length);
//		_tos.closeEntry();
	}

}
