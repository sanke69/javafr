package fr.java.sdk.math.geometry.space.camera.models;

import fr.java.math.algebra.NumberMatrix;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Frame3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.matrices.Matrix33d;
import fr.java.sdk.math.algebra.matrices.Matrix44d;
import fr.java.sdk.math.algebra.matrices.Matrixmnd;
import fr.java.sdk.math.geometry.Geometry;
import fr.java.sdk.math.geometry.space.camera.CameraModel;
import fr.java.sdk.math.geometry.space.shapes.quadrics.surfaces.Plane3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;
import fr.java.sdk.math.geometry.space.utils.Quaternion;
import fr.java.sdk.math.geometry.utils.SpaceTests;

public class PinholeCamera3D extends PinholeCameraParameters implements CameraModel {
	NumberMatrix  projector;

	public PinholeCamera3D() {
		super();
		frame = Geometry.Space.worldFrame.clone();
	}

	public Point2D 		inImage(Point3D _pt) {
		Point3D P = Points.of3(projector.times(_pt.uniform()) );

		double U = (P.getZ() == 0) ? P.getX() : P.getX() / P.getZ();
		double V = (P.getZ() == 0) ? P.getY() : P.getY() / P.getZ();
		
		if(!calibratedInPixel) {
			U *= pixelWidth();
			V *= pixelHeight();
		}

		V = H - V;

//		DecimalFormat df = new DecimalFormat("0.0000");
//		System.out.println(_pt.toString(df) + "\t" + "( " + df.format(U) + " ; " + df.format(V) + ")");
		
		return Points.of(U, V);
	}
	public Point3D   	inImage3D(double _px, double _py) {
		if(_px < 0 || _px > W || _py < 0 || _py > H)
			return null;

		Point3D  imgTL = getImageBoundaries()[0];
		Vector3D imgU  = getImageU();
		Vector3D imgV  = getImageV();

		return imgTL . plus(imgU.times(_px)) . plus (imgV.times(_py));
	}
	public Point3D   	inImage3D(Point2D _pxl) {
		if(_pxl.getX() < 0 || _pxl.getX() > W || _pxl.getY() < 0 || _pxl.getY() > H)
			return null;

		Point3D  imgTL = getImageBoundaries()[0];
		Vector3D imgU  = getImageU();
		Vector3D imgV  = getImageV();

		return imgTL . plus(imgU.times(_pxl.getX())) . plus (imgV.times(_pxl.getY()));
	}
	public SimpleRay3D 	inSpace(Point2D _pxl) {
		if(_pxl.getX() < 0 || _pxl.getX() > W || _pxl.getY() < 0 || _pxl.getY() > H)
			return null;

		/* From the CMOS Point of View
		 * The correct way to deal with ... *
		Vector3D dir = Vector3D.delta(
										getCmosBoundaries()[0]
											.plus(getCmosU().times(_pxl.getX()))
											.plus(getCmosV().times(- _pxl.getY())), 
										getFrame().getOrigin()).normalized();
		return new Ray3D(getFrame().getOrigin(), dir.times(-1));
		/*/ // From Image Point of View: Best to fit with rendering...
		Vector3D dir = Vectors.delta(
				getImageBoundaries()[0]
					.plus(getImageU().times(_pxl.getX()))
					.plus(getImageV().times(_pxl.getY())), 
				getFrame().getOrigin()).normalized();
		return new SimpleRay3D(getFrame().getOrigin(), dir);
		/**/
	}
	public Point3D 		onPlane(Point2D _pixel, Plane3D _plane) {
		SimpleRay3D ray = inSpace(_pixel);
		return ray != null ? SpaceTests.getIntersection(ray, _plane) : null;
	}

	public Matrix44d 	getTransformMatrix() {
		Point3D  O = getFrame().getOrigin();
		Vector3D X = getFrame().getXAxis();
		Vector3D Y = getFrame().getYAxis();
		Vector3D Z = getFrame().getZAxis();

		return Matrix44d.from(X, Y, Z, O);
	}
	// https://www.3dgep.com/understanding-the-view-matrix/
	public Matrix44d 	getViewMatrix() {
		Point3D  O = getFrame().getOrigin();
		Vector3D X = getFrame().getXAxis();
		Vector3D Y = getFrame().getYAxis().times(-1);
		Vector3D Z = getFrame().getZAxis();

		return Matrix44d.from(X, Y, Z, O);
		//return getTransformMatrix().inverse();
	}

