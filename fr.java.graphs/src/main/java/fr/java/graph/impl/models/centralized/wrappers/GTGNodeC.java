package fr.java.graph.impl.models.centralized.wrappers;

import java.util.Collection;

import fr.java.beans.impl.BeanPropertyList;
import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.impl.models.centralized.GTGraphGatedC;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.patterns.valueable.ValueObject;

public class GTGNodeC implements GTNode.Gated {
	private final GTGraphGatedC graph;
	private final ID           id;

	public GTGNodeC(GTGraphGatedC _graph, ID _id) throws IllegalOperationException {
		super();
		graph = _graph;
		id    = _id;
	}

	@Override
	public GTGraph.Gated			 		getGraph() {
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
	public ValueObject						getValueObject() {
		return graph.getNodeValueObject(id);
	}

	@Override
	public BeanPropertyList<? extends GTGate> getInputs() {
		return graph.getInputs(id);
	}
	@Override
	public BeanPropertyList<? extends GTGate> getOutputs() {
		return graph.getOutputs(id);
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
	public int 								getDegreeIn(int _inputIdx) {
		return graph.getNodeDegreeIn(id, _inputIdx);
	}
	@Override
	public int 								getDegreeOut() {
		return graph.getNodeDegreeOut(id);
	}
	@Override
	public int 								getDegreeOut(int _outputIdx) {
		return graph.getNodeDegreeOut(id, _outputIdx);
	}

	@Override
	public boolean 							hasPredecessors() {
		return graph.hasPredecessors(id);
	}
	@Override
	public Collection<? extends GTNode.Gated> 		getPredecessors() {
		return graph.getPredecessors(id);
	}

	@Override
	public boolean 							hasPredecessors(int _inputIdx) {
		return graph.hasPredecessors(id, _inputIdx);
	}
	@Override
	public Collection<? extends GTNode.Gated> 		getPredecessors(int _inputIdx) {
		return graph.getPredecessors(id, _inputIdx);
	}

	@Override
	public boolean 							hasSuccessors() {
		return graph.hasPredecessors(id);
	}
	@Override
	public Collection<? extends GTNode.Gated> 		getSuccessors() {
		return graph.getSuccessors(id);
	}

	@Override
	public boolean 							hasSuccessors(int _outputIdx) {
		return graph.hasSuccessors(id, _outputIdx);
	}
	@Override
	public Collection<? extends GTNode.Gated> 		getSuccessors(int _outputIdx) {
		return graph.getSuccessors(id, _outputIdx);
	}

	@Override
	public boolean 							hasIncomings() {
		return graph.hasIncomings(id);
	}
	@Override
	public Collection<? extends GTEdge.Gated> 		getIncomings() {
		return graph.getIncomings(id);
	}

	@Override
	public boolean 							hasIncomings(int _inputIdx) {
		return graph.hasIncomings(id, _inputIdx);
	}
	@Override
	public Collection<? extends GTEdge.Gated> 		getIncomings(int _inputIdx) {
		return graph.getIncomings(id, _inputIdx);
	}

	@Override
	public boolean 							hasOutcomings() {
		return graph.hasOutcomings(id);
	}
	@Override
	public Collection<? extends GTEdge.Gated> 		getOutcomings() {
		return graph.getOutcomings(id);
	}

	@Override
	public boolean 							hasOutcomings(int _outputIdx) {
		return graph.hasOutcomings(id, _outputIdx);
	}
	@Override
	public Collection<? extends GTEdge.Gated> 		getOutcomings(int _outputIdx) {
		return graph.getOutcomings(id, _outputIdx);
	}

}
