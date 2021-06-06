package fr.java.graph.viewer;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.lang.properties.ID;
import fr.java.patterns.displayable.Moveable;
import fr.java.patterns.displayable.Resizeable;
import fr.java.patterns.displayable.Selectable;
import fr.java.patterns.identifiable.Identifiable;
import fr.java.patterns.mvc.Viewable;

public interface GTLayout extends Identifiable 
								  // , Viewable
								  // , Selectable
								  // , Moveable.TwoDims
								  // , Resizeable.TwoDims
								  {

	public interface Node extends GTLayout {
		public GTNode getModel();
	}
	public interface NodeGenerator<N extends GTLayout.Node> {
		N 						create(GTNode _nodeModel);
	}

	public interface Edge extends GTLayout {
		public GTEdge			getModel();
	
		public GTLayout.Node 	getSource();
		public GTLayout.Node 	getTarget();
	}
	public interface EdgeGenerator<N extends GTLayout.Node, E extends GTLayout.Edge> {
		E 						create(GTEdge _edgeModel, N _sourceView, N _targetView);
	}

	public interface Gate extends GTLayout {
		public GTGate 			getModel();

		public GTLayout.Node 	getOwner();
	}
	public interface GateGenerator<N extends GTLayout.Node, G extends GTLayout.Gate> {
		G 						create(GTGate _gateModel, N _ownerLayout);
	}

	@Override
	public default ID 			getId() 		{ return getModel().getId(); }
	public Identifiable			getModel();

}
