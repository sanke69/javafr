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
package fr.javafx.scene.chart;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static javafx.geometry.Side.LEFT;
import static javafx.geometry.Side.RIGHT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

public class XYChartPane<X, Y> extends Region implements XY.Chart<X, Y> {
	public static final String SCATTER_SYMBOL = ".chart-symbol";
	public static final String LINE_SYMBOL    = ".chart-line-symbol";
	public static final String LINE_SEGMENT   = ".chart-series-line";
	public static final String AREA_SYMBOL    = ".chart-area-symbol";
	public static final String AREA_SEGMENT   = ".chart-series-area-line";
	public static final String AREA_REGION    = ".chart-series-area-fill";
	public static final String LEGEND         = ".chart-legend";
	public static final String LEGEND_SYMBOL  = ".chart-legend-item";
	public static final String BUBBLE_CHART   = ".chart-bubble";
	public static final String BAR_CHART      = ".chart-bar";
	public static final String PIE_CHART      = ".chart-pie";
	public static final String DEFAULT_COLOR  = ".default-color";

	private static final String CHART_CSS         = XYChartPane.class.getResource("chart.css").toExternalForm();
	private static final String CHART_OVERLAY_CSS = XYChartPane.class.getResource("chart-overlay.css").toExternalForm();

	private final XYChart<X, Y> 									baseChart;

	private final Group         									overlayChartsArea     = createChildGroup();
	private final ObservableList<XYChart<X, Y>> 					overlayCharts         = FXCollections.observableList(new LinkedList<>());
	private final ListChangeListener<XYChart<X, Y>> 				overlayChartsChanged  = (change) -> {
		while(change.next()) {
			change.getAddedSubList().forEach(this::overlayChartAdded);
			change.getRemoved().forEach(this::overlayChartRemoved);
		}
		overlayChartsArea.getChildren().setAll(overlayCharts);
	};

	private final Group         									pluginsArea           = createChildGroup();
	private final Map<XY.ChartPlugin<X, Y>, Group> 					pluginGroups          = new HashMap<>();
	private final ObservableList<XY.ChartPlugin<X, Y>> 				plugins               = FXCollections.observableList(new LinkedList<>());
	private final ListChangeListener<XY.ChartPlugin<X, Y>> 			pluginsChanged        = (change) -> {
		while (change.next()) {
			change.getRemoved().forEach(this::pluginRemoved);
			change.getAddedSubList().forEach(this::pluginAdded);
		}
		updatePluginsArea();
	};

	private final Label         									titleLabel            = new Label();
	private final LegendPane        								legend                = new LegendPane();

	boolean 														layoutOngoing         = false;
	private final ChangeListener<Boolean> 							layoutRequestListener = (_obs, _old, needsLayout) -> { if(needsLayout && !layoutOngoing) requestLayout(); };

	private final BooleanProperty 									commonYAxis           = new SimpleBooleanProperty(this, "commonYAxis", false) {
		@Override
		protected void invalidated() {
			overlayCharts.stream().map(XYChart::getYAxis).forEach(XYChartPane.this::configureOverlayYAxis);
			requestLayout();
		}
	};

	final XYChartStyle<X,Y> 										style;

	public static final <X, Y> XYChartPane<X, Y> of(XY.Type _type, Axis<X> _xAxis, Axis<Y> _yAxis) {
		XYChart<X,Y> chart = null;
		switch(_type) {
			case Scatter: 		chart = new ScatterChart<X, Y>(_xAxis, _yAxis); break;
			case Line:    		chart = new LineChart<X, Y>(_xAxis, _yAxis); break;
			case Area: 			chart = new AreaChart<X, Y>(_xAxis, _yAxis); break;
			case StackedArea: 	chart = new StackedAreaChart<X, Y>(_xAxis, _yAxis); break;
			case Bar: 			chart = new BarChart<X, Y>(_xAxis, _yAxis); break;
		}
		return new XYChartPane<X, Y>(chart);
/*
		return new XYChartPane<X, Y>(switch(_type) {
										case Scatter-> 		new ScatterChart<X, Y>(_xAxis, _yAxis);
										case Line->    		new LineChart<X, Y>(_xAxis, _yAxis);
										case Area-> 		new AreaChart<X, Y>(_xAxis, _yAxis);
										case StackedArea-> 	new StackedAreaChart<X, Y>(_xAxis, _yAxis);
										case Bar-> 			new BarChart<X, Y>(_xAxis, _yAxis);
									});
*/
	}

