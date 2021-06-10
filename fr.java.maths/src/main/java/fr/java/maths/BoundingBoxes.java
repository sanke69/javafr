package fr.java.maths;

import java.util.Collection;
import java.util.Set;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.adapters.BoundingBoxAdapter1D;
import fr.java.maths.adapters.BoundingBoxAdapter2D;
import fr.java.maths.adapters.BoundingBoxAdapter3D;
import fr.java.maths.adapters.EditableBoundingBoxAdapter1D;
import fr.java.maths.adapters.EditableBoundingBoxAdapter2D;
import fr.java.maths.adapters.EditableBoundingBoxAdapter3D;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.java.maths.geometry.space.shapes.SimpleCube3D;

public final class BoundingBoxes {

	public static final BoundingBox.TwoDims.Editable 	empty2() {
		return ofEditable(0, 0, 0, 0);
	}
	public static final BoundingBox.ThreeDims.Editable 	empty3() {
		return ofEditable(0, 0, 0, 0, 0, 0);
	}

	public static final BoundingBox.TwoDims.Editable 	unit2() {
		return ofEditable(0, 0, 1, 1);
	}
	public static final BoundingBox.ThreeDims.Editable 	unit3() {
		return ofEditable(0, 0, 0, 1, 1, 1);
	}

	public static final BoundingBox.OneDim 				of(final double _x, final double _width) {
		return new BoundingBoxAdapter1D(_x, _width);
	}
	public static final BoundingBox.OneDim.Editable 	ofEditable(final double _x, final double _width) {
		return new EditableBoundingBoxAdapter1D(_x, _width);
	}

	public static final BoundingBox.TwoDims 			of(final double _x, final double _y, final double _width, final double _height) {
		return new BoundingBoxAdapter2D(_x, _y, _width, _height);
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final double _x, final double _y, final double _width, final double _height) {
		return new EditableBoundingBoxAdapter2D(_x, _y, _width, _height);
	}

	public static final BoundingBox.ThreeDims 			of(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		return new BoundingBoxAdapter3D(_x, _y, _z, _width, _height, _depth);
	}
	public static final BoundingBox.ThreeDims.Editable 	ofEditable(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		return new EditableBoundingBoxAdapter3D(_x, _y, _z, _width, _height, _depth);
	}

	// 2D Specialization
	public static BoundingBox.TwoDims 					of2D(final double _size) {
		return of(0, 0, _size, _size);
	}
	public static BoundingBox.TwoDims 					of2D(final double _width, final double _height) {
		return of(0, 0, _width, _height);
	}

