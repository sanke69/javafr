package fr.java.maths.geometry.plane.projectors;

import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProjector;
import fr.java.patterns.tileable.TileSystem;

abstract class DefaultTileProjector<COORD extends Coordinate.TwoDims, T_COORD extends TileCoordinate> implements TileProjector<COORD, T_COORD> {
	private final TileSystem tileSystem;

	protected DefaultTileProjector(TileSystem _tileSystem) {
		super();
		tileSystem = _tileSystem;
	}

	public abstract COORD 						newCoord(double _x, double _y);
	public abstract T_COORD 					newTileCoord(double _x, double _y, int _i, int _j, int _lvl);

	protected TileSystem						getTileSystem() { return tileSystem; }

	@Override
	public COORD								fromTile(T_COORD _tile) {
		double TSZ = getTileSystem().tileSize(_tile.getLevel());

		double X   = _tile.getI() * TSZ + _tile.getX();
		double Y   = _tile.getJ() * TSZ + _tile.getY();

		return newCoord(X, Y);
	}

	@Override
	public T_COORD 								toTile(COORD _map_point, int _level) {
		double  TSZ = getTileSystem().tileSize(_level);

		int     I   = (int) (_map_point.getFirst()  / TSZ);
		int     J   = (int) (_map_point.getSecond() / TSZ);
		double  X   = _map_point.getFirst()  - I * TSZ;
		double  Y   = _map_point.getSecond() - J * TSZ;

		return newTileCoord(X, Y, I, J, _level);
	}

}
