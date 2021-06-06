package fr.java.plugins.internal.loaders;

import java.io.IOException;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.graph.GT;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.adapters.DepthFirstTraversalAdapter;
import fr.java.lang.properties.ID;
import fr.java.plugins.lang.exceptions.CircularDependencyException;
import fr.java.sdk.lang.SoftPtr;

public class Modules {

	public static final void			 		displayGraphNodes(GTGraph.Directed _graph) {
		for(GTNode n : _graph.getNodes()) {
			ID nodeId = n.getId();

        	StringBuilder sb = new StringBuilder();
        	sb.append("Module: " + n.getValue());
        	sb.append("\t  Requires: " + _graph.getSuccessors(nodeId)
        										.stream()
        										.map(node -> node.getValue().toString())
        										.collect(Collectors.joining(", ")));
        	System.out.println(sb.toString());
        	
        	sb = new StringBuilder();
        	sb.append("Module: " + n.getValue());
        	sb.append("\t  Required by: " + _graph.getPredecessors(nodeId)
        										.stream()
        										.map(node -> node.getValue().toString())
        										.collect(Collectors.joining(", ")));
        	System.out.println(sb.toString());
		}
	}
	public static final void			 		displayDependencyGraph(GTGraph.Directed _graph) {
		_graph.traverse(_graph.getNodes().iterator().next().getId(), new DepthFirstTraversalAdapter() {

            @Override
            public VisitReturn visit(ID _nodeId) {
            	if(isMarkedAsVisited(_nodeId))
            		return VisitReturn.TERMINATE;

				GTNode n = _graph.getNode(_nodeId);
	        	String v = (String) _graph.getNode(_nodeId).getValue();

            	StringBuilder sb = new StringBuilder();
	        	sb.append("Module: " + n.getValue());
	        	sb.append("\t  Requires: " + _graph.getSuccessors(_nodeId)
	        										.stream()
	        										.map(node -> node.getValue().toString())
	        										.collect(Collectors.joining(", ")));
            	System.out.println(sb.toString());
            	
            	sb = new StringBuilder();
	        	sb.append("Module: " + n.getValue());
	        	sb.append("\t  Required by: " + _graph.getPredecessors(_nodeId)
	        										.stream()
	        										.map(node -> node.getValue().toString())
	        										.collect(Collectors.joining(", ")));
            	System.out.println(sb.toString());
            	
                return VisitReturn.CONTINUE;
            }

        });
	}

