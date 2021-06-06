package fr.java.sdk.time.tools;

import java.time.Instant;
import java.util.function.Supplier;

import fr.java.time.tools.TimeWatch;

public class TimeWatchImpl implements TimeWatch {
	Instant start, stop;

	public static final void eval(Runnable _run) {
    	TimeWatchImpl time = new TimeWatchImpl();
    	_run.run();
        System.out.println(time.toc().toString());
	}
	public static final <O> O eval(Supplier<O> _supplier) {
    	TimeWatchImpl time = new TimeWatchImpl();
    	O result = _supplier.get();
        System.out.println(time.toc().toString());
        return result;
	}

	public TimeWatchImpl() {
		start = Instant.now();
		stop  = Instant.now();
	}

	public Instant tic() {
		return start = Instant.now();
	}
	public Instant toc() {
		return stop = Instant.now();
	}

	public long getInNanoseconds() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) (s * 1e9 + ns);
	}
	public long getInMicroseconds() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) (s * 1e6 + ns * 1e-3);
	}
	public long getInMilliseconds() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) (s * 1e3 + ns * 1e-6);
	}
	public long getInSeconds() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) (s + ns * 1e-9);
	}
	public long getInMinutes() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) ((s + ns * 1e-9) / 60d);
	}
	public long getInHours() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) ((s + ns * 1e-9) / 3600d);
	}
	public long getInDays() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) ((s + ns * 1e-9) / 86400d); // 86400.0 = 24.0 * 3600.0
	}
	public long getInYears() {
		long s  = stop.getEpochSecond() - start.getEpochSecond();
		long ns = stop.getNano() - start.getNano();

		return (long) ((s + ns * 1e-9) / 31536000d); // 31536000.0 = 365.0 * 24.0 * 3600.0;
	}

	public String toString() {
		String result = "Elapsed time:";
		int test = 0;
		
		test = (int) getInYears();
		if(test > 0)
			result += String.format("%dY", test);

		test = (int) getInDays() - 365 * (int) getInYears();
		if(test > 0)
			result += String.format(" %dd", test);

		test = (int) getInHours() - 24 * (365 * (int) getInYears() + (int) getInDays());
		if(test > 0)
			result += String.format(" %dh", test);

		test = (int) getInMinutes() - 60 * (24 * (365 * (int) getInYears() + (int) getInDays()) + (int) getInHours());
		if(test > 0)
			result += String.format(" %dm", test);

		test = (int) getInSeconds() - 60 * (60 * (24 * (365 * (int) getInYears() + (int) getInDays()) + (int) getInHours()) + (int) getInMinutes());
		if(test > 0)
			result += String.format(" %ds", test);

		test = (int) getInMilliseconds() - 1000 * (60 * (60 * (24 * (365 * (int) getInYears() + (int) getInDays()) + (int) getInHours()) + (int) getInMinutes()) + (int) getInSeconds());
		if(test > 0)
			result += String.format(" %dms", test);

		test = (int) getInMicroseconds() - 1000 * (1000 * (60 * (60 * (24 * (365 * (int) getInYears() + (int) getInDays()) + (int) getInHours()) + (int) getInMinutes()) + (int) getInSeconds()) + (int) getInMilliseconds());
		if(test > 0)
			result += String.format(" %dµs", test);
/*
		test = (int) getInNanoseconds() - 1000 * (1000 * (1000 * (60 * (60 * (24 * (365 * (int) getInYears() + (int) getInDays()) + (int) getInHours()) + (int) getInMinutes()) + (int) getInSeconds()) + (int) getInMilliseconds()) + (int) getInMicroseconds());
		if(test > 0)
			result += String.format(" %dns", test);
*/
		return result;
	}
	
}
