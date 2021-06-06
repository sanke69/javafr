package fr.javafx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import fr.java.multitasks.threads.Threads;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFX {
	static class SoftPtr<C> { C ref; SoftPtr(){} SoftPtr(C _c){ref=_c;} C get(){return ref;} void set(C _c){ref=_c;}};

    public static class DummyApplication extends Application {
    	public DummyApplication() {}
		@Override public void start(Stage primaryStage) throws Exception {}
    }

	public static boolean 						isLaunched() {
		return Threads.getThreadByName("JavaFX-Launcher") != null;
	}

	public static void 							launch() {
		final CountDownLatch latch  = new CountDownLatch(1);

		Platform.startup(() -> {latch.countDown();});

        try { latch.await(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { System.err.println("Must not happened"); }
	}
	public static <APP extends Application> APP launch(Class<APP> _appClass) {
		return launchWithArgs(_appClass, (String[]) null);
	}
	public static <APP extends Application> APP launchWithArgs(Class<APP> _appClass, String... _args) {
    	Platform.setImplicitExit(false);

		final CountDownLatch latch  = new CountDownLatch(1);
		SoftPtr<APP>         appPtr = new SoftPtr<APP>();

		if( ! isLaunched() ) {

			new Thread(() -> {
				Application.launch(_appClass, _args);
				latch.countDown();
			}, "JavaFX-Wrapper").start();

		} else {

			Platform.runLater(() -> {
				try {
					APP   appFx      = _appClass.newInstance(); 
				    Stage appFxStage = new Stage();

					appPtr.set(appFx);
				    appFx.start(appFxStage);

					latch.countDown();
				} catch(Exception e) {
					e.printStackTrace();
				}
			});

		}

        try { latch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace(); return null; }

		return appPtr.get();
	}

	public static void 							runLater(Runnable _runnable) {
		Platform.runLater(_runnable);
	}

	public static void 							terminate() {
    	Platform.setImplicitExit(true);
    	Platform.exit();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Start Java...");
		Thread.sleep(5000);
		System.out.println("Start JavaFX...");
		JavaFX.launch();
		System.out.println("JavaFX is running... ");

		JavaFX.runLater(() -> {
			Stage  s    = new Stage();
			Button btn  = new Button("Hello World !!!");
			VBox   pane = new VBox(btn);
			Scene  scn  = new Scene(pane);
			s.setScene(scn);
			s.show();
			
			btn.setOnMouseClicked((e) -> {
				System.out.println("JavaFX Button clicked !");
			});
			
		});

		Thread.sleep(5000);
		JavaFX.terminate();
		System.out.println("JavaFX is terminated...");

		Thread.sleep(5000);
		System.out.println("Java is terminated...");
		System.out.println("Bye.");
	}

}
