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
 * @file     ResizeableBean.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.patterns;

import fr.java.beans.impl.BooleanBeanProperty;
import fr.java.beans.impl.DoubleBeanProperty;
import fr.java.beans.impl.ReadOnlyBooleanBeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.patterns.displayable.Resizeable;
import fr.java.patterns.displayable.Resizeable.OneDim;
import fr.java.patterns.displayable.Resizeable.ThreeDims;
import fr.java.patterns.displayable.Resizeable.TwoDims;

@Deprecated
public interface ResizeableBean extends Resizeable {

	public interface Editable extends ResizeableBean, Resizeable.Editable {

		public BooleanBeanProperty 			resizeableProperty();

	}
	public interface OneDim extends Resizeable {

		public interface Editable extends OneDim, ResizeableBean.Editable {

			public DoubleBeanProperty 		widthProperty();

		}

		public DoubleBeanProperty 			widthProperty();

	}
	public interface TwoDims extends Resizeable.TwoDims {

		public interface Editable extends TwoDims, OneDim.Editable {

			public DoubleBeanProperty 		heightProperty();

		}

		public DoubleBeanProperty 			heightProperty();

	}
	public interface ThreeDims extends Resizeable.TwoDims {

		public interface Editable extends ThreeDims, TwoDims.Editable {

			public DoubleBeanProperty 		depthProperty();

		}

		public DoubleBeanProperty 			depthProperty();

	}

	public ReadOnlyBeanProperty<Boolean> 	resizingProperty();
//	public ReadOnlyBooleanBeanProperty 		resizingProperty();

	/**
	 * Returns the resize property.
	 *
	 * @return the minimize property
	 */
	public ReadOnlyBooleanBeanProperty 		resizeableProperty();

}
