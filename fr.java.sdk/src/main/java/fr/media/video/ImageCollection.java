package fr.media.video;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fr.java.media.image.ImageFormat;
import fr.java.media.sound.SoundFormat;
import fr.java.media.video.Video;
import fr.java.media.video.VideoFormat;
import fr.java.media.video.buffer.VideoFrame;
import fr.media.image.ImageCollectionFileReader;
import fr.media.image.utils.BufferedImages;

public abstract class ImageCollection<T> extends ImageCollectionFileReader implements Video<T> {
	
	public static ImageCollection<BufferedImage> from(Path _file, int _w, int _h, int _d) throws IOException {
		return new ImageCollection<BufferedImage>(_file, _w, _h, _d) {
			byte[][][] buffer;

			@Override
			public SoundFormat audio() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public ImageFormat image() {
				return null;
			}
			@Override
			public VideoFormat format() {
				return null;
			}

			@Override
			public VideoFrame<BufferedImage> 			data() {
				return readNext().get();
			}

			public Optional<VideoFrame<BufferedImage>> 	read(long _index) {
				if(_index < 0 || _index > getFrameCount())
					return Optional.empty();

				long lastIndex = getFrameIndex();

				setFrameIndex(_index);
				VideoFrame<BufferedImage> frame = VideoFrame.of( BufferedImages.createBgrImage( readImageByChannel() ) );
				setFrameIndex(lastIndex);

				return frame != null ? Optional.of(frame) : Optional.empty();
			}

			public Optional<VideoFrame<BufferedImage>> 	readNext() {
				return Optional.of( VideoFrame.of( BufferedImages.createBgrImage( readImageByChannel() ) ) );
			}

			private byte[][][] 							readImageByChannel() {
				if(buffer == null)
					buffer = new byte[getLayers()][getCols()][getRows()];
				try {
			        for(int d = 0; d < getLayers(); d++)
				        for(int j = 0; j < getCols(); j++)
				        	for(int i = 0; i < getRows(); i++)
				        		buffer[d][j][i] = (byte) readByte();
				} catch(IOException e) { return null; }
		        return buffer;
		    }

		};
	}

	public ImageCollection(Path _file, int _rows, int _cols, int _layers) throws IOException {
		super(_file, _rows, _cols, _layers);
	}

	public Long 				getFrameCount() {
		return super.getEntryCount();
	}

	public void 				setFrameIndex(long _currentFrame) {
		super.setEntryIndex(_currentFrame);
	}
	public Long 				getFrameIndex() {
		return super.getEntryIndex();
	}

}
