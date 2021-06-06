package fr.java.graph.impl.layouts;

import fr.java.graph.GTGraph;
import fr.java.graph.impl.layouts.planar.GTEdgeLayout2DAdapter;
import fr.java.graph.impl.layouts.planar.GTGateLayout2DAdapter;
import fr.java.graph.impl.layouts.planar.GTNodeLayout2DAdapter;
import fr.java.graph.impl.layouts.utils.GTLayoutFactoryBase;
import fr.java.graph.impl.layouts.utils.GTLayoutRegisterBase;
import fr.java.graph.layouts.GTEdgeLayout2D;
import fr.java.graph.layouts.GTGateLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;

public class GTViewerLayout2D extends GTViewerLayout<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>  {

	public GTViewerLayout2D(GTGraph _graph) {
		super(_graph, 
				new GTLayoutFactoryBase<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>
									   (GTNodeLayout2D.class, GTNodeLayout2DAdapter.class, GTEdgeLayout2DAdapter.class, GTGateLayout2DAdapter.class)
									   {}, 
				new GTLayoutRegisterBase<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>
									   ()
									   {});
	}

}
