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
package fr.java.file.text.config;

import java.util.Set;
import java.util.regex.Pattern;

import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;
import fr.java.patterns.identifiable.Nameable;

public interface CfgProperties extends Composite {
	static final String  NULL_SECTION    = "NO_SECTION";

	static final Pattern SECTION_PATTERN = Pattern.compile("\\s*\\[(?<SECTION>[^]]*)\\]\\s*#?.*");
	static final Pattern KEY_PATTERN     = Pattern.compile("\\s*(?<KEY>[^=]*)=(?<VALUE>[^#]*)");

	public interface Section extends Nameable, Composite {

		public interface Editable extends Section {

			public Key 				addKey(String _name, String _value);
			public Key 				removeKey(String _name);

			public Key 				addKey(Key _key);
			public Key 				removeKey(Key _key);

		}

		@SuppressWarnings("unchecked")
		default public Set<Component> 	getChildren() { return (Set<Component>) (Set<?>) getKeys(); }
		public Set<Key> 				getKeys();

		public boolean 					hasKey(String _name);
		public Key 						getKey(String _name);

	}
	public interface Key     extends Nameable, /*Valueable,*/ Component {

		default public Composite 		getParent() { return getSection(); }

		public Section 					getSection();

//		public void   					setValue(String _value);
		public String 					getValue();

	}

	public Set<Section> 				getSections();
	public Section 						getSection(String _name);

	public String 						getValue(String _session, String _key);

	public default String 				getString(String _section, String _key, String _defaultvalue) {
		String value = getSection(_section).getKey(_key).getValue();
		if (value == null)
			return _defaultvalue;
		return value;
	}
	public default int 					getInt(String _section, String _key, int _defaultvalue) {
		String value = getSection(_section).getKey(_key).getValue();
		if (value == null)
			return _defaultvalue;
		return Integer.parseInt(value);
	}
	public default float 				getFloat(String _section, String _key, float _defaultvalue) {
		String value = getSection(_section).getKey(_key).getValue();
		if (value == null)
			return _defaultvalue;
		return Float.parseFloat(value);
	}
	public default double 				getDouble(String _section, String _key, double _defaultvalue) {
		String value = getSection(_section).getKey(_key).getValue();
		if (value == null)
			return _defaultvalue;
		return java.lang.Double.parseDouble(value);
	}

}
