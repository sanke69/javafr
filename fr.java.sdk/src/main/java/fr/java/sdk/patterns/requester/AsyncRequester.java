package fr.java.sdk.patterns.requester;

import java.util.function.Consumer;

public interface AsyncRequester<REQUEST, RESPONSE, RESULT> {

	public void newRequest(REQUEST     _cmdLine);
	public void newRequest(REQUEST     _cmdLine, Consumer<RESULT> _afterRun);
	public void newRequests(REQUEST[]  _cmdLines);
	public void newRequests(REQUEST[]  _cmdLines, Consumer<RESULT> _afterRun);

}
