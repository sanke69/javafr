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
import fr.java.graph.impl.models.centralized.wrappers.GTUEdgeC;
import fr.java.graph.impl.models.centralized.wrappers.GTUNodeC;
import fr.java.lang.exceptions.AlreadyExistingNodeException;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.valueable.ValueObject;
import fr.java.patterns.valueable.ValueType;
import fr.java.utils.graphs.IDs;
import fr.java.utils.graphs.Tuples;

public class GTGraphUndirectedC implements GTGraph.Undirected {
	final ID									id;

	final BeanPropertyMap<ID, GTUNodeC>			nodes;
	final BeanPropertyMap<GTUNodeC, ID>			nodes_reverse;		// TODO:: 

	final BeanPropertyMap<ID, ValueObject>		nodeValues;
	final Map<ID, BeanPropertySet<ID>>			nodeNeighbors;
//	final Map<ID, Set<ID>>						nodeConnections;

	final BeanPropertyMap<ID, GTUEdgeC>			edges;
	final BeanPropertyMap<GTUEdgeC, ID>			edges_reverse;		// TODO:: 

	final BeanPropertyMap<ID, ValueObject>		edgeValues;

	final Map<ID, Pair<ID, ID>>					bounds;
	final Map<Pair<ID, ID>, ID>					bounds_reverse;

	public GTGraphUndirectedC() {
		super();

		id             = IDs.random();

		nodes          = new SimpleBeanPropertyMap<ID, GTUNodeC>();
		nodes_reverse  = new SimpleBeanPropertyMap<GTUNodeC, ID>();

		nodeValues     = new SimpleBeanPropertyMap<ID, ValueObject>();
		nodeNeighbors  = new HashMap<ID, BeanPropertySet<ID>>();

		edges          = new SimpleBeanPropertyMap<ID, GTUEdgeC>();
		edges_reverse  = new SimpleBeanPropertyMap<GTUEdgeC, ID>();

		edgeValues     = new SimpleBeanPropertyMap<ID, ValueObject>();
		bounds         = new HashMap<ID, Pair<ID, ID>>();
		bounds_reverse = new HashMap<Pair<ID, ID>, ID>();
	}

	@Override
	public ID 									getId				() {
		return id;
	}

    // GRAPH Visitor
    // =============
	@Override
	public void 								traverse			(ID _startId, GTGraphTraversal _traversal) {
		_traversal.visit(this, _startId);
	}

    // NODE Related Methods
    // ====================
	@Override
	public GTNode.Undirected 								addNode				() {
		return __addNode(__newID(nodes), ValueObject.empty());
	}
	@Override
	public GTNode.Undirected 								addNode				(Object _value) {
		return __addNode(__newID(nodes), _value instanceof ValueObject ? (ValueObject) _value : ValueObject.of(_value));
	}
	@Override
	public GTNode.Undirected 								addNode				(ValueObject _value) throws IllegalOperationException, AlreadyExistingNodeException {
		return __addNode(__newID(nodes), _value);
	}
	@Override
	public GTNode.Undirected									addNode				(ID _id) throws AlreadyExistingNodeException {
		return __addNode(_id, ValueObject.empty());
	}
	@Override
	public GTNode.Undirected									addNode				(ID _nodeId, Object _value) throws AlreadyExistingNodeException {
		return __addNode(_nodeId, _value instanceof ValueObject ? (ValueObject) _value : ValueObject.of(_value));
	}
	@Override
	public GTNode.Undirected 								addNode				(ID _nodeId, ValueObject _value) throws IllegalOperationException, AlreadyExistingNodeException {
		return __addNode(_nodeId, _value);
	}

	@Override
	public boolean 								containsNode		(ID _id) {
		return nodes.containsKey(_id);
	}

	@Override
	public GTUNodeC 							getNode				(ID _nodeId) {
		GTUNodeC node = nodes.get(_nodeId);
		
		
		return node;
//		return nodes.get(_nodeId);
	}
	@Override
	public GTUNodeC					 			getNode				(Object _nodeValue) {
		return nodeValues.entrySet()
						 .stream()
						 .filter(e -> _nodeValue.equals(e.getValue().getValue()))
//						 .filter(e -> e.getValue().getValue().equals(_nodeValue))
						 .findFirst()
						 .map(e -> nodes.get(e.getKey()))
						 .orElseGet(() -> null);
	}

