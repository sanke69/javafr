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
 * @file     ObjectOutputX.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.nio.data.objects;

import java.io.IOException;
import java.io.ObjectOutput;

import fr.java.nio.data.DataOutputX;

public interface ObjectOutputX extends DataOutputX, ObjectOutput {

    public void writeObject(Object obj) throws IOException;
    
    public void write(int b) throws IOException;
    public void write(byte b[]) throws IOException;
    public void write(byte b[], int off, int len) throws IOException;

    public void flush() throws IOException;

    public void close() throws IOException;
 
}