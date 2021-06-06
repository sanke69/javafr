package fr.java.cli.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import fr.java.cli.CommandLineInterpreter;
import fr.java.multitasks.processes.ProcessX;
import fr.java.nio.stream.StreamX;
import fr.java.nio.stream.std.UserStdStreamX;

public class CliSkeletonAsync implements CommandLineInterpreter {

	private ProcessX		cli_process;
	private PrintStream     cli_writer;
	private BufferedReader 	cli_reader;

	private UserStdStreamX 	usr_streams;
	private Thread			usr_thread;
	private BufferedReader 	usr_reader;
	private PrintStream 	usr_writer;

	public static void main(String[] args) throws IOException {
		try(CliSkeletonAsync interpreter = new CliSkeletonAsync(new ProcessX("/bin/bash"), UserStdStreamX.terminal())) {
			interpreter.mainLoop();
		};
	}

	public CliSkeletonAsync() {
		super();
	}
	public CliSkeletonAsync(ProcessX _process) {
		super();
		setProcess(_process);
	}
	public CliSkeletonAsync(UserStdStreamX _external) {
		super();
		setUserStreams(_external);
	}
	public CliSkeletonAsync(ProcessX _process, UserStdStreamX _external) {
		super();
		setProcess(_process);
		setUserStreams(_external);
	}

	@Override
	public UserStdStreamX 	getUserStreams() {
		return usr_streams;
	}
	public void				setUserStreams(UserStdStreamX _streams) {
		usr_streams = _streams;
		usr_reader  = new BufferedReader( new InputStreamReader( StreamX.asStreamJava( getUserStreams().getIn() )));
		usr_writer  = new PrintStream( StreamX.asStreamJava( getUserStreams().getOut() ) );
	}

	public ProcessX 		getProcess() {
		return cli_process;
	}
	public void				setProcess(ProcessX _process) {
		cli_process = _process;
	}

	@Override
	public void 			open() {
		if(!getProcess().isRunning())
			getProcess().start();

		cli_writer     = new PrintStream( StreamX.asStreamJava( cli_process.getIn() ) );
		cli_reader     = new BufferedReader(new InputStreamReader( StreamX.asStreamJava( cli_process.getOut() ) ));

		usr_thread  = new Thread(() -> {
			while(!Thread.currentThread().isInterrupted()) {
				String[] externalOutputs = readFromProcess();
				if(externalOutputs != null)
					Arrays.stream(externalOutputs)
						  .forEach(line -> usr_writer.println( line ));

				try { Thread.sleep(50);
				} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
			}
		});
		usr_thread.start();
	}
	@Override
	public void 			mainLoop() {
		open();

		if(getUserStreams() == null)
			System.err.println("Command Interpreter is not initialized!!!");

		while(getProcess().isRunning()) {
			try {
				writeToProcess( waitUserInput() );

			} catch(IOException e) {
				e.printStackTrace();
				getProcess().terminate(500);
			}
		}
	}
	@Override
	public void 			close() {
		usr_thread.interrupt();
	}

	private String 			waitUserInput() throws IOException {
		return usr_reader.readLine();
	}

	private void 			writeToProcess(String _command) {
		cli_writer.println( _command );
		cli_writer.flush();
	}
	private String[] 		readFromProcess() {
		Collection<String> s = new ArrayList<String>();

		String tmp;
		try {

			while(cli_reader.ready())
				if((tmp = cli_reader.readLine()) != null)
					s.add(tmp);

		} catch(IOException e) {
			e.printStackTrace();
		}

		return s.isEmpty() ? null : s.stream().toArray(n -> new String[n]);
	}

}