	@Override
	public void									removeNode			(ID _nodeId) {
		if(!nodes.containsKey(_nodeId))
			throw new NullPointerException();

		__removeNode(_nodeId);
	}
	@Override
	public void									removeNode			(Object _node) {
		if(_node instanceof GTNode.Undirected)
			removeNode(((GTNode.Undirected) _node).getId());
		else
			removeNode(getNode(_node).getId());
    }
	/*
	public void									removeNode			(Node _node) {
    	removeNode(_node.getId());
    }
    */

    // ... ~ Graph Aspects
	@Override
	public BeanPropertySet<? extends GTNode.Undirected> 		getNodes			() {
		return nodes_reverse.keySet();
	}

    // ... ~ Node Aspects
	@Override
	public ValueObject 							setNodeValue		(ID _nodeId, ValueObject _newValue) throws AlreadyExistingNodeException {
		return null;
	}
	@Override
	public Object								setNodeValue		(ID _nodeId, Object _newValue) throws AlreadyExistingNodeException {
		return null;
	}

	@Override
	public boolean 								hasNeighbors		(ID _nodeId) {
		return nodeNeighbors.get(_nodeId).stream()/*.distinct()*/.count() != 0;
	}
	@Override
	public Collection<? extends GTNode.Undirected> 			getNeighbors		(ID _nodeId) {
		return nodeNeighbors.get(_nodeId).stream().distinct().map(id -> getNode(id)).collect(Collectors.toList());
	}

	@Override
	public boolean 								hasConnections		(ID _nodeId) {
		return hasNeighbors(_nodeId);
	}
	@Override
	public Collection<? extends GTEdge.Undirected> 			getConnections		(ID _nodeId) {
		return  bounds.entrySet().stream()
									 .filter(e -> e.getValue().contains(_nodeId))
								     .map(e -> edges.get(e.getKey()))
								     .collect(Collectors.toList());
	}

    // EDGE Related Methods
    // ====================
	@Override
	public GTEdge.Undirected 								addEdge				(ID _srcId, ID _dstId) {
		return __addEdge(__newID(edges), ValueObject.empty(), _srcId, _dstId);
	}
	@Override
	public GTEdge.Undirected				 					addEdge				(ID _srcId, ID _dstId, ValueObject _value) throws IllegalOperationException {
		return __addEdge(__newID(edges), _value, _srcId, _dstId);
	}
	@Override
	public GTEdge.Undirected				 					addEdge				(ID _srcId, ID _dstId, Object _value) throws IllegalOperationException {
		return __addEdge(__newID(edges), (_value instanceof ValueObject) ? (ValueObject) _value : ValueObject.of(_value), _srcId, _dstId);
	}

	@Override
	public boolean 								containsEdge		(ID _edgeId) {
		return edges.containsKey(_edgeId);
	}
	@Override
	public boolean 								containsEdge		(Object _edgeValue) {
		return __getEdgeId(_edgeValue) != null;
	}
	@Override
	public boolean 								containsEdge		(ID _srcNodeId, ID _dstNodeId) {
		if(containsNode(_srcNodeId) && containsNode(_dstNodeId))
			return nodeNeighbors.get(_srcNodeId).contains(_dstNodeId);

		return false;
	}
	@Override
	public boolean 								containsEdge		(Object _srcNode, Object _dstNode) {
		if(_srcNode instanceof GTNode.Undirected && _dstNode instanceof GTNode.Undirected) {
			GTNode.Undirected srcNode = (GTNode.Undirected) _srcNode;
			GTNode.Undirected dstNode = (GTNode.Undirected) _dstNode;

			if(containsNode(srcNode) && containsNode(dstNode))
				return nodeNeighbors.get(srcNode.getId()).contains(dstNode.getId());

		} else {
			ID srcNodeId = __getNodeId(_srcNode);
			ID dstNodeId = __getNodeId(_dstNode);
	
			if(containsNode(srcNodeId) && containsNode(dstNodeId))
				return nodeNeighbors.get(srcNodeId).contains(dstNodeId);

		}

		return false;
	}
	/*
	@Override
	public boolean 								containsEdge		(Node _srcNode, Node _dstNode) {
		if(containsNode(_srcNode.getId()) && containsNode(_dstNode.getId()))
			return nodeNeighbors.get(_srcNode.getId()).contains(_dstNode.getId());

		return false;
	}
	*/