	public static void main(String[] args) {
		String rootPath    = "/s_drive/opt/aio";
		String libPath     = rootPath + "/lib";
		String pluginPath  = rootPath + "/plugins";
		String modulePath  = libPath;
		String moduleFile  = libPath + "/" + "fr.java.beans-0.9.0.2.jar";
		String moduleIssue = libPath + "/" + "batik-script-1.14.jar";
		String moduleName  = "javafr.svg";

		System.out.println("Java 9 - JigSaw Module System Util");
		System.out.println("----------------------------------");
		System.out.println("Non modules: ");
		getNonModule(Paths.get(libPath))
				.stream()
				.forEach(m -> System.out.println("  - " + m));

		System.out.println("Loaded modules: " + getModuleReferences(true).size());
		/*
		getModuleReferences(true)
				.stream()
				.forEach(m -> System.out.println("  - " + m));
		*/

		System.out.println("modules in path: " + getModuleReferences(false, Paths.get(libPath)).size());
		/*
		getModuleReferences(false, Paths.get(libPath))
				.stream()
				.forEach(m -> System.out.println("  - " + m));
		*/

		System.out.println("mixed modules: " + getModuleReferences(true, Paths.get(libPath)).size());
		/*
		getModuleReferences(true, Paths.get(libPath))
				.stream()
				.forEach(m -> System.out.println("  - " + m));
		*/

		ModuleReference ref = getModuleReference(moduleName, false, Paths.get(libPath));
		System.out.println(">>> " + ref);
		
		System.out.println("+ isModule '" + modulePath  + "'? " + isModule(modulePath));
		System.out.println("+ isModule '" + moduleFile  + "'? " + isModule(moduleFile) + "\t" + getModuleName(moduleFile));
		System.out.println("+ isModule '" + moduleIssue + "'? " + isModule(moduleIssue) + "\t" + getModuleName(moduleIssue));

		System.out.println("requirements for : " + moduleFile);
		getRequiresReferences( Paths.get(moduleFile), false )
				.stream()
				.forEach(System.out::println);
		System.out.println("requirements for : " + moduleFile);
		getRequiresReferences( Paths.get(moduleFile), true, Paths.get(modulePath) )
				.stream()
				.forEach(System.out::println);

		System.out.println("requirements for : " + getModuleName(moduleFile));
		getRequiresReferences( getModuleName(moduleFile), true, Paths.get(moduleFile) )
				.stream()
				.forEach(System.out::println);

		System.out.println("requirements for : " + moduleName);
		getRequiresReferences( moduleName, true, Paths.get(modulePath) )
				.stream()
				.forEach(System.out::println);

		System.out.println("missing requirements for : " + moduleName);
		getRequiresReferences( moduleName, false, Paths.get(modulePath) )
				.stream()
				.forEach(System.out::println);
		

		System.out.println("");
		System.out.println("DependencyGraphForLoadedModules");
		System.out.println("----------------------------------");
		displayGraphNodes( buildDependencyGraphForLoadedModules() );

		System.out.println("");
		System.out.println("buildDependencyGraph");
		System.out.println("----------------------------------");
		displayDependencyGraph( buildDependencyGraph(moduleName, false, Paths.get(libPath), Paths.get(pluginPath)) );
		

		System.out.println("");
		System.out.println("DependencyGraphForLoadedModules");
		System.out.println("----------------------------------");
		displayDependencyGraph( buildDependencyGraph(moduleName, true, Paths.get(libPath), Paths.get(pluginPath)) );
	}

	public static final boolean 				isModule(Path _modulePath) {
		ModuleFinder finder = ModuleFinder.of(_modulePath);

		if(_modulePath.toFile().isDirectory())
			try {
				return finder.findAll().size() == 1;
			} catch(java.lang.module.FindException e) {
				return false;
			}

		try {
			return !finder.findAll().isEmpty();
		} catch(java.lang.module.FindException e) {
			return false;
		}
	}
	public static final boolean 				isModule(String _modulePath) {
		return isModule( Paths.get(_modulePath) );
	}

	public static final Set<Path> 				getNonModule(Path... _lookupPathes) {
		Set<Path> set = new HashSet<Path>();

		try {
			for(Path lookupPath : _lookupPathes)
				Files.walk(lookupPath)
			         .filter(Files::isRegularFile)
			         .forEach(f -> {
			        	 if(!isModule(f))
			        		set.add(f);
			         });
		} catch (IOException e) { }

		return set;
	}

	public static final Set<ModuleReference> 	getModuleReferences(boolean _includeLoaded, Path... _lookupPathes) {
		Set<ModuleReference> set = new HashSet<ModuleReference>();

		try {
			for(Path lookupPath : _lookupPathes)
				Files.walk(lookupPath)
			         .filter(Files::isRegularFile)
			         .forEach(f -> {
			        	ModuleReference module = null;

						try {
							Set<ModuleReference>
							modules = ModuleFinder.of(f).findAll();
							module  = modules.size() == 1 ? modules.iterator().next() : null;
						} catch(java.lang.module.FindException e) { }

			        	if(module != null)
			        		set.add(module);
			         });
		} catch (IOException e) { }

		if(_includeLoaded)
			ModuleLayer.boot()
					   .modules()
					   .stream()
					   .forEach(m -> {
						   boolean found = set.stream()
								   			  .filter(ref -> ref.descriptor().name().compareTo(m.getName()) == 0)
								   			  .findAny()
								   			  .isPresent();
						   if(!found)
							   set.add(new_ModuleReference(m.getDescriptor(), true));
					   });

		return set;
	}