	public XYChartPane(XYChart<X, Y> chart) {
		super();
		getStyleClass().add("chart-pane");

		baseChart = requireNonNull(chart, "The chart must not be null");
		XYChartUtils.getChartContent(baseChart).needsLayoutProperty().addListener(layoutRequestListener);
		baseChart.setAnimated(false);

		overlayCharts.addListener(overlayChartsChanged);
		plugins.addListener(pluginsChanged);

		titleLabel.setAlignment(Pos.CENTER);
		titleLabel.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		titleLabel.getStyleClass().add("chart-title");

		legend.visibleProperty().bind(legendVisibleProperty());

		getChildren().addAll(baseChart, overlayChartsArea, pluginsArea, titleLabel, legend);

		style = new XYChartStyle<X,Y>( getXYChart() );
	}

	public final Node 													getNode() {
		return this;
	}
	public final XYChart<X, Y> 											getXYChart() {
		return baseChart;
	}
	public final ObservableList<XYChart<X, Y>> 							getOverlayCharts() {
		return overlayCharts;
	}
	protected final List<XYChart<?, ?>> 								getAllCharts() {
		List<XYChart<?, ?>> allCharts = new LinkedList<>();
		allCharts.add(baseChart);
		allCharts.addAll(overlayCharts);
		return allCharts;
	}
	public final ObservableList<XY.ChartPlugin<X, Y>> 					getPlugins() {
		return plugins;
	}

	public final void 													setCommonYAxis(boolean shareYAxis) {
		commonYAxisProperty().set(shareYAxis);
	}
	public final boolean 												isCommonYAxis() {
		return commonYAxisProperty().get();
	}
	public final BooleanProperty 										commonYAxisProperty() {
		return commonYAxis;
	}

	@Override
	public String 														getUserAgentStylesheet() {
		return CHART_CSS;
	}

	private static Group 												createChildGroup() {
		Group 
		group = new Group();
		group . setManaged(false);
		group . setAutoSizeChildren(false);
		group . relocate(0, 0);
		return group;
	}

	public final Bounds 												getPlotAreaBounds() {
		Axis<X> xAxis = baseChart.getXAxis();
		Axis<Y> yAxis = baseChart.getYAxis();

		Point2D xAxisLoc = getLocationInChartPane(xAxis);
		Point2D yAxisLoc = getLocationInChartPane(yAxis);
		return new BoundingBox(xAxisLoc.getX(), yAxisLoc.getY(), xAxis.getWidth(), yAxis.getHeight());
	}
	Point2D 															getLocationInChartPane(Node node) {
		return localToChartPane(node, new Point2D(0, 0));
	}
	Point2D 															localToChartPane(Node node, Point2D point) {
		if (this.equals(node) || node.getParent() == null)
			return point;

		return localToChartPane(node.getParent(), node.localToParent(point));
	}

