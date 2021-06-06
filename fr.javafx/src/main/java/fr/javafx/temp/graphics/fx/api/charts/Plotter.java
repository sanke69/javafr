package fr.javafx.temp.graphics.fx.api.charts;

public interface Plotter {

	public static interface Quantitative<X extends Number, Y extends Number> {
		public X getX();
		public Y getY();
	}
	public static interface Qualitative<X, Y extends Number> {
		public X getX();
		public Y getY();
	}

	public static enum XAxisType { NUMERIC, CATEGORY; }
	
	
	public void setXAxis(XAxisType _type);
	
	
	
}
