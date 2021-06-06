package fr.javafx.scene.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Skin;

/**
 * The status bar control is normally placed at the bottom of a window. It is
 * used to display various types of application status information. This can be
 * a text message, the progress of a task, or any other kind of status (e.g. red
 * / green / yellow lights). By default the status bar contains a label for
 * displaying plain text and a progress bar (see {@link ProgressBar}) for long
 * running tasks. Additional controls / nodes can be placed on the left and
 * right sides (see {@link #getLeftItems()} and {@link #getRightItems()}).
 * 
 * <h3>Screenshots</h3> The picture below shows the default appearance of the
 * statusbar. <center><img src="statusbar.png" /></center> <br>
 * The following picture shows the status bar reporting progress of a task.
 * <center><img src="statusbar-progress.png" /></center> <br>
 * The last picture shows the status bar with a couple of extra items added to
 * the left and right. <center><img src="statusbar-items.png" /></center>
 * 
 * <h3>Code Sample</h3>
 * 
 * <pre>
 * StatusBar statusBar = new StatusBar();
 * statusBar.getLeftItems().add(new Button(&quot;Info&quot;));
 * statusBar.setProgress(.5);
 * </pre>
 */
public class StatusBar extends Control {
    public static final String         cssDefault  = StatusBar.class.getResource("StatusBar.css").toExternalForm();

    private final StringProperty       text        = new SimpleStringProperty(this, "text", "OK");
    private final ObjectProperty<Node> graphic     = new SimpleObjectProperty<>(this, "graphic");
    private final DoubleProperty       progress    = new SimpleDoubleProperty(this,"progress");

    private final ObservableList<Node> leftItems   = FXCollections.observableArrayList();
    private final ObservableList<Node> centerItems = FXCollections.observableArrayList();
    private final ObservableList<Node> rightItems  = FXCollections.observableArrayList();

    /**
     * Constructs a new status bar control.
     */
    public StatusBar() {
        getStyleClass().add("status-bar");
    }

    @Override
    protected Skin<?> 					createDefaultSkin() {
        return new StatusBarVisualDefault(this); // StatusBarSkin
    }

    @Override
	public String 						getUserAgentStylesheet() {
        return cssDefault;
    }

    /**
     * The property used for storing the text message shown by the status bar.
     * 
     * @return the text message property
     */
    public final StringProperty 		textProperty() {
        return text;
    }

    /**
     * Sets the value of the {@link #textProperty()}.
     * 
     * @param text the text shown by the label control inside the status bar
     */
    public final void 					setText(String text) {
        textProperty().set(text);
    }

    /**
     * Returns the value of the {@link #textProperty()}. 
     * 
     * @return the text currently shown by the status bar
     */
    public final String					 getText() {
        return textProperty().get();
    }

    /**
     * The property used to store the progress, a value between 0 and 1. A negative
     * value causes the progress bar to show an indeterminate state.
     * 
     * @return the property used to store the progress of a task
     */
    public final DoubleProperty 		progressProperty() {
        return progress;
    }

    /**
     * Sets the value of the {@link #progressProperty()}.
     * 
     * @param progress the new progress value
     */
    public final void 					setProgress(double progress) {
        progressProperty().set(progress);
    }

    /**
     * Returns the value of {@link #progressProperty()}.
     * 
     * @return the current progress value
     */
    public final double 				getProgress() {
        return progressProperty().get();
    }

    /**
     * The property used to store a graphic node that can be displayed by the 
     * status label inside the status bar control.
     * 
     * @return the property used for storing a graphic node
     */
    public final ObjectProperty<Node> 	graphicProperty() {
        return graphic;
    }

    /**
     * Sets the value of {@link #graphicProperty()}.
     * 
     * @param node the graphic node shown by the label inside the status bar
     */
    public final void 					setGraphic(Node node) {
        graphicProperty().set(node);
    }

    /**
     * Returns the value of the {@link #graphicProperty()}.
     * 
     * @return the graphic node shown by the label inside the status bar
     */
    public final Node 					getGraphic() {
        return graphicProperty().get();
    }

    /**
     * Returns the list of items / nodes that will be shown to the left of the status label.
     * 
     * @return the items on the left-hand side of the status bar
     */
    public final ObservableList<Node> 	getLeftItems() {
        return leftItems;
    }

    /**
     * Returns the list of items / nodes that will be shown to the center of the status label.
     * 
     * @return the items on the left-hand side of the status bar
     */
    public final ObservableList<Node> 	getCenterItems() {
        return centerItems;
    }

    /**
     * Returns the list of items / nodes that will be shown to the right of the status label.
     * 
     * @return the items on the left-hand side of the status bar
     */
    public final ObservableList<Node> 	getRightItems() {
        return rightItems;
    }

    public void 						forceLayout() {
    	;
    }

}