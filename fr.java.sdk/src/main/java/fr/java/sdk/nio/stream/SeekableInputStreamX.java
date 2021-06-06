package fr.java.sdk.nio.stream;

import java.io.IOException;
import java.io.InputStream;

import fr.java.nio.Seekable;
import fr.java.nio.stream.InputStreamX;

public class SeekableInputStreamX extends PeekableInputStreamX implements Seekable, InputStreamX {

	public SeekableInputStreamX(InputStreamX _isx) {
		super(_isx);
	}
	public SeekableInputStreamX(InputStream _is) {
		super(_is);
	}

	@Override
	public int indexOf(byte _magicWord) throws IOException {
		int i = 0;
		while(peek(i) != -1) {
			if(peek(i) == _magicWord)
				return i;
			i++;
		}
		return -1;
	}
	@Override
	public int indexOf(byte _magicWord, int _maxDepth) throws IOException {
		for(int i = 0; i < _maxDepth && peek(i) != -1; ++i)
			if(peek(i) == _magicWord)
				return i;
		return -1;
	}
	@Override
	public int indexOf(byte[] _magicWord) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(byte[] _magicWord, int _maxDepth) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(short _magicWord) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(short _magicWord, int _maxDepth) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(int _magicWord) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(int _magicWord, int _maxDepth) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(long _magicWord) throws IOException {
		return 0;
	}
	@Override
	public int indexOf(long _magicWord, int _maxDepth) throws IOException {
		return 0;
	}

}
