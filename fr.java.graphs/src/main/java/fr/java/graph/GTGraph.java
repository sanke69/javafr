package fr.java.graph;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.java.beans.impl.BeanPropertyList;
import fr.java.beans.impl.BeanPropertySet;
import fr.java.graph.algorithms.traversal.GTGraphTraversal;
import fr.java.graph.impl.exceptions.AlreadyExistingEdgeException;
import fr.java.lang.exceptions.AlreadyExistingGateException;
import fr.java.lang.exceptions.AlreadyExistingNodeException;
import fr.java.lang.exceptions.IllegalOperationException;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.capabilities.Identifiable;
import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;
import fr.java.patterns.valueable.ValueObject;

public interface GTGraph extends Identifiable, Composite.Single {

	public boolean 									isDirected();

    // GT API + Composite
    // ==================
	@Override
	public default GTGraph					 		getParent() {
		return null;
	}
	@Override
	public default Set<? extends Component.Single> 	getChildren() {
		return (Set<? extends Component.Single>) Stream.concat(getNodes().stream(), getEdges().stream()).collect(Collectors.toSet());
	}

    // GRAPH Visitor
    // =============
	public void 									traverse			(ID _startId, GTGraphTraversal _traversal);

    // NODE Related Methods
    // ====================
    public GTNode									addNode				()						  				throws IllegalOperationException;
	public GTNode									addNode				(ValueObject _nodeValue)			  	throws IllegalOperationException, AlreadyExistingNodeException;
    public GTNode									addNode				(Object _nodeValue)				  		throws IllegalOperationException, AlreadyExistingNodeException;
    public GTNode									addNode				(ID _nodeId)              				throws IllegalOperationException, AlreadyExistingNodeException;
	public GTNode									addNode				(ID _nodeId, ValueObject _nodeValue)   	throws IllegalOperationException, AlreadyExistingNodeException;
    public GTNode									addNode				(ID _nodeId, Object _nodeValue)    		throws IllegalOperationException, AlreadyExistingNodeException;

    public boolean 									containsNode		(ID _nodeId);
    public boolean 									containsNode		(Object _nodeValue);

	public GTNode									getNode				(ID _nodeId);
	public GTNode									getNode				(Object _nodeValue);

    public void										removeNode			(ID _nodeId);
    public void										removeNode			(Object _nodeValue);

    // ... ~ Graph Aspects
    public default boolean							hasNodes			() 										{ return ! getNodes().isEmpty(); }
    public BeanPropertySet<? extends GTNode>		getNodes			();

    // ... ~ Node Aspects
	public ValueObject								setNodeValue		(ID _nodeId, ValueObject _newValue) 	throws AlreadyExistingNodeException;
    public Object									setNodeValue		(ID _nodeId, Object _newValue) 			throws AlreadyExistingNodeException;

	public boolean 									hasNeighbors		(ID _nodeId);
    public Collection<? extends GTNode> 			getNeighbors		(ID _nodeId);

	public boolean 									hasConnections		(ID _nodeId);
    public Collection<? extends GTEdge> 			getConnections		(ID _nodeId);

    // EDGE Related Methods
    // ====================
    public boolean 									containsEdge		(ID _edgeId);
    public boolean 									containsEdge		(Object _edgeValue);

	public GTEdge									getEdge				(ID _edgeId);
	public GTEdge									getEdge				(Object _edgeValue);

    public void										removeEdge			(ID _edgeId);
    public void										removeEdge			(Object _edgeValue);

    // ... ~ Graph Aspects
	public default boolean							hasEdges			() 										{ return ! getEdges().isEmpty(); }
	public BeanPropertySet<? extends GTEdge>		getEdges			();

    // ... ~ Edge Aspects
	public ValueObject								setEdgeValue		(ID _edgeId, ValueObject _newValue);
	public Object									setEdgeValue		(ID _edgeId, Object _newValue);

	public Pair<ID, ID>								getApexIds			(ID _edgeId);
	public Pair<? extends GTNode, ? extends GTNode>	getApexNodes		(ID _edgeId);

	public interface Undirected extends GTGraph {

		@Override
		public default boolean 							isDirected				() { return false; }

	    // NODE Related Methods
	    // ====================
	    public GTNode.Undirected						addNode					()						  									throws IllegalOperationException;
		public GTNode.Undirected						addNode					(ValueObject _nodeValue)			  						throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Undirected						addNode					(Object _nodeValue)				  							throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Undirected						addNode					(ID _nodeId)              									throws IllegalOperationException, AlreadyExistingNodeException;
		public GTNode.Undirected						addNode					(ID _nodeId, ValueObject _nodeValue)   						throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Undirected						addNode					(ID _nodeId, Object _nodeValue)    							throws IllegalOperationException, AlreadyExistingNodeException;

		public GTNode.Undirected						getNode					(ID _nodeId);
		public GTNode.Undirected						getNode					(Object _nodeValue);

