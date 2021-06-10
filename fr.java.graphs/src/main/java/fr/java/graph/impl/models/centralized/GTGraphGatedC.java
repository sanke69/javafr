package fr.java.graph.impl.models.centralized;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.java.beans.impl.BeanPropertyList;
import fr.java.beans.impl.BeanPropertyListView;
import fr.java.beans.impl.BeanPropertyMap;
import fr.java.beans.impl.BeanPropertySet;
import fr.java.beans.impl.SimpleBeanPropertyList;
import fr.java.beans.impl.SimpleBeanPropertyMap;
import fr.java.beans.impl.SimpleBeanPropertySet;
import fr.java.graph.GT;
import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.traversal.GTGraphTraversal;
import fr.java.graph.impl.models.centralized.wrappers.GTGEdgeC;
import fr.java.graph.impl.models.centralized.wrappers.GTGGateC;
import fr.java.graph.impl.models.centralized.wrappers.GTGNodeC;
import fr.java.lang.exceptions.AlreadyExistingGateException;
import fr.java.lang.exceptions.AlreadyExistingNodeException;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.InvalidMembershipException;
import fr.java.lang.exceptions.InvalidRelationshipException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;
import fr.java.patterns.valueable.ValueType;
import fr.utils.graphs.IDs;
import fr.utils.graphs.Tuples;

public class GTGraphGatedC implements GTGraph.Gated {

	final ID										id;

	final BeanPropertyMap<ID, GTGNodeC>				nodes;
	final BeanPropertyMap<GTGNodeC, ID>				nodes_reverse;
	final BeanPropertyMap<ID, ValueObject>				nodeValues;
	final Map<ID, BeanPropertyList<ID>>					nodeInputs;
	final Map<ID, BeanPropertyList<ID>>					nodeOutputs;

	final Map<ID, BeanPropertyList<GTGGateC>>		nodeInputViews;
	final Map<ID, BeanPropertyList<GTGGateC>>		nodeOutputViews;

	final BeanPropertyMap<ID, GTGGateC>				gates;
	final BeanPropertyMap<GTGGateC, ID>				gates_reverse;
	final BeanPropertyMap<ID, ValueObject>				gateValues;
	final Map<ID, ID>									gateOwners;
	final Map<ID, BeanPropertySet<Pair<ID, ID>>>		gatePreviouses, gateNexts;
	final Map<ID, BeanPropertySet<ID>>					gateIncomings,  gateOutcomings;
	
	final BeanPropertyMap<ID, GTGEdgeC>				edges;
	final BeanPropertyMap<GTGEdgeC, ID>				edges_reverse;
	final Map<ID, Pair<Pair<ID, ID>, Pair<ID, ID>>>		bounds;
	final Map<Pair<Pair<ID, ID>, Pair<ID, ID>>, ID>		bounds_reverse;
	final BeanPropertyMap<ID, ValueObject>				edgeValues;

	// Extra accessors, no retained information
	final Supplier<List<ID>>						inputs, outputs;
	final Function<ID, List<ID>>					node_previouses, node_nexts;
	final Function<ID, List<ID>>					node_incomings,  node_outcomings;
	
	public GTGraphGatedC() {
		super();

		id              = IDs.random();

		nodes           = new SimpleBeanPropertyMap<ID, GTGNodeC>();
		nodes_reverse	= new SimpleBeanPropertyMap<GTGNodeC, ID>();
		nodeValues      = new SimpleBeanPropertyMap<ID, ValueObject>();
		nodeInputs      = new HashMap<ID, BeanPropertyList<ID>>();
		nodeOutputs     = new HashMap<ID, BeanPropertyList<ID>>();

		nodeInputViews  = new HashMap<ID, BeanPropertyList<GTGGateC>>();
		nodeOutputViews = new HashMap<ID, BeanPropertyList<GTGGateC>>();

		gates           = new SimpleBeanPropertyMap<ID, GTGGateC>();
		gates_reverse   = new SimpleBeanPropertyMap<GTGGateC, ID>();
		gateValues      = new SimpleBeanPropertyMap<ID, ValueObject>();
		gateOwners      = new HashMap<ID, ID>();
		gatePreviouses  = new HashMap<ID, BeanPropertySet<Pair<ID, ID>>>();
		gateNexts       = new HashMap<ID, BeanPropertySet<Pair<ID, ID>>>();
		gateIncomings   = new HashMap<ID, BeanPropertySet<ID>>();
		gateOutcomings  = new HashMap<ID, BeanPropertySet<ID>>();

		edges            = new SimpleBeanPropertyMap<ID, GTGEdgeC>();
		edges_reverse    = new SimpleBeanPropertyMap<GTGEdgeC, ID>();
		edgeValues       = new SimpleBeanPropertyMap<ID, ValueObject>();
		bounds           = new HashMap<ID, Pair<Pair<ID, ID>, Pair<ID, ID>>>();
		bounds_reverse   = new HashMap<Pair<Pair<ID, ID>, Pair<ID, ID>>, ID>();

		inputs           = () -> {
			return nodeInputs.values()
						.stream()
						.flatMap(ids -> ids.stream())
						.distinct()
						.collect(Collectors.toList());
		};
		outputs          = () -> {
			return nodeOutputs.values()
						.stream()
						.flatMap(ids -> ids.stream())
						.distinct()
						.collect(Collectors.toList());
		};
		node_previouses  = (nodeId) -> {
			return nodeInputs.get(nodeId)
						.stream()
						.flatMap(id -> gatePreviouses.get(id).stream())
						.map(Pair::getFirst)
						.distinct()
						.collect(Collectors.toList());
		};
		node_nexts      = (nodeId) -> {
			return nodeOutputs.get(nodeId)
						.stream()
						.flatMap(id -> gateNexts.get(id).stream())
						.map(Pair::getFirst)
						.distinct()
						.collect(Collectors.toList());
		};
		node_incomings  = (nodeId) -> {
			return nodeInputs.get(nodeId)
						.stream()
						.flatMap(id -> gatePreviouses.get(id).stream())
						.map(Pair::getFirst)
						.distinct()
						.collect(Collectors.toList());
		};
		node_outcomings = (nodeId) -> {
			return nodeOutputs.get(nodeId)
						.stream()
						.flatMap(id -> gateNexts.get(id).stream())
						.map(Pair::getFirst)
						.distinct()
						.collect(Collectors.toList());
		};
	}

