package fr.javafx.cell;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MutableListCell<T, V extends Node, E extends Node> extends ListCell<T> {
	public static final boolean useGraphicOnly = true;

    private final V 				 visual;
    private final BiConsumer<T, V>   visualUpdater;
    private final E 				 editor;
    private final BiConsumer<T, E>   editorUpdater;

    MenuItem    helloWorld  = new MenuItem("Hello World!");
    ContextMenu contextMenu = new ContextMenu(helloWorld);

    public MutableListCell(Supplier<V>      _visual,
    					   Supplier<E>      _editor,
    					   BiConsumer<T, V> _visualUpdater,
    					   BiConsumer<T, E> _editorUpdater) {
    	super();

    	visual = _visual.get(); visualUpdater = _visualUpdater;
    	editor = _editor.get(); editorUpdater = _editorUpdater;

    	editor.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE)
                cancelEdit();
        });

    	if(useGraphicOnly) {
    		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    	} else {
    		setContentDisplay(ContentDisplay.BOTTOM);
//    		setContentDisplay(ContentDisplay.TEXT_ONLY);
//    		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    	}

    	switchToVisualisation();
    }

    @Override
    protected void updateItem(T client, boolean empty) {
        super.updateItem(client, empty);
        
        if(empty) {
            setText("");
        	setContentDisplay(ContentDisplay.TEXT_ONLY);
        	return ;
        }

        if (isEditing()) {
            switchToEdition();
        	editorUpdater.accept( getItem(), editor );
        } else {
        	switchToVisualisation();
        	visualUpdater.accept( getItem(), visual );
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();

        switchToEdition();
        getGraphic().requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        switchToVisualisation();
    }

    private void switchToVisualisation() {
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic ( visual );
        setText    ("visual");

    	setContextMenu(contextMenu);
    }
    private void switchToEdition() {
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic ( editor );
        setText    ("editor");

    	setContextMenu(null);

        editorUpdater.accept(getItem(), editor);
    }

}
