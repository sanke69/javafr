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
 * @file     ViewRequest.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.patterns.mvc;

import fr.java.lang.properties.PropertyStorage;

//@Deprecated
// TODO:: 
// Must be merged with Diagram !!!
@Deprecated
public interface ViewRequest extends PropertyStorage {

	static final String KEY_STYLE 						= "visualization-request:style";

	static final String	KEY_MAX_CONNECTOR_SIZE			= "visualization-request:connector:max-size";
	static final String	KEY_CONNECTOR_AUTO_LAYOUT		= "visualization-request:connector:auto-layout";
	static final String	KEY_DISABLE_EDITING				= "visualization-request:skin:disable-editing";
	static final String	KEY_NODE_NOT_REMOVABLE			= "visualization-request:skin:node-not-removable";

	static final String KEY_CONNECTOR_PREFER_TOP_DOWN 	= "visualization-request:connector:prefer-top-down";

	public void 	setStyle(String style);
	public String 	getStyle();

//	public void 	addListener(BeanPropertyMapChangeListener<String, Object> vReqLister);
//	public void 	removeListener(BeanPropertyMapChangeListener<String, Object> vReqLister);

}
