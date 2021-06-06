package fr.java.graph.viewer.utils;

import java.util.Collection;

import fr.java.graph.viewer.GTLayout;
import fr.java.lang.properties.ID;

public interface GTLayoutRegister<NL extends GTLayout.Node, EL extends GTLayout.Edge, GL extends GTLayout.Gate> {

    // NODE Related Methods
    // ====================
	public Collection<NL>	getNodeLayouts			();

	public NL				getNodeLayout			(ID _nodeId);
	public void 			registerNodeLayout		(ID _nodeId, NL _nodeLayout);
	public void 			unregisterNodeLayout	(NL _nodeLayout);

    // EDGE Related Methods
    // ====================
	public Collection<EL>	getEdgeLayouts			();

	public EL				getEdgeLayout			(ID _edgeId);
	public void 			registerEdgeLayout		(ID _edgeId, EL _edgeLayout);
	public void 			unregisterEdgeLayout	(EL _edgeLayout);

    // GATE Related Methods
    // ====================
	public Collection<GL>	getGateLayouts			();

	public GL				getGateLayout			(ID _gateId);
	public void 			registerGateLayout		(ID _gateId, GL _gateLayout);
	public void 			unregisterGateLayout	(GL _gateLayout);

}
