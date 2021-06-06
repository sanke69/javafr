package fr.java.sdk.nio.stream.std;

import java.io.IOException;

import fr.java.nio.stream.std.StdStreamX;
import fr.java.nio.stream.std.UserStdStreamX;
import fr.java.sdk.nio.stream.util.ThreadedStreamConnectorX;

public class PipedStdStreamX {

	public static PipedStdStreamX connect(final StdStreamX _in, final UserStdStreamX _out) {
		PipedStdStreamX pipes = new PipedStdStreamX(_in, _out);
		pipes.start();
		return new PipedStdStreamX(_in, _out);
	}

	StdStreamX 		spring;
	UserStdStreamX   pit;

	ThreadedStreamConnectorX input, output, error;

	private PipedStdStreamX(final StdStreamX _in, final UserStdStreamX _out) {
		super();
		spring = _in;
		pit    = _out;
	}

	public void disconnect() {
		stop();
	}
	
	void start() {
		try {
			if(spring.getIn() != null && pit.getIn() != null) {
				input = new ThreadedStreamConnectorX( pit.getIn(), spring.getIn() );
				input.start();
			}
			if(spring.getOut() != null && pit.getOut() != null) {
				output = new ThreadedStreamConnectorX( spring.getOut(), pit.getOut() );
				output.start();
			}
			if(spring.getErr() != null && pit.getErr() != null) {
				error  = new ThreadedStreamConnectorX( spring.getErr(), pit.getErr() );
				error.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	void stop() {
		if(input != null)
			input.terminate();
		if(output != null)
			output.terminate();
		if(error != null)
			error.terminate();

		input  = null;
		output = null;
		error  = null;
	}

}
