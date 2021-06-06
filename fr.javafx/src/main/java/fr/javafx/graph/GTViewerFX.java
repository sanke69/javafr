package fr.javafx.graph;

import java.util.List;
import java.util.function.Supplier;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.adapters.GTSkinFactoryBase;
import fr.java.graph.adapters.GTSkinRegisterBase;
import fr.java.graph.adapters.GTViewerBase;
import fr.java.graph.layouts.GTEdgeLayout2D;
import fr.java.graph.layouts.GTGateLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;
import fr.java.graph.viewer.GTView;
import fr.java.graph.viewer.utils.GTSkinFactory;
import fr.java.graph.viewer.utils.GTSkinRegister;
import fr.javafx.graph.behaviors.GTViewerFXBehavior;
import fr.javafx.scene.layout.ScalablePane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

public class GTViewerFX extends GTViewerBase<Region, GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D> {

	private static final Supplier<GTSkinFactory<Region, GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>> skinFxFactory = () -> {
		return new GTSkinFactoryBase<Region, GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>
				   (GTNodeLayout2D.class, GTEdgeLayout2D.class, GTGateLayout2D.class)
				   {};
	};
	private static final Supplier<GTSkinRegister<Region, GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>> skinFxRegister = () -> {
		return new GTSkinRegisterBase<Region, GTNodeLayout2D, GTEdgeLayout2D, GTGateLayout2D>
				   ()
				   {};
	};

	private final ScalablePane     		pannableCanvas;

	private final BooleanProperty    	useViewportGestures;
	private final GTViewerFXBehavior 	viewportGestures;

	public GTViewerFX() {
		this(null);
	}
	public GTViewerFX(GTGraph _model) {
		super(_model, GTViewerBase . layout2DFactory.get(), GTViewerBase . layout2DRegister.get(), 
					  GTViewerFX   . skinFxFactory.get(), 	GTViewerFX   . skinFxRegister.get());

		pannableCanvas      = new ScalablePane();
		viewportGestures    = new GTViewerFXBehavior(pannableCanvas);

		useViewportGestures = new SimpleBooleanProperty(false);
		useViewportGestures.addListener((obs, oldVal, newVal) -> {
			final Parent parent = pannableCanvas.parentProperty().get();
			if(parent == null)
				return;

			if(newVal)
				viewportGestures.applyTo(parent);
			else
				viewportGestures.unapplyFrom(parent);
		});
		pannableCanvas.parentProperty().addListener((obs, oldVal, newVal) -> {
			if (oldVal != null)
				viewportGestures.unapplyFrom(oldVal);
			if (newVal != null)
				viewportGestures.applyTo(newVal);
		});

		setModel(_model);
	}

	public ScalablePane 	getCanvas() {
		return pannableCanvas;
	}
	public double 			getScale() {
		return getCanvas().getScale();
	}

	protected void 			addNodes(List<GTView<Region, GTNode, GTNodeLayout2D>> cells) {
		cells.forEach(cell -> {
			try {
				Region cellGraphic = getGraphics(cell);
				getCanvas().getChildren().add(cellGraphic);
			} catch (final Exception e) {
				throw new RuntimeException("failed to add " + cell, e);
			}
		});
	}
	protected void 			removeNodes(List<GTView<Region, GTNode, GTNodeLayout2D>> cells) {
		cells.forEach(cell -> {
			try {
				Region cellGraphic = getGraphics(cell);
				getCanvas().getChildren().remove(cellGraphic);
			} catch (final Exception e) {
				throw new RuntimeException("failed to remove " + cell, e);
			}
		});
	}

	protected void 			addEdges(List<GTView<Region, GTEdge, GTEdgeLayout2D>> _edgeViews) {
		_edgeViews.forEach(edgeView -> {
			try {
				Region edgeGraphic = getGraphics(edgeView);
				getCanvas().getChildren().add(edgeGraphic);
			} catch (final Exception e) {
				e.printStackTrace();
				throw new RuntimeException("failed to add " + edgeView, e);
			}
		});
	}
	protected void 			removeEdges(List<GTView<Region, GTEdge, GTEdgeLayout2D>> edges) {
		edges.forEach(edge -> {
			try {
				Region edgeGraphic = getGraphics(edge);
				getCanvas().getChildren().remove(edgeGraphic);
			} catch (final Exception e) {
				throw new RuntimeException("failed to remove " + edge, e);
			}
		});
	}

}
