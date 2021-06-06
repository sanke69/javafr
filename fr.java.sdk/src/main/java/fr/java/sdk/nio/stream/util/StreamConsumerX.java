package fr.java.sdk.nio.stream.util;

import java.util.function.Consumer;

import fr.java.nio.stream.InputStreamX;

public abstract class StreamConsumerX<T> implements Runnable {
    private InputStreamX inputStream;
    private Consumer<T> consumer;
 
    public StreamConsumerX(InputStreamX _inputStream, Consumer<T> _consumer) {
    	super();
        inputStream = _inputStream;
        consumer    = _consumer;
    }

    public InputStreamX getInputStream() {
    	return inputStream;
    }

    public Consumer<T> getConsumer() {
    	return consumer;
    }

}
