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
 * @file     Recorder.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.player;

import java.io.IOException;

import fr.java.lang.properties.Timestamp;

public interface Recorder<T> /*extends Service*/ {

	public void openRecord(String filename, String formatname) throws InterruptedException, IOException;
	public void closeRecord() throws InterruptedException, IOException;

	public void addFrame(T _frame);
	public void addFrame(Timestamp _t, T _frame);

}
