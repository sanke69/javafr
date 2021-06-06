package fr.java.sdk.file.archive.multi.tar;

import java.io.File;

public class TarUtils {
	public static final int EOF_BLOCK = 1024;
	public static final int DATA_BLOCK = 512;
	public static final int HEADER_BLOCK = 512;

	public static long calculateTarSize(File path) {
		return tarSize(path) + EOF_BLOCK;
	}

	private static long tarSize(File dir) {
		long size = 0;

		if (dir.isFile()) {
			return entrySize(dir.length());
		} else {
			File[] subFiles = dir.listFiles();

			if (subFiles != null && subFiles.length > 0) {
				for (File file : subFiles) {
					if (file.isFile()) {
						size += entrySize(file.length());
					} else {
						size += tarSize(file);
					}
				}
			} else {
				// Empty folder header
				return HEADER_BLOCK;
			}
		}

		return size;
	}

	private static long entrySize(long fileSize) {
		long size = 0;
		size += HEADER_BLOCK; // Header
		size += fileSize; // File size

		long extra = size % DATA_BLOCK;

		if (extra > 0) {
			size += (DATA_BLOCK - extra); // pad
		}

		return size;
	}

}
