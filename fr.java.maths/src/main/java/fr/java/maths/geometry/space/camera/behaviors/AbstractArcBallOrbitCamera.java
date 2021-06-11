package fr.java.maths.geometry.space.camera.behaviors;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.maths.algebra.matrices.DoubleMatrix44;
import fr.java.maths.geometry.space.transformations.specials.Translation3D;
import fr.java.maths.geometry.space.utils.Quaternion;

// https://www.3dgep.com/understanding-the-view-matrix/
public class AbstractArcBallOrbitCamera {

	Vector3D 	position;
	Quaternion 	rotation;
	Vector3D 	target;
	
	DoubleMatrix44 getViewMatrix() {
		DoubleMatrix44 T0 = Translation3D.of( position ).asUniformMatrix(); 	// Translation away from object.
		DoubleMatrix44 R  = rotation.toMatrix();     						// Rotate around object.
		DoubleMatrix44 T1 = Translation3D.of( target ).asUniformMatrix(); 	// Translate to center of object.
	
		DoubleMatrix44 viewMatrix = T1.times(R).times(T0).inverse();
	
		return viewMatrix;
	}

}
