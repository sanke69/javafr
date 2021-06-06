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
 * @file     XLogger.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.log;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import fr.java.lang.tuples.Triplet;
import fr.java.log.LoggerX;
import fr.java.sdk.lang.Tuples;

public class LogInstance extends Logger implements LoggerX {
	public static final Instant start  = Instant.now();
	public static final Logger  system = Logger.getLogger("");

 //   final static ConsoleHandler consoleHandler = new ConsoleHandler();
    
	static { // DEFAULT INITIALIZATION
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new LogFormatter());

        for(Handler h : system.getHandlers())
        	system.removeHandler(h);
		system.addHandler(consoleHandler);

//		for(Handler h : Logger.getAnonymousLogger().getHandlers())
//			Logger.getAnonymousLogger().removeHandler(h);
//		Logger.getAnonymousLogger().addHandler(consoleHandler);
	}

	public static LogInstance getLogger() {
    	return new LogInstance("", null);
	}
	public static LogInstance getLogger(String _name) {
    	return new LogInstance(_name, null);
	}
	public static LogInstance getLogger(Class<?> _class) {
    	return new LogInstance(_class.getName(), null);
	}
	public static LogInstance getLogger(Level _level) {
		LogInstance logger = new LogInstance("", null);
		logger.setLevel(_level);
    	return logger;
	}
	public static LogInstance getLogger(String _name, Level _level) {
		LogInstance logger = new LogInstance(_name, null);
		logger.setLevel(_level);
    	return logger;
	}
	public static LogInstance getLogger(Class<?> _class, Level _level) {
		LogInstance logger = new LogInstance(_class.getName(), null);
		logger.setLevel(_level);
    	return logger;
	}

	public static void setSystemLevel(Level _level) {
		for(Handler h : system.getHandlers())
			h.setLevel(_level);
	}

	public void setLevel(int _level) {
		Level lvl = new LogLevel("CUSTOM", _level);
		super.setLevel(lvl);
		for(Handler h : getHandlers())
			h.setLevel(lvl);
	}

	public void setLevel(Level _level) {
		super.setLevel(_level);
		for(Handler h : getHandlers())
			h.setLevel(_level);
	}

	LogInstance(String _name, String _rcName) {
		super(_name, null);
		setLevel(Level.ALL);
		setParent(Logger.getAnonymousLogger());
	}

	public void entering(String sourceClass, String sourceMethod) {
		if(getLevel() == Level.OFF)
			return ;
        sendLog(Level.FINER, getCallerInfo(), "ENTRY");
    }
	public void entering(String sourceClass, String sourceMethod, Object _param) {
		if(getLevel() == Level.OFF)
			return ;
		sendLog(Level.FINER, getCallerInfo(), "ENTRY {0}", new Object[] { _param });
    }
	public void entering(String _sourceClass, String _sourceMethod, Object[] _params) {
		if(getLevel() == Level.OFF)
			return ;
		String msg = "ENTRY";
        if(_params == null ) {
            sendLog(Level.FINER, getCallerInfo(), msg);
           return;
        }
        if (!isLoggable(Level.FINER)) return;
        for (int i = 0; i < _params.length; i++) {
            msg = msg + " {" + i + "}";
        }

        sendLog(Level.FINER, getCallerInfo(), msg, _params);
	}
	public void exiting(String sourceClass, String sourceMethod, Object result) {
        sendLog(Level.FINER, getCallerInfo(), "RETURN {0}", new Object[] { result });
    }

	public void trace(int _nLevel) {
		Level  lvl = new LogLevel("TRACE", Level.FINEST.intValue());

	    String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
	    String className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	    String methodName    = Thread.currentThread().getStackTrace()[2].getMethodName();
	    int    lineNumber    = Thread.currentThread().getStackTrace()[2].getLineNumber();

		StringBuilder sb = new StringBuilder();
		sb.append("Stack Trace for " + className + "." + methodName + "\n");
		for(int i = 2; i < 2 + _nLevel && i < Thread.currentThread().getStackTrace().length; ++i) {
		    fullClassName = Thread.currentThread().getStackTrace()[i].getClassName();
		    className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		    methodName    = Thread.currentThread().getStackTrace()[i].getMethodName();
		    lineNumber    = Thread.currentThread().getStackTrace()[i].getLineNumber();

		    sb.append(className + "." +  methodName + " -> (" + className + ".java:" + lineNumber + ")\n");
		}

		sb.append("End of Stack Trace");
        sendLog(lvl, getCallerInfo(), sb.toString(), (Object[]) null);
	}
	public void trace(String _format) {
		Level  lvl = new LogLevel("TRACE", Level.FINEST.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void trace(String _format, Object... _args) {
		Level  lvl = new LogLevel("TRACE", Level.FINEST.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void trace(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("TRACE", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void trace(Supplier<String> msgSupplier) {
		Level  lvl = new LogLevel("TRACE", Level.FINEST.intValue());
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }
	public void trace(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("TRACE", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void log(String _format) {
		Level  lvl = new LogLevel("LOG", Level.INFO.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void log(String _format, Object... _args) {
		Level  lvl = new LogLevel("LOG", Level.INFO.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void log(int _level, String _format, Object... _args) {
		Level lvl = new LogLevel("LOG", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void log(Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("LOG", Level.INFO.intValue());
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }
	public void log(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("LOG", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void log(int _level, Throwable _thrown, Supplier<String> _msgSupplier) {
		Level lvl = new LogLevel("LOG", _level);
		log(lvl, _thrown, _msgSupplier);
	}
	public void log(Level _level, Throwable _thrown, Supplier<String> _msgSupplier) {
		if(!isLoggable(_level))
            return;

		Triplet<String, String, Integer> caller = getCallerInfo();

        LogRecord lr = new LogRecord(Level.FINER, "THROW " + _msgSupplier.get());
        lr.setSourceClassName(caller.getFirst());
        lr.setSourceMethodName(caller.getSecond() + ":" + caller.getThird());
        lr.setThrown(_thrown);        
        lr.setLoggerName(getName());

        log(lr);
	}
	public void log(Level _level, String _msg, Throwable _thrown) {
		if(!isLoggable(_level))
            return;

		Triplet<String, String, Integer> caller = getCallerInfo();

        LogRecord lr = new LogRecord(Level.FINER, "THROW ");
        lr.setSourceClassName(caller.getFirst());
        lr.setSourceMethodName(caller.getSecond() + ":" + caller.getThird());
        lr.setThrown(_thrown);        
        lr.setLoggerName(getName());

        log(lr);
	}

	public void info(String _format) {
        sendLog(Level.INFO, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void info(String _format, Object... _args) {
        sendLog(Level.INFO, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void info(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("INFO", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void info(Supplier<String> msgSupplier) {
        sendLog(Level.INFO, getCallerInfo(), msgSupplier.get());
    }
	public void info(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("INFO", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void debug(String _format) {
		Level  lvl = new LogLevel("DEBUG", Level.FINEST.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void debug(String _format, Object... _args) {
		Level  lvl = new LogLevel("DEBUG", Level.FINEST.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void debug(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("DEBUG", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void debug(Supplier<String> msgSupplier) {
		Level  lvl = new LogLevel("DEBUG", Level.FINEST.intValue());
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }
	public void debug(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("DEBUG", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void fine(String _format) {
        sendLog(Level.FINE, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void fine(String _format, Object... _args) {
        sendLog(Level.FINE, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void fine(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("FINE", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void fine(Supplier<String> msgSupplier) {
        sendLog(Level.FINE, getCallerInfo(), msgSupplier.get());
    }
	public void fine(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("FINE", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void finer(String _format) {
        sendLog(Level.FINER, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void finer(String _format, Object... _args) {
        sendLog(Level.FINER, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void finer(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("FINER", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void finer(Supplier<String> msgSupplier) {
        sendLog(Level.FINER, getCallerInfo(), msgSupplier.get());
    }
	public void finer(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("FINER", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void finest(String _format) {
        sendLog(Level.FINEST, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void finest(String _format, Object... _args) {
        sendLog(Level.FINEST, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void finest(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("FINEST", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void finest(Supplier<String> msgSupplier) {
        sendLog(Level.FINEST, getCallerInfo(), msgSupplier.get());
    }
	public void finest(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("FINEST", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void warning(String _message) {
        sendLog(Level.WARNING, getCallerInfo(), restoreFormat(_message), (Object[]) null);
	}
	public void warning(String _format, Object... _args) {
        sendLog(Level.WARNING, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void warning(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("WARNING", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void warning(Supplier<String> msgSupplier) {
        sendLog(Level.WARNING, getCallerInfo(), msgSupplier.get());
    }
	public void warning(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("WARNING", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void error(String _format) {
		Level lvl = new LogLevel("ERROR", Level.SEVERE.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void error(String _format, Object... _args) {
		Level lvl = new LogLevel("ERROR", Level.SEVERE.intValue());
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void error(int _level, String _format, Object... _args) {
		Level lvl = new LogLevel("ERROR", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void error(Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("ERROR", Level.SEVERE.intValue());
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }
	public void error(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("ERROR", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public void severe(String _format) {
        sendLog(Level.SEVERE, getCallerInfo(), restoreFormat(_format), (Object[]) null);
	}
	public void severe(String _format, Object... _args) {
        sendLog(Level.SEVERE, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void severe(int _level, String _format, Object... _args) {
		Level  lvl = new LogLevel("SEVERE", _level);
        sendLog(lvl, getCallerInfo(), restoreFormat(_format), _args);
	}
	public void severe(Supplier<String> msgSupplier) {
        sendLog(Level.SEVERE, getCallerInfo(), msgSupplier.get());
    }
	public void severe(int _level, Supplier<String> msgSupplier) {
		Level lvl = new LogLevel("SEVERE", _level);
        sendLog(lvl, getCallerInfo(), msgSupplier.get());
    }

	public <T extends Throwable> void throwing(T thrown, String _format, Object... _args) throws T {
		String msg = String.format(restoreFormat(_format), _args);

		Triplet<String, String, Integer> caller = getCallerInfo();

        LogRecord lr = new LogRecord(Level.FINER, "THROW " + msg);
        lr.setSourceClassName(caller.getFirst());
        lr.setSourceMethodName(caller.getSecond() + ":" + caller.getThird());
        lr.setThrown(thrown);        
        lr.setLoggerName(getName());

        log(lr);
        throw thrown;
    }
	
	private void sendLog(Level _lvl, Triplet<String, String, Integer> _caller, String _msg) {
        logp(_lvl, _caller.getFirst(), _caller.getSecond() + ":" + _caller.getThird(), _msg);
	}
	private void sendLog(Level _lvl, Triplet<String, String, Integer> _caller, String _msg, Object[] _params) {
        logp(_lvl, _caller.getFirst(), _caller.getSecond() + ":" + _caller.getThird(), _msg, _params);
	}

	private String restoreFormat(String _msg) {
		int i = 0;
		while(_msg.contains("{}")) {
			if(_msg.contains("'{}'")) {
				if(_msg.indexOf("{}") == _msg.indexOf("'{}'") + 1)
					_msg = _msg.replaceFirst("\\'\\{\\}\\'", "\\'\\'{" + i++ + "}\\'\\'");
				else
					_msg = _msg.replaceFirst("\\{\\}", "{" + i++ + "}");
			} else
				_msg = _msg.replaceFirst("\\{\\}", "{" + i++ + "}");
		}
		
		return _msg;
	}
	private Triplet<String, String, Integer> getCallerInfo() {
	    String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
	    String className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	    String methodName    = Thread.currentThread().getStackTrace()[3].getMethodName();
	    int    lineNumber    = Thread.currentThread().getStackTrace()[3].getLineNumber();
		
	    return Tuples.of(className, methodName, lineNumber);
	}

	/* *****
	 * CONFIGURATION
	 */
	public void clearHandlers() {
    	for(Handler h : getHandlers())
    		removeHandler(h);
	}
	public void addFileHandler(Path _logFile, boolean _append) throws SecurityException, IOException {
    	final FileHandler fileHandler = new FileHandler(_logFile.toString(), _append);
    	fileHandler.setFormatter(new LogFormatter());

		addHandler(fileHandler);
	}

}
