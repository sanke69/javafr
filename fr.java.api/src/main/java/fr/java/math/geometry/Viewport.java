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
 * @file     Viewport2D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry;

import java.util.function.Function;

import fr.java.math.topology.Coordinate;
import fr.java.math.window.Edges2D;

/**
 * Viewport
 * 
 * An object to 
 * @author sanke
 *
 * @param <MODEL>
 * @param <COORD>
 * @param <VP_BOUNDS>
 * @param <VP_COORD>
 * @see fr.java.math.geometry.plane.
 */
public interface Viewport<MODEL, COORD extends Coordinate, VP_BOUNDS extends BoundingBox, VP_COORD extends Coordinate> {

	public static interface Editable<MODEL, COORD extends Coordinate, VP_BOUNDS extends BoundingBox, VP_COORD extends Coordinate> extends Viewport<MODEL, COORD, VP_BOUNDS, VP_COORD> {

		public void 					setModel				(MODEL _model);
		public void 					setModel				(MODEL _model, Function<MODEL, VP_BOUNDS> _modelBounds);
		public void 					setModel				(MODEL _model, Function<MODEL, VP_BOUNDS> _modelBounds, Projector<COORD, VP_COORD> _modelProjector);

		public void 					setModelBounder			(Function<MODEL, VP_BOUNDS> _modelBounds);
		public void 					setModelProjector		(Projector<COORD, VP_COORD> _modelProjector);

	}

	public static interface TwoDims<MODEL, COORD extends Coordinate> extends Viewport<MODEL, COORD, BoundingBox.TwoDims, Coordinate.TwoDims> {
	
		public static interface Editable<MODEL, COORD extends Coordinate> extends TwoDims<MODEL, COORD>, Viewport.Editable<MODEL, COORD, BoundingBox.TwoDims, Coordinate.TwoDims> {

			public void 										setWindow				(Dimension.TwoDims _window);
	
			public void											setEdges				(Edges2D _edges);
			public void											setMinScale				(double _min_scale);
			public void											setMaxScale				(double _max_scale);
	
			public void 										fitWindowToModel		();
			public void 										fitModelToWindow		();
	
			public void 										translateView			(double _dx, double _dy);
			public void 										translateView			(Coordinate.TwoDims _delta);
	
			public void 										scaleView				(double _scale, double _cx, double _cy);
			public void 										scaleView				(double _scale, Coordinate.TwoDims _cursor);
	
		}

		@Override public Dimension.TwoDims			getWindow();

		public double 								getViewScale();
		public double 								getViewRatioX();
		public double 								getViewRatioY();
		public Coordinate.TwoDims 					getViewAnchor();
	
		public Edges2D 								getEdges();
	
		public double 								getMinScale();
		public double 								getMaxScale();
	
		/**
		 * Return true if one of the dimension of the model are over the dimension of the window
		 * @return true if one of the dimension of the model are over the dimension of the window
		 */
		public default boolean 						scaledModelOverWindow() {
			if( getModelBounds() == null || getWindow() == null )
				return false;
	
			double mw = getModelBounds().getWidth();
			double mh = getModelBounds().getHeight();
			double ww = getWindow().getWidth();
			double wh = getWindow().getHeight();
			double vs = getViewScale();
	
			boolean bow_w = vs * mw > ww;
			boolean bow_h = vs * mh > wh;

			return bow_w && bow_h;
		}
		public default boolean 						scaledModelOverWindowX() {
			if( getModelBounds() == null || getWindow() == null )
				return false;
	
			double mw = getModelBounds().getWidth();
			double ww = getWindow().getWidth();
			double vs = getViewScale();

			return vs * mw > ww;
		}
		public default boolean 						scaledModelOverWindowY() {
			if( getModelBounds() == null || getWindow() == null )
				return false;
	
			double mh = getModelBounds().getHeight();
			double wh = getWindow().getHeight();
			double vs = getViewScale();

			return vs * mh > wh;
		}

