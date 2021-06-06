package fr.java.graph.impl.models.centralized.wrappers;

import java.util.Collection;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphUndirectedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.patterns.valueable.ValueObject;

public class GTUNodeC implements GTNode.Undirected {
	private final GTGraphUndirectedC 	graph;
	private final ID            		id;

	public GTUNodeC(GTGraphUndirectedC _graph, ID _id) throws IllegalOperationException {
		super();
		id    = _id;
		graph = _graph;
	}

	@Override
	public GTGraph.Undirected 			getGraph() {
		return graph;
	}

	@Override
	public ID 							getId() {
		return id;
	}

	@Override
	public void 							setValueObject(ValueObject _value) {
		throw new NotYetImplementedException();
	}
	@Override
	public ValueObject					getValueObject() {
		return graph.getNodeValueObject(id);
	}

	@Override
	public int 							getDegree() {
		return graph.getNeighbors(id).size();
	}

	@Override
	public boolean 						hasNeighbors() {
		return graph.hasNeighbors(id);
	}
	@Override
	public Collection<? extends GTNode.Undirected> 	getNeighbors() {
		return graph.getNeighbors(id);
	}

	@Override
	public boolean 						hasConnections() {
		return graph.hasConnections(id);
	}
	@Override
	public Collection<? extends GTEdge.Undirected> 	getConnections() {
		return graph.getConnections(id);
	}

}
