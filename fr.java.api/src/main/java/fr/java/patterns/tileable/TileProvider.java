package fr.java.patterns.tileable;

import java.text.DecimalFormat;

import fr.java.data.Data;
import fr.java.data.provider.DataProvider;

public interface TileProvider<TILE> extends TileSystem, DataProvider<TileCoordinate, TILE> {

	public interface Async<TILE> extends TileProvider<TILE>, DataProvider.Async<TileCoordinate, TILE> {

		public default Data.Async<TileCoordinate, TILE> get(int _level, int _i, int _j) {
			return get(new TileCoordinate() {

				@Override public int 			getLevel() 					{ return _level; }
				@Override public int 			getI() 						{ return _i; }
				@Override public int 			getJ() 						{ return _j; }

				@Override public double 		getY() 						{ return 0; }
				@Override public double 		getX() 						{ return 0; }

				@Override public String 		toString(DecimalFormat _df) { return toString(); }

			});
		}

	}

	public default Data<TileCoordinate, TILE> get(int _level, int _i, int _j) {
		return get(new TileCoordinate() {

			@Override public int 			getLevel() 					{ return _level; }
			@Override public int 			getI() 						{ return _i; }
			@Override public int 			getJ() 						{ return _j; }

			@Override public double 		getY() 						{ return 0; }
			@Override public double 		getX() 						{ return 0; }

			@Override public String 		toString(DecimalFormat _df) { return toString(); }

		});
	}

}