		// Internal Operator XD, X = 2
		public COORD 								boundsInModel  (Coordinate.TwoDims _cursor);
		public Coordinate.TwoDims 					boundsInView   (Coordinate.TwoDims _bounds_point);
		public default Coordinate.TwoDims 			boundsInWindow (Coordinate.TwoDims _bounds_point)	{ return viewInWindow(boundsInView(_bounds_point)); }

		public default COORD 						viewInModel    (Coordinate.TwoDims _view_point)     { return boundsInModel(viewInBounds(_view_point)); }
		public Coordinate.TwoDims 					viewInBounds   (Coordinate.TwoDims _view_point);
		public Coordinate.TwoDims 					viewInWindow   (Coordinate.TwoDims _view_point);

		public default COORD 						windowInModel  (Coordinate.TwoDims _cursor)		    { return viewInModel(windowInView(_cursor)); }
		public Coordinate.TwoDims					windowInView   (Coordinate.TwoDims _cursor);

		// External Operator ?D <-> XD, X = 2
		public Coordinate.TwoDims 					modelInBounds  (COORD _model_point);
		public default Coordinate.TwoDims 			modelInWindow  (COORD _model_point) 	            { return boundsInWindow( modelInBounds( _model_point ) ); }
		public default Coordinate.TwoDims 			modelInView    (COORD _model_point) 	            { return boundsInView(   modelInBounds( _model_point ) ); }

	}

	public MODEL									getModel();
	public Function<MODEL, VP_BOUNDS> 				getModelBounder();	
	public Projector<COORD, VP_COORD> 				getModelProjector();
	
	public Dimension								getWindow();

	/**
	 * Get the original bounding box of the model
	 * 
	 */
	public VP_BOUNDS 								getModelBounds();
	public VP_BOUNDS 								getModelBounds(boolean _visible);

	/**
	 * View bounds depends on the current ratio between 
	 * the relative bounds of the model compared to the window ones
	 * @param VP_BOUNDS a sub-class of BoundingBox 
	 * @return the current view bounds
	 * @see fr.java.math.geometry.BoundingBox
	 */
	public VP_BOUNDS 								getViewBounds();
	/**
	 * View bounds depends on the current ratio between 
	 * the relative bounds of the model compared to the window ones
	 * @param VP_BOUNDS a sub-class of BoundingBox 
	 * @param _withEdges include or not 
	 * @return the current view bounds
	 * @see fr.java.math.geometry.BoundingBox
	 */
	public VP_BOUNDS 								getViewBounds(boolean _withEdges);

	// Internal Operator XD, X = 2
	public COORD 									boundsInModel  (VP_COORD _cursor);
	public VP_COORD 								boundsInView   (VP_COORD _bounds_point);
	public default VP_COORD 						boundsInWindow (VP_COORD _bounds_point)	{ return viewInWindow(boundsInView(_bounds_point)); }

	public default COORD 							viewInModel    (VP_COORD _view_point)   { return boundsInModel(viewInBounds(_view_point)); }
	public VP_COORD 								viewInBounds   (VP_COORD _view_point);
	public VP_COORD 								viewInWindow   (VP_COORD _view_point);

	public default COORD 							windowInModel  (VP_COORD _cursor)		{ return viewInModel(windowInView(_cursor)); }
	public VP_COORD									windowInView   (VP_COORD _cursor);

	// External Operator ?D <-> XD, X = 2
	public VP_COORD 								modelInBounds  (COORD _model_point);
	public default VP_COORD 						modelInWindow  (COORD _model_point) 	{ return boundsInWindow( modelInBounds( _model_point ) ); } // { return viewInWindow( modelInView( _model_point ) ); }
	public default VP_COORD 						modelInView    (COORD _model_point) 	{ return boundsInView(   modelInBounds( _model_point ) ); }

}
