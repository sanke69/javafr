package fr.java.graph.impl.layouts.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.utils.GTLayoutFactory;

public abstract class GTLayoutFactoryBase<NL extends GTLayout.Node, EL extends GTLayout.Edge, GL extends GTLayout.Gate> 
				implements GTLayoutFactory<NL, EL, GL> {

	private final Class<NL>									nodeLayoutRootClass;
	private final GTLayout.NodeGenerator<? extends NL> 		nodeLayoutGenerator;
	private final GTLayout.EdgeGenerator<NL, ? extends EL>	edgeLayoutGenerator;
	private final GTLayout.GateGenerator<NL, ? extends GL>	gateLayoutGenerator;

	public GTLayoutFactoryBase(Class<NL> _nodeLayoutRoot, Class<? extends NL> _nodeLayoutImpl, Class<? extends EL> _edgeLayoutImpl, Class<? extends GL> _gateLayoutImpl) {
		super();

		nodeLayoutRootClass = _nodeLayoutRoot;
		nodeLayoutGenerator = getNodeLayoutGenerator(_nodeLayoutImpl);
		edgeLayoutGenerator = getEdgeLayoutGenerator(_edgeLayoutImpl);
		gateLayoutGenerator = getGateLayoutGenerator(_gateLayoutImpl);

	}

	@Override
	public NL 								createNodeLayout(GTNode _node) {
		return nodeLayoutGenerator.create(_node);
	}
	@Override
	public EL 								createEdgeLayout(GTEdge _edge, NL _sourceLayout, NL _targetLayout) {
		return edgeLayoutGenerator.create(_edge, _sourceLayout, _targetLayout);
	}
	@Override
	public GL								createGateLayout(GTGate _gate, NL _ownerLayout) {
		return gateLayoutGenerator.create(_gate, _ownerLayout);
	}

	private GTLayout.NodeGenerator<NL> 		getNodeLayoutGenerator(Class<? extends NL> _nodeLayoutImpl) {
		GTLayout.NodeGenerator<NL> nodeLayoutGenerator = null;
		try {
			final Constructor<? extends NL> ctor = _nodeLayoutImpl.getConstructor(GTNode.class);

			nodeLayoutGenerator = node -> {
				try {
					return ctor.newInstance(node);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			};

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return nodeLayoutGenerator;
	}
	private GTLayout.EdgeGenerator<NL, EL> 	getEdgeLayoutGenerator(Class<? extends EL> _edgeLayoutImpl) {
		GTLayout.EdgeGenerator<NL, EL> edgeLayoutGenerator = null;
		try {
			final Constructor<? extends EL> ctor = _edgeLayoutImpl.getConstructor(GTEdge.class, nodeLayoutRootClass, nodeLayoutRootClass);

			edgeLayoutGenerator = (edge, s, t) -> {
				try {
					return ctor.newInstance(edge, s, t);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			};

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return edgeLayoutGenerator;
	}
	private GTLayout.GateGenerator<NL, GL> 	getGateLayoutGenerator(Class<? extends GL> _gateLayoutImpl) {
		GTLayout.GateGenerator<NL, GL> gateLayoutGenerator = null;
		try {
			final Constructor<? extends GL> ctor = _gateLayoutImpl.getConstructor(GTGate.class, nodeLayoutRootClass);

			gateLayoutGenerator = (gate, owner) -> {
				try {
					return ctor.newInstance(gate, owner);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			};

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return gateLayoutGenerator;
	}

}
