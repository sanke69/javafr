package fr.java.graph.impl.models.centralized.wrappers;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphDirectedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;

public class GTDEdgeC implements GTEdge.Directed {
	private final GTGraphDirectedC 	graph;
	private final ID            	id;

	public GTDEdgeC(GTGraphDirectedC _graph, ID _id) throws IllegalOperationException {
		super();
		graph = _graph;
		id    = _id;
	}

	@Override
	public GTGraph.Directed 						getGraph() {
		return graph;
	}

	@Override
	public ID 									getId() {
		return id;
	}

	@Override
	public ValueObject 							getValueObject() {
		return graph.getEdgeValueObject(id);
	}

	@Override
	public Pair<ID, ID> 						getApexNodeIds() {
		return graph.getApexIds(id);
	}
	@Override
	public Pair<? extends GTNode.Directed, ? extends GTNode.Directed> getApexNodes() {
		return graph.getApexNodes(id);
	}

	public ID 									getSourceId() {
		return getApexNodeIds().getFirst();
	}
	@Override
	public GTNode.Directed									getSource() {
		return getApexNodes().getFirst();
	}

	public ID 									getDestinationId() {
		return getApexNodeIds().getSecond();
	}
	@Override
	public GTNode.Directed									getDestination() {
		return getApexNodes().getSecond();
	}

}
