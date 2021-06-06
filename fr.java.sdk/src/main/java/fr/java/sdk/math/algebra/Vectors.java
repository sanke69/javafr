package fr.java.sdk.math.algebra;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.geometry.space.Vector4D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.Angles;
import fr.java.sdk.math.algebra.vectors.DoubleVector2D;
import fr.java.sdk.math.algebra.vectors.DoubleVector3D;
import fr.java.sdk.math.algebra.vectors.DoubleVector4D;
import fr.java.sdk.math.algebra.vectors.DoubleArrayVector;

public class Vectors {

	public static Vector2D 	unit2() 												{ return new DoubleVector2D(1, 1); }
	public static Vector3D 	unit3() 												{ return new DoubleVector3D(1, 1, 1); }
	public static Vector4D 	unit4() 												{ return new DoubleVector4D(1, 1, 1, 1); }
	public static Vector2D.Editable zero2() 										{ return new DoubleVector2D(); }
	public static Vector3D.Editable zero3() 										{ return new DoubleVector3D(); }
	public static Vector4D.Editable zero4() 										{ return new DoubleVector4D(); }

	public static Vector2D.Editable of(double _x, double _y) 						{ return new DoubleVector2D(_x, _y); }
	public static Vector3D.Editable of(double _x, double _y, double _z) 			{ return new DoubleVector3D(_x, _y, _z); }
	public static Vector4D.Editable of(double _x, double _y, double _z, double _w) 	{ return new DoubleVector4D(_x, _y, _z, _w); }
	public static Vector2D.Editable of(Point2D _pt) 								{ return new DoubleVector2D(_pt.getX(), _pt.getY()); }
	public static Vector3D.Editable of(Point3D _pt) 								{ return new DoubleVector3D(_pt.getX(), _pt.getY(), _pt.getZ()); }
	public static Vector2D.Editable of(Point2D _a, Point2D _b) 						{ return new DoubleVector2D(_b.getX() - _a.getX(), _b.getY() - _a.getY()); }

	public static Vector2D.Editable delta(Coordinate.Cartesian2D _B, Coordinate.Cartesian2D _A) { return new DoubleVector2D(_B.getX() - _A.getX(), _B.getY() - _A.getY()); }
	public static Vector2D.Editable delta(Point2D _B, Point2D _A) 					{ return new DoubleVector2D(_B.getX() - _A.getX(), _B.getY() - _A.getY()); }
	public static Vector3D.Editable delta(Point3D _p1, Point3D _p0) 				{ return new DoubleVector3D(_p1.getX() - _p0.getX(), _p1.getY() - _p0.getY(), _p1.getZ() - _p0.getZ()); }

	public static double 			dotProduct(final Vector2D _a, final NumberVector _b) {
		assert(_b.size() == 2);
		return _a.getX() * _b.getNumber(0).doubleValue() + _a.getY() * _b.getNumber(1).doubleValue();
	}
	public static double 			dotProduct(final NumberVector _a, final Vector2D _b) {
		assert(_a.size() == 2);
		return _a.getNumber(0).doubleValue() * _b.getX() + _a.getNumber(1).doubleValue() * _b.getY();
	}
	public static double 			dotProduct(final Vector2D _a, final Vector2D _b) {
		return _a.getX() * _b.getX() + _a.getY() * _b.getY();
	}

	public static Vector2D 			crossProduct(final NumberVector _a, final NumberVector _b) {
		assert(_a.size() == _b.size() && _a.size() == 2);
		return new DoubleVector2D( _a.getNumber(1).doubleValue()*_b.getNumber(0).doubleValue() - _a.getNumber(0).doubleValue()*_b.getNumber(1).doubleValue(), _a.getNumber(0).doubleValue()*_b.getNumber(1).doubleValue() - _a.getNumber(1).doubleValue()*_b.getNumber(0).doubleValue()  );
	}
	public static Vector2D 			crossProduct(final Vector2D _a, final NumberVector _b) {
		assert(_b.size() == 2);
		return new DoubleVector2D( _a.getY()*_b.getNumber(0).doubleValue() - _a.getX()*_b.getNumber(1).doubleValue(), _a.getX()*_b.getNumber(1).doubleValue() - _a.getY()*_b.getNumber(0).doubleValue()  );
	}
	public static Vector2D 			crossProduct(final NumberVector _a, final Vector2D _b) {
		assert(_a.size() == 2);
		return new DoubleVector2D( _a.getNumber(1).doubleValue()*_b.getX() - _a.getNumber(0).doubleValue()*_b.getY(), _a.getNumber(0).doubleValue()*_b.getY() - _a.getNumber(1).doubleValue()*_b.getX()  );
	}
	public static Vector2D 			crossProduct(final Vector2D _a, final Vector2D _b) {
		return new DoubleVector2D( _a.getY()*_b.getX() - _a.getX()*_b.getY(), _a.getX()*_b.getY() - _a.getY()*_b.getX()  );
	}

