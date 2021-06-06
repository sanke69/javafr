package fr.java.graph.impl.layouts.base;

import fr.java.graph.GTGate;
import fr.java.graph.viewer.GTLayout;

public abstract class GTGateLayoutBase implements GTLayout.Gate {

	private final GTGate 		model;
	private final GTLayout.Node owner;

	public GTGateLayoutBase(GTGate _gate, GTLayout.Node _ownerLayout) {
		super();
		model = _gate;
		owner = _ownerLayout;
	}

	@Override
	public final GTGate 	getModel() {
		return model;
	}

	@Override
	public GTLayout.Node 	getOwner() {
		return owner;
	}

}