	public final Point2D 												toPlotArea(Point2D point) {
		return toPlotArea(point.getX(), point.getY());
	}
	public final Point2D 												toPlotArea(double xCoord, double yCoord) {
		Bounds plotAreaBounds = getPlotAreaBounds();
		return new Point2D(xCoord - plotAreaBounds.getMinX(), yCoord - plotAreaBounds.getMinY());
	}
	public final Bounds 												toPlotArea(Bounds bounds) {
		Bounds plotAreaBounds = getPlotAreaBounds();
		return new BoundingBox(bounds.getMinX() - plotAreaBounds.getMinX(), bounds.getMinY() - plotAreaBounds.getMinY(), bounds.getWidth(), bounds.getHeight());
	}
	public final Rectangle2D 											toPlotArea(Rectangle2D bounds) {
		Bounds plotAreaBounds = getPlotAreaBounds();
		return new Rectangle2D(bounds.getMinX() - plotAreaBounds.getMinX(), bounds.getMinY() - plotAreaBounds.getMinY(), bounds.getWidth(), bounds.getHeight());
	}
	
/*
	@Deprecated
	public final void 													enableAxisAutoRanging() {
		enableXAxisAutoRanging();
		enableYAxisAutoRanging();
	}
	@Deprecated
	public final void 													enableXAxisAutoRanging() {
		getXYChart().getXAxis().setAutoRanging(true);
	}
	@Deprecated
	public final void 													enableYAxisAutoRanging() {
		getXYChart().getYAxis().setAutoRanging(true);
		if (!isCommonYAxis()) {
			getOverlayCharts().forEach(chart -> chart.getYAxis().setAutoRanging(true));
		}
	}
*/
    @Override public String 											getTitle() 										{ return titleLabel.getText(); }   // { return baseChart.getTitle(); }
	@Override public void 												setTitle(String value) 							{ titleLabel.setText(value); } // { baseChart.setTitle(value); }
	@Override public StringProperty 									titleProperty() 								{ return titleLabel.textProperty(); } // { return baseChart.titleProperty(); }
	
	@Override public Side 												getTitleSide() 									{ return baseChart.getTitleSide(); }
    @Override public void 												setTitleSide(Side value) 						{ baseChart.setTitleSide(value); }
    @Override public ObjectProperty<Side> 								titleSideProperty() 							{ return baseChart.titleSideProperty(); }

    @Override public boolean 											isLegendVisible() 								{ return baseChart.isLegendVisible(); }
    @Override public void 												setLegendVisible(boolean value) 				{ baseChart.setLegendVisible(value); }
    @Override public BooleanProperty 									legendVisibleProperty() 						{ return baseChart.legendVisibleProperty(); }

    @Override public Side 												getLegendSide() 								{ return baseChart.getLegendSide(); }
    @Override public void 												setLegendSide(Side value) 						{ baseChart.setLegendSide(value); }
    @Override public ObjectProperty<Side> 								legendSideProperty() 							{ return baseChart.legendSideProperty(); }

    @Override public boolean 											getAnimated() 									{ return baseChart.getAnimated(); }
    @Override public void 												setAnimated(boolean value) 						{ baseChart.setAnimated(value); }
    @Override public BooleanProperty 									animatedProperty() 								{ return baseChart.animatedProperty(); }

    @Override public ObservableList<Series<X, Y>> 						getData() 										{ return baseChart.getData(); }
    @Override public void 												setData(ObservableList<Series<X, Y>> value) 	{ baseChart.setData(value); }
    @Override public ObjectProperty<ObservableList<Series<X, Y>>> 		dataProperty() 									{ return baseChart.dataProperty(); }

    @Override public boolean 											getVerticalGridLinesVisible() 					{ return baseChart.getVerticalGridLinesVisible(); }
    @Override public void 												setVerticalGridLinesVisible(boolean value) 		{ baseChart.setVerticalGridLinesVisible(value); }
    @Override public BooleanProperty 									verticalGridLinesVisibleProperty() 				{ return baseChart.verticalGridLinesVisibleProperty(); }

    @Override public boolean 											isHorizontalGridLinesVisible() 					{ return baseChart.isHorizontalGridLinesVisible(); }
    @Override public void 												setHorizontalGridLinesVisible(boolean value) 	{ baseChart.setHorizontalGridLinesVisible(value); }
    @Override public BooleanProperty 									horizontalGridLinesVisibleProperty() 			{ return baseChart.horizontalGridLinesVisibleProperty(); }

    @Override public boolean 											isAlternativeColumnFillVisible() 				{ return baseChart.isAlternativeColumnFillVisible(); }
    @Override public void 												setAlternativeColumnFillVisible(boolean value) 	{ baseChart.setAlternativeColumnFillVisible(value); }
    @Override public BooleanProperty 									alternativeColumnFillVisibleProperty() 			{ return baseChart.alternativeColumnFillVisibleProperty(); }