	@Override
	public ID 									getId					() {
		return id;
	}

    // GRAPH Visitor
    // =============
	public void 								traverse				(ID _startId, GTGraphTraversal _traversal) {
		_traversal.visit(this, _startId);
	}

    // NODE Related Methods
    // ====================
	@Override
	public GTNode.Gated		 					addNode					() {
		return __addNode(__newID(nodes), ValueObject.empty());
	}
	@Override
	public GTNode.Gated 						addNode					(ValueObject _value) throws IllegalOperationException, AlreadyExistingNodeException {
		return __addNode(__newID(nodes), _value);
	}
	@Override
	public GTNode.Gated		 					addNode					(Object _value) throws AlreadyExistingNodeException {
		return __addNode(__newID(nodes), (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value));
	}
	@Override
	public GTNode.Gated							addNode					(ID _nodeId) throws AlreadyExistingNodeException {

		return __addNode(_nodeId, ValueObject.empty());
	}
	@Override
	public GTNode.Gated 						addNode					(ID _nodeId, ValueObject _value) throws IllegalOperationException, AlreadyExistingNodeException {
		return __addNode(_nodeId, _value);
	}
	@Override
	public GTNode.Gated		 					addNode					(ID _nodeId, Object _value) throws AlreadyExistingNodeException {
		return __addNode(_nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value));
	}

	@Override
	public boolean 								containsNode			(ID _nodeId) {
		return nodes.containsKey(_nodeId);
	}
	@Override
	public boolean 								containsNode			(Object _nodeValue) {
		return nodeValues.containsValue(_nodeValue);
//		return containsNode(getNodeId(_nodeValue));
//		return containsNode(getNodeId(_nodeValue));
	}

	@Override
	public GTGNodeC 							getNode					(ID _nodeId) {
		return nodeValues.entrySet()
						 .stream()
						 .filter(e -> e.getKey().equals(_nodeId))
						 .findFirst()
						 .map(e -> nodes.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTGNodeC					 			getNode					(Object _nodeValue) {
		return nodeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_nodeValue))
						 .findFirst()
						 .map(e -> nodes.get(e.getKey()))
						 .orElseGet(() -> null);
	}

	@Override
	public void									removeNode				(ID _nodeId) {
		__assertNode(_nodeId);
		__removeNode(_nodeId);
	}
	@Override
	public void 								removeNode				(Object _nodeValue) {
		ID nodeId = __getNodeId(_nodeValue);
		if(nodeId == null)
			return ;
		__removeNode(nodeId);
	}

	@Override
	public GTGate		 							getGate					(ID _gateId) {
		__assertGate(_gateId);
		return gates.get(_gateId);
	}

	@Override
	public GTGate 								getGate					(Object _gate) {
		return gateValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_gate))
						 .findFirst()
						 .map(e -> gates.get(e.getKey()))
						 .orElseGet(() -> null);
	}

    // ... ~ Gate Aspects
	@Override
    public BeanPropertySet<? extends GTNode.Gated>		getNodes				() {
		return nodes_reverse.keySet();
	}
	
	@Override
	public BeanPropertySet<? extends GTGate> 		getGates				() {
		return gates_reverse.keySet();
	}

	@Override
	public ValueObject 							setNodeValue			(ID _nodeId, ValueObject _newValue) throws AlreadyExistingNodeException {
		throw new NotYetImplementedException();
	}
	@Override
	public Object 								setNodeValue			(ID _nodeId, Object _newValue) throws AlreadyExistingNodeException {
		throw new NotYetImplementedException();
	}

	@Override
	public ValueObject 							setGateValue			(ID _gateId, ValueObject _newValue) throws AlreadyExistingGateException {
		throw new NotYetImplementedException();
	}
	@Override
	public Object 								setGateValue			(ID _gateId, Object _newValue) throws AlreadyExistingNodeException {
		throw new NotYetImplementedException();
	}

	@Override
	public GTGate 								addGate					(ID _nodeId, boolean _isInput) {
		return __addGate(__newID(gates), _nodeId, ValueObject.empty(), _isInput, -1);
	}
	@Override
	public GTGate 								addGate					(ID _nodeId, ValueObject _value, boolean _isInput) {
		return __addGate(__newID(gates), _nodeId, _value, _isInput, -1);
	}
	@Override
	public GTGate 								addGate					(ID _nodeId, Object _value, boolean _isInput) {
		return __addGate(__newID(gates), _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), _isInput, -1);
	}
	@Override
	public GTGate 								addGate					(ID _nodeId, ID _gateId, boolean _isInput) {
		return __addGate(_gateId, _nodeId, ValueObject.empty(), _isInput, -1);
	}
	@Override
	public GTGate 								addGate					(ID _nodeId, ID _gateId, ValueObject _value, boolean _isInput) {
		return __addGate(_gateId, _nodeId, _value, _isInput, -1);
	}
	@Override
	public GTGate 								addGate					(ID _nodeId, ID _gateId, Object _value, boolean _isInput) {
		return __addGate(_gateId, _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), _isInput, -1);
	}

	@Override
	public GTGate			 						addInput				(ID _nodeId) {
		return __addGate(__newID(gates), _nodeId, ValueObject.empty(), true, -1);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, int _index) {
		return __addGate(__newID(gates), _nodeId, ValueObject.empty(), true, _index);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ValueObject _value) {
		return __addGate(__newID(gates), _nodeId, _value, true, -1);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ValueObject _value, int _index) {
		return __addGate(__newID(gates), _nodeId, _value, true, _index);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, Object _value) {
		return __addGate(__newID(gates), _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), true, -1);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, Object _value, int _index) {
		return __addGate(__newID(gates), _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), true, _index);
	}
	@Override
	public GTGate			 						addInput				(ID _nodeId, ID _gateId) {
		return __addGate(_gateId, _nodeId, ValueObject.empty(), true, -1);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ID _gateId, int _index) {
		return __addGate(_gateId, _nodeId, ValueObject.empty(), true, _index);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ID _gateId, ValueObject _value) {
		return __addGate(_gateId, _nodeId, _value, true, -1);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ID _gateId, ValueObject _value, int _index) {
		return __addGate(_gateId, _nodeId, _value, true, _index);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ID _gateId, Object _value) {
		return __addGate(_gateId, _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), true, -1);
	}
	@Override
	public GTGate 								addInput				(ID _nodeId, ID _gateId, Object _value, int _index) {
		return __addGate(_gateId, _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), true, _index);
	}

	@Override
	public GTGate			 						addOutput				(ID _nodeId) {
		return __addGate(__newID(gates), _nodeId, ValueObject.empty(), false, -1);
	}
	@Override
	public GTGate 								addOutput				(ID _nodeId, int _index) {
		return __addGate(__newID(gates), _nodeId, ValueObject.empty(), false, _index);
	}
	@Override
	public GTGate 								addOutput				(ID _nodeId, ValueObject _value) {
		return __addGate(__newID(gates), _nodeId, _value, false, -1);
	}
	@Override
	public GTGate									addOutput				(ID _nodeId, ValueObject _value, int _index) {
		return __addGate(__newID(gates), _nodeId, _value, false, _index);
	}
	@Override
	public GTGate 								addOutput				(ID _nodeId, Object _value) {
		return __addGate(__newID(gates), _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), false, -1);
	}
	@Override
	public GTGate									addOutput				(ID _nodeId, Object _value, int _index) {
		return __addGate(__newID(gates), _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), false, _index);
	}
	@Override
	public GTGate			 						addOutput				(ID _nodeId, ID _gateId) {
		return __addGate(_gateId, _nodeId, ValueObject.empty(), false, -1);
	}
	@Override
	public GTGate 								addOutput				(ID _nodeId, ID _gateId, int _index) {
		return __addGate(_gateId, _nodeId, ValueObject.empty(), false, _index);
	}
	@Override
	public GTGate 								addOutput				(ID _nodeId, ID _gateId, ValueObject _value) {
		return __addGate(_gateId, _nodeId, _value, false, -1);
	}
	@Override
	public GTGate									addOutput				(ID _nodeId, ID _gateId, ValueObject _value, int _index) {
		return __addGate(_gateId, _nodeId, _value, false, _index);
	}
	@Override
	public GTGate 								addOutput				(ID _nodeId, ID _gateId, Object _value) {
		return __addGate(_gateId, _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), false, -1);
	}
	@Override
	public GTGate									addOutput				(ID _nodeId, ID _gateId, Object _value, int _index) {
		return __addGate(_gateId, _nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), false, _index);
	}

	@Override
	public boolean 								hasInputs				(ID _nodeId) {
		__assertNode(_nodeId);
		return nodeInputs.get(_nodeId).stream().count() != 0;
	}
	@Override
	public BeanPropertyList<? extends GTGate> 	getInputs				(ID _nodeId) {
		__assertNode(_nodeId);

		if(nodeInputViews.get(_nodeId) == null)
			nodeInputViews.put(_nodeId, new BeanPropertyListView<GTGGateC>(nodeInputs.get(_nodeId), id -> gates.get(id)));
			
		return nodeInputViews.get(_nodeId);
	}
	@Override
	public GTGate									getInput				(ID _nodeId, int _inputIdx) {
		__assertNode(_nodeId);
		return gates.get(nodeInputs.get(_nodeId).get(_inputIdx));
	}

	@Override
	public boolean 								hasOutputs				(ID _nodeId) {
		__assertNode(_nodeId);
		return nodeOutputs.get(_nodeId).stream().count() != 0;
	}
	@Override
	public BeanPropertyList<? extends GTGate> 	getOutputs				(ID _nodeId) {
		__assertNode(_nodeId);

		if(nodeOutputViews.get(_nodeId) == null)
			nodeOutputViews.put(_nodeId, new BeanPropertyListView<GTGGateC>(nodeOutputs.get(_nodeId), id -> gates.get(id)));
			
		return nodeOutputViews.get(_nodeId);
	}
	@Override
	public GTGate								getOutput				(ID _nodeId, int _inputIdx) {
		__assertNode(_nodeId);
		return gates.get(nodeOutputs.get(_nodeId).get(_inputIdx));
	}

	@Override
	public void									removeGate				(ID _gateId) {
		__assertGate(_gateId);
		__removeGate(_gateId);
	}
	@Override
	public void									removeGate				(Object _gate) {
		if(_gate instanceof GTGate) {
			__removeGate(((GTGate) _gate).getId());
		} else {
			__removeGate(__getGateId(_gate));
		}
	}

	@Override
	public void 								removeInput				(ID _nodeId, int _gateIndex) {
		ID gateId = getInputs(_nodeId).get(_gateIndex).getId();
//		__assertInput(_nodeId, gateId);
		__removeGate(gateId);
	}
	@Override
	public void 								removeOutput			(ID _nodeId, int _gateIndex) {
		ID gateId = getOutputs(_nodeId).get(_gateIndex).getId();
//		__assertOutput(_nodeId, gateId);
		__removeGate(gateId);
	}

	@Override
	public boolean 								hasPredecessors			(ID _nodeId) {
		__assertNode(_nodeId);
		return !node_previouses.apply(_nodeId).isEmpty();
	}
	@Override
	public Collection<? extends GTNode.Gated> 			getPredecessors			(ID _nodeId) {
		__assertNode(_nodeId);
		return nodeInputs.get(_nodeId)
						   .stream()
					       .map(gateId -> gatePreviouses.get(gateId))
				               .flatMap(l -> l.stream())
						       .map(pair -> pair.getFirst())
		 					   .distinct()
						       .map(id -> nodes.get(id))
						       .collect(Collectors.toList());
	}
	@Override
	public boolean 								hasPredecessors			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getInput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gatePreviouses.get(gateId).stream().count() > 0;
	}
	@Override
	public Collection<? extends GTNode.Gated> 			getPredecessors			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getInput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gatePreviouses.get(gateId).stream()
									 	 .map(ids -> ids.getFirst())
				 						 .distinct()
									     .map(id -> nodes.get(id))
										 .collect(Collectors.toSet());
	}

	@Override
	public boolean 								hasSuccessors			(ID _nodeId) {
		__assertNode(_nodeId);
		return !node_nexts.apply(_nodeId).isEmpty();
	}
	@Override
	public Collection<? extends GTNode.Gated> 			getSuccessors			(ID _nodeId) {
		__assertNode(_nodeId);
		return nodeOutputs.get(_nodeId)
				   .stream()
			       .map(gateId -> gateNexts.get(gateId))
		               .flatMap(l -> l.stream())
				       .map(pair -> pair.getFirst())
					   .distinct()
				       .map(id -> nodes.get(id))
				       .collect(Collectors.toList());
	}
	@Override
	public boolean 								hasSuccessors			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getOutput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gateNexts.get(gateId).stream().count() > 0;
	}
	@Override
	public Collection<? extends GTNode.Gated> 			getSuccessors			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getOutput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gateNexts.get(gateId).stream()
									 	 .map(ids -> ids.getFirst())
				 						 .distinct()
									     .map(id -> nodes.get(id))
										 .collect(Collectors.toSet());
	}
	

	@Override
	public boolean 								hasIncomings			(ID _nodeId) {
		return hasPredecessors(_nodeId);
	}
	@Override
	public Collection<? extends GTEdge.Gated> 			getIncomings			(ID _nodeId) {
		__assertNode(_nodeId);
		return node_incomings.apply(_nodeId).stream().map(id -> getEdge(id)).collect(Collectors.toSet());
	}
	@Override
	public boolean 								hasIncomings			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getInput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gateIncomings.get(gateId).stream().count() > 0;
	}
	@Override
	public Collection<? extends GTEdge.Gated> 			getIncomings			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getInput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gateIncomings.get(gateId).stream()
											.distinct()
											.map(id -> getEdge(id))
											.collect(Collectors.toSet());
	}

	@Override
	public boolean 								hasOutcomings			(ID _nodeId) {
		return hasSuccessors(_nodeId);
	}
	@Override
	public Collection<? extends GTEdge.Gated> 			getOutcomings			(ID _nodeId) {
		__assertNode(_nodeId);
		return node_outcomings.apply(_nodeId).stream().map(id -> getEdge(id)).collect(Collectors.toSet());
	}
	@Override
	public boolean 								hasOutcomings			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getInput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gateOutcomings.get(gateId).stream().count() > 0;
	}
	@Override
	public Collection<? extends GTEdge.Gated> 			getOutcomings			(ID _nodeId, int _gateIndex) {
		__assertNode(_nodeId);
		ID gateId = getInput(_nodeId, _gateIndex).getId();
		__assertInput(_nodeId, gateId);

		return gateOutcomings.get(gateId).stream()
											.distinct()
											.map(id -> getEdge(id))
											.collect(Collectors.toSet());
	}

    // EDGE Related Methods
    // ====================
	@Override
	public GTEdge.Gated		 							addEdge					(ID _srcId, ID _srcGateId, ID _dstId, ID _dstGateId) throws IllegalOperationException {
		__assertOutput(_srcId, _srcGateId);
		__assertInput(_dstId, _dstGateId);

		return __addEdge(__newID(edges), ValueObject.empty(), _srcId, _srcGateId, _dstId, _dstGateId);
	}
	@Override
	public GTEdge.Gated		 							addEdge					(ID _srcId, ID _srcGateId, ID _dstId, ID _dstGateId, ValueObject _value) throws IllegalOperationException {
		__assertOutput(_srcId, _srcGateId);
		__assertInput(_dstId, _dstGateId);

		return __addEdge(__newID(edges), _value, _srcId, _srcGateId, _dstId, _dstGateId);
	}
	@Override
	public GTEdge.Gated		 							addEdge					(ID _srcId, ID _srcGateId, ID _dstId, ID _dstGateId, Object _value) throws IllegalOperationException {
		__assertOutput(_srcId, _srcGateId);
		__assertInput(_dstId, _dstGateId);

		return __addEdge(__newID(edges), (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), _srcId, _srcGateId, _dstId, _dstGateId);
	}
	@Override
	public GTEdge.Gated 								addEdge					(ID _src, int _srcGateIdx, ID _dst, int _dstGateIdx) throws IllegalOperationException {
		__assertNode(_src);
		__assertNode(_dst);

		ID srcGateId = getOutput (_src, _srcGateIdx).getId();
		ID dstGateId = getInput  (_dst, _dstGateIdx).getId();

		if(srcGateId == null || dstGateId == null)
			throw new NullPointerException();

		return __addEdge(__newID(edges), ValueObject.empty(), _src, srcGateId, _dst, dstGateId);
	}
	@Override
	public GTEdge.Gated									addEdge					(ID _src, int _srcGateIdx, ID _dst, int _dstGateIdx, ValueObject _value) throws IllegalOperationException {
		__assertNode(_src);
		__assertNode(_dst);

		ID srcGateId = getOutput (_src, _srcGateIdx).getId();
		ID dstGateId = getInput  (_dst, _dstGateIdx).getId();

		if(srcGateId == null || dstGateId == null)
			throw new NullPointerException();

		return __addEdge(__newID(edges), _value, _src, srcGateId, _dst, dstGateId);
	}
	@Override
	public GTEdge.Gated									addEdge					(ID _src, int _srcGateIdx, ID _dst, int _dstGateIdx, Object _value) throws IllegalOperationException {
		__assertNode(_src);
		__assertNode(_dst);

		ID srcGateId = getOutput (_src, _srcGateIdx).getId();
		ID dstGateId = getInput  (_dst, _dstGateIdx).getId();

		if(srcGateId == null || dstGateId == null)
			throw new NullPointerException();

		return __addEdge(__newID(edges), (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), _src, srcGateId, _dst, dstGateId);
	}

	@Override
	public ValueObject 							setEdgeValue			(ID _edgeId, ValueObject _newValue) {
		throw new NotYetImplementedException();
	}
	@Override
	public Object 								setEdgeValue			(ID _edgeId, Object _newValue) {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 								containsEdge			(ID _edgeId) {
		return edges.containsKey(_edgeId);
	}
	@Override
	public boolean 								containsEdge			(Object _edge) {
		return containsEdge(getEdge(_edge).getId());
	}
	@Override
	public boolean 								containsEdge			(ID _srcId, ID _dstId) {
		__assertNode(_srcId);
		__assertNode(_dstId);

		if(node_nexts.apply(_srcId).stream().anyMatch(p -> p.equals(_dstId)))
			return true;

		return false;
	}
	@Override
	public boolean 								containsEdge			(Object _srcNode, Object _dstNode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean 								containsEdge			(ID _srcNodeId, ID _srcGateId, ID _dstNodeId, ID _dstGateId) {
		__assertOutput	(_srcNodeId, _srcGateId);
		__assertInput	(_dstNodeId, _dstGateId);

		return gateNexts.get(_srcGateId).contains(Tuples.of(_dstNodeId, _dstGateId));
	}
	public boolean 								containsEdge			(ID _src, int _srcGateIdx, ID _dst, int _dstGateIdx) {
		return false;
	}

	@Override
	public GTGEdgeC 							getEdge					(ID _edgeId) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getKey().equals(_edgeId))
						 .findFirst()
						 .map(e -> edges.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTGEdgeC					 			getEdge					(Object _edgeValue) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_edgeValue))
						 .findFirst()
						 .map(e -> edges.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public Collection<GTEdge.Gated>						getEdges				(ID _srcId, ID _dstId) {
		return bounds_reverse.entrySet().stream()
										.filter(e -> {
												Pair<Pair<ID, ID>, Pair<ID, ID>> key = e.getKey();
												if(key.getFirst().getFirst().equals(_srcId) && key.getSecond().getFirst().equals(_dstId))
													return true;
												return false;
											})
										.map(e -> edges.get(e.getValue()))
										.collect(Collectors.toSet());
	}
	@Override
	public GTGEdgeC 							getEdge					(ID _srcId, int _srcGateIdx, ID _dstId, int _dstGateIdx) {
		__assertNode(_srcId);
		__assertNode(_dstId);

		ID srcGateId = getOutput (_srcId, _srcGateIdx).getId();
		ID dstGateId = getInput  (_dstId, _dstGateIdx).getId();

		if(srcGateId == null || dstGateId == null)
			throw new NullPointerException();

		Pair<Pair<ID, ID>, Pair<ID, ID>> apex = Tuples.of(Tuples.of(_srcId, srcGateId), Tuples.of(_dstId, dstGateId));
		
		return edges.get( bounds_reverse.get(apex) );
	}
	@Override
	public Collection<GTEdge.Gated> 					getEdges				(Object _srcNode, Object _dstNode) {
		return null;
	}
	@Override
	public GTGEdgeC 							getEdge					(Object _srcNodeValue, int _srcGateIdx, Object _dstNodeValue, int _dstGateIdx) {
		return null;
	}

	@Override
	public void			 						removeEdge				(ID _edgeId) {
		__assertEdge(_edgeId);
		__removeEdge(_edgeId);
	}
	@Override
	public void 								removeEdge				(Object _edgeValue) {
		ID edgeId = getEdge(_edgeValue).getId();
		__removeEdge(edgeId);
	}
	@Override
	public void 								removeEdge				(GTEdge.Gated _edge) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void 								removeEdges				(ID _srcId, ID _dstId) {
		bounds_reverse.entrySet().stream()
									.filter(e -> {
											Pair<Pair<ID, ID>, Pair<ID, ID>> key = e.getKey();
											if(key.getFirst().getFirst().equals(_srcId) && key.getSecond().getFirst().equals(_dstId))
												return true;
											return false;
										})
									.map(e -> e.getValue())
									.forEach(this::__removeEdge);
	}
	@Override
	public void 								removeEdge				(ID _srcId, int _srcGateIdx, ID _dstId, int _dstGateIdx) {
		__assertNode(_srcId);
		__assertNode(_dstId);

		ID srcGateId = getOutput (_srcId, _srcGateIdx).getId();
		ID dstGateId = getInput  (_dstId, _dstGateIdx).getId();

		if(srcGateId == null || dstGateId == null)
			throw new NullPointerException();

		Pair<Pair<ID, ID>, Pair<ID, ID>> apex = Tuples.of(Tuples.of(_srcId, srcGateId), Tuples.of(_dstId, dstGateId));
		
		__removeEdge(bounds_reverse.get(apex));
	}
	@Override
	public void 								removeEdges				(Object _src, Object _dst) {
		throw new NotYetImplementedException();
		
	}
	@Override
	public void 								removeEdge				(Object _src, int _srcGateIdx, Object _dst, int _dstGateIdx) {
		throw new NotYetImplementedException();
	}

    // ... ~ Graph Aspects
    public BeanPropertySet<? extends GTEdge.Gated>		getEdges				() {
    	return edges_reverse.keySet();
    }

    // ... ~ Edge Aspects
	@Override
	public Pair<ID, ID>							getApexIds				(ID _edgeId) {
		__assertEdge(_edgeId);
		Pair<Pair<ID,ID>,Pair<ID,ID>> apex_ext = bounds.get(_edgeId);

		return Tuples.of(apex_ext.getFirst().getFirst(), apex_ext.getSecond().getFirst());
	}
	@Override
	public Pair<ID, ID> 						getApexGateIds			(ID _edgeId) {
		Pair<Pair<ID,ID>,Pair<ID,ID>> apex_ext = bounds.get(_edgeId);

		return Tuples.of(apex_ext.getFirst().getSecond(), apex_ext.getSecond().getSecond());
	}

	@Override
	public Pair<? extends GTNode.Gated, ? extends GTNode.Gated>	getApexNodes			(ID _edgeId) {
		Pair<ID, ID> bIds = getApexIds(_edgeId);
		return Tuples.of(getNode(bIds.getFirst()), getNode(bIds.getSecond()));
	}
	@Override
	public Pair<? extends GTGate, ? extends GTGate> getApexGates			(ID _edgeId) {
		Pair<Pair<ID,ID>,Pair<ID,ID>> apex_ext = bounds.get(_edgeId);

		return Tuples.of(getGate(apex_ext.getFirst().getSecond()), getGate(apex_ext.getSecond().getSecond()));
	}

	// PACKAGE METHODS
	public ID 									getNodeId				(Object _nodeValue) {
		return nodeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_nodeValue))
						 .findFirst()
						 .map(e -> e.getKey())
						 .orElseGet(() -> null);
	}
	public ValueObject 							getNodeValueObject		(ID _nodeId) {
		return nodeValues.get(_nodeId);
	}
	public ValueType<?> 						getNodeValueType		(ID _nodeId) {
		return nodeValues.get(_nodeId).getType();
	}
	public Object 								getNodeValue			(ID _nodeId) {
		return nodeValues.get(_nodeId).getValue();
	}
	public int 									getNodeDegree			(ID _nodeId) {
		return getNodeDegreeIn(_nodeId) + getNodeDegreeOut(_nodeId);
	}
	public int 									getNodeDegreeIn			(ID _nodeId) {
		__assertNode(_nodeId);
		return node_previouses.apply(_nodeId).size();
	}
	public int 									getNodeDegreeIn			(ID _nodeId, int _inputIdx) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int 									getNodeDegreeOut		(ID _nodeId) {
		__assertNode(_nodeId);
		return node_nexts.apply(_nodeId).size();
	}
	public int 									getNodeDegreeOut		(ID _nodeId, int _outputIdx) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ID 									getEdgeId				(Object _edgeValue) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_edgeValue))
						 .findFirst()
						 .map(e -> e.getKey())
						 .orElseGet(() -> null);
	}
	public ValueObject 							getEdgeValueObject		(ID _edgeId) {
		return edgeValues.get(_edgeId);
	}
	public ValueType<?> 						getEdgeValueType		(ID _edgeId) {
		return edgeValues.get(_edgeId).getType();
	}
	public Object 								getEdgeValue			(ID _edgeId) {
		return edgeValues.get(_edgeId).getValue();
	}

	public ID 									getGateId				(Object _gateValue) {
		return gateValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_gateValue))
						 .findFirst()
						 .map(e -> e.getKey())
						 .orElseGet(() -> null);
	}
	public ValueObject 							getGateValueObject		(ID _gateId) {
		return gateValues.get(_gateId);
	}

	// INTERNAL METHODS
	private ID									__newID					(Map<ID, ?> _register) {
		ID newId = IDs.random(GT.DEFAULT_ID_SIZE);

		while(_register.containsKey(newId))
			newId = IDs.random(GT.DEFAULT_ID_SIZE);
		
		return newId;
	}

	private void 								__assertNode			(ID _id) {
		if(!nodes.containsKey(_id))
			throw new IllegalOperationException();
//			throw new NullPointerException();
	}
	private void 								__assertGate			(ID _id) {
		if(!gates.containsKey(_id))
			throw new NullPointerException();
	}
	@SuppressWarnings("unused")
	private void 								__assertInput			(ID _gateId) {
		if(!inputs.get().contains(_gateId))
			throw new NullPointerException();
	}
	@SuppressWarnings("unused")
	private void 								__assertOutput			(ID _gateId) {
		if(!outputs.get().contains(_gateId))
			throw new NullPointerException();
	}
	private void 								__assertEdge			(ID _id) {
		if(!edges.containsKey(_id))
			throw new NullPointerException();
	}

	private void 								__assertMembership		(ID _nodeId, ID _gateId) {
		__assertNode(_nodeId);
		__assertGate(_gateId);

		if(!gateOwners.get(_gateId).equals(_nodeId))
			throw new InvalidMembershipException();
	}
	private void 								__assertInput			(ID _nodeId, ID _gateId) {
		__assertMembership(_nodeId, _gateId);

		if(!nodeInputs.get(_nodeId).contains(_gateId))
			throw new InvalidRelationshipException();
	}
	private void 								__assertOutput			(ID _nodeId, ID _gateId) {
		__assertMembership(_nodeId, _gateId);
		
		if(!nodeOutputs.get(_nodeId).contains(_gateId))
			throw new InvalidRelationshipException();
	}

	private boolean 							__isInput				(ID _gateId) {
		__assertGate(_gateId);

		if(inputs.get().contains(_gateId))
			return true;
		return false;
	}
	private boolean 							__isOutput				(ID _gateId) {
		__assertGate(_gateId);

		if(outputs.get().contains(_gateId))
			return true;
		return false;
	}

	private GTNode.Gated								__addNode				(ID _nodeId, ValueObject _nodeValue) {
		if(nodes.containsKey(_nodeId))
			throw new AlreadyExistingNodeException();
		if(_nodeValue == null)
			throw new NullPointerException();

		GTGNodeC newNode = new GTGNodeC(this, _nodeId);

		nodes			. put(_nodeId, newNode);
		nodes_reverse	. put(newNode, _nodeId);

		nodeValues  	. put(_nodeId, _nodeValue);
		nodeInputs  	. put(_nodeId, new SimpleBeanPropertyList<ID>());
		nodeOutputs 	. put(_nodeId, new SimpleBeanPropertyList<ID>());

		return newNode;
	}
	private void 								__removeNode			(ID _nodeId) {
		if(_nodeId == null || !nodes.containsKey(_nodeId))
			throw new NullPointerException();

		// Remove all edges containing the node
		bounds_reverse.entrySet().stream()
									 .filter(e -> {
										 if(e.getKey().getFirst().getFirst().equals(_nodeId) || e.getKey().getSecond().getFirst().equals(_nodeId))
											 return true;
										 return false;
									 })
									 .map(e -> e.getValue())
									 .distinct()
									 .forEach(this::__removeEdge);

		// Remove all gates belonging to the node
		Stream.concat(nodeInputs.get(_nodeId).stream(), nodeOutputs.get(_nodeId).stream())
									.forEach(this::__removeGate);

		GTGNodeC node = nodes.get(_nodeId);

		// Remove the node
		nodes           . remove(_nodeId);
		nodes_reverse	. remove(node);

		nodeValues      . remove(_nodeId);
		nodeInputs      . remove(_nodeId);
		nodeOutputs     . remove(_nodeId);
	}

	private GTGate		 						__addGate				(ID _gateId, ID _nodeId, ValueObject _gateValue, boolean _isInput, int _index) {
		__assertNode(_nodeId);

		GTGGateC newGate = new GTGGateC(this, _gateId);

		gates		   . put(_gateId, newGate);
		gates_reverse  . put(newGate, _gateId);

		gateValues     . put(_gateId, _gateValue);
		gateOwners     . put(_gateId, _nodeId);
		gatePreviouses . put(_gateId, new SimpleBeanPropertySet<Pair<ID, ID>>());
		gateNexts      . put(_gateId, new SimpleBeanPropertySet<Pair<ID, ID>>());
		gateIncomings  . put(_gateId, new SimpleBeanPropertySet<ID>());
		gateOutcomings . put(_gateId, new SimpleBeanPropertySet<ID>());

		if(_isInput)
			nodeInputs.get(_nodeId).add(_gateId);
		else
			nodeOutputs.get(_nodeId).add(_gateId);

		return newGate;
	}
	private ID	 								__removeGate			(ID _gateId) {
		__assertGate(_gateId);

		// Remove all edges containing the gate
		bounds_reverse.entrySet().stream()
								 .filter(e -> {
									 if(e.getKey().getFirst().getSecond().equals(_gateId) || e.getKey().getSecond().getSecond().equals(_gateId))
										 return true;
									 return false;
								 })
								 .map(e -> e.getValue())
								 .distinct()
								 .forEach(id -> __removeEdge(id));

		// Remove all references to this gate in the node
		ID owner = gateOwners.get(_gateId);
		if(__isInput(_gateId))
			nodeInputs.get(owner).remove(_gateId);
		else if(__isOutput(_gateId))
			nodeOutputs.get(owner).remove(_gateId);

		GTGGateC gate = gates.get(_gateId);

		// Remove the gate
		gates          . remove(_gateId);
		gates_reverse  . remove(gate);

		gateValues     . remove(_gateId);
		gateOwners     . remove(_gateId);
		gatePreviouses . remove(_gateId);
		gateNexts      . remove(_gateId);
		gateIncomings  . remove(_gateId);
		gateOutcomings . remove(_gateId);
		
		return _gateId;
	}

	private GTEdge.Gated		 						__addEdge				(ID _edgeId, ValueObject _edgeValue, ID _srcNodeId, ID _srcGateId, ID _dstNodeId, ID _dstGateId) {
		if(containsEdge(_srcNodeId, _srcGateId, _dstNodeId, _dstGateId))
			throw new IllegalOperationException("Edge already exists");

		GTGEdgeC newEdge     = new GTGEdgeC(this, _edgeId);

		// Set references for Nodes/Gates
		Set<Pair<ID, ID>> srcGateNext      = gateNexts.get(_srcGateId);
		Set<Pair<ID, ID>> dstGatePrevious  = gatePreviouses.get(_dstGateId);
		// Set references for Edges
		Set<ID>			  srcGateIncoming  = gateIncomings.get(_srcGateId);
		Set<ID>			  dstGateOutcoming = gateOutcomings.get(_dstGateId);
		// Set edge information
		Pair<ID, ID>      srcIds           = Tuples.of(_srcNodeId, _srcGateId);
		Pair<ID, ID>      dstIds           = Tuples.of(_dstNodeId, _dstGateId);

		edges            . put(_edgeId, newEdge);
		edges_reverse    . put(newEdge, _edgeId);

		edgeValues       . put(_edgeId, _edgeValue);
		bounds           . put(_edgeId, Tuples.of(srcIds, dstIds));
		bounds_reverse   . put(Tuples.of(srcIds, dstIds), _edgeId);
		srcGateNext      . add(dstIds);
		dstGatePrevious  . add(srcIds);
		srcGateIncoming  . add(_edgeId);
		dstGateOutcoming . add(_edgeId);

		return newEdge;
	}
	private void 								__removeEdge			(ID _edgeId) {
		Pair<ID, ID>      apexNodeIds = getApexIds(_edgeId);
		Pair<ID, ID>      apexGateIds = getApexGateIds(_edgeId);

		Pair<ID, ID>      srcIds      = Tuples.of(apexNodeIds.getFirst(),  apexGateIds.getFirst());
		Pair<ID, ID>      dstIds      = Tuples.of(apexNodeIds.getSecond(), apexGateIds.getSecond());

		// TODO:: WARNING, MUST CHECK UNICITY OF RELATIONSHIP BETWEEN THObject 2 NODES BEFORObject RELEASING !
		// Remove references for Nodes/Gates
		Set<Pair<ID, ID>> srcGateNext     = gateNexts.get(srcIds.getSecond());
		Set<Pair<ID, ID>> dstGatePrevious = gatePreviouses.get(dstIds.getSecond());

		// Remove references for Edges
		Set<ID>			  srcGateIncoming  = gateIncomings.get(srcIds.getSecond());
		Set<ID>			  dstGateOutcoming = gateOutcomings.get(dstIds.getSecond());

		srcGateNext      . remove(dstIds);
		dstGatePrevious  . remove(srcIds);
		srcGateIncoming  . remove(_edgeId);
		dstGateOutcoming . remove(_edgeId);

		GTGEdgeC edge = edges.get(_edgeId);

		edges            . remove(_edgeId);
		edges_reverse    . remove(edge);

		edgeValues       . remove(_edgeId);
		bounds           . remove(_edgeId);
		bounds_reverse   . remove(Tuples.of(srcIds,  dstIds));
	}

	private ID 									__getNodeId				(Object _nodeValue) {
		ID     nodeId = nodeValues.entrySet()
								  .stream()
								  .filter(e -> _nodeValue.equals(e.getValue()))
								  .findFirst()
								  .map(Map.Entry::getKey)
								  .get();

		return nodeId;
	}
	private ID 									__getGateId				(Object _gateValue) {
		ID     gateId = gateValues.entrySet()
								  .stream()
								  .filter(e -> _gateValue.equals(e.getValue()))
								  .findFirst()
								  .map(Map.Entry::getKey)
								  .get();

		return gateId;
	}
	private ID 									__getEdgeId				(Object _edgeValue) {
		ID     edgeId = edgeValues.entrySet()
								  .stream()
								  .filter(e -> _edgeValue.equals(e.getValue()))
								  .findFirst()
								  .map(Map.Entry::getKey)
								  .get();

		return edgeId;
	}
	private ID 									__getEdgeId				(ID _srcId, ID _dstId) {
		if(!containsNode(_srcId) || !containsNode(_dstId))
			throw new NullPointerException();

		Pair<ID, ID> ids = Tuples.of(_srcId, _dstId);

		ID edgeId = bounds.entrySet().stream().filter(e -> ids.equals(e.getValue())).map(Map.Entry::getKey).findFirst().get();

		return edgeId;
	}

}
