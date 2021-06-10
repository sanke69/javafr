package fr.java.rasters.tensors;

import java.text.NumberFormat;
import java.util.function.Function;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.raster.XRaster;

public class XRasterTensor implements XRaster {
	private static final long serialVersionUID = 1L;

	static {
		XRaster.Collection.registerNewSupplier(NumberTensor.class, (Function<NumberTensor, XRaster>) XRasterTensor::new);
	}

	public static class SubCoordinate {
		int[] coords;
		
		public SubCoordinate(int _size) {
			super();
			coords = new int[_size];
			for(int i = 0; i < coords.length; ++i)
				coords[i] = 0;
		}
		
		public void setSubSlice(int... _subSlice) {
			for(int i = 0; i < _subSlice.length; ++i)
				coords[i] = _subSlice[i];
		}
		public void setSubSlice(Integer... _subSlice) {
			for(int i = 0; i < _subSlice.length; ++i)
				coords[i] = _subSlice[i];
		}

		public void setCoords(int _i, int _j) {
			if(coords.length >= 2) {
				coords[coords.length - 2] = _j;
				coords[coords.length - 1] = _i;
			}
		}
		public void setCoords(int _i, int _j, int _k) {
			if(coords.length >= 3) {
				coords[coords.length - 3] = _k;
				coords[coords.length - 2] = _j;
				coords[coords.length - 1] = _i;
			}
		}

		public int getActiveChannel() {
			if(coords.length <= 2)
				return 0;
			return coords[coords.length - 3];
		}
		
		public int[] getCoords() {
			return coords;
		}

	}

	private NumberTensor  tensor;

	private boolean 	  monoChannel;
	private SubCoordinate active;

	private int    		  width, height, depth;

	public XRasterTensor(NumberTensor _tensor) {
		tensor = _tensor;
		active = new SubCoordinate(tensor.getSliceDepth());

		width  = _tensor.getSliceDimension(0, true);
		height = _tensor.getSliceDimension(1, true);
		depth  = _tensor.getSliceDepth() < 2 ? 1 : tensor.getSliceDimension(2, true);
	}
	public XRasterTensor(NumberTensor _tensor, boolean _monoChannel) {
		tensor = _tensor;
		active = new SubCoordinate(tensor.getSliceDepth());

		width  = _tensor.getSliceDimension(0, true);
		height = _tensor.getSliceDimension(1, true);
		depth  = _tensor.getSliceDepth() < 2 ? 1 : tensor.getSliceDimension(2, true);
	}

	@Override
	public final NumberTensor 	getObject() {
		return tensor;
	}

	@Override
	public final double		getWidth() {
		return width;
	}
	@Override
	public final double		getHeight() {
		return height;
	}
	@Override
	public final int		getDepth() {
		return monoChannel ? 1 : depth;
	}

	@Override
	public final byte 		getValue(int _i, int _j, int _k) {
		return tensor.getNumber(_k, _j, _i).byteValue();
	}
	
	@Override
	public final byte 		getLuminance(int _i, int _j) {
		return tensor.getNumber(0, _j, _i).byteValue();
	}
	@Override
	public final byte 		getRed(int _i, int _j) {
		return tensor.getNumber(0, _j, _i).byteValue();
	}
	@Override
	public final byte 		getGreen(int _i, int _j) {
		return tensor.getNumber(1, _j, _i).byteValue();
	}
	@Override
	public final byte 		getBlue(int _i, int _j) {
		return tensor.getNumber(2, _j, _i).byteValue();
	}

	public final boolean 	isMonoChannel() {
		return monoChannel;
	}
	public final Integer 	getActiveChannel() {
		return active.getActiveChannel();
	}

	public final void 		setMonoChannel(boolean _true) {
		monoChannel = _true;
	}
	public final void 		setActiveSubSlice(Integer... subSlice) {
		active.setSubSlice(subSlice);
	}

	@Override
	public double 			getX() {
		return 0;
	}
	@Override
	public double 			getY() {
		return 0;
	}

	@Override
	public boolean 			isEmpty() {
		return getWidth() != 0 && getHeight() != 0;
	}

	@Override
	public boolean 			contains(Coordinate _pt) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 			contains(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			intersects(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}
	@Override
	public String 			toString(NumberFormat _nf) {
		throw new NotYetImplementedException();
	}

}
