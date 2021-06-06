package fr.java.graph.impl.models.centralized.wrappers;

import java.util.Collection;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphDirectedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.patterns.valueable.ValueObject;

public class GTDNodeC implements GTNode.Directed {
	private final GTGraphDirectedC 	graph;
	private final ID            	id;

	public GTDNodeC(GTGraphDirectedC _graph, ID _id) throws IllegalOperationException {
		super();
		graph = _graph;
		id    = _id;
	}

	@Override
	public GTGraph.Directed 				getGraph() {
		return graph;
	}

	@Override
	public ID 								getId() {
		return id;
	}

	@Override
	public void 							setValueObject(ValueObject _value) {
		throw new NotYetImplementedException();
	}
	@Override
	public ValueObject 						getValueObject() {
		return graph.getNodeValueObject(id);
	}

	@Override
	public int 								getDegree() {
		return graph.getNodeDegree(id);
	}
	@Override
	public int 								getDegreeIn() {
		return graph.getNodeDegreeIn(id);
	}
	@Override
	public int 								getDegreeOut() {
		return graph.getNodeDegreeOut(id);
	}

	@Override
	public boolean 							hasNeighbors() {
		return graph.hasNeighbors(id);
	}
	@Override
	public Collection<? extends GTNode.Directed> 		getNeighbors() {
		return graph.getNeighbors(id);
	}

	@Override
	public boolean 							hasPredecessors() {
		return graph.hasPredecessors(id);
	}
	@Override
	public Collection<? extends GTNode.Directed> 		getPredecessors() {
		return graph.getPredecessors(id);
	}

	@Override
	public boolean 							hasSuccessors() {
		return graph.hasSuccessors(id);
	}
	@Override
	public Collection<? extends GTNode.Directed> 		getSuccessors() {
		return graph.getSuccessors(id);
	}

	@Override
	public boolean 							hasConnections() {
		return graph.hasConnections(id);
	}
	@Override
	public Collection<? extends GTEdge.Directed> 		getConnections() {
		return graph.getConnections(id);
	}

	@Override
	public boolean 							hasIncomings() {
		return graph.hasIncomings(id);
	}
	@Override
	public Collection<? extends GTEdge.Directed> 		getIncomings() {
		return graph.getIncomings(id);
	}

	@Override
	public boolean 							hasOutcomings() {
		return graph.hasOutcomings(id);
	}
	@Override
	public Collection<? extends GTEdge.Directed> 		getOutcomings() {
		return graph.getOutcomings(id);
	}

}
