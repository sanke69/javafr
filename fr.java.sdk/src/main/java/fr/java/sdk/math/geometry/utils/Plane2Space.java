package fr.java.sdk.math.geometry.utils;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.geometry.Geometry;
import fr.java.sdk.math.geometry.plane.transformations.Transformation2D;
import fr.java.sdk.math.geometry.plane.transformations.special.Euclidian2D;
import fr.java.sdk.math.geometry.plane.transformations.special.Rotation2D;
import fr.java.sdk.math.geometry.plane.transformations.special.Scale2D;
import fr.java.sdk.math.geometry.plane.transformations.special.Translation2D;
import fr.java.sdk.math.geometry.space.transformations.Transformation3D;
import fr.java.sdk.math.geometry.space.transformations.specials.Euclidian3D;
import fr.java.sdk.math.geometry.space.transformations.specials.Rotation3D;
import fr.java.sdk.math.geometry.space.transformations.specials.Scale3D;
import fr.java.sdk.math.geometry.space.transformations.specials.Translation3D;

public class Plane2Space {
	Vector3D normal;

	public Plane2Space(Vector3D _normal) {
		super();
		normal = _normal;
	}

	public Transformation3D transform(Transformation2D _transformation) {
		if(_transformation instanceof Translation2D)
			return transformTranslation((Translation2D) _transformation);

		if(_transformation instanceof Rotation2D)
			return transformRotation((Rotation2D) _transformation);

		if(_transformation instanceof Scale2D)
			return transformScale((Scale2D) _transformation);

		throw new NotYetImplementedException();
	}

	public Euclidian3D transformEuclidian(Euclidian2D _euclidian) {
		if(normal.equals(Geometry.Space.WorldXAxis))
			return Euclidian3D.aroundX(0.0f, _euclidian.getTX(), _euclidian.getTY(), _euclidian.getAngle());

		if(normal.equals(Geometry.Space.WorldYAxis))
			return Euclidian3D.aroundY(_euclidian.getTX(), 0.0, _euclidian.getTY(), _euclidian.getAngle());

		if(normal.equals(Geometry.Space.WorldZAxis))
			return Euclidian3D.aroundZ(_euclidian.getTX(), _euclidian.getTY(), 0.0f, _euclidian.getAngle());

		throw new NotYetImplementedException();
	}

	public Translation3D transformTranslation(Translation2D _translation) {
		if(normal.equals(Geometry.Space.WorldXAxis))
			return new Translation3D(0.0f, _translation.getTX(), _translation.getTY());

		if(normal.equals(Geometry.Space.WorldYAxis))
			return new Translation3D(_translation.getTX(), 0.0, _translation.getTY());

		if(normal.equals(Geometry.Space.WorldZAxis))
			return new Translation3D(_translation.getTX(), _translation.getTY(), 0.0f);


		throw new NotYetImplementedException();
	}

	public Rotation3D transformRotation(Rotation2D _rotation) {
		if(normal.equals(Geometry.Space.WorldXAxis))
			return Rotation3D.aroundX(_rotation.getAngle());

		if(normal.equals(Geometry.Space.WorldYAxis))
			return Rotation3D.aroundY(_rotation.getAngle());

		if(normal.equals(Geometry.Space.WorldZAxis))
			return Rotation3D.aroundZ(_rotation.getAngle());


		throw new NotYetImplementedException();
	}

	public Scale3D transformScale(Scale2D _scale) {
		if(normal.equals(Geometry.Space.WorldXAxis))
			return new Scale3D(0.0f, _scale.getSX(), _scale.getSY());

		if(normal.equals(Geometry.Space.WorldYAxis))
			return new Scale3D(_scale.getSX(), 0.0f, _scale.getSY());

		if(normal.equals(Geometry.Space.WorldZAxis))
			return new Scale3D(_scale.getSX(), _scale.getSY(), 0.0f);


		throw new NotYetImplementedException();
	}

}
