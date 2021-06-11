package fr.java.graph;

import java.util.Collection;

import fr.java.lang.tuples.Pair;
import fr.java.patterns.capabilities.Identifiable;
import fr.java.patterns.composite.Component;
import fr.java.patterns.valueable.Valueable;

public interface GTGate extends Component.Single, Identifiable, Valueable {

	@Override
	public default GTNode.Gated								getParent				() {
		return getOwner();
	}
	public GTGraph.Gated 									getGraph				();
	public GTNode.Gated 									getOwner				();

	public boolean				 							isInput					();
	public boolean				 							isOutput				();

	public boolean 											hasNeighbors			();
    public Collection<Pair<? extends GTNode.Gated, ? extends GTGate>> 
    														getNeighbors			();

	public boolean 											hasConnections			();
    public Collection<? extends GTEdge.Gated> 				getConnections			();

}
