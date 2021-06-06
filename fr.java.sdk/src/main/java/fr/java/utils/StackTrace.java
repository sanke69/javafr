package fr.java.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StackTrace {

	public static void displayFiltered() {
		filterStackTrace().forEach( System.out::println );
	}

	public static List<String> filterStackTrace() {
		return StackWalker.getInstance().walk(s -> s.map(frame -> frame.getClassName() + "." + frame.getMethodName() + "():" + frame.getLineNumber())
													.filter(c -> c.startsWith("fr"))
													.limit(10)
													.collect(Collectors.toList()));
	}

}
