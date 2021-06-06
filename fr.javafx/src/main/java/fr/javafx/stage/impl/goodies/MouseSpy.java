package fr.javafx.stage.impl.goodies;

import java.awt.MouseInfo;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import fr.javafx.io.mouse.MouseStatus;

public class MouseSpy extends Stage {
	MouseStatus		mouse	= new MouseStatus();
	MouseService	service;

	private Label	screenLabel, sceneLabel, nodeLabel;
	private Label	xScreen, yScreen;
	private Label	xScene, yScene;
	private Label	xNode, yNode;

	public MouseSpy() {
		super();

		screenLabel = new Label("SCREEN");
		sceneLabel = new Label("SCENE");
		nodeLabel = new Label("NODE");

		xScreen = new Label();
		yScreen = new Label();
		xScene = new Label();
		yScene = new Label();
		xNode = new Label();
		yNode = new Label();

		VBox screenFrame = new VBox(screenLabel, xScreen, yScreen);
		VBox sceneFrame  = new VBox(sceneLabel, xScene, yScene);
		VBox nodeFrame   = new VBox(nodeLabel, xNode, yNode);

		HBox mainFrame   = new HBox(screenFrame, new Separator(), sceneFrame, new Separator(), nodeFrame);

		setScene(new Scene(mainFrame));
		sizeToScene();
		show();

		startMouseService();
		
		Locale locale  = new Locale("fr", "FR");
		xScreen.textProperty().bind(Bindings.format(locale, "%,.0f", mouse.xScreenProperty()));
		yScreen.textProperty().bind(Bindings.format(locale, "%,.0f", mouse.yScreenProperty()));

		xScene.textProperty().bind(Bindings.format(locale, "%,.0f", mouse.xSceneProperty()));
		yScene.textProperty().bind(Bindings.format(locale, "%,.0f", mouse.ySceneProperty()));

		xNode.textProperty().bind(Bindings.format(locale, "%,.0f", mouse.xProperty()));
		yNode.textProperty().bind(Bindings.format(locale, "%,.0f", mouse.yProperty()));
	}

	public void startMouseService() {
		service = new MouseService();
		service.setOnSucceeded((WorkerStateEvent t) -> {
			try { Thread.sleep(25); } catch(InterruptedException e) { }
			service.restart();
		});
		service.start();
	}

	public void attachTo(Node _node) {
		_node.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouse.setScreenX(event.getScreenX());
				mouse.setScreenY(event.getScreenY());
				mouse.setSceneX(event.getSceneX());
				mouse.setSceneY(event.getSceneY());
				mouse.setX(event.getX());
				mouse.setY(event.getY());
			}
		});

		_node.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouse.setX(-1);
				mouse.setY(-1);
			}
		});
	}

	private class MouseService extends Service<MouseStatus> {

		protected Task<MouseStatus> createTask() {
			final MouseStatus _mouse = mouse;

			return new Task<MouseStatus>() {

				@Override
				protected MouseStatus call() {
					Platform.runLater(() -> {					
						_mouse.setScreenX(MouseInfo.getPointerInfo().getLocation().getX());
						_mouse.setScreenY(MouseInfo.getPointerInfo().getLocation().getY());
					});
					return _mouse;
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					updateMessage("Done!");
				}

				@Override
				protected void cancelled() {
					super.cancelled();
					updateMessage("Cancelled!");
				}

				@Override
				protected void failed() {
					super.failed();
					updateMessage("Failed!");
				}

			};
		}

	}

}