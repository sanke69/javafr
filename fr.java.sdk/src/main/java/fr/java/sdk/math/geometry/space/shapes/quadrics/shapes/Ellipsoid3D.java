/** ************************************************************************ **\
 * Copyright (c) 2007-?XYZ Steve PECHBERTI                                    *
 *                                                                            *
 * @author <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>   *
 *                                                                            *
 * @section license License                                                   *
 *    [EN] This program is free software:                                     *
 *         you can redistribute it and/or modify it under the terms of        * 
 *         the GNU General Public License as published by                     *
 *         the Free Software Foundation, either version 3 of the License, or  *
 *         (at your option) any later version.                                *
 *         You should have received a copy of the GNU General Public License  *
 *         along with this program. If not, see                               *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *    [FR] Ce programme est un logiciel libre ; vous pouvez le redistribuer   * 
 *         ou le modifier suivant les termes de la GNU General Public License *
 *         telle que publiée par la Free Software Foundation ;                *
 *         soit la version 3 de la licence, soit (à votre gré) toute version  *
 *         ultérieure.                                                        *
 *         Vous devez avoir reçu une copie de la GNU General Public License   *
 *         en même temps que ce programme ; si ce n'est pas le cas, consultez *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *                                                                            *
 * @section disclaimer Disclaimer                                             *
 *    [EN] This program is distributed in the hope that it will be useful,    *
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.               *
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,         *
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de      *
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.        *
 *                                                                            *
\** ************************************************************************ **/
package fr.java.sdk.math.geometry.space.shapes.quadrics.shapes;

import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.geometry.space.shapes.quadrics.Quadric3DBase;
import fr.java.sdk.math.geometry.space.shapes.quadrics.QuadricShape3D;
import fr.java.sdk.math.geometry.space.types.SimpleDimension3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;

// https://en.wikipedia.org/wiki/Geodesics_on_an_ellipsoid#Triaxial_coordinate_systems
public class Ellipsoid3D extends Quadric3DBase implements QuadricShape3D {
	private static final long serialVersionUID = 1404554009084171544L;

	public static Ellipsoid3D from(Point3D _center, Dimension3D _dimensions) {
		double A = 1 / (_dimensions.getWidth()*_dimensions.getWidth());
		double B = 1 / (_dimensions.getHeight()*_dimensions.getHeight());
		double C = 1 / (_dimensions.getDepth()*_dimensions.getDepth());
		double G = - 2 * _center.getX() * A;
		double H = - 2 * _center.getY() * B;
		double I = - 2 * _center.getZ() * C;
		double J = _center.getX()*_center.getX() * A + _center.getY()*_center.getY() * B + _center.getZ()*_center.getZ() * C - 1;

		Ellipsoid3D result = new Ellipsoid3D(A, B, C, G, H, I, J);
		result.center     = _center;
		result.dimensions = _dimensions;
		
		return result;
	}

	Point3D     center;
	Dimension3D dimensions;

	private Ellipsoid3D(double _x2, double _y2, double _z2, double _x, double _y, double _z, double _cst) {
		super(_x2, _y2, _z2, 0, 0, 0, _x, _y, _z, _cst);
	}
	public  Ellipsoid3D() {
		super(1, 1, 1, 0, 0, 0, 0, 0, 0, -1);
		center     = Points.zero3();
		dimensions = SimpleDimension3D.unit();
	}

	public Point3D getCenter() 			{ return center; }

	public QuadricEquation 				getEquation() {
		return (_x, _y, _z) -> {
			return getCoef(COEF.X2) * _x*_x + getCoef(COEF.Y2) * _y*_y + getCoef(COEF.Z2) * _z*_z
					+ getCoef(COEF.UN);
		};
	}

	/**
	 * From polar coordinate <img src="http://latex.codecogs.com/png.latex?(\rho, \theta)"/> <br/>
	 * with <img src="http://latex.codecogs.com/png.latex?
	 * 		\rho \in [ - \pi / 2, \pi / 2 ]"
	 * /> and <img src="http://latex.codecogs.com/png.latex?
	 * 		\theta \in [ - \pi, \pi ]"
	 * /> <br/>
	 * then <img src="http://latex.codecogs.com/png.latex?
	 * 		\left\{\begin{matrix}
			x & = & w . cos(\rho) . cos(\theta) \\ 
			y & = & h . cos(\rho) . sin(\theta) \\ 
			z & = & d . sin(\rho)
			\end{matrix}\right.
	 * /> <br/>
	 * 
	 * see https://en.wikipedia.org/wiki/Ellipsoid
	 */
	public QuadricShapeParametrization 	getParametrization() {
		return (_rho, _phi) -> {
			double w  = dimensions.getWidth(),	h  = dimensions.getHeight(), 	d  = dimensions.getDepth();
			double cx = center.getX(), 			cy = center.getY(), 			cz = center.getZ();

//			double x = cx + w * Math.sin(_rho) * Math.cos(_theta);
//			double y = cy + h * Math.sin(_rho) * Math.sin(_theta);
//			double z = cz + d * Math.cos(_rho);

			double x = cx + w * Math.cos(_rho) * Math.cos(_phi);
			double y = cy + h * Math.cos(_rho) * Math.sin(_phi);
			double z = cz + d * Math.sin(_rho);

			return Points.of(x, y, z);
		};
	}
	/**
	 * From polar coordinate <img src="http://latex.codecogs.com/png.latex?(\rho, \phi)"/> <br/>
	 * with <img src="http://latex.codecogs.com/png.latex?
	 * 		\rho \in [ - \pi / 2, \pi / 2 ]"
	 * /> and <img src="http://latex.codecogs.com/png.latex?
	 * 		\phi \in [ - \pi, \pi ]"
	 * /> <br/>
	 * then <img src="http://latex.codecogs.com/png.latex?
	 * 		\left\{\begin{matrix}
			x & = & w . cos(\rho) . cos(\phi) \\ 
			y & = & h . cos(\rho) . sin(\phi) \\ 
			z & = & d . sin(\rho)
			\end{matrix}\right.
	 * /> <br/>
	 * 
	 * see https://en.wikipedia.org/wiki/Ellipsoid
	 */
	public Point3D 						getSurfacePoint(double _rho, double _phi) { return getParametrization().get(_rho, _phi); }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[ELLIPSOID: c= ]");
		
		return sb.toString();
	}


	@Override
	public boolean intersects(SimpleRay3D _ray) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Point3D _pt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(BoundingBox3D _bbox) {
		// TODO Auto-generated method stub
		return false;
	}

}
