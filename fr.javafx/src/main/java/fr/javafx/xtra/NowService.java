package fr.javafx.xtra;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class NowService {
	private static final Duration 							refreshPeriod     = Duration.millis(27);

	private static final AtomicLong     					timestamp         = new AtomicLong(0);
    private static final LongProperty   					timestampProperty = new SimpleLongProperty();

    private static final Binding<LocalDateTime> 			ldt               = new ObjectBinding<LocalDateTime>() {
        { bind(timestampProperty); }

        @Override
        protected LocalDateTime computeValue() {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampProperty.get()), ZoneId.systemDefault());
        }
    };
    private static final Binding<LocalDate>     			ld                = Bindings.createObjectBinding(() -> ldt.getValue().toLocalDate(), timestampProperty);
    private static final Binding<LocalTime>     			lt                = Bindings.createObjectBinding(() -> ldt.getValue().toLocalTime(), timestampProperty);

    private static final Map<String, ObjectBinding<String>> bindings          = new HashMap<String, ObjectBinding<String>>();

    private static final ScheduledService<Long>             service           = new ScheduledService<Long>() {

        protected Task<Long>                    	createTask() {
            return new Task<Long>() {
                protected Long call() {
                	Platform.runLater( () -> timestampProperty.set( Instant.now().toEpochMilli() ) );
                    return timestampProperty.get();
                }
            };
        }

    };

    static {
		service . setPeriod( refreshPeriod );
        service . setOnSucceeded(e -> timestamp.set((long) e.getSource().getValue()));
        service . start();
    }

	public NowService() {
		super();
	}

    public final LongProperty 	            	timestampProperty() {
        return timestampProperty;
    }

    public final ObservableValue<LocalDateTime> datetimeProperty() {
        return ldt;
    }
    public final ObservableValue<LocalDate> 	dateProperty() {
        return ld;
    }
    public final ObservableValue<LocalTime> 	timeProperty() {
        return lt;
    }

	public ObjectBinding<String> 				textProperty() {
		return bindings.get(null);
	}
	public ObjectBinding<String> 				textProperty(String format) {
		if(bindings.containsKey(format))
			return bindings.get(format);

		DateTimeFormatter     ldtFormat = DateTimeFormatter.ofPattern(format);
		ObjectBinding<String> obinding  = Bindings.createObjectBinding(() -> ldtFormat.format( datetimeProperty().getValue() ), datetimeProperty());

		bindings.put(format, obinding);

		return obinding;
	}

}
