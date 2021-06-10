package fr.java.maths.geometry.space.camera;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Point3D;

public interface CameraModel {

	public Point2D 		inImage(Point3D _pt);
//	public Ray3D 		inSpace(Point2D _pxl);

}
