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
