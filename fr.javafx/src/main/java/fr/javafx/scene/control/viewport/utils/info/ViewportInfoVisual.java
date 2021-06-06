package fr.javafx.scene.control.viewport.utils.info;

import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.javafx.behavior.Visual;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ViewportInfoVisual<VP extends ViewportInfoControl> implements Visual<VP> {
	protected final VP 		skinnable;

	protected VBox  		pane;

	protected CategoryPane	modelPane, viewPane, windowPane, cursorPane;

	protected Label 		modelClass, modelValue;

	public ViewportInfoVisual(VP _skinnable) {
		super();
		skinnable = _skinnable;
		skinnable . mouseProperty().addListener((_obs, _old, _new) -> refreshAll(_new));

		build();
	}

	@Override
	public VP getSkinnable() {
		return skinnable;
	}

	@Override
	public Node getNode() {
		return pane;
	}

	@Override
	public void dispose() {
		
	}

	private void build() {
		modelPane  = new CategoryPane("Model Info");
		modelClass = modelPane.addCategory("Class:");
		modelValue = modelPane.addCategory("Value:");

		viewPane   = new CategoryPane("View Info");

		windowPane = new CategoryPane("Window Info");

		cursorPane = new CategoryPane("Cursor Info");

		pane = new VBox(modelPane, windowPane, viewPane, cursorPane);
	}

	public void        refreshAll(Point2D _cursor) {
		setAllUndef();

		Viewport<?, ?, ?, ?> viewport = skinnable.getViewport();

		if(viewport.getModel() != null) {
			Class<?> _modelClass = viewport.getModel() != null ? viewport.getModel().getClass() : null;
			String   _modelCls   = viewport.getModel() != null ? viewport.getModel().getClass().getSimpleName() : "<empty>";
			
			if(_modelClass.isAnonymousClass()) {
				_modelCls = _modelClass.toString();
				_modelCls = _modelCls.substring(_modelCls.lastIndexOf('.') + 1);
			} else {
				_modelCls = _modelClass.getSimpleName();
			}

			modelClass.setText(_modelCls);
			modelValue.setText(viewport.getModel().toString());
		}

	}
	public void        setAllUndef() {
		modelClass.setText("<undef>");
		modelValue.setText("<undef>");
	}

	protected static class CategoryPane extends TitledPane {
		private static final ColumnConstraints labels = new ColumnConstraints( 120 );
		private static final ColumnConstraints values = new ColumnConstraints() {{ setHgrow( Priority.ALWAYS ); }};

		GridPane container;
		int      nb_Category;

		public CategoryPane(String _name) {
			super();
			
			container = new GridPane();
			container . setVgap(4);
			container . setPadding(new Insets(5, 5, 5, 5));
			container . getColumnConstraints().addAll( labels, values );

			nb_Category = 0;

			setText(_name);
			setContent(container);
		}
		
		public Label addCategory(String _label) {
			Label result = null;

			container . add(new Label(_label), 		0, nb_Category);
			container . add(result = new Label(), 	1, nb_Category);

			nb_Category++;
			return result;
		}

	}

}
