package fr.javafx.stage.impl.notifications;

import java.net.URL;

import fr.javafx.lang.enums.StageAnchor;
import fr.javafx.stage.StageExt;
import fr.javafx.stage.animation.StageAnimation;
import fr.javafx.stage.animation.XStageAnimations;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class SystrayNotification {

	public static interface Type {
		String getURLResource();
		String getPaintHex();
	}
	public enum Types implements Type {
		INFORMATION	("default/notifier/info.png", 		"#2C54AB"),
		NOTICE		("default/notifier/notice.png", 	"#8D9695"),
		SUCCESS		("default/notifier/success.png", 	"#009961"),
		WARNING		("default/notifier/warning.png", 	"#E23E0A"),
		ERROR		("default/notifier/error.png", 		"#CC0033");
	
		private final String urlResource;
		private final String paintHex;
	
		Types(String urlResource, String paintHex) {
			this.urlResource = urlResource;
			this.paintHex    = paintHex;
		}
	
		@Override public String getURLResource() { return urlResource; }
		@Override public String getPaintHex() { return paintHex; }

	}

	private StageExt	stage;
	private AnchorPane	pane;
	private Rectangle	rectangleColor;
	private ImageView	imageIcon;
	private Label		lblTitle, lblMessage, lblClose;

	private EventHandler<ActionEvent> onDismissedCallBack, onShownCallback;

	private Type		notification;
	private StageAnimation	animation;

	/**
	 * Initializes the tray notification with the default type.
	 */
	public SystrayNotification() {
		this(Types.NOTICE);
	}

	/**
	 * Initializes an empty instance of the tray notification
	 */
	public SystrayNotification(Type notification) {
		this("", "", notification);
	}

	/**
	 * Initializes an instance of the tray notification object
	 *
	 * @param title        The title text to assign to the tray
	 * @param body         The body text to assign to the tray
	 * @param notification The notification type to assign to the tray
	 */
	public SystrayNotification(String title, String body, Type notification) {
		initTrayNotification(title, body, notification);
	}

	/**
	 * Initializes an instance of the tray notification object
	 *
	 * @param title         The title text to assign to the tray
	 * @param body          The body text to assign to the tray
	 * @param img           The image to show on the tray
	 * @param rectangleFill The fill for the rectangle
	 */
	public SystrayNotification(String title, String body, Image img, Paint rectangleFill, Type notification) {
		initTrayNotification(title, body, notification);

		setImage(img);
		setRectangleFill(rectangleFill);
	}

	private void initTrayNotification(String title, String message, Type type) {
		initScene();
		initStage();
		initAnimations();

		setTray(title, message, type);
	}

	private void initScene() {
		rectangleColor = new Rectangle();
		rectangleColor.setStyle("-fx-arc-width: 0;");
		rectangleColor.setArcWidth(5.0);
		rectangleColor.setArcHeight(5.0);
		rectangleColor.setFill(Color.GREY);
		rectangleColor.setStrokeType(StrokeType.INSIDE);
		rectangleColor.setStroke(Color.GREY);
		rectangleColor.setHeight(85);
		rectangleColor.setWidth(32);
		rectangleColor.setLayoutX(1);
		rectangleColor.setLayoutY(1);

		imageIcon = new ImageView();
		imageIcon.setFitWidth(67);
		imageIcon.setFitHeight(67);
		imageIcon.setLayoutX(47);
		imageIcon.setLayoutY(9);
		imageIcon.setPickOnBounds(true);

		lblTitle = new Label("Title:");
		lblTitle.setLayoutX(126);
		lblTitle.setLayoutY(9);
		lblTitle.setWrapText(true);
		lblTitle.setFont(Font.font("System Bold", 16.0));

		lblMessage = new Label();
		lblMessage.setLayoutX(126);
		lblMessage.setLayoutY(52);
		lblMessage.setWrapText(true);
		lblMessage.setFont(Font.font("System Bold", 13.0));

		lblClose = new Label("x");
		lblClose.setLayoutX(441);
		lblClose.setWrapText(true);
		lblClose.setFont(Font.font("System Bold", 20.0));
		lblClose.setCursor(Cursor.HAND);

		pane = new AnchorPane();
		pane.setPrefSize(461, 87);
		pane.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
		pane.getChildren().addAll(rectangleColor, imageIcon, lblTitle, lblMessage, lblClose);

	}
	private void initStage() {

		//		stage = new CustomStage(rootNode, StageStyle.UTILITY);
		stage = new StageExt(StageStyle.UNDECORATED, StageAnchor.SCREEN_BOTTOM_RIGHT);
		stage.setScene(new Scene(pane));
		stage.setAlwaysOnTop(true);
//		stage.setLocation(stage.getBottomRight());

		lblClose.setOnMouseClicked(e -> dismiss());
	}

	private void initAnimations() {
		setAnimation(XStageAnimations.POPUP); // Default animation type
	}

	public void setType(Type nType) {
		notification = nType;

		URL imageLocation = getClass().getClassLoader().getResource(nType.getURLResource());
		setRectangleFill(Paint.valueOf(nType.getPaintHex()));
		setImage(new Image(imageLocation.toString()));
		setTrayIcon(imageIcon.getImage());
	}

	public Type getNotification() {
		return notification;
	}

	public void setTray(String title, String message, Type type) {
		setTitle(title);
		setMessage(message);
		setType(type);
	}

	public void setTray(String title, String message, Image img, Paint rectangleFill, StageAnimation animation) {
		setTitle(title);
		setMessage(message);
		setImage(img);
		setRectangleFill(rectangleFill);
		setAnimation(animation);
	}

	public boolean isTrayShowing() {
		return animation.isShowing();
	}

	/**
	 * Shows and dismisses the tray notification
	 *
	 * @param dismissDelay How long to delay the start of the dismiss animation
	 */
	public void showAndDismiss(Duration dismissDelay) {
		if(!isTrayShowing()) {
			stage.show();

			onShown();
			animation.playSequential(dismissDelay);
		} else
			dismiss();

		onDismissed();
	}

	/**
	 * Displays the notification tray
	 */
	public void showAndWait() {
		if(!isTrayShowing()) {
			stage.show();

			animation.playShowAnimation();

			onShown();
		}
	}

	/**
	 * Dismisses the notifcation tray
	 */
	public void dismiss() {
		if(isTrayShowing()) {
			animation.playDismissAnimation();
			onDismissed();
		}
	}

	private void onShown() {
		if(onShownCallback != null)
			onShownCallback.handle(new ActionEvent());
	}

	private void onDismissed() {
		if(onDismissedCallBack != null)
			onDismissedCallBack.handle(new ActionEvent());
	}

	/**
	 * Sets an action event for when the tray has been dismissed
	 *
	 * @param event The event to occur when the tray has been dismissed
	 */
	public void setOnDismiss(EventHandler<ActionEvent> event) {
		onDismissedCallBack = event;
	}

	/**
	 * Sets an action event for when the tray has been shown
	 *
	 * @param event The event to occur after the tray has been shown
	 */
	public void setOnShown(EventHandler<ActionEvent> event) {
		onShownCallback = event;
	}

	/**
	 * Sets a new task bar image for the tray
	 *
	 * @param img The image to assign
	 */
	public void setTrayIcon(Image img) {
		stage.getIcons().clear();
		stage.getIcons().add(img);
	}

	public Image getTrayIcon() {
		return stage.getIcons().get(0);
	}

	/**
	 * Sets a title to the tray
	 *
	 * @param txt The text to assign to the tray icon
	 */
	public void setTitle(String txt) {
		Platform.runLater(() -> lblTitle.setText(txt));
	}

	public String getTitle() {
		return lblTitle.getText();
	}

	/**
	 * Sets the message for the tray notification
	 *
	 * @param txt The text to assign to the body of the tray notification
	 */
	public void setMessage(String txt) {
		lblMessage.setText(txt);
	}

	public String getMessage() {
		return lblMessage.getText();
	}

	public void setImage(Image img) {
		imageIcon.setImage(img);

		setTrayIcon(img);
	}

	public Image getImage() {
		return imageIcon.getImage();
	}

	public void setRectangleFill(Paint value) {
		rectangleColor.setFill(value);
	}

	public Paint getRectangleFill() {
		return rectangleColor.getFill();
	}

	public void setAnimation(StageAnimation animation) {
		this.animation = animation;
	}

	public void setAnimation(XStageAnimations animation) {
		setAnimation(animation.newInstance(stage));
	}

	public StageAnimation getAnimation() {
		return animation;
	}

}
