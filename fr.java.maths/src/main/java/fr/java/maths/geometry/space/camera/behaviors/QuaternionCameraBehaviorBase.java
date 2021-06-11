package fr.java.maths.geometry.space.camera.behaviors;

import java.util.Optional;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Frame3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.DoubleMatrix44;
import fr.java.maths.geometry.Space;
import fr.java.maths.geometry.space.camera.CameraBehavior;
import fr.java.maths.geometry.space.utils.Quaternion;

public abstract class QuaternionCameraBehaviorBase implements CameraBehavior {
	Frame3D frame;
	Point3D	target;

	public QuaternionCameraBehaviorBase() {
		super();
		frame = Space.newFrame();
	}

	public Frame3D				getFrame() {
		return frame;
	}

	public void 				lookAt(Point3D _position, Point3D _target, Vector3D _up) {
		Vector3D back  = Vectors.of(_position.minus(_target)).normalized();				// Correct
		Vector3D right = Vectors.crossProduct(_up, back).normalized();						// Correct
		Vector3D up    = Vectors.crossProduct(back, right).normalized();					// Correct

		frame.setOrigin(_position);
		frame.setAxis(right, up, back);
	}
	public void 				lookAt(Point3D _target, Vector3D _up) {
		Vector3D back  = Vectors.of(frame.getOrigin().minus(_target)).normalized();
		Vector3D right = Vectors.crossProduct(_up, back).normalized();
		Vector3D up    = Vectors.crossProduct(back, right).normalized();

		frame.setAxis(right, up, back);
	}
	public void 				headUp(Vector3D _up) {
		Vector3D up    = _up.normalized();
		Vector3D back  = Vectors.crossProduct(frame.getXAxis(), frame.getYAxis()).normalized();
		Vector3D right = Vectors.crossProduct(frame.getYAxis(), frame.getZAxis()).normalized();
		frame.setAxis(right, up, back);
	}

	public void 				strafe(float _step) {
//		frame.getOrigin().plusEquals( frame.getXAxis().times(_step) );
		frame.setOrigin( frame.getOrigin().plus( frame.getXAxis().times(_step) ) );
	}
	public void 				raise(float _step) {
//		frame.getOrigin().plusEquals( frame.getYAxis().times(_step) );
		frame.setOrigin( frame.getOrigin().plus( frame.getYAxis().times(_step) ) );
	}
	public void 				move(float _step) {
//		frame.getOrigin().minusEquals( frame.getZAxis().times(_step) );
		frame.setOrigin( frame.getOrigin().minus( frame.getZAxis().times(_step) ) );
	}

	public void 				pitched(float _deg) {
		if(_deg != 0) {
			Vector3D   right = frame.getXAxis();
			Quaternion q     = new Quaternion(right, (float) (_deg * Math.PI / 180.0f));
			Vector3D   back  = q.transform(frame.getZAxis()).normalized();
			Vector3D   up    = q.transform(frame.getYAxis()).normalized();

			frame.setAxis(right, up, back);
		}
	}
	public void 				yawed(float _deg) {
		if(_deg != 0) {
			Vector3D   up    = frame.getYAxis();
			Quaternion q     = new Quaternion(up, (float) (_deg * Math.PI / 180.0f));
			Vector3D   right = q.transform(frame.getXAxis()).normalized();
			Vector3D   back  = q.transform(frame.getZAxis()).normalized();

			frame.setAxis(right, up, back);
		}
	}
	public void 				rolled(float _deg) {
		if(_deg != 0) {
			Vector3D   back  = frame.getZAxis();
			Quaternion q     = new Quaternion(back, (float) (_deg * Math.PI / 180.0f));
			Vector3D   right = q.transform(frame.getXAxis()).normalized();
			Vector3D   up    = q.transform(frame.getYAxis()).normalized();

			frame.setAxis(right, up, back);
		}
	}

	public Optional<Point3D> 	getTarget() {
		return target != null ? Optional.of(target) : Optional.empty();
	}
	public void					lock(Point3D _target) {
		target = _target;
	}
	public void					unlock() {
		target = null;
	}

	public DoubleMatrix44 			getTransformMatrix() {
		Point3D  O = getFrame().getOrigin();
		Vector3D X = getFrame().getXAxis();
		Vector3D Y = getFrame().getYAxis();
		Vector3D Z = getFrame().getZAxis();

		return DoubleMatrix44.from(X, Y, Z, O);
	}
	// https://www.3dgep.com/understanding-the-view-matrix/
	public DoubleMatrix44 			getViewMatrix() {
		Point3D  O = getFrame().getOrigin();
		Vector3D X = getFrame().getXAxis();
		Vector3D Y = getFrame().getYAxis().times(-1);
		Vector3D Z = getFrame().getZAxis();

		return DoubleMatrix44.from(X, Y, Z, O);
		//return getTransformMatrix().inverse();
	}

}
