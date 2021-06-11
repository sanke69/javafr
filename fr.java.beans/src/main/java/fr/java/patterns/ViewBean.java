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

import fr.java.beans.impl.BooleanBeanProperty;
import fr.java.beans.impl.DoubleBeanProperty;
import fr.java.beans.impl.ReadOnlyBooleanBeanProperty;
import fr.java.beans.impl.ReadOnlyDoubleBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.lang.properties.ID;
import fr.java.mvc.ViewModel;

public interface ViewBean extends ViewModel {

	public interface Editable extends ViewBean {

		public void 						setCloseable(boolean _enable);
		public void 						setVisible(boolean _enable);
		public void 						setMoveable(boolean v);
		public void 						setResizable(boolean v);

		public void 						setSelectable(boolean _value);
		public boolean 						requestSelection(boolean b);

//		public void 						setViewRequest(ViewRequestBean _view_req);

		public BooleanBeanProperty			visibleProperty();
		public BooleanBeanProperty	 		movableProperty();
		public BooleanBeanProperty 			resizableProperty();
	}

	public interface OneDim   extends ViewBean {

		public interface Editable extends OneDim, ViewBean.Editable {

			public void 					setX(double _x);
			public void 					setWidth(double _width);

			public DoubleBeanProperty 		xProperty();
			public DoubleBeanProperty 		widthProperty();

		}

		public double 						getX();
		public double 						getWidth();

		public ReadOnlyDoubleBeanProperty 	xProperty();
		public ReadOnlyDoubleBeanProperty 	widthProperty();

	}
	public interface TwoDims  extends ViewBean {

		public interface Editable extends ViewBean.TwoDims, ViewBean.OneDim.Editable {

			public void 					setY(double _y);
			public void 					setHeight(double _height);

			public DoubleBeanProperty 		yProperty();
			public DoubleBeanProperty 		heightProperty();

		}

		public double 						getY();
		public double 						getHeight();

		public ReadOnlyDoubleBeanProperty 	yProperty();
		public ReadOnlyDoubleBeanProperty 	heightProperty();

	}
	public interface ThreeDims extends ViewBean.TwoDims {

		public interface Editable extends ViewBean.ThreeDims, ViewBean.TwoDims.Editable {

			public void 					setZ(double _z);
			public void 					setDepth(double _depth);

			public DoubleBeanProperty 		zProperty();
			public DoubleBeanProperty 		depthProperty();

		}

		public double 						getZ();
		public double 						getDepth();

		public ReadOnlyDoubleBeanProperty 	zProperty();
		public ReadOnlyDoubleBeanProperty 	depthProperty();

	}

	public boolean 							isCloseable();
	public boolean 							isSelectable();
	public boolean 							isMoveable();
	public boolean 							isResizable();

	public boolean 							isVisible();
	public boolean 							isSelected();
	public boolean 							isMoving();
	public boolean 							isResizing();

	public boolean							isCloseRequested();

	public ID   							getId();
	ViewRequestBean 						getViewRequest();

	public ReadOnlyBooleanBeanProperty		visibleProperty();
	
	public ReadOnlyBeanProperty<ID> 		idProperty();
//	public ReadOnlyBeanProperty<ViewRequestBean> 	viewRequestProperty();

	public ReadOnlyBeanProperty<Boolean> 	closeRequestedProperty();

	public BeanProperty<Boolean> 			selectableProperty();
	public ReadOnlyBeanProperty<Boolean> 	movableProperty();
	public ReadOnlyBooleanBeanProperty 		resizableProperty();

	public ReadOnlyBeanProperty<Boolean> 	selectedProperty();
	public ReadOnlyBooleanBeanProperty		movingProperty();
	public ReadOnlyBooleanBeanProperty 		resizingProperty();

}
