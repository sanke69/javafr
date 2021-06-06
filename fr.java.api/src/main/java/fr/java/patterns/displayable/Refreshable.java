package fr.java.patterns.displayable;

public interface Refreshable {
	public static final double MIN_FPS = 0D;
	public static final double MAX_FPS = 100D;

	public static interface WithFPS   extends Refreshable {

		public double 			getPreferredFps();

	}

	// Refresh Processing
	public boolean 				isRefreshRequested();

	// Refresh Trigger
	public void    				requestRefresh();

	// Refresh Action
	public void    				refresh();

}
