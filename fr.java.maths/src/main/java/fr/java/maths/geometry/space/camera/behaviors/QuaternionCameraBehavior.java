package fr.java.maths.geometry.space.camera.behaviors;

import java.util.Optional;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.geometry.space.camera.CameraBehavior;

public interface QuaternionCameraBehavior extends CameraBehavior {
/*
	public Matrix44d 			getTransformationMatrix();
	public default Matrix44d 	getViewMatrix() {
		return getTransformationMatrix().inverse();
	}
*/
	
//	public Matrix44d 			getViewMatrix();
	
	
		// Orientation
	public void 				lookAt	(Point3D _position, Point3D _target, Vector3D _up);
	public void 				lookAt	(Point3D _target, Vector3D _up);
	public void 				headUp	(Vector3D _up);

		// Movement
	public void 				move	(float _step);
	public void 				strafe	(float _step);
	public void 				raise	(float _step);

	public void 				yawed	(float _deg);
	public void 				pitched	(float _deg);
	public void 				rolled	(float _deg);

	// Tracking
	public Optional<Point3D> 	getTarget();

	public void					lock(Point3D _target);
	public void					unlock();


}
