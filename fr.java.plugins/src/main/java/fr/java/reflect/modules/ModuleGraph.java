package fr.java.reflect.modules;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.graph.GT;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.adapters.DepthFirstTraversalAdapter;
import fr.java.lang.properties.ID;
import fr.java.plugins.lang.exceptions.CircularDependencyException;

public class ModuleGraph {
	
	public final GTGraph.Directed 	dgraph;		// of ModuleModel

	public final ModuleLayer     	mLayer;
	public final ModuleFinder     	mFinder;
	
	public ModuleGraph() {
		this(ModuleLayer.boot(), ModuleFinder.ofSystem());
	}
	public ModuleGraph(ModuleLayer _modLayer) {
		this(_modLayer, ModuleFinder.ofSystem());
		initialize();
	}
	public ModuleGraph(ModuleFinder _modFinder) {
		this(ModuleLayer.boot(), _modFinder);
		initialize();
	}
	public ModuleGraph(ModuleLayer _modLayer, ModuleFinder _modFinder) {
		super();
		dgraph  = GT.newDirectedGraph();
		mLayer  = _modLayer;
		mFinder = _modFinder;
		initialize();
	}
	
	public static void main(String[] args) {
		Path         rootPath   = Paths.get("/media/sanke/opt.ssd/symbiot");
		Path         libPath    = rootPath.resolve("lib");
		Path         pluginPath = rootPath.resolve("plugins");

		ModuleGraph  graph      = new ModuleGraph(	ModuleLayer.boot(), 
													ModuleFinder.of(libPath, pluginPath)  );

		graph.dgraph.traverse(graph.dgraph.getNode("java.base").getId(), new DepthFirstTraversalAdapter() {

            @Override
            public VisitReturn visit(ID _nodeId) {
            	if(isMarkedAsVisited(_nodeId))
            		return VisitReturn.TERMINATE;

				GTNode      n = graph.dgraph.getNode(_nodeId);
				ModuleModel v = (ModuleModel) graph.dgraph.getNode(_nodeId).getValue();

            	StringBuilder sb = new StringBuilder();
	        	sb.append("Module: " + v.name + "\t[" + (v.found ? "X" : "-") + (v.loaded ? "X" : "-") + "]");
	        	sb.append("\n\t  Requires    : " + graph.dgraph.getSuccessors(_nodeId).stream().map(node -> node.getValue().toString()).collect(Collectors.joining(", ")));
	        	sb.append("\n\t  Required by : " + graph.dgraph.getPredecessors(_nodeId).stream().map(node -> node.getValue().toString()).collect(Collectors.joining(", ")));

            	System.out.println(sb.toString());

                return VisitReturn.CONTINUE;
            }

        });

		
		Set<? extends GTNode.Directed> nodes = graph.dgraph.getNodes();
		
		nodes.stream()
					.map(GTNode.Directed::getValue)
					.map(value -> (ModuleModel) value)
					.sorted((a, b) -> -1)
					.collect(Collectors.toList());
		
		
		List<? extends GTNode.Directed> sortedNodes = new ArrayList<>(nodes);
//		Collections.sort(sortedNodes);
		
		
	}

	private final void 				initialize() {
		if(mLayer != null) {
			Set<ModuleModel> loadedModules     = mLayer.modules()
														.stream()
														.map(ModuleModel::new)
														.collect(Collectors.toSet());

			for(ModuleModel mod : loadedModules)
				addModule(mod);
		}

		if(mFinder != null) {
			Set<ModuleModel> discoveredModules = mFinder.findAll()
														.stream()
														.map(ModuleModel::new)
														.collect(Collectors.toSet());
	
			for(ModuleModel mod : discoveredModules)
				addModule(mod);
		}

	}

	private final void 				addModule				(ModuleModel _module) {
		if(!dgraph.containsNode(_module))
			dgraph.addNode(_module);

		if(isPresent(_module)) {
			Set<ModuleModel> deps = getRequires(_module);
			for(ModuleModel dep : deps) {
				if(!dgraph.containsNode(dep))
					addModule(dep);
	
				if(!dgraph.containsEdge(dep, _module))
					dgraph.addEdge(dep, _module);
	
				checkCircularDependecy(dep, _module);
			}
		}
		
		isLoaded(_module);
	}
	private final void 				checkCircularDependecy	(ModuleModel _module, ModuleModel _requires) {
    	List<ModuleModel> neighbors = dgraph.getNeighbors(_module).stream().map(n -> (ModuleModel) n.getValue()).collect(Collectors.toList());

        if(neighbors.contains(_module))
            throw new CircularDependencyException("Circular Dependency detected: %s <----> %s", _module, _requires);
	}

	private final boolean 			isPresent				(ModuleModel _module) {
		boolean found = mLayer.modules()
							   .stream()
							   .filter(m -> m.getDescriptor().name().compareTo(_module.name) == 0)
							   .findFirst().isPresent();

		if(!found && mFinder != null)
			found = mFinder.find(_module.name).isPresent();

		_module.found = found;

		return _module.found;
	}
	private final boolean 			isLoaded				(ModuleModel _module) {
		boolean loaded = mLayer.modules()
							   .stream()
							   .filter(m -> m.getDescriptor().name().compareTo(_module.name) == 0)
							   .findFirst().isPresent();

		_module.loaded = loaded;

		return _module.loaded;
	}
	private final ModuleReference	getReference			(ModuleModel _module) {
		if(_module.reference != null)
			return _module.reference;

		Optional<ModuleReference> modRef = mFinder.find(_module.name);
		if(modRef.isPresent())
			_module.reference = modRef.get();

		if(_module.reference != null)
			return _module.reference;

		return getReference(_module, ModuleFinder.ofSystem());
	}
	private final ModuleReference	getReference			(ModuleModel _module, ModuleFinder _modFinder) {
		Optional<ModuleReference> modRef = _modFinder.find(_module.name);
		if(!modRef.isPresent())
			return null;

		_module.reference = modRef.get();

		return _module.reference;
	}
	private final ModuleDescriptor	getDescriptor			(ModuleModel _module) {
		if(_module.descriptor != null)
			return _module.descriptor;

		// Attempt to find the module in the defined ModuleLayer
		// -----------------------------------------------------
		if(mLayer != null) {
			Optional<Module> oModule = mLayer.modules()
											 .stream()
											 .filter(m -> m.getDescriptor().name().compareTo(_module.name) == 0)
											 .findFirst();
			
			if(oModule.isPresent())
				_module.descriptor = oModule.get().getDescriptor();
		}

		if(_module.descriptor != null)
			return _module.descriptor;

		// Attempt to find the module in the defined ModuleFinder
		// ------------------------------------------------------
		if(mFinder != null) {
			Optional<ModuleReference> modRef = mFinder.find(_module.name);
	
			if(modRef.isPresent())
				_module.descriptor = modRef.get().descriptor();
		}
		
		if(_module.descriptor == null)
			System.err.println("Failed to find module descriptor for '" + _module.name + "'");

		return _module.descriptor;
	}
	private final Set<ModuleModel> 	getRequires				(ModuleModel _module) {
		if(getDescriptor(_module) == null)
			return Collections.emptySet();

		Set<Requires> requires = _module.descriptor.requires();
		
		if(requires == null || requires.isEmpty())
			return Collections.emptySet();
		
		return requires .stream()
						.map(req -> new ModuleModel(req.name(), true))
						.collect(Collectors.toSet());
	}

}
