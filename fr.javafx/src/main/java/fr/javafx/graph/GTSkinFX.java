package fr.javafx.graph;

import java.lang.reflect.Constructor;
import java.util.function.BiFunction;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.layouts.GTEdgeLayout2D;
import fr.java.graph.layouts.GTGateLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;
import fr.java.graph.utils.GTSkinProvider;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.math.geometry.BoundingBox.Anchor;
import fr.java.patterns.capabilities.Identifiable;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Region;

public interface GTSkinFX<GTO extends Identifiable, GTL extends GTLayout> extends GTSkin<Region, GTO, GTL> {

	public interface Node extends GTSkinFX<GTNode, GTNodeLayout2D> {
		public default DoubleBinding 	getXAnchor(GTViewerFX _graph, Anchor _anchor) {
			final Region graphic = _graph.getGraphics(this);

			switch(_anchor) {
			case NORTH_EAST:
			case SOUTH_EAST:
			case EAST:			return graphic.layoutXProperty().add(graphic.widthProperty());

			case NORTH_WEST:
			case SOUTH_WEST:
			case WEST:			return graphic.layoutXProperty().multiply(1);

			case CENTER:
			case NORTH:
			case SOUTH:			
			default:			return graphic.layoutXProperty().add(graphic.widthProperty().divide(2));
			}
		}
		public default DoubleBinding 	getYAnchor(GTViewerFX _graph, Anchor _anchor) {
			final Region graphic = _graph.getGraphics(this);
			
			switch(_anchor) {
			case NORTH_EAST:
			case NORTH_WEST:
			case NORTH:			return graphic.layoutYProperty().multiply(1);

			case SOUTH_EAST:
			case SOUTH_WEST:
			case SOUTH:			return graphic.layoutYProperty().add(graphic.heightProperty());

			case CENTER:
			case EAST:
			case WEST:
			default:			return graphic.layoutYProperty().add(graphic.heightProperty().divide(2));
			}
		}
	}
	public interface Edge extends GTSkinFX<GTEdge, GTEdgeLayout2D> {}
	public interface Gate extends GTSkinFX<GTGate, GTGateLayout2D> {}

	public static GTSkin.Generator<Region, GTNode, GTNodeLayout2D> 	ofNode(Class<? extends GTSkin<Region, GTNode, GTNodeLayout2D>> _skinClass) {
		return new GTSkinProvider<Region, GTNode, GTNodeLayout2D>(GTNodeLayout2D.class, _skinClass);
	};
	public static GTSkin.Generator<Region, GTNode, GTNodeLayout2D> 	ofNode(Class<? extends GTSkin<Region,GTNode, GTNodeLayout2D>> _skinClass, Object... _skinParameters) {
		return new GTSkinProvider<Region, GTNode, GTNodeLayout2D>(GTNodeLayout2D.class, _skinClass, _skinParameters);
	};
	public static GTSkin.Generator<Region, GTNode, GTNodeLayout2D> 	ofNode(Class<? extends GTSkin<Region,GTNode, GTNodeLayout2D>> _skinClass, Constructor<GTSkin<Region,GTNode, GTNodeLayout2D>> _skinConstructor, Object... _skinParameters) {
		return new GTSkinProvider<Region, GTNode, GTNodeLayout2D>(GTNodeLayout2D.class, _skinClass, _skinConstructor, _skinParameters);
	};
	public static GTSkin.Generator<Region, GTNode, GTNodeLayout2D> 	ofNode(BiFunction<GTNode, GTNodeLayout2D, GTSkin<Region,GTNode, GTNodeLayout2D>> _skinSupplier) {
		return new GTSkinProvider<Region, GTNode, GTNodeLayout2D>(GTNodeLayout2D.class, _skinSupplier);
	};

	public static GTSkin.Generator<Region, GTEdge, GTEdgeLayout2D> 	ofEdge(Class<? extends GTSkin<Region, GTEdge, GTEdgeLayout2D>> _skinClass) {
		return new GTSkinProvider<Region, GTEdge, GTEdgeLayout2D>(GTEdgeLayout2D.class, _skinClass);
	};
	public static GTSkin.Generator<Region, GTEdge, GTEdgeLayout2D> 	ofEdge(Class<? extends GTSkin<Region, GTEdge, GTEdgeLayout2D>> _skinClass, Object... _skinParameters) {
		return new GTSkinProvider<Region, GTEdge, GTEdgeLayout2D>(GTEdgeLayout2D.class, _skinClass, _skinParameters);
	};
	public static GTSkin.Generator<Region, GTEdge, GTEdgeLayout2D> 	ofEdge(Class<? extends GTSkin<Region, GTEdge, GTEdgeLayout2D>> _skinClass, Constructor<GTSkin<Region, GTEdge, GTEdgeLayout2D>> _skinConstructor, Object... _skinParameters) {
		return new GTSkinProvider<Region, GTEdge, GTEdgeLayout2D>(GTEdgeLayout2D.class, _skinClass, _skinConstructor, _skinParameters);
	};
	public static GTSkin.Generator<Region, GTEdge, GTEdgeLayout2D> 	ofEdge(BiFunction<GTEdge, GTEdgeLayout2D, GTSkin<Region, GTEdge, GTEdgeLayout2D>> _skinSupplier) {
		return new GTSkinProvider<Region, GTEdge, GTEdgeLayout2D>(GTEdgeLayout2D.class, _skinSupplier);
	};

	public static GTSkin.Generator<Region, GTGate, GTGateLayout2D> 	ofGate(Class<? extends GTSkin<Region, GTGate, GTGateLayout2D>> _skinClass) {
		return new GTSkinProvider<Region, GTGate, GTGateLayout2D>(GTGateLayout2D.class, _skinClass);
	};
	public static GTSkin.Generator<Region, GTGate, GTGateLayout2D> 	ofGate(Class<? extends GTSkin<Region, GTGate, GTGateLayout2D>> _skinClass, Object... _skinParameters) {
		return new GTSkinProvider<Region, GTGate, GTGateLayout2D>(GTGateLayout2D.class, _skinClass, _skinParameters);
	};
	public static GTSkin.Generator<Region, GTGate, GTGateLayout2D> 	ofGate(Class<? extends GTSkin<Region, GTGate, GTGateLayout2D>> _skinClass, Constructor<GTSkin<Region, GTGate, GTGateLayout2D>> _skinConstructor, Object... _skinParameters) {
		return new GTSkinProvider<Region, GTGate, GTGateLayout2D>(GTGateLayout2D.class, _skinClass, _skinConstructor, _skinParameters);
	};
	public static GTSkin.Generator<Region, GTGate, GTGateLayout2D> 	ofGate(BiFunction<GTGate, GTGateLayout2D, GTSkin<Region, GTGate, GTGateLayout2D>> _skinSupplier) {
		return new GTSkinProvider<Region, GTGate, GTGateLayout2D>(GTGateLayout2D.class, _skinSupplier);
	};

}
