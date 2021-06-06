package fr.java.patterns.connectable;

import fr.java.lang.tuples.Pair;
import fr.java.patterns.compatible.CompatibilityResult;
import fr.java.patterns.compatible.CompatibilityRules;
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
