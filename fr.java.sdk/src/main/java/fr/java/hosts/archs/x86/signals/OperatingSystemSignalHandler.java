package fr.java.hosts.archs.x86.signals;

import java.io.File;
import java.io.IOException;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class OperatingSystemSignalHandler {
	
	enum WindowsSignals { ABRT, FPE, ILL, INT, SEGV, TERM };

	enum SolarisSignals { ABRT, ALRM, BUS, CHLD, CONT, EMT, FPE, HUP, ILL, INT, IO, KILL, PIPE, POLL, PROF, PWR, QUIT, SEGV, STOP, SYS, TERM, TRAP, TSTP, TTIN, TTOU, URG, USR1, USR2, VTALRM, WINCH, XCPU, XFSZ };

	enum LinuxSignals {
	HUP, INT,
	TRAP, ABRT, BUS,
	USR2,
	PIPE, ALRM, TERM, STKFLT,
	CHLD, CONT, TSTP,
	TTIN, TTOU, URG, XCPU,
	XFSZ, VTALRM, PROF, WINCH,
	IO, PWR, SYS, 
	
	// Not Accessible: Used by JVM
//	QUIT, ILL, FPE, KILL, USR1, SEGV, STOP, 

	// Not Tested
//	RTMIN,
//	RTMIN_P1		,RTMIN_P2	,RTMIN_P3	,RTMIN_P4,
//	RTMIN_P5		,RTMIN_P6	,RTMIN_P7	,RTMIN_P8,
//	RTMIN_P9		,RTMIN_P10	,RTMIN_P11	,RTMIN_P12,
//	RTMIN_P13, RTMIN_P14	,RTMIN_P15	,RTMAX_M14,
//	RTMAX_M13, RTMAX_M12	,RTMAX_M11	,RTMAX_M10,
//	RTMAX_M9		,RTMAX_M8	,RTMAX_M7	,RTMAX_M6,
//	RTMAX_M5		,RTMAX_M4	,RTMAX_M3	,RTMAX_M2,
//	RTMAX_M1		,RTMAX	
	}

	private static boolean running = true;

	public static void init() {
		SignalHandler handler = new SignalHandler() {
			public void handle(Signal sig) {
				System.out.println("Signal " + sig);
				try {
					new File("/ssd/src/test/" + sig).createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					try {
						new File("/ssd/src/test/ERROR").createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		
		for(LinuxSignals s : LinuxSignals.values())
			Signal.handle(new Signal(s.name()), handler);
//		Signal.handle(new Signal("INT"), handler);
//		Signal.handle(new Signal("TERM"), handler);
	}

	public static void main(String args[]) throws Exception {
		init();
		Object o = new Object();
		synchronized (o) {
			o.wait(10000);
		}
		System.exit(0);
	}
	
}