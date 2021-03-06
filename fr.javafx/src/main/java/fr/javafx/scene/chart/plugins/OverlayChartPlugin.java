/**
 * JavaFR
 * Copyright (C) 2007-?XYZ  Steve PECHBERTI <steve.pechberti@laposte.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.javafx.scene.chart.plugins;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;

public class OverlayChartPlugin<X, Y> extends AbstractChartPlugin<X, Y> {

    private final ObjectProperty<Node> 			node        = new SimpleObjectProperty<>(this, "node");
    private final ObjectProperty<OverlayArea> 	overlayArea = new SimpleObjectProperty<OverlayArea>(this, "overlayArea") {
        @Override
        protected void invalidated() {
            layoutChildren();
        }
    };

    public enum OverlayArea { CHART_PANE, PLOT_AREA }

    public OverlayChartPlugin(OverlayArea area) {
        this(area, null);
    }
    public OverlayChartPlugin(OverlayArea area, Node node) {
        setOverlayArea(area);
        nodeProperty().addListener((obs, oldNode, newNode) -> {
            getChartChildren().remove(oldNode);
            if (newNode != null) {
                getChartChildren().add(newNode);
            }
            layoutChildren();
        });
        setNode(node);
    }

    public final void 							setNode(Node _node) {
    	node.set(_node);
    }
    public final Node 							getNode() {
        return node.get();
    }
    public final ObjectProperty<Node> 			nodeProperty() {
        return node;
    }

    public final void 							setOverlayArea(OverlayArea _area) {
    	overlayArea.set(_area);
    }
    public final OverlayArea 					getOverlayArea() {
        return overlayArea.get();
    }
    public final ObjectProperty<OverlayArea> 	overlayAreaProperty() {
        return overlayArea;
    }

    @Override
    public void 								layoutChildren() {
        if (getChartPane() == null || getNode() == null)
            return;

        if (getOverlayArea() == OverlayArea.CHART_PANE)
            getNode().resizeRelocate(0, 0, getChartPane().getWidth(), getChartPane().getHeight());
        else {
            Bounds plotArea = getChartPane().getPlotAreaBounds();
            getNode().resizeRelocate(plotArea.getMinX(), plotArea.getMinY(), plotArea.getWidth(), plotArea.getHeight());
        }
    }

}
