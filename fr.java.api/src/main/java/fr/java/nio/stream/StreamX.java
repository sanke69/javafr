/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.nio.stream;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

import fr.java.nio.stream.utils.PrintStreamX;

public interface StreamX extends Closeable {

	public static InputStream   asStreamJava(InputStreamX _xStream) {
		return new InputStream() {

			@Override
			public int 		read() throws IOException {
				return _xStream.read();
			}
			@Override
		    public int 		read(byte b[]) throws IOException {
				return _xStream.read(b);
			}
			@Override
		    public int 		read(byte b[], int off, int len) throws IOException {
				return _xStream.read(b, off, len);
			}

			@Override
		    public long 	skip(long n) throws IOException {
				return _xStream.skip(n);
			}

			@Override
		    public int 		available() throws IOException {
				return _xStream.available();
			}

			@Override
		    public boolean 	markSupported() {
				return _xStream.markSupported();
			}
			@Override
		    public void 	mark(int readlimit) {
				_xStream.mark(readlimit);
			}

			@Override
		    public void 	reset() throws IOException {
				_xStream.reset();
			}

			@Override
		    public void 	close() throws IOException {
				_xStream.close();
			}

		};
	}
	public static InputStreamX  asStreamX(InputStream _xStream) {
		return new InputStreamX() {

			@Override
		    public int 		available() throws IOException {
				return _xStream.available();
			}

			@Override
			public int 		read() throws IOException {
				return _xStream.read();
			}
			@Override
		    public int 		read(byte b[]) throws IOException {
				return _xStream.read(b);
			}
			@Override
		    public int 		read(byte b[], int off, int len) throws IOException {
				return _xStream.read(b, off, len);
			}

			@Override
		    public long 	skip(long n) throws IOException {
				return _xStream.skip(n);
			}

			@Override
		    public boolean 	markSupported() {
				return _xStream.markSupported();
			}
			@Override
		    public void 	mark(int readlimit) {
				_xStream.mark(readlimit);
			}

			@Override
		    public void 	reset() throws IOException {
				_xStream.reset();
			}

			@Override
		    public void 	close() throws IOException {
				_xStream.close();
			}

		};
	}

	public static OutputStream  asStreamJava(OutputStreamX _xStream) {
		return new OutputStream() {

			@Override
		    public void 	write(int b) throws IOException {
				_xStream.write(b);
			}
			@Override
		    public void 	write(byte b[]) throws IOException {
				_xStream.write(b);
			}
			@Override
		    public void 	write(byte b[], int off, int len) throws IOException {
				_xStream.write(b, off, len);
			}

			@Override
		    public void 	flush() throws IOException {
				_xStream.flush();
			}

			@Override
		    public void 	close() throws IOException {
				_xStream.close();
			}

		};
	}
	public static OutputStreamX asStreamX(OutputStream _xStream) {
		return new OutputStreamX() {

			@Override
		    public void 	write(int b) throws IOException {
				_xStream.write(b);
			}
			@Override
		    public void 	write(byte b[]) throws IOException {
				_xStream.write(b);
			}
			@Override
		    public void 	write(byte b[], int off, int len) throws IOException {
				_xStream.write(b, off, len);
			}

			@Override
		    public void 	flush() throws IOException {
				_xStream.flush();
			}

			@Override
		    public void 	close() throws IOException {
				_xStream.close();
			}

		};
	}

