package fr.java.graph;

import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.capabilities.Identifiable;
import fr.java.patterns.composite.Component;
import fr.java.patterns.valueable.Valueable;

public interface GTEdge extends Component.Single, Identifiable, Valueable {

	@Override
	public default GTGraph 								getParent() {
		return getGraph();
	}
	public GTGraph										getGraph();

	public Pair<ID, ID>									getApexNodeIds();
	public Pair<? extends GTNode, ? extends GTNode>		getApexNodes();

	public static interface Undirected extends GTEdge {

		@Override
		public default GTGraph.Undirected 				getParent() {
			return getGraph();
		}
		@Override
		public GTGraph.Undirected 						getGraph();

		public Pair<? extends GTNode.Undirected, ? extends GTNode.Undirected>
														getApexNodes();

	}
	public static interface Directed   extends GTEdge {

		@Override
		public default GTGraph.Directed		 			getParent() {
			return getGraph();
		}
		@Override
		public GTGraph.Directed 						getGraph();

		public Pair<? extends GTNode.Directed, ? extends GTNode.Directed>
														getApexNodes();

		public GTNode.Directed							getSource();
		public GTNode.Directed							getDestination();

	}
	public static interface Gated      extends GTEdge {

		@Override
		public default GTGraph.Gated					getParent() {
			return getGraph();
		}
		@Override
		public GTGraph.Gated 							getGraph();

		public Pair<? extends GTNode.Gated, ? extends GTNode.Gated>
														getApexNodes();

		public Pair<ID, ID>								getApexGateIds();
		public Pair<? extends GTGate, ? extends GTGate>	getApexGates();

		public Pair<ID, ID>								getSenderIds();
		public GTNode.Gated								getSenderNode();
		public GTGate 									getSenderGate();

		public Pair<ID, ID>								getReceiverIds();
		public GTNode.Gated								getReceiverNode();
		public GTGate 									getReceiverGate();

	}
	
}
