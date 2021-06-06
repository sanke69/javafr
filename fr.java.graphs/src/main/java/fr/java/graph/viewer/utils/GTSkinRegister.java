package fr.java.graph.viewer.utils;

import java.util.Collection;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.lang.properties.ID;

public interface GTSkinRegister<GO, NL extends GTLayout.Node, 
								  	EL extends GTLayout.Edge, 
								  	GL extends GTLayout.Gate> {

//	public GO									getGraphics(GTSkin<GO, ?, ?> _view);
//	public void 								registerGraphics(GTSkin<GO, ?, ?> _view, GO _graphics);
//	public void 								unregisterGraphics(GTSkin<GO, ?, ?> _view);

    // NODE Related Methods
    // ====================
	public Collection<GTSkin<GO, GTNode, NL>> 	getNodeSkins		();

	public GTSkin<GO, GTNode, NL>	 			getNodeSkin			(ID _nodeId);
	public void       							registerNodeSkin	(ID _nodeId, GTSkin<GO, GTNode, NL> _view);
	public void       							unregisterNodeSkin	(ID _nodeId);

    // EDGE Related Methods
    // ====================
	public Collection<GTSkin<GO, GTEdge, EL>> 	getEdgeSkins		();

	public GTSkin<GO, GTEdge, EL>				getEdgeSkin			(ID _edgeId);
	public void       							registerEdgeSkin	(ID _nodeId, GTSkin<GO, GTEdge, EL> _view);
	public void       							unregisterEdgeSkin	(ID _edgeId);

    // GATE Related Methods
    // ====================
	public Collection<GTSkin<GO, GTGate, GL>> 	getGateSkins		();

	public GTSkin<GO, GTGate, GL>	 			getGateSkin			(ID _gateId);
	public void       							registerGateSkin	(ID _nodeId, GTSkin<GO, GTGate, GL> _view);
	public void       							unregisterGateSkin	(ID _gateId);

}