    public static double 	dotProduct(final NumberVector _a, final NumberVector _b) {
    	assert(_a.size() == _b.size());
    	
    	if(_a.size() == 2)
    		return _a.getNumber(0).doubleValue() * _b.getNumber(0).doubleValue() + _a.getNumber(1).doubleValue() * _b.getNumber(1).doubleValue();
    	if(_a.size() == 3)
    		return _a.getNumber(0).doubleValue() * _b.getNumber(0).doubleValue() + _a.getNumber(1).doubleValue() * _b.getNumber(1).doubleValue() + _a.getNumber(2).doubleValue() * _b.getNumber(2).doubleValue();
    	
    	return -1;
    }
    public static double 	dotProduct(final Vector3D _a, final NumberVector _b) {
    	assert(_b.size() == 2);
        return _a.getX() * _b.getNumber(0).doubleValue() + _a.getY() * _b.getNumber(1).doubleValue() + _a.getZ() * _b.getNumber(2).doubleValue();
    }
    public static double 	dotProduct(final Vector3D _a, final Vector3D _b) {
        return _a.getX() * _b.getX() + _a.getY() * _b.getY() + _a.getZ() * _b.getZ();
    }

	public static Vector3D 	crossProduct(final Vector3D _a, final NumberVector _b) {
		assert(_b.size() == 2);
		return new DoubleVector3D( _a.getY() * _b.getNumber(2).doubleValue() - _a.getZ() * _b.getNumber(1).doubleValue(),
							 _a.getZ() * _b.getNumber(0).doubleValue() - _a.getX() * _b.getNumber(2).doubleValue(),
							 _a.getX() * _b.getNumber(1).doubleValue() - _a.getY() * _b.getNumber(0).doubleValue()  );
	}
	public static Vector3D 	crossProduct(final NumberVector _a, final Vector3D _b) {
		assert(_a.size() == 2);
		return new DoubleVector3D( _a.getNumber(1).doubleValue() * _b.getZ() - _a.getNumber(2).doubleValue() * _b.getY(),
							 _a.getNumber(2).doubleValue() * _b.getX() - _a.getNumber(0).doubleValue() * _b.getZ(),
							 _a.getNumber(0).doubleValue() * _b.getY() - _a.getNumber(1).doubleValue() * _b.getX()  );
	}
	public static Vector3D 	crossProduct(final Vector3D _a, final Vector3D _b) {
		return new DoubleVector3D( _a.getY() * _b.getZ() - _a.getZ() * _b.getY(),
							 _a.getZ() * _b.getX() - _a.getX() * _b.getZ(),
							 _a.getX() * _b.getY() - _a.getY() * _b.getX()  );
	}

	public enum LinearDirection {
		LEFT, RIGHT, CENTER, BOTH;
	}
	
	public enum PaddingMode {
			ZPD, // Zero-Padding
			PPD, // Periodized Extension
			SYM; // Symetric Extension
	}

	public enum SmoothingMethod {
		MovingAverageSymetric,
		MovingAverageForward,
		MovingAverageBackward,
		Gaussian, // nadaraya_watson
		LocalMaxima,
		LocalMinima;
	}

	// QUANTITIES
	public static double 		min(final NumberVector _v) {
		double min = _v.getNumber(0).doubleValue();

		for(int i = 1; i < _v.size(); ++i)
			if( min > _v.getNumber(i).doubleValue() )
				min = _v.getNumber(i).doubleValue();

		return min;
	}
	public static double 		min(final DoubleVector _v) {
		double min = _v.get(0);

		for(int i = 1; i < _v.size(); ++i)
			if( min > _v.get(i) )
				min = _v.get(i);

		return min;
	}
	public static double 		min(final DoubleArrayVector _v) {
		double min = _v.data[0];

		for(int i = 1; i < _v.data.length; ++i)
			if( min > _v.data[i] )
				min = _v.data[i];

		return min;
	}

	public static double 		max(final NumberVector _v) {
		double max = _v.getNumber(0).doubleValue();

		for(int i = 1; i < _v.size(); ++i)
			if( max < _v.getNumber(i).doubleValue() )
				max = _v.getNumber(i).doubleValue();

		return max;
	}
	public static double 		max(final DoubleVector _v) {
		double max = _v.get(0);

		for(int i = 1; i < _v.size(); ++i)
			if( max < _v.get(i) )
				max = _v.get(i);

		return max;
	}
	public static double 		max(final DoubleArrayVector _v) {
		double max = _v.data[0];

		for(int i = 1; i < _v.data.length; ++i)
			if( max < _v.data[i] )
				max = _v.data[i];

		return max;
	}

	public static double 		sum(final NumberVector _v) {
		double sum = 0;

		for(int i = 0; i < _v.size(); ++i)
			sum += _v.getNumber(i).doubleValue();

		return sum;
	}
	public static double 		sum(final DoubleVector _v) {
		double sum = 0;

		for(int i = 0; i < _v.size(); ++i)
			sum += _v.get(i);

		return sum;
	}
	public static double 		sum(final DoubleArrayVector _v) {
		double sum = 0;

		for(int i = 0; i < _v.data.length; ++i)
			sum += _v.data[i];

		return sum;
	}

