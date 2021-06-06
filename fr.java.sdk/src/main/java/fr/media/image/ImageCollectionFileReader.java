package fr.media.image;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fr.java.lang.enums.PixelFormat;
import fr.java.media.image.ImageFormat;
import fr.java.sdk.io.file.binary.collections.BinaryCollectionFileReader;

public class ImageCollectionFileReader extends BinaryCollectionFileReader implements ImageFormat {
	int 	layers, cols, rows;
	boolean readByChannel = false;

	public ImageCollectionFileReader(Path _file, int _rows, int _cols, int _layers) throws IOException {
		super(_file, _layers * _rows * _cols);
		layers = _layers;
		cols   = _cols;
		rows   = _rows;
	}
	public ImageCollectionFileReader(final ImageCollectionFileReader _src) throws IOException {
		super(_src);
		layers = _src.layers;
		cols   = _src.cols;
		rows   = _src.rows;
		readByChannel = _src.readByChannel;
	}

	@Override public Optional<Integer> 	getMagicNumber() 	{ return Optional.empty(); }
	@Override public Optional<Integer> 	getHeaderSize()  	{ return Optional.empty(); }
	@Override public long              	getEntryCount()  	{ try { return getLength() / getEntryLength(); } catch(IOException e) { return 0; } }

	@Override public PixelFormat 		getPixelFormat() 	{ return PixelFormat.PXF_RGB8; }

	@Override public int 				getHeight() 		{ return rows; }
	public int 							getRows()   		{ return rows; }

	@Override public int 				getWidth() 			{ return cols; }
    public int 							getCols() 			{ return cols; }

	@Override public int 				getDepth() 			{ return layers; }
    public int 							getLayers() 		{ return layers; }

	@Override
	public ImageCollectionFileReader clone() {
		try {
			return new ImageCollectionFileReader(this);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int readHeader() throws IOException {
		return -1;
	}

}
