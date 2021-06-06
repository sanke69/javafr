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
 * @file     NotSupportedException.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.lang.exceptions;

public class NotSupportedException extends RuntimeException {
	private static final long serialVersionUID = -6443975500617681223L;

	public NotSupportedException() {
		super("Not Supported");
	}
	public NotSupportedException(String _msg) {
		super(_msg);
	}
	public NotSupportedException(Exception _e) {
		super("Not Supported", _e);
	}
	public NotSupportedException(String _msg, Exception _e) {
		super(_msg, _e);
	}
	
}