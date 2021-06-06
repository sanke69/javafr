package fr.java.sdk.file.archive.multi.tar;

import java.io.File;
import java.util.Date;

public class TarEntry {

	public static class Header {
		/**
		 * Header
		 * 
		 * <pre>
		 * Offset  Size     Field
		 * 0       100      File name
		 * 100     8        File mode
		 * 108     8        Owner's numeric user ID
		 * 116     8        Group's numeric user ID
		 * 124     12       File size in bytes
		 * 136     12       Last modification time in numeric Unix time format
		 * 148     8        Checksum for header block
		 * 156     1        Link indicator (file type)
		 * 157     100      Name of linked file
		 * </pre>
		 * 
		 * 
		 * File Types
		 * 
		 * <pre>
		 * Value        Meaning
		 * '0'          Normal file
		 * (ASCII NUL)  Normal file (now obsolete)
		 * '1'          Hard link
		 * '2'          Symbolic link
		 * '3'          Character special
		 * '4'          Block special
		 * '5'          Directory
		 * '6'          FIFO
		 * '7'          Contigous
		 * </pre>
		 * 
		 * 
		 * 
		 * Ustar header
		 * 
		 * <pre>
		 * Offset  Size    Field
		 * 257     6       UStar indicator "ustar"
		 * 263     2       UStar version "00"
		 * 265     32      Owner user name
		 * 297     32      Owner group name
		 * 329     8       Device major number
		 * 337     8       Device minor number
		 * 345     155     Filename prefix
		 * </pre>
		 */

		/*
		 * Header
		 */
		public static final int  NAMELEN    = 100;
		public static final int  MODELEN    = 8;
		public static final int  UIDLEN     = 8;
		public static final int  GIDLEN     = 8;
		public static final int  SIZELEN    = 12;
		public static final int  MODTIMELEN = 12;
		public static final int  CHKSUMLEN  = 8;
		public static final byte LF_OLDNORM = 0;

		/*
		 * File Types
		 */
		public static final byte LF_NORMAL  = (byte) '0';
		public static final byte LF_LINK    = (byte) '1';
		public static final byte LF_SYMLINK = (byte) '2';
		public static final byte LF_CHR     = (byte) '3';
		public static final byte LF_BLK     = (byte) '4';
		public static final byte LF_DIR     = (byte) '5';
		public static final byte LF_FIFO    = (byte) '6';
		public static final byte LF_CONTIG  = (byte) '7';

		/*
		 * File Modes
		 */
		public static final int  DIR_MOD    = 040755;
		public static final int  FILE_MOD   = 0100644;
		
		/*
		 * Ustar header
		 */
		public static final int  MAGICLEN   = 8;
		/**
		 * The magic tag representing a POSIX tar archive.
		 */
		public static final String TMAGIC   = "ustar";
		/**
		 * The magic tag representing a GNU tar archive.
		 */
		public static final String GNU_TMAGIC = "ustar  ";

		public static final int UNAMELEN    = 32;
		public static final int GNAMELEN    = 32;
		public static final int DEVLEN      = 8;

		// Header values
		public StringBuffer name;
		public int          mode;
		public int          userId;
		public int          groupId;
		public long         size;
		public long         modTime;
		public int          checkSum;
		public byte         linkFlag;
		public StringBuffer linkName;
		public StringBuffer magic;
		public StringBuffer userName;
		public StringBuffer groupName;
		public int          devMajor;
		public int          devMinor;

		public Header() {
			super();
			magic    = new StringBuffer(Header.TMAGIC);

			name     = new StringBuffer();
			linkName = new StringBuffer();

			String user = System.getProperty("user.name", "");

			if(user.length() > UNAMELEN - 1)
				user = user.substring(0, UNAMELEN - 1);

			userId    = 0;
			groupId   = 0;
			userName  = new StringBuffer(user);
			groupName = new StringBuffer("");
		}

