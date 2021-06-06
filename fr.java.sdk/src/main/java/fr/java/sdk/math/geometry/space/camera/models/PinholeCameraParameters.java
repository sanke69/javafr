package fr.java.sdk.math.geometry.space.camera.models;

import fr.java.math.geometry.plane.Dimension2D;

// Modèle Sténopé Complet
// cf. https://docs.opencv.org/3.0-beta/modules/calib3d/doc/camera_calibration_and_3d_reconstruction.html
// cf. https://fr.wikipedia.org/wiki/Calibration_de_cam%C3%A9ra
// cf. http://ksimek.github.io/2013/08/13/intrinsic/
public class PinholeCameraParameters {
	Double 	f, cu, cv;			// in mm
	Double 	ku, kv, suv;		// -
	Double	w, h;				// in mm
	Double 	Fx, Fy, Cu, Cv;		// in pixels
	Integer W, H;				// in pixels
	boolean calibratedInPixel;

	public PinholeCameraParameters() {
		super();

		f  = ku = kv = cu = cv = suv = null;
		Fx = Fy = Cu = Cv = null;
		w  = h  = null;
		W  = H  = null;
	}

	public boolean isCalibrateInPixel() {
		return calibratedInPixel;
	}
	
	public void 	setCalibrationInMilli(double _f, double _ku, double _kv, double _cu, double _cv, double _suv) {
		f   = _f;
		ku  = _ku;
		kv  = _kv;
		cu  = _cu;
		cv  = _cv;
		suv = _suv;

		calibratedInPixel = false;
	}
	public void 	setCalibrationInPixel(double _Fx, double _Fy, double _Cu, double _Cv, double _Suv) {
		Fx  = _Fx;
		Fy  = _Fy;
		Cu  = _Cu;
		Cv  = _Cv;
		suv = _Suv;

		calibratedInPixel = true;
	}

	public Double 	f() {
		return f;
	}

	public Double 	ku() {
		return ku;
	}
	public Double 	kv() {
		return kv;
	}

	public Double 	fx() {
		return !calibratedInPixel ? f * ku : Fx * W / w;
	}
	public Double 	fy() {
		return !calibratedInPixel ? f * kv : Fy * H / h;
	}

	public Double 	cu() {
		return !calibratedInPixel ? cu : Cu * W / w;
	}
	public Double 	cv() {
		return !calibratedInPixel ? cv : Cv * H / h;
	}

	public Double 	suv() {
		return suv;
	}

	public Double 	w() {
		return w;
	}
	public Double 	h() {
		return h;
	}

	public Integer 	W() {
		return W;
	}
	public Integer 	H() {
		return H;
	}

	public Double 	pixelWidth() {
		return w / W;
	}
	public Double 	pixelHeight() {
		return h / H;
	}

	public Double 	F()  { return f * w / W; }
	public Double 	Fx() { return calibratedInPixel ? Fx : f * ku * w / W; }
	public Double 	Fy() { return calibratedInPixel ? Fy : f * kv * h / H; }

	public Double 	Cu() { return calibratedInPixel ? Cu : cu * w / W; }
	public Double 	Cv() { return calibratedInPixel ? Cv : cv * h / H; }

	public void 	setFocalLength(double _f) {
		f = _f;
	}
	public void 	setFocalParamU(double _fx) {
		Fx = _fx;
	}
	public void 	setFocalParamV(double _fy) {
		Fy = _fy;
	}

	public void 	setScaleFactor(double _ku, double _kv) {
		ku = _ku;
		kv = _kv;
	}
	public void 	setScaleFactorU(double _ku) {
		ku = _ku;
	}
	public void 	setScaleFactorV(double _kv) {
		kv = _kv;
	}

	public void 	setOpticalCenter(double _x0, double _y0) {
		cu = _x0;
		cv = _y0;
	}
	public void 	setOpticalCenterX(double _cu) {
		if(calibratedInPixel)
			Cu = _cu;
		else
			cu = _cu;
	}
	public void 	setOpticalCenterY(double _cv) {
		if(calibratedInPixel)
			Cv = _cv;
		else
			cv = _cv;
	}

	public void 	setNonOrthogonalityFactor(double _suv) {
		suv = _suv;
	}
	public void 	setAxisScrew(double _suv) {
		suv = _suv;
	}

	public void 	setCmosDimension(double _w_mm, double _h_mm) {
		w = _w_mm;
		h = _h_mm;
	}
	public void 	setCmosDimension(Dimension2D _cmos) {
		w = _cmos.getWidth();
		h = _cmos.getHeight();
	}
	public void 	setCmosWidth(double _w_mm) {
		w = _w_mm;
	}
	public void 	setCmosHeight(double _h_mm) {
		h = _h_mm;
	}

	public void 	setImageDimension(int _width, int _height) {
		W = _width;
		H = _height;
	}
	public void 	setImageDimension(Dimension2D _image) {
		W = (int) _image.getWidth();
		H = (int) _image.getHeight();
	}
	public void 	setImageWidth(int _width) {
		W = _width;
	}
	public void 	setImageHeight(int _height) {
		H = _height;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(isCalibrateInPixel()) {
			sb.append("PxlCalib. : ");
			sb.append("Fx= " + Fx + ", ");
			sb.append("Fy= " + Fy + ", ");
			sb.append("Cu= " + Cu + ", ");
			sb.append("Cv= " + Cv + ", ");
			sb.append("suv= " + suv + "\n");
		} else {
			sb.append("DftCalib. : ");
			sb.append("f= " + f + ", ");
			sb.append("ku= " + ku + ", ");
			sb.append("kv= " + kv + ", ");
			sb.append("cu= " + cu + ", ");
			sb.append("cv= " + cv + ", ");
			sb.append("suv= " + suv + "\n");
		}
		sb.append("w= " + w + ", h= " + h + ", W= " + W + ", H= " + H + "\n");

		return sb.toString();
	}
	
}
