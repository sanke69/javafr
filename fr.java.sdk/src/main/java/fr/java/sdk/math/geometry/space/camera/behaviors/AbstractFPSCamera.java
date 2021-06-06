package fr.java.sdk.math.geometry.space.camera.behaviors;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.matrices.Matrix44d;

public class AbstractFPSCamera /*implements CameraBehavior*/ {
	Point3D position;
	double  pitch, yaw;

	Matrix44d viewMatrix;

	public Matrix44d getTransformationMatrix() {
		return getViewMatrix().inverse();
	}
	public Matrix44d getViewMatrix() {
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
	 
	    viewMatrix = Matrix44d.from(xaxis, yaxis, zaxis, eye);
	}

}
