package fr.javafx.temp.windows;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.log.LogInstance;
import fr.javafx.temp.windows.behaviors.MutableWindowBehavior;
import fr.javafx.temp.windows.components.WindowHeader;
import fr.javafx.temp.windows.components.WindowProperties;
import fr.javafx.temp.windows.components.WindowStatus;
import fr.javafx.temp.windows.components.WindowProperties.Mode;
import fr.javafx.temp.windows.components.WindowProperties.Position;
import fr.javafx.temp.windows.pane.DockPane;
import fr.javafx.temp.windows.skins.DockedWindowSkin;
import fr.javafx.temp.windows.skins.FloatingWindowSkin;
import fr.javafx.temp.windows.skins.StagedWindowSkin;

public class MutableWindow {
	static final LogInstance log = LogInstance.getLogger();

	MutableWindowPane	owner;
	WindowHeader		header;
	Parent				content;

	WindowProperties	properties;
	WindowStatus 		status;

	MutableWindowSkin	currentSkin	= null;
	DockedWindowSkin	dockedSkin;
	FloatingWindowSkin	floatingSkin;
	StagedWindowSkin	stagedSkin;

	private ObjectProperty<Mode>	modeProperty	= new SimpleObjectProperty<Mode>() {
														@Override
														public String getName() {
															return "mode";
														}
													};
	private ObjectProperty<Node>	iconProperty	= new SimpleObjectProperty<Node>() {
														@Override
														public String getName() {
															return "icon";
														}
													};
	private StringProperty			titleProperty	= new SimpleStringProperty("MutableWindow") {
														@Override
														public String getName() {
															return "title";
														}
													};

	public MutableWindow(Parent _content, String _title) {
		this(_content, _title, null);
	}
	public MutableWindow(Parent _content, String _title, Node _icon) {
		super();
		if(_icon != null) setIcon(_icon);
		setTitle(_title);

		properties 	= new WindowProperties();
		status 		= new WindowStatus();

		header  	= new WindowHeader(this);
		content 	= _content;
		
		modeProperty.addListener((_p, _old, _new) -> {
			log.debug("State Changed: " + _old + " -> " + _new);
			switch(_new) {
			case DOCKED		:	status.setDocked_unsafe(true);
								status.setFloating_unsafe(false);
								status.setStaged_unsafe(false);
								break;
			case FLOATING	:	status.setDocked_unsafe(false);
								status.setFloating_unsafe(true);
								status.setStaged_unsafe(false);
								break;
			case STAGED		:	status.setDocked_unsafe(false);
								status.setFloating_unsafe(false);
								status.setStaged_unsafe(true);
								break;
			case HIDDEN		:
			case UNKNOWN	:
			case DISABLED	:
			default			:	status.setDocked_unsafe(false);
								status.setFloating_unsafe(false);
								status.setStaged_unsafe(false);
								break;
			}
		});

		MutableWindowBehavior.defineControlBehaviors(this);
		
		header.addEventHandler(MouseEvent.MOUSE_PRESSED, MutableWindowBehavior.onMousePressed(this));
		header.addEventHandler(MouseEvent.MOUSE_DRAGGED, MutableWindowBehavior.enableDragAndDrop(this));
		header.addEventHandler(MouseEvent.DRAG_DETECTED, MutableWindowBehavior.onMouseDragDetected(this));
		header.addEventHandler(MouseEvent.MOUSE_DRAGGED, MutableWindowBehavior.onMouseDragged(this));
		header.addEventHandler(MouseEvent.MOUSE_RELEASED, MutableWindowBehavior.onMouseReleased(this));
		

		header.addEventHandler(MouseEvent.MOUSE_MOVED, MutableWindowBehavior.onMouseMoved(this));
	}

	private void setMode(Mode _mode) {
		modeProperty.set(_mode);
	}
	public Mode getMode() {
		return modeProperty.get();
	}
	public ObjectProperty<Mode> modeProperty() {
		return modeProperty;
	}

	public final void setIcon(Node graphic) {
		this.iconProperty.setValue(graphic);
	}
	public final Node getIcon() {
		return iconProperty.get();
	}
	public final ObjectProperty<Node> iconProperty() {
		return iconProperty;
	}

	public final void setTitle(String title) {
		this.titleProperty.setValue(title);
	}
	public final String getTitle() {
		return titleProperty.get();
	}
	public final StringProperty titleProperty() {
		return titleProperty;
	}

	public WindowHeader getHeader() {
		return header;
	}

	public void setContent(Parent _content) {
		content = _content;
	}
	public Parent getContent() {
		return content;
	}

	public WindowProperties properties() {
		return properties;
	}
	public WindowStatus status() {
		return status;
	}

	public void setSkin(MutableWindowSkin _skin) {
		currentSkin = _skin;
	}
	public MutableWindowSkin getSkin() {
		return currentSkin;
	}

	public DockedWindowSkin getDockSkin() {
		if(dockedSkin == null)
			dockedSkin = new DockedWindowSkin(this);
		return dockedSkin;
	}
	public FloatingWindowSkin getFloatSkin() {
		if(floatingSkin == null)
			floatingSkin = new FloatingWindowSkin(this);
		return floatingSkin;
	}
	public StagedWindowSkin getStageSkin() {
		if(stagedSkin == null)
			stagedSkin = new StagedWindowSkin(this);
		return stagedSkin;
	}

	public void dock(MutableWindowPane dockPane, Position dockPos) {
		log.debug("docked !!!");
		if(currentSkin != null)
			currentSkin.release();

		setMode(Mode.DOCKED);
		setSkin(getDockSkin());
		getDockSkin().fill();
		getDockSkin().dock((DockPane) dockPane, dockPos);
	}
	public void dock(MutableWindowPane dockPane, Position dockPos, Node _sibling) {
		log.debug("docked !!!");
		if(currentSkin != null)
			currentSkin.release();

		setMode(Mode.DOCKED);
		setSkin(getDockSkin());
		getDockSkin().fill();
		getDockSkin().dock((DockPane) dockPane, dockPos, _sibling);
	}
	public void dock(MutableWindowPane dockPane, Position dockPos, MutableWindow _sibling) {
		log.debug("docked !!!");
		if(currentSkin != null)
			currentSkin.release();

		setMode(Mode.DOCKED);
		setSkin(getDockSkin());
		getDockSkin().fill();
		getDockSkin().dock((DockPane) dockPane, dockPos, _sibling.getDockSkin());
	}
	public void attach(MutableWindowPane floatPane, double _x, double _y) {
		log.debug("floating !!!");
		if(currentSkin != null)
			currentSkin.release();

		setMode(Mode.FLOATING);
	}
	public void staged(Point2D _position) {
		log.debug("staged !!!");
		if(currentSkin != null)
			currentSkin.release();

		setMode(Mode.STAGED);
		setSkin(getStageSkin());
		getStageSkin().fill();

		if(_position != null) {
			getStageSkin().setX(_position.getX());
			getStageSkin().setY(_position.getY());
		}
	}

	public void close() {
		currentSkin.release();
		setMode(Mode.HIDDEN);
	}

}
