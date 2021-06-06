package fr.java.sdk.nio.file.filters.samples;

import java.io.File;

import fr.java.sdk.nio.file.filters.FileFilter;

public enum MimeFileFilter implements FileFilter {
	image, audio, video, text, source;

	private static String[] imageExts = new String[] { ".jpeg", ".jpg", ".png", ".gif", ".bmp" };
	private static String[] audioExts = new String[] { ".mp3", ".wav", ".ogg" };
	private static String[] videoExts = new String[] { ".avi", ".mpeg", ".mp4", ".mkv", ".mov" };
	private static String[] srcExts   = new String[] { ".java", ".c", ".h", ".cpp" };
	private static String[] txtExts   = new String[] { ".txt", ".md" };
	
	@Override
	public boolean accept(File _file) {
		return test(_file);
	}
	@Override
	public boolean test(File _file) {
		switch(this) {
		case text:		return test(_file, txtExts);
		case source:	return test(_file, txtExts) || test(_file, srcExts);
		case image:		return test(_file, imageExts);
		case audio:		return test(_file, audioExts);
		case video:		return test(_file, videoExts);
		default:		throw new IllegalAccessError();
		}
	}

	private boolean test(File _file, String[] _extensions) {
		String ext = _file.getAbsolutePath().substring(_file.getAbsolutePath().lastIndexOf("."));

		for(String extension : _extensions)
			if(ext.compareTo(extension) == 0) return true;

		return false;
	}

}