    @Override public boolean 											isAlternativeRowFillVisible() 					{ return baseChart.isAlternativeRowFillVisible(); }
    @Override public void 												setAlternativeRowFillVisible(boolean value) 	{ baseChart.setAlternativeRowFillVisible(value); }
    @Override public BooleanProperty 									alternativeRowFillVisibleProperty() 			{ return baseChart.alternativeRowFillVisibleProperty(); }

    @Override public boolean 											isVerticalZeroLineVisible() 					{ return baseChart.isVerticalZeroLineVisible(); }
    @Override public void 												setVerticalZeroLineVisible(boolean value) 		{ baseChart.setVerticalZeroLineVisible(value); }
    @Override public BooleanProperty 									verticalZeroLineVisibleProperty() 				{ return baseChart.verticalZeroLineVisibleProperty(); }

    @Override public boolean 											isHorizontalZeroLineVisible() 					{ return baseChart.isHorizontalZeroLineVisible(); }
    @Override public void 												setHorizontalZeroLineVisible(boolean value) 	{ baseChart.setHorizontalZeroLineVisible(value); }
    @Override public BooleanProperty 									horizontalZeroLineVisibleProperty() 			{ return baseChart.horizontalZeroLineVisibleProperty(); }
	
//    @Override public List<CssMetaData<? extends Styleable, ?>> 			getCssMetaData() 								{ return baseChart.getCssMetaData(); }
	
    @Override public Axis<X> 											getXAxis() 										{ return baseChart.getXAxis(); }
	@Override public Axis<Y> 											getYAxis() 										{ return baseChart.getYAxis(); }

	private static Side 												getEffectiveVerticalSide(Axis<?> axis) {
		return axis.getSide().isVertical() ? axis.getSide() : LEFT;
	}


	public void 														setStyle(XYChart.Series<X, Y> _series, XY.Series.Style _style) {
		style.styles.put(_series, _style);
	}
	
	// Internal Pane Layout Methods
	@Override
	protected void 														layoutChildren() {
		layoutOngoing = true;
		new LayoutManager().layout();
		layoutPluginsChildren();
		layoutOngoing = false;
	}
	private void 														layoutPluginsChildren() {
		for(XY.ChartPlugin<X, Y> plugin : plugins)
			plugin.layoutChildren();
	}

	private void 														overlayChartAdded(XYChart<X, Y> chart) {
		applyOverlayStyle(chart);
		makeTransparentToMouseEventsExceptPlotContent(chart);
		configureOverlayXAxis(chart.getXAxis());
		configureOverlayYAxis(chart.getYAxis());
		XYChartUtils.getChartContent(chart).needsLayoutProperty().addListener(layoutRequestListener);
	}
	private void 														overlayChartRemoved(XYChart<?, ?> chart) {
		unbindAxisBounds(chart.getXAxis());
		unbindAxisBounds(chart.getYAxis());
		XYChartUtils.getChartContent(chart).needsLayoutProperty().removeListener(layoutRequestListener);
	}

	private void 														pluginAdded(XY.ChartPlugin<X, Y> plugin) {
		plugin.setChartPane(this);
		Group group = createChildGroup();
		Bindings.bindContent(group.getChildren(), plugin.getChartChildren());
		pluginGroups.put(plugin, group);
	}
	private void 														pluginRemoved(XY.ChartPlugin<X, Y> plugin) {
		plugin.setChartPane(null);
		Group group = pluginGroups.remove(plugin);
		Bindings.unbindContent(group, plugin.getChartChildren());
		group.getChildren().clear();
		pluginsArea.getChildren().remove(group);
	}

