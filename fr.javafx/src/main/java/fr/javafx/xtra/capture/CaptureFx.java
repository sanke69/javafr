package fr.javafx.xtra.capture;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import fr.java.lang.enums.State;
import fr.java.lang.properties.ID;
import fr.java.patterns.service.Service;
import fr.java.state.StateListener;
import fr.javafx.sdk.controls.service.ServiceActionControl;
import fr.javafx.sdk.controls.service.ServiceControl;
import fr.javafx.stage.StageExt;
import fr.javafx.utils.FxImageUtils;
import fr.javafx.utils.FxStageUtils;
import fr.javafx.xtra.capture.processes.SnapshotImageRecorder;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CaptureFx implements Service {

	public static CaptureFx newController() {
		return new CaptureFx();
	}
	public static CaptureFx newController(int _fps, CaptureFxUtility.SnapshotProcessor _processor) {
		return new CaptureFx(_fps, _processor);
	}
	public static CaptureFx newController(Scene _target) {
		return new CaptureFx(_target);
	}
	public static CaptureFx newController(Scene _target, int _fps, CaptureFxUtility.SnapshotProcessor _processor) {
		return new CaptureFx(_target, _fps, _processor);
	}
	public static CaptureFx newController(Node _target, int _fps, CaptureFxUtility.SnapshotProcessor _processor) {
		return new CaptureFx(_target, _fps, _processor);
	}

	CaptureFxController control;
	CaptureFxUtility    service;

	CaptureFx() {
		super();
		service = new CaptureFxUtility(25, new SnapshotImageRecorder(Paths.get("/home/sanke/.capture")));
		control = new CaptureFxController();
	}
	CaptureFx(int _fps, CaptureFxUtility.SnapshotProcessor _processor) {
		super();
		service = new CaptureFxUtility(_fps, _processor);
		control = new CaptureFxController();
	}

	CaptureFx(Scene _target) {
		this();
		service.setTarget(_target);
	}
	CaptureFx(Scene _target, int _fps, CaptureFxUtility.SnapshotProcessor _processor) {
		this(_fps, _processor);
		service.setTarget(_target);
	}
	CaptureFx(Node _target, int _fps, CaptureFxUtility.SnapshotProcessor _processor) {
		this(_fps, _processor);
		service.setTarget(_target);
	}

	@Override
	public ID getId() {
		return service.getId();
	}
	@Override
	public String getName() {
		return service.getName();
	}
	@Override
	public State getState() {
		return service.getState();
	}
	@Override
	public void registerListener(StateListener _listener) {
		service.registerListener(_listener);
	}
	@Override
	public void unregisterListener(StateListener _listener) {
		service.unregisterListener(_listener);
	}
	@Override
	public <T extends EventListener> T[] getListeners(Class<T> _class) {
		return service.getListeners(_class);
	}

	@Override
	public void start() throws Exception {
		service.start();
	}

	@Override
	public void stop() throws Exception {
		service.stop();
	}

	class CaptureFxController extends StageExt {

		public CaptureFxController() {
			super();
			build();
			show();
		}
		
		void build() {
			ServiceControl<CaptureFx> ctrl = new ServiceControl<CaptureFx>(CaptureFx.this, "CaptureFx", ServiceControl.Style.Node, ServiceControl.Mode.Action);
			ctrl.getRightButtons().add(0, new NodeSelecter(ctrl));
			setScene(new Scene(ctrl, 320, 48));
		}

	}

	private static Image target;
	static {
		try {
			target = FxImageUtils.toFXImage(ImageIO.read(NodeSelecter.class.getResourceAsStream("/default/capture/crosshair.png")), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class NodeSelecter extends ServiceActionControl {
		boolean 			inSelection    = false;
		Map<Scene, Cursor> 	previousValues = new HashMap<Scene, Cursor>();

		private final EventHandler<? super MouseEvent> mouseOver = e -> {
			if(e.getSource() instanceof Node) {
				Node   n = (Node) e.getSource();
				Window w = n.getScene().getWindow();

				if(w instanceof Stage)
					((Stage) w).toFront();
				
				n.requestFocus();
			}
        };
		private final EventHandler<? super MouseEvent> mousePressed = e -> {
			if(e.getSource() instanceof Node) {
				Node n = (Node) e.getSource();

				if(n.getScene() == NodeSelecter.this.getScene())
					service.setTarget((Node) null);
				else {
					if(e.isControlDown())
						service.setTarget(n.getScene());
					else
						service.setTarget(n);
				}
				
				finalizeTargetSelection();
			}
        };
		
		public NodeSelecter(ServiceControl<?> _control) {
			super(_control);
			setIcon(target);
			
			setOnAction((evt) -> initializeTargetSelection());
		}

		public void initializeTargetSelection() {
			for(Stage s : FxStageUtils.getAllStages()) {					
				previousValues.put(s.getScene(), s.getScene().getCursor());

				s.getScene().setCursor(new ImageCursor(target));

				s.getScene().getRoot().addEventFilter(MouseEvent.MOUSE_ENTERED, mouseOver);
				s.getScene().getRoot().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
			}
			inSelection = true;
		}
		public void finalizeTargetSelection() {
			for(Stage s : FxStageUtils.getAllStages()) {

				s.getScene().setCursor(previousValues.get(s.getScene()));

				s.getScene().getRoot().removeEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
				s.getScene().getRoot().removeEventFilter(MouseEvent.MOUSE_ENTERED, mouseOver);
			}
			inSelection = false;
			previousValues.clear();
		}

	}

}
