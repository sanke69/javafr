package fr.java.maths.geometry.space.camera;

import fr.java.maths.geometry.space.camera.projections.Frustum3D;
import fr.java.maths.geometry.space.camera.projections.Ortho3D;
import fr.java.maths.geometry.space.camera.projections.Perspective3D;
import fr.java.maths.geometry.space.transformations.Transformation3D;

public abstract class Projections3D extends Transformation3D {

    public static Ortho3D ortho(double _left, double _right, double _bottom, double _top, double _zNear, double _zFar) {
        return new Ortho3D(_left, _right, _bottom, _top, _zNear, _zFar);
    }

    public static Frustum3D frustum(double _left, double _right, double _bottom, double _top, double _zNear, double _zFar) {
        return new Frustum3D(_left, _right, _bottom, _top, _zNear, _zFar);
    }

    public static Perspective3D perspective(double _fovy, double _aspect, double _zNear, double _zFar) {                                   
        return new Perspective3D(_fovy, _aspect, _zNear, _zFar);
    }
  
}
