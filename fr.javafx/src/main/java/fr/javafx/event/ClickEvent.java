package fr.javafx.event;

import javafx.event.Event;
import javafx.event.EventType;

import fr.javafx.lang.enums.MouseButton;

public class ClickEvent extends Event {
	private static final long serialVersionUID = -8783311545520482832L;

	public static final EventType<ClickEvent> ANY = new EventType<>(Event.ANY, "ClickEvent:ANY");

    private final Object object;
    private final MouseButton button;
    private final Object event;

    public ClickEvent(EventType<? extends Event> et, Object obj, MouseButton btn, Object evt) {
        super(et);
        this.object = obj;
        this.button = btn;
        this.event = evt;
    }

    public Object getObject() {
        return object;
    }

    public MouseButton getButton() {
        return button;
    }

    public Object getEvent() {
        return event;
    }

}