		/**
		 * Parse an entry name from a header buffer.
		 * 
		 * @param name
		 * @param _header The header buffer from which to parse.
		 * @param _offset The offset into the buffer from which to parse.
		 * @param _length The number of header bytes to parse.
		 * @return The header's entry name.
		 */
		public static StringBuffer parseName(byte[] _header, int _offset, int _length) {
			StringBuffer result = new StringBuffer(_length);

			int end = _offset + _length;
			for(int i = _offset; i < end; ++i) {
				if(_header[i] == 0)
					break;
				result.append((char) _header[i]);
			}

			return result;
		}

		/**
		 * Determine the number of bytes in an entry name.
		 * 
		 * @param _name
		 * @param header The header buffer from which to parse.
		 * @param _offset The offset into the buffer from which to parse.
		 * @param _length The number of header bytes to parse.
		 * @return The number of bytes in a header's entry name.
		 */
		public static int getNameBytes(StringBuffer _name, byte[] _buffer, int _offset, int _length) {
			int i;

			for(i = 0; i < _length && i < _name.length(); ++i)
				_buffer[_offset + i] = (byte) _name.charAt(i);

			for(; i < _length; ++i)
				_buffer[_offset + i] = 0;

			return _offset + _length;
		}

	}

	public static class Octal {

		/**
		 * Parse an octal string from a header buffer. This is used for the file
		 * permission mode value.
		 * 
		 * @param _headerBuf The header buffer from which to parse.
		 * @param _offset The offset into the buffer from which to parse.
		 * @param _length The number of header bytes to parse.
		 * 
		 * @return The long value of the octal string.
		 */
		public static long parseOctal(byte[] _headerBuf, int _offset, int _length) {
			long    result       = 0;
			boolean stillPadding = true;

			int end = _offset + _length;
			for(int i = _offset; i < end; ++i) {
				if(_headerBuf[i] == 0)
					break;

				if(_headerBuf[i] == (byte) ' ' || _headerBuf[i] == '0') {
					if(stillPadding)
						continue;

					if(_headerBuf[i] == (byte) ' ')
						break;
				}

				stillPadding = false;
				result       = (result << 3) + (_headerBuf[i] - '0');
			}

			return result;
		}

		/**
		 * Parse an octal integer from a header buffer.
		 * 
		 * @param _value
		 * @param _buffer    The header buffer from which to parse.
		 * @param _offset The offset into the buffer from which to parse.
		 * @param _length The number of header bytes to parse.
		 * 
		 * @return The integer value of the octal bytes.
		 */
		public static int getOctalBytes(long _value, byte[] _buffer, int _offset, int _length) {		// TODO:: Change It !!!
			int idx = _length - 1;

			_buffer[_offset + idx] = 0;
			--idx;
			_buffer[_offset + idx] = (byte) ' ';
			--idx;

			if (_value == 0) {
				_buffer[_offset + idx] = (byte) '0';
				--idx;
			} else {
				for(long val = _value; idx >= 0 && val > 0; --idx) {
					_buffer[_offset + idx] = (byte) ((byte) '0' + (byte) (val & 7));
					val = val >> 3;
				}
			}

			for(; idx >= 0; --idx)
				_buffer[_offset + idx] = (byte) ' ';

			return _offset + _length;
		}

		/**
		 * Parse the checksum octal integer from a header buffer.
		 * 
		 * @param _value
		 * @param _buffer    The header buffer from which to parse.
		 * @param _offset The offset into the buffer from which to parse.
		 * @param _length The number of header bytes to parse.
		 * @return The integer value of the entry's checksum.
		 */
		public static int getCheckSumOctalBytes(long _value, byte[] _buffer, int _offset, int _length) {
			getOctalBytes(_value, _buffer, _offset, _length);
			_buffer[_offset + _length - 1] = (byte) ' ';
			_buffer[_offset + _length - 2] = 0;
			return _offset + _length;
		}

