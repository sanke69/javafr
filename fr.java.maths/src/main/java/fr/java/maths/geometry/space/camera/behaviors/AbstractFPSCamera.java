package fr.java.maths.geometry.space.camera.behaviors;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.DoubleMatrix44;
import fr.java.maths.geometry.types.Points;

public class AbstractFPSCamera /*implements CameraBehavior*/ {
	Point3D position;
	double  pitch, yaw;

	DoubleMatrix44 viewMatrix;

	public DoubleMatrix44 getTransformationMatrix() {
		return getViewMatrix().inverse();
	}
	public DoubleMatrix44 getViewMatrix() {
		updateViewMatrix();
	    return viewMatrix;
	}
	
	protected void updateViewMatrix() {
	    double cosPitch = Math.cos(pitch);
	    double sinPitch = Math.sin(pitch);
	    double cosYaw   = Math.cos(yaw);
	    double sinYaw   = Math.sin(yaw);

	    Point3D  eye   = Points.of( cosYaw, 0, -sinYaw );
	    Vector3D xaxis = Vectors.of( cosYaw, 0, -sinYaw );
	    Vector3D yaxis = Vectors.of( sinYaw * sinPitch, cosPitch, cosYaw * sinPitch );
	    Vector3D zaxis = Vectors.of( sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw );
	 
	    viewMatrix = DoubleMatrix44.from(xaxis, yaxis, zaxis, eye);
	}

}
