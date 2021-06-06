package fr.java.plugins.internal.di;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import fr.java.graph.GT;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.plugins.apt.PluginProcessor;

public class DependencyResolver {
    private final PluginProcessor 	processor;

	GTGraph.Directed				 	dgraph; // <PluginModel<?>, ?>

    public DependencyResolver(PluginProcessor _processor) {
    	super();
        processor = _processor;
    }

    public Collection<PluginModel<?>> 	resolve(Collection<Class<?>> pluginClasses) {
    	dgraph = GT.newDirectedGraph();

        for(Class<?> pluginClass : pluginClasses) {
            final PluginModel<?>             node = new PluginModel<>(pluginClass);
            final Collection<PluginModel<?>> deps = processor.getDependencies(pluginClass);

			if(!dgraph.containsNode(node))
				dgraph.addNode(node);

            if(!deps.isEmpty())
            	for(PluginModel<?> dep : deps) {
        			if(!dgraph.containsNode(dep))
        				dgraph.addNode(dep);

            		dgraph.addEdge(node, dep);
                	checkCircularDependency(node, dep);
            	}
        }

        return topologicalSort();
    }

    private Collection<PluginModel<?>> 	topologicalSort() {
        final Stack<PluginModel<?>>        stack   = new Stack<>();
        final Map<PluginModel<?>, Boolean> visited = new HashMap<>();
        final List<PluginModel<?>>         classes = new ArrayList<>();

//		for(PluginModel<?> node : dgraph.getNodeValues(PluginModel.class))
        for(GTNode node : dgraph.getNodes()) {
        	PluginModel<?> plugin = node.getValue(PluginModel.class);
            visited.put(plugin, false);
        }

//        for(PluginModel<?> node : dgraph.getNodeValues(PluginModel.class))
        for(GTNode node : dgraph.getNodes()) {
        	PluginModel<?> plugin = node.getValue(PluginModel.class);
            if(!visited.get(plugin))
                doTopologicalSort(plugin, visited, stack);
        }

        for(PluginModel<?> node : stack)
            classes.add(node);

        return classes;
    }

    private void 						checkCircularDependency(PluginModel<?> node, PluginModel<?> adj) {
    	/*
    	List<PluginModel<?>> neighbors = dgraph.getNeighbors(node).stream()	.map(n -> n.getValue(PluginModel.class))
    																		.collect(Collectors.toList());

        if (neighbors.contains(node))
            throw new CircularDependencyException("Circular Dependency detected: %s <----> %s", node, adj);
        */
    }

    private void 						doTopologicalSort(PluginModel<?> _node, Map<PluginModel<?>, Boolean> _visited, Stack<PluginModel<?>> _stack) {
        _visited.put(_node, true);

        final Collection<PluginModel> dependencies = dgraph.getSuccessors(_node).stream()
        															.map(n -> n.getValue(PluginModel.class))
        															.collect(Collectors.toList());

        for(PluginModel<?> dependency : dependencies) {
            final Boolean visited = _visited.get(dependency);
            if (visited != null && !visited)
                doTopologicalSort(dependency, _visited, _stack);
        }

        _stack.push(_node);
    }

}
