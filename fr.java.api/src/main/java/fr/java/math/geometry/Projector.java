package fr.java.math.geometry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.math.topology.Coordinate;

public interface Projector< K_Point extends Coordinate, I_Point extends Coordinate> {

	public interface OneDim<K_Point extends Coordinate.OneDim, 
							I_Point extends Coordinate.OneDim> 
						extends Projector<K_Point, I_Point> {

		public interface Levelable<K_Point extends Coordinate.OneDim, 
								   I_Point extends Coordinate.OneDim> 
							extends Projector.OneDim<K_Point, I_Point>, 
									Projector.Levelable<K_Point, I_Point>{

			public int 								getLevel();

			public default I_Point  				toImage(K_Point        _ker) 						{ return toImage(_ker, getLevel()); }
			public I_Point  						toImage(K_Point        _ker,  int _level);
			public default List<I_Point> 			toImage(List<K_Point>  _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toList()); }
			public default Set<I_Point> 			toImage(Set<K_Point>   _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toSet()); }

		}

		public BoundingBox.OneDim					getKernel();
		public BoundingBox.OneDim					getImage();

	}
	public interface TwoDims<K_Point extends Coordinate.TwoDims, I_Point extends Coordinate.TwoDims> 
						extends Projector<K_Point, I_Point> {

		public interface Levelable<K_Point extends Coordinate.TwoDims, I_Point extends Coordinate.TwoDims> 
							extends Projector.TwoDims<K_Point, I_Point>, 
									Projector.Levelable<K_Point, I_Point> {

			public BoundingBox.TwoDims				getImage(int _level);

			public K_Point  						toKernel(I_Point       _img,  int _level);
			public default List<K_Point> 			toKernel(List<I_Point> _imgs, int _level) 			{ return _imgs.stream().map(ker -> toKernel(ker, _level)).collect(Collectors.toList()); }
			public default Set<K_Point> 			toKernel(Set<I_Point>  _imgs, int _level) 			{ return _imgs.stream().map(ker -> toKernel(ker, _level)).collect(Collectors.toSet()); }

			public I_Point  						toImage(K_Point        _ker,  int _level);
			public default List<I_Point> 			toImage(List<K_Point>  _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toList()); }
			public default Set<I_Point> 			toImage(Set<K_Point>   _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toSet()); }

		}

		public BoundingBox.TwoDims					getKernel();
		public BoundingBox.TwoDims					getImage();

	}
	public interface ThreeDims<K_Point extends Coordinate.ThreeDims, I_Point extends Coordinate.ThreeDims> 
					extends Projector<K_Point, I_Point> {

		public interface Levelable<K_Point extends Coordinate.ThreeDims, I_Point extends Coordinate.ThreeDims> 
							extends Projector.ThreeDims<K_Point, I_Point>, 
									Projector.Levelable<K_Point, I_Point>{

			public int 								getLevel();

			public default I_Point  				toImage(K_Point        _ker) 						{ return toImage(_ker, getLevel()); }

			public I_Point  						toImage(K_Point        _ker,  int _level);
			public default List<I_Point> 			toImage(List<K_Point>  _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toList()); }
			public default Set<I_Point> 			toImage(Set<K_Point>   _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toSet()); }

		}

		public BoundingBox.ThreeDims				getKernel();
		public BoundingBox.ThreeDims				getImage();

	}

	public interface Levelable<K_Point extends Coordinate, I_Point extends Coordinate> 
						extends Projector<K_Point, I_Point> {

		public BoundingBox							getImage(int _level);

		public K_Point  							toKernel(I_Point       _img,  int _level);
		public default List<K_Point> 				toKernel(List<I_Point> _imgs, int _level) 			{ return _imgs.stream().map(img -> toKernel(img, _level)).collect(Collectors.toList()); }
		public default Set<K_Point> 				toKernel(Set<I_Point>  _imgs, int _level) 			{ return _imgs.stream().map(img -> toKernel(img, _level)).collect(Collectors.toSet()); }

		public I_Point  							toImage(K_Point _ker, 		  int _level);
		public default List<I_Point> 				toImage(List<K_Point>  _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toList()); }
		public default Set<I_Point> 				toImage(Set<K_Point>   _kers, int _level) 			{ return _kers.stream().map(ker -> toImage(ker, _level)).collect(Collectors.toSet()); }

	}

	public BoundingBox								getKernel();
	public BoundingBox								getImage();

	public K_Point  								toKernel(I_Point       _img);
	public default List<K_Point> 					toKernel(List<I_Point> _imgs) 						{ return _imgs.stream().map(this::toKernel).collect(Collectors.toList()); }
	public default Set<K_Point> 					toKernel(Set<I_Point>  _imgs) 						{ return _imgs.stream().map(this::toKernel).collect(Collectors.toSet()); }

	public I_Point  								toImage(K_Point        _ker);
	public default List<I_Point> 					toImage(List<K_Point>  _kers) 						{ return _kers.stream().map(this::toImage).collect(Collectors.toList()); }
	public default Set<I_Point> 					toImage(Set<K_Point>   _kers) 						{ return _kers.stream().map(this::toImage).collect(Collectors.toSet()); }

//	public void 									findIntruders();

}
