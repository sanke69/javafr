package fr.java.multitasks.processes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import fr.java.jvm.JavaVM;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.nio.stream.StreamX;
import fr.java.nio.stream.std.StdStreamX;

public class ProcessX implements StdStreamX {

	public static class Arguments {
		List<String> values;

		public Arguments() {
			super();
			values = new ArrayList<String>();
		}
		public Arguments(String... _values) {
			super();
			values = new ArrayList<String>();
			if(_values != null)
				values.addAll(Arrays.asList(_values));
		}

		public int			size() {
			return values.size();
		}

		public boolean 		contains(String _arg) {
			return values.contains(_arg);
		}

		public void 		clear() {
			values.clear();
		}

		public void 		set(String... _args) {
			values.clear();
			values.addAll(Arrays.asList(_args));
		}
		public void 		set(Collection<String> _args) {
			values.clear();
			values.addAll(_args);
		}

		public void 		add(String _arg) {
			values.add(_arg);
		}
		public void 		addAll(String... _args) {
			values.addAll(Arrays.asList(_args));
		}
		public void 		addAll(Collection<String> _args) {
			values.addAll(_args);
		}

		public String 		toString() {
			return values.stream().collect(Collectors.joining(" "));
		}
		public String[] 	toStringArray() {
			return values.stream().toArray((n) -> new String[n]);
		}
		public List<String>	toStringList() {
			return values;
		}

	}

	public static class Environment {
		boolean             inherited;
		Map<String, String> variables;

		public Environment() {
			super();
			inherited = false;
			variables = new HashMap<String, String>();
		}
		public Environment(boolean _inherit) {
			super();
			inherited = _inherit;
			variables = new HashMap<String, String>();
		}
		public Environment(boolean _inherit, Map<String, String> _variables) {
			super();
			inherited = _inherit;
			variables = new HashMap<String, String>();
			variables.putAll(_variables);
		}

		public int								size() {
			return variables.size();
		}
		public Set<Map.Entry<String,String>>	entrySet() {
			return variables.entrySet();
		}

		public boolean 							containsKey(String _key) {
			return variables.containsKey(_key);
		}
		public String 							get(String _key) {
			return variables.get(_key);
		}

		public void 							inherits(boolean _inherit) {
			inherited = _inherit;
		}
		public boolean 							inherits() {
			return inherited;
		}

		public void 							set(Map<String, String> _envVars) {
			variables.clear();
			variables.putAll(_envVars);
		}

		public void 							put(String _variable, String _value) {
			variables.put(_variable, _value);
		}
		public void 							putAll(Map<String, String> _variables) {
			variables.putAll(_variables);
		}

		public void 							unset(String _variable) {
			variables.put(_variable, "");
		}
		public void 							remove(String _variable) {
			variables.remove(_variable);
		}

		public String 							toString() {
			return variables.entrySet()
							.stream()
							.map((e) -> e.getKey() + "=" + e.getValue())
							.collect(Collectors.joining(";"));
		}
		public String[] 						toStringArray() {
			return toStringMap().entrySet()
								.stream()
								.map((e) -> e.getKey() + "=" + e.getValue())
								.toArray(String[]::new);
		}
		public Map<String, String>				toStringMap() {
			Map<String, String> envVars = inherited ? JavaVM.EnvironmentVariable.getAll() : new HashMap<String, String>();

			if(inherited && variables.isEmpty())
				return null; // Default Behavior

			if(!variables.isEmpty()) {

				// Special case PATH
				if(inherited && variables.containsKey("PATH")) {
					String PATH = envVars.get("PATH");
					variables.put("PATH", variables.get("PATH") + ":" + PATH);
				}

				envVars.putAll(variables);
			}

			return envVars;
		}

	}

	public static class CommandLine {
		File      binary;
		Arguments arguments;

		public CommandLine() {
			this(null, (String[]) null);
		}
		public CommandLine(File _binary) {
			this(_binary, (String[]) null);
		}
		public CommandLine(File _binary, String... _args) {
			super();
			binary = _binary;
			arguments = new Arguments(_args);
		}
		public CommandLine(File _binary, Arguments _arguments) {
			super();
			binary = _binary;
			arguments = _arguments;
		}

