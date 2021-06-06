package fr.java.draw.styles;

public enum PointSkin {
	Invisible		( 0, ' '),
	Dot				( 1, '.'),
	Plus			( 2, '+'),
	Cross			( 3, 'x'),
	Star			( 4, '*'), 
	OpenedCircle	(10, 'o'), 
	OpenedSquare	(20, 's'), 
	OpenedDiamond	(30, 'd'), 
	FilledCircle	(11, 'O'), 
	FilledSquare	(21, 'S'), 
	FilledDiamond	(31, 'D');

	int		value;
	char	symbol;

	PointSkin(int _value, char _symbol) {
		value = _value;
	}

	public static PointSkin fromValue(int _value) {
		switch (_value) {
		case 0: return Invisible;
		case 1: return OpenedCircle;
		case 2: return OpenedSquare;
		case 3: return OpenedDiamond;
		case 4: return FilledCircle;
		case 5: return FilledSquare;
		case 6: return FilledDiamond;
		case 7: return Cross;
		case 8: return Plus;
		default: return Dot;
		}

	}
}