		/**
		 * Parse an octal long integer from a header buffer.
		 * 
		 * @param _value
		 * @param _buffer    The header buffer from which to parse.
		 * @param _offset The offset into the buffer from which to parse.
		 * @param _length The number of header bytes to parse.
		 * 
		 * @return The long value of the octal bytes.
		 */
		public static int getLongOctalBytes(long _value, byte[] _buffer, int _offset, int _length) {
			byte[] temp = new byte[_length + 1];
			getOctalBytes(_value, temp, 0, _length + 1);
			System.arraycopy(temp, 0, _buffer, _offset, _length);
			return _offset + _length;
		}

	}

	protected Header header;
	protected File 	 file;

	private TarEntry() {
		super();
		file   = null;
		header = new Header();
	}
	// For TarArchiver
	public  TarEntry(File _file, String _entryName) {
		this();
		file = _file;
		extractTarHeader(_entryName);
	}
	public  TarEntry(byte[] _headerBuf) {
		this();
		parseTarHeader(_headerBuf);
	}

	public Header 	getHeader() {
		return header;
	}
	public File 	getFile() {
		return file;
	}

	public void 	setName(String _name) {
		header.name = new StringBuffer(_name);
	}
	public String 	getName() {
		return header.name.toString();
	}

	public void 	setIds(int _userId, int _groupId) {
		setUserId(_userId);
		setGroupId(_groupId);
	}

	public void 	setUserId(int _userId) {
		header.userId = _userId;
	}
	public int 		getUserId() {
		return header.userId;
	}

	public void 	setGroupId(int _groupId) {
		header.groupId = _groupId;
	}
	public int 		getGroupId() {
		return header.groupId;
	}

	public void 	setUserName(String _userName) {
		header.userName = new StringBuffer(_userName);
	}
	public String 	getUserName() {
		return header.userName.toString();
	}

	public void 	setGroupName(String _groupName) {
		header.groupName = new StringBuffer(_groupName);
	}
	public String 	getGroupName() {
		return header.groupName.toString();
	}

	public void 	setModTime(long _time) {
		header.modTime = _time / 1000;
	}
	public void 	setModTime(Date _time) {
		header.modTime = _time.getTime() / 1000;
	}
	public Date 	getModTime() {
		return new Date(header.modTime * 1000);
	}

	public void 	setSize(long _size) {
		header.size = _size;
	}
	public long 	getSize() {
		return header.size;
	}

	public boolean 	isDirectory() {
		if(file != null)
			return file.isDirectory();

		if(header != null) {
			if(header.linkFlag == Header.LF_DIR)
				return true;

			if(header.name.toString().endsWith("/"))
				return true;
		}

		return false;
	}
	public boolean 	isDescendent(TarEntry _entry) {
		return _entry.header.name.toString().startsWith(header.name.toString());
	}

	/**
	 * Extract header from File
	 * 
	 * @param _entryName
	 */
	public void 	extractTarHeader(String _entryName) {
		String name = _entryName;

		name = name.replace(File.separatorChar, '/');

		if(name.startsWith("/"))
			name = name.substring(1);

		header.linkName = new StringBuffer("");
		header.name     = new StringBuffer(name);

		if(file.isDirectory()) {
			header.mode     = Header.DIR_MOD;
			header.linkFlag = Header.LF_DIR;
			if(header.name.charAt(header.name.length() - 1) != '/') {
				header.name.append("/");
			}
			header.size = 0;
		} else {
			header.size     = file.length();
			header.mode     = Header.FILE_MOD;
			header.linkFlag = Header.LF_NORMAL;
		}

		header.modTime = file.lastModified() / 1000;
		header.checkSum = 0;
		header.devMajor = 0;
		header.devMinor = 0;
	}

