package fr.session.graphics;

import fr.java.user.session.Session;
import fr.session.graphics.SessionControl.SessionControlSkin;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SessionControlVisualInfo extends SessionControlSkin/* extends VBox*/ {
	VBox		node;
	ImageView 	imageView;
	Label 		label;

	public SessionControlVisualInfo(SessionControl _control) {
		super(_control);

    	initialize();
    	populate();

    	getSkinnable().sessionProperty().addListener((_obs, _old, _new) -> update(_new));
    	update(getSkinnable().sessionProperty().get());
	}

	@Override
	public Node 									getNode() {
		return node;
	}

	public void 									initialize() {
		node = new VBox();
    	node.setAlignment(Pos.TOP_CENTER);
    	node.setMinWidth(Double.NEGATIVE_INFINITY);
    	node.setMaxWidth(Double.NEGATIVE_INFINITY);
    	node.setMinHeight(Double.NEGATIVE_INFINITY);
    	node.setMaxHeight(Double.NEGATIVE_INFINITY);
	}
	public void 									populate() {
    	imageView = new ImageView();
    	imageView.setFitWidth(SessionControl.GRAPHIC_WIDTH);
    	imageView.setFitHeight(SessionControl.GRAPHIC_HEIGHT);
    	imageView.setPickOnBounds(true);
    	imageView.setPreserveRatio(true);
    	VBox.setMargin(imageView, new Insets(50, 0, 10, 0));

    	label = new Label();
    	label.setTextFill(Color.web("#e7e5e5"));
    	VBox.setMargin(label, new Insets(0, 0, 20, 0));
		
    	node.getChildren().addAll(imageView, label);
	}
	
	public void update(Session _session) {
		if(_session == null) {
	    	label.setText("???");
	    	imageView.setImage(new Image(SessionControl.USER_UNKNOWN));
	    	return ;
		}
		
		_session.getUser().ifPresent(su -> {
	    	label.setText(su.getUsername());
	    	imageView.setImage(getAvatar(su));
		});
	}

}
