package fr.java.graph.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.layouts.GTLayoutGenerator;
import fr.java.graph.impl.layouts.GTViewerLayout;
import fr.java.graph.impl.layouts.planar.GTEdgeLayout2DAdapter;
import fr.java.graph.impl.layouts.planar.GTGateLayout2DAdapter;
import fr.java.graph.impl.layouts.planar.GTNodeLayout2DAdapter;
import fr.java.graph.impl.layouts.utils.GTLayoutFactoryBase;
import fr.java.graph.impl.layouts.utils.GTLayoutRegisterBase;
import fr.java.graph.layouts.GTEdgeLayout2D;
import fr.java.graph.layouts.GTGateLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.graph.viewer.GTView;
import fr.java.graph.viewer.GTViewer;
import fr.java.graph.viewer.utils.GTLayoutFactory;
import fr.java.graph.viewer.utils.GTLayoutRegister;
import fr.java.graph.viewer.utils.GTSkinFactory;
import fr.java.graph.viewer.utils.GTSkinRegister;
import fr.java.lang.properties.ID;

public abstract class GTViewerBase<GO, NL extends GTLayout.Node, 
									   EL extends GTLayout.Edge, 
									   GL extends GTLayout.Gate> extends GTViewerLayout<NL, EL, GL> implements GTViewer.Graphics<GO, NL, EL, GL> {

	public static final Supplier<GTLayoutFactory<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>> layout2DFactory = () -> {
		return new GTLayoutFactoryBase<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>
				   (GTNodeLayout2D.class, GTNodeLayout2DAdapter.class, GTEdgeLayout2DAdapter.class, GTGateLayout2DAdapter.class)
				   {};
	};
	public static final Supplier<GTLayoutRegister<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>> layout2DRegister = () -> {
		return new GTLayoutRegisterBase<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>
				   ()
				   {};
	};

	protected GTGraph 						model;
	final GTSkinFactory<GO, NL, EL, GL>  	skinFactory;
	final GTSkinRegister<GO, NL, EL, GL> 	skinRegister;

	final Map<ID, GTView<GO, GTNode, NL>> 		nodeViewRegister;
	final Map<ID, GTView<GO, GTEdge, EL>> 		edgeViewRegister;
	final Map<ID, GTView<GO, GTGate, GL>> 		gateViewRegister;
	final Map<GTSkin<GO, ?, ?>, GO> 			graphics;

	public GTViewerBase(GTGraph _graph, 
						GTLayoutFactory<NL, EL, GL>   _layoutFactory, GTLayoutRegister<NL, EL, GL>   _layoutRegister, 
						GTSkinFactory<GO, NL, EL, GL> _skinFactory,   GTSkinRegister<GO, NL, EL, GL> _skinRegister) {
		super(_graph, _layoutFactory, _layoutRegister);

		skinFactory = _skinFactory;
		skinRegister = _skinRegister;

		graphics         = new HashMap<GTSkin<GO, ?, ?>, GO>();
		nodeViewRegister = new HashMap<ID, GTView<GO, GTNode, NL>>();
		edgeViewRegister = new HashMap<ID, GTView<GO, GTEdge, EL>>();
		gateViewRegister = new HashMap<ID, GTView<GO, GTGate, GL>>();
	}

	public final void								setModel(GTGraph _graph) {
		model = _graph;
		update();
	}
	@Override
	public final GTGraph							getModel() {
		return model;
	}
	@Override
	public final GTGraph.Gated 						getModelGated() {
		if(GTGraph.Gated.class.isAssignableFrom(model.getClass()))
			return (GTGraph.Gated) model;

		throw new IllegalArgumentException();
	}

	@Override
	public GTSkinFactory<GO, NL, EL, GL> 			getSkinFactory() {
		return skinFactory;
	}
	@Override
	public GTSkinRegister<GO, NL, EL, GL> 			getSkinRegister() {
		return skinRegister;
	}

	@Override
	public Collection<GTView<GO, GTNode, NL>> 		getNodeViews() {
		return nodeViewRegister.values();
	}
	@Override
	public GTView<GO, GTNode, NL> 					getNodeView(ID _nodeId) {
		if(nodeViewRegister.containsKey(_nodeId))
			return nodeViewRegister.get(_nodeId);

		GTNode node = getModel().getNode(_nodeId);
		
		NL layout = getLayoutRegister().getNodeLayout(_nodeId);
		if(layout == null) {
			layout = getLayoutFactory().createNodeLayout(node);
			if(layout != null)
				getLayoutRegister().registerNodeLayout(_nodeId, layout);
		}

		GTSkin<GO, GTNode, NL> skin = getSkinRegister().getNodeSkin(_nodeId);
		if(skin == null) {
			skin = getSkinFactory().createNodeSkin(node, layout);
			if(skin != null)
				getSkinRegister().registerNodeSkin(_nodeId, skin);
		}

		GTView<GO, GTNode, NL> view = new GTViewProxy<GO, GTNode, NL>(node, layout, skin);
		
		nodeViewRegister.put(_nodeId, view);

		return view;
	}

	@Override
	public Collection<GTView<GO, GTEdge, EL>> 		getEdgeViews() {
		return edgeViewRegister.values();
	}
	@Override
	public GTView<GO, GTEdge, EL>					getEdgeView(ID _edgeId) {
		if(edgeViewRegister.containsKey(_edgeId))
			return edgeViewRegister.get(_edgeId);

		GTEdge edge = getModel().getEdge(_edgeId);

		EL layout = getLayoutRegister().getEdgeLayout(_edgeId);
		if(layout == null) {
			NL srcLayout = getNodeLayout(edge.getApexNodeIds().getFirst());
			NL tgtLayout = getNodeLayout(edge.getApexNodeIds().getSecond());

			layout = getLayoutFactory().createEdgeLayout(edge, srcLayout, tgtLayout);
			if(layout != null)
				getLayoutRegister().registerEdgeLayout(_edgeId, layout);
		}

		GTSkin<GO, GTEdge, EL> skin = getSkinRegister().getEdgeSkin(_edgeId);
		if(skin == null) {
			skin = getSkinFactory().createEdgeSkin(edge, layout);
			if(skin != null)
				getSkinRegister().registerEdgeSkin(_edgeId, skin);
		}

		GTView<GO, GTEdge, EL> view = new GTViewProxy<GO, GTEdge, EL>(edge, layout, skin);
		
		edgeViewRegister.put(_edgeId, view);

		return view;
	}

	@Override
	public Collection<GTView<GO, GTGate, GL>> 		getGateViews() {
		return gateViewRegister.values();
	}

	@Override
	public GTView<GO, GTGate, GL> 					getGateView(ID _gateId) {
		if(gateViewRegister.containsKey(_gateId))
			return gateViewRegister.get(_gateId);

		GTGate gate = getModelGated().getGate(_gateId);

		GL layout = getLayoutRegister().getGateLayout(_gateId);
		if(layout == null) {
			NL ownLayout = getNodeLayout(gate.getOwner().getId());

			layout = getLayoutFactory().createGateLayout(gate, ownLayout);
			if(layout != null)
				getLayoutRegister().registerGateLayout(_gateId, layout);
		}

		GTSkin<GO, GTGate, GL> skin = getSkinRegister().getGateSkin(_gateId);
		if(skin == null) {
			skin = getSkinFactory().createGateSkin(gate, layout);
			if(skin != null)
				getSkinRegister().registerGateSkin(_gateId, skin);
		}

		GTView<GO, GTGate, GL> view = new GTViewProxy<GO, GTGate, GL>(gate, layout, skin);
		
		gateViewRegister.put(_gateId, view);

		return view;
	}

	@Override
	public GO 										createGraphics(GTSkin<GO, ?, ?> _skin) {
		if(GTSkin.class.isAssignableFrom(_skin.getClass())) {
			GO viewGo = _skin.getGraphics(this);
			graphics.put(_skin, viewGo);
			return viewGo;
		}
		throw new IllegalArgumentException();
	}
	@Override
	public GO 										getGraphics(GTSkin<GO, ?, ?> _skin) {
		GO gObject = graphics.get(_skin);

		if(gObject != null)
			return gObject;

		gObject = createGraphics(_skin);

		return gObject;
	}
	@Override
	public GO 										releaseGraphics(GTSkin<GO, ?, ?> _skin) {
		return graphics.remove(_skin);
	}

	public void 									update() {
		ViewInstanceUpdater updater = new ViewInstanceUpdater();
		if(getModel() != null)
			updater.process(this);

		List<GTView<GO, GTNode, NL>> newNodeViews = updater.getAddedNodeIds()
												   .stream()
												   .map(id -> getNodeView(id))
												   .collect(Collectors.toList());

		List<GTView<GO, GTEdge, EL>> newEdgeViews = updater.getAddedEdgeIds()
												   .stream()
												   .map(id -> getEdgeView(id))
												   .collect(Collectors.toList());

		List<GTView<GO, GTEdge, EL>> oldEdgeViews = updater.getRemovedEdgeIds()
												   .stream()
												   .map(id -> edgeViewRegister.get(id))
												   .collect(Collectors.toList());

		List<GTView<GO, GTNode, NL>> oldNodeViews = updater.getRemovedNodeIds()
												   .stream()
												   .map(id -> nodeViewRegister.get(id))
												   .collect(Collectors.toList());

		// remove components to graph pane
		removeEdges(oldEdgeViews);
		removeNodes(oldNodeViews);

		// add components to graph pane
		addEdges(newEdgeViews);
		addNodes(newNodeViews);
	}
	public void 									layout(GTLayoutGenerator<GTViewer<NL, EL, GL>> layout) {
		layout.execute(this);
	}

	protected abstract void 						addNodes(List<GTView<GO, GTNode, NL>> cells);
	protected abstract void 						removeNodes(List<GTView<GO, GTNode, NL>> cells);

	protected abstract void 						addEdges(List<GTView<GO, GTEdge, EL>> _edgeViews);
	protected abstract void 						removeEdges(List<GTView<GO, GTEdge, EL>> edges);


	public class ViewInstanceUpdater {
		List<ID>	addedNodes, removedNodes;
		List<ID>	addedGates, removedGates;
		List<ID>	addedEdges, removedEdges;
	
		public ViewInstanceUpdater() {
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
