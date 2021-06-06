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
 * @file     MoveableBean.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.patterns;

import fr.java.beans.annotations.BeanInterface;
import fr.java.beans.impl.DoubleBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.patterns.displayable.Moveable;

@Deprecated
@BeanInterface
public interface MoveableBean extends Moveable {

	public interface Editable extends MoveableBean, Moveable.Editable {

		public BeanProperty<Boolean> 		movableProperty();

	}
	public interface OneDim   extends MoveableBean {

		public DoubleBeanProperty 			xProperty();

	}
	public interface TwoDims  extends MoveableBean, Moveable.TwoDims {

		public DoubleBeanProperty 			yProperty();

	}

	/**
	 * Returns the movable property.
	 *
	 * @return the movable property
	 */
//	public BooleanBeanProperty 				movableProperty();
	public ReadOnlyBeanProperty<Boolean> 	movableProperty();

	/**
	 * Indicates whether this object is currently moving.
	 *
	 * @return the movingProperty
	 */
//	public ReadOnlyBooleanBeanProperty 		movingProperty();
	public ReadOnlyBeanProperty<Boolean> 	movingProperty();

//	public DoubleBeanProperty 				xProperty();
//	public DoubleBeanProperty 				yProperty();

}
