package fr.java.graph.impl.layouts.base;

import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;

public abstract class GTNodeLayoutBase implements GTLayout.Node {

	private final GTNode model;

	public GTNodeLayoutBase(GTNode _node) {
		super();
		model = _node;
	}

	@Override
	public final GTNode getModel() {
		return model;
	}

}
