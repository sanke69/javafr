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
package fr.java.patterns.connection;

import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.Valueable;

public interface Connector extends Valueable {

	public Connectable 	getParent();
//	public String	 	getType();
//	public int	 		getMaxLinkAllowed();

	public boolean 		isInput();
	public boolean 		isOutput();

	public default CompatibilityRules<Connector> 	compatibility() {
		return null;
	}
	public default CompatibilityResult 				compatible(final Connector _source) {
		if(compatibility() != null)
			return compatibility().apply(_source, this);
		
		return new CompatibilityResult() {
			private final boolean compatible;
			private final String  errorMessage;
	
			{
				Pair<Boolean, String> result = ConnectorRules.defaultCompatibility(_source, Connector.this);
				compatible   = result.getFirst();
				errorMessage = result.getSecond();
			}
	
			@Override
			public boolean 	isCompatible() {
				return compatible;
			}
	
			@Override
			public String 	getMessage() {
				if(isCompatible())
					return "compatible   : " + _source.getParent() + " -> " + getParent();
				return "incompatible : " + _source.getParent() + " -> " + getParent() + ", reason: " + errorMessage;
			}
	
			@Override
			public String 	getStatus() {
				throw new UnsupportedOperationException("Not supported yet.");
			}
	
			@Override
			public String 	toString() {
				StringBuilder sb = new StringBuilder();
	
				sb.append("[CR(DftConnVO): ");
	//    		sb.append("isCompatible? " + (isCompatible() ? "T" : "F"));
	//    		sb.append(", msg= " + getMessage());
				sb.append(getMessage());
				sb.append(" ]");
	
				return sb.toString();
			}
		};
	}

}
