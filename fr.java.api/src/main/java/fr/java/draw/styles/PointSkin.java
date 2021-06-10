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
