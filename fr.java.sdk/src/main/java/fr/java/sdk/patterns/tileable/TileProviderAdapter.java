package fr.java.sdk.patterns.tileable;

import fr.java.patterns.tiled.TileProvider;

public abstract class TileProviderAdapter<TILE> implements TileProvider<TILE> {

	private final int	 minLevel, maxLevel;

	private final long[] tilesCountPerLevel;
	private final long[] mapSizePerLevel;

	private int			 tileSize;

	public TileProviderAdapter() {
		this(256, 0, 19);
	}
	public TileProviderAdapter(int _tileSize, int _minLevel, int _maxLevel) {
		super();

		tileSize = _tileSize;
		minLevel = _minLevel;
		maxLevel = _maxLevel;

		int nbLevel        = maxLevel - minLevel + 1;
		tilesCountPerLevel = new long[nbLevel];
		mapSizePerLevel    = new long[nbLevel];

		for(int i = 0; i <= maxLevel - minLevel; ++i) {
			tilesCountPerLevel[i] = (int) Math.pow(2, i);
			mapSizePerLevel[i]    = tileSize * tilesCountPerLevel[i];
		}
	}

	@Override
	public int 	maxLevel() {
		return maxLevel;
	}

	@Override
	public int  tileSize(int _level) {
		return tileSize;
	}
	@Override
	public long tileCount(int _level) {
		return tilesCountPerLevel[_level - minLevel];
	}

	@Override
	public long mapSize(int _level) {
		if(_level - minLevel < 0 || _level > maxLevel)
			throw new IllegalArgumentException();
		return mapSizePerLevel[_level - minLevel];
	}

}
