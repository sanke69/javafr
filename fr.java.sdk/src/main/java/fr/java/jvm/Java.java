package fr.java.jvm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Java {
	public static final String javaHome = System.getProperty("java.home");
	public static final String javacBin = javaHome + File.separator + ".." + File.separator + "bin" + File.separator + "javac";
	public static final String javaBin  = javaHome + File.separator + "bin" + File.separator + "java";

	public static Process newProcess(Class<?> _class) throws IOException, InterruptedException {
        String classpath = System.getProperty("java.class.path");
        String className = _class.getCanonicalName();

        Process process = new ProcessBuilder()
        							.command(javaBin, "-cp", classpath, className)
        							.inheritIO()
        							.start();

        return process;
	}

	public static Thread newThread(Class<?> _class, String... _argv) throws IOException, InterruptedException, NoSuchMethodException, SecurityException {
		final Method main = _class.getMethod("main", String[].class);

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					main.invoke(null, new Object[] { _argv });
				} catch(Exception e) {
					throw new AssertionError(e);
				}
			}
		});
		th.start();
		
		return th;
	}

}
