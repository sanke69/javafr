package fr.java.graph.impl.layouts;

import java.util.Collection;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTViewer;
import fr.java.graph.viewer.utils.GTLayoutFactory;
import fr.java.graph.viewer.utils.GTLayoutRegister;
import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;

public abstract class GTViewerLayout<NL extends GTLayout.Node, EL extends GTLayout.Edge, GL extends GTLayout.Gate> implements GTViewer<NL, EL, GL> {

	private final GTGraph 				       model;
	private final GTLayoutFactory<NL, EL, GL>  factory;
	private final GTLayoutRegister<NL, EL, GL> register;

	protected GTViewerLayout(GTGraph _graph, GTLayoutFactory<NL, EL, GL> _factory, GTLayoutRegister<NL, EL, GL> _register) {
		super();
		model = _graph;

		factory  = _factory;
		register = _register;
	}

	@Override
	public GTGraph 						getModel() {
		return model;
	}
	@Override
	public GTGraph.Gated 				getModelGated() throws IllegalCastException {
		if(!(model instanceof GTGraph.Gated))
			throw new IllegalCastException();

		return (GTGraph.Gated) model;
	}

	@Override
	public GTLayoutFactory<NL, EL, GL> 	getLayoutFactory() {
		return factory;
	}
	@Override
	public GTLayoutRegister<NL, EL, GL> getLayoutRegister() {
		return register;
	}

	@Override
	public Collection<NL> 				getNodeLayouts() {
		return register.getNodeLayouts();
	}
	@Override
	public NL 							getNodeLayout(ID _nodeId) {
		NL nodeView = register.getNodeLayout(_nodeId);

		if(nodeView == null) {
			GTNode node = model.getNode(_nodeId);
			nodeView    = factory.createNodeLayout(node);
			
			register.registerNodeLayout(_nodeId, nodeView);
		}

		return nodeView;
	}

	@Override
	public Collection<EL> 				getEdgeLayouts() {
		return register.getEdgeLayouts();
	}
	@Override
	public EL 							getEdgeLayout(ID _edgeId) {
		EL edgeView = register.getEdgeLayout(_edgeId);

		if(edgeView == null) {
			GTEdge edge         = model.getEdge(_edgeId);
			NL      sourceLayout = getNodeLayout(edge.getApexNodeIds().getFirst());
			NL      targetLayout = getNodeLayout(edge.getApexNodeIds().getSecond());

			edgeView    = factory.createEdgeLayout(edge, sourceLayout, targetLayout);
			
			register.registerEdgeLayout(_edgeId, edgeView);
		}

		return edgeView;
	}

	@Override
	public Collection<GL>  				getGateLayouts() {
		return register.getGateLayouts();
	}
	@Override
	public GL 							getGateLayout(ID _gateId) {
		GL gateView = register.getGateLayout(_gateId);

		if(gateView == null) {
			GTGate gate        = ((GTGraph.Gated) model).getGate(_gateId);
			NL      ownerLayout = getNodeLayout(gate.getOwner().getId());

			gateView    = factory.createGateLayout(gate, ownerLayout);
			
			register.registerGateLayout(_gateId, gateView);
		}

		return gateView;
	}

}
