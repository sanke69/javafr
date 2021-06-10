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

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.StreamX;
import fr.java.nio.stream.utils.PrintStreamX;

public interface UserStdStreamX {

	public static UserStdStreamX from(InputStreamX _in, OutputStreamX _out, OutputStreamX _err) {
		return new UserStdStreamX() {
			@Override public InputStreamX  getIn()   { return _in; }
			@Override public OutputStreamX getOut() { return _out; }
			@Override public OutputStreamX getErr() { return _err; }
		};
	}
	public static UserStdStreamX terminal() {
		return new UserStdStreamX() {
			@Override public InputStreamX getIn()  { return StreamX.asStreamX(System.in); }
			@Override public PrintStreamX getOut() { return StreamX.asStreamX(System.out); }
			@Override public PrintStreamX getErr() { return StreamX.asStreamX(System.err); }
		};
	}

    public InputStreamX 	getIn();
    public OutputStreamX	getOut();
    public OutputStreamX	getErr();

}
