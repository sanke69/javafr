package fr.java.sdk.math.geometry.space.shapes;

import java.util.List;

import fr.java.math.geometry.space.Point3D;

public class Polygon3D {

	List<Point3D> tops;
	
	public Polygon3D(Point3D... _pts) {
		super();
		
		for(Point3D pt : _pts)
			tops.add(pt);
	}
	
	public List<Point3D> getTops() {
		return tops;
	}

}
