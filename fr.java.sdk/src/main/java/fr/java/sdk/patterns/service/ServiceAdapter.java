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
 * @file     ServiceSkeleton.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.sdk.patterns.service;

import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;
import fr.java.patterns.service.Service;
import fr.java.sdk.patterns.stateable.StateableAdapter;

public abstract class ServiceAdapter extends StateableAdapter implements Service {
	private ID	 		id;
	private String 		name;

	public ServiceAdapter() {
		this(null);
	}
	public ServiceAdapter(String _name) {
		super();

		id        = IDs.random(16);
		name      = _name;
	}
	public ServiceAdapter(ID _id, String _name) {
		super();

		id        = _id;
		name      = _name;
	}

	@Override
	public final ID 	getId() {
		return id;
	}
	@Override
	public final String getName() {
		return name;
	}

}
