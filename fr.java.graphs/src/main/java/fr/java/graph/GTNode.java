package fr.java.graph;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.java.beans.impl.BeanPropertyList;
import fr.java.patterns.capabilities.Identifiable;
import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;
import fr.java.patterns.valueable.Valueable;

public interface GTNode extends Component.Single, Identifiable, Valueable.Editable {

	@Override
	public default GTGraph 						getParent() {
		return getGraph();
	}
	public GTGraph		 						getGraph();

	public int									getDegree();

	public boolean 								hasNeighbors();
    public Collection<? extends GTNode> 		getNeighbors();

	public boolean 								hasConnections();
    public Collection<? extends GTEdge> 		getConnections();

	public static interface Undirected extends GTNode {

		@Override
		public default GTGraph.Undirected 			getParent() {
			return getGraph();
		}
		@Override
		public GTGraph.Undirected 					getGraph();

		@Override
	    public Collection<? extends GTNode.Undirected> 	getNeighbors();

		@Override
	    public Collection<? extends GTEdge.Undirected> 	getConnections();

	}
	public static interface Directed extends GTNode {

		@Override
		public default GTGraph.Directed 							getParent() {
			return getGraph();
		}
		@Override
		public GTGraph.Directed 									getGraph();

		public int												getDegreeIn();
		public int												getDegreeOut();

		@Override
		public default boolean 									hasNeighbors			() {
			return hasPredecessors() || hasSuccessors();
		}
		@Override
	    public default Collection<? extends GTNode.Directed> 	getNeighbors			() {
			return Stream.concat(getPredecessors().stream(), getSuccessors().stream()).collect(Collectors.toList());
		}

		public boolean 											hasPredecessors			();
	    public Collection<? extends GTNode.Directed> 			getPredecessors			();

		public boolean 											hasSuccessors			();
	    public Collection<? extends GTNode.Directed> 			getSuccessors			();

		@Override
		public default boolean 									hasConnections			() {
			return hasIncomings() || hasOutcomings();
		}
		@Override
	    public default Collection<? extends GTEdge.Directed> 	getConnections			() {
			return Stream.concat(getIncomings().stream(), getOutcomings().stream()).collect(Collectors.toList());
		}

		public boolean 											hasIncomings			();
	    public Collection<? extends GTEdge.Directed> 			getIncomings			();

		public boolean 											hasOutcomings			();
	    public Collection<? extends GTEdge.Directed> 			getOutcomings			();

	}

	public static interface Gated extends GTNode, Composite.Single { // TODO:: Composite.Multi

		@Override
		public default GTGraph.Gated					getParent() {
			return getGraph();
		}
		@Override
		public GTGraph.Gated			 				getGraph();

		@Override
		public default Set<? extends GTGate> 			getChildren() {
			return (Set<? extends GTGate>) Stream.concat(getInputs().stream(), getOutputs().stream()).collect(Collectors.toSet());
		}

		// List<?> as we want to be able to use index
		public BeanPropertyList<? extends GTGate>		getInputs				();
		public BeanPropertyList<? extends GTGate>		getOutputs				();

		public int										getDegreeIn				();
		public int										getDegreeIn				(int _inputIdx);
		public int										getDegreeOut			();
		public int										getDegreeOut			(int _outputIdx);

		@Override
		public default boolean 							hasNeighbors			() {
			return hasPredecessors() || hasSuccessors();
		}
		@Override
	    public default Collection<? extends GTNode.Gated> 		getNeighbors			() {
			return Stream.concat(getPredecessors().stream(), getSuccessors().stream()).collect(Collectors.toList());
		}

		public boolean 									hasPredecessors			();
		public boolean 									hasPredecessors			(int _inputIdx);
	    public Collection<? extends GTNode.Gated> 		getPredecessors			();
	    public Collection<? extends GTNode.Gated> 		getPredecessors			(int _inputIdx);

		public boolean 									hasSuccessors			();
		public boolean 									hasSuccessors			(int _outputIdx);
	    public Collection<? extends GTNode.Gated> 		getSuccessors			();
	    public Collection<? extends GTNode.Gated> 		getSuccessors			(int _outputIdx);

		@Override
		public default boolean 							hasConnections			() {
			return hasIncomings() || hasOutcomings();
		}
		@Override
	    public default Collection<? extends GTEdge.Gated> 		getConnections			() {
			return Stream.concat(getIncomings().stream(), getOutcomings().stream()).collect(Collectors.toList());
		}

		public boolean 									hasIncomings			();
		public boolean 									hasIncomings			(int _inputIdx);
	    public Collection<? extends GTEdge.Gated> 		getIncomings			();
	    public Collection<? extends GTEdge.Gated> 		getIncomings			(int _inputIdx);

		public boolean 									hasOutcomings			();
		public boolean 									hasOutcomings			(int _outputIdx);
	    public Collection<? extends GTEdge.Gated> 		getOutcomings			();
	    public Collection<? extends GTEdge.Gated> 		getOutcomings			(int _outputIdx);

	}

}