	public static PrintStream   asStreamJava(PrintStreamX _xStream) {
		return new PrintStream(new ByteArrayOutputStream() /*(OutputStream) null*/) { // TODO:: HACK:: Must be returned as 

			@Override
		    public void 		write(int b) { _xStream.write(b); }
			@Override
		    public void 		write(byte b[]) throws IOException { _xStream.write(b); }
			@Override
		    public void 		write(byte b[], int off, int len) { _xStream.write(b, off, len); }

			@Override
		    public void 		flush() { _xStream.flush(); }

			@Override
		    public void 		close() { _xStream.close(); }

		    public boolean 		checkError() 										{ return _xStream.checkError(); }

		    public void 		print(boolean b) 									{ _xStream.print(b); }
		    public void 		print(char c) 										{ _xStream.print(c); }
		    public void 		print(int i) 										{ _xStream.print(i); }
		    public void 		print(long l) 										{ _xStream.print(l); }
		    public void 		print(float f) 										{ _xStream.print(f); }
		    public void 		print(double d) 									{ _xStream.print(d); }
		    public void 		print(char s[]) 									{ _xStream.print(s); }
		    public void 		print(String s) 									{ _xStream.print(s); }
		    public void 		print(Object obj) 									{ _xStream.print(obj); }

		    public void 		println() 											{ _xStream.println(); }
		    public void 		println(boolean b)									{ _xStream.println(b); }
		    public void 		println(char c)										{ _xStream.println(c); }
		    public void 		println(int i)										{ _xStream.println(i); }
		    public void 		println(long l)										{ _xStream.println(l); }
		    public void 		println(float f)									{ _xStream.println(f); }
		    public void 		println(double d)									{ _xStream.println(d); }
		    public void 		println(char s[])									{ _xStream.println(s); }
		    public void 		println(String s)									{ _xStream.println(s); }
		    public void 		println(Object obj)									{ _xStream.println(obj); }

		    public PrintStream 	printf(String format, Object ... args)				{ _xStream.printf(format, args); return this; }
		    public PrintStream 	printf(Locale l, String format, Object ... args)	{ _xStream.printf(l, format, args); return this; }

		    public PrintStream 	format(String format, Object ... args)				{ _xStream.format(format, args); return this; }
		    public PrintStream 	format(Locale l, String format, Object ... args)	{ _xStream.format(l, format, args); return this; }

		    public PrintStream 	append(CharSequence csq)							{ _xStream.append(csq); return this; }
		    public PrintStream 	append(CharSequence csq, int start, int end)		{ _xStream.append(csq); return this; }
		    public PrintStream 	append(char c)										{ _xStream.append(c); return this; }

		};
	}
	public static PrintStreamX  asStreamX(PrintStream _jStream) {
		return new PrintStreamX() {

			@Override
		    public void 		write(int b) { _jStream.write(b); }
			@Override
		    public void 		write(byte b[]) throws IOException { _jStream.write(b); }
			@Override
		    public void 		write(byte b[], int off, int len) { _jStream.write(b, off, len); }

			@Override
		    public void 		flush() { _jStream.flush(); }

			@Override
		    public void 		close() { _jStream.close(); }

		    public boolean 		checkError() 										{ return _jStream.checkError(); }

		    public void 		print(boolean b) 									{ _jStream.print(b); }
		    public void 		print(char c) 										{ _jStream.print(c); }
		    public void 		print(int i) 										{ _jStream.print(i); }
		    public void 		print(long l) 										{ _jStream.print(l); }
		    public void 		print(float f) 										{ _jStream.print(f); }
		    public void 		print(double d) 									{ _jStream.print(d); }
		    public void 		print(char s[]) 									{ _jStream.print(s); }
		    public void 		print(String s) 									{ _jStream.print(s); }
		    public void 		print(Object obj) 									{ _jStream.print(obj); }

		    public void 		println() 											{ _jStream.println(); }
		    public void 			println(boolean b)									{ _jStream.println(b); }
		    public void 			println(char c)										{ _jStream.println(c); }
		    public void 			println(int i)										{ _jStream.println(i); }
		    public void 			println(long l)										{ _jStream.println(l); }
		    public void 			println(float f)									{ _jStream.println(f); }
		    public void 			println(double d)									{ _jStream.println(d); }
		    public void 			println(char s[])									{ _jStream.println(s); }
		    public void 			println(String s)									{ _jStream.println(s); }
		    public void 			println(Object obj)									{ _jStream.println(obj); }

		    public PrintStreamX 	printf(String format, Object ... args)				{ _jStream.printf(format, args); return this; }
		    public PrintStreamX 	printf(Locale l, String format, Object ... args)	{ _jStream.printf(l, format, args); return this; }

		    public PrintStreamX 	format(String format, Object ... args)				{ _jStream.format(format, args); return this; }
		    public PrintStreamX 	format(Locale l, String format, Object ... args)	{ _jStream.format(l, format, args); return this; }

		    public PrintStreamX 	append(CharSequence csq)							{ _jStream.append(csq); return this; }
		    public PrintStreamX 	append(CharSequence csq, int start, int end)		{ _jStream.append(csq); return this; }
		    public PrintStreamX 	append(char c)										{ _jStream.append(c); return this; }

		};
	}

}