	@Override
	public GTUEdgeC 							getEdge				(ID _nodeId) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getKey().equals(_nodeId))
						 .findFirst()
						 .map(e -> edges.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTUEdgeC					 			getEdge				(Object _node) {
		return edgeValues.entrySet()
						 .stream()
						 .filter(e -> e.getValue().getValue().equals(_node))
						 .findFirst()
						 .map(e -> edges.get(e.getKey()))
						 .orElseGet(() -> null);
	}
	@Override
	public GTUEdgeC								getEdge				(ID _srcNodeId, ID _dstNodeId) {
		ID edgeId    = __getEdgeId(_srcNodeId, _dstNodeId);

		if(edgeId == null)
			throw new NullPointerException();

		return edges.get(edgeId);
	}
	@Override
	public GTUEdgeC 							getEdge				(Object _srcNode, Object _dstNode) {
		ID srcNodeId = __getNodeId(_srcNode);
		ID dstNodeId = __getNodeId(_dstNode);
		ID edgeId    = __getEdgeId(srcNodeId, dstNodeId);

		if(edgeId == null)
			throw new NullPointerException();

		return edges.get(edgeId);
	}
/*
	@Override
	public GTUEdgeC 							getEdge				(Node _srcNode, Node _dstNode) {
		ID edgeId    = __getEdgeId(_srcNode.getId(), _dstNode.getId());

		if(edgeId == null)
			throw new NullPointerException();

		return edges.get(edgeId);
	}
*/
	@Override
	public void 								removeEdge			(ID _edgeId) {
		if(!containsEdge(_edgeId))
			throw new NullPointerException();

		__removeEdge(_edgeId);
	}
	@Override
	public void 								removeEdge			(Object _edge) {
		if(_edge instanceof GTEdge.Undirected)
			__removeEdge(((GTEdge.Undirected) _edge).getId());
		else
			__removeEdge(getEdge(_edge).getId());
	}
	@Override
	public void 								removeEdge			(ID _srcId, ID _dstId) {
		if(!containsNode(_srcId) || !containsNode(_dstId))
			throw new NullPointerException();

		Pair<ID, ID> ids = Tuples.of(_srcId, _dstId);

		ID edgeId = __getEdgeId(_srcId, _dstId);
		if(edgeId == null)
			throw new NullPointerException();

		__removeEdge(edgeId);
	}
	@Override
	public void 								removeEdge			(Object _src, Object _dst) {
		if(_src instanceof GTNode.Undirected && _dst instanceof GTNode.Undirected)
			removeEdge(((GTNode.Undirected) _src).getId(), ((GTNode.Undirected) _dst).getId());
		else
			removeEdge(getNode(_src).getId(), getNode(_dst).getId());
	}

    // ... ~ Graph Aspects
	@Override
    public BeanPropertySet<? extends GTEdge.Undirected>		getEdges			() {
		return edges_reverse.keySet();
	}

    // ... ~ Edge Aspects
	@Override
	public ValueObject							setEdgeValue		(ID _edgeId, ValueObject _newValue) throws AlreadyExistingNodeException {
		throw new NotYetImplementedException();
	}
	@Override
	public Object								setEdgeValue		(ID _edgeId, Object _newValue) {
		throw new NotYetImplementedException();
	}

	@Override
	public Pair<ID, ID>							getApexIds			(ID _edgeId) {
		return bounds.get(_edgeId);
	}
	@Override
	public Pair<? extends GTNode.Undirected, ? extends GTNode.Undirected> getApexNodes		(ID _edgeId) {
		Pair<ID, ID> ids = bounds.get(_edgeId);
		return Tuples.of(getNode(ids.getFirst()), getNode(ids.getSecond()));
	}

	// PACKAGE METHODS
	public int 									getNodeDegree		(ID _nodeId) {
		return getConnections(_nodeId).size();
	}
	public GTUNodeC				 				getNodeObject		(ID _nodeId) {
		return nodes.get(_nodeId);
	}
	public ValueObject 							getNodeValueObject	(ID _nodeId) {
		return nodeValues.get(_nodeId);
	}
	public ValueType<?> 						getNodeValueType	(ID _nodeId) {
		return nodeValues.get(_nodeId).getType();
	}
	public Object 								getNodeValue		(ID _nodeId) {
		return nodeValues.get(_nodeId).getValue();
	}

	public GTUEdgeC				 				getEdgeObject		(ID _edgeId) {
		return edges.get(_edgeId);
	}
	public ValueObject 							getEdgeValueObject	(ID _edgeId) {
		return edgeValues.get(_edgeId);
	}
	public ValueType<?>							getEdgeValueType	(ID _edgeId) {
		return edgeValues.get(_edgeId).getType();
	}
	public Object 								getEdgeValue		(ID _edgeId) {
		return edgeValues.get(_edgeId).getValue();
	}

	// INTERNAL METHODS
	private ID									__newID				(Map<ID, ?> _register) {
		ID newId = IDs.random(GT.DEFAULT_ID_SIZE);

		while(_register.containsKey(newId))
			newId = IDs.random(GT.DEFAULT_ID_SIZE);
		
		return newId;
	}

	private GTNode.Undirected								__addNode			(ID _nodeId, ValueObject _nodeValue) {
		if(nodes.containsKey(_nodeId))
			throw new AlreadyExistingNodeException();

		GTUNodeC newNode = new GTUNodeC(this, _nodeId);

		nodes			. put(_nodeId, newNode);
		nodes_reverse	. put(newNode, _nodeId);

		nodeValues      . put(_nodeId, _nodeValue);
		nodeNeighbors   . put(_nodeId, new SimpleBeanPropertySet<ID>());

		return newNode;
	}
	private void								__removeNode		(ID _nodeId) {
		if(_nodeId == null)
			throw new NullPointerException();

		bounds.entrySet().stream()
							 .filter(e -> e.getValue().contains(_nodeId))
							 .map(e -> e.getKey())
							 .collect(Collectors.toList())
								 .stream()
								 .forEach(id -> __removeEdge(id));

		Set<ID> nexts = nodeNeighbors.get(_nodeId);
		if(nexts == null)
			throw new NullPointerException();

		for(ID n : nexts) {
			Set<ID> nodeInNexts = nodeNeighbors.get(n);
			nodeInNexts.remove(_nodeId);
		}
		nodeNeighbors.remove(_nodeId);

		GTUNodeC node = nodes.get(_nodeId);

		// Remove the node
		nodes           . remove(_nodeId);
		nodes_reverse	. remove(node);

		nodeValues      . remove(_nodeId);
	}

	private GTEdge.Undirected 								__addEdge			(ID _edgeId, ValueObject _edgeValue, ID _srcId, ID _dstId) {
		if(!containsNode(_srcId) && !containsNode(_dstId))
			throw new NullPointerException();
		if(containsEdge(_edgeId)  || containsEdge(_srcId, _dstId))
			throw new IllegalOperationException("Already existing edge...");
		if(_edgeValue == null)
			throw new NullPointerException();

		Set<ID>  srcNext     = nodeNeighbors.get(_srcId);
		Set<ID>  dstPrevious = nodeNeighbors.get(_dstId);
		GTUEdgeC newEdge     = new GTUEdgeC(this, _edgeId);

		edges         . put(_edgeId, newEdge);
		edges_reverse . put(newEdge, _edgeId);

		edgeValues    . put(_edgeId, _edgeValue);
		bounds        . put(_edgeId, Tuples.of(_srcId, _dstId));
		srcNext       . add(_dstId);
		dstPrevious   . add(_srcId);

		return newEdge;
	}
	private void 								__removeEdge		(ID _edgeId) {
		Pair<ID, ID> apexIds     = getApexIds(_edgeId);
		Set<ID>      srcNext     = nodeNeighbors.get(apexIds.getFirst());
		Set<ID>      dstPrevious = nodeNeighbors.get(apexIds.getFirst());

		srcNext     . remove(apexIds.getSecond());
		dstPrevious . remove(apexIds.getFirst());

		GTUEdgeC edge = edges.get(_edgeId);

		edges         . remove(_edgeId);
		edges_reverse . remove(edge);

		edgeValues 	  . remove(_edgeId);
		bounds        . remove(_edgeId);

	}

	private ID 									__getNodeId			(Object _nodeValue) {
		ID     nodeId = nodeValues.entrySet()
								  .stream()
								  .filter(e -> _nodeValue.equals(e.getValue()))
								  .findFirst()
								  .map(Map.Entry::getKey)
								  .get();

		return nodeId;
	}
	private ID 									__getEdgeId			(Object _edgeValue) {
		ID     edgeId = edgeValues.entrySet()
								  .stream()
								  .filter(e -> _edgeValue.equals(e.getValue()))
								  .findFirst()
								  .map(Map.Entry::getKey)
								  .get();

		return edgeId;
	}
	private ID 									__getEdgeId			(ID _srcId, ID _dstId) {
		if(!containsNode(_srcId) || !containsNode(_dstId))
			throw new NullPointerException();

		Pair<ID, ID> ids = Tuples.of(_srcId, _dstId);

		ID edgeId = bounds.entrySet().stream().filter(e -> ids.equals(e.getValue())).map(Map.Entry::getKey).findFirst().get();

		return edgeId;
	}

}
