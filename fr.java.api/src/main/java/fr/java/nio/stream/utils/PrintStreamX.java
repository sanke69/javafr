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
package fr.java.nio.stream.utils;

import java.io.IOException;
import java.util.Locale;

import fr.java.nio.stream.OutputStreamX;

public interface PrintStreamX extends OutputStreamX, Appendable {

    public void 		write(int b);
    public void 		write(byte b[]) throws IOException;
    public void 		write(byte b[], int off, int len);

    public void 		flush();
    public void 		close();
    
    public boolean 		checkError();
    public void 		print(boolean b);
    public void 		print(char c);
    public void 		print(int i);
    public void 		print(long l);
    public void 		print(float f);
    public void 		print(double d);
    public void 		print(char s[]);
    public void 		print(String s);
    public void 		print(Object obj);
    public void 		println();
    public void 		println(boolean x);
    public void 		println(char x);
    public void 		println(int x);
    public void 		println(long x);
    public void 		println(float x);
    public void 		println(double x);
    public void 		println(char x[]);
    public void 		println(String x);
    public void 		println(Object x);
   
    public PrintStreamX 	printf(String format, Object ... args);
    public PrintStreamX 	printf(Locale l, String format, Object ... args);
    public PrintStreamX 	format(String format, Object ... args);
    public PrintStreamX 	format(Locale l, String format, Object ... args);

    public PrintStreamX 	append(CharSequence csq);
    public PrintStreamX 	append(CharSequence csq, int start, int end);
    public PrintStreamX 	append(char c);

}
