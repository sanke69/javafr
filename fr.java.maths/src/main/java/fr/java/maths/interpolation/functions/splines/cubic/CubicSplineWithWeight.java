package fr.java.maths.interpolation.functions.splines.cubic;

import java.util.Arrays;

import fr.java.math.interpolation.FunctionInterpolator;
import fr.java.maths.polynoms.RealPolynomial;

public class CubicSplineWithWeight implements FunctionInterpolator.OneVar {
	public double 			x[];
	public double 			y[];
	public double 			w[];

	private double 			rho = 0.5;
	private Polynomial[] 	splineVector;

	public CubicSplineWithWeight() {
		super();
	}
	public CubicSplineWithWeight(double _rho) {
		super();
		rho = _rho;
	}
	public CubicSplineWithWeight(double[] _x, double[] _y) {
		super();
		init(_x, _y);
	}
	public CubicSplineWithWeight(double[] _x, double[] _y, double _rho) {
		super();
		init(_x, _y, _rho);
	}
	public CubicSplineWithWeight(double[] _x, double[] _y, double[] _w) {
		super();
		init(_x, _y, _w);
	}
	public CubicSplineWithWeight(double[] _x, double[] _y, double[] _w, double _rho) {
		super();
		init(_x, _y, _w, _rho);
	}

	public void 						init(double _x[], double _y[]) {
		init(_x, _y, rho);
	}
	public void 						init(double _x[], double _y[], double _rho) {
		init(_x, _y, null, _rho);
	}
	public void 						init(double _x[], double _y[], double _w[]) {
		init(_x, _y, _w, rho);
	}
	public void 						init(double _x[], double _y[], double _w[], double _rho) {
		if (_x.length <= 2)
			throw new IllegalArgumentException("x.length <= 2");
		if (_x.length != _y.length)
			throw new IllegalArgumentException("x.length != y.length");
		if (_w != null && _x.length != _w.length)
			throw new IllegalArgumentException("x.length != w.length");
		if (rho < 0.0D || rho > 1.0D)
			throw new IllegalArgumentException("rho not in ]0, 1]");
		splineVector = new Polynomial[_x.length + 1];
		if (_w == null) {
			_w = new double[_x.length];
			Arrays.fill(_w, 1);
		}

		rho = _rho;
		x   = _x.clone();
		y   = _y.clone();
		w   = _w.clone();

		double sigma[] = new double[_w.length];
		for(int i = 0; i < _w.length; i++)
			sigma[i] = (w[i] <= 0.0D) ? 1E+100D : 1.0D / Math.sqrt(w[i]);

		double qh[] = new double[_x.length];
		double qr[] = new double[_x.length];
		double qu[] = new double[_x.length];
		double qv[] = new double[_x.length];
		double qw[] = new double[_x.length];
		double qq[] = new double[_x.length + 1];

		double mu;
		if (rho <= 0.0D)
			mu = 1E+100D;
		else
			mu = (2D * (1.0D - rho)) / (3D * rho);

		int n = _x.length - 1;

		qh[0] = x[1] - x[0];
		qr[0] = 3D / qh[0];
		for(int i = 1; i < n; i++) {
			qh[i] = x[i + 1] - x[i];
			qr[i] = 3D / qh[i];
			qq[i] = (3D * (y[i + 1] - y[i])) / qh[i] - (3D * (y[i] - y[i - 1])) / qh[i - 1];
		}

		for(int i = 1; i < n; i++) {
			qu[i] = qr[i - 1] * qr[i - 1] * sigma[i - 1] + (qr[i - 1] + qr[i]) * (qr[i - 1] + qr[i]) * sigma[i] + qr[i] * qr[i] * sigma[i + 1];
			qu[i] = mu * qu[i] + 2D * (x[i + 1] - x[i - 1]);
			qv[i] = -(qr[i - 1] + qr[i]) * qr[i] * sigma[i] - qr[i] * (qr[i] + qr[i + 1]) * sigma[i + 1];
			qv[i] = mu * qv[i] + qh[i];
			qw[i] = mu * qr[i] * qr[i + 1] * sigma[i + 1];
		}

		compileQuincunx(qu, qv, qw, qq);

		double 
		params[]  = new double[4];
		params[0] = y[0] - mu * qr[0] * qq[1] * sigma[0];
		params[1] = y[1] - mu * ((-qr[0] - qr[1]) * qq[1] + qr[1] * qq[2]) * sigma[1];
		params[1] = (params[1] - params[0]) / qh[0] - (qq[1] * qh[0]) / 3D;
		splineVector[0] = new Polynomial(params);

		params[0] = y[0] - mu * qr[0] * qq[1] * sigma[0];
		params[1] = y[1] - mu * ((-qr[0] - qr[1]) * qq[1] + qr[1] * qq[2]) * sigma[1];
		params[1] = (params[1] - params[0]) / qh[0] - (qq[1] * qh[0]) / 3D;
		params[2] = 0.0D;
		params[3] = qq[1] / (3D * qh[0]);
		splineVector[1] = new Polynomial(params);

		for(int j = 1; j < n; j++) {
			params[0] = qr[j - 1] * qq[j - 1] + (-qr[j - 1] - qr[j]) * qq[j] + qr[j] * qq[j + 1];
			params[0] = y[j] - mu * params[0] * sigma[j];
			params[1] = (qq[j] + qq[j - 1]) * qh[j - 1] + splineVector[j].getCoeff(1);
			params[2] = qq[j];
			params[3] = (qq[j + 1] - qq[j]) / (3D * qh[j]);
			splineVector[j + 1] = new Polynomial(params);
		}

		params[0] = splineVector[n].evaluate(x[x.length - 1] - x[x.length - 2]);
		params[1] = splineVector[n].derivative(x[x.length - 1] - x[x.length - 2]);
		params[2] = splineVector[n].getCoeff(2); // 0.0D;
		params[3] = 0;// splineVector[j].getCoefficient(3);//0.0D;

		splineVector[n + 1] = new Polynomial(params);
	}

