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

import java.util.Set;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Projector;
import fr.java.math.geometry.Viewport;
import fr.java.math.topology.Coordinate;

public interface TileViewport<MODEL, COORD extends Coordinate.TwoDims> extends Viewport.TwoDims<MODEL, COORD> {

	public static interface Editable<MODEL, COORD extends Coordinate.TwoDims> extends TileViewport<MODEL, COORD>, Viewport.TwoDims.Editable<MODEL, COORD> {

		/**
		 * Model to Map projector
		 * 
		 * @param <C>
		 * @param _model2mapProjector
		 */
		public void 					setModelProjector(Projector.Levelable<COORD, Coordinate.TwoDims> _model2mapProjector);

		public void 					setTileSystem(TileSystem _tileSystem);

		public void						setPreferedMapLevel(int _prefered_level);

		public void 					setCenter(COORD _center, int _level);
		public void						setOptimizedFor(Set<COORD> _model_coords);

	}

	public TileSystem 					getTileSystem();

//	public Projector.Levelable<COORD, Coordinate.TwoDims> getModelProjectorLvl();

	public BoundingBox.TwoDims			getMapBounds();
	public BoundingBox.TwoDims			getMapBounds(int _level);

	public int							getPreferedMapLevel();
	public int 							getMapLevel();
	public double 						getMapScale();
	public double 						getMapScale(int _mapLevel);

	// Model <-> { Map, Tile }
	public Coordinate.TwoDims 			modelInMap(COORD _model_point);								// use current map level
	public Coordinate.TwoDims 			modelInMap(COORD _model_point, int _level);

	public COORD 						mapInModel(Coordinate.TwoDims _model_point);				// use current map level
	public COORD 						mapInModel(Coordinate.TwoDims _model_point, int _level);

	public TileCoordinate 				modelInTile(COORD _model_point);							// use current map level
	public TileCoordinate 				modelInTile(COORD _model_point, int _level);

	public COORD 						tileInModel(TileCoordinate _tile_point);

	// Window <-> { Map, Tile }
	public Coordinate.TwoDims 			windowInMap(Coordinate.TwoDims _cursor);					// use current map level
	public Coordinate.TwoDims 			windowInMap(Coordinate.TwoDims _cursor, int _level);

	public Coordinate.TwoDims 			mapInWindow(Coordinate.TwoDims _model_point);				// use current map level
	public Coordinate.TwoDims 			mapInWindow(Coordinate.TwoDims _model_point, int _level);

	public TileCoordinate 				windowInTile(Coordinate.TwoDims _model_point);				// use current map level
	public TileCoordinate 				windowInTile(Coordinate.TwoDims _model_point, int _level);
	
	public Coordinate.TwoDims 			tileInWindow(TileCoordinate _tile_point);

	public default boolean 				scaledModelOverWindow() {
		if( getModelBounds() == null || getWindow() == null )
			return false;

		double ms = getMapScale();
		double mw = getMapBounds().getWidth();
		double mh = getMapBounds().getHeight();
		double ww = getWindow().getWidth();
		double wh = getWindow().getHeight();

		boolean bow_w = ms * mw > ww;
		boolean bow_h = ms * mh > wh;

		return bow_w && bow_h;
	}

}