	public static final BoundingBox.TwoDims 			of(final Dimension.TwoDims _dimension) {
		return of(0, 0, _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims 			of(final Coordinate.TwoDims _topLeft, final Coordinate.TwoDims _bottomRight) {
		return of(_topLeft.getFirst(), _topLeft.getSecond(), _bottomRight.getFirst() - _topLeft.getFirst(), _bottomRight.getSecond() - _topLeft.getSecond());
	}
	public static final BoundingBox.TwoDims 			of(final Coordinate.TwoDims _position, final Dimension.TwoDims _dimension) {
		return of(_position.getFirst(), _position.getSecond(), _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final Dimension.TwoDims _dimension) {
		return ofEditable(0, 0, _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final Coordinate.TwoDims _topLeft, final Coordinate.TwoDims _bottomRight) {
		return ofEditable(_topLeft.getFirst(), _topLeft.getSecond(), _bottomRight.getFirst() - _topLeft.getFirst(), _bottomRight.getSecond() - _topLeft.getSecond());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final Coordinate.TwoDims _position, final Dimension.TwoDims _dimension) {
		return ofEditable(_position.getFirst(), _position.getSecond(), _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox.TwoDims.Editable 	ofEditable(final BoundingBox.TwoDims _bbox) {
		return ofEditable(_bbox.getX(), _bbox.getY(), _bbox.getWidth(), _bbox.getHeight());
	}

	public static BoundingBox.TwoDims 					of(Coordinate.TwoDims... _points) {
		double minY = Integer.MAX_VALUE;
		double minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;

		for(Coordinate.TwoDims position : _points) {
			minY = Math.min(minY, position.getSecond());
			minX = Math.min(minX, position.getFirst());
			maxY = Math.max(maxY, position.getSecond());
			maxX = Math.max(maxX, position.getFirst());
		}
        
		return fromBounds(minX, minY, maxX, maxY);
	}
	public static BoundingBox.TwoDims 					of(Collection<Point2D> _points) {
		double minY = Integer.MAX_VALUE;
		double minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;

		for(Coordinate.TwoDims position : _points) {
			minY = Math.min(minY, position.getSecond());
			minX = Math.min(minX, position.getFirst());
			maxY = Math.max(maxY, position.getSecond());
			maxX = Math.max(maxX, position.getFirst());
		}
        
		return fromBounds(minX, minY, maxX, maxY);
	}

	public static BoundingBox.TwoDims 					fromBounds(double _minX, double _minY, double _maxX, double _maxY) {
		if(_maxX < _minX) {
			double tmp = _minX;
			_minX = _maxX;
			_maxX = tmp;
		}
		if(_maxY < _minY) {
			double tmp = _minY;
			_minY = _maxY;
			_maxY = tmp;
		}
		return of(_minX, _minY, _maxX - _minX, _maxY - _minY);
	}
	public static BoundingBox.TwoDims.Editable 			fromBoundsEditable(double _minX, double _minY, double _maxX, double _maxY) {
		if(_maxX < _minX) {
			double tmp = _minX;
			_minX = _maxX;
			_maxX = tmp;
		}
		if(_maxY < _minY) {
			double tmp = _minY;
			_minY = _maxY;
			_maxY = tmp;
		}
		return ofEditable(_minX, _minY, _maxX - _minX, _maxY - _minY);
	}

	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scale) {
		return of(_bbox.getX(), _bbox.getY(), _scale * _bbox.getWidth(), _scale * _bbox.getHeight());
	}
	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scalex, double _scaley) {
		return of(_bbox.getX(), _bbox.getY(), _scalex * _bbox.getWidth(), _scaley * _bbox.getHeight());
	}

	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scale, boolean _scaledPosition) {
		return _scaledPosition ? 
					of(_scale * _bbox.getX(), _scale * _bbox.getY(), _scale * _bbox.getWidth(), _scale * _bbox.getHeight())
					:
					of(_scale * _bbox.getX(), _scale * _bbox.getY(), _scale * _bbox.getWidth(), _scale * _bbox.getHeight());
	}
	public static final BoundingBox.TwoDims				scaled(BoundingBox.TwoDims _bbox, double _scalex, double _scaley, boolean _scaledPosition) {
		return _scaledPosition ? 
					of(_bbox.getX(), _bbox.getY(), _scalex * _bbox.getWidth(), _scaley * _bbox.getHeight())
					:
					of(_scalex * _bbox.getX(), _scaley * _bbox.getY(), _scalex * _bbox.getWidth(), _scaley * _bbox.getHeight());
	}
	
	//
	
	/*
	public static final BoundingBox2D 					of2(double _size) {
		return of(0, 0, _size, _size);
	}
	public static final BoundingBox2D.Editable 			ofEditable2(double _size) {
		return ofEditable(0, 0, _size, _size);
	}

	public static final BoundingBox2D 					of(Dimension.TwoDims _dimension) {
		return ofEditable(0, 0, _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox2D.Editable 			ofEditable(Dimension.TwoDims _dimension) {
		return ofEditable(0, 0, _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox2D.Editable 			ofEditable(BoundingBox.TwoDims _bbox) {
		return ofEditable(_bbox.getX(), _bbox.getY(), _bbox.getWidth(), _bbox.getHeight());
	}

	public static final BoundingBox2D 					of(Coordinate.TwoDims _topLeft, Coordinate.TwoDims _bottomRight) {
		return of(_topLeft.getFirst(), _topLeft.getSecond(), _bottomRight.getFirst() - _topLeft.getFirst(), _bottomRight.getSecond() - _topLeft.getSecond());
	}
	public static final BoundingBox2D 					of(Coordinate.TwoDims _position, Dimension.TwoDims _dimension) {
		return of(_position.getFirst(), _position.getSecond(), _dimension.getWidth(), _dimension.getHeight());
	}
	public static final BoundingBox2D.Editable 			ofEditable(Point2D _position, Dimension.TwoDims _dimension) {
		return ofEditable(_position.getX(), _position.getY(), _dimension.getWidth(), _dimension.getHeight());
	}
	*/
	
	
	
	
/*	public static BoundingBox.TwoDims.Editable 		of(double _x, double _y, double _w, double _h) {
		return new SimpleRectangle2D(_x, _y, _w, _h);
	}*/


	public static BoundingBox.ThreeDims.Editable 	of(double _width, double _height, double _depth) {
		return new SimpleCube3D(_width, _height, _depth);
	}
	public static BoundingBox.ThreeDims.Editable 	of(Dimension3D _dimension) {
		return new SimpleCube3D(_dimension.getWidth(), _dimension.getHeight(), _dimension.getDepth());
	}
	
	public static BoundingBox.ThreeDims.Editable 	of(Point3D _position, Dimension3D _dimension) {
		return new SimpleCube3D(_position, _dimension);
	}
	public static BoundingBox.ThreeDims.Editable 	of(Set<Point3D> _points) {
		return new SimpleCube3D(_points);
	}

	
	
	
	
	
	public static BoundingBox.TwoDims.Editable 		newRectangular() {
		return new SimpleRectangle2D(0, 0, 1, 1);
	}

	public static BoundingBox3D 					fromBounds(double _minX, double _minY, double _minZ, double _maxX, double _maxY, double _maxZ) {
		if(_maxX < _minX) {
			double tmp = _minX;
			_minX = _maxX;
			_maxX = tmp;
		}
		if(_maxY < _minY) {
			double tmp = _minY;
			_minY = _maxY;
			_maxY = tmp;
		}
		if(_maxZ < _minZ) {
			double tmp = _minZ;
			_minZ = _maxZ;
			_maxZ = tmp;
		}
		return new SimpleCube3D(_minX, _minY, _minZ, _maxX - _minX, _maxY - _minY, _maxZ - _minZ);
	}

}