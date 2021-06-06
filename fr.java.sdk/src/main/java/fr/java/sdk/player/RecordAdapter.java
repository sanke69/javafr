package fr.java.sdk.player;

import java.util.Optional;

import fr.java.media.Media;

public abstract class RecordAdapter<T> implements Media.Record<T> {
	protected long totalFrame, indexFrame;
	protected long startFrame, stopFrame;

	protected RecordAdapter() {
		super();
	}
	public RecordAdapter(int _frameCount) {
		super();
		setFrameCount(_frameCount);
	}

	@Override // TODO:: Must be implemented
	public Optional<Long> 	duration() {
		return Optional.empty();
	}

	@Override
	public Optional<Long> 	frameCount() {
		return totalFrame > 0 ? Optional.of(totalFrame) : Optional.empty();
	}
	protected void			setFrameCount(long _nbFrame) {
		totalFrame = _nbFrame;
	}

	@Override
	public Optional<Long> 	frameIndex() {
		return Optional.of(indexFrame);
	}
	@Override
	public boolean		 	setIndex(long _currentFrame) {
		indexFrame = _currentFrame;
		return true;
	}
	public boolean		 	increaseIndex() {
		if(indexFrame++ < totalFrame)
			return true;
		indexFrame = totalFrame - 1;
		return false;
	}

	@Override
	public Optional<Long> 	frameStart() {
		return startFrame > 0 ? Optional.of(startFrame) : Optional.empty();
	}
	@Override
	public boolean 			setStart(long _start) {
		if(_start < 0 || _start >= totalFrame)
			throw new IllegalArgumentException("_start is out of range [0, " + (totalFrame-1) + "]");
		startFrame = _start;
		return true;
	}

	@Override
	public Optional<Long> 	frameStop() {
		return stopFrame > 0 ? Optional.of(stopFrame) : Optional.empty();
	}
	@Override
	public boolean 			setStop(long _stop) {System.out.println(_stop);
		if(_stop < startFrame || _stop >= totalFrame)
			throw new IllegalArgumentException("_stop is out of range [" + startFrame + ", " + (totalFrame-1) + "], value is: " + _stop);
		stopFrame = _stop;
		return true;
	}

	@Override
	public boolean 			setInterval(long _start, long _stop) {
		if( setStart(_start) )
			return setStop(_stop);
		return false;
	}

}
