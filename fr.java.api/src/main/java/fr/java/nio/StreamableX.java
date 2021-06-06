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
 * @file     StreamableX.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.nio;

import java.io.IOException;
import java.io.Serializable;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;

public interface StreamableX extends Serializable {

	void readFrom(InputStreamX in) throws IOException, ClassNotFoundException;
	void writeTo(OutputStreamX out) throws IOException;

}