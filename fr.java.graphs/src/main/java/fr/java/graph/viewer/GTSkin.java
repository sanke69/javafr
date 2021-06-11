package fr.java.graph.viewer;

import fr.java.lang.properties.ID;
import fr.java.patterns.capabilities.Identifiable;

public interface GTSkin<GO, GTO extends Identifiable, GTL extends GTLayout> extends Identifiable {

	interface Generator<GO, GTO extends Identifiable, GTL extends GTLayout>
	{ public GTSkin<GO, GTO, GTL> newInstance(GTO _object, GTL _layout); }


	public default ID 	getId() { return getModel().getId(); }
	public GTO 			getModel();
	public GO 			getGraphics(GTViewer.Graphics<GO, ?, ?, ?> _viewer);

}
