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
package fr.java.nio.stream.std;

import java.io.InputStream;
import java.io.OutputStream;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.StreamX;

public interface StdStreamX {

	public static StdStreamX from(OutputStreamX _in, InputStreamX _out, InputStreamX _err) {
		return new StdStreamX() {
			@Override public OutputStreamX getIn()  { return _in; }
			@Override public InputStreamX  getOut() { return _out; }
			@Override public InputStreamX  getErr() { return _err; }
		};
	}
	public static StdStreamX from(OutputStream _in, InputStream _out, InputStream _err) {
		return new StdStreamX() {
			final OutputStreamX in  = StreamX.asStreamX(_in);
			final InputStreamX  out = StreamX.asStreamX(_out);
			final InputStreamX  err = StreamX.asStreamX(_err);
			@Override public OutputStreamX getIn()  { return in; }
			@Override public InputStreamX  getOut() { return out; }
			@Override public InputStreamX  getErr() { return err; }
		};
	}

    public OutputStreamX getIn();
    public InputStreamX	getOut();
    public InputStreamX	getErr();

}
