package fr.java.raster;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.media.image.Image;
import fr.java.media.video.buffer.VideoFrame;
import fr.java.patterns.geometry.Boundable;

public interface XRaster extends Boundable.TwoDims {

	public static class Collection {

		public static final Map<Class<?>, Function<?, XRaster>> collection = new HashMap<Class<?>, Function<?, XRaster>>();

		public static boolean isSupplierExist(Class<?> _cls) {
			Function<?, XRaster> supplier = collection.get(_cls);
			if(supplier != null)
				return true;
			
			for(Class<?> cls : collection.keySet())
				if(cls.isAssignableFrom(_cls))
					return true;
			
			return false;
		}
	
		public static void    registerNewSupplier(Class<?> _cls, Function<?, XRaster> _supplier) {
			collection.put(_cls, _supplier);
		}
	
		public static XRaster newXRaster(Object _o) {
			
			if(_o == null)
				return null;

			if(_o instanceof XRaster)
				return (XRaster) _o;

			if(_o instanceof Image)
				return newXRaster(((Image<?>) _o).data());

			if(_o instanceof VideoFrame)
				return newXRaster(((VideoFrame<?>) _o).image());

			Function<Object, XRaster> s = (Function<Object, XRaster>) collection.get(_o.getClass());
			if(s != null)
				return s.apply(_o);

			for(Class<?> cls : collection.keySet())
				if(cls.isAssignableFrom(_o.getClass()))
					return ((Function<Object, XRaster>) collection.get(cls)).apply(_o);

			return null;
		}

	}

	public default void 	refresh(){};

	public Object 			getObject();

	public double  			getWidth();
	public double			getHeight();
	public int	 			getDepth();

	public default byte 	getValue(int _i, int _j, int _k) {
		switch(_k) {
		case 0  : return getRed(_i, _j);
		case 1  : return getGreen(_i, _j);
		case 2  : return getBlue(_i, _j);
		default : return getRed(_i, _j);
		}
	}

	public byte   			getLuminance(int _i, int _j);

	public byte   			getRed(int _i, int _j);
	public byte   			getGreen(int _i, int _j);
	public byte   			getBlue(int _i, int _j);

	@Override
	public default double 	getX() { return 0; }

	@Override
	public default double 	getY() { return 0; }

	@Override
	public default boolean 	isEmpty() {
		return getWidth() != 0 && getHeight() != 0 && getDepth() != 0;
	}

	@Override
	public default boolean 	contains(Coordinate _pt) { throw new NotYetImplementedException(); }
	@Override
	public default boolean 	contains(BoundingBox _bb) { throw new NotYetImplementedException(); }
	@Override
	public default boolean 	intersects(BoundingBox _bb) { throw new NotYetImplementedException(); }

	@Override
	public default String 	toString(NumberFormat _nf) { throw new NotYetImplementedException(); }

}