	private void 		updateProjector_V1() {
		Matrix33d intrinsic33 = Matrix33d.identity();
		Matrixmnd focal34     = Matrixmnd.identity(3, 4);

		intrinsic33.m00 = ku();
		intrinsic33.m11 = kv();
		intrinsic33.m01 = suv();
		intrinsic33.m02 = cu();
		intrinsic33.m12 = cv();

		focal34.set(0, 0, f());
		focal34.set(1, 1, f());

		NumberMatrix intrinsic = intrinsic33.times(focal34);
		NumberMatrix extrinsic = getViewMatrix();

		projector = intrinsic.times(extrinsic);
	}
	private void 		updateProjector_V2() {
		Matrixmnd intrinsic34 = Matrixmnd.identity(3, 4);

		intrinsic34.set(0, 0, Fx());
		intrinsic34.set(1, 1, Fy());
		intrinsic34.set(0, 1, f() * suv());
		intrinsic34.set(0, 2, cu());
		intrinsic34.set(1, 2, cv());

		NumberMatrix intrinsic = intrinsic34;
		NumberMatrix extrinsic = getViewMatrix();

		projector = intrinsic.times(extrinsic);
	}
	private void 		updateProjector_V3() {
		Matrix33d translation2D = Matrix33d.identity();
		translation2D.m02 = cu();
		translation2D.m12 = cv();

		Matrix33d shear2D = Matrix33d.identity();
		shear2D.m01 = suv() / fy();
		
		Matrix33d scaling2D = Matrix33d.identity();
		scaling2D.m00 = fx();
		scaling2D.m11 = fy();

		Point3D  O = getFrame().getOrigin();
		Vector3D X = getFrame().getXAxis();
		Vector3D Y = getFrame().getYAxis();
		Vector3D Z = getFrame().getZAxis();

		Matrixmnd T = Matrixmnd.identity(3, 4);
		T.set(0, 3, -Vectors.dotProduct(X, Vectors.of(O)));
		T.set(1, 3, -Vectors.dotProduct(Y, Vectors.of(O)));
		T.set(2, 3, -Vectors.dotProduct(Z, Vectors.of(O)));

		Matrixmnd R = Matrixmnd.identity(4, 4);
		R.set(0, 0, X.getX()); R.set(0, 1, X.getY());  R.set(0, 2, X.getZ());
		R.set(1, 0, Y.getX()); R.set(1, 1, Y.getY());  R.set(1, 2, Y.getZ());
		R.set(2, 0, Z.getX()); R.set(2, 1, Z.getY());  R.set(2, 2, Z.getZ());

		NumberMatrix intrinsic = translation2D.times(scaling2D).times(shear2D); // || translation2D.times(shear2D).times(scaling2D);
		NumberMatrix extrinsic = T.times(R);

		projector = intrinsic.times(extrinsic);
	}
	private void 		updateProjector_V4() {
		Matrix33d intrinsic33 = Matrix33d.identity();

		intrinsic33.m01 = suv();
		intrinsic33.m00 = Fx();
		intrinsic33.m11 = Fy();
		intrinsic33.m02 = Cu();
		intrinsic33.m12 = Cv();

		Point3D  O = getFrame().getOrigin();
		Vector3D X = getFrame().getXAxis();
		Vector3D Y = getFrame().getYAxis();
		Vector3D Z = getFrame().getZAxis();

		Matrixmnd mv = new Matrixmnd(3, 4);
		mv.set(0, 0,  X.getX()); mv.set(0, 1,  X.getY());  mv.set(0, 2,  X.getZ()); mv.set(0, 3, -Vectors.dotProduct(X, Vectors.of(O)));
		mv.set(1, 0,  Y.getX()); mv.set(1, 1,  Y.getY());  mv.set(1, 2,  Y.getZ()); mv.set(1, 3, -Vectors.dotProduct(Y, Vectors.of(O)));
		mv.set(2, 0, -Z.getX()); mv.set(2, 1, -Z.getY());  mv.set(2, 2, -Z.getZ()); mv.set(2, 3, -Vectors.dotProduct(Z, Vectors.of(O)));

		NumberMatrix intrinsic = intrinsic33;
		NumberMatrix extrinsic = mv;

		projector = intrinsic.times(extrinsic);
	}

	public void 		update() {
		if(!isCalibrateInPixel())
			updateProjector_V3();
		else
			updateProjector_V4();
		
		if(w != null && W != null)
			updateRetroProjector();
	}
	
	// Screen Distance
	double screen_distance, screen_scale;

