package fr.java.jvm.properties.id.generators;

import fr.java.jvm.properties.id.IDs;
import fr.java.lang.functionals.Constructor;
import fr.java.lang.properties.ID;

public class GenCounter implements Constructor.NoArg<ID> {
	private int lastID = 0;

	public GenCounter() {
		super();
		lastID = 0;
	}
	public GenCounter(int _firstId) {
		super();
		lastID = _firstId;
	}

	@Override
	public ID newInstance() {
		return IDs.newInt(lastID++);
	}

}
