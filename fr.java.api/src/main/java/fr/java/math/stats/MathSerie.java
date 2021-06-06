package fr.java.math.stats;

public interface MathSerie {

	public interface OneDim extends MathSerie {
		public double[] getY();
		public double   getY(int _idx);
	}
	public interface TwoDims extends MathSerie {
		public double[][] getY();
		public double[]   getY(int _idx);
		public double     getY1(int _idx);
		public double     getY2(int _idx);
	}
	public interface ThreeDims extends MathSerie {
		public double[][] getY();
		public double[]   getY(int _idx);
		public double     getY1(int _idx);
		public double     getY2(int _idx);
		public double     getY3(int _idx);
	}
	public interface NDims extends MathSerie {
		public double[][] getY();
		public double[]   getY(int _idx);
		public double     getY(int _xIdx, int _yIdx);
	}

	public int 		size();
	public int 		getLength();

	public double[] getX();
	public double   getX(int _idx);

}
