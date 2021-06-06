package fr.javafx.xtra.capture.processes;

import java.io.IOException;
import java.nio.file.Path;

import javafx.scene.image.Image;
import fr.javafx.utils.FxImageUtils;
import fr.javafx.xtra.capture.CaptureFxUtility;

public class SnapshotImageRecorder implements CaptureFxUtility.SnapshotProcessor {

	public enum FORMAT {
		PNG("png", "png"),
		JPEG("jpeg", "jpg");

		String format, extension;

		FORMAT(String _format, String _extension) {
			format    = _format;
			extension = _extension;
		}
		
		public String format() {
			return format;
		}
		public String extension() {
			return extension;
		}
		
	}

	private Path   captureDir;
	private FORMAT captureFormat = FORMAT.PNG;
	private String capturePrefix = "capture";

	public SnapshotImageRecorder(Path _captureDir) {
		super();
		
		captureDir = _captureDir;
	}
	
	public void initialize(int _width, int _height, double _fps) {
		captureDir.toFile().mkdirs();
	}
	public void process(Image _img, int _index) {
		try {
			FxImageUtils.saveToFile(_img, captureFormat.format(), captureDir.resolve(capturePrefix + String.format("%05d", _index) + "." + captureFormat.extension()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void finalize() {
		
	}
}
