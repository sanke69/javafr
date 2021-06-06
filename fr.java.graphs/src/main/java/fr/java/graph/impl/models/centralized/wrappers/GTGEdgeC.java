package fr.java.graph.impl.models.centralized.wrappers;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphGatedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;
import fr.java.utils.graphs.Tuples;

public class GTGEdgeC implements GTEdge.Gated {
	private final GTGraphGatedC graph;
	private final ID            id;

	public GTGEdgeC(GTGraphGatedC _graph, ID _id) throws IllegalOperationException {
		super();
		graph = _graph;
		id    = _id;
	}

	@Override
	public GTGraph.Gated 						getGraph() {
		return graph;
	}

	@Override
	public ID 									getId() {
		return id;
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
	public Pair<? extends GTNode.Gated, ? extends GTNode.Gated>	getApexNodes() {
		return graph.getApexNodes(id);
	}

	@Override
	public Pair<ID, ID> 						getApexGateIds() {
		return graph.getApexGateIds(id);
	}
	@Override
	public Pair<? extends GTGate, ? extends GTGate> getApexGates() {
		return graph.getApexGates(id);
	}

	@Override
	public Pair<ID, ID> 						getSenderIds() {
		return Tuples.of(getApexNodeIds().getFirst(), getApexGateIds().getFirst());
	}
	@Override
	public GTNode.Gated									getSenderNode() {
		throw new NotYetImplementedException();
	}
	@Override
	public GTGate									getSenderGate() {
		throw new NotYetImplementedException();
//		return getApex().getFirst();
	}

	@Override
	public Pair<ID, ID> 						getReceiverIds() {
		return Tuples.of(getApexNodeIds().getSecond(), getApexGateIds().getSecond());
	}
	@Override
	public GTNode.Gated		 							getReceiverNode() {
		throw new NotYetImplementedException();
//		return (Node<N, G>) getApex().getSecond();
	}
	@Override
	public GTGate 								getReceiverGate() {
		throw new NotYetImplementedException();
	}

}
