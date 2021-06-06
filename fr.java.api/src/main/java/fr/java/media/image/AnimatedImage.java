package fr.java.media.image;

public interface AnimatedImage<T> extends Image<T> {

	public int			count();
	public T			data(int _n);
	public int			duration(int _n);

}
