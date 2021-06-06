package fr.javafx.windows;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public interface Window {
	public static final String DEFAULT_STYLE					 = "default.css";

	public static final String DEFAULT_STYLE_CLASS	             = "window";
    public static final String DEFAULT_STYLE_CLASS_TITLEBAR      = "window-titlebar";
    public static final String DEFAULT_STYLE_CLASS_ICON          = "window-icon";

    public static final String DEFAULT_STYLE_CLASS_ICON_CLOSE    = DEFAULT_STYLE_CLASS_ICON + "-close";
	public static final String DEFAULT_STYLE_CLASS_ICON_MAXIMIZE = DEFAULT_STYLE_CLASS_ICON + "-maximize";
	public static final String DEFAULT_STYLE_CLASS_ICON_MINIMIZE = DEFAULT_STYLE_CLASS_ICON + "-minimize";
	public static final String DEFAULT_STYLE_CLASS_ICON_ICONIZE  = DEFAULT_STYLE_CLASS_ICON + "-iconize";

	public static final String DEFAULT_STYLE_CLASS_ICON_ROTATE   = DEFAULT_STYLE_CLASS_ICON + "-rotate";

	public StringProperty 					titleBarStyleClassProperty();
	public String 							getTitleBarStyleClass();

	public StringProperty					titleProperty();
	public String 							getTitle();

	public ObjectProperty<Pane> 			contentPaneProperty();
	public Pane 							getContentPane();

	public ObservableList<WindowIcon> 		getLeftIcons();
	public ObservableList<WindowIcon> 		getRightIcons();

	// Selection
	public BooleanProperty 					selectableProperty();
	public void 							setSelectable(boolean b);
	public boolean 							isSelectable();

	public ReadOnlyBooleanProperty 			selectedProperty();
	public boolean 							isSelected();

	// Motion
	public BooleanProperty 					moveableProperty();
	public void 							setMoveable(boolean v);
	public boolean 							isMoveable();

	public ReadOnlyBooleanProperty 			movingProperty();
	public boolean 							isMoving();

	// Dimension
	public BooleanProperty 					resizeableProperty();
	public void 							setResizable(boolean v);
	public boolean 							isResizable();

	public ReadOnlyBooleanProperty 			resizingProperty();
	public boolean 							isResizing();

	public BooleanProperty		 			minimizedProperty();
	public boolean 							isMinimized();

	// Close
	public ReadOnlyBooleanProperty		 	closeRequestedProperty();
	public boolean							isCloseRequested();
	public void 							close();

	// Common Properties for Control and Stage
	public ReadOnlyDoubleProperty			heightProperty();
	public ReadOnlyDoubleProperty	 		widthProperty();

	public double 							getHeight();
	public double 							getWidth();

	public ReadOnlyDoubleProperty			layoutYProperty();
	public ReadOnlyDoubleProperty			layoutXProperty();

	public double 							getLayoutY();
	public double 							getLayoutX();

}
