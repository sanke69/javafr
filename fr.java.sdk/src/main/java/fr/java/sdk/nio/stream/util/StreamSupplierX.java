package fr.java.sdk.nio.stream.util;

import java.util.function.Supplier;

import fr.java.nio.stream.OutputStreamX;

public abstract class StreamSupplierX<T> implements Runnable {
    private OutputStreamX outputStream;
    private Supplier<T>  supplier;
 
    public StreamSupplierX(OutputStreamX _outputStream, Supplier<T> _supplier) {
    	super();
        outputStream = _outputStream;
        supplier     = _supplier;
    }

    public OutputStreamX 	getOutputStream() {
    	return outputStream;
    }

    public Supplier<T> 		getSupplier() {
    	return supplier;
    }

}
