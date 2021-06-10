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
package fr.java.patterns;

import fr.java.beans.properties.BeanProperty;
import fr.java.patterns.displayable.Positionable;

@Deprecated
public interface PositionableBean extends Positionable {
	public static final String X              = "X";
	public static final String Y              = "Y";
	public static final String WIDTH          = "Width";
	public static final String HEIGHT         = "Height";

	public BeanProperty<Double>	xProperty();
	public BeanProperty<Double>	yProperty();
	public BeanProperty<Double>	widthProperty();
	public BeanProperty<Double>	heightProperty();

}
