package fr.java.graph.impl.models.centralized.wrappers;

import java.util.Collection;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphGatedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;

public class GTGGateC implements GTGate {

	private final GTGraphGatedC graph;
	private final ID            id, ownerId;

	public GTGGateC(GTGraphGatedC _graph, ID _id) throws IllegalOperationException {
		super();
		graph   = _graph;

		id      = _id;
		ownerId = null;
	}
	@Deprecated
	public GTGGateC(GTGraphGatedC _graph, ID _id, ID _ownerId) throws IllegalOperationException {
		super();
		graph   = _graph;

		id      = _id;
		ownerId = _ownerId;
	}

	@Override
	public ID 						getId() {
		return id;
	}

	@Override
	public GTGraph.Gated 			getGraph() {
		return graph;
	}
	@Override
	public GTNode.Gated 					getOwner() {
		return graph.getNode(ownerId);
	}
	@Override
	public ValueObject 				getValueObject() {
		return graph.getGateValueObject(id);
	}

	@Override
	public boolean 					isInput() {
		return graph.getInputs(ownerId).contains(this);
	}
	@Override
	public boolean 					isOutput() {
		return graph.getOutputs(ownerId).contains(this);
	}

	@Override
	public boolean 					hasNeighbors() {
		return graph.hasNeighbors(ownerId);
	}
	@Override
	public Collection<Pair<? extends GTNode.Gated, ? extends GTGate>> getNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean 					hasConnections() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Collection<? extends GTEdge.Gated> 						getConnections() {
		// TODO Auto-generated method stub
		return null;
	}

}