	public void 		setScreenProperty(double _distance_mm, double _cmos_ratio) {
		screen_distance = _distance_mm;
		screen_scale    = _cmos_ratio;
	}
	public double 		getScreenDistance() {
		return screen_distance;
	}
	public double 		getScreenScale() {
		return screen_scale;
	}
	
	// Camera Behavior
	Frame3D frame;

	public Frame3D		getFrame() {
		return frame;
	}
	public Point3D  	getOrigin() {
		return getFrame().getOrigin();
	}

	public void 		lookAt(Point3D _position, Point3D _target, Vector3D _up) {
		Vector3D back  = Vectors.of(_position.minus(_target)).normalized();				// Correct
		Vector3D right = Vectors.crossProduct(_up, back).normalized();						// Correct
		Vector3D up    = Vectors.crossProduct(back, right).normalized();					// Correct

		frame.setOrigin(_position);
		frame.setAxis(right, up, back);
	}
	public void 		lookAt(Point3D _target, Vector3D _up) {
		Vector3D back  = Vectors.of(frame.getOrigin().minus(_target)).normalized();
		Vector3D right = Vectors.crossProduct(_up, back).normalized();
		Vector3D up    = Vectors.crossProduct(back, right).normalized();

		frame.setAxis(right, up, back);
	}
	public void 		headUp(Vector3D _up) {
		Vector3D up    = _up.normalized();
		Vector3D back  = Vectors.crossProduct(frame.getXAxis(), frame.getYAxis()).normalized();
		Vector3D right = Vectors.crossProduct(frame.getYAxis(), frame.getZAxis()).normalized();
		frame.setAxis(right, up, back);
	}

	public void 		strafe(float _step) {
//		frame.getOrigin().plusEquals( frame.getXAxis().times(_step) );
		frame.setOrigin( frame.getOrigin().plus( frame.getXAxis().times(_step) ) );
	}
	public void 		raise(float _step) {
//		frame.getOrigin().plusEquals( frame.getYAxis().times(_step) );
		frame.setOrigin( frame.getOrigin().plus( frame.getYAxis().times(_step) ) );
	}
	public void 		move(float _step) {
//		frame.getOrigin().minusEquals( frame.getZAxis().times(_step) );
		frame.setOrigin( frame.getOrigin().minus( frame.getZAxis().times(_step) ) );
	}

	public void 		pitched(float _deg) {
		if(_deg != 0) {
			Vector3D   right = frame.getXAxis();
			Quaternion q     = new Quaternion(right, (float) (_deg * Math.PI / 180.0f));
			Vector3D   back  = q.transform(frame.getZAxis()).normalized();
			Vector3D   up    = q.transform(frame.getYAxis()).normalized();

			frame.setAxis(right, up, back);
		}
	}
	public void 		yawed(float _deg) {
		if(_deg != 0) {
			Vector3D   up    = frame.getYAxis();
			Quaternion q     = new Quaternion(up, (float) (_deg * Math.PI / 180.0f));
			Vector3D   right = q.transform(frame.getXAxis()).normalized();
			Vector3D   back  = q.transform(frame.getZAxis()).normalized();

			frame.setAxis(right, up, back);
		}
	}
	public void 		rolled(float _deg) {
		if(_deg != 0) {
			Vector3D   back  = frame.getZAxis();
			Quaternion q     = new Quaternion(back, (float) (_deg * Math.PI / 180.0f));
			Vector3D   right = q.transform(frame.getXAxis()).normalized();
			Vector3D   up    = q.transform(frame.getYAxis()).normalized();

			frame.setAxis(right, up, back);
		}
	}

	// RetroProjection in Space
	private Point3D   	cmosCenter,     imgCenter;
	private Point3D[] 	cmosBoundaries, imgBoundaries, farBoundaries;
	private Vector3D  	cmosU, cmosV,   imgU, imgV;

