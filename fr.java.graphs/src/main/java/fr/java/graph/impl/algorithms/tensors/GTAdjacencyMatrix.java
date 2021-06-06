package fr.java.graph.impl.algorithms.tensors;

import java.util.ArrayList;
import java.util.List;

import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.lang.properties.ID;

public class GTAdjacencyMatrix {
	List<ID>     ids;
	BinaryMatrix matrix;
	
	public GTAdjacencyMatrix(GTGraph _graph) {
		super();
		build(_graph);
	}

	public ID 			at(int _index) {
		return ids.get(_index);
	}
	public BinaryMatrix asMatrix() {
		return matrix;
	}

	private void 		build(GTGraph _graph) {
		int size = _graph.getNodes().size();

		ids    = new ArrayList<ID>(size);
		matrix = new BinaryMatrix(size, size);
		
		for(GTNode node : _graph.getNodes())
			ids.add(node.getId());

		for(ID id : ids) {
			int j = ids.indexOf(id);
			
			for(GTNode n : _graph.getNeighbors(id))
				matrix.on(ids.indexOf(n.getId()), j);
		}
		
	}

}