	/**
	 * Parses the tar header to the byte buffer
	 * 
	 * @param header
	 * @param _headerBuf
	 */
	public void 	parseTarHeader(byte[] _headerBuf) {
		int offset = 0;

		header.name = Header.parseName(_headerBuf, offset, Header.NAMELEN);
		offset += Header.NAMELEN;

		header.mode = (int) Octal.parseOctal(_headerBuf, offset, Header.MODELEN);
		offset += Header.MODELEN;

		header.userId = (int) Octal.parseOctal(_headerBuf, offset, Header.UIDLEN);
		offset += Header.UIDLEN;

		header.groupId = (int) Octal.parseOctal(_headerBuf, offset, Header.GIDLEN);
		offset += Header.GIDLEN;

		header.size = Octal.parseOctal(_headerBuf, offset, Header.SIZELEN);
		offset += Header.SIZELEN;

		header.modTime = Octal.parseOctal(_headerBuf, offset, Header.MODTIMELEN);
		offset += Header.MODTIMELEN;

		header.checkSum = (int) Octal.parseOctal(_headerBuf, offset, Header.CHKSUMLEN);
		offset += Header.CHKSUMLEN;

		header.linkFlag = _headerBuf[offset++];

		header.linkName = Header.parseName(_headerBuf, offset, Header.NAMELEN);
		offset += Header.NAMELEN;

		header.magic = Header.parseName(_headerBuf, offset, Header.MAGICLEN);
		offset += Header.MAGICLEN;

		header.userName = Header.parseName(_headerBuf, offset, Header.UNAMELEN);
		offset += Header.UNAMELEN;

		header.groupName = Header.parseName(_headerBuf, offset, Header.GNAMELEN);
		offset += Header.GNAMELEN;

		header.devMajor = (int) Octal.parseOctal(_headerBuf, offset, Header.DEVLEN);
		offset += Header.DEVLEN;

		header.devMinor = (int) Octal.parseOctal(_headerBuf, offset, Header.DEVLEN);
	}

	/**
	 * Writes the header to the byte buffer
	 * 
	 * @param _buffer
	 */
	public void 	writeEntryHeader(byte[] _buffer) {
		int offset = 0;

		offset = Header.getNameBytes(header.name,        _buffer, offset, Header.NAMELEN);
		offset = Octal.getOctalBytes(header.mode,        _buffer, offset, Header.MODELEN);
		offset = Octal.getOctalBytes(header.userId,      _buffer, offset, Header.UIDLEN);
		offset = Octal.getOctalBytes(header.groupId,     _buffer, offset, Header.GIDLEN);

		long size = header.size;

		offset = Octal.getLongOctalBytes(size,           _buffer, offset, Header.SIZELEN);
		offset = Octal.getLongOctalBytes(header.modTime, _buffer, offset, Header.MODTIMELEN);

		int csOffset = offset;
		for (int c = 0; c < Header.CHKSUMLEN; ++c)
			_buffer[offset++] = (byte) ' ';
		_buffer[offset++] = header.linkFlag;

		offset = Header.getNameBytes(header.linkName,    _buffer, offset, Header.NAMELEN);
		offset = Header.getNameBytes(header.magic,       _buffer, offset, Header.MAGICLEN);
		offset = Header.getNameBytes(header.userName,    _buffer, offset, Header.UNAMELEN);
		offset = Header.getNameBytes(header.groupName,   _buffer, offset, Header.GNAMELEN);
		offset = Octal.getOctalBytes(header.devMajor,    _buffer, offset, Header.DEVLEN);
		offset = Octal.getOctalBytes(header.devMinor,    _buffer, offset, Header.DEVLEN);

		for(; offset < _buffer.length;)
			_buffer[offset++] = 0;

		long checkSum = computeCheckSum(_buffer);

		Octal.getCheckSumOctalBytes(checkSum, _buffer, csOffset, Header.CHKSUMLEN);
	}

	/**
	 * Calculate checksum
	 * 
	 * @param _buffer
	 * @return
	 */
	public long 	computeCheckSum(byte[] _buffer) {
		long sum = 0;
		for (int i = 0; i < _buffer.length; ++i)
			sum += 255 & _buffer[i];

		return sum;
	}

	public boolean 	equals(Object _o) {
		if(_o instanceof TarEntry)
			return equals((TarEntry) _o);
		return false;
	}
	public boolean 	equals(TarEntry _it) {
		return header.name.toString().equals(_it.header.name.toString());
	}

}