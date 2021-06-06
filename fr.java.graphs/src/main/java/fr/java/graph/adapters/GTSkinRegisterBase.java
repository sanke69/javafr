package fr.java.graph.adapters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.graph.viewer.utils.GTSkinRegister;
import fr.java.lang.exceptions.AlreadyExistingEntry;
import fr.java.lang.properties.ID;

public class GTSkinRegisterBase<GO,  NL extends GTLayout.Node, 
									 EL extends GTLayout.Edge, 
									 GL extends GTLayout.Gate> implements GTSkinRegister<GO, NL, EL, GL> {

	final Map<ID, GTSkin<GO, GTNode, NL>> 	nodeSkins;
	final Map<ID, GTSkin<GO, GTEdge, EL>> 	edgeSkins;
	final Map<ID, GTSkin<GO, GTGate, GL>> 	gateSkins;

	public GTSkinRegisterBase() {
		super();

		nodeSkins = new HashMap<ID, GTSkin<GO, GTNode, NL>>();
		edgeSkins = new HashMap<ID, GTSkin<GO, GTEdge, EL>>();
		gateSkins = new HashMap<ID, GTSkin<GO, GTGate, GL>>();
	}

	@Override
	public Collection<GTSkin<GO, GTNode, NL>> 	getNodeSkins() {
		return nodeSkins.values();
	}
	@Override
	public Collection<GTSkin<GO, GTEdge, EL>> 	getEdgeSkins() {
		return edgeSkins.values();
	}
	@Override
	public Collection<GTSkin<GO, GTGate, GL>> 	getGateSkins() {
		return gateSkins.values();
	}

	@Override
	public GTSkin<GO, GTNode, NL>			  	getNodeSkin(ID _nodeId) {
		return nodeSkins.get(_nodeId);
	}
	@Override
	public void 								registerNodeSkin(ID _nodeId, GTSkin<GO, GTNode, NL> _skin) {	
		if(nodeSkins.containsKey(_nodeId))
			throw new AlreadyExistingEntry();

		nodeSkins.put(_nodeId, _skin);
	}
	@Override
	public void 								unregisterNodeSkin(ID _nodeId) {
		if(!nodeSkins.containsKey(_nodeId))
			throw new NullPointerException();

		nodeSkins.remove(_nodeId);
	}

	@Override
	public GTSkin<GO, GTEdge, EL>				getEdgeSkin(ID _edgeId) {
		return edgeSkins.get(_edgeId);
	}
	@Override
	public void 								registerEdgeSkin(ID _edgeId, GTSkin<GO, GTEdge, EL> _skin) {
		if(edgeSkins.containsKey(_edgeId))
			throw new AlreadyExistingEntry();

		edgeSkins.put(_edgeId, _skin);
	}
	@Override
	public void 								unregisterEdgeSkin(ID _edgeId) {
		if(!edgeSkins.containsKey(_edgeId))
			throw new NullPointerException();

		edgeSkins.remove(_edgeId);
	}

	@Override
	public GTSkin<GO, GTGate, GL>				getGateSkin(ID _gateId) {
		return gateSkins.get(_gateId);
	}
	@Override
	public void 								registerGateSkin(ID _gateId, GTSkin<GO, GTGate, GL> _skin) {
		if(gateSkins.containsKey(_gateId))
			throw new AlreadyExistingEntry();

		gateSkins.put(_gateId, _skin);
	}
	@Override
	public void 								unregisterGateSkin(ID _gateId) {
		if(!gateSkins.containsKey(_gateId))
			throw new NullPointerException();

		gateSkins.remove(_gateId);
	}

}