	private void 														applyOverlayStyle(XYChart<?, ?> chart) {
		chart.getStylesheets().add(CHART_OVERLAY_CSS);
		chart.setTitle(null);
	}
	private void 														makeTransparentToMouseEventsExceptPlotContent(XYChart<?, ?> chart) {
		makeTransparentToMouseEvents(chart, XYChartUtils.getPlotContent(chart));
	}
	private boolean 													makeTransparentToMouseEvents(Node node, Node plotContent) {
		node.setPickOnBounds(false);
		if (node == plotContent) {
			return true;
		}

		boolean containsPlotContent = false;
		if (node instanceof Parent) {
			for (Node childNode : ((Parent) node).getChildrenUnmodifiable()) {
				if (makeTransparentToMouseEvents(childNode, plotContent)) {
					containsPlotContent = true;
				}
			}
		}
		node.setMouseTransparent(!containsPlotContent);
		return containsPlotContent;
	}
	private void 														configureOverlayXAxis(Axis<?> xAxis) {
		xAxis.setOpacity(0);
		xAxis.setAutoRanging(false);
		bindAxisBounds(xAxis, baseChart.getXAxis());
	}
	private void 														configureOverlayYAxis(Axis<Y> yAxis) {
		if (isCommonYAxis()) {
			yAxis.setOpacity(0);
			yAxis.setAutoRanging(false);
			yAxis.setSide(getEffectiveVerticalSide(baseChart.getYAxis()));
			bindAxisBounds(yAxis, baseChart.getYAxis());
		} else {
			yAxis.setOpacity(1);
			yAxis.prefWidthProperty().set(USE_COMPUTED_SIZE);
			unbindAxisBounds(yAxis);
		}
	}

	private void 														bindAxisBounds(Axis<?> axis, Axis<?> baseAxis) {
		if (XYChartUtils.Axes.isValueAxis(axis) && XYChartUtils.Axes.isValueAxis(baseAxis)) {
			XYChartUtils.Axes.bindBounds(XYChartUtils.Axes.toValueAxis(axis), XYChartUtils.Axes.toValueAxis(baseAxis));
		}
	}
	private void 														unbindAxisBounds(Axis<?> axis) {
		if (XYChartUtils.Axes.isValueAxis(axis)) {
			XYChartUtils.Axes.unbindBounds(XYChartUtils.Axes.toValueAxis(axis));
		}
	}

	private void 														updatePluginsArea() {
		pluginsArea.getChildren().setAll(
											plugins.stream().map(plugin -> pluginGroups.get(plugin)).collect(toList()) );
		requestLayout();
	}

	private class LayoutManager {
		final Region 				baseChartContent;
		final Map<Axis<Y>, Double> 	yAxesPrefWidths;
		final double 				overlayYAxisTotalWidth;
		double 						baseChartContentXOffset;
		double 						top, left, bottom, right;
		final double 				chartPaneWidth  = getWidth();
		final double 				chartPaneHeight = getHeight();

		LayoutManager() {
			top    = baseChart.snappedTopInset();
			bottom = baseChart.snappedBottomInset();
			left   = baseChart.snappedLeftInset();
			right  = baseChart.snappedRightInset();

			baseChartContent = XYChartUtils.getChartContent(baseChart);

			if (overlayCharts.isEmpty() || isCommonYAxis()) {
				yAxesPrefWidths         = Collections.emptyMap();
				overlayYAxisTotalWidth  = 0;
				baseChartContentXOffset = 0;
			} else {
				yAxesPrefWidths         = yAxesPrefWidths( getHeight() );
				overlayYAxisTotalWidth  = yAxisTotalWidth();
				baseChartContentXOffset = baseChartContentXOffset();
			}
		}

		private Map<Axis<Y>, Double> 	yAxesPrefWidths(double height) {
			Map<Axis<Y>, Double> yAxesWidths = new HashMap<>();
			for (XYChart<X, Y> chart : overlayCharts) {
				Axis<Y> yAxis = chart.getYAxis();
				updateYAxisRange(chart);
				yAxis.prefWidthProperty().set(USE_COMPUTED_SIZE);
				yAxesWidths.put(yAxis, Math.ceil(yAxis.prefWidth(height)));
			}
			return yAxesWidths;
		}
		private void 					updateYAxisRange(XYChart<X, Y> chart) {
			if (!chart.getYAxis().isAutoRanging())
				return;

			List<Y> yData = new ArrayList<>();
			for (Series<X, Y> series : chart.getData())
				for (Data<X, Y> data : series.getData())
					yData.add(data.getYValue());

			if (!yData.isEmpty())
				chart.getYAxis().invalidateRange(yData);
		}

