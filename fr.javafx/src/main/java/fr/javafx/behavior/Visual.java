package fr.javafx.behavior;

import java.util.List;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.control.Control;

@Deprecated
public interface Visual<C extends Control> {

	public C 													getSkinnable();
	public Node 												getNode();

	public default List<CssMetaData<? extends Styleable, ?>> 	getCssMetaData() { return null; }

	public default void 										dispose() { }

}
