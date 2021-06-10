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
package fr.java.patterns.connectable;

import fr.java.lang.tuples.Pair;
import fr.java.patterns.compatible.CompatibilityResult;
import fr.java.patterns.compatible.CompatibilityRules;

public interface ConnectorRules extends CompatibilityRules<Connector> {

	public CompatibilityResult apply(Connector _source, Connector _destination);

	public static Pair<Boolean, String> defaultCompatibility(Connector _source, Connector _destination) {
		boolean differentObjects 				= _source.getParent() != _destination.getParent();
		boolean compatibleLink   				= _source.isInput()   && _destination.isOutput();
		boolean compatibleType	 				= true;
		boolean compatibleValue 				= _destination.getValueObject().compatible(_source.getValueObject()).isCompatible();
		boolean lessThanMaxNumberOfConnections 	= true;

//		compatibleType	 						= _destination.getType().equals(_source.getType());
		
//		int numConnectionsOfSender      		= _source      . getNeighbors();
//		int numConnectionsOfReceiver    		= _destination . getNeighbors();
//		int maxNumConnections           		= Math.min(_source.getMaxLinkAllowed(), _destination.getMaxLinkAllowed());
//		lessThanMaxNumberOfConnections  		= numConnectionsOfSender < maxNumConnections && numConnectionsOfReceiver < maxNumConnections;

		String  errorMessage					= null;

		if(!differentObjects)
			errorMessage = "Connections can only established between different nodes. Sender node cannot be equal to receiver node.";
		else if (!compatibleType)
			errorMessage = "Connections can only established between connectors of the same connection/flow type.";
//		else if (!lessThanMaxNumberOfConnections)
//			errorMessage = "Trying to creating more than " + maxNumConnections + " number of connections is not allowed.";

		final String message = errorMessage;
		return new Pair<Boolean, String>() {
			@Override public Boolean getFirst() { return differentObjects && compatibleType && lessThanMaxNumberOfConnections; }
			@Override public String getSecond() { return message; }
		};
	}

}
