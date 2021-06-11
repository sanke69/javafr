package fr.java.graph.viewer;

import fr.java.lang.properties.ID;
import fr.java.mvc.View;
import fr.java.patterns.capabilities.Identifiable;

public interface GTView<GO, GTO extends Identifiable, GTL extends GTLayout> extends Identifiable, View<GTO>, GTSkin<GO, GTO, GTL> {

	@Override
	public default ID 	getId() 		{ return getModel().getId(); }
	public GTO			getModel();
	public GTL 			getLayout();

	public GO 			getGraphics();

}
