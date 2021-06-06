package fr.java.math.stats;

public interface DataSerie {

//	public double[] getRefs();
//	public double   getRef(int _idx);

	public int 		valueCount();
	
	public double[] getValues();
	public double   getValue(int _idx);

}
