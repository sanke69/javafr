package fr.javafx.windows;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowStage extends Stage {

	final WindowControl main;

	public WindowStage() {
		super(StageStyle.UNDECORATED);

		main = new WindowControl(this);
		main.getRightIcons().addAll(WindowIcon.iconizeIcon(main), WindowIcon.minimizeIcon(main), WindowIcon.maximizeIcon(main), WindowIcon.closeIcon(main));
		main.setSelectable(false);

		setScene(new Scene(main, main.getWidth(), main.getHeight()));

		titleProperty().bind(main.titleProperty());

		getChildren().addListener((ListChangeListener<? super Node>) (_changes) -> {
			main.autosize();
			main.layout();

            setWidth( main.minWidth(0) );
            setHeight( main.minHeight(0) );
		});
	}
	public WindowStage(double _width, double _height) {
		super(StageStyle.UNDECORATED);

		main = new WindowControl(this);
		main.getRightIcons().addAll(WindowIcon.minimizeIcon(main), WindowIcon.maximizeIcon(main), WindowIcon.closeIcon(main));

		setScene(new Scene(main, _width, _height));
		
		titleProperty().bind(main.titleProperty());
	}

	public ObservableList<Node> getChildren() {
        return main.getContentPane().getChildren();
    }

}
