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

import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Pen;

public class LineStyle {
	double   width; // thickness
	double[] dashPattern;
	Brush    brush;

	public static LineStyle of(double _width, Brush _brush, double... _dashPattern) {
		LineStyle style = new LineStyle();
		style.width = _width;
		style.brush = _brush;
		
		if(_dashPattern != null)
			style.dashPattern = _dashPattern.clone();
		
		return style;
	}
	
	
	
	public LineStyle() {
		super();
		width = 1d;
	}
	
	public double 		getWidth() {
		return width;
	}

	public boolean 		hasDashPattern() {
		return dashPattern != null;
	}
	public double[] 	getDashPattern() {
		return dashPattern;
	}

	public Pen 			getPen() {
		return brush;
	}
	public Brush 		getBrush() {
		return brush;
	}
	
}
