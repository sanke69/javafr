package fr.java.sdk.math.stats.randoms;

// https://en.wikipedia.org/wiki/Linear_congruential_generator
public class LCG {

	public static enum Impl {
		/*
		Numerical_Recipes 	Math.pow(2, 32) 	1664525 	1013904223 	
		Borland_C_CPP 		Math.pow(2, 32) 	22695477 	1 	bits 30..16 in rand(), 30..0 in lrand()
		glibc 				Math.pow(2, 31) 	1103515245 	12345 	bits 30..0
		C99_C11				Math.pow(2, 31) 	1103515245 	12345 	bits 30..16
		Borland_Delphi_Virtual_Pascal 	Math.pow(2, 32) 	134775813 	1 	bits 63..32 of (seed * L)
		Turbo Pascal 	232 	134775813 (0x808840516) 	1 	
		Microsoft Visual/Quick C/C++ 	232 	214013 (343FD16) 	2531011 (269EC316) 	bits 30..16
		Microsoft Visual Basic (6 and earlier)[12] 	224 	1140671485 (43FD43FD16) 	12820163 (C39EC316) 	
		RtlUniform from Native API[13] 	231 − 1 	2147483629 (7FFFFFED16) 	2147483587 (7FFFFFC316) 	
		Apple CarbonLib, C++11's minstd_rand0[14] 	231 − 1 	16807 	0 	see MINSTD
		C++11's minstd_rand[14] 	231 − 1 	48271 	0 	see MINSTD
		MMIX by Donald Knuth 	264 	6364136223846793005 	1442695040888963407 	
		Newlib, Musl 	264 	6364136223846793005 	1 	bits 63...32
		VMS's MTH$RANDOM,[15] old versions of glibc 	232 	69069 (10DCD16) 	1 	
		Java's java.util.Random, POSIX [ln]rand48, glibc [ln]rand48[_r] 	248 	25214903917 (5DEECE66D16) 	11 	bits 47...16

		random0[16][17][18][19][20]

		If Xn is even then Xn+1 will be odd, and vice versa—the lowest bit oscillates at each step.
			134456 = 2375 	8121 	28411 	X n 134456 {\displaystyle {\frac {X_{n}}{134456}}} {\displaystyle {\frac {X_{n}}{134456}}}
		POSIX[21] [jm]rand48, glibc [mj]rand48[_r] 	248 	25214903917 (5DEECE66D16) 	11 	bits 47...15
		POSIX [de]rand48, glibc [de]rand48[_r] 	248 	25214903917 (5DEECE66D16) 	11 	bits 47...0
		cc65[22] 	223 	65793 (1010116) 	4282663 (41592716) 	bits 22...8
		cc65 	232 	16843009 (101010116) 	826366247 (3141592716) 	bits 31...16
		Formerly common: RANDU [4] 	231 	  65539 	0 	
		*/
	}
	
}