	public static final ModuleReference			getModuleReference(Path _modulePath) {
		if( !isModule(_modulePath) )
			return null;

		ModuleFinder    finder = ModuleFinder.of(_modulePath);
		ModuleReference modRef = finder.findAll().stream().findFirst().get();
		
		return modRef;
	}
	public static final ModuleReference			getModuleReference(String _modulePath) {
		return getModuleReference( Paths.get(_modulePath) );
	}
	public static final ModuleReference			getModuleReference(String _moduleName, boolean _includeLoaded, Path... _lookupPathes) {
		// TODO:: WorkAround due to fu.king malformed jars
//		ModuleFinder finder = _lookupPathes.length == 0 ? ModuleFinder.ofSystem() : ModuleFinder.of(_lookupPathes);
//		return finder.findAll().stream().collect(Collectors.toSet());

		final SoftPtr<ModuleReference> reference = new SoftPtr<ModuleReference>();

		try {
			for(Path lookupPath : _lookupPathes)
				Files.walk(lookupPath)
				        .filter(Files::isRegularFile)
				        .forEach(f -> {
				        	ModuleReference module = null;
	
							try {
								Set<ModuleReference>
								modules = ModuleFinder.of(f).findAll();
								module  = modules.size() == 1 ? modules.iterator().next() : null;
							} catch(java.lang.module.FindException e) { }
	
				        	if(module != null && module.descriptor().name().compareTo(_moduleName) == 0)
				        		reference.set( module );
				        });
		} catch (IOException e) { }

		if(!reference.isEmpty())
			return reference.get();
		
		if(_includeLoaded) {
			ModuleLayer layer = ModuleLayer.boot();
			for(Module m : layer.modules()) {
				if(m.getName().compareTo(_moduleName) == 0)
					return new_ModuleReference(m.getDescriptor(), true);
			}
		}
		return null;
	}

	public static final Set<ModuleReference>	getRequiresReferences(Path _modulePath, boolean _includeLoaded, Path... _lookupPathes) {
		if( !isModule(_modulePath) )
			return null;

		return _lookupPathes.length > 0 ?
					getModuleReferences(_includeLoaded, _lookupPathes)
						.stream()
						.filter(ref -> ref.descriptor().name().compareTo(getModuleName(_modulePath)) == 0)
						.findFirst()
						.map(ModuleReference::descriptor)
						.map(ModuleDescriptor::requires)
						.orElse(Collections.emptySet())
						.stream()
						.map(req -> {
							ModuleReference ref = getModuleReference(req.name(), _includeLoaded, _lookupPathes);
							return ref != null ? ref : new_ModuleReference(req.name(), req.compiledVersion().orElseGet(() -> Version.parse("0-unknown")));
						})
						.collect(Collectors.toSet())
					:
					getModuleReference(_modulePath)
						.descriptor().requires()
						.stream()
						.map(req -> new_ModuleReference(req.name(), req.compiledVersion().orElseGet(() -> Version.parse("0-unknown"))))
						.collect(Collectors.toSet());
	}
	public static final Set<ModuleReference>	getRequiresReferences(String _moduleName, boolean _includeLoaded, Path... _lookupPathes) {
		if(_lookupPathes.length == 0 && !_includeLoaded)
			throw new IllegalArgumentException("Can(t find a module with just its 'name', it's not magic !");



		if(_includeLoaded)
			return getModuleReferences(_includeLoaded, _lookupPathes)
							.stream()
							.filter(ref -> ref.descriptor().name().compareTo(_moduleName) == 0)
							.findFirst()
							.map(ModuleReference::descriptor)
							.map(ModuleDescriptor::requires)
							.orElse(Collections.emptySet())
							.stream()
							.map(ModuleDescriptor.Requires::name)
							.map(name -> {
								ModuleReference ref = getModuleReference(name, _includeLoaded, _lookupPathes);
								return ref != null ? ref : new_ModuleReference(name, Version.parse("0-unknown"));
							})
							.collect(Collectors.toSet());

		ModuleLayer boot = ModuleLayer.boot();
		return getModuleReferences(_includeLoaded, _lookupPathes)
						.stream()
						.filter(ref -> ref.descriptor().name().compareTo(_moduleName) == 0)
						.findFirst()
						.map(ModuleReference::descriptor)
						.map(ModuleDescriptor::requires)
						.orElse(Collections.emptySet())
						.stream()
						.filter(req -> {
							Optional<Module> loaded = boot.findModule(req.name());
							return loaded.isEmpty();
						})
						.map(ModuleDescriptor.Requires::name)
						.map(name -> {
							ModuleReference ref = getModuleReference(name, _includeLoaded, _lookupPathes);
							return ref != null ? ref : new_ModuleReference(name, Version.parse("0-unknown"));
						})
						.collect(Collectors.toSet());

	}

