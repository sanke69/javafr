package fr.java.sdk.patterns.requester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

public abstract class StringAsyncRequester extends GenericAsyncRequester<String, String, Integer> {

	public StringAsyncRequester(int _nbSimultaneousTasks) {
		super(_nbSimultaneousTasks);
	}

	@Override
	public void 				writeRequest(OutputStream _os, String _request) {
		PrintStream bashWriter = new PrintStream( _os );

		bashWriter.println(_request);
		bashWriter.flush();
	}
	@Override
	public String[] 			readResponses(InputStream _is) {
		BufferedReader     br = new BufferedReader(new InputStreamReader( _is ));
		Collection<String> sc = new ArrayList<String>();

		String tmp = null;
		try {

			while(br.ready() && ((tmp = br.readLine()) != null))
				sc.add(tmp);

		} catch(IOException e) {
			e.printStackTrace();
		}

		return sc.isEmpty() ? null : sc.stream().toArray(n -> new String[n]);
	}

}
