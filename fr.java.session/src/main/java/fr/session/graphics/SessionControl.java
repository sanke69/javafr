package fr.session.graphics;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import fr.java.sdk.security.encryption.GuardedString;
import fr.java.user.session.Session;
import fr.java.user.session.SessionUser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SessionControl extends Control {
	public static final int 	CONTROL_WIDTH  = 240;
	public static final int 	CONTROL_HEIGHT = 360;
	public static final int 	GRAPHIC_WIDTH  =  96;
	public static final int 	GRAPHIC_HEIGHT =  96;
	public static final int 	LABEL_HEIGHT   =  20;

	public static final Color 	MSG_OK         =  Color.web("#126912");
	public static final Color 	MSG_ERROR      =  Color.web("#FA3339");

	public static final String 	USER_DEFAULT   =  "/images/user-default.png";
	public static final String 	USER_UNKNOWN   =  "/images/user-unknown.png";
	public static final String 	USER_GUEST     =  "/images/user-guest.png";

	private final boolean 										infoMode;
	private final ObjectProperty<Session> 						session;

	private final BiFunction<String, GuardedString, Session> 	onLogin;
	private final Consumer<Session> 							onLoginSuccess;
	private final Runnable			 							onLogout;

	// LOG MODE
	public SessionControl(BiFunction<String, GuardedString, Session> _onLogin, Consumer<Session> _onLoginSuccess, Runnable _onLogout) {
		super();
		infoMode       = false;
		onLogin        = _onLogin;
		onLoginSuccess = _onLoginSuccess;
		onLogout       = _onLogout;
		session        = new SimpleObjectProperty<Session>();
	}
	// INFO MODE
	public SessionControl(ObjectProperty<Session> _session) {
		super();
		infoMode        = true;
		onLogin         = null;
		onLoginSuccess  = null;
		onLogout        = null;
		session         = _session;
	}

	@Override
	protected Skin<SessionControl> 								createDefaultSkin() {
		if(infoMode)
			return new SessionControlVisualInfo(this);

		if(session.get() == null)
			return new SessionControlVisualLogin(this);
		else
			return new SessionControlVisualLogout(this);
	}

	void switchToLogin() {
		// TODO:: HACK:: Due to Skin.set(...) tests in Skin Class, must change the Skin Class before updating it with AdvancedSkin wrapper...
		setSkin( new Skin<SessionControl>() {
			@Override public SessionControl getSkinnable() 	{ return getSkinnable(); }
			@Override public Node 			getNode() 		{ return new StackPane(); }
			@Override public void 			dispose() 		{ }
		});
		setSkin( new SessionControlVisualLogin(this) );
	}
	void switchToLogout() {
		// TODO:: HACK:: Due to Skin.set(...) tests in Skin Class, must change the Skin Class before updating it with AdvancedSkin wrapper...
		setSkin( new Skin<SessionControl>() {
			@Override public SessionControl getSkinnable() 	{ return getSkinnable(); }
			@Override public Node 			getNode() 		{ return new StackPane(); }
			@Override public void 			dispose() 		{ }
		});
		setSkin( new SessionControlVisualLogout(this) );
	}
	
	public final ObjectProperty<Session> 						sessionProperty() {
		return session;
	}
	public final Optional<SessionUser> 							sessionUser() {
		return session.get() != null ? session.get().getUser() : Optional.empty();
	}

	public final BiFunction<String, GuardedString, Session> 	onLoginHandler() {
		return onLogin;
	}
	public final Consumer<Session> 								onLoginSuccessHandler() {
		return onLoginSuccess;
	}
	public final Runnable										onLogoutHandler() {
		return onLogout;
	}

	public static abstract class SessionControlSkin implements Skin<SessionControl> {
		SessionControl control;

		public SessionControlSkin(SessionControl _control) {
			super();
			control = _control;
		}

		@Override
		public final SessionControl getSkinnable() {
			return control;
		}

		@Override
		public void 				dispose() {
			;
		}

		public Image 				getAvatar(SessionUser _user) {
			String url = _user.getAvatarUrl().orElse(USER_DEFAULT);
			
			return new Image(url);
		}

	}


}
