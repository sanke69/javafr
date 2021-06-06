package fr.java.graph.viewer;

import fr.java.lang.properties.ID;
import fr.java.patterns.displayable.Moveable;
import fr.java.patterns.displayable.Resizeable;
import fr.java.patterns.displayable.Selectable;
import fr.java.patterns.identifiable.Identifiable;
import fr.java.patterns.mvc.Viewable;

public interface GTView<GO, GTO extends Identifiable, GTL extends GTLayout> extends Identifiable, 
																					Viewable, 
																					Selectable, 
																					Moveable.TwoDims, 
																					Resizeable.TwoDims, 
																					GTSkin<GO, GTO, GTL> {

	@Override
	public default ID 	getId() 		{ return getModel().getId(); }
	public GTO			getModel();
	public GTL 			getLayout();

	public GO 			getGraphics();

}
