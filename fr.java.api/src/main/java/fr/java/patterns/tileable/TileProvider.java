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
**/
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
