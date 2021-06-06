package fr.java.patterns.tileable;

import fr.java.math.topology.Coordinate;

/**
 * 
 * Convert a Coordinate in a planar system to an associated point on a particular tile
 * 
 * @author sanke
 *
 * @param <M_Point>
 * @param <T_Point>
 */
// TODO:: Must extends Projector...
public interface TileProjector<M_Point extends Coordinate.TwoDims, T_Point extends TileCoordinate> {

	public T_Point  toTile(M_Point _map, int _level);
	public M_Point	fromTile(T_Point _tile);

}