	public static double 		mean(final NumberVector _v) {
		return sum(_v) / _v.size();
	}
	public static double 		mean(final DoubleVector _v) {
		return sum(_v) / _v.size();
	}
	public static double 		mean(final DoubleArrayVector _v) {
		return sum(_v) / _v.size();
	}

	public static double 		var(final NumberVector _v) {
        double mean = mean(_v);
		double var  = 0;

		for(int i = 0; i < _v.size(); ++i) {
			double d = _v.getNumber(i).doubleValue();
			var += (d-mean) * (d-mean);
		}

		return var / (_v.size() - 1);
	}
	public static double 		var(final DoubleVector _v) {
        double mean = mean(_v);
		double var  = 0;

		for(int i = 0; i < _v.size(); ++i) {
			double d = _v.get(i);
			var += (d-mean) * (d-mean);
		}

		return var / (_v.size() - 1);
	}
	public static double 		var(final DoubleArrayVector _v) {
        double mean = mean(_v);
		double var  = 0;

		for(int i = 0; i < _v.data.length; ++i) {
			double d = _v.data[i];
			var += (d-mean) * (d-mean);
		}

		return var / (_v.size() - 1);
	}

	public static double 		std(final NumberVector _v) {
		return Math.sqrt(var(_v));
	}
	public static double 		std(final DoubleVector _v) {
		return Math.sqrt(var(_v));
	}
	public static double 		std(final DoubleArrayVector _v) {
		return Math.sqrt(var(_v));
	}

	// TRANSFORMATION
	public static NumberVector 		sign(final NumberVector _v) {
		DoubleArrayVector s = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			s.data[i] = _v.getNumber(i).doubleValue() > 0 ? 1 : _v.getNumber(i).doubleValue() < 0 ? -1 : 0;

		return s;
	}
	public static DoubleVector 	sign(final DoubleVector _v) {
		DoubleArrayVector s = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			s.data[i] = _v.get(i) > 0 ? 1 : _v.get(i) < 0 ? -1 : 0;

		return s;
	}
	public static DoubleArrayVector 		sign(final DoubleArrayVector _v) {
		DoubleArrayVector s = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			s.data[i] = _v.data[i] > 0 ? 1 : _v.data[i] < 0 ? -1 : 0;

		return s;
	}

	public static NumberVector 		derive(final NumberVector _v) {
		return derive(_v, 1.0);
	}
	public static DoubleVector 	derive(final DoubleVector _v) {
		return derive(_v, 1.0);
	}
	public static DoubleArrayVector 		derive(final DoubleArrayVector _v) {
		return derive(_v, 1.0);
	}
	public static NumberVector 		derive(final NumberVector _v, final double _dt) {
		DoubleArrayVector d = new DoubleArrayVector( _v.size() - 1 );

		for(int i = 0; i < _v.size(); ++i)
			d.data[i] = (_v.getNumber(i + 1).doubleValue() - _v.getNumber(i).doubleValue()) / _dt;

		return d;
	}
	public static DoubleVector 	derive(final DoubleVector _v, final double _dt) {
		DoubleArrayVector d = new DoubleArrayVector( _v.size() - 1 );

		for(int i = 0; i < _v.size(); ++i)
			d.data[i] = (_v.get(i + 1) - _v.get(i)) / _dt;

		return d;
	}
	public static DoubleArrayVector 		derive(final DoubleArrayVector _v, final double _dt) {
		DoubleArrayVector d = new DoubleArrayVector( _v.data.length - 1 );

		for(int i = 0; i < _v.data.length; ++i)
			d.data[i] = (_v.data[i + 1] - _v.data[i]) / _dt;

		return d;
	}

