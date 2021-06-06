package fr.session.graphics;

import java.net.URL;

import fr.java.sdk.security.encryption.GuardedString;
import fr.java.user.session.Session;
import fr.session.graphics.SessionControl.SessionControlSkin;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SessionControlVisualLogin extends SessionControlSkin {

	boolean 		useGraphics = true;
	
	AnchorPane    	anchor;

	StackPane 		loginGraphics;
	ImageView     		imageView;

	Label         	lblMessage;

	Label         	lblUsername;
	TextField     	txtUsername;
	Label         	lblPassword;
	PasswordField 	txtPassword;
	
	CheckBox 		remember;
	

	StackPane 		linkPane;
	Hyperlink 			link;
	
	Button 			CONNECT;
	
	public SessionControlVisualLogin(SessionControl _skinnable) {
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
		topAnchor = createMessageCtrl(topAnchor + 10d);
		topAnchor = createUsernameCtrl(topAnchor + 7d);
		topAnchor = createPasswordCtrl(topAnchor);
		topAnchor = createRememberCtrl(topAnchor + 7d);
		topAnchor = createRegisterCtrl(topAnchor);
		topAnchor = createActionCtrl(topAnchor);

		anchor.getChildren().addAll(loginGraphics, lblMessage, lblUsername, txtUsername, lblPassword, txtPassword, remember, linkPane, CONNECT);

		initializeHandlers();

		CONNECT.requestFocus();
	}
	
	private AnchorPane 	createMainPane() {
		AnchorPane anchor = new AnchorPane();
		anchor . setMinWidth   (SessionControl.CONTROL_WIDTH);
		anchor . setMinHeight  (SessionControl.CONTROL_HEIGHT);
		anchor . setPrefWidth  (SessionControl.CONTROL_WIDTH);
		anchor . setPrefHeight (SessionControl.CONTROL_HEIGHT);
		anchor . setMaxWidth   (SessionControl.CONTROL_WIDTH);
		anchor . setMaxHeight  (SessionControl.CONTROL_HEIGHT);
		
		return anchor;
	}
	private double 		createGraphicsCtrl(double _topAnchor) {
		URL   url   = getClass().getResource(SessionControl.USER_DEFAULT);
		Image image = new Image(url.toExternalForm());

		imageView     = new ImageView(image);
		imageView     . setFitWidth   (SessionControl.GRAPHIC_WIDTH);
		imageView     . setFitHeight  (SessionControl.GRAPHIC_HEIGHT);
		imageView     . setPickOnBounds(true);
		imageView     . setPreserveRatio(true);

		loginGraphics = new StackPane();
		loginGraphics . setPrefHeight(SessionControl.GRAPHIC_HEIGHT);
		loginGraphics . getChildren().add(imageView);

		AnchorPane.setLeftAnchor	(loginGraphics, 0d);
		AnchorPane.setRightAnchor	(loginGraphics, 0d);
		AnchorPane.setTopAnchor		(loginGraphics, _topAnchor);

		return _topAnchor + SessionControl.GRAPHIC_HEIGHT;
	}
	private double 		createMessageCtrl(double _topAnchor) {
		lblMessage = new Label();
		lblMessage . setPrefHeight(SessionControl.LABEL_HEIGHT);
		lblMessage . setAlignment(Pos.CENTER);

		AnchorPane.setLeftAnchor	(lblMessage, 0d);
		AnchorPane.setRightAnchor	(lblMessage, 0d);
		AnchorPane.setTopAnchor		(lblMessage, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT;

		return _topAnchor;
	}
	private double 		createUsernameCtrl(double _topAnchor) {
		lblUsername = new Label("Username");
		lblUsername . setPrefHeight(SessionControl.LABEL_HEIGHT);

		AnchorPane.setLeftAnchor	(lblUsername, 27d);
		AnchorPane.setTopAnchor		(lblUsername, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT;

		txtUsername = new TextField();
		txtUsername . setPrefHeight(SessionControl.LABEL_HEIGHT);
		txtUsername.setPromptText("Enter username or email");
		txtUsername.setFocusTraversable(false); 

		AnchorPane.setLeftAnchor	(txtUsername, 27d);
		AnchorPane.setRightAnchor	(txtUsername, 27d);
		AnchorPane.setTopAnchor		(txtUsername, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT + 10d;
		
		return _topAnchor;
	}
	private double 		createPasswordCtrl(double _topAnchor) {
		lblPassword = new Label("Password");
		lblPassword . setPrefHeight(SessionControl.LABEL_HEIGHT);

		AnchorPane.setLeftAnchor	(lblPassword, 27d);
		AnchorPane.setTopAnchor		(lblPassword, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT;

		
		txtPassword = new PasswordField();
		txtPassword.setPromptText("Enter password");
		txtPassword.setFocusTraversable(false); 
		txtPassword . setPrefHeight(SessionControl.LABEL_HEIGHT);

		AnchorPane.setLeftAnchor	(txtPassword, 27d);
		AnchorPane.setRightAnchor	(txtPassword, 27d);
		AnchorPane.setTopAnchor		(txtPassword, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT;
		
		return _topAnchor;
	}
	private double 		createRememberCtrl(double _topAnchor) {
		remember = new CheckBox("Remember Me");

		AnchorPane.setRightAnchor	(remember, 27d);
		AnchorPane.setTopAnchor		(remember, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT;
		
		return _topAnchor;
	}
	private double 		createRegisterCtrl(double _topAnchor) {
		link = new Hyperlink();
		link.setText("Create new account");
		link.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		        System.out.println("This link is clicked");
		    }
		});

		linkPane = new StackPane();
		linkPane.getChildren().add(link);

		AnchorPane.setLeftAnchor	(linkPane, 0d);
		AnchorPane.setRightAnchor	(linkPane, 0d);
		AnchorPane.setTopAnchor		(linkPane, _topAnchor);
		_topAnchor += SessionControl.LABEL_HEIGHT;
		
		return _topAnchor;
	}
	private double 		createActionCtrl(double _topAnchor) {
		CONNECT = new Button("Sign in");

		AnchorPane.setLeftAnchor	(CONNECT, 27d);
		AnchorPane.setRightAnchor	(CONNECT, 27d);
		AnchorPane.setBottomAnchor	(CONNECT, 21d);

		return _topAnchor;
	}

	private void 		initializeHandlers() {
		CONNECT.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
		    		msgError("username or password is empty !");
		    		return ;
		    	}

		        String 		  username = txtUsername.getText();
		        GuardedString password = GuardedString.of(txtPassword.getText());

		        Session session = getSkinnable().onLoginHandler().apply(username, password);
		        if(session != null) {
		        	msgSuccess("Login success");

		    		getSkinnable().onLoginSuccessHandler().accept(session);

		    		getSkinnable().sessionProperty().set(session);

		    		getSkinnable().switchToLogout();
		    		return ;
		    	}

		        msgError("Invalid identifier");
		    }
		});
	}
	
	

	void msgSuccess(String _msg) {
		lblMessage.setTextFill(SessionControl.MSG_OK);
		lblMessage.setText(_msg);
	}
	void msgError(String _msg) {
		lblMessage.setTextFill(SessionControl.MSG_ERROR);
		lblMessage.setText(_msg);
	}
	
	
	
	
	
	private static final PseudoClass NOT_EMPTY_AND_UNFOCUSED = PseudoClass.getPseudoClass("not-empty-and-unfocused");

	private void checkState(TextField field) {
	    field.pseudoClassStateChanged(NOT_EMPTY_AND_UNFOCUSED, !(field.isFocused() || field.getLength() == 0));
	}
	

}
