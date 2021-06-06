package fr.java.patterns.tileable;

public interface TileSystem {

	public default int	 			minLevel() { return 0; }
	public int	 					maxLevel();

	public int	 					tileSize(int _level);
	public long						tileCount(int _level);

	public long	 					mapSize(int _level);

}
