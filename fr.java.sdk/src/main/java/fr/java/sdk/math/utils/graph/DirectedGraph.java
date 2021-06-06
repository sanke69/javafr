package fr.java.sdk.math.utils.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectedGraph<V> {

	private Map<V, List<V>> neighbors = new HashMap<>();

	public void addVertex(V vertex) {
		if(neighbors.containsKey(vertex))
			return;

		neighbors.put(vertex, new ArrayList<V>());
	}

	public boolean containsVertex(V vertex) {
		return neighbors.containsKey(vertex);
	}

	public void addEdge(V from, V to) {
		this.addVertex(from);
		this.addVertex(to);
		neighbors.get(from).add(to);
	}

	public void remove(V from, V to) {
		if(!(this.containsVertex(from) && this.containsVertex(to)))
			throw new IllegalArgumentException("Nonexistent vertex");

		neighbors.get(from).remove(to);
	}

	public List<V> getNeighbors(V vertex) {
		if(!neighbors.containsKey(vertex))
			return new ArrayList<V>();

		return neighbors.get(vertex);
	}

	public Map<V, Integer> outDegree() {
		Map<V, Integer> result = new HashMap<>();
		for(V vertex : neighbors.keySet())
			result.put(vertex, neighbors.get(vertex).size());

		return result;
	}

	public Map<V, Integer> inDegree() {
		Map<V, Integer> result = new HashMap<>();
		for(V vertex : neighbors.keySet())
			result.put(vertex, 0);

		for(V from : neighbors.keySet())
			for(V to : neighbors.get(from))
				result.put(to, result.get(to) + 1);

		return result;
	}

	public List<V> topologicalSort() {
		Map<V, Integer> degree = inDegree();

		Stack<V> zeroVertices = new Stack<>();
		for(V v : degree.keySet())
			if(degree.get(v) == 0)
				zeroVertices.push(v);

		// determine the topological order
		List<V> result = new ArrayList<>();
		while(!zeroVertices.isEmpty()) {
			V vertex = zeroVertices.pop(); // choose a vertex with zero in-degree
			result.add(vertex); // vertex 'v' is next in topological order
			// "remove" vertex 'v' by updating its neighbors
			for(V neighbor : neighbors.get(vertex)) {
				degree.put(neighbor, degree.get(neighbor) - 1);
				// remember any vertices that now have zero in-degree
				if(degree.get(neighbor) == 0)
					zeroVertices.push(neighbor);
			}
		}

		// check that we have used the entire graph (if not, there was a cycle)
		if(result.size() != neighbors.size())
			return null;

		return result;
	}

	/**
	 * Report (as a List) the reverse topological sort of the vertices; null for no such sort.
	 */
	public List<V> reverseTopologicalSort() {
		List<V> list = topologicalSort();
		if(list == null)
			return null;

		Collections.reverse(list);

		return list;
	}

	/**
	 * True if graph is a dag (directed acyclic graph).
	 */
	public boolean isDag() {
		return topologicalSort() != null;
	}

	/**
	 * String representation of graph.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(V vertex : neighbors.keySet())
			sb.append("\n   " + vertex + " -> " + neighbors.get(vertex));

		return sb.toString();
	}

}
