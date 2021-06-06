package fr.javafx.xtra.capture;

import fr.java.jvm.properties.id.IDs;
import fr.java.lang.enums.state.ServiceState;
import fr.java.sdk.patterns.service.ServiceAdapter;
import fr.javafx.xtra.Clipboard;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class SnapshotFxUtility extends ServiceAdapter {

	private Scene 			scene;
	private Node 			node;

	private WritableImage 	snapshot;

	boolean            		copyToClipboard = true;

	Snapshoter				snapshoter;

	public SnapshotFxUtility() {
		super(IDs.random(), "SnapshotFx");
		scene     = null;
		node      = null;

		snapshoter = new Snapshoter();
		setState(ServiceState.unavailable);
	}

	public void setTarget(Scene _scene) {
		scene     = _scene;
		node      = null;

		if(_scene == null)
			setState(ServiceState.unavailable);
		else
			setState(ServiceState.ready);
	}
	public void setTarget(Node _node) {
		scene     = null;
		node      = _node;

		if(_node == null)
			setState(ServiceState.unavailable);
		else
			setState(ServiceState.ready);
	}

	public void start() {
		setState(ServiceState.starting);
		setState(ServiceState.started);
		snapshoter.process();
		setState(ServiceState.stopping);
		setState(ServiceState.stopped);
	}
	public void stop() {
		;
	}

	class Snapshoter {
		final SnapshotParameters params = new SnapshotParameters();
		final Runnable           task   = () -> {
			Platform.runLater(() -> {
				if(scene != null)
					snapshot = scene.snapshot(null);
				else if(node != null)
					snapshot = node.snapshot(params, null);
				else throw new IllegalAccessError();
				
				if(copyToClipboard)
					Clipboard.System.setImageContent(snapshot);
			});
		};

		Snapshoter() {
			super();
		}
		public void process() {
			task.run();
		}

	}

}