	public static final String					getModuleName(Path _modulePath) {
		ModuleReference moduleRef = getModuleReference(_modulePath);

		return moduleRef != null ? moduleRef.descriptor().name() : null;
	}
	public static final String	 				getModuleName(String _modulePath) {
		return getModuleName( Paths.get(_modulePath) );
	}

	public static final GTGraph.Directed 		buildDependencyGraphForLoadedModules() {
		GTGraph.Directed dgraph = GT.newDirectedGraph();

		Set<String> loadedModules = ModuleLayer.boot().modules().stream().map(m -> m.getDescriptor().name()).collect(Collectors.toSet());
		for(String module : loadedModules)
			addModule(dgraph, module, true);

		return dgraph;
	}
	public static final GTGraph.Directed 		buildDependencyGraph(String _moduleName, boolean _includeLoaded, Path... _lookupPathes) {
		GTGraph.Directed dgraph = GT.newDirectedGraph();

		addModule(dgraph, _moduleName, _includeLoaded, _lookupPathes);

    	return dgraph;
	}

	private static final void 					addModule				(GTGraph.Directed dgraph, String modName, boolean _includeJvm, Path... _lookupPath) {
		if(dgraph.getNode(modName) != null)
			return ;

		dgraph.addNode(modName);

		// Do not add dependencies inside the JVM
		if(!_includeJvm)
			if(modName.indexOf("java.") == 0 || modName.indexOf("javafx.") == 0 || modName.indexOf("jdk.") == 0)
				return ;

		Set<ModuleReference> reqs = getRequiresReferences(modName, _includeJvm, _lookupPath);
		Set<String>          deps = reqs.stream().map(ref -> ref.descriptor().name()).collect(Collectors.toSet());
		for(String dep : deps) {
			if(!dgraph.containsNode(dep))
				addModule(dgraph, dep, _includeJvm, _lookupPath);

			dgraph.addEdge(modName, dep);
			checkCircularDependecy(dgraph, modName, dep);
		}
	}
	private static final void 					checkCircularDependecy	(GTGraph.Directed dgraph, String node, String dep) {
    	List<String> neighbors = dgraph.getNeighbors(node).stream().map(n -> (String) n.getValue()).collect(Collectors.toList());

        if(neighbors.contains(node))
            throw new CircularDependencyException("Circular Dependency detected: %s <----> %s", node, dep);
	}

	private static final ModuleReference 		new_ModuleReference(String _name, Version _version) {
		ModuleDescriptor descriptor = ModuleDescriptor.newModule(_name).version(_version).build();
		
		return new ModuleReference(descriptor, null) {
			@Override
			public ModuleReader open() throws IOException {
				return null;
			}
			@Override
			public String toString() {
				return "[module " + descriptor.name() + ", location=<generated>]";
			}
		};
	}
	private static final ModuleReference 		new_ModuleReference(ModuleDescriptor _descriptor, boolean _fromLoadedModules) {
		return new ModuleReference(_descriptor, null) {
			@Override
			public ModuleReader open() throws IOException {
				return null;
			}
			@Override
			public String toString() {
				return "[module " + _descriptor.name() + ", location=" + (_fromLoadedModules ? "<runtime>]" : "<unknown>]");
			}
		};
	}

}
