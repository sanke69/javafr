package fr.javafx.temp.graphics.fx.api.charts.multiaxes;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import fr.javafx.temp.graphics.fx.api.charts.addons.DetailsPopup;

public class MultipleAxesXYChart extends BorderPane {

    private final ObservableList<LineChart> seriesCharts = FXCollections.observableArrayList();
    private final Map<LineChart, Color> chartColorMap = new HashMap<>();

    private final double yAxisWidth = 60;
    private final AnchorPane detailsWindow;

    private final double yAxisSeparation = 20;
    private double strokeWidth = 0.3;

	private final StackPane chartPane = new StackPane();

    public MultipleAxesXYChart() {
        super();

        chartPane.setAlignment(Pos.CENTER_LEFT);

        seriesCharts.addListener((Observable observable) -> rebuildChart());

        detailsWindow = new AnchorPane();

        rebuildChart();
    }

    public void addSeries(XYChart.Series series, Color lineColor) {
    	Platform.runLater(() -> {
	        NumberAxis yAxis = new NumberAxis();
	        NumberAxis xAxis = new NumberAxis();
	
	        // style x-axis
	        xAxis.setAutoRanging(false);

	        // style y-axis
	        yAxis.setLabel(series.getName());

	        if(seriesCharts.size() != 0) {
		        xAxis.lowerBoundProperty() . bind(((NumberAxis) seriesCharts.get(0).getXAxis()).lowerBoundProperty());
		        xAxis.upperBoundProperty() . bind(((NumberAxis) seriesCharts.get(0).getXAxis()).upperBoundProperty());
		        xAxis.tickUnitProperty()   . bind(((NumberAxis) seriesCharts.get(0).getXAxis()).tickUnitProperty());
		        xAxis.setVisible(false);
		        xAxis.setOpacity(0.0);
		        yAxis.setSide(Side.RIGHT);
	        } else {
	        	xAxis.setLowerBound(0);
	        	xAxis.setUpperBound(series.getData().size());
	        	xAxis.setTickUnit(200);
		        xAxis.setVisible(true);
		        xAxis.setOpacity(1.0);
		        yAxis.setSide(Side.LEFT);
	        }
	
	        // create chart
	        LineChart lineChart = new LineChart(xAxis, yAxis);
	        lineChart.setAnimated(false);
	        lineChart.setLegendVisible(seriesCharts.size() == 0 ? true : false);
	        lineChart.getData().add(series);

	        // setFixedAxisWidth
	        lineChart.getYAxis().setPrefWidth(yAxisWidth);
	        lineChart.getYAxis().setMaxWidth(yAxisWidth);

	        // styleChartLine
	        lineChart.getYAxis().lookup(".axis-label").setStyle("-fx-text-fill: " + toRGBCode(lineColor) + "; -fx-font-weight: bold;");
	        Node seriesLine = lineChart.lookup(".chart-series-line");
	        seriesLine.setStyle("-fx-stroke: " + toRGBCode(lineColor) + "; -fx-stroke-width: " + strokeWidth + ";");

        	lineChart.setCreateSymbols(false);
        	
	        if(seriesCharts.size() == 0) {
	        	lineChart.setLegendVisible(false);
	        	lineChart.getXAxis().setAutoRanging(false);
	        	lineChart.getXAxis().setAnimated(false);
	        	lineChart.getYAxis().setAnimated(false);

		        bindMouseEvents(lineChart, this.strokeWidth);
	        } else {
	            lineChart.setVerticalZeroLineVisible(false);
	            lineChart.setHorizontalZeroLineVisible(false);
	            lineChart.setVerticalGridLinesVisible(false);
	            lineChart.setHorizontalGridLinesVisible(false);
	            
	            // set transparent background
	            lineChart.lookup(".chart-content").lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
	        }
	
	        chartColorMap.put(lineChart, lineColor);
	        seriesCharts.add(lineChart);

	        // Update Legends
	        setBottom(getLegend());
    	});
    }

    protected Node getLegend() {
        HBox hBox = new HBox();
        
        if(seriesCharts.size() != 0) {
	        final CheckBox baseChartCheckBox = new CheckBox(seriesCharts.get(0).getYAxis().getLabel());
	        baseChartCheckBox.setSelected(true);
	        baseChartCheckBox.setStyle("-fx-text-fill: " + toRGBCode(chartColorMap.get(seriesCharts.get(0))) + "; -fx-font-weight: bold;");
	        baseChartCheckBox.setDisable(true);
	        baseChartCheckBox.getStyleClass().add("readonly-checkbox");
	        baseChartCheckBox.setOnAction(event -> baseChartCheckBox.setSelected(true));
	        hBox.getChildren().add(baseChartCheckBox);

	        for(int i = 1; i < seriesCharts.size(); ++i) {
	        	LineChart lineChart = seriesCharts.get(i);
	            CheckBox checkBox = new CheckBox(lineChart.getYAxis().getLabel());
	            checkBox.setStyle("-fx-text-fill: " + toRGBCode(chartColorMap.get(lineChart)) + "; -fx-font-weight: bold");
	            checkBox.setSelected(true);
	            checkBox.setOnAction(event -> {
	                if (seriesCharts.contains(lineChart)) {
	                    seriesCharts.remove(lineChart);
	                } else {
	                    seriesCharts.add(lineChart);
	                }
	            });
	            hBox.getChildren().add(checkBox);
	        }
        }

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setStyle("-fx-padding: 0 10 20 10");

        return hBox;
    }


