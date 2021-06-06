package fr.java.graph.impl.algorithms.layouts.random;

import java.util.Random;

import fr.java.graph.algorithms.layouts.GTLayoutGenerator;
import fr.java.graph.layouts.GTEdgeLayout2D;
import fr.java.graph.layouts.GTGateLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;
import fr.java.graph.viewer.GTViewer;

public class GTRandomLayout2D implements GTLayoutGenerator<GTViewer<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>> {
	private final Random rnd = new Random();

	@Override
	public void execute(GTViewer<GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D> _viewer) {
		for (final GTNodeLayout2D cell : _viewer.getLayoutRegister().getNodeLayouts()) {
			final double x = rnd.nextDouble() * 500;
			final double y = rnd.nextDouble() * 500;

			cell.relocate(x, y);
		}
	}

}