	public double 						evaluate(double _x) {
		int i = Utils.findIntervalAlt(_x, x);
		return splineVector[i].evaluate(i == 0 ? _x - x[0] : _x - x[i - 1]);
	}

	public double[] 					tangente(double _x) {
		int i = Utils.findIntervalAlt(_x, x);
		return splineVector[i].tangente(i == 0 ? _x - x[0] : _x - x[i - 1]);
	}

	@Override
	public boolean 						equals(Object obj) {
		if(!(obj instanceof CubicSplineWithWeight))
			return false;
		CubicSplineWithWeight s = (CubicSplineWithWeight) obj;
		return Arrays.equals(s.x, x) && Arrays.equals(s.y, y) && Arrays.equals(s.w, w) && rho == rho;
	}

	public CubicSplineWithWeight 		clone() {
		CubicSplineWithWeight wcs = new CubicSplineWithWeight();

		wcs.rho = rho;

		if(x != null) {
			wcs.x = x.clone();
			wcs.y = y.clone();
			wcs.w = w.clone();
		}

		if(splineVector != null) {
			wcs.splineVector = new Polynomial[splineVector.length];

			for (int i = 0; i < splineVector.length; i++)
				wcs.splineVector[i] = splineVector[i].clone();
		}

		return wcs;
	}

	// 
	public void 						xTranslate(double _delta) {
		for(Polynomial polynomial : splineVector)
			polynomial.getCoeffs()[0] += _delta;
	}
	public void 						yTranslate(double _delta) {
		for(int i = 0; i < x.length; i++)
			x[i] -= _delta;
	}

	private static void 				compileQuincunx(double _u[], double _v[], double _w[], double _q[]) {
		_u[0] = 0.0D;
		_v[1] = _v[1] / _u[1];
		_w[1] = _w[1] / _u[1];
		for (int j = 2; j < _u.length - 1; j++) {
			_u[j] = _u[j] - _u[j - 2] * _w[j - 2] * _w[j - 2] - _u[j - 1] * _v[j - 1] * _v[j - 1];
			_v[j] = (_v[j] - _u[j - 1] * _v[j - 1] * _w[j - 1]) / _u[j];
			_w[j] = _w[j] / _u[j];
		}

		_q[1] = _q[1] - _v[0] * _q[0];
		for (int j = 2; j < _u.length - 1; j++)
			_q[j] = _q[j] - _v[j - 1] * _q[j - 1] - _w[j - 2] * _q[j - 2];
		for (int j = 1; j < _u.length - 1; j++)
			_q[j] = _q[j] / _u[j];
		_q[_u.length - 1] = 0.0D;
		for (int j = _u.length - 3; j > 0; j--)
			_q[j] = _q[j] - _v[j] * _q[j + 1] - _w[j] * _q[j + 2];
	}

	public static CubicSplineWithWeight getParallelFunction(CubicSplineWithWeight _wcs, double _distance) {
		double[] xs = new double[_wcs.x.length];
		double[] ys = new double[_wcs.y.length];

		for(int i = 0; i < _wcs.x.length; i++) {
			double angle = Math.atan( _wcs.tangente(_wcs.x[i])[0] );

			xs[i] = _wcs.x[i] - Math.sin(angle) * _distance;
			ys[i] = _wcs.evaluate(_wcs.x[i]) + Math.cos(angle) * _distance;
		}

		CubicSplineWithWeight 
		scsd = new CubicSplineWithWeight();
		scsd . init(xs, ys, _wcs.w.clone());

		return scsd;
	}

}

class Polynomial extends RealPolynomial {
	private static final long serialVersionUID = 1L;

	public Polynomial(double _coeffs[]) {
		super(_coeffs);
	}

	public double[] 	tangente(double x) {
		int l = getCoeffs().length;
		double der = getCoeff(l - 1) * (l - 1);
		for (int i = l - 2; i > 0; i--)
			der = getCoeff(i) * i + x * der;
		return new double[] { der, -der * x + evaluate(x) };
	}

	public double 		derivative(double x) {
//		return evaluate(x, 1);

		double res = getCoeffDerivative(getCoeffs().length - 1, 1);
		for(int i = getCoeffs().length - 2; i >= 1; i--)
			res = getCoeffDerivative(i, 1) + x * res;
		return res;
	}

	@Override
	public Polynomial 	clone() {
		return new Polynomial(getCoeffs());
	}

}
