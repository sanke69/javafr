package fr.java.sdk.security;

import java.security.Key;
import java.util.Arrays;

import fr.java.security.Cypher;

public abstract class AbstractCypher implements Cypher {

	private boolean	decrypt	= false;
	private int		keySize, blockSize;

	public AbstractCypher(int _keySize) {
		super();
		keySize = _keySize;
	}

	public byte[] encrypt(byte[] _data, Key _public) {
		return encrypt(_data, 0, _data.length, _public);
	}

	public byte[] encrypt(byte[] _data, int _offset, int _count, Key _public) {
		setMagicConstant(_public, false);

		int    szData = _count, beg = _offset, retIndex = 0, retSize = 0;
		byte[] cipher = new byte[2 * magicRatio * _data.length], block, encrypted;

		while(szData >= magicStep) {
			block     = Arrays.copyOfRange(_data, beg, beg + magicStep);
			encrypted = encode(block, _public);
//			encrypted = enchant(encrypted);

			System.arraycopy(encrypted, 0, cipher, retIndex, encrypted.length);

			retIndex += encrypted.length;
			retSize  += encrypted.length;
			beg      += magicStep;
			szData   -= magicStep;
		}

		if(szData != 0) {
			block     = Arrays.copyOfRange(_data, beg, beg + szData);
			encrypted = encode(block, _public);
//			encrypted = enchant(encrypted);

			System.arraycopy(encrypted, 0, cipher, retIndex, encrypted.length);

			retSize += encrypted.length;
		}

		return Arrays.copyOfRange(cipher, 0, retSize);
	}

	public byte[] decrypt(byte[] _data, Key _public) {
		return encrypt(_data, 0, _data.length, _public);
	}

	public byte[] decrypt(byte[] _cypher, int _offset, int _count, Key _private) {
		setMagicConstant(_private, true);

		int size = _count, beg = _offset, retIndex = 0, retSize = 0;
		byte[] plain = new byte[2 * _cypher.length], block, decrypted = null;

		while(size >= magicStep) {
			block = Arrays.copyOfRange(_cypher, beg, beg + magicStep);
//			block = desenchant(block);
			decrypted = decode(block, _private);

			System.arraycopy(decrypted, 0, plain, retIndex, decrypted.length);

			retIndex += decrypted.length;
			retSize += decrypted.length;
			beg += magicStep;
			size -= magicStep;
		}

		if(size != 0) {
			block = Arrays.copyOfRange(_cypher, beg, beg + magicStep);

			byte[] buffer = decode(block, _private);
			System.arraycopy(decrypted, 0, plain, retIndex, buffer.length);

			size -= blockSize;
			retSize += buffer.length;
			beg += blockSize;
		}


		return Arrays.copyOfRange(plain, 0, retSize);
	}

	protected byte[] addOneByte(byte[] input) {
		byte[] result = new byte[input.length + 1];
		result[0] = 1;
		System.arraycopy(input, 0, result, 1, input.length);
		return result;
	}
	protected byte[] removeOneByte(byte[] input) {
		return Arrays.copyOfRange(input, 1, input.length);
	}

	protected void pad(byte[] _in, int _offset, int _length) {
		if(_in == null)
			return;

		if((_offset + _length) > _in.length)
			throw new IllegalArgumentException("Buffer too small to hold padding");

		byte paddingOctet = (byte) (_length & 0xff);

		Arrays.fill(_in, _offset, _length, paddingOctet);
	}
	protected int  unpad(byte[] _in, int _offset, int _length) {
		if((_in == null) || (_length == 0))
			return 0;

		byte lastByte = _in[_offset + _length - 1];

		int padValue = (int) lastByte & 0x0ff;
		if((padValue < 0x01) || (padValue > blockSize))
			return -1;

		int start = _offset + _length - ((int) lastByte & 0x0ff);
		if(start < _offset)
			return -1;

		for(int i = 0; i < ((int) lastByte & 0x0ff); i++)
			if(_in[start + i] != lastByte)
				return -1;

		return start;
	}

	protected final boolean onlyPositiveValue = true;
	private final   boolean noMagic = true;
	private int			    magicStep, magicRatio, magicPad;

	public byte[] enchant(byte[] _in) {
		if(noMagic)
			return _in;

		int neededPad = blockSize + magicPad - _in.length;

		byte[] enchanted = new byte[blockSize + magicPad];
		for(int i = 0; i < neededPad; ++i)
			enchanted[i] = (byte) (neededPad & 0xFF);

		// System.arraycopy(_in, 0, enchanted, neededPad > 0 ? neededPad : 0, _in.length < enchanted.length ? _in.length : enchanted.length);
		System.arraycopy(_in, 0, enchanted, neededPad, _in.length);

		return enchanted;
	}
	public byte[] desenchant(byte[] _in) {
		return noMagic ? _in : Arrays.copyOfRange(_in, _in[0], _in.length);
	}

	private void setMagicConstant(Key _key, boolean _decrypt) {
		decrypt   = _decrypt;
		blockSize = _decrypt ? keySize/8 : 88;

		if(noMagic) {
			magicRatio = 1; // [1..4..]
			magicStep  = blockSize / magicRatio;
		} else {
			magicRatio = 1; // [1..4..]
			magicStep  = !decrypt ? blockSize / magicRatio : blockSize + magicPad;
			magicPad   = 0;
		}
	}

}