	    // ... ~ Graph Aspects
	    public BeanPropertySet<? extends GTNode.Undirected>			getNodes				();

	    // ... ~ Node Aspects
	    public Collection<? extends GTNode.Undirected> 	getNeighbors			(ID _nodeId);

	    public Collection<? extends GTEdge.Undirected> 				getConnections			(ID _nodeId);

	    // EDGE Related Methods
	    // ====================
		public GTEdge.Undirected										addEdge					(ID _srcId, ID _dstId) 										throws IllegalOperationException, AlreadyExistingEdgeException;
		public GTEdge.Undirected										addEdge					(ID _srcId, ID _dstId, ValueObject _value) 					throws IllegalOperationException, AlreadyExistingEdgeException;
		public GTEdge.Undirected										addEdge					(ID _srcId, ID _dstId, Object _value) 						throws IllegalOperationException, AlreadyExistingEdgeException;

		public boolean 									containsEdge			(ID _srcNodeId, ID _dstNodeId);
		public boolean 									containsEdge			(Object _srcNode, Object _dstNode);

		public GTEdge.Undirected										getEdge					(ID _edgeId);
		public GTEdge.Undirected										getEdge					(Object _edgeValue);

		public GTEdge.Undirected 									getEdge					(ID _srcNodeId, ID _dstNodeId);
		public GTEdge.Undirected										getEdge					(Object _srcNode, Object _dstNode);

	    public void										removeEdge				(Object _edge);
	    public void 									removeEdge				(ID _srcId, ID _dstId);
		public void 									removeEdge				(Object _src, Object _dst);

	    // ... ~ Graph Aspects
	    public BeanPropertySet<? extends GTEdge.Undirected>			getEdges				();

	    // ... ~ Edge Aspects
		public Pair<? extends GTNode.Undirected, ? extends GTNode.Undirected>		getApexNodes			(ID _edgeId);

		/* ************************************************************************** *\
		 *                                  EXTRA METHODS                             *
		 *                                 = = = = = = = =                            *
		\* ************************************************************************** */

		public default boolean 							containsNode			(Object _node) {
			if(_node instanceof GTNode.Undirected)
				return containsNode(((GTNode.Undirected) _node).getId());
			return getNode(_node) != null;
		}

		public default boolean 							hasNeighbors			(Object _node) {
			if(_node instanceof ID)
				return hasNeighbors((ID)_node);

			if(_node instanceof GTNode.Undirected)
				return hasNeighbors(((GTNode.Undirected)_node).getId());

			ID nodeId = getNode(_node).getId();
			return hasNeighbors(nodeId);
		}
	    public default Collection<? extends GTNode.Undirected> 		getNeighbors			(Object _node) {
			if(_node instanceof ID)
				return getNeighbors((ID)_node);

			if(_node instanceof GTNode.Undirected)
				return getNeighbors(((GTNode.Undirected)_node).getId());

			ID nodeId = getNode(_node).getId();
			return getNeighbors(nodeId);
		}

		public default boolean 							hasConnections			(Object _node) {
			if(_node instanceof ID)
				return hasConnections((ID)_node);

			if(_node instanceof GTNode.Undirected)
				return hasConnections(((GTNode.Undirected)_node).getId());

			ID nodeId = getNode(_node).getId();
			return hasConnections(nodeId);
		}
	    public default Collection<? extends GTEdge.Undirected> 		getConnections			(Object _node) {
			if(_node instanceof ID)
				return getConnections((ID)_node);

			if(_node instanceof GTNode.Undirected)
				return getConnections(((GTNode.Undirected)_node).getId());

			ID nodeId = getNode(_node).getId();
			return getConnections(nodeId);
		}

	    public default GTEdge.Undirected				 				addEdge					(Object _src, Object _dst) 									throws IllegalOperationException, AlreadyExistingEdgeException {
			if(_src instanceof GTNode.Undirected && _dst instanceof GTNode.Undirected)
				return addEdge(((GTNode.Undirected) _src).getId(), ((GTNode.Undirected) _dst).getId());
			return addEdge(getNode(_src).getId(), getNode(_dst).getId());
		}
		public default GTEdge.Undirected								addEdge					(Object _src, Object _dst, ValueObject _value) 				throws IllegalOperationException, AlreadyExistingEdgeException {
			if(_src instanceof GTNode.Undirected && _dst instanceof GTNode.Undirected)
				return addEdge(((GTNode.Undirected) _src).getId(), ((GTNode.Undirected) _dst).getId(), _value);
			return addEdge(getNode(_src).getId(), getNode(_dst).getId(), _value);
		}
		public default GTEdge.Undirected								addEdge					(Object _src, Object _dst, Object _value) 					throws IllegalOperationException, AlreadyExistingEdgeException {
			if(_src instanceof GTNode.Undirected && _dst instanceof GTNode.Undirected)
				return addEdge(((GTNode.Undirected) _src).getId(), ((GTNode.Undirected) _dst).getId(), _value);
			return addEdge(getNode(_src).getId(), getNode(_dst).getId(), _value);
		}

	}
	public interface Directed   extends GTGraph {
	
