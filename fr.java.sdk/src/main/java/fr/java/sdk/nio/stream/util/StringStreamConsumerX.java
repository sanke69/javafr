package fr.java.sdk.nio.stream.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.StreamX;

public final class StringStreamConsumerX extends StreamConsumerX<String> {
 
    public StringStreamConsumerX(InputStreamX inputStream, Consumer<String> consumer) {
    	super(inputStream, consumer);
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(StreamX.asStreamJava(getInputStream()))).lines().forEach(getConsumer());
    }

}
