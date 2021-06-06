package fr.javafx.lang;

public class ZLevel {
	static int	zTopLevel;
	static int	zBottomLevel;

	int			zLevel;

	public ZLevel(int _zLvl) { 
		zLevel = _zLvl;
	}
	public ZLevel(ZLevel _zLvl) { 
		zLevel = _zLvl.zLevel;
	}
	
	void up() {
		if(zLevel < zTopLevel)
			zLevel++;
		else {
			if(zLevel != 0xEFFFFFFF)
				zTopLevel = ++zLevel;
			else
				zTopLevel = zLevel = 0xEFFFFFFF;
		}
	}
	void down() {
		if(zLevel > zBottomLevel)
			zLevel--;
		else {
			if(zLevel != 0xFFFFFFFF)
				zBottomLevel = --zLevel;
			else
				zBottomLevel = zLevel = 0xFFFFFFFF;
		}
	}
	void onTop() {
		zLevel = zTopLevel;
	}
	void onBottom() {
		zLevel = zBottomLevel;
	}
}