		@Override
		public default boolean 							isDirected				() {
			return true;
		}
	
	    // NODE Related Methods
	    // ====================
	    public GTNode.Directed							addNode					()						  				throws IllegalOperationException;
		public GTNode.Directed							addNode					(ValueObject _value)				  	throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Directed							addNode					(Object _value)				  			throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Directed							addNode					(ID _nodeId)              				throws IllegalOperationException, AlreadyExistingNodeException;
		public GTNode.Directed							addNode					(ID _nodeId, ValueObject _value)    	throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Directed							addNode					(ID _nodeId, Object _value)    			throws IllegalOperationException, AlreadyExistingNodeException;
	
		public GTNode.Directed							getNode					(ID _nodeId)							throws IllegalOperationException;
		public GTNode.Directed							getNode					(Object _nodeValue)						throws IllegalOperationException;
	
	    // ... ~ Graph Aspects
	    public BeanPropertySet<? extends GTNode.Directed>			getNodes				();
	
	    // ... ~ Node Aspects
		@Override
		public default boolean 							hasNeighbors			(ID _nodeId) {
			return hasPredecessors(_nodeId) || hasSuccessors(_nodeId);
		}
		@Override
	    public default Collection<? extends GTNode.Directed> 		getNeighbors			(ID _nodeId) {
			return Stream.concat(getPredecessors(_nodeId).stream(), getSuccessors(_nodeId).stream()).collect(Collectors.toList());
		}
	
		public boolean 									hasPredecessors			(ID _nodeId);
	    public Collection<? extends GTNode.Directed> 				getPredecessors			(ID _nodeId);
	
		public boolean 									hasSuccessors			(ID _nodeId);
	    public Collection<? extends GTNode.Directed> 				getSuccessors			(ID _nodeId);
	
		@Override
		public default boolean 							hasConnections			(ID _nodeId) {
			return hasIncomings(_nodeId) || hasOutcomings(_nodeId);
		}
		@Override
	    public default Collection<? extends GTEdge.Directed> 		getConnections			(ID _nodeId) {
			return Stream.concat(getIncomings(_nodeId).stream(), getOutcomings(_nodeId).stream()).collect(Collectors.toList());
		}
	
		public boolean 									hasIncomings			(ID _nodeId);
	    public Collection<? extends GTEdge.Directed> 				getIncomings			(ID _nodeId);
	
		public boolean 									hasOutcomings			(ID _nodeId);
	    public Collection<? extends GTEdge.Directed> 				getOutcomings			(ID _nodeId);
	
	    // EDGE Related Methods
	    // ====================
	    public GTEdge.Directed										addEdge					(ID _srcId, ID _dstId) 									throws IllegalOperationException;
		public GTEdge.Directed										addEdge					(ID _srcId, ID _dstId, ValueObject _value) 				throws IllegalOperationException;
	    public GTEdge.Directed										addEdge					(ID _srcId, ID _dstId, Object _value) 					throws IllegalOperationException;
	
	    public boolean 									containsEdge			(ID _srcNodeId, ID _dstNodeId);
		public boolean 									containsEdge			(Object _srcNode, Object _dstNode);
	
		public GTEdge.Directed										getEdge					(ID _edgeId);
		public GTEdge.Directed										getEdge					(Object _edgeObj);
		public GTEdge.Directed 									getEdge					(ID _srcNodeId, ID _dstNodeId);
		public GTEdge.Directed										getEdge					(Object _srcNodeObj, Object _dstNodeObj);
	
	    public void 									removeEdge				(ID _srcId, ID _dstId);
		public void 									removeEdge				(Object _src, Object _dst) ;
	
	    // ... ~ Graph Aspects
	    public BeanPropertySet<? extends GTEdge.Directed>			getEdges				();
	
	    // ... ~ Edge Aspects
		public Pair<? extends GTNode.Directed, ? extends GTNode.Directed>		getApexNodes			(ID _edgeId);
	
		/* ****************************************************************************************** *\
		 *                                         EXTRA METHODS                                      *
		 *                                        = = = = = = = =                                     *
		\* ****************************************************************************************** */
	
		public default boolean 							containsNode			(Object _node) {
			if(_node instanceof GTNode.Directed)
				return containsNode(((GTNode.Directed) _node).getId());
			return getNode(_node) != null;
		}
	
