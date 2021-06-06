package fr.java.graph.viewer;

import java.util.Collection;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.utils.GTLayoutFactory;
import fr.java.graph.viewer.utils.GTLayoutRegister;
import fr.java.graph.viewer.utils.GTSkinFactory;
import fr.java.graph.viewer.utils.GTSkinRegister;
import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.properties.ID;

public interface GTViewer<NL extends GTLayout.Node, EL extends GTLayout.Edge, GL extends GTLayout.Gate> {

	public GTGraph 									getModel();
	public GTGraph.Gated			 				getModelGated() throws IllegalCastException;

	public GTLayoutFactory<NL, EL, GL>				getLayoutFactory();
	public GTLayoutRegister<NL, EL, GL>				getLayoutRegister();

    public Collection<NL>							getNodeLayouts();
    public GTLayout.Node							getNodeLayout(ID _nodeId);

    public Collection<EL>							getEdgeLayouts();
    public GTLayout.Edge							getEdgeLayout(ID _edgeId);

    public Collection<GL>							getGateLayouts();
    public GTLayout.Gate							getGateLayout(ID _gateId);

	public interface Graphics<GO, NL extends GTLayout.Node, 
								  EL extends GTLayout.Edge, 
								  GL extends GTLayout.Gate> extends GTViewer<NL, EL, GL> {

	    // Use GTSkin to manage Renderer API aspects...
		public GTSkinFactory<GO, NL, EL, GL>		getSkinFactory();
		public GTSkinRegister<GO, NL, EL, GL>		getSkinRegister();

		public GO 									getGraphics		(GTSkin<GO, ?, ?> _skin);
		public GO 									createGraphics	(GTSkin<GO, ?, ?> _skin);
		public GO 									releaseGraphics	(GTSkin<GO, ?, ?> _skin);

		// From here, use GTView, Proxy of GTLayout and GTSkin !
	    public Collection<GTView<GO, GTNode, NL>>	getNodeViews();
	    public GTView<GO, GTNode, NL>				getNodeView(ID _nodeId);

	    public Collection<GTView<GO, GTEdge, EL>>	getEdgeViews();
	    public GTView<GO, GTEdge, EL>				getEdgeView(ID _edgeId);

	    public Collection<GTView<GO, GTGate, GL>>	getGateViews();
	    public GTView<GO, GTGate, GL>				getGateView(ID _gateId);

	}

}
