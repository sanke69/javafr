package fr.java.sdk.lang;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import fr.java.lang.TriConsumer;

public class Loops {
	
	public static class Params {
		int min, max, step;

		public static final Params of(int _max) {
			Params p = new Params();
			p.min  = 0;
			p.max  = _max;
			p.step = 1;
			
			return p;
		}
		public static final Params of(int _min, int _max) {
			Params p = new Params();
			p.min  = _min;
			p.max  = _max;
			p.step = 1;
			
			return p;
		}
		public static final Params of(int _min, int _max, int _step) {
			Params p = new Params();
			p.min  = _min;
			p.max  = _max;
			p.step = _step;
			
			return p;
		}
	}

	public static final void for1D(Params _params, Consumer<Integer> _loop) {
		for(int i = _params.min; i < _params.max; i += _params.step)
				_loop.accept(i);
	}
	public static final void for2D(Params _outer, Params _inner, BiConsumer<Integer,Integer> _loop) {
		for(int j = _outer.min; j < _outer.max; j += _outer.step)
			for(int i = _inner.min; i < _inner.max; i += _inner.step)
				_loop.accept(i, j);
	}
	public static final void for2D(int _x_min, int _x_max, int _y_min, int _y_max, int _stride, Consumer<Integer> _loopOn1DArray) {
		for (int y = _y_min * _stride; y < _y_max * _stride; y += _stride)
			for (int x = _x_min; x < _x_max; ++x)
				_loopOn1DArray.accept(x + y);
	}
	public static final void for3D(Params _outer, Params _middler, Params _inner, TriConsumer<Integer,Integer,Integer> _loop) {
		for(int k = _outer.min; k < _outer.max; k += _outer.step)
			for(int j = _middler.min; j < _middler.max; j += _middler.step)
				for(int i = _inner.min; i < _inner.max; i += _inner.step)
					_loop.accept(i, j, k);
	}
	public static final void for3D(int _d_min, int _d_max, int _x_min, int _x_max, int _y_min, int _y_max, int _stride, Consumer<Integer> _loopOn1DArray) {
		final int channel = _y_max * _stride;

		for (int d = _d_min; d < _d_max * channel; ++d)
			for (int y = _y_min; y < channel; y += _stride)
				for (int x = _x_min; x < _x_max; ++x)
					_loopOn1DArray.accept(x + y + d);
	}
	
	
	
}