		private double 					yAxisTotalWidth() {
			double axesWidth = yAxesPrefWidths.values().stream().mapToDouble(Double::doubleValue).sum();
			for (int i = 0; i < overlayCharts.size() - 1; i++) {
				XYChart<X, Y> chart         = overlayCharts.get(i);
				Side          effectiveSide = getEffectiveVerticalSide(chart.getYAxis());

				axesWidth += effectiveSide == LEFT ? chart.snappedLeftInset() : chart.snappedRightInset();
			}
			return axesWidth;
		}

		private double 					baseChartContentXOffset() {
			double offset = 0;

			for (XYChart<X, Y> chart : overlayCharts)
				if (getEffectiveVerticalSide(chart.getYAxis()) == LEFT)
					offset += prefWidth(chart.getYAxis()) + chart.snappedLeftInset();

			return offset;
		}

		void 							layout() {
			updateLegend();

			layoutTitle();
			layoutLegend();
			layoutBaseChart();
			layoutOverlayCharts();
		}

		private void					updateLegend() {
			List<Pane>  allLegends  = getAllCharts().stream()
													.map(c -> (Pane) c.lookup(LEGEND))
													.filter(Objects::nonNull)
													.collect(toList());
			List<Label> legendItems = allLegends	.stream()
													.flatMap(p -> p.getChildrenUnmodifiable().stream())
													.filter(n -> n instanceof Label)
													.map(n -> (Label) n)
													.collect(Collectors.toList());
			legend.update(legendItems);

			allLegends  . forEach(legend -> legend.setPrefSize(0, 0));
			legendItems . forEach(item   -> item.setVisible(false));
		}
		
		private void 					layoutTitle() {
			getAllCharts().forEach(chart -> chart.setTitle(null));

			if (getTitle() == null) {
				titleLabel.setVisible(false);
			} else {
				titleLabel.setVisible(true);
				if (getTitleSide().isHorizontal()) {
					double titleHeight = snapSize(titleLabel.prefHeight(chartPaneWidth - left - right));

					if (getTitleSide().equals(Side.TOP)) {
						titleLabel.resizeRelocate(left, top, chartPaneWidth - left - right, titleHeight);
						top += titleHeight;
					} else {
						titleLabel.resizeRelocate(left, chartPaneHeight - bottom - titleHeight,
								chartPaneWidth - left - right, titleHeight);
						bottom += titleHeight;
					}
				} else {
					double titleWidth = snapSize(titleLabel.prefWidth(chartPaneHeight - top - bottom));

					if (getTitleSide().equals(Side.LEFT)) {
						titleLabel.resizeRelocate(left, top, titleWidth, chartPaneHeight - top - bottom);
						left += titleWidth;
					} else {
						titleLabel.resizeRelocate(chartPaneWidth - right - titleWidth, top, titleWidth,
								chartPaneHeight - top - bottom);
						right += titleWidth;
					}
				}
			}
		}
		private void 					layoutLegend() {
			if(!isLegendVisible()) {
				legend.resize(0, 0);
				return;
			}

			double legendY, legendX, legendWidth, legendHeight;

			if (getLegendSide().isHorizontal()) {
				legendHeight = legend.prefHeight(chartPaneWidth - left - right);
				legendWidth  = legend.prefWidth(legendHeight);

				legendX      = left + (chartPaneWidth - left - right - legendWidth) / 2;

				if (getLegendSide() == Side.TOP) {
					legendY = top;
					top += legendHeight;
				} else {
					legendY = chartPaneHeight - bottom - legendHeight;
					bottom += legendHeight;
				}

			} else {
				legendWidth  = legend.prefWidth(chartPaneHeight - top - bottom);
				legendHeight = legend.prefHeight(legendWidth);

				if (getLegendSide() == Side.LEFT) {
					legendX = left;
					left += legendWidth;
				} else {
					legendX = chartPaneWidth - right - legendWidth;
					right += legendWidth;
				}

				legendY = (chartPaneHeight - top - bottom - legendHeight) / 2;
			}

			legend.resizeRelocate(legendX, legendY, legendWidth, legendHeight);
		}
		private void 					layoutBaseChart() {
			double baseChartWidth  = chartPaneWidth - overlayYAxisTotalWidth - left - right;
			double baseChartHeight = chartPaneHeight - top - bottom;

			baseChart.resizeRelocate(0, 0, baseChartWidth, baseChartHeight);
			baseChart.requestLayout();
			baseChart.layout();

			baseChartContent.setTranslateX(baseChartContentXOffset + left);
			baseChartContent.setTranslateY(top);
		}
		private void 					layoutOverlayCharts() {
			if(overlayCharts.isEmpty())
				return;

			XYChart<X, Y> prevChart     = baseChart;
			Side          effectiveSide = getEffectiveVerticalSide(baseChart.getYAxis());
			Axis<Y>       prevLeftAxis  = effectiveSide == LEFT ? baseChart.getYAxis() : null;
			Axis<Y>       prevRightAxis = effectiveSide == RIGHT ? baseChart.getYAxis() : null;

			for (XYChart<X, Y> chart : overlayCharts) {
				Region chartContent = XYChartUtils.getChartContent(chart);
				chart.resizeRelocate(0, 0, chartWidth(chart, chartContent), chartHeight(chart, chartContent));

				Axis<X> xAxis = chart.getXAxis();
				Axis<Y> yAxis = chart.getYAxis();
				copyXAxisCategories(xAxis);
				adjustPrefSize(xAxis, yAxis);

				chart.requestLayout();
				chart.layout();

				chartContent.setTranslateX(contentLayoutX(chartContent, yAxis) - chartContent.getLayoutX());
				chartContent.setTranslateY(contentLayoutY(chartContent) - chartContent.getLayoutY());

				if (!isCommonYAxis()) {
					if (getEffectiveVerticalSide(yAxis) == LEFT) {
						yAxis.setTranslateX(leftAxisLayoutX(prevChart, prevLeftAxis, xAxis, yAxis) - yAxis.getLayoutX());
						prevLeftAxis = yAxis;
					} else {
						yAxis.setTranslateX(rightAxisLayoutX(prevChart, prevRightAxis, xAxis) - yAxis.getLayoutX());
						prevRightAxis = yAxis;
					}
				}
				prevChart = chart;
			}
		}

