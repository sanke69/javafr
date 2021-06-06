package fr.session.graphics;

import java.net.URL;

import fr.java.user.session.Session;
import fr.java.user.session.SessionUser;
import fr.session.graphics.SessionControl.SessionControlSkin;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SessionControlVisualLogout extends SessionControlSkin {
	public static final int 	CONTROL_WIDTH  = 240;
	public static final int 	CONTROL_HEIGHT = 360;
	public static final int 	GRAPHIC_WIDTH  =  96;
	public static final int 	GRAPHIC_HEIGHT =  96;
	public static final int 	LABEL_HEIGHT   =  20;

	public static final Color 	MSG_OK         =  Color.web("#126912");
	public static final Color 	MSG_ERROR      =  Color.web("#FA3339");

	boolean 		useGraphics = true;
	
	AnchorPane    	anchor;

	StackPane 		loginGraphics;
	ImageView     		imageView;

	Label         	lblMessage;

	Label         	lblUsername;

	Button 			DISCONNECT;
	
	public SessionControlVisualLogout(SessionControl _skinnable) {
		super(_skinnable);
		build();
	}

	@Override
	public final Node 								getNode() {
		if(anchor == null)
			anchor = new AnchorPane();
		return anchor;
	}
	public ObservableList<Node> 					getChildren() {
		return ((AnchorPane) getNode()).getChildren();
	}

	public void build() {
		anchor    = createMainPane();

		double 
		topAnchor = useGraphics ? createGraphicsCtrl(27d) : 27d;
		topAnchor = createUsernameCtrl(topAnchor + 7d);
		topAnchor = createActionCtrl(topAnchor);

		anchor.getChildren().addAll(loginGraphics, lblUsername, DISCONNECT);

		initializeHandlers();
		initializeValues();

		DISCONNECT.requestFocus();
	}
	
	private AnchorPane 	createMainPane() {
		AnchorPane anchor = new AnchorPane();
		anchor . setMinWidth   (CONTROL_WIDTH);
		anchor . setMinHeight  (CONTROL_HEIGHT);
		anchor . setPrefWidth  (CONTROL_WIDTH);
		anchor . setPrefHeight (CONTROL_HEIGHT);
		anchor . setMaxWidth   (CONTROL_WIDTH);
		anchor . setMaxHeight  (CONTROL_HEIGHT);
		
		return anchor;
	}
	private double 		createGraphicsCtrl(double _topAnchor) {
		URL   url   = getClass().getResource(SessionControl.USER_DEFAULT);
		Image image = new Image(url.toExternalForm());

		imageView     = new ImageView(image);
		imageView     . setFitWidth   (GRAPHIC_WIDTH);
		imageView     . setFitHeight  (GRAPHIC_HEIGHT);
		imageView     . setPickOnBounds(true);
		imageView     . setPreserveRatio(true);

		loginGraphics = new StackPane();
		loginGraphics . setPrefHeight(GRAPHIC_HEIGHT);
		loginGraphics . getChildren().add(imageView);

		AnchorPane.setLeftAnchor	(loginGraphics, 0d);
		AnchorPane.setRightAnchor	(loginGraphics, 0d);
		AnchorPane.setTopAnchor		(loginGraphics, _topAnchor);

		return _topAnchor + GRAPHIC_HEIGHT;
	}
	private double 		createUsernameCtrl(double _topAnchor) {
		lblUsername = new Label("Username");
		lblUsername . setPrefHeight(LABEL_HEIGHT);
		lblUsername . setAlignment(Pos.CENTER);

		AnchorPane.setLeftAnchor	(lblUsername, 27d);
		AnchorPane.setRightAnchor	(lblUsername, 27d);
		AnchorPane.setTopAnchor		(lblUsername, _topAnchor);
		_topAnchor += LABEL_HEIGHT;

		return _topAnchor;
	}
	private double 		createActionCtrl(double _topAnchor) {
		DISCONNECT = new Button("Disonnect");

		AnchorPane.setLeftAnchor	(DISCONNECT, 27d);
		AnchorPane.setRightAnchor	(DISCONNECT, 27d);
		AnchorPane.setBottomAnchor	(DISCONNECT, 21d);

		return _topAnchor;
	}

	private void 		initializeHandlers() {
		DISCONNECT.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	getSkinnable().sessionProperty().set(null);
		    	getSkinnable().onLogoutHandler().run();
		    	getSkinnable().switchToLogin();
		    }
		});
	}
	private void 		initializeValues() {
		Session session = getSkinnable().sessionProperty().get();
		
		if(session == null) {
			lblUsername.setText("???");
			return ;
		}

		SessionUser user = session.getUser().get();

		lblUsername.setText(user.getUsername());
	}
	
	
	
	
	
	
	
	
	
	private static final PseudoClass NOT_EMPTY_AND_UNFOCUSED = PseudoClass.getPseudoClass("not-empty-and-unfocused");

	private void checkState(TextField field) {
	    field.pseudoClassStateChanged(NOT_EMPTY_AND_UNFOCUSED, !(field.isFocused() || field.getLength() == 0));
	}
	

}
