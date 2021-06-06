package fr.java.graph.viewer.utils;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;

public interface GTSkinFactory<GO, NL extends GTLayout.Node, 
								   EL extends GTLayout.Edge, 
								   GL extends GTLayout.Gate> {

	public GTSkin<GO, GTNode, NL>		createNodeSkin				(GTNode _node, NL _layout);
	public GTSkin<GO, GTEdge, EL>		createEdgeSkin				(GTEdge _edge, EL _layout);
	public GTSkin<GO, GTGate, GL>		createGateSkin				(GTGate _gate, GL _layout);

	public void       					registerNodeSkinClass		(Class<?> _modelClass, Class<? extends GTSkin<GO, GTNode, NL>> _viewClass);
	public void       					registerNodeSkinClass		(Class<?> _modelClass, Class<? extends GTSkin<GO, GTNode, NL>> _viewClass, Object... _viewCtorArgs);
	public void       					registerNodeSkinSupplier	(Class<?> _modelClass, GTSkin.Generator<GO, GTNode, NL> _viewClass);

	public void       					registerEdgeSkinClass		(Class<?> _modelClass, Class<? extends GTSkin<GO, GTEdge, EL>> _viewClass);
	public void       					registerEdgeSkinClass		(Class<?> _modelClass, Class<? extends GTSkin<GO, GTEdge, EL>> _viewClass, Object... _viewCtorArgs);
	public void       					registerEdgeSkinSupplier	(Class<?> _modelClass, GTSkin.Generator<GO, GTEdge, EL> _viewClass);

	public void       					registerGateSkinClass		(Class<?> _modelClass, Class<? extends GTSkin<GO, GTGate, GL>> _viewClass);
	public void       					registerGateSkinClass		(Class<?> _modelClass, Class<? extends GTSkin<GO, GTGate, GL>> _viewClass, Object... _viewCtorArgs);
	public void       					registerGateSkinSupplier	(Class<?> _modelClass, GTSkin.Generator<GO, GTGate, GL> _viewClass);
//	public void       					registerGateSkinSupplier	(Class<?> _modelClass, GTSkin.GateGenerator<GO, ? extends GTSkin<GO>> _viewClass);

}
