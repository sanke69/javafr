package fr.java.jvm.properties.id.generators;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import fr.java.jvm.properties.id.IDs;
import fr.java.lang.functionals.Constructor;
import fr.java.lang.properties.ID;
import fr.java.maths.stats.randoms.MT19937;

public class GenUUID implements Constructor.NoArg<ID> {
	private final int     size  = 32;				// bytes, min=4
	private final MT19937 random;

	public GenUUID() {
		super();
		random = new MT19937();
	}
	public GenUUID(int _seed) {
		super();
		random = new MT19937(_seed);
	}

	@Override
	public ID newInstance() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
		DataOutputStream      dos  = new DataOutputStream(baos);

		random.ints(size / 4).forEach(t -> {
			try {
				dos.writeInt(t);
			} catch(IOException e) {
				e.printStackTrace();
			}
		});

		return IDs.newBytes(baos.toByteArray());
	}

}
