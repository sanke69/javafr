package fr.java.graph.impl.models.centralized;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.beans.impl.BeanPropertyMap;
import fr.java.beans.impl.BeanPropertySet;
import fr.java.beans.impl.SimpleBeanPropertyMap;
import fr.java.beans.impl.SimpleBeanPropertySet;
import fr.java.graph.GT;
import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.traversal.GTGraphTraversal;
import fr.java.graph.impl.exceptions.AlreadyExistingEdgeException;
import fr.java.graph.impl.models.centralized.wrappers.GTDEdgeC;
import fr.java.graph.impl.models.centralized.wrappers.GTDNodeC;
import fr.java.lang.exceptions.AlreadyExistingNodeException;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;
import fr.utils.graphs.IDs;
import fr.utils.graphs.Tuples;

public class GTGraphDirectedC implements GTGraph.Directed {
	final ID									id;

	final BeanPropertyMap<ID, GTDNodeC>			nodes;
	final BeanPropertyMap<GTDNodeC, ID>			nodes_reverse;

	final BeanPropertyMap<ID, ValueObject>		nodeValues;
	final Map<ID, BeanPropertySet<ID>>			nodePreviouses, nodeNexts;

	final BeanPropertyMap<ID, GTDEdgeC>			edges;
	final BeanPropertyMap<GTDEdgeC, ID>			edges_reverse;

	final BeanPropertyMap<ID, ValueObject>		edgeValues;
	final Map<ID, Pair<ID, ID>>					edgeBounds;

	public GTGraphDirectedC() {
		super();
		id             = IDs.random();

		nodes          = new SimpleBeanPropertyMap<ID, GTDNodeC>();
		nodes_reverse  = new SimpleBeanPropertyMap<GTDNodeC, ID>();

		nodeValues     = new SimpleBeanPropertyMap<ID, ValueObject>();
		nodeNexts      = new HashMap<ID, BeanPropertySet<ID>>();
		nodePreviouses = new HashMap<ID, BeanPropertySet<ID>>();

		edges          = new SimpleBeanPropertyMap<ID, GTDEdgeC>();
		edges_reverse  = new SimpleBeanPropertyMap<GTDEdgeC, ID>();

		edgeValues     = new SimpleBeanPropertyMap<ID, ValueObject>();
		edgeBounds     = new HashMap<ID, Pair<ID, ID>>();
	}

	@Override
	public ID 									getId() {
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
	public GTNode.Directed 								addNode					() {
		return __addNode(__newID(nodes), ValueObject.empty());
	}
	@Override
	public GTNode.Directed 								addNode					(ValueObject _value) throws IllegalOperationException, AlreadyExistingNodeException {
		return __addNode(__newID(nodes), _value);
	}
	@Override
	public GTNode.Directed 								addNode					(Object _value) {
		return __addNode(__newID(nodes), (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value));
	}
	@Override
	public GTNode.Directed									addNode					(ID _nodeId) throws AlreadyExistingNodeException {
		return __addNode(_nodeId, ValueObject.empty());
	}
	@Override
	public GTNode.Directed 								addNode					(ID _nodeId, ValueObject _value) throws IllegalOperationException, AlreadyExistingNodeException {
		return __addNode(_nodeId, _value);
	}
	@Override
	public GTNode.Directed 								addNode					(ID _nodeId, Object _value) throws AlreadyExistingNodeException {
		return __addNode(_nodeId, (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value));
	}

	@Override
	public boolean 								containsNode			(ID _nodeId) {
		return nodes.containsKey(_nodeId);
	}
	@Override
	public boolean 								containsNode			(Object _nodeValue) {
		return nodeValues.containsValue(_nodeValue);
	}

	@Override
	public GTDNodeC 							getNode					(ID _nodeId) {
		return nodeValues.entrySet()
						 .stream()
						 .filter(e -> e.getKey().equals(_nodeId))
						 .findFirst()
						 .map(e -> nodes.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTDNodeC					 			getNode					(Object _nodeValue) {
		return nodeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_nodeValue))
						 .findFirst()
						 .map(e -> nodes.get(e.getKey()))
						 .orElseGet(() -> null);
	}

	@Override
	public void									removeNode				(ID _id) {
		__removeNode(_id);
	}
	@Override
	public void 								removeNode				(Object _node) {
		if(_node instanceof GTNode.Directed)
			removeNode(((GTNode.Directed) _node).getId());
		else
			removeNode(getNode(_node).getId());
	}

    // ... ~ Graph Aspects
	@Override
    public BeanPropertySet<? extends GTNode.Directed>		getNodes				() {
		return nodes_reverse.keySet();
	}

    // ... ~ Node Aspects
	@Override
	public ValueObject 							setNodeValue			(ID _nodeId, ValueObject _newValue) throws AlreadyExistingNodeException {
		throw new NotYetImplementedException();
	}
	@Override
	public Object 								setNodeValue			(ID _nodeId, Object _value) throws AlreadyExistingNodeException {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 								hasPredecessors			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new IllegalOperationException();
		return !nodePreviouses.get(_nodeId).isEmpty();
	}
	@Override
	public Collection<? extends GTNode.Directed>			getPredecessors			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new IllegalOperationException();
		return nodePreviouses.get(_nodeId).stream().map(id -> getNode(id)).collect(Collectors.toSet());
	}

