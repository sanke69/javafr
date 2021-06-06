package fr.javafx.xtra.capture;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.java.jvm.properties.id.IDs;
import fr.java.lang.enums.state.ServiceState;
import fr.java.sdk.patterns.service.ServiceAdapter;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class CaptureFxUtility extends ServiceAdapter {

	public interface SnapshotProcessor {
		public void initialize(int _width, int _height, double _fps);
		public void process(Image _img, int _index);
		public void finalize();
	}

	private Scene 			scene;
	private Node 			node;
	private Queue<Image> 	snapshots;
	private double			fps;

	boolean            		isRunning = false;

	Snapshoter				snapshoter;
	SnapshotProcessorRunner	processor;

	public CaptureFxUtility(double _fps, SnapshotProcessor _processor) {
		super(IDs.random(), "CaptureFx");
		scene     = null;
		node      = null;
		fps       = _fps;

		snapshots = new ConcurrentLinkedQueue<Image>();
		
		snapshoter = new Snapshoter();
		processor  = new SnapshotProcessorRunner(_processor);
		setState(ServiceState.unavailable);
	}
	public CaptureFxUtility(Scene _scene, double _fps, SnapshotProcessor _processor) {
		this(_fps, _processor);
		scene     = _scene;
		node      = null;
		setState(ServiceState.ready);
	}
	public CaptureFxUtility(Node _node, double _fps, SnapshotProcessor _processor) {
		this(_fps, _processor);
		scene     = null;
		node      = _node;
		setState(ServiceState.ready);
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
		isRunning = true;

		setState(ServiceState.starting);
		snapshoter.start();
		processor.start();
		setState(ServiceState.started);
	}
	public void stop() {
		isRunning = false;

		setState(ServiceState.stopping);
		snapshoter.stop();
		processor.stop();
		setState(ServiceState.stopped);
	}

	class Snapshoter {
		Thread thread;

		final SnapshotParameters params = new SnapshotParameters();
		final Runnable           task   = () -> {
			while(CaptureFxUtility.this.isRunning) {
				Platform.runLater(() -> {
					WritableImage snapshot;

					if(scene != null)
						snapshot = scene.snapshot(null);
					else if(node != null)
						snapshot = node.snapshot(params, null);
					else throw new IllegalAccessError();

					snapshots.add(snapshot);
				});

				try {
					Thread.sleep((long) (1e3 / fps));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Snapshoter() {
			super();
		}
		public void start() {
			thread = new Thread(task);
			thread.start();
		}
		public void stop() {
			synchronized (thread) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			thread = null;
		}
	}

	class SnapshotProcessorRunner {
		Thread thread;

		final SnapshotProcessor processor;
		final Runnable          task;

		SnapshotProcessorRunner(SnapshotProcessor _processor) {
			super();
			processor = _processor;
			task      = () -> {
				int counter = 0;

				if(scene != null)
					processor.initialize((int) scene.getWidth(), (int) scene.getHeight(), fps);
				else if(node != null)
					processor.initialize((int) node.getBoundsInParent().getWidth(), (int) node.getBoundsInParent().getHeight(), fps);
				else throw new IllegalAccessError();
				
				while(CaptureFxUtility.this.isRunning || !CaptureFxUtility.this.snapshots.isEmpty()) {
					Image snapshot = null;
					while((snapshot = CaptureFxUtility.this.snapshots.poll()) != null)
						processor.process(snapshot, counter++);

					try { Thread.sleep(50); } catch (InterruptedException e) { }
				}
				processor.finalize();
			};

		}
		
		public void start() {
			thread = new Thread(task);
			thread.start();
		}
		public synchronized void stop() {
			synchronized (thread) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			thread = null;
		}

	}

}
