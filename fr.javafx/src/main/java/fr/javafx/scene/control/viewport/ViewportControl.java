package fr.javafx.scene.control.viewport;

import static java.util.stream.Collectors.toList;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import fr.java.math.geometry.Viewport;
import fr.javafx.scene.control.AbstractFxControlWithFPS;
import fr.javafx.utils.SwingFXUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class ViewportControl extends AbstractFxControlWithFPS {

	private Viewport.Editable<?, ?, ?, ?> 	 										viewport;
	private final ReadOnlyJavaBeanObjectProperty<Viewport.Editable<?, ?, ?, ?>> 	viewportProperty;

	boolean autofitModel   = false;
	boolean autofitControl = false;
	boolean preserveRatio  = true;
	
	double fixedWidth      = USE_COMPUTED_SIZE;
	double fixedHeight     = USE_COMPUTED_SIZE;

	protected ViewportControl(double _fps, Viewport.Editable<?, ?, ?, ?> _viewport) {
		super(_fps);

		viewport         = _viewport;
		viewportProperty = createViewportProperty();

		plugins.addListener(pluginsChanged);

		pluginsArea.getChildren().addListener((ListChangeListener<? super Node>) c -> {
			while(c.next()) {
				for(Node n : c.getAddedSubList())
					if(n instanceof Group)
						Platform.runLater(() -> super.getChildren().addAll(((Group) n).getChildren()));
				for(Node n : c.getRemoved())
					if(n instanceof Group)
						Platform.runLater(() -> super.getChildren().removeAll(((Group) n).getChildren()));
			}
		});
	}

	/*** Properties ***/
	private final ReadOnlyJavaBeanObjectProperty<Viewport.Editable<?, ?, ?, ?>> 
											createViewportProperty() {
    	ReadOnlyJavaBeanObjectProperty<Viewport.Editable<?, ?, ?, ?>> viewportProperty = null;

        try {
        	viewportProperty = ReadOnlyJavaBeanObjectPropertyBuilder.<Viewport.Editable<?, ?, ?, ?>>create().bean(this).name("viewport").build();
		} catch(NoSuchMethodException e) { e.printStackTrace(); }

        return viewportProperty;
    }
	public ReadOnlyJavaBeanObjectProperty<? extends Viewport.Editable<?, ?, ?, ?>>
											viewportProperty() {
		return (ReadOnlyJavaBeanObjectProperty<? extends Viewport.Editable<?, ?, ?, ?>>) viewportProperty;
	}
	public Viewport.Editable<?, ?, ?, ?> 	getViewport() {
		return viewport;
	}

	public void 							setFixedSize(double _width, double _height) {
		fixedWidth  = _width;
		fixedHeight = _height;
		setMinSize  (fixedWidth, fixedHeight);
		setPrefSize (fixedWidth, fixedHeight);
		setMaxSize  (fixedWidth, fixedHeight);
	}
	public void 							setFixedSize() {
		setMinSize  (fixedWidth, fixedHeight);
		setPrefSize (fixedWidth, fixedHeight);
		setMaxSize  (fixedWidth, fixedHeight);
	}
	public void 							unsetFixedSize() {
		setMinSize  (USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		setPrefSize (USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		setMaxSize  (USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
	}

	public double							getFixedWidth() {
		return fixedWidth;
	}
	public double							getFixedHeight() {
		return fixedHeight;
	}

	public void 							setPreserveRatio(boolean _true) {
		preserveRatio = _true;
	}
	public boolean 							getPreserveRatio() {
		return preserveRatio;
	}

	public void 							autofitContentToControl(boolean _true) {
		autofitModel = _true;
		if(_true)
			autofitControl = false;

		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	}
	public boolean 							autofitContentToControl() {
		return autofitModel;
	}

	public void 							autofitToContent(boolean _true) {
		autofitControl = _true;
		if(_true) {
			setManaged(false);
			autofitModel = false;
		}

		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	}
	public boolean 							autofitToContent() {
		return autofitControl;
	}

	/*** Action ***/
	public abstract void 					reset();

	public abstract void 					fitToContent();
	public abstract void 					fitContentToControl();

	/*** Extra ***/
	public WritableImage 					snapshot(double pixelScale) {
		WritableImage      img = new WritableImage((int) Math.rint(pixelScale * getWidth()), (int) Math.rint(pixelScale * getHeight()));
		SnapshotParameters spa = new SnapshotParameters();

		spa.setTransform(Transform.scale(pixelScale, pixelScale));
		spa.setFill(Color.TRANSPARENT);

		return snapshot(spa, img);
	}
	public BufferedImage 					snapshotAwt(double pixelScale) {
		return SwingFXUtils.fromFXImage(snapshot(pixelScale), null);
	}

	public void 							setFullScreen() {
		Window windows = getScene().getWindow();

		if (windows instanceof Stage) {
			Stage stage = (Stage) windows;
			stage.setFullScreen(!stage.isFullScreen());
		}
	}

	/*** PlugIns ***/
	public interface Plugin {

		public void 				setViewportControl(ViewportControl vpControl);

		public default void 		layoutChildren() {}
		public ObservableList<Node> getChildren();

	}

	private final Group         			 pluginsArea           = createChildGroup();
	private final Map<Plugin, Group> 		 pluginGroups          = new HashMap<>();
	private final ObservableList<Plugin> 	 plugins               = FXCollections.observableList(new LinkedList<>());
	private final ListChangeListener<Plugin> pluginsChanged        = (change) -> {
		while (change.next()) {
			change.getRemoved().forEach(this::pluginRemoved);
			change.getAddedSubList().forEach(this::pluginAdded);
		}
		updatePluginsArea();
	};

	public final ObservableList<Plugin> 	getPlugins() {
		return plugins;
	}

	public final ObservableList<Node> 		getChildren() {
		return super.getChildren();
	}

	@Override
	protected void 							layoutChildren() {
		super.layoutChildren();
		layoutPluginsChildren();
	}
	private void 							layoutPluginsChildren() {
		for(Plugin plugin : plugins)
			plugin.layoutChildren();
	}

	private void 							pluginAdded(Plugin plugin) {
		plugin.setViewportControl(this);
		Group group = createChildGroup();
		Bindings.bindContent(group.getChildren(), plugin.getChildren());
		pluginGroups.put(plugin, group);
	}
	private void 							pluginRemoved(Plugin plugin) {
		plugin.setViewportControl(null);
		Group group = pluginGroups.remove(plugin);
		Bindings.unbindContent(group, plugin.getChildren());
		group.getChildren().clear();
		pluginsArea.getChildren().remove(group);
	}

	private void 							updatePluginsArea() {
		pluginsArea.getChildren().setAll( plugins.stream().map(plugin -> pluginGroups.get(plugin)).collect(toList()) );
		requestLayout();
	}

	private static Group 					createChildGroup() {
		Group 
		group = new Group();
		group . setManaged(false);
		group . setAutoSizeChildren(false);
		group . relocate(0, 0);
		return group;
	}

}