	@Override
	public boolean 								hasSuccessors			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new IllegalOperationException();
		return !nodeNexts.get(_nodeId).isEmpty();
	}
	@Override
	public Collection<? extends GTNode.Directed> 			getSuccessors			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new IllegalOperationException();
		return nodeNexts.get(_nodeId).stream().map(id -> getNode(id)).collect(Collectors.toSet());
	}

	@Override
	public boolean 								hasIncomings			(ID _nodeId) {
		return hasPredecessors(_nodeId);
	}
	@Override
	public Collection<? extends GTEdge.Directed> 			getIncomings			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new IllegalOperationException();

		return edgeBounds.entrySet().stream()
								.filter(e -> e.getValue().getSecond().equals(_nodeId))
								.map(e -> edges.get(e.getKey()))
								.collect(Collectors.toSet());
	}

	@Override
	public boolean 								hasOutcomings			(ID _nodeId) {
		return hasSuccessors(_nodeId);
	}
	@Override
	public Collection<? extends GTEdge.Directed> 			getOutcomings			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new IllegalOperationException();

		return edgeBounds.entrySet().stream()
								.filter(e -> e.getValue().getFirst().equals(_nodeId))
								.map(e -> edges.get(e.getKey()))
								.collect(Collectors.toSet());
	}

    // EDGE Related Methods
    // ====================
	@Override
	public GTEdge.Directed									addEdge					(ID _srcId, ID _dstId) {
		return __addEdge(__newID(edges), ValueObject.empty(), _srcId, _dstId);
	}
	@Override
	public GTEdge.Directed									addEdge					(ID _srcId, ID _dstId, ValueObject _value) {
		return __addEdge(__newID(edges), _value, _srcId, _dstId);
	}
	@Override
	public GTEdge.Directed									addEdge					(ID _srcId, ID _dstId, Object _value) {
		return __addEdge(__newID(edges), (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), _srcId, _dstId);
	}

	@Override
	public boolean 								containsEdge			(ID _edgeId) {
		return edges.containsKey(_edgeId);
	}
	@Override
	public boolean 								containsEdge			(Object _edge) {
		return edges.containsKey(_edge);
	}
	@Override
	public boolean 								containsEdge			(ID _srcId, ID _dstId) {
		if(containsNode(_srcId) && containsNode(_dstId))
			return nodeNexts.get(_srcId).contains(_dstId);

		return false;
	}
	@Override
	public boolean 								containsEdge			(Object _src, Object _dst) {
		GTNode.Directed src, dst;
		if(_src instanceof GTNode.Directed && _src instanceof GTNode.Directed) {
			src = (GTNode.Directed) _src;
			dst = (GTNode.Directed) _dst;
		} else {
			src = getNode(_src);
			dst = getNode(_dst);
		}

		if(src == null || dst == null)
			return false;

		if(nodes.containsKey(src.getId()) && nodes.containsKey(dst.getId()))
			return nodeNexts.get(src.getId()).contains(dst.getId());

		return false;
	}

	@Override
	public GTDEdgeC 							getEdge					(ID _nodeId) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getKey().equals(_nodeId))
						 .findFirst()
						 .map(e -> edges.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTDEdgeC					 			getEdge					(Object _nodeValue) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_nodeValue))
						 .findFirst()
						 .map(e -> edges.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTDEdgeC 							getEdge					(ID _srcNodeId, ID _dstNodeId) {
		ID edgeId    = __getEdgeId(_srcNodeId, _dstNodeId);

		if(edgeId == null)
			throw new NullPointerException();

		return edges.get(edgeId);
	}
	@Override
	public GTDEdgeC 							getEdge					(Object _srcNode, Object _dstNode) {
		ID srcNodeId = __getNodeId(_srcNode);
		ID dstNodeId = __getNodeId(_dstNode);

		if(srcNodeId == null || dstNodeId == null)
			throw new NullPointerException();

		ID edgeId    = __getEdgeId(srcNodeId, dstNodeId);

		if(edgeId == null)
			throw new NullPointerException();

		return edges.get(edgeId);
	}

	@Override
	public void			 						removeEdge				(ID _edgeId) {
		if(!containsEdge(_edgeId))
			throw new NullPointerException();

		__removeEdge(_edgeId);
	}
	@Override
	public void 								removeEdge				(Object _edge) {
		ID edgeId = _edge instanceof GTEdge.Directed ? ((GTEdge.Directed) _edge).getId() : getEdge(_edge).getId();
		removeEdge(edgeId);
	}
//	@Override
//	public void 								removeEdge				(Edge _edge) {
//		removeEdge(_edge.getId());
//	}
	@Override
	public void			 						removeEdge				(ID _srcId, ID _dstId) {
		if(!containsNode(_srcId) || !containsNode(_dstId))
			throw new NullPointerException();

		Pair<ID, ID> ids = Tuples.of(_srcId, _dstId);

		ID edgeId = edgeBounds.entrySet().stream().filter(e -> ids.equals(e.getValue())).map(Map.Entry::getKey).findFirst().get();
		if(edgeId == null)
			throw new NullPointerException();

		__removeEdge(edgeId);
	}
	@Override
	public void 								removeEdge				(Object _src, Object _dst) {
		if(_src instanceof GTNode.Directed && _dst instanceof GTNode.Directed)
			removeEdge(((GTNode.Directed) _src).getId(), ((GTNode.Directed) _dst).getId());
		else
			removeEdge(getNode(_src).getId(), getNode(_dst).getId());
	}

    // ... ~ Graph Aspects
	@Override
	public BeanPropertySet<? extends GTEdge.Directed> 		getEdges				() {
		return edges_reverse.keySet();
	}

    // ... ~ Edge Aspects
	@Override
	public ValueObject 							setEdgeValue			(ID _edgeId, ValueObject _newValue) {
		throw new NotYetImplementedException();
	}
	@Override
	public Object 								setEdgeValue			(ID _id, Object _value) {
		throw new NotYetImplementedException();
	}


	@Override
	public Pair<ID, ID> 						getApexIds				(ID _edgeId) {
		return edgeBounds.get(_edgeId);
	}
	@Override
	public Pair<? extends GTNode.Directed, ? extends GTNode.Directed> getApexNodes			(ID _edgeId) {
		Pair<ID, ID> ids = edgeBounds.get(_edgeId);
		return Tuples.of(getNode(ids.getFirst()), getNode(ids.getSecond()));
	}

	// PACKAGE METHODS
	public GTNode.Directed 				getNodeObject			(ID _nodeId) {
		return nodes.get(_nodeId);
	}
	public ValueObject 							getNodeValueObject		(ID _nodeId) {
		return nodeValues.get(_nodeId);
	}
	public Object 								getNodeValue			(ID _nodeId) {
		return nodeValues.get(_nodeId).getValue();
	}
	public int 									getNodeDegree			(ID _nodeId) {
		return 0;
	}
	public int									getNodeDegreeIn			(ID _nodeId) {
		return 0;
	}
	public int									getNodeDegreeOut		(ID _nodeId) {
		return 0;
	}

	public GTEdge.Directed 				getEdgeObject			(ID _nodeId) {
		return edges.get(_nodeId);
	}
	public ValueObject 							getEdgeValueObject		(ID _nodeId) {
		return edgeValues.get(_nodeId);
	}
	public Object 								getEdgeValue			(ID _nodeId) {
		return edgeValues.get(_nodeId).getValue();
	}

	// INTERNAL METHODS
	private ID									__newID					(Map<ID, ?> _register) {
		ID newId = IDs.random(GT.DEFAULT_ID_SIZE);

		while(_register.containsKey(newId))
			newId = IDs.random(GT.DEFAULT_ID_SIZE);
		
		return newId;
	}

	private GTNode.Directed								__addNode				(ID _nodeId, ValueObject _nodeValue) {
		if(nodes.containsKey(_nodeId))
			throw new AlreadyExistingNodeException();

		if(_nodeValue.isPresent() && nodeValues.containsValue(_nodeValue))
			throw new AlreadyExistingNodeException(_nodeValue);

		GTDNodeC newNode = new GTDNodeC(this, _nodeId);

		nodes		   . put(_nodeId, newNode);
		nodes_reverse  . put(newNode, _nodeId);

		nodeValues     . put(_nodeId, _nodeValue);
		nodeNexts      . put(_nodeId, new SimpleBeanPropertySet<ID>());
		nodePreviouses . put(_nodeId, new SimpleBeanPropertySet<ID>());

		return newNode;
	}
	private void 								__removeNode			(ID _id) {
		if(_id == null || !nodes.containsKey(_id))
			throw new NullPointerException();

		// Remove all edges containing the node
		edgeBounds.entrySet().stream()
						 .filter(e -> e.getValue().contains(_id))
						 .map(e -> e.getKey())
						 .collect(Collectors.toList())
							 .stream()
							 .forEach(id -> __removeEdge(id));

		GTDNodeC node = nodes.get(_id);

		// Remove the node
		nodes          . remove(_id);
		nodes_reverse  . remove(node);

		nodeValues     . remove(_id);
		nodeNexts      . remove(_id);
		nodePreviouses . remove(_id);
	}

	private GTEdge.Directed								__addEdge				(ID _edgeId, ValueObject _edgeValue, ID _srcNodeId, ID _dstNodeId) {
		if(!nodes.containsKey(_srcNodeId) || !nodes.containsKey(_dstNodeId)) 
			throw new NullPointerException();
		if(nodeNexts.get(_srcNodeId).contains(_dstNodeId)) 
			throw new AlreadyExistingEdgeException(nodes.get(_srcNodeId), nodes.get(_dstNodeId));
//			throw new AlreadyExistingEdgeException(_srcNodeId, _dstNodeId);
		if(_edgeValue == null)
			throw new NullPointerException();

		Set<ID>  srcNext     = nodeNexts.get(_srcNodeId);
		Set<ID>  dstPrevious = nodePreviouses.get(_dstNodeId);
		GTDEdgeC newEdge     = new GTDEdgeC(this, _edgeId);

		edges		   . put(_edgeId, newEdge);
		edges_reverse  . put(newEdge, _edgeId);

		edgeValues     . put(_edgeId, _edgeValue);
		edgeBounds     . put(_edgeId, Tuples.of(_srcNodeId, _dstNodeId));

		srcNext     . add(_dstNodeId);
		dstPrevious . add(_srcNodeId);

		return newEdge;
	}
	private void 								__removeEdge			(ID _id) {
		Pair<ID, ID> apexIds     = getApexIds(_id);
		Set<ID>      srcNext     = nodeNexts.get(apexIds.getFirst());
		Set<ID>      dstPrevious = nodePreviouses.get(apexIds.getFirst());

		GTDEdgeC edge = edges.get(_id);

		srcNext        . remove(apexIds.getSecond());
		dstPrevious    . remove(apexIds.getFirst());

		edges		   . remove(_id);
		edges_reverse  . remove(edge);

		edgeValues 	   . remove(_id);
		edgeBounds     . remove(_id);
	}

	private ID 									__getNodeId				(Object _nodeValue) {
		Map.Entry<ID, ValueObject> entry = nodeValues.entrySet()
													  .stream()
													  .filter(e -> _nodeValue.equals(e.getValue()))
													  .findFirst()
													  .orElse(null);
		
		if(entry != null)
			return entry.getKey();

		return null;
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

		ID edgeId = edgeBounds.entrySet().stream().filter(e -> ids.equals(e.getValue())).map(Map.Entry::getKey).findFirst().get();

		return edgeId;
	}

}
