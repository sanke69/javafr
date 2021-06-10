package fr.java.maths.geometry.plane.transformations.special;

import fr.java.maths.geometry.plane.transformations.Transformation2D;

public class Symetry2D extends Transformation2D {

	public static final Symetry2D newSymetryOnX() {
		Symetry2D symOnX = new Symetry2D();

		symOnX.m.m00 =  1.0f; symOnX.m.m01 =  0.0f; symOnX.m.m02 =  0.0f;
		symOnX.m.m10 =  0.0f; symOnX.m.m11 = -1.0f; symOnX.m.m12 =  0.0f;
		symOnX.m.m20 =  0.0f; symOnX.m.m21 =  0.0f; symOnX.m.m22 =  1.0f;

		return symOnX;
	}
	public static final Symetry2D newSymetryOnY() {
		Symetry2D symOnY = new Symetry2D();

		symOnY.m.m00 = -1.0f; symOnY.m.m01 =  0.0f; symOnY.m.m02 =  0.0f;
		symOnY.m.m10 =  0.0f; symOnY.m.m11 =  1.0f; symOnY.m.m12 =  0.0f;
		symOnY.m.m20 =  0.0f; symOnY.m.m21 =  0.0f; symOnY.m.m22 =  1.0f;

		return symOnY;
	}
	public static final Symetry2D newSymetryFromOrigin() {
		Symetry2D symFromO = new Symetry2D();

		symFromO.m.m00 = -1.0f; symFromO.m.m01 =  0.0f; symFromO.m.m02 =  0.0f;
		symFromO.m.m10 =  0.0f; symFromO.m.m11 = -1.0f; symFromO.m.m12 =  0.0f;
		symFromO.m.m20 =  0.0f; symFromO.m.m21 =  0.0f; symFromO.m.m22 =  1.0f;
		
		return symFromO;
	}

	Symetry2D() {
		super();
	}
}
