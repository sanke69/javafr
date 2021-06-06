package fr.java.graph;

import java.lang.reflect.Constructor;
import java.util.function.BiFunction;

import fr.java.graph.impl.models.centralized.GTGraphDirectedC;
import fr.java.graph.impl.models.centralized.GTGraphGatedC;
import fr.java.graph.impl.models.centralized.GTGraphUndirectedC;
import fr.java.graph.utils.GTSkinProvider;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;

/**
 * GraphTheory
 * -----------
 *
 */
public interface GT {
	public static final int DEFAULT_ID_SIZE = 8;

	public static GTGraph.Undirected 	newUndirectedGraph() {
		return new GTGraphUndirectedC();
	}
	public static GTGraph.Directed  	newDirectedGraph() {
		return new GTGraphDirectedC();
	}
	public static GTGraph.Gated 		newGatedGraph() {
		return new GTGraphGatedC();
	}

	public interface NodeViewSupplier {

		public static <GO, NL extends GTLayout.Node> 
					 GTSkin.Generator<GO, GTNode, NL> 	of(Class<NL> _layoutClass, Class<? extends GTSkin<GO, GTNode, NL>> _skinClass) {
			return new GTSkinProvider<GO, GTNode, NL>(_layoutClass, _skinClass);
		};
		public static <GO, NL extends GTLayout.Node> 
					 GTSkin.Generator<GO, GTNode, NL> 	of(Class<NL> _layoutClass, Class<? extends GTSkin<GO,GTNode, NL>> _skinClass, Object... _skinParameters) {
			return new GTSkinProvider<GO, GTNode, NL>(_layoutClass, _skinClass, _skinParameters);
		};
		public static <GO, NL extends GTLayout.Node> 
					 GTSkin.Generator<GO, GTNode, NL> 	of(Class<NL> _layoutClass, Class<? extends GTSkin<GO,GTNode, NL>> _skinClass, Constructor<GTSkin<GO,GTNode, NL>> _skinConstructor, Object... _skinParameters) {
			return new GTSkinProvider<GO, GTNode, NL>(_layoutClass, _skinClass, _skinConstructor, _skinParameters);
		};
		public static <GO, NL extends GTLayout.Node> 
					 GTSkin.Generator<GO, GTNode, NL> 	of(Class<NL> _layoutClass, BiFunction<GTNode, NL, GTSkin<GO,GTNode, NL>> _skinSupplier) {
			return new GTSkinProvider<GO, GTNode, NL>(_layoutClass, _skinSupplier);
		};

	}

	public interface EdgeViewSupplier {

		public static <GO, EL extends GTLayout.Edge> 
					 GTSkin.Generator<GO, GTEdge, EL> 	of(Class<EL> _layoutClass, Class<? extends GTSkin<GO, GTEdge, EL>> _skinClass) {
			return new GTSkinProvider<GO, GTEdge, EL>(_layoutClass, _skinClass);
		};
		public static <GO, EL extends GTLayout.Edge> 
					 GTSkin.Generator<GO, GTEdge, EL> 	of(Class<EL> _layoutClass, Class<? extends GTSkin<GO, GTEdge, EL>> _skinClass, Object... _skinParameters) {
			return new GTSkinProvider<GO, GTEdge, EL>(_layoutClass, _skinClass, _skinParameters);
		};
		public static <GO, EL extends GTLayout.Edge> 
					 GTSkin.Generator<GO, GTEdge, EL> 	of(Class<EL> _layoutClass, Class<? extends GTSkin<GO, GTEdge, EL>> _skinClass, Constructor<? extends GTSkin<GO, GTEdge, EL>> _skinConstructor, Object... _skinParameters) {
			return new GTSkinProvider<GO, GTEdge, EL>(_layoutClass, _skinClass, _skinConstructor, _skinParameters);
		};
		public static <GO, EL extends GTLayout.Edge> 
					 GTSkin.Generator<GO, GTEdge, EL> 	of(Class<EL> _layoutClass, BiFunction<GTEdge, EL, GTSkin<GO, GTEdge, EL>> _skinSupplier) {
			return new GTSkinProvider<GO, GTEdge, EL>(_layoutClass, _skinSupplier);
		};

	}

	public interface GateViewSupplier {

		public static <GO, GL extends GTLayout.Gate> 
					 GTSkin.Generator<GO, GTGate, GL> 	of(Class<GL> _layoutClass, Class<? extends GTSkin<GO, GTGate, GL>> _skinClass) {
			return new GTSkinProvider<GO, GTGate, GL>(_layoutClass, _skinClass);
		};
		public static <GO, GL extends GTLayout.Gate> 
					 GTSkin.Generator<GO, GTGate, GL> 	of(Class<GL> _layoutClass, Class<? extends GTSkin<GO, GTGate, GL>> _skinClass, Object... _skinParameters) {
			return new GTSkinProvider<GO, GTGate, GL>(_layoutClass, _skinClass, _skinParameters);
		};
		public static <GO, GL extends GTLayout.Gate> 
					 GTSkin.Generator<GO, GTGate, GL> 	of(Class<GL> _layoutClass, Class<? extends GTSkin<GO, GTGate, GL>> _skinClass, Constructor<? extends GTSkin<GO, GTGate, GL>> _skinConstructor, Object... _skinParameters) {
			return new GTSkinProvider<GO, GTGate, GL>(_layoutClass, _skinClass, _skinConstructor, _skinParameters);
		};
		public static <GO, GL extends GTLayout.Gate> 
					 GTSkin.Generator<GO, GTGate, GL> 	of(Class<GL> _layoutClass, BiFunction<GTGate, GL, GTSkin<GO, GTGate, GL>> _skinSupplier) {
			return new GTSkinProvider<GO, GTGate, GL>(_layoutClass, _skinSupplier);
		};

	}

}
