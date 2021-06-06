package fr.javafx.sdk.controls.signal1D;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.stage.Stage;

public class Signal1DViewer extends Control implements Skin<Signal1DViewer> {
	private static final int MAX_DATA_POINTS = 50;

	private Series							series;
	private int								xSeriesData	= 0;
	private ConcurrentLinkedQueue<Number>	dataQ		= new ConcurrentLinkedQueue<Number>();
	private ExecutorService					executor;
	private AddToQueue						addToQueue;
	private NumberAxis						xAxis;

	public Signal1DViewer() {
		super();
	}

	
	private void init(Stage primaryStage) {
		xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);
		//        xAxis.setTickLabelsVisible(false);
		//        xAxis.setTickMarkVisible(false);
		xAxis.setMinorTickVisible(false);

		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);

		//-- Chart
/**
		final AreaChart<Number, Number> sc = new AreaChart<Number, Number>(xAxis, yAxis) {
			// Override to remove symbols on each data point
//			@Override protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) { }
		};
/*/
		final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis);
/**/

		sc.setAnimated(false);
		sc.setId("liveAreaChart");
		sc.setTitle("Animated Area Chart");

		//-- Chart Series
		series = new AreaChart.Series<Number, Number>();
		series.setName("Area Chart Series");
		sc.getData().add(series);

		primaryStage.setScene(new Scene(sc));
	}

	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();

		//-- Prepare Executor Services
		executor = Executors.newCachedThreadPool();
		addToQueue = new AddToQueue();
		executor.execute(addToQueue);
		//-- Prepare Timeline
		prepareTimeline();
	}

	private class AddToQueue implements Runnable {
		public void run() {
			try {
				// add a item of random data to queue
				dataQ.add(Math.random());
				Thread.sleep(50);
				executor.execute(this);
			} catch(InterruptedException ex) {
				Logger.getLogger(Signal1DViewer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	//-- Timeline gets called in the JavaFX Main thread
	private void prepareTimeline() {
		// Every frame to take any data from queue and add to chart
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				addDataToSeries();
			}
		}.start();
	}

	private void addDataToSeries() {
		for(int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
			if(dataQ.isEmpty())
				break;
			series.getData().add(new AreaChart.Data(xSeriesData++, dataQ.remove()));
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if(series.getData().size() > MAX_DATA_POINTS) {
			series.getData().remove(0, series.getData().size() - MAX_DATA_POINTS);
		}
		// update 
		xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
		xAxis.setUpperBound(xSeriesData - 1);
	}


	@Override
	protected Skin<?> createDefaultSkin() {
		return this;
	}

	@Override
	public Signal1DViewer getSkinnable() {
		return this;
	}

	@Override
	public Node getNode() {
		return null;
	}

	@Override
	public void dispose() {
		;
	}

}