package fr.javafx.windows.skins;

import fr.javafx.windows.WindowIcon;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.SkinBase;

public class DefaultWindowIconSkin extends SkinBase<WindowIcon> {

	public DefaultWindowIconSkin(final WindowIcon c) {
		super(c);

		getNode().setCursor(Cursor.DEFAULT);
		getNode().onMouseClickedProperty().set((me) -> {
			if(c.getOnAction() != null)
				c.getOnAction().handle(new ActionEvent(me, c));
		});
	}

}
