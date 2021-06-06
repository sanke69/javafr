package fr.java.graph.impl.layouts.base;

import fr.java.graph.GTEdge;
import fr.java.graph.viewer.GTLayout;

public abstract class GTEdgeLayoutBase implements GTLayout.Edge {

	private final GTEdge 		model;
	private final GTLayout.Node source, target;

	public GTEdgeLayoutBase(GTEdge _edge, GTLayout.Node _sourceLayout, GTLayout.Node _targetLayout) {
		super();
		model  = _edge;
		source = _sourceLayout;
		target = _targetLayout;
	}

	@Override
	public final GTEdge 	getModel() {
		return model;
	}

	@Override
	public GTLayout.Node 	getSource() {
		return source;
	}
	@Override
	public GTLayout.Node 	getTarget() {
		return target;
	}

}
