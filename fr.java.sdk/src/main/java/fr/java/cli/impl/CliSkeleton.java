package fr.java.cli.impl;

import java.io.IOException;
import java.util.function.Function;

import fr.java.cli.CommandLineInterpreter;
import fr.java.lang.exceptions.InvalidInitializationException;
import fr.java.nio.stream.std.UserStdStreamX;

public class CliSkeleton implements CommandLineInterpreter {
	private Function<String, Integer>   function;
	private UserStdStreamX 				streams;

	public CliSkeleton() {
		super();
	}
	public CliSkeleton(Function<String, Integer> _function) {
		super();
		function = _function;
	}
	public CliSkeleton(Function<String, Integer> _function, UserStdStreamX _streams) {
		super();
		function = _function;
		streams  = _streams;
	}

	@Override
	public 		UserStdStreamX 		getUserStreams() {
		return streams;
	}
	@Override
	public 		void				setUserStreams(UserStdStreamX _streams) {
		streams = _streams;
	}

	public 		Function<String, Integer> getFunction() {
		return function;
	}
	public 		void				setFunction(Function<String, Integer> _function) {
		function = _function;
	}
	
	@Override
	public 		void 				open() {}
	@Override
	public 		void 				close() {}
		@Override
	public 		void 				mainLoop() {
		if(getUserStreams() == null || getFunction() == null)
			throw new InvalidInitializationException("Interpreter is not initialized!!!");


		while(!Thread.currentThread().isInterrupted()) {
			StringBuilder sb = new StringBuilder();
			prompt();

			int key = -1;
			while((key = readUserInput()) != 10 && key != -1)
				sb.append((char) key );

			if(key == -1)
				Thread.currentThread().interrupt();
			

			int err = function.apply( sb.toString() );
			if(err != 0) error("return " + err);

		}
	}

	protected 	int 				readUserInput() {
		int entry;
		try {
			entry = getUserStreams().getIn().read();
			return entry;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	protected String  getPrompt() { return "prompt> "; }

	protected 	void 				prompt() {
		if(getPrompt() == null)
			return ;

		try {
			getUserStreams().getOut().write("prompt> ".getBytes());
			getUserStreams().getOut().flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	protected 	void 				output(String _output) {
		try {
			getUserStreams().getOut().write(_output.getBytes());
			getUserStreams().getOut().write('\n');
			getUserStreams().getOut().flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	protected 	void 				error(String _error) {
		try {
			getUserStreams().getErr().write(_error.getBytes());
			getUserStreams().getErr().write('\n');
			getUserStreams().getErr().flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
