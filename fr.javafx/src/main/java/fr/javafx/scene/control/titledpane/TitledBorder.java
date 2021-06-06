package fr.javafx.scene.control.titledpane;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

public class TitledBorder extends Control {
	private final StringProperty 		titleProperty;
	private final ObjectProperty<Node> 	contentProperty;
	private final ObjectProperty<Color> colorProperty;

	private final DoubleProperty			leftMarginProperty;
	private final DoubleProperty			rightMarginProperty;
	private final DoubleProperty			topMarginProperty;
	private final DoubleProperty			bottomMarginProperty;

	public TitledBorder() {
		this(null, null);
	}
	public TitledBorder(String _title) {
		this(_title, null);
	}
	public TitledBorder(String _title, Node _content) {
		super();
		titleProperty   = new SimpleStringProperty(_title);
		contentProperty = new SimpleObjectProperty<Node>(_content);
		colorProperty   = new SimpleObjectProperty<Color>();

		leftMarginProperty   = new SimpleDoubleProperty( 3);
		rightMarginProperty  = new SimpleDoubleProperty( 3);
		topMarginProperty    = new SimpleDoubleProperty( 6);
		bottomMarginProperty = new SimpleDoubleProperty( 3);
	}

	@Override
	protected Skin<?> 				createDefaultSkin() {
		return new TitledBorderSkin(this);
	}

	public StringProperty			titleProperty() {
		return titleProperty;
	}
	public void 					setTitle(String title) {
		titleProperty.set(" " + title + " ");
	}
	public String 					getTitle() {
		return titleProperty.get();
	}

	public ObjectProperty<Node> 	contentProperty() {
		return contentProperty;
	}
	public void 					setContent(Node content) {
		contentProperty.set(content);
	}
	public Node 					getContent() {
		return contentProperty.get();
	}

	public ObjectProperty<Color> 	colorProperty() {
		return colorProperty;
	}
	public void 					setColor(Color _color) {
		colorProperty.set(_color);
	}
	public Color					getColor() {
		return colorProperty.get();
	}

	public DoubleProperty			leftMarginProperty() {
		return leftMarginProperty;
	}
	public DoubleProperty			rightMarginProperty() {
		return rightMarginProperty;
	}
	public DoubleProperty			topMarginProperty() {
		return topMarginProperty;
	}
	public DoubleProperty			bottomMarginProperty() {
		return bottomMarginProperty;
	}
	public DoubleBinding			innerWidthProperty() {
		if(getSkin() == null)
			setSkin( createDefaultSkin() );

		ReadOnlyDoubleProperty widthProperty = ((TitledBorderSkin) getSkin()).widthProperty();
		System.out.println(widthProperty + "\t" + leftMarginProperty + "\t" + rightMarginProperty);
		return ((TitledBorderSkin) getSkin()).widthProperty().subtract(leftMarginProperty).subtract(rightMarginProperty);
	}
}
