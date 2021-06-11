module javafr.maths {
	requires transitive javafr;
	requires transitive javafr.beans;
	requires transitive javafx.graphics;

	exports fr.java.maths;

	exports fr.java.maths.numbers.complex;
	exports fr.java.maths.numbers.ranged;

	exports fr.java.maths.algebra;
	exports fr.java.maths.algebra.tensors;
	exports fr.java.maths.algebra.vectors;
	exports fr.java.maths.algebra.matrices;
	exports fr.java.maths.algebra.matrices.doubles;
	exports fr.java.maths.algebra.matrices.doubles.decompositions;

	exports fr.java.maths.geometry;
	exports fr.java.maths.geometry.types;

	exports fr.java.maths.geometry.plane;
	exports fr.java.maths.geometry.plane.projectors;
	exports fr.java.maths.geometry.plane.types;
	exports fr.java.maths.geometry.plane.shapes;
	exports fr.java.maths.geometry.plane.transformations;
	exports fr.java.maths.geometry.plane.transformations.generic;

	exports fr.java.maths.geometry.space.types;
	exports fr.java.maths.geometry.space.shapes;
	exports fr.java.maths.geometry.space.shapes.quadrics;
	exports fr.java.maths.geometry.space.shapes.quadrics.surfaces;
	exports fr.java.maths.geometry.space.shapes.quadrics.shapes;
	exports fr.java.maths.geometry.space.transformations;
	exports fr.java.maths.geometry.space.transformations.specials;
	exports fr.java.maths.geometry.space.camera;
	exports fr.java.maths.geometry.space.camera.models;
	exports fr.java.maths.geometry.space.camera.projections;
	exports fr.java.maths.geometry.space.camera.behaviors;
	exports fr.java.maths.geometry.space.utils;

	exports fr.java.maths.topology;
	exports fr.java.maths.stats;
	exports fr.java.maths.stats.randoms;
	exports fr.java.maths.stats.laws.continuous;
	exports fr.java.maths.stats.laws.discrete;
	exports fr.java.maths.utils.graph;
	exports fr.java.maths.window;

	exports fr.java.maths.interpolation.functions;
	exports fr.java.maths.interpolation.functions.splines.cubic;
	exports fr.java.maths.interpolation.coordinates;

	exports fr.java.maths.stats.series;

}
