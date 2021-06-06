package fr.javafx.temp.windows.events;

import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.components.WindowHeader;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.PickResult;

public class MutableWindowEvent extends Event {

	private static final long serialVersionUID = 4413700316447127355L;

	public static final EventType<MutableWindowEvent>	ANY				= new EventType<MutableWindowEvent>(Event.ANY, "DOCK");
	public static final EventType<MutableWindowEvent>	DOCK_ENTER		= new EventType<MutableWindowEvent>(MutableWindowEvent.ANY, "DOCK_ENTER");
	public static final EventType<MutableWindowEvent>	DOCK_OVER		= new EventType<MutableWindowEvent>(MutableWindowEvent.ANY, "DOCK_OVER");
	public static final EventType<MutableWindowEvent>	DOCK_EXIT		= new EventType<MutableWindowEvent>(MutableWindowEvent.ANY, "DOCK_EXIT");
	public static final EventType<MutableWindowEvent>	DOCK_RELEASED	= new EventType<MutableWindowEvent>(MutableWindowEvent.ANY, "DOCK_RELEASED");

	private transient double x;
	private transient double y;
	private transient double z;

	private final double screenX;
	private final double screenY;

	private final double sceneX;
	private final double sceneY;

	private PickResult pickResult;
	private Node contents;

	public MutableWindowEvent(EventType<? extends MutableWindowEvent> eventType, double x, double y, double screenX, double screenY, PickResult pickResult) {
		this(null, null, eventType, x, y, screenX, screenY, pickResult);
	}
	public MutableWindowEvent(Object source, EventTarget target, EventType<? extends MutableWindowEvent> eventType, double x, double y, double screenX, double screenY, PickResult pickResult) {
		this(source, target, eventType, x, y, screenX, screenY, pickResult, null);
	}
	public MutableWindowEvent(Object source, EventTarget target, EventType<? extends MutableWindowEvent> eventType, double x, double y, double screenX, double screenY, PickResult pickResult, Node contents) {
		super(source, target, eventType);
		this.x = x;
		this.y = y;
		this.screenX = screenX;
		this.screenY = screenY;
		this.sceneX = x;
		this.sceneY = y;
		this.pickResult = pickResult != null ? pickResult : new PickResult(target, x, y);
		final Point3D p = Point3D.ZERO; //InputEventUtils.recomputeCoordinates(this.pickResult, null);
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
		this.contents = contents;
	}

	public final double getX() {
		return x;
	}
	public final double getY() {
		return y;
	}
	public final double getZ() {
		return z;
	}

	public final double getScreenX() {
		return screenX;
	}
	public final double getScreenY() {
		return screenY;
	}

	public final double getSceneX() {
		return sceneX;
	}
	public final double getSceneY() {
		return sceneY;
	}

	public final PickResult getPickResult() {
		return pickResult;
	}

	public final Node getContents() {
		return contents;
	}

}