		public CommandLine setBinary(String _binary) {
			binary = new File(_binary);
			return this;
		}
		public CommandLine setBinary(File _binary) {
			binary = _binary;
			return this;
		}
		public CommandLine setBinary(Path _binary) {
			binary = _binary.toFile();
			return this;
		}

		public CommandLine clearArguments() {
			arguments.clear();
			return this;
		}

		public CommandLine setArguments(String... _args) {
			arguments.addAll(_args);
			return this;
		}
		public CommandLine setArguments(List<String> _args) {
			arguments.addAll(_args);
			return this;
		}

		public CommandLine addArgument(String _arg) {
			arguments.add(_arg);
			return this;
		}
		public CommandLine addArguments(String... _arg) {
			arguments.addAll(_arg);
			return this;
		}
		public CommandLine addArguments(List<String> _arg) {
			arguments.addAll(_arg);
			return this;
		}

		public String[] 	toStringArray() {
			if(binary == null)
				throw new NullPointerException();

			String[] result;
			
			if(arguments != null) {
				result    = new String[arguments.size() + 1];
				result[0] = binary.toString();
				System.arraycopy(arguments.toStringArray(), 0, result, 1, arguments.size());
			} else 
				result = new String[] { binary.toString() };
			
			return result;
		}

	}

	public static String[] readOutput(ProcessX _processus) {
		if(_processus.process == null)
			return new String[] { "Processus is not running." };

		Collection<String> s = new ArrayList<String>();
		String tmp;
		try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( StreamX.asStreamJava( _processus.getOut() ) ));