		private void 					copyXAxisCategories(Axis<X> xAxis) {
			if (XYChartUtils.Axes.isCategoryAxis(xAxis) && XYChartUtils.Axes.isCategoryAxis(baseChart.getXAxis()))
				((CategoryAxis) xAxis).setCategories(((CategoryAxis) baseChart.getXAxis()).getCategories());
		}

		private double 					prefWidth(Axis<Y> yAxis) {
			return yAxesPrefWidths.get(yAxis).doubleValue();
		}
		private void 					adjustPrefSize(Axis<X> xAxis, Axis<Y> yAxis) {
			xAxis.prefHeightProperty().set(USE_COMPUTED_SIZE);
			xAxis.prefHeight(baseChart.getXAxis().getWidth());
			xAxis.prefHeightProperty().set(baseChart.getXAxis().getHeight());
			xAxis.prefWidthProperty().set(baseChart.getXAxis().getWidth());

			yAxis.prefWidthProperty().set(USE_COMPUTED_SIZE);
			yAxis.prefWidth(baseChart.getYAxis().getHeight());
			yAxis.prefWidthProperty().set(isCommonYAxis() ? baseChart.getYAxis().getWidth() : prefWidth(yAxis));
			yAxis.prefHeightProperty().set(baseChart.getYAxis().getHeight());
		}

		private double 					chartWidth(XYChart<X, Y> chart, Region chartContent) {
			double chartWidth = baseChart.getXAxis().getWidth();
			chartWidth += XYChartUtils.getHorizontalInsets(chart.getInsets());
			chartWidth += XYChartUtils.getHorizontalInsets(chartContent.getInsets());
			chartWidth += isCommonYAxis() ? baseChart.getYAxis().getWidth() : prefWidth(chart.getYAxis());
			return snapSize(chartWidth);
		}
		private double 					chartHeight(XYChart<X, Y> chart, Region chartContent) {
			double chartHeight = baseChart.getYAxis().getHeight();
			chartHeight += XYChartUtils.getVerticalInsets(chart.getInsets());
			chartHeight += XYChartUtils.getVerticalInsets(chartContent.getInsets());
			chartHeight += baseChart.getXAxis().getHeight();
			return snapSize(chartHeight);
		}