    private void rebuildChart() {
    	chartPane.getChildren().clear();

        for(LineChart chart : seriesCharts)
        	chartPane.getChildren().add(resizeChart(chart));

        if(seriesCharts.size() != 0)
        	chartPane.getChildren().add(detailsWindow);

        setCenter(chartPane);
    }
    private Node resizeChart(LineChart lineChart) {
        HBox hBox = new HBox(lineChart);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.prefHeightProperty().bind(chartPane.heightProperty());
        hBox.prefWidthProperty().bind(chartPane.widthProperty());

        ObservableValue<? extends Number> shift = chartPane.widthProperty().subtract((yAxisWidth + yAxisSeparation) * (seriesCharts.size()-1));
        lineChart.minWidthProperty()  . bind(shift);
        lineChart.prefWidthProperty() . bind(shift);
        lineChart.maxWidthProperty()  . bind(shift);
        
        if(seriesCharts.indexOf(lineChart) != 0) {
            hBox.setMouseTransparent(true);
	        lineChart.translateXProperty().bind(seriesCharts.get(0).getYAxis().widthProperty());
	        lineChart.getYAxis().setTranslateX((yAxisWidth + yAxisSeparation) * (seriesCharts.indexOf(lineChart)-1));
        }

        return hBox;
    }

    private void bindMouseEvents(LineChart baseChart, Double strokeWidth) {
        final DetailsPopup detailsPopup = new DetailsPopup(seriesCharts, chartColorMap);
        chartPane.getChildren().add(detailsWindow);
        detailsWindow.getChildren().add(detailsPopup);
        detailsWindow.prefHeightProperty().bind(chartPane.heightProperty());
        detailsWindow.prefWidthProperty().bind(chartPane.widthProperty());
        detailsWindow.setMouseTransparent(true);

        chartPane.setOnMouseMoved(null);
        chartPane.setMouseTransparent(false);

        final Axis xAxis = baseChart.getXAxis();
        final Axis yAxis = baseChart.getYAxis();

        final Line xLine = new Line();
        final Line yLine = new Line();
        yLine.setFill(Color.GRAY);
        xLine.setFill(Color.GRAY);
        yLine.setStrokeWidth(strokeWidth/2);
        xLine.setStrokeWidth(strokeWidth/2);
        xLine.setVisible(false);
        yLine.setVisible(false);

        final Node chartBackground = baseChart.lookup(".chart-plot-background");
        for (Node n: chartBackground.getParent().getChildrenUnmodifiable()) {
            if (n != chartBackground && n != xAxis && n != yAxis) {
                n.setMouseTransparent(true);
            }
        }
        chartBackground.setCursor(Cursor.CROSSHAIR);
        chartBackground.setOnMouseEntered((event) -> {
            chartBackground.getOnMouseMoved().handle(event);
            detailsPopup.setVisible(true);
            xLine.setVisible(true);
            yLine.setVisible(true);
            detailsWindow.getChildren().addAll(xLine, yLine);
        });
        chartBackground.setOnMouseExited((event) -> {
            detailsPopup.setVisible(false);
            xLine.setVisible(false);
            yLine.setVisible(false);
            detailsWindow.getChildren().removeAll(xLine, yLine);
        });
        chartBackground.setOnMouseMoved(event -> {
            double x = event.getX() + chartBackground.getLayoutX();
            double y = event.getY() + chartBackground.getLayoutY();

            xLine.setStartX(10);
            xLine.setEndX(detailsWindow.getWidth()-10);
            xLine.setStartY(y+5);
            xLine.setEndY(y+5);

            yLine.setStartX(x+5);
            yLine.setEndX(x+5);
            yLine.setStartY(10);
            yLine.setEndY(detailsWindow.getHeight()-10);

            detailsPopup.showChartDescription(event);

            if (y + detailsPopup.getHeight() + 10 < chartPane.getHeight()) {
                AnchorPane.setTopAnchor(detailsPopup, y+10);
            } else {
                AnchorPane.setTopAnchor(detailsPopup, y-10-detailsPopup.getHeight());
            }

            if (x + detailsPopup.getWidth() + 10 < chartPane.getWidth()) {
                AnchorPane.setLeftAnchor(detailsPopup, x+10);
            } else {
                AnchorPane.setLeftAnchor(detailsPopup, x-10-detailsPopup.getWidth());
            }
        });
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

}