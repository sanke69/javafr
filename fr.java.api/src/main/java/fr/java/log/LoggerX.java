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
 * @file     LoggerX.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.logging.Level;

public interface LoggerX {

	public void setLevel(int _level);
	public void setLevel(Level _level);

	public void entering(String sourceClass, String sourceMethod);
	public void entering(String sourceClass, String sourceMethod, Object _param) ;
	public void entering(String _sourceClass, String _sourceMethod, Object[] _params);
	public void exiting(String sourceClass, String sourceMethod, Object result);

	public void trace(int _nLevel);
	public void trace(String _message);
	public void trace(String _format, Object... _args);
	public void trace(int _level, String _format, Object... _args);
	public void trace(Supplier<String> msgSupplier);
	public void trace(int _level, Supplier<String> msgSupplier);

	public void log(String _message);
	public void log(String _format, Object... _args);
	public void log(int _level, String _format, Object... _args);
	public void log(Supplier<String> msgSupplier);
	public void log(int _level, Supplier<String> msgSupplier);

	public void log(int _level, Throwable _thrown, Supplier<String> _msgSupplier);
	public void log(Level _level, Throwable _thrown, Supplier<String> _msgSupplier);
	public void log(Level _level, String _msg, Throwable _thrown);

	public void info(String _message);
	public void info(String _format, Object... _args);
	public void info(int _level, String _format, Object... _args);
	public void info(Supplier<String> msgSupplier);
	public void info(int _level, Supplier<String> msgSupplier);

	public void debug(String _message);
	public void debug(String _format, Object... _args);
	public void debug(int _level, String _format, Object... _args);
	public void debug(Supplier<String> msgSupplier);
	public void debug(int _level, Supplier<String> msgSupplier);

	public void fine(String _message);
	public void fine(String _format, Object... _args);
	public void fine(int _level, String _format, Object... _args);
	public void fine(Supplier<String> msgSupplier);
	public void fine(int _level, Supplier<String> msgSupplier);

	public void finer(String _message);
	public void finer(String _format, Object... _args);
	public void finer(int _level, String _format, Object... _args);
	public void finer(Supplier<String> msgSupplier);
	public void finer(int _level, Supplier<String> msgSupplier);

	public void finest(String _message);
	public void finest(String _format, Object... _args);
	public void finest(int _level, String _format, Object... _args);
	public void finest(Supplier<String> msgSupplier);
	public void finest(int _level, Supplier<String> msgSupplier);

	public void warning(String _message);
	public void warning(String _format, Object... _args);
	public void warning(int _level, String _format, Object... _args);
	public void warning(Supplier<String> msgSupplier);
	public void warning(int _level, Supplier<String> msgSupplier);

	public void error(String _message);
	public void error(String _format, Object... _args);
	public void error(int _level, String _format, Object... _args);
	public void error(Supplier<String> msgSupplier);
	public void error(int _level, Supplier<String> msgSupplier);

	public void severe(String _message);
	public void severe(String _format, Object... _args);
	public void severe(int _level, String _format, Object... _args);
	public void severe(Supplier<String> msgSupplier);
	public void severe(int _level, Supplier<String> msgSupplier);

	public <T extends Throwable> void throwing(T thrown, String _format, Object... _args) throws T;
	
	/* *****
	 * CONFIGURATION
	 */
	public void clearHandlers();
	public void addFileHandler(Path _logFile, boolean _append) throws SecurityException, IOException;

}
