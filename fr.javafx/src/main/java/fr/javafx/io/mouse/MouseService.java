package fr.javafx.io.mouse;

import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class MouseService extends ScheduledService<Void> {

	private final DoubleProperty	x;
	private final DoubleProperty	y;

	private final List<EventHandler<MouseEvent>> listeners;

	public MouseService(Duration _fps) {
		super();
		setPeriod(_fps);

		x = new SimpleDoubleProperty(0.0);
		y = new SimpleDoubleProperty(0.0);
		listeners = new ArrayList<EventHandler<MouseEvent>>();
	}

	public DoubleProperty xProperty() { return x; }
	public DoubleProperty yProperty() { return y; }

	public void addHandler(EventHandler<MouseEvent> _handler) {
		listeners.add(_handler);
	}

	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() {
				Platform.runLater(() -> {
					x.set(MouseInfo.getPointerInfo().getLocation().getX());
					y.set(MouseInfo.getPointerInfo().getLocation().getY());

					if(!listeners.isEmpty()) {
						MouseEvent me = new MouseEvent( MouseEvent.MOUSE_MOVED,
														x.get(), y.get(), x.get(), y.get(),
														null, 0, 
														false, false, false, false, false, false, false, false, false, false, 
														null);

						for(EventHandler<MouseEvent> handler : listeners)
							handler.handle(me);
					}
				});
				return null;
			}

		};
	}

}