		private double 					contentLayoutX(Region chartContent, Axis<Y> yAxis) {
			double xOffset = getEffectiveVerticalSide(yAxis) == LEFT ? yAxis.getWidth() : 0;
			return XYChartUtils.getLocationX(baseChartContent) + baseChart.getXAxis().getLayoutX() - xOffset - chartContent.snappedLeftInset();
		}
		private double 					contentLayoutY(Region chartContent) {
			return XYChartUtils.getLocationY(baseChartContent) + baseChart.getYAxis().getLayoutY() - chartContent.snappedTopInset();
		}

		private double 					leftAxisLayoutX(XYChart<X, Y> prevChart, Axis<Y> prevLeftAxis, Axis<X> xAxis, Axis<Y> yAxis) {
			double 
			layoutX  = (prevLeftAxis == null) ? xAxis.getLayoutX() : XYChartUtils.getLocationX(prevLeftAxis) - prevChart.snappedLeftInset();
			layoutX -= yAxis.getWidth();
			return layoutX;
		}
		private double 					rightAxisLayoutX(XYChart<X, Y> prevChart, Axis<Y> prevRightAxis, Axis<X> xAxis) {
			if (prevRightAxis == null)
				return xAxis.getLayoutX() + xAxis.getWidth();
			return XYChartUtils.getLocationX(prevRightAxis) + prevRightAxis.getWidth() + prevChart.snappedRightInset();
		}

	}

	static class LegendPane extends TilePane {
		private static final int GAP = 5;

		LegendPane() {
			super(GAP, GAP);
			setTileAlignment(Pos.CENTER_LEFT);
		}

		void update(List<Label> legendItems) {
			if (legendItemsChanged(legendItems)) {
				getChildren().clear();

				for(Label item : legendItems)
					getChildren().add( clone(item) );
			}
		}

		private boolean legendItemsChanged(List<Label> legendItems) {
			if (getChildren().size() != legendItems.size())
				return true;

			for (int i = 0, size = getChildren().size(); i < size; i++) {
				Label oldItem = (Label) getChildren().get(i);
				Label newItem = legendItems.get(i);

				if(legendLabelsDiffer(oldItem, newItem)
				|| oldItem.getStyle().compareTo(newItem.getStyle()) != 0
				|| !oldItem.getStylesheets().equals(newItem.getStylesheets()))
					return true;

			}
			return false;
		}

		private static boolean legendLabelsDiffer(Label label1, Label label2) {
			if (!Objects.equals(label1.getText(), label2.getText())) {
				return true;
			}
			if (label1.getGraphic() == null ^ label2.getGraphic() == null) {
				return true;
			}
			if (label1.getGraphic() != null) {
				Node graphic1 = label1.getGraphic();
				Node graphic2 = label2.getGraphic();

				if (!graphic1.getClass().equals(graphic2.getClass())
				|| !graphic1.getStyleClass().equals(graphic2.getStyleClass())) {
					return true;
				}
			}
			return false;
		}

		private static Node clone(Label src) {
			Label 
			label = new Label(src.getText());
			label . getStyleClass().setAll(src.getStyleClass());
			label . setStyle( src.getStyle() );

			label . setAlignment(Pos.CENTER_LEFT);
			label . setContentDisplay(ContentDisplay.LEFT);
			
			if(src.getGraphic() != null 
			&& src.getGraphic().getClass().equals(Region.class)) {
				Region symbol = new Region();
				symbol.getStyleClass().setAll(src.getGraphic().getStyleClass());
				symbol.setStyle( src.getGraphic().getStyle() );

				label.setGraphic(symbol);
			}

			return label;
		}

	}

}
