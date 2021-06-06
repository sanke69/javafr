package fr.java.patterns.tileable;

import fr.java.math.topology.Coordinate;

public interface TileCoordinate extends Coordinate.Cartesian2D {

	public static interface Editable extends TileCoordinate, Coordinate.Cartesian2D.Editable {

		public void setLevel(int _level);

		public void setI(int _i);
		public void setJ(int _j);

	}

	public int getLevel();

	public int getI();
	public int getJ();

}
