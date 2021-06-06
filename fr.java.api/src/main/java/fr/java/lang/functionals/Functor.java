/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @file     Functor.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.lang.functionals;

public interface Functor<R> {

	@FunctionalInterface
	public interface NoArg<R> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 0; }
		@Override default R run(Object... arguments) { assert(arguments == null || arguments.length == 0); return run(); }
		R run();
	}
	@FunctionalInterface
	public interface OneArg<R, T0> extends Functor<R> {

		@Override default int getNumberOfArguments() { return 1; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args)     { assert(_args.length == 1); return run((T0) _args[0]); }
		R run(T0 _arg0);
	}
	@FunctionalInterface
	public interface TwoArgs<R, T0, T1> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 2; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert(_args.length == 2); return run((T0) _args[0], (T1) _args[1]); }
		R run(T0 _arg0, T1 _arg1);
	}
	@FunctionalInterface
	public interface ThreeArgs<R, T0, T1, T2> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 3; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 3); return run((T0) _args[0], (T1) _args[1], (T2) _args[2]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2);
	}
	@FunctionalInterface
	public interface FourArgs<R, T0, T1, T2, T3> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 4; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 4); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3);
	}
	@FunctionalInterface
	public interface FiveArgs<R, T0, T1, T2, T3, T4> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 5; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 5); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3], (T4) _args[4]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3, T4 _arg4);
	}
	@FunctionalInterface
	public interface SixArgs<R, T0, T1, T2, T3, T4, T5> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 6; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 6); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3], (T4) _args[4], (T5) _args[5]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3, T4 _arg4, T5 _arg5);
	}
	@FunctionalInterface
	public interface SevenArgs<R, T0, T1, T2, T3, T4, T5, T6> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 7; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 7); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3], (T4) _args[4], (T5) _args[5], (T6) _args[6]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3, T4 _arg4, T5 _arg5, T6 _arg6);
	}
	@FunctionalInterface
	public interface EightArgs<R, T0, T1, T2, T3, T4, T5, T6, T7> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 8; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 8); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3], (T4) _args[4], (T5) _args[5], (T6) _args[6], (T7) _args[7]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3, T4 _arg4, T5 _arg5, T6 _arg6, T7 _arg7);
	}
	@FunctionalInterface
	public interface NineArgs<R, T0, T1, T2, T3, T4, T5, T6, T7, T8> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 9; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 9); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3], (T4) _args[4], (T5) _args[5], (T6) _args[6], (T7) _args[7], (T8) _args[8]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3, T4 _arg4, T5 _arg5, T6 _arg6, T7 _arg7, T8 _arg8);
	}
	@FunctionalInterface
	public interface TenArgs<R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Functor<R> {
		@Override default int getNumberOfArguments() { return 10; }
		@SuppressWarnings("unchecked")
		@Override default R run(Object... _args) { assert (_args.length == 10); return run((T0) _args[0], (T1) _args[1], (T2) _args[2], (T3) _args[3], (T4) _args[4], (T5) _args[5], (T6) _args[6], (T7) _args[7], (T8) _args[8], (T9) _args[9]); }
		R run(T0 _arg0, T1 _arg1, T2 _arg2, T3 _arg3, T4 _arg4, T5 _arg5, T6 _arg6, T7 _arg7, T8 _arg8, T9 _arg9);
	}
	
	default R run(Object... _args) {
		throw new UnsupportedOperationException("not possible");
	}
	default int getNumberOfArguments() {
		throw new UnsupportedOperationException("unknown");
	}

}
