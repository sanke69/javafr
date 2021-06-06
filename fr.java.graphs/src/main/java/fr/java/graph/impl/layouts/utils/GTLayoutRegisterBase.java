package fr.java.graph.impl.layouts.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTViewer;
import fr.java.graph.viewer.utils.GTLayoutRegister;
import fr.java.lang.exceptions.AlreadyExistingEntry;
import fr.java.lang.properties.ID;

public abstract class GTLayoutRegisterBase<NL extends GTLayout.Node, EL extends GTLayout.Edge, GL extends GTLayout.Gate> implements GTLayoutRegister<NL, EL, GL> {
	private final Map<ID, NL> nodeLayouts = new HashMap<ID, NL>();
	private final Map<ID, EL> edgeLayouts = new HashMap<ID, EL>();
	private final Map<ID, GL> gateLayouts = new HashMap<ID, GL>();

	public GTLayoutRegisterBase() {
		super();
	}

	@Override
	public Collection<NL> 			getNodeLayouts() {
		return nodeLayouts.values();
	}
	@Override
	public NL 						getNodeLayout(ID _nodeId) {
		return nodeLayouts.get(_nodeId);
	}
	@Override
	public void 					registerNodeLayout(ID _nodeId, NL _nodeLayout) {
		if(nodeLayouts.containsKey(_nodeLayout.getId()))
			throw new AlreadyExistingEntry();
		
		nodeLayouts.put(_nodeLayout.getId(), _nodeLayout);
	}
	@Override
	public void 					unregisterNodeLayout(NL _nodeLayout) {
		if(!nodeLayouts.containsKey(_nodeLayout.getId()))
			throw new NullPointerException();
		
		nodeLayouts.remove(_nodeLayout.getId());
	}

	@Override
	public Collection<EL> 			getEdgeLayouts() {
		return edgeLayouts.values();
	}
	@Override
	public EL 						getEdgeLayout(ID _edgeId) {
		return edgeLayouts.get(_edgeId);
	}
	@Override
	public void 					registerEdgeLayout(ID _edgeId, EL _edgeLayout) {
		if(edgeLayouts.containsKey(_edgeLayout.getId()))
			throw new AlreadyExistingEntry();
		
		edgeLayouts.put(_edgeLayout.getId(), _edgeLayout);
	}
	@Override
	public void 					unregisterEdgeLayout(EL _edgeLayout) {
		if(!edgeLayouts.containsKey(_edgeLayout.getId()))
			throw new NullPointerException();
		
		edgeLayouts.remove(_edgeLayout.getId());
	}

	@Override
	public Collection<GL> 			getGateLayouts() {
		return gateLayouts.values();
	}
	@Override
	public GL 						getGateLayout(ID _gateId) {
		if(!nodeLayouts.containsKey(_gateId))
			throw new NullPointerException();
		
		return gateLayouts.get(_gateId);
	}
	@Override
	public void 					registerGateLayout(ID _gateId, GL _gateLayout) {
		if(gateLayouts.containsKey(_gateId))
			throw new AlreadyExistingEntry();
		
		gateLayouts.put(_gateId, _gateLayout);
	}
	@Override
	public void 					unregisterGateLayout(GL _gateLayout) {
		if(!gateLayouts.containsKey(_gateLayout.getId()))
			throw new NullPointerException();
		
		gateLayouts.remove(_gateLayout.getId());
	}

	/**
	 * Added   nodes refers to existing nodes in Model without View
	 * Removed nodes refers to existing views in View  without models in Model
	 * @author sanke
	 *
	 */
	public class LayoutInstanceUpdater {
		List<ID>	addedNodes, removedNodes;
		List<ID>	addedGates, removedGates;
		List<ID>	addedEdges, removedEdges;
	
		public LayoutInstanceUpdater() {
			super();
			addedNodes   = new ArrayList<ID>();
			addedGates   = new ArrayList<ID>();
			addedEdges   = new ArrayList<ID>();
			removedNodes = new ArrayList<ID>();
			removedGates = new ArrayList<ID>();
			removedEdges = new ArrayList<ID>();
		}
	
		/**
		 * 
		 * @return true, if modifications
		 */
		public  boolean 		process(GTViewer<NL, EL, GL> _viewer) {
			GTGraph graph = _viewer.getModel();
	
			addedNodes.clear();	removedNodes.clear();
			addedEdges.clear();	removedEdges.clear();
	
			Collection<ID> nodes         = graph.getNodes().stream().map(GTNode::getId)
																    .collect(Collectors.toSet());
			Collection<ID> nodeWithViews = _viewer.getLayoutRegister().getNodeLayouts()
																    .stream()
																    .map(GTLayout::getId)
																    .collect(Collectors.toSet());
	
			Collection<ID> edges         = graph.getEdges().stream().map(GTEdge::getId)
				    												.collect(Collectors.toSet());
			Collection<ID> edgeWithViews = _viewer.getLayoutRegister().getEdgeLayouts()
																    .stream()
																    .map(GTLayout::getId)
																    .collect(Collectors.toSet());
	
			addedNodes   . addAll(deltaObjects(nodes, nodeWithViews));
			removedNodes . addAll(deltaObjects(nodeWithViews, nodes));
	
			addedEdges   . addAll(deltaObjects(edges, edgeWithViews));
			removedEdges . addAll(deltaObjects(edgeWithViews, edges));
	
			boolean changed = !addedNodes.isEmpty() || !removedNodes.isEmpty()|| !addedEdges.isEmpty() || !removedEdges.isEmpty();
	
			if(!(_viewer.getModel() instanceof GTGraph.Gated))
				return changed;
	
			return changed || processWithGates(_viewer);
		}
		private boolean 		processWithGates(GTViewer<NL, EL, GL> _viewer) {	
			GTGraph.Gated graph = (GTGraph.Gated) _viewer.getModel();
	
			addedGates.clear();	removedGates.clear();
	
			Collection<ID> gates         = graph.getNodes().stream().map(GTNode::getId)
																    .collect(Collectors.toSet());
			Collection<ID> gateWithViews = _viewer.getLayoutRegister().getNodeLayouts()
																    .stream()
																    .map(GTLayout::getId)
																    .collect(Collectors.toSet());
	
			addedGates   . addAll(deltaObjects(gates, gateWithViews));
			removedGates . addAll(deltaObjects(gateWithViews, gates));
	
			return !addedGates.isEmpty() || !removedGates.isEmpty();
		}
	
		public Collection<ID> 	getAddedNodeIds() {
			return addedNodes;
		}
		public Collection<ID> 	getRemovedNodeIds() {
			return removedNodes;
		}
	
		public Collection<ID> 	getAddedGateIds() {
			return addedGates;
		}
		public Collection<ID> 	getRemovedGateIds() {
			return removedGates;
		}
	
		public Collection<ID> 	getAddedEdgeIds() {
			return addedEdges;
		}
		public Collection<ID> 	getRemovedEdgeIds() {
			return removedEdges;
		}
		
		private Collection<ID> 	deltaObjects(Collection<ID> _testedCollection, Collection<ID> _referenceCollection) {
			Collection<ID> diff = new ArrayList<ID>();
	
			_testedCollection.stream().forEach(id -> {
				if(!_referenceCollection.contains(id))
					diff.add(id);
			});
	
			return diff;
		}
	
	}

}
