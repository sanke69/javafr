package fr.javafx.temp.graphics.fx.api.charts.addons;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DetailsPopup extends VBox {
	final private ObservableList<LineChart> seriesCharts;
	final private Map<LineChart, Color> chartColorMap;

    public DetailsPopup(ObservableList<LineChart> _seriesCharts, Map<LineChart, Color> _chartColorMap) {
        setStyle("-fx-border-width: 1px; -fx-padding: 5 5 5 5px; -fx-border-color: gray; -fx-background-color: whitesmoke;");
        setVisible(false);
        
        seriesCharts  = _seriesCharts;
        chartColorMap = _chartColorMap;
    }

    public void showChartDescription(MouseEvent event) {
        getChildren().clear();
        
        if(seriesCharts.size() != 0) {
            Long xValueLong = Math.round((double)seriesCharts.get(0).getXAxis().getValueForDisplay(event.getX()));

            HBox baseChartPopupRow = buildPopupRow(event, xValueLong, seriesCharts.get(0));
            if (baseChartPopupRow != null) {
                getChildren().add(baseChartPopupRow);
            }

	        for(int i = 1; i < seriesCharts.size(); ++i) {
	        	LineChart lineChart = seriesCharts.get(i);
                HBox popupRow = buildPopupRow(event, xValueLong, lineChart);
                if (popupRow == null) continue;

                getChildren().add(popupRow);
            }
        }
    }

    private HBox buildPopupRow(MouseEvent event, Long xValueLong, LineChart lineChart) {
        Label seriesName = new Label(lineChart.getYAxis().getLabel());
        seriesName.setTextFill(chartColorMap.get(lineChart));

        Number yValueForChart = getYValueForX(lineChart, xValueLong.intValue());
        if (yValueForChart == null) {
            return null;
        }
        Number yValueLower = Math.round(normalizeYValue(lineChart, event.getY() - 10));
        Number yValueUpper = Math.round(normalizeYValue(lineChart, event.getY() + 10));
        Number yValueUnderMouse = Math.round((double) lineChart.getYAxis().getValueForDisplay(event.getY()));

        // make series name bold when mouse is near given chart's line
        if (isMouseNearLine(yValueForChart, yValueUnderMouse, Math.abs(yValueLower.doubleValue()-yValueUpper.doubleValue()))) {
            seriesName.setStyle("-fx-font-weight: bold");
        }

        HBox popupRow = new HBox(10, seriesName, new Label("["+yValueForChart+"]"));
        return popupRow;
    }

    private double normalizeYValue(LineChart lineChart, double value) {
        Double val = (Double) lineChart.getYAxis().getValueForDisplay(value);
        if (val == null) {
            return 0;
        } else {
            return val;
        }
    }

    private boolean isMouseNearLine(Number realYValue, Number yValueUnderMouse, Double tolerance) {
        return (Math.abs(yValueUnderMouse.doubleValue() - realYValue.doubleValue()) < tolerance);
    }

    public Number getYValueForX(LineChart chart, Number xValue) {
        List<XYChart.Data> dataList = ((List<XYChart.Data>)((XYChart.Series)chart.getData().get(0)).getData());
        for(XYChart.Data data : dataList) {
            if (data.getXValue().equals(xValue)) {
                return (Number)data.getYValue();
            }
        }
        return null;
    }
}