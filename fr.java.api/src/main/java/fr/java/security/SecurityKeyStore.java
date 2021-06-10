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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public interface SecurityKeyStore {

	public void 		addKey(String _alias, PrivateKey _key, String _keyPWD, Certificate _certs);
	public void 		addKey(String _alias, PrivateKey _key, String _keyPWD, Certificate[] _certs);
	public void 		addKey(String _storePWD, String _alias, PublicKey _key, String _keyPWD);

	public void 		addCertificate(String _alias, Certificate _certs);

	public Key  		getKey(String _alias, String _keyPWD);
	public Certificate  getCertificate(String _alias);

	public void 		save(String _storePWD);

}