	public static DoubleVector	cos(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.cos(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	cos(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.cos(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		cos(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.cos(_v.data[i]);

		return tmp;
	}

	public static DoubleVector 	sin(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.sin(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	sin(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.sin(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		sin(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.sin(_v.data[i]);

		return tmp;
	}

	public static DoubleVector 	tan(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.tan(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	tan(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.tan(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		tan(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.tan(_v.data[i]);

		return tmp;
	}

	public static DoubleVector	acos(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.acos(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	acos(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.acos(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		acos(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.acos(_v.data[i]);

		return tmp;
	}

	public static DoubleVector	asin(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.asin(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector	asin(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.asin(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		asin(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.asin(_v.data[i]);

		return tmp;
	}

	public static DoubleVector 	atan(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.atan(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	atan(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.atan(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		atan(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.atan(_v.data[i]);

		return tmp;
	}

	public static DoubleVector 	exp(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.exp(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	exp(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.exp(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		exp(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.exp(_v.data[i]);

		return tmp;
	}

	public static DoubleVector	log(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.log(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector	log(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.log(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		log(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.log(_v.data[i]);

		return tmp;
	}

	public static DoubleVector 	log10(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.log10(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	log10(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.log10(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		log10(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.log10(_v.data[i]);

		return tmp;
	}

	public static DoubleVector 	sqrt(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.sqrt(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	sqrt(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.sqrt(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		sqrt(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.sqrt(_v.data[i]);

		return tmp;
	}

	public static NumberVector		abs(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.abs(_v.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector	abs(final DoubleVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.abs(_v.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		abs(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.abs(_v.data[i]);

		return tmp;
	}

	public static NumberVector		arg(final NumberVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = -1; // TODO:: Math.arg(_v.data[i]);

		return tmp;
	}
	public static DoubleArrayVector 		arg(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = -1; // TODO:: Math.arg(_v.data[i]);

		return tmp;
	}

	public static DoubleVector	pow(final NumberVector _v, final double _pow) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.pow(_v.getNumber(i).doubleValue(), _pow);

		return tmp;
	}
	public static DoubleVector 	pow(final DoubleVector _v, final double _pow) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.pow(_v.get(i), _pow);

		return tmp;
	}
	public static DoubleArrayVector 		pow(final DoubleArrayVector _v, final double _pow) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.pow(_v.data[i], _pow);

		return tmp;
	}

	public static NumberVector 		pow(final NumberVector _v, final NumberVector _pow) {
		assert(_v.size() == _pow.size());
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.pow(_v.getNumber(i).doubleValue(), _pow.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	pow(final DoubleVector _v, final DoubleVector _pow) {
		assert(_v.size() == _pow.size());
		DoubleArrayVector tmp = new DoubleArrayVector( _v.size() );

		for(int i = 0; i < _v.size(); ++i)
			tmp.data[i] = Math.pow(_v.get(i), _pow.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		pow(final DoubleArrayVector _v, final DoubleArrayVector _pow) {
		assert(_v.data.length == _pow.data.length);
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.pow(_v.data[i], _pow.data[i]);

		return tmp;
	}

	public static NumberVector 		pow(final double _v, final NumberVector _pow) {
		DoubleArrayVector tmp = new DoubleArrayVector( _pow.size() );

		for(int i = 0; i < _pow.size(); ++i)
			tmp.data[i] = Math.pow(_v, _pow.getNumber(i).doubleValue());

		return tmp;
	}
	public static DoubleVector 	pow(final double _v, final DoubleVector _pow) {
		DoubleArrayVector tmp = new DoubleArrayVector( _pow.size() );

		for(int i = 0; i < _pow.size(); ++i)
			tmp.data[i] = Math.pow(_v, _pow.get(i));

		return tmp;
	}
	public static DoubleArrayVector 		pow(final double _v, final DoubleArrayVector _pow) {
		DoubleArrayVector tmp = new DoubleArrayVector( _pow.data.length );

		for(int i = 0; i < _pow.data.length; ++i)
			tmp.data[i] = Math.pow(_v, _pow.data[i]);

		return tmp;
	}

	// OPERATION
	public final void 			plus(final NumberVector.Editable _out, final NumberVector _v, final Number _d) {
		for(int i = _out.getCapacity(); i-- >= 0;)
			_out.setNumber(_v.getNumber(i).doubleValue() + _d.doubleValue(), i);
	}
	public final void 			plus(final NumberVector.Editable _out, final NumberVector _v, final NumberVector _w) {
		for(int i = _out.getCapacity(); i-- >= 0;)
			_out.setNumber(_v.getNumber(i).doubleValue() + _w.getNumber(i).doubleValue(), i);
	}

	public final void 			plus(final DoubleVector.Editable _out, final DoubleVector _v, final double _d) {
		for(int i = _out.getCapacity(); i-- >= 0;)
			_out.setValue(_v.getValue(i) + _d, i);
	}
	
	
	
/*
	public final Vectornd 		plus(final Number[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d[i].doubleValue();
		return new Vectornd(v.length, v);
	}
	public final Vectornd 		plus(final Vector _v) {
		assert (data.length == _v.dim());
		double[] v = new 	double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _v.getNumber(i).doubleValue();
		return new Vectornd(v.length, v);
	}

	public final Vectornd 		plus(final double _d) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d;
		return new Vectornd(v.length, v);
	}
	public final Vectornd 		plus(final double[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d[i];
		return new Vectornd(v.length, v);
	}
	public final Vectornd 		plus(final DoubleVector _v) {
		assert (data.length == _v.dim());
		double[] v = new 	double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _v.get(i);
		return new Vectornd(v.length, v);
	}
*/
	
	
	
	
	
	public final static DoubleArrayVector clamp(final DoubleArrayVector a, final DoubleArrayVector low, final DoubleArrayVector high) {
		assert(a.size() == low.size());
		assert(a.size() == high.size());
		
		final DoubleArrayVector clamped = new DoubleArrayVector(a.size());
		
		for(int i = 0; i < a.size(); ++i)
			clamped.set(a.get(i) > high.get(i) ? high.get(i) : a.get(i) < low.get(i) ? low.get(i) : a.get(i), i);

		return clamped;
	}

	public static NumberVector gauss(final double _u, final double _r, final int _n) {
		double two_r_r = - 2 *_r *_r,
			   sq_2piR = Math.sqrt(2*Angles.pi) * _r;
		DoubleArrayVector T    = new DoubleArrayVector( _n );
		DoubleArrayVector TmU  = T.minusEquals(_u),
				   tmp = TmU.timesEquals(TmU).dividesEquals(two_r_r),
				   exp = exp(tmp).dividesEquals(sq_2piR);

		return exp;
	}

	public static DoubleArrayVector linspace(double _a, double _b, int _size) {
		if( _size < 1 )
			return new DoubleArrayVector(0.0);
		else if( _size == 1 )
			return new DoubleArrayVector(1, _a);
		else {
			double dx = (_b-_a) / (_size-1);

			DoubleArrayVector tmp = new DoubleArrayVector(_size);
			for(int i=0; i<_size; ++i)
				tmp.data[i] = _a + i*dx;

			return tmp;
		}
	}

	public static DoubleArrayVector rand(int _size, Supplier<Double> _f) {
		if( _size < 1 )
			return new DoubleArrayVector(0.0);
		else if( _size == 1 )
			return new DoubleArrayVector(1, _f.get());
		else {
			DoubleArrayVector tmp = new DoubleArrayVector(_size);
			for(int i = 0; i < _size; ++i)
				tmp.data[i] = _f.get();
			return tmp;
		}
	}
	public static DoubleArrayVector randUniform(int _size, double _a, double _b) {
		return rand(_size, () -> _a + (_b - _a) * Math.random());
	}

	public static DoubleArrayVector reverse(final DoubleArrayVector _v) {
		DoubleArrayVector tmp = new DoubleArrayVector( _v.data.length );
		
		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = _v.data[_v.data.length - 1 - i];

		return tmp;
	}
	public static DoubleArrayVector flip(final DoubleArrayVector _v) {
		return reverse(_v);
	}

	public static DoubleArrayVector shift(final DoubleArrayVector _v, int _shiftSize) {
		DoubleArrayVector tmp = new DoubleArrayVector(_v.data.length);

		if( _shiftSize >= 0 ) {
			for(int i = 0; i < _v.data.length - _shiftSize; ++i)
				tmp.data[i] = _v.data[i + _shiftSize];
		} else {
			for(int i = 0; i < _v.data.length - _shiftSize; ++i)	// TODO::
				tmp.data[i] = _v.data[i + _shiftSize];
		}

		return tmp;
	}
	public static DoubleArrayVector circShift(final DoubleArrayVector _v, int _shiftSize) {
		DoubleArrayVector tmp = new DoubleArrayVector(_v.data.length);

		if( _shiftSize >= 0 ) {
			for(int i = 0; i < _v.data.length - _shiftSize; ++i)
				tmp.data[i] = _v.data[i + _shiftSize];
			for(int i = _v.data.length - _shiftSize; i < _v.data.length; ++i)
				tmp.data[i] = _v.data[i - _v.data.length - _shiftSize];
		} else {
			for(int i = 0; i < _v.data.length - _shiftSize; ++i)	// TODO::
				tmp.data[i] = _v.data[i + _shiftSize];
			for(int i = _v.data.length - _shiftSize; i < _v.data.length; ++i)
				tmp.data[i] = _v.data[i - _v.data.length - _shiftSize];
		}

		return tmp;
	}
	public static DoubleArrayVector fftShift(final DoubleArrayVector _v) {
		int shiftsize = _v.data.length - _v.data.length/2 - 1;
		return circShift( _v, shiftsize );
	}
	/*
	template <typename T>
	TVector<T> fftInterp( const TVector<T> &sn, int factor ) {
		int N = sn.size(),
			halfN = N/2,
			offset = (factor-1)*N;

		TVector< TComplex<T> > Sk = fft(sn);
		TVector< TComplex<T> > Xk(factor*N);

		for( int i=0; i<=halfN; ++i )
			Xk[i] = T(factor)*Sk[i];
		for( int i=halfN+1; i<N; ++i )
			Xk[offset+i] = T(factor)*Sk[i];

		return ifftc2r(Xk);
	}

	template <typename T>
	TVector<TComplex<T> > fftInterp( const TVector<TComplex<T> > &sn, int factor ) {
		int N = sn.size(),
			halfN = N/2,
			offset = (factor-1)*N;

		TVector< TComplex<T> > Sk = fft(sn);
		TVector< TComplex<T> > Xk(factor*N);

		for( int i=0; i<=halfN; ++i )
			Xk[i] = T(factor)*Sk[i];
		for( int i=halfN+1; i<N; ++i )
			Xk[offset+i] = T(factor)*Sk[i];

		return ifft(Xk);
	}
*/
	public static DoubleArrayVector dyadUp(final DoubleArrayVector _v, final int evenodd) {
		int    length = _v.data.length;
		DoubleArrayVector tmp;

		if(evenodd%2 == 0 ){
			tmp = new DoubleArrayVector(2*length+1);
			for(int i = 0; i < length; ++i) {
				tmp.data[2*i] = 0;
				tmp.data[2*i+1] = _v.data[i];
			}
			tmp.data[2*length] = 0;
		} else {
			tmp = new DoubleArrayVector(2*length-1);
			for(int i = 0; i < length-1; ++i) {
				tmp.data[2*i] = _v.data[i];
				tmp.data[2*i+1] = 0;
			}
			tmp.data[2*length-2] = _v.data[length-1];
		}

		return tmp;
	}

	public static DoubleArrayVector dyadDown(final DoubleArrayVector _v, final int _evenodd) {
		int length = _v.data.length;
		DoubleArrayVector tmp;

		if( _evenodd%2 == 0 ) {
			tmp = new DoubleArrayVector( (length+1)/2 );
			for(int i = 0; i < tmp.data.length; ++i)
				tmp.data[i] = _v.data[2*i];
		} else {
			tmp = new DoubleArrayVector( length/2 );
			for(int i = 0; i < tmp.data.length; ++i)
				tmp.data[i] = _v.data[2*i+1];
		}

		return tmp;
	}

	public static DoubleArrayVector wkeep(final DoubleArrayVector _v, final int length, final int first) {
		DoubleArrayVector tmp = new DoubleArrayVector(length);

		if( ( 0 < length ) && ( length <= _v.data.length-first ) )
		{
			for( int i=0; i<length; ++i )
				tmp.data[i] = _v.data[first+i];

			return tmp;
		}
		else
		{
			return tmp;
		}
	}

	public static DoubleArrayVector wkeep(final DoubleArrayVector _v, final int _length, final LinearDirection _direction) {
		int lv = _v.data.length;
		DoubleArrayVector tmp = new DoubleArrayVector(_length);

		if( ( 0 <= _length ) && ( _length <= lv ) ) {
			switch(_direction) {
			case RIGHT :	for(int i = 0; i < _length; ++i)
								tmp.data[i] = _v.data[lv - _length + i];
							break ;
			case LEFT :		for(int i = 0; i < _length; ++i)
								tmp.data[i] = _v.data[i];
							break ;
			case CENTER :
			default :
							int first = (lv - _length) / 2;
							for(int i = 0; i < _length; ++i)
								tmp.data[i] = _v.data[first + i];
							break ;
			};
			return tmp;
		} else {
			return tmp;
		}
	}

	public static DoubleArrayVector wextend(final DoubleArrayVector _v, final int _extLength, final LinearDirection _direction, final PaddingMode _mode) {
		if( _extLength >= 0 ) {
			DoubleArrayVector tmp = null;
			int lv = _v.data.length;

			switch(_direction) {
			case RIGHT :	tmp = new DoubleArrayVector( lv + _extLength );
							for(int i = 0; i < lv; ++i)
								tmp.data[i] = _v.data[i];

							switch(_mode) {
							case SYM :	for(int i = 0; i < _extLength; ++i)
											tmp.data[lv + i] = _v.data[lv - 1 - i];
										break ;
							case PPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[lv + i] = _v.data[i];
										break ;
							case ZPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[lv + i] = 0;
										break ;
							};
							break ;

			case LEFT :		tmp = new DoubleArrayVector( lv+_extLength );

							switch(_mode) {
							case SYM :	for(int i = 0; i < _extLength; ++i)
											tmp.data[i] = _v.data[_extLength - 1 - i];
										break ;
							case PPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[i] = _v.data[lv - _extLength + i];
										break ;
							case ZPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[i] = 0;
										break ;
							};
							
							for(int i = 0; i < lv; ++i)
								tmp.data[i + _extLength] = _v.data[i];
							break ;

			case BOTH :		tmp = new DoubleArrayVector( lv + 2 * _extLength );
							for(int i = 0; i < lv; ++i)
								tmp.data[i + _extLength] = _v.data[i];

							switch(_mode) {
							case SYM :	for(int i = 0; i < _extLength; ++i) {
											tmp.data[i] = _v.data[_extLength-1-i];
											tmp.data[lv + _extLength + i] = _v.data[lv-1-i];
										}
										break ;
							case PPD :	for(int i = 0; i < _extLength; ++i) {
											tmp.data[i] = _v.data[lv-_extLength+i];
											tmp.data[lv + _extLength + i] = _v.data[i];
										}
										break ;
							case ZPD :	for(int i = 0; i < _extLength; ++i) {
											tmp.data[i] = 0;
											tmp.data[lv + _extLength + i] = 0;
										}
										break ;
							};
							break ;
			default :		break;
			}
			return tmp;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static DoubleArrayVector smooth(final DoubleArrayVector _v, final SmoothingMethod _method, final int _degree) {
		DoubleArrayVector smoothed = new DoubleArrayVector( _v.data.length );

		// pour GAUSSIAN method
		double G = 0.0, sumG = 0.0;
		// pour LOCAL_EXTREMA method
		Set<Integer> POI = new HashSet<Integer>();
		int xp = 0, xn = 0;
		double yp, yn;

		switch(_method) {
			case MovingAverageSymetric	:	for(int i = 0; i < _degree; ++i)
												smoothed.data[i] = _v.data[i];
											for(int i = _degree; i < _v.data.length - _degree; ++i)
												for(int j = 0; j < _degree; ++j)
													smoothed.data[i] += ( _v.data[i-j] + _v.data[i+j] ) / (2 * _degree + 2);
											for(int i = _v.data.length - _degree; i < _v.data.length; ++i)
												smoothed.data[i] = _v.data[i];
											break;
			case MovingAverageForward	:	for(int i = 0; i < _v.data.length - _degree; ++i)
												for(int j = 0; j < _degree; ++j)
													smoothed.data[i] += _v.data[i+j] / (_degree + 1);
											for(int i = _v.data.length - _degree; i < _v.data.length; ++i)
												smoothed.data[i] = _v.data[i];
											break;
			case MovingAverageBackward	:	for(int i = 0; i < _degree; ++i)
												smoothed.data[i] = _v.data[i];
											for(int i = _degree; i < _v.data.length; ++i)
												for(int j = 0; j < _degree; ++j)
													smoothed.data[i] += _v.data[i-j] / (_degree + 1);
											break;
			case Gaussian				:	for(int i = 0; i < _degree; ++i)
												smoothed.data[i] = _v.data[i];
											for(int i = _degree; i < _v.data.length - _degree; ++i) {
												for(int j = 0; j < _degree; ++j) {
													G = Math.exp( - (double) j*j / (double) _degree );
													sumG += 2.0 * G;
													smoothed.data[i] += G * _v.data[i-j]
													            +  G * _v.data[i+j];
												}
												smoothed.data[i] /= sumG;
												sumG = 0.0;
											}
											for(int i = _v.data.length - _degree; i < _v.data.length; ++i)
												smoothed.data[i] = _v.data[i];
											break;
			case LocalMaxima			:	POI = listMaxima(_v);
											yp  = _v.data[0];
											for(Integer i : POI) {
												xn = i;
												yn = _v.data[i];

												for(int j = xp; j < xn; ++j) {
													smoothed.data[j] = (j - xp) * (yn - yp) / (xn - xp) + yp;;
												}

												xp = xn;
												yp = yn;
											}
											xn = _v.data.length;
											yn = _v.data[_v.data.length - 1];
											for(int j = xp; j < _v.data.length; ++j) {
												smoothed.data[j] = (j - xp) * (yn - yp) / (xn - xp) + yp;;
											}
											break;
			case LocalMinima			:	break;
		};

		return smoothed;
	}
/*
	public static Vector phase(final Vector _in0, final Vector _in1) {
		Vector out = new Vector( _in0.data.length );
		t_complex  c0, c1;

		for(t_uint i = 0; i < _in0.dim(); ++i) {
			c0 = t_complex(_in0[i], 0);
			c1 = t_complex(_in1[i], 0);

			out[i] = arg( c1 / c0 );
			if(out[i] < 0.0f)
				out[i] += 2.0f * constant::pi;
		}

		return out;
	}
*
	Vector phase(const TVector<TComplex<T> >& _in0, const TVector<TComplex<T> >& _in1) {
		CASSERT(_in0.dim() == _in1.dim())

		TVector<T> out( _in0.dim() );

		for(t_uint i = 0; i < _in0.dim(); ++i) {
			out[i] = arg( _in1[i] / _in0[i] );
			if(out[i] < 0.0f)
				out[i] += constant::twopi;
			else if(out[i] > constant::twopi)
				out[i] -= constant::twopi;
		}

		return out;
	}
*/
	
	public static Set<Integer>	listExtrema(final DoubleArrayVector _v) {
		Set<Integer> extrema = new HashSet<Integer>();

		DoubleArrayVector s = sign(derive(_v, 1.0));

		for(int i = 1; i < s.data.length; ++i)
			if( (s.data[i] - s.data[i - 1] == -2) /* ruptures franches */ ||  ( s.data[i - 1] > 0 && s.data[i] == 0 && _v.data[i + 1] <= _v.data[i] ) /* "plateau" */ ) {
				extrema.add(i);
			} else if( (s.data[i] - s.data[i - 1] == 2) /* ruptures franches */ ||  ( s.data[i - 1] < 0 && s.data[i] == 0 && _v.data[i + 1] >= _v.data[i] ) /* "plateau" */ ) {
				extrema.add(i);
			}

		return extrema;
	}
	public static Set<Integer>	listMaxima(final DoubleArrayVector _v) {
		Set<Integer> maxima = new HashSet<Integer>();

		DoubleArrayVector s = sign(derive(_v, 1.0));

		for(int i = 1; i < s.data.length - 1; ++i)
			if( (s.data[i] - s.data[i - 1] == -2) /* ruptures franches */ ||  ( s.data[i - 1] > 0 && s.data[i] == 0 && _v.data[i + 1] <= _v.data[i] ) /* "plateau" */ )
				maxima.add(i);

		return maxima;
	}
	public static Set<Integer>	listMinima(final DoubleArrayVector _v) {
		Set<Integer> minima = new HashSet<Integer>();

		DoubleArrayVector s = sign(derive(_v, 1.0));

		for(int i = 1; i < s.data.length - 1; ++i)
			if( (s.data[i] - s.data[i - 1] == 2) /* ruptures franches */ ||  ( s.data[i - 1] < 0 && s.data[i] == 0 && _v.data[i + 1] >= _v.data[i] ) /* "plateau" */ )
				minima.add(i);

		return minima;
	}

	public static int 			firstAbove(final DoubleArrayVector _v, final double _threshold, final int _offset) {
		for(int i = _offset; i < _v.data.length - 1; ++i)
			if(_v.data[i] >= _threshold) return i;
		return -1;
	}
	public static int 			lastAbove(final DoubleArrayVector _v, final double _threshold, final int _offset) {
		int offset = (_offset == -1) ? _v.data.length - 1 : _offset;

		for(int i = offset; i > 0; --i)
			if(_v.data[i] >= _threshold)
				return i;
		return -1;
	}
	public static Set<Integer>	listMaximaAbove(final DoubleArrayVector _v, final double _threshold, final int _jump) {
		Set<Integer> above = new HashSet<Integer>();

		DoubleArrayVector s = sign(derive(_v, 1.0));

		for(int i = 1; i < s.data.length - 1; ++i)
			if( ( ( s.data[i] - s.data[i - 1] == -2 ) /* ruptures franches */ ||  ( s.data[i - 1] > 0 && s.data[i] == 0 && _v.data[i + 1] <= _v.data[i] ) /* "plateau" */ )
					&& _v.data[i] >= _threshold) {
				above.add(i);
				i += _jump;
			}

		return above;
	}

	public static int 			firstBelow(final DoubleArrayVector _v, final double _threshold, final int _offset) {
		for(int i = _offset; i < _v.data.length - 1; ++i)
			if(_v.data[i] <= _threshold) return i;
		return -1;
	}
	public static int 			lastBelow(final DoubleArrayVector _v, final double _threshold, final int _offset) {
		int offset = (_offset == -1) ? _v.data.length - 1 : _offset;

		for(int i = offset; i > 0; --i)
			if(_v.data[i] <= _threshold) return i;
		return -1;
	}
	public static Set<Integer>	listMinimaBelow(final DoubleArrayVector _v, final double _threshold, final int _jump) {
		Set<Integer> below = new HashSet<Integer>();

		DoubleArrayVector s = sign(derive(_v, 1.0));

		for(int i = 1; i < s.data.length - 1; ++i)
			if( (s.data[i] - s.data[i - 1] == 2) /* ruptures franches */ ||  ( s.data[i - 1] < 0 && s.data[i] == 0 && _v.data[i + 1] >= _v.data[i] ) /* "plateau" */
					&& _v.data[i] <= _threshold) {
				below.add(i);
				i += _jump;
			}

		return below;
	}

	public final static DoubleVector4D min(final DoubleVector4D _a, final DoubleVector4D _b) {
		return new DoubleVector4D(_a.getX() < _b.getX() ? _a.getX() : _b.getX(), _a.getY() < _b.getY() ? _a.getY() : _b.getY(), _a.getZ() < _b.getZ() ? _a.getZ() : _b.getZ(), _a.getW() < _b.getW() ? _a.getW() : _b.getW());
	}
	public final static DoubleVector4D max(final DoubleVector4D _a, final DoubleVector4D _b) {
		return new DoubleVector4D(_a.getX() > _b.getX() ? _a.getX() : _b.getX(), _a.getY() > _b.getY() ? _a.getY() : _b.getY(), _a.getZ() > _b.getZ() ? _a.getZ() : _b.getZ(), _a.getW() > _b.getW() ? _a.getW() : _b.getW());
	}

	public static Vector3D min(Vector3D _a, Vector3D _b) {
		return new DoubleVector3D(_a.getX() < _b.getX() ? _a.getX() : _b.getX(), _a.getY() < _b.getY() ? _a.getY() : _b.getY(), _a.getZ() < _b.getZ() ? _a.getZ() : _b.getZ());
	}
	public static Vector3D max(Vector3D _a, Vector3D _b) {
		return new DoubleVector3D(_a.getX() > _b.getX() ? _a.getX() : _b.getX(), _a.getY() > _b.getY() ? _a.getY() : _b.getY(), _a.getZ() > _b.getZ() ? _a.getZ() : _b.getZ());
	}

}