			while(stdInput.ready())
				if((tmp = stdInput.readLine()) != null)
					s.add(tmp);

		} catch(IOException e) {
			e.printStackTrace();
		}

		return s.isEmpty() ? null : s.stream().toArray(n -> new String[n]);
	}
	public static String[] readError(ProcessX _processus) {
		if(_processus.process == null)
			return new String[] { "Processus is not running." };

		Collection<String> s = new ArrayList<String>();
		String tmp;
		try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader( StreamX.asStreamJava( _processus.getErr() ) ));
			while((tmp = stdInput.readLine()) != null)
				s.add(tmp);
		} catch(IOException e) {
			e.printStackTrace();
		}

		return s.stream().toArray(n -> new String[n]);
	}

	CommandLine			commandLine;
	Path				workingDir;
	Environment			environment;

	Process 			process;
	OutputStreamX 		inx;
	InputStreamX  		outx, errx;

	public static void main(String[] args) throws Exception {
		ProcessX p = new ProcessX("ping");
		p.addArguments("-c", "10", "localhost");
		p.start();

		System.out.println("<>");
		p.waitFor(500);
		System.out.println("<>");
		System.out.println(Arrays.asList( ProcessX.readOutput(p) ).stream().collect(Collectors.joining("\n")));	
		System.out.println("<>");
		p.waitFor(2000);
		System.out.println("<>");
		System.out.println(Arrays.asList( ProcessX.readOutput(p) ).stream().collect(Collectors.joining("\n")));	
		System.out.println("<>");
		p.waitFor();
		System.out.println(Arrays.asList( ProcessX.readOutput(p) ).stream().collect(Collectors.joining("\n")));
		System.out.println(p.returnCode().get());	
	}

	public ProcessX(String _binary) {
		this(new File(_binary), null, false);
	}
	public ProcessX(String _binary, String... _arguments) {
		this(new File(_binary), null, true);
		setArguments(_arguments);
	}
	public ProcessX(File _binary) {
		this(_binary, null, true);
	}
	public ProcessX(Path _binary) {
		this(_binary.toFile(), null, true);
	}
	public ProcessX(File _binary, Path _workingDir, boolean _inheritEnvironment) {
		super();
		workingDir  = _workingDir;
		commandLine     = new CommandLine(_binary);
		environment	= new Environment(_inheritEnvironment);

		process     = null;
	}

	// Configure
	public ProcessX 			setWorkingDirectory(Path _wDir) {
		workingDir = _wDir;		
		return this;
	}

	public ProcessX 			setBinary(String _arg) {
		commandLine.setBinary(new File(_arg));
		return this;
	}
	public ProcessX 			setBinary(File _arg) {
		commandLine.setBinary(_arg);
		return this;
	}
	public ProcessX 			setBinary(Path _arg) {
		commandLine.setBinary(_arg.toFile());
		return this;
	}

	public ProcessX 			setArguments(String... _args) {
		commandLine.setArguments(_args);
		return this;
	}
	public ProcessX 			setArguments(List<String> _args) {
		commandLine.setArguments(_args);
		return this;
	}

	public ProcessX 			addArguments(String... _args) {
		commandLine.addArguments(_args);
		return this;
	}
	public ProcessX 			addArguments(List<String> _args) {
		commandLine.addArguments(_args);
		return this;
	}

	public ProcessX 			inheritEnvironmentVariables(boolean _inheritEnvironemnt) {
		environment.inherits(_inheritEnvironemnt);
		return this;
	}

	public ProcessX 			setEnvironmentVariables(Map<String, String> _envVars) {
		environment.set(_envVars);
		return this;
	}

	public ProcessX 			addEnvironmentVariable(String _key, String _value) {
		environment.put(_key, _value);
		return this;
	}
	public ProcessX 			addEnvironmentVariables(Map<String, String> _envVars) {
		environment.putAll(_envVars);
		return this;
	}

	// Status
	public boolean 				isExist() {
		return commandLine.binary != null 
				&& commandLine.binary.exists() 
				&& commandLine.binary.isFile();
	}
	public boolean 				isRunning() {
		return process != null
				&& process.isAlive();
	}
	
	public boolean 				hasFinished() {
		return process != null && !process.isAlive();
	}

	public boolean 				hasOutputs() {
		if(process == null)
			return false;

		try {
			return process.getInputStream().available() > 0;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean 				hasErrors() {
		if(process == null)
			return false;

		try {
			if(process.isAlive())
				return process.getErrorStream().available() > 0;
			else
				return process.getErrorStream().available() > 0 || process.exitValue() != 0;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Optional<Integer> 	returnCode() {
		if(process == null || process.isAlive())
			return Optional.empty();

		try {
			return Optional.of(process.exitValue());
		} catch(IllegalThreadStateException e) {
			return Optional.of(666);
		}
	}

	// Properties	@Override
	public int					getPID() { return (int) process.pid(); }

	public OutputStreamX 		getIn() {
		if(inx == null)
			inx = StreamX.asStreamX( process.getOutputStream() );
		return inx;
	}
	@Override
	public InputStreamX  		getOut() {
		if(outx == null)
			outx = StreamX.asStreamX( process.getInputStream() );
		return outx;
	}
	@Override
	public InputStreamX  		getErr() {
		if(errx == null)
			errx = StreamX.asStreamX( process.getErrorStream() );
		return errx;
	}
/*
	public OutputStream			getIn() {
		return process != null ? process.getOutputStream() : null;
	}
	public InputStream			getOut() {
		return process != null ? process.getInputStream() : null;
	}
	public InputStream			getErr() {
		return process != null ? process.getErrorStream() : null;
	}
*/
	// Actions
	public boolean 				start() {
		if(process != null)
			return false;

		try {
			process = Runtime.getRuntime().exec(commandLine.toStringArray(),  
												environment.toStringArray(), 
												workingDir != null ? workingDir.toFile() : null);

			return process != null;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean 				waitFor() {
		if(process == null)
			return false;

		try {
			process.waitFor();
		} catch(InterruptedException e) { e.printStackTrace(); }
		
		return true;
	}
	public boolean				waitFor(int _ms) {
		if(process == null)
			return false;

		try {
			if( process.waitFor(_ms, TimeUnit.MILLISECONDS) )
				return true;

		} catch(InterruptedException e) { e.printStackTrace(); }

		return true;
	}
	public boolean 				terminate(int _ms) {
		TimeUnit ms = TimeUnit.MILLISECONDS;
		TimeUnit ns = TimeUnit.NANOSECONDS;

        long startTime = System.nanoTime();
        long rem       = ms.toNanos(_ms);

        do {
            try {
        		process.destroy();
                return true;
            } catch(IllegalThreadStateException ex) {
                if (rem > 0 && process.isAlive())
					try {
						Thread.sleep(Math.min(ns.toMillis(rem) + 1, 100));
					} catch (InterruptedException e) { }
            }
            rem = ms.toNanos(_ms) - (System.nanoTime() - startTime);
        } while (rem > 0);

        return false;
	}

	public void waitTermination() throws InterruptedException {
		process.waitFor();
	}

}
