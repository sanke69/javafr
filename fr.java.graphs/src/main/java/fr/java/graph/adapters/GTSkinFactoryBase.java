package fr.java.graph.adapters;

import java.util.HashMap;
import java.util.Map;

import fr.java.graph.GT;
import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.graph.viewer.utils.GTSkinFactory;
import fr.java.lang.exceptions.AlreadyExistingEntry;

public abstract class GTSkinFactoryBase<GO, NL extends GTLayout.Node, 
											EL extends GTLayout.Edge, 
											GL extends GTLayout.Gate> implements GTSkinFactory<GO, NL, EL, GL> {

	final Class<NL> 										nodeLayoutClass;
	final Map<Class<?>, GTSkin.Generator<GO, GTNode, NL>> 	nodeSkinSuppliers;
	final Class<EL> 										edgeLayoutClass;
	final Map<Class<?>, GTSkin.Generator<GO, GTEdge, EL>> 	edgeSkinSuppliers;
	final Class<GL> 										gateLayoutClass;
	final Map<Class<?>, GTSkin.Generator<GO, GTGate, GL>> 	gateSkinSuppliers;

	public GTSkinFactoryBase(Class<NL> _nodeLayoutClass, Class<EL> _edgeLayoutClass, Class<GL> _gateLayoutClass) {
		super();

		nodeLayoutClass   = _nodeLayoutClass;
		nodeSkinSuppliers = new HashMap<Class<?>, GTSkin.Generator<GO, GTNode, NL>>();
		edgeLayoutClass   = _edgeLayoutClass;
		edgeSkinSuppliers = new HashMap<Class<?>, GTSkin.Generator<GO, GTEdge, EL>>();
		gateLayoutClass   = _gateLayoutClass;
		gateSkinSuppliers = new HashMap<Class<?>, GTSkin.Generator<GO, GTGate, GL>>();
	}

	@Override
	public void 								registerNodeSkinClass(Class<?> _modelClass, Class<? extends GTSkin<GO, GTNode, NL>> _skinClass) {
		if(nodeSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		nodeSkinSuppliers.put(_modelClass, GT.NodeViewSupplier.of(nodeLayoutClass, _skinClass));
	}
	@Override
	public void 								registerEdgeSkinClass(Class<?> _modelClass, Class<? extends GTSkin<GO, GTEdge, EL>> _skinClass) {
		if(edgeSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		edgeSkinSuppliers.put(_modelClass, GT.EdgeViewSupplier.of(edgeLayoutClass, _skinClass));
	}
	@Override
	public void 								registerGateSkinClass(Class<?> _modelClass, Class<? extends GTSkin<GO, GTGate, GL>> _skinClass) {
		if(gateSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		gateSkinSuppliers.put(_modelClass, GT.GateViewSupplier.of(gateLayoutClass, _skinClass));
	}

	@Override
	public void 								registerNodeSkinClass(Class<?> _modelClass, Class<? extends GTSkin<GO, GTNode, NL>> _viewClass, Object... _viewCtorArgs) {
		if(nodeSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		nodeSkinSuppliers.put(_modelClass, GT.NodeViewSupplier.of(nodeLayoutClass, _viewClass, _viewCtorArgs));
	}
	@Override
	public void 								registerEdgeSkinClass(Class<?> _modelClass, Class<? extends GTSkin<GO, GTEdge, EL>> _viewClass, Object... _viewCtorArgs) {
		if(edgeSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		edgeSkinSuppliers.put(_modelClass, GT.EdgeViewSupplier.of(edgeLayoutClass, _viewClass, _viewCtorArgs));
	}
	@Override
	public void 								registerGateSkinClass(Class<?> _modelClass, Class<? extends GTSkin<GO, GTGate, GL>> _viewClass, Object... _viewCtorArgs) {
		if(gateSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		gateSkinSuppliers.put(_modelClass, GT.GateViewSupplier.of(gateLayoutClass, _viewClass, _viewCtorArgs));
	}

	@Override
	public void 								registerNodeSkinSupplier(Class<?> _modelClass, GTSkin.Generator<GO, GTNode, NL> _viewClass) {
		if(nodeSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		nodeSkinSuppliers.put(_modelClass, _viewClass);
	}
	@Override
	public void 								registerEdgeSkinSupplier(Class<?> _modelClass, GTSkin.Generator<GO, GTEdge, EL> _viewClass) {
		if(edgeSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		edgeSkinSuppliers.put(_modelClass, _viewClass);
	}
	@Override
	public void 								registerGateSkinSupplier(Class<?> _modelClass, GTSkin.Generator<GO, GTGate, GL> _viewClass) {
		if(gateSkinSuppliers.containsKey(_modelClass))
			throw new AlreadyExistingEntry();
		gateSkinSuppliers.put(_modelClass, _viewClass);
	}

	@Override
	public GTSkin<GO, GTNode, NL>				createNodeSkin(GTNode _node, NL _layout) {
		return __getNodeSkinSupplier(_node.getValue()).newInstance(_node, _layout);
	}
	@Override
	public GTSkin<GO, GTEdge, EL>				createEdgeSkin(GTEdge _edge, EL _layout) {
		return __getEdgeSkinSupplier(_edge.getValue()).newInstance(_edge, _layout);
	}
	@Override
	public GTSkin<GO, GTGate, GL>				createGateSkin(GTGate _gate, GL _layout) {
		return __getGateSkinSupplier(_gate.getValue()).newInstance(_gate, _layout);
	}

	private GTSkin.Generator<GO, GTNode, NL> 	__getNodeSkinSupplier(Object _node) {
		GTSkin.Generator<GO, GTNode, NL> viewSupplier = nodeSkinSuppliers.get(_node != null ? _node.getClass() : null);
		return viewSupplier != null ? viewSupplier : nodeSkinSuppliers.get(null);
	}
	private GTSkin.Generator<GO, GTEdge, EL> 	__getEdgeSkinSupplier(Object _edge) {
		GTSkin.Generator<GO, GTEdge, EL> viewSupplier = edgeSkinSuppliers.get(_edge != null ? _edge.getClass() : null);
		return viewSupplier != null ? viewSupplier : edgeSkinSuppliers.get(null);
	}
	private GTSkin.Generator<GO, GTGate, GL> 	__getGateSkinSupplier(Object _gate) {
		GTSkin.Generator<GO, GTGate, GL> viewSupplier = gateSkinSuppliers.get(_gate != null ? _gate.getClass() : null);
		return viewSupplier != null ? viewSupplier : gateSkinSuppliers.get(null);
	}



}
