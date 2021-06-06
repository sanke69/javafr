package fr.java.sdk.log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.logging.Level;

import fr.java.log.LoggerX;

public class LogX {
	static final LoggerX instance = LogInstance.getLogger();

	public static final boolean TRACE = true;
	
	public static void setLevel(Level _level) {
		instance.setLevel(_level);
	}

	public static void entering(String _sourceClass, String _sourceMethod) {
		instance.entering(_sourceClass, _sourceMethod);
	}
	public static void entering(String sourceClass, String sourceMethod, Object _param)  {
		instance.entering(sourceClass, sourceMethod, _param);
	}
	public static void entering(String _sourceClass, String _sourceMethod, Object[] _params) {
		instance.entering(_sourceClass, _sourceMethod, _params);
	}
	public static void exiting(String _sourceClass, String _sourceMethod, Object _result) {
		instance.entering(_sourceClass, _sourceMethod, _result);
	}

	public static void trace(int _nLevel) {
		instance.trace(_nLevel);
	}
	public static void trace(String _message) {
		instance.trace(_message);
	}
	public static void trace(String _format, Object... _args) {
		instance.trace(_format, _args);
	}
	public static void trace(int _level, String _format, Object... _args) {
		instance.trace(_level, _format, _args);
	}
	public static void trace(Supplier<String> _msgSupplier) {
		instance.trace(_msgSupplier);
	}
	public static void trace(int _level, Supplier<String> _msgSupplier) {
		instance.trace(_level, _msgSupplier);
	}

	public static void log(String _message) {
		instance.log(_message);
	}
	public static void log(String _format, Object... _args) {
		instance.log(_format, _args);
	}
	public static void log(int _level, String _format, Object... _args) {
		instance.log(_level, _format, _args);
	}
	public static void log(Supplier<String> _msgSupplier) {
		instance.log(_msgSupplier);
	}
	public static void log(int _level, Supplier<String> _msgSupplier) {
		instance.log(_level, _msgSupplier);
	}

	public static void log(int _level, Throwable _thrown, Supplier<String> _msgSupplier) {
		instance.log(_level, _thrown, _msgSupplier);
	}
	public static void log(Level _level, Throwable _thrown, Supplier<String> _msgSupplier) {
		instance.log(_level, _thrown, _msgSupplier);
	}
	public static void log(Level _level, String _msg, Throwable _thrown) {
		instance.log(_level, _msg, _thrown);
	}

	public static void info(String _message) {
		instance.info(_message);
	}
	public static void info(String _format, Object... _args) {
		instance.info(_format, _args);
	}
	public static void info(int _level, String _format, Object... _args) {
		instance.info(_level, _format, _args);
	}
	public static void info(Supplier<String> _msgSupplier) {
		instance.info(_msgSupplier);
	}
	public static void info(int _level, Supplier<String> _msgSupplier) {
		instance.info(_level, _msgSupplier);
	}

	public static void debug(String _message) {
		instance.debug(_message);
	}
	public static void debug(String _format, Object... _args) {
		instance.debug(_format, _args);
	}
	public static void debug(int _level, String _format, Object... _args) {
		instance.debug(_level, _format, _args);
	}
	public static void debug(Supplier<String> _msgSupplier) {
		instance.debug(_msgSupplier);
	}
	public static void debug(int _level, Supplier<String> _msgSupplier) {
		instance.debug(_level, _msgSupplier);
	}

	public static void fine(String _message) {
		instance.fine(_message);
	}
	public static void fine(String _format, Object... _args) {
		instance.fine(_format, _args);
	}
	public static void fine(int _level, String _format, Object... _args) {
		instance.fine(_level, _format, _args);
	}
	public static void fine(Supplier<String> _msgSupplier) {
		instance.fine(_msgSupplier);
	}
	public static void fine(int _level, Supplier<String> _msgSupplier) {
		instance.fine(_level, _msgSupplier);
	}

	public static void finer(String _message) {
		instance.finer(_message);
	}
	public static void finer(String _format, Object... _args) {
		instance.finer(_format, _args);
	}
	public static void finer(int _level, String _format, Object... _args) {
		instance.finer(_level, _format, _args);
	}
	public static void finer(Supplier<String> _msgSupplier) {
		instance.finer(_msgSupplier);
	}
	public static void finer(int _level, Supplier<String> _msgSupplier) {
		instance.finer(_level, _msgSupplier);
	}

	public static void finest(String _message) {
		instance.finest(_message);
	}
	public static void finest(String _format, Object... _args) {
		instance.finest(_format, _args);
	}
	public static void finest(int _level, String _format, Object... _args) {
		instance.finest(_level, _format, _args);
	}
	public static void finest(Supplier<String> _msgSupplier) {
		instance.finest(_msgSupplier);
	}
	public static void finest(int _level, Supplier<String> _msgSupplier) {
		instance.finest(_level, _msgSupplier);
	}

	public static void warning(String _message) {
		instance.warning(_message);
	}
	public static void warning(String _format, Object... _args) {
		instance.warning(_format, _args);
	}
	public static void warning(int _level, String _format, Object... _args) {
		instance.warning(_level, _format, _args);
	}
	public static void warning(Supplier<String> _msgSupplier) {
		instance.warning(_msgSupplier);
	}
	public static void warning(int _level, Supplier<String> _msgSupplier) {
		instance.warning(_level, _msgSupplier);
	}

	public static void error(String _message) {
		instance.error(_message);
	}
	public static void error(String _format, Object... _args) {
		instance.error(_format, _args);
	}
	public static void error(int _level, String _format, Object... _args) {
		instance.error(_level, _format, _args);
	}
	public static void error(Supplier<String> _msgSupplier) {
		instance.error(_msgSupplier);
	}
	public static void error(int _level, Supplier<String> _msgSupplier) {
		instance.error(_level, _msgSupplier);
	}

	public static void severe(String _message) {
		instance.severe(_message);
	}
	public static void severe(String _format, Object... _args) {
		instance.severe(_format, _args);
	}
	public static void severe(int _level, String _format, Object... _args) {
		instance.severe(_level, _format, _args);
	}
	public static void severe(Supplier<String> _msgSupplier) {
		instance.severe(_msgSupplier);
	}
	public static void severe(int _level, Supplier<String> _msgSupplier) {
		instance.severe(_level, _msgSupplier);
	}

	public static <T extends Throwable> void throwing(T _thrown, String _format, Object... _args) throws T {
		instance.throwing(_thrown, _format, _args);
	}
	
	/* *****
	 * CONFIGURATION
	 */
	public static void clearHandlers() {
		instance.clearHandlers();
	}
	public static void addFileHandler(Path _logFile, boolean _append) throws SecurityException, IOException {
		instance.addFileHandler(_logFile, _append);
	}

}