		public default boolean 							hasNeighbors			(Object _node) {
			if(_node instanceof ID)
				return hasNeighbors((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return hasNeighbors(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return hasNeighbors(nodeId);
		}
	    public default Collection<? extends GTNode.Directed> 		getNeighbors			(Object _node) {
			if(_node instanceof ID)
				return getNeighbors((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return getNeighbors(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return getNeighbors(nodeId);
		}
	
		public default boolean 							hasPredecessors			(Object _node) {
			if(_node instanceof ID)
				return hasPredecessors((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return hasPredecessors(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return hasPredecessors(nodeId);
		}
	    public default Collection<? extends GTNode.Directed> 		getPredecessors			(Object _node) {
			if(_node instanceof ID)
				return getPredecessors((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return getPredecessors(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return getPredecessors(nodeId);
		}
	
		public default boolean 							hasSuccessors			(Object _node) {
			if(_node instanceof ID)
				return hasSuccessors((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return hasSuccessors(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return hasSuccessors(nodeId);
		}
	    public default Collection<? extends GTNode.Directed> 		getSuccessors			(Object _node) {
			if(_node instanceof ID)
				return getSuccessors((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return getSuccessors(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return getSuccessors(nodeId);
		}
	
		public default boolean 							hasConnections			(Object _node) {
			if(_node instanceof ID)
				return hasConnections((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return hasConnections(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return hasConnections(nodeId);
		}
	    public default Collection<? extends GTEdge.Directed> 		getConnections			(Object _node) {
			if(_node instanceof ID)
				return getConnections((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return getConnections(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return getConnections(nodeId);
		}
	
		public default boolean 							hasIncomings			(Object _node) {
			if(_node instanceof ID)
				return hasIncomings((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return hasIncomings(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return hasIncomings(nodeId);
		}
	    public default Collection<? extends GTEdge.Directed> 		getIncomings			(Object _node) {
			if(_node instanceof ID)
				return getIncomings((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return getIncomings(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return getIncomings(nodeId);
		}
	
		public default boolean 							hasOutcomings			(Object _node) {
			if(_node instanceof ID)
				return hasOutcomings((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return hasOutcomings(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return hasOutcomings(nodeId);
		}
	    public default Collection<? extends GTEdge.Directed> 		getOutcomings			(Object _node) {
			if(_node instanceof ID)
				return getOutcomings((ID)_node);
	
			if(_node instanceof GTNode.Directed)
				return getOutcomings(((GTNode.Directed)_node).getId());
	
			ID nodeId = getNode(_node).getId();
			return getOutcomings(nodeId);
		}
	
	    public default GTEdge.Directed				 				addEdge					(Object _src, Object _dst) 									throws IllegalOperationException, AlreadyExistingEdgeException {
			if(_src instanceof GTNode.Directed && _dst instanceof GTNode.Directed)
				return addEdge(((GTNode.Directed) _src).getId(), ((GTNode.Directed) _dst).getId());
			return addEdge(getNode(_src).getId(), getNode(_dst).getId());
		}
		public default GTEdge.Directed								addEdge					(Object _src, Object _dst, ValueObject _value) 				throws IllegalOperationException, AlreadyExistingEdgeException {
			if(_src instanceof GTNode.Directed && _dst instanceof GTNode.Directed)
				return addEdge(((GTNode.Directed) _src).getId(), ((GTNode.Directed) _dst).getId(), _value);
			return addEdge(getNode(_src).getId(), getNode(_dst).getId(), _value);
		}
		public default GTEdge.Directed								addEdge					(Object _src, Object _dst, Object _value) 					throws IllegalOperationException, AlreadyExistingEdgeException {
			if(_src instanceof GTNode.Directed && _dst instanceof GTNode.Directed)
				return addEdge(((GTNode.Directed) _src).getId(), ((GTNode.Directed) _dst).getId(), _value);
			return addEdge(getNode(_src).getId(), getNode(_dst).getId(), _value);
		}
	
	}
	public interface Gated      extends GTGraph {
	
		@Override
		public default boolean 							isDirected				() { return true; }
	
		// TODO:: Add gates ??? if recursive
		@Override
		public default Set<? extends Component.Single> 	getChildren				() {
			return (Set<? extends Component.Single>) Stream.concat(getNodes().stream(), getEdges().stream()).collect(Collectors.toSet());
		}
		public default Set<? extends Component.Single> 	getChildren				(boolean _recursive) {
			return (Set<? extends Component.Single>) Stream.concat(getNodes().stream(), getEdges().stream()).collect(Collectors.toSet());
		}
	
	    // NODE Related Methods
	    // ====================
	    public GTNode.Gated								addNode					()						  																			throws IllegalOperationException;
		public GTNode.Gated								addNode					(ValueObject _value)				  																throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Gated								addNode					(Object _value)				  																		throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Gated								addNode					(ID _nodeId)              																			throws IllegalOperationException, AlreadyExistingNodeException;
		public GTNode.Gated								addNode					(ID _nodeId, ValueObject _value)    																throws IllegalOperationException, AlreadyExistingNodeException;
	    public GTNode.Gated								addNode					(ID _nodeId, Object _value)    																		throws IllegalOperationException, AlreadyExistingNodeException;
	
		public GTNode.Gated								getNode					(ID _nodeId);
		public GTNode.Gated								getNode					(Object _nodeValue);
	
		public GTGate									getGate					(ID _gateId);
		public GTGate									getGate					(Object _gateValue);
	
	    public void										removeGate				(ID _gateId);
	    public void										removeGate				(Object _gateValue);
	
	    // ... ~ Gate Aspects
	    public BeanPropertySet<? extends GTNode.Gated>	getNodes				();
	
		public BeanPropertySet<? extends GTGate> 		getGates				();
	
	    // ... ~ Node Aspects
		public ValueObject								setGateValue			(ID _gateId, ValueObject _newValue) 																throws AlreadyExistingGateException;
	    public Object									setGateValue			(ID _gateId, Object _newValue) 																		throws AlreadyExistingGateException;
	
		public GTGate									addGate					(ID _nodeId, boolean _isInput)																		throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addGate					(ID _nodeId, ValueObject _value, boolean _isInput)													throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addGate					(ID _nodeId, Object _value, boolean _isInput)														throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addGate					(ID _nodeId, ID _gateId, boolean _isInput)															throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addGate					(ID _nodeId, ID _gateId, ValueObject _value, boolean _isInput)										throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addGate					(ID _nodeId, ID _gateId, Object _value, boolean _isInput)											throws IllegalOperationException, AlreadyExistingGateException;
	
		public GTGate									addInput				(ID _nodeId)																						throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, int _index)																			throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ValueObject _value)																	throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ValueObject _value, int _index)														throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, Object _value)																			throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, Object _value, int _index)																throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ID _gateId)																			throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ID _gateId, int _index)																throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ID _gateId, ValueObject _value)														throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ID _gateId, ValueObject _value, int _index)											throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ID _gateId, Object _value)																throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addInput				(ID _nodeId, ID _gateId, Object _value, int _index)													throws IllegalOperationException, AlreadyExistingGateException;
	
		public GTGate									addOutput				(ID _nodeId)																						throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, int _index)																			throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ValueObject _value)																	throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ValueObject _value, int _index)														throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, Object _value)																			throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, Object _value, int _index)																throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ID _gateId)																			throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ID _gateId, int _index)																throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ID _gateId, ValueObject _value)														throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ID _gateId, ValueObject _value, int _index)											throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ID _gateId, Object _value)																throws IllegalOperationException, AlreadyExistingGateException;
		public GTGate									addOutput				(ID _nodeId, ID _gateId, Object _value, int _index)													throws IllegalOperationException, AlreadyExistingGateException;
	
	
		public default boolean				 			hasGates				(ID _nodeId) {
			return hasInputs(_nodeId) || hasOutputs(_nodeId);
		}
		public default Collection<? extends GTGate>		getGates				(ID _nodeId) {
			return Stream.concat(getInputs(_nodeId).stream(), getOutputs(_nodeId).stream()).collect(Collectors.toSet());
		}
	
		public boolean				 					hasInputs				(ID _nodeId);
		public BeanPropertyList<? extends GTGate>		getInputs				(ID _nodeId);
		public GTGate									getInput				(ID _nodeId, int _inputIdx);
	
		public boolean				 					hasOutputs				(ID _nodeId);
		public BeanPropertyList<? extends GTGate>		getOutputs				(ID _nodeId);
		public GTGate									getOutput				(ID _nodeId, int _outputIdx);
	
	    public default void								removeInput				(ID _inputId) 																						{ removeGate(_inputId); }
	    public default void								removeOutput			(ID _outputId) 																						{ removeGate(_outputId); }
	
	    public void										removeInput				(ID _nodeId, int _inputIdx);
	    public void										removeOutput			(ID _nodeId, int _outputIdx);
	
		@Override
		public default boolean 							hasNeighbors			(ID _nodeId) {
			return hasPredecessors(_nodeId) || hasSuccessors(_nodeId);
		}
		@Override
	    public default Collection<? extends GTNode.Gated> 		getNeighbors			(ID _nodeId) {
			return Stream.concat(getPredecessors(_nodeId).stream(), getSuccessors(_nodeId).stream()).collect(Collectors.toList());
		}
	
		public boolean 									hasPredecessors			(ID _nodeId);
	    public Collection<? extends GTNode.Gated> 		getPredecessors			(ID _nodeId);
		public boolean 									hasPredecessors			(ID _nodeId, int _inputId);
	    public Collection<? extends GTNode.Gated> 		getPredecessors			(ID _nodeId, int _inputId);
	
		public boolean 									hasSuccessors			(ID _nodeId);
	    public Collection<? extends GTNode.Gated> 		getSuccessors			(ID _nodeId);
		public boolean 									hasSuccessors			(ID _nodeId, int _outputId);
	    public Collection<? extends GTNode.Gated> 		getSuccessors			(ID _nodeId, int _outputId);
	
		@Override
		public default boolean 							hasConnections			(ID _nodeId) {
			return hasIncomings(_nodeId) || hasOutcomings(_nodeId);
		}
		@Override
	    public default Collection<? extends GTEdge.Gated> 		getConnections			(ID _nodeId) {
			return Stream.concat(getIncomings(_nodeId).stream(), getOutcomings(_nodeId).stream()).collect(Collectors.toList());
		}
	
		public boolean 									hasIncomings			(ID _nodeId);
	    public Collection<? extends GTEdge.Gated> 		getIncomings			(ID _nodeId);
		public boolean 									hasIncomings			(ID _nodeId, int _inputId);
	    public Collection<? extends GTEdge.Gated> 		getIncomings			(ID _nodeId, int _inputId);
	
		public boolean 									hasOutcomings			(ID _nodeId);
	    public Collection<? extends GTEdge.Gated> 		getOutcomings			(ID _nodeId);
		public boolean 									hasOutcomings			(ID _nodeId, int _outputId);
	    public Collection<? extends GTEdge.Gated> 		getOutcomings			(ID _nodeId, int _outputId);
	
	    // EDGE Related Methods
	    // ====================
		public GTEdge.Gated								addEdge					(ID _srcNode, ID _srcGate, ID _dstNode, ID _dstGate) 												throws IllegalOperationException;
		public GTEdge.Gated								addEdge					(ID _srcNode, ID _srcGate, ID _dstNode, ID _dstGate, ValueObject _value) 							throws IllegalOperationException;
		public GTEdge.Gated								addEdge					(ID _srcNode, ID _srcGate, ID _dstNode, ID _dstGate, Object _value) 								throws IllegalOperationException;
		public GTEdge.Gated 							addEdge					(ID _srcNode, int _srcGateIdx, ID _dstNode, int _dstGateIdx) 										throws IllegalOperationException;
		public GTEdge.Gated 							addEdge					(ID _srcNode, int _srcGateIdx, ID _dstNode, int _dstGateIdx, ValueObject _value) 					throws IllegalOperationException;
		public GTEdge.Gated 							addEdge					(ID _srcNode, int _srcGateIdx, ID _dstNode, int _dstGateIdx, Object _value) 						throws IllegalOperationException;
	
		public boolean 									containsEdge			(ID _srcNodeId, ID _dstNodeId);
		public boolean 									containsEdge			(Object _srcNode, Object _dstNode);
	
		public GTEdge.Gated								getEdge					(ID _edgeId);
		public GTEdge.Gated								getEdge					(Object _edgeValue);
		public Collection<GTEdge.Gated> 				getEdges				(ID _srcNodeId, ID _dstNodeId);	// Could be gate Ids => Only One elements!
		public GTEdge.Gated								getEdge					(ID _srcId, int _srcGateIdx, ID _dstId, int _dstGateIdx);
		public Collection<GTEdge.Gated>					getEdges				(Object _srcNodeValue, Object _dstNodeValue);
		public GTEdge.Gated								getEdge					(Object _srcNodeValue, int _srcGateIdx, Object _dstNodeValue, int _dstGateIdx);
	
	    public void										removeEdge				(GTEdge.Gated _edge);
	    public void 									removeEdges				(ID _srcId, ID _dstId);
	    public void 									removeEdge				(ID _srcId, int _srcGateIdx, ID _dstId, int _dstGateIdx);
		public void 									removeEdges				(Object _src, Object _dst);
	    public void 									removeEdge				(Object _src, int _srcGateIdx, Object _dst, int _dstGateIdx);
	
	    // ... ~ Graph Aspects
	    public BeanPropertySet<? extends GTEdge.Gated>	getEdges				();
	
	    // ... ~ Edge Aspects
		public Pair<ID, ID> 							getApexGateIds			(ID _edgeId);
	
		public Pair<? extends GTNode.Gated, ? extends GTNode.Gated>		getApexNodes			(ID _edgeId);
	
		public Pair<? extends GTGate, ? extends GTGate>	getApexGates			(ID _edgeId);
	
	
		/* ****************************************************************************************** *\
		 *                                       EXTRA METHODS                                        *
		 *                                      = = = = = = = =                                       *
		\* ****************************************************************************************** */
	
		public default GTGate							addGate					(Object _node, boolean _isInput)																	throws IllegalOperationException, AlreadyExistingGateException {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return _isInput ? addInput(nodeId) : addOutput(nodeId);
		}
		public default GTGate							addGate					(Object _node, ValueObject _value, boolean _isInput)												throws IllegalOperationException, AlreadyExistingGateException {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return _isInput ? addInput(nodeId, _value) : addOutput(nodeId, _value);
		}
		public default GTGate							addGate					(Object _node, Object _value, boolean _isInput)														throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			ValueObject value  = _value instanceof ValueObject ? (ValueObject) _value : ValueObject.of(_value);
			return _isInput ? addInput(nodeId, value) : addOutput(nodeId, value);
		}
		public default GTGate							addGate					(Object _node, ID _gateId, boolean _isInput)														throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return _isInput ? addInput(nodeId, _gateId) : addOutput(nodeId, _gateId);
		}
		public default GTGate							addGate					(Object _node, ID _gateId, ValueObject _value, boolean _isInput)									throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return _isInput ? addInput(nodeId, _gateId, _value) : addOutput(nodeId, _gateId, _value);
		}
		public default GTGate							addGate					(Object _node, ID _gateId, Object _value, boolean _isInput)											throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			ValueObject value  = _value instanceof ValueObject ? (ValueObject) _value : ValueObject.of(_value);
			return _isInput ? addInput(nodeId, _gateId, value) : addOutput(nodeId, _gateId, value);
		}
	
		public default GTGate							addInput				(Object _node)																						throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId);
		}
		public default GTGate							addInput				(Object _node, int _index)																			throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _index);
		}
		public default GTGate							addInput				(Object _node, ValueObject _value)																	throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _value);
		}
		public default GTGate							addInput				(Object _node, ValueObject _value, int _index)														throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _value, _index);
		}
		public default GTGate							addInput				(Object _node, Object _value)																		throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _value);
		}
		public default GTGate							addInput				(Object _node, Object _value, int _index)															throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _value, _index);
		}
		public default GTGate							addInput				(Object _node, ID _gateId)																			throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _gateId);
		}
		public default GTGate							addInput				(Object _node, ID _gateId, int _index)																throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _gateId, _index);
		}
		public default GTGate							addInput				(Object _node, ID _gateId, ValueObject _value)														throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _gateId, _value);
		}
		public default GTGate							addInput				(Object _node, ID _gateId, ValueObject _value, int _index)											throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _gateId, _value, _index);
		}
		public default GTGate							addInput				(Object _node, ID _gateId, Object _value)															throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _gateId, _value);
		}
		public default GTGate							addInput				(Object _node, ID _gateId, Object _value, int _index)												throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addInput(nodeId, _gateId, _value, _index);
		}
	
		public default GTGate							addOutput				(Object _node)																						throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId);
		}
		public default GTGate							addOutput				(Object _node, int _index)																			throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId);
		}
		public default GTGate							addOutput				(Object _node, ValueObject _value)																	throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _value);
		}
		public default GTGate							addOutput				(Object _node, ValueObject _value, int _index)														throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _value, _index);
		}
		public default GTGate							addOutput				(Object _node, Object _value)																		throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _value);
		}
		public default GTGate							addOutput				(Object _node, Object _value, int _index)															throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _value, _index);
		}
		public default GTGate							addOutput				(Object _node, ID _gateId)																			throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _gateId);
		}
		public default GTGate							addOutput				(Object _node, ID _gateId, int _index)																throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _gateId, _index);
		}
		public default GTGate							addOutput				(Object _node, ID _gateId, ValueObject _value)														throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _gateId, _value);
		}
		public default GTGate							addOutput				(Object _node, ID _gateId, ValueObject _value, int _index)											throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _gateId, _value, _index);
		}
		public default GTGate							addOutput				(Object _node, ID _gateId, Object _value)															throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _gateId, _value);
		}
		public default GTGate							addOutput				(Object _node, ID _gateId, Object _value, int _index)												throws IllegalOperationException, AlreadyExistingGateException {
			ID          nodeId = _node  instanceof GTNode.Gated        ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return addOutput(nodeId, _gateId, _value, _index);
		}
	
		public default boolean				 			hasGates				(Object _node) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return hasGates(nodeId);
		}
		public default Collection<? extends GTGate>		getGates				(Object _node) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return getGates(nodeId);
		}
	
		public default boolean				 			hasInputs				(Object _node) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return hasInputs(nodeId);
		}
		public default BeanPropertyList<? extends GTGate>	getInputs				(Object _node) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return getInputs(nodeId);
		}
		public default GTGate							getInput				(Object _node, int _inputIdx) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return getInput(nodeId, _inputIdx);
		}
	
		public default boolean				 			hasOutputs				(Object _node) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return hasOutputs(nodeId);
		}
		public default BeanPropertyList<? extends GTGate>	getOutputs				(Object _node) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return getOutputs(nodeId);
		}
		public default GTGate							getOutput				(Object _node, int _outputIdx) {
			ID nodeId = _node instanceof GTNode.Gated ? ((GTNode.Gated) _node).getId() : getNode(_node).getId();
			return getOutput(nodeId, _outputIdx);
		}
	
		public default GTEdge.Gated 					addEdge					(Object _srcNode, int _srcGateIdx, Object _dstNode, int _dstGateIdx) 									throws IllegalOperationException {
			ID srcId = _srcNode  instanceof GTNode.Gated ? ((GTNode.Gated) _srcNode).getId() : getNode(_srcNode).getId();
			ID dstId = _dstNode  instanceof GTNode.Gated ? ((GTNode.Gated) _dstNode).getId() : getNode(_dstNode).getId();
			if(srcId == null || dstId == null)
				throw new NullPointerException("src or dst are null");
			return addEdge(srcId, _srcGateIdx, dstId, _dstGateIdx);
		}
		public default GTEdge.Gated 					addEdge					(Object _srcNode, int _srcGateIdx, Object _dstNode, int _dstGateIdx, ValueObject _value) 				throws IllegalOperationException {
			ID srcId = _srcNode  instanceof GTNode.Gated ? ((GTNode.Gated) _srcNode).getId() : getNode(_srcNode).getId();
			ID dstId = _dstNode  instanceof GTNode.Gated ? ((GTNode.Gated) _dstNode).getId() : getNode(_dstNode).getId();
			if(srcId == null || dstId == null)
				throw new NullPointerException("src or dst are null");
			return addEdge(srcId, _srcGateIdx, dstId, _dstGateIdx, _value);
		}
		public default GTEdge.Gated 					addEdge					(Object _srcNode, int _srcGateIdx, Object _dstNode, int _dstGateIdx, Object _value) 					throws IllegalOperationException {
			ID srcId = _srcNode  instanceof GTNode.Gated ? ((GTNode.Gated) _srcNode).getId() : getNode(_srcNode).getId();
			ID dstId = _dstNode  instanceof GTNode.Gated ? ((GTNode.Gated) _dstNode).getId() : getNode(_dstNode).getId();
			if(srcId == null || dstId == null)
				throw new NullPointerException("src or dst are null");
			return addEdge(srcId, _srcGateIdx, dstId, _dstGateIdx, _value instanceof ValueObject ? (ValueObject) _value : ValueObject.of(_value));
		}
	
		public default GTEdge.Gated						addEdge					(Object _srcNode, Object _srcGate, Object _dstNode, Object _dstGate) 								throws IllegalOperationException {
			ID srcNodeId = _srcNode  instanceof GTNode.Gated ? ((GTNode.Gated) _srcNode).getId() : getNode(_srcNode).getId();
			ID srcGateId = _srcGate  instanceof GTGate ? ((GTGate) _srcGate).getId() : getGate(_srcGate).getId();
			ID dstNodeId = _dstNode  instanceof GTNode.Gated ? ((GTNode.Gated) _dstNode).getId() : getNode(_dstNode).getId();
			ID dstGateId = _dstGate  instanceof GTGate ? ((GTGate) _dstGate).getId() : getGate(_dstGate).getId();
			if(srcNodeId == null || dstNodeId == null)
				throw new NullPointerException("src or dst are null");
			return addEdge(srcNodeId, srcGateId, dstNodeId, dstGateId);
		}
		public default GTEdge.Gated						addEdge					(Object _srcNode, Object _srcGate, Object _dstNode, Object _dstGate, ValueObject _value) 			throws IllegalOperationException {
			ID srcNodeId = _srcNode  instanceof GTNode.Gated ? ((GTNode.Gated) _srcNode).getId() : getNode(_srcNode).getId();
			ID srcGateId = _srcGate  instanceof GTGate ? ((GTGate) _srcGate).getId() : getGate(_srcGate).getId();
			ID dstNodeId = _dstNode  instanceof GTNode.Gated ? ((GTNode.Gated) _dstNode).getId() : getNode(_dstNode).getId();
			ID dstGateId = _dstGate  instanceof GTGate ? ((GTGate) _dstGate).getId() : getGate(_dstGate).getId();
			if(srcNodeId == null || dstNodeId == null)
				throw new NullPointerException("src or dst are null");
			return addEdge(srcNodeId, srcGateId, dstNodeId, dstGateId, _value);
		}
		public default GTEdge.Gated						addEdge					(Object _srcNode, Object _srcGate, Object _dstNode, Object _dstGate, Object _value) 				throws IllegalOperationException {
			ID srcNodeId = _srcNode  instanceof GTNode.Gated ? ((GTNode.Gated) _srcNode).getId() : getNode(_srcNode).getId();
			ID srcGateId = _srcGate  instanceof GTGate ? ((GTGate) _srcGate).getId() : getGate(_srcGate).getId();
			ID dstNodeId = _dstNode  instanceof GTNode.Gated ? ((GTNode.Gated) _dstNode).getId() : getNode(_dstNode).getId();
			ID dstGateId = _dstGate  instanceof GTGate ? ((GTGate) _dstGate).getId() : getGate(_dstGate).getId();
			if(srcNodeId == null || dstNodeId == null || srcGateId == null || dstGateId == null)
				throw new NullPointerException("something is null, check it !!!");
			return addEdge(srcNodeId, srcGateId, dstNodeId, dstGateId, _value instanceof ValueObject ? (ValueObject) _value : ValueObject.of(_value));
		}
	
	}

}
