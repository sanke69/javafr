package fr.session.test;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import fr.java.sdk.lang.SoftPtr;
import fr.java.sdk.security.encryption.GuardedString;
import fr.java.user.session.Session;
import fr.java.user.session.SessionAuthenticator;
import fr.java.user.session.SessionUser;
import fr.java.user.session.exceptions.InvalidSessionParameter;
import fr.session.SessionService;
import fr.session.SessionUserBean;
import fr.session.graphics.SessionControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SessionLoginTest extends Application {

	private static final SessionAuthenticator 						authenticator;

	private static final BiFunction<String, GuardedString, Session> onLoginTest;
	private static final Consumer<Session> 							onLoginSuccess;
	private static final Runnable									onLogout;

	static {
//		authenticator = new SessionService().getDefaultImplementation();
		authenticator = new SessionService().getImplementation("MonoSessionAuthenticator");

		onLoginTest = (user, pswd) -> {
			final SoftPtr<Session> session = SoftPtr.empty();

			pswd.access(decoded -> {
				String      magic      = new String(decoded);
				SessionUser userBean   = new SessionUserBean(user, magic, null);
				Session     newSession = null;
				try {
					newSession = authenticator.openSession(userBean);
				} catch (InvalidSessionParameter e) {
					e.printStackTrace();
				}
				session.set(newSession);
			});

			return session.isEmpty() ? null : session.get();
		};

		onLoginSuccess = session -> System.out.println("OK");

		onLogout = () -> {};
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setScene(new Scene(new StackPane(new SessionControl(onLoginTest, onLoginSuccess, onLogout))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
