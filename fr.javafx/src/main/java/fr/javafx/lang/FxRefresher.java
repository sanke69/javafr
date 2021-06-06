package fr.javafx.lang;

import java.util.ArrayList;
import java.util.List;

import fr.javafx.event.RefreshEvent;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public interface FxRefresher {
	public static final int PAUSE_MS = 10;

	public static double   			fpsControl(FxRefreshable _refreshable) {
		return instance().unit(_refreshable).currentFps;
	}

	public static void   			registerControl(FxRefreshable _refreshable) {
		instance().registerNewControl(_refreshable);
	}
	public static void   			unregisterControl(FxRefreshable _refreshable) {
		instance().unregisterOldControl(_refreshable);
	}

	public static AbstractRefresher instance() {
		return MonoThreadRefresher.instance();
	}

	// Implementation
	static class FxRefreshableUnit {
		protected final FxRefreshable 	wrapped;
		protected boolean				refreshInProgress;

		protected long					frameLastInstant;
		protected long					frameCount;
		protected double				currentFps;

		FxRefreshableUnit(FxRefreshable _wrapped) {
			wrapped = _wrapped;
		}

		public boolean 	refreshRequested() {
			if(refreshInProgress)
				return false;

			if( wrapped instanceof FxRefreshable.WithFPS ) {
				FxRefreshable.WithFPS fps = (FxRefreshable.WithFPS) wrapped;

				if( fps.isRefreshRequested() )
					return true;

				if( fps.getPreferredFps() <= 0d )
					return false;

				long    lastRequest = System.currentTimeMillis() - frameLastInstant;
				boolean needRefresh = lastRequest > ( 1e3 / fps.getPreferredFps() );

				return needRefresh ;
			}

			return wrapped.isRefreshRequested() && !refreshInProgress;
		}

		public void 	process() {
			refreshInProgress = true;

			wrapped.refresh();

			frameCount++;
			frameLastInstant = System.currentTimeMillis();
			if(System.currentTimeMillis() - frameLastInstant > 1e3) {
				currentFps       = 1e3 * frameCount / (System.currentTimeMillis() - frameLastInstant);
				frameCount       = 0;
			}

			wrapped.fireEvent( new RefreshEvent() );

			refreshInProgress = false;
		}

	}

	public void   					registerNewControl(FxRefreshable _refreshable);
	public void   					unregisterOldControl(FxRefreshable _refreshable);

	abstract class AbstractRefresher    implements FxRefresher {
		final List<FxRefreshableUnit> units;

		AbstractRefresher() {
			super();
			units = new ArrayList<FxRefreshableUnit>();
		}

		public FxRefreshableUnit 	unit(FxRefreshable _control) {
			return units.stream()
							.filter(ra -> ra.wrapped.equals(_control))
							.findFirst()
							.orElse(null);
		}

		public void 				registerNewControl(FxRefreshable _control) {
			AbstractRefresher         instance  = FxRefresher.instance();
			List<FxRefreshableUnit> animators = instance.units;

			synchronized (animators) {
				FxRefreshableUnit animator = new FxRefreshableUnit( _control );

				animators.add(animator);
			}
		}
		public void 				unregisterOldControl(FxRefreshable _control) {
			AbstractRefresher         instance  = FxRefresher.instance();
			List<FxRefreshableUnit> animators = instance.units;

			synchronized (animators) {
				FxRefreshableUnit animator = unit(_control);

				if(animator != null)
					animators.remove(animator);
			}
		}

	}
	public class   MonoThreadRefresher  extends    AbstractRefresher {
		private boolean isRunning;

		private MonoThreadRefresher() {
			super();
			new Thread(this::treatRequests).start();
		}
	
		private void treatRequests() {
			AbstractRefresher         instance  = FxRefresher.instance();
			List<FxRefreshableUnit> animators = instance.units;

			isRunning = true;
			while(isRunning) {
				synchronized (animators) {
					for(FxRefreshableUnit refresher : animators)
						if(refresher.refreshRequested())
							Platform.runLater(refresher::process);
				}

				try { Thread.sleep(PAUSE_MS); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}

		private static MonoThreadRefresher instance;
		static MonoThreadRefresher instance() {
			if(instance == null)
				instance = new MonoThreadRefresher();
			return instance;
		}

	}
	public class   MultiThreadRefresher extends    AbstractRefresher {

		static class RefresherService extends ScheduledService<Void> {
			FxRefreshableUnit animator;
	
			RefresherService(FxRefreshableUnit _animator) {
				super();
				animator = _animator;
				
				if( _animator.wrapped instanceof FxRefreshable.WithFPS ) {
					FxRefreshable.WithFPS animated = (FxRefreshable.WithFPS) _animator.wrapped;
					animated.preferredFpsProperty().addListener((_obs, _old, _new) -> setPeriod(Duration.millis(1000.0 / _new.intValue())));
				}
				setPeriod(Duration.millis(PAUSE_MS));
				start();
			}

			@Override
			protected Task<Void> createTask() {
				return new RefresherTask();
			}
	
			class RefresherTask extends Task<Void> {
				RefresherTask() {
					super();
				}
	
				@Override
				protected Void call() {
					if(animator.refreshRequested())
						Platform.runLater(animator::process);
					
					return null;
				}
			}
			
		}

		private List<RefresherService> services;

		private MultiThreadRefresher() {
			super();
			services = new ArrayList<RefresherService>();
		}

		public RefresherService 	service(FxRefreshable _control) {
			return services.stream()
							.filter(ra -> ra.animator.wrapped.equals(_control))
							.findFirst()
							.orElse(null);
		}

		public void registerNewControl(FxRefreshable _refreshable) {
			super.registerNewControl(_refreshable);

			services.add( new RefresherService(unit(_refreshable)) );
		}
		public void unregisterOldControl(FxRefreshable _refreshable) {
			super.unregisterOldControl(_refreshable);

			RefresherService service = service(_refreshable);

			if(service != null)
				services.remove(service);
		}

		private static MultiThreadRefresher instance;
		static MultiThreadRefresher instance() {
			if(instance == null)
				instance = new MultiThreadRefresher();
			return instance;
		}

	}

}
