package fr.java.graph.impl.models.centralized.wrappers;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphUndirectedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;

public class GTUEdgeC implements GTEdge.Undirected {
	private final GTGraphUndirectedC 	graph;
	private final ID            		id;

	public GTUEdgeC(GTGraphUndirectedC _graph, ID _id) throws IllegalOperationException {
		super();
		id    = _id;
		graph = _graph;
	}

	@Override
	public ID 									getId() {
		return id;
	}

	@Override
	public GTGraph.Undirected 					getGraph() {
		return graph;
	}

	@Override
	public ValueObject							getValueObject() {
		return graph.getEdgeValueObject(id);
	}

	@Override
	public Pair<ID, ID> 						getApexNodeIds() {
		return graph.getApexIds(id);
	}
	@Override
	public Pair<? extends GTNode.Undirected, ? extends GTNode.Undirected> getApexNodes() {
		return graph.getApexNodes(id);
	}

}
