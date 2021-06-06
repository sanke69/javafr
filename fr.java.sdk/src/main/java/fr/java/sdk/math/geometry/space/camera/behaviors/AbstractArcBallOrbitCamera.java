package fr.java.sdk.math.geometry.space.camera.behaviors;

import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.algebra.matrices.Matrix44d;
import fr.java.sdk.math.geometry.space.transformations.specials.Translation3D;
import fr.java.sdk.math.geometry.space.utils.Quaternion;

// https://www.3dgep.com/understanding-the-view-matrix/
public class AbstractArcBallOrbitCamera {

	Vector3D 	position;
	Quaternion 	rotation;
	Vector3D 	target;
	
	Matrix44d getViewMatrix() {
		Matrix44d T0 = Translation3D.of( position ).asUniformMatrix(); 	// Translation away from object.
		Matrix44d R  = rotation.toMatrix();     						// Rotate around object.
		Matrix44d T1 = Translation3D.of( target ).asUniformMatrix(); 	// Translate to center of object.
	
		Matrix44d viewMatrix = T1.times(R).times(T0).inverse();
	
		return viewMatrix;
	}

}