	private void 		updateRetroProjector() {
		double cmos_dist_factor = 1, cmos_dim_factor = 1;

		if(imgBoundaries == null)
			imgBoundaries = new Point3D[4];
		if(cmosBoundaries == null)
			cmosBoundaries = new Point3D[4];
		if(farBoundaries == null)
			farBoundaries = new Point3D[4];

		Point3D  O = getFrame().getOrigin();
		Vector3D I = getFrame().getXAxis();
		Vector3D J = getFrame().getYAxis();
		Vector3D K = getFrame().getZAxis();

		double X0, Y0;
		
		// CMOS Configuration
		double  d_cmos  = f   * cmos_dist_factor,
				hw_cmos = w/2 * cmos_dim_factor, 
				hh_cmos = h/2 * cmos_dim_factor;

		if(isCalibrateInPixel()) {
			X0 = (W/2 - Cu) * (hw_cmos/W);
			Y0 = (H/2 - Cv) * (hh_cmos/H);
		} else {
			X0 = cu;
			Y0 = cv;
		}

		double cmos_left  = hw_cmos - X0;
		double cmos_right = hw_cmos + X0;
		double cmos_top   = hh_cmos + Y0;
		double cmos_down  = hh_cmos - Y0;

		cmosCenter        = O.plus(K.times((double) d_cmos));
		cmosBoundaries[0] = cmosCenter . plus (I.times(cmos_left))  . minus (J.times(cmos_top));
		cmosBoundaries[1] = cmosCenter . minus(I.times(cmos_right)) . minus (J.times(cmos_top));
		cmosBoundaries[2] = cmosCenter . minus(I.times(cmos_right)) . plus  (J.times(cmos_down));
		cmosBoundaries[3] = cmosCenter . plus (I.times(cmos_left))  . plus  (J.times(cmos_down));
		cmosU             = Vectors.delta(cmosBoundaries[1], cmosBoundaries[0]).divides((double) W());
		cmosV             = Vectors.delta(cmosBoundaries[0], cmosBoundaries[3]).divides((double) H());


		// Image Configuration
		double  d_img  = screen_distance * 1e-3,
				hw_img = w/2 * screen_scale, 
				hh_img = h/2 * screen_scale;

		if(isCalibrateInPixel()) {
			X0 = (W/2 - Cu) * (hw_img/W);
			Y0 = (H/2 - Cv) * (hh_img/H);
		} else {
			X0 = cu;
			Y0 = cv;
		}

		double img_left  = hw_img - X0;
		double img_right = hw_img + X0;
		double img_top   = hh_img + Y0;
		double img_down  = hh_img - Y0;

		imgCenter         = O.plus(K.times(- d_img));
//		imgBoundaries[0]  = imgCenter . minus(I.times(img_left))  . plus(J.times(img_top));
//		imgBoundaries[1]  = imgCenter . plus (I.times(img_right)) . plus(J.times(img_top));
//		imgBoundaries[2]  = imgCenter . plus (I.times(img_right)) . minus (J.times(img_down));
//		imgBoundaries[3]  = imgCenter . minus(I.times(img_left))  . minus (J.times(img_down));
		imgBoundaries[0]  = imgCenter . minus(I.times(img_left))  . minus (J.times(img_down));
		imgBoundaries[1]  = imgCenter . plus (I.times(img_right)) . minus (J.times(img_down));
		imgBoundaries[2]  = imgCenter . plus (I.times(img_right)) . plus(J.times(img_top));
		imgBoundaries[3]  = imgCenter . minus(I.times(img_left))  . plus(J.times(img_top));
		imgU              = Vectors.delta(imgBoundaries[1], imgBoundaries[0]).divides((double) W());
		imgV              = Vectors.delta(imgBoundaries[3], imgBoundaries[0]).divides((double) H());

		
		
		double far         = 50;	// in m
		double thalesMin   = imgCenter.minus(O).norm();
		double thalesMax   = K.times(far).norm();
		double thalesRatio = thalesMax / thalesMin;
		farBoundaries[0]   = O . plus(Vectors.delta(imgBoundaries[0], O).times(thalesRatio));
		farBoundaries[1]   = O . plus(Vectors.delta(imgBoundaries[1], O).times(thalesRatio));
		farBoundaries[2]   = O . plus(Vectors.delta(imgBoundaries[2], O).times(thalesRatio));
		farBoundaries[3]   = O . plus(Vectors.delta(imgBoundaries[3], O).times(thalesRatio));

	}

	public Point3D  	getCmosOrigin() {
		return cmosCenter;
	}
	public Point3D[] 	getCmosBoundaries() {
		return cmosBoundaries;
	}
	public Vector3D 	getCmosU() {
		return cmosU;
	}
	public Vector3D 	getCmosV() {
		return cmosV;
	}

	public Point3D 		getImageOrigin() {
		return imgCenter;
	}
	public Point3D[] 	getImageBoundaries() {
		return imgBoundaries;
	}
	public Vector3D 	getImageU() {
		return imgU;
	}
	public Vector3D 	getImageV() {
		return imgV;
	}

	public Point3D[] 	getFarBoundaries() {
		return farBoundaries;
	}

	public String toString() {
		return super.toString() + "\n" + getFrame().toString() + "\n" + projector;
	}
	
}
