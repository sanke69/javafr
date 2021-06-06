package fr.java.time.api;

import java.time.Instant;

public interface TimeOperation {

	public Instant 			getTerm();

	public default String 	toStringValue() {
		StringBuilder sb = new StringBuilder();

		sb.append(getTerm());
		
		return sb.toString();
	}

	public interface WithValue<T> extends TimeOperation {

		public T 			getValue();

	}
	public interface WithVariation<T> extends TimeOperation {

		public T 			getVariation();

	}


}
