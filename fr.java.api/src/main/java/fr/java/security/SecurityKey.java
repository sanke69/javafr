/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import fr.java.lang.tuples.Pair;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;
import fr.java.security.enums.KeyNature;
import fr.java.security.exceptions.ConversionNotSupported;
import fr.java.security.exceptions.GenerationNotSupported;

public interface SecurityKey {

	public interface Generator {
		public void					setSeed(byte[] _bytes);

		public SecurityKey.Private	generate(int _keySize, KeyAlgorithm _type, KeyFormat _format) throws GenerationNotSupported, NoSuchAlgorithmException;
		public SecurityKey.Private	generate(int _keySize, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption, String _password) throws GenerationNotSupported, NoSuchAlgorithmException;
		public SecurityKey.Public 	generate(SecurityKey.Private _privateKey) throws GenerationNotSupported, NoSuchAlgorithmException;

		public Pair<SecurityKey.Private, SecurityKey.Public>
									generatePair(int _keySize, KeyAlgorithm _type, KeyFormat _format) throws GenerationNotSupported, NoSuchAlgorithmException;
	}
	public interface Converter {
		public SecurityKey 			convert(SecurityKey _key, KeyFormat _newFormat) throws ConversionNotSupported;
	}

	public interface Private extends SecurityKey {
		@Override public PrivateKey	getKey();
	}
	public interface Public  extends SecurityKey {
		@Override public PublicKey	getKey();
	}

	public default boolean 	isPrivate() 		{ return getNature() == KeyNature.PRIVATE; }
	public Private 			asPrivate();
	public default boolean 	isPublic() 			{ return getNature() == KeyNature.PUBLIC; }
	public Public 			asPublic();
	
	public KeyAlgorithm 	getType();
	public KeyFormat 		getFormat();
	public KeyNature 		getNature();
	public KeyEncryption 	getEncryption();

	public Key				getKey();

}
