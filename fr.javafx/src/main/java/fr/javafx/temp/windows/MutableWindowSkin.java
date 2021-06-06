package fr.javafx.temp.windows;

public interface MutableWindowSkin {

	public static interface Docked extends MutableWindowSkin {
		
	}
	public static interface Floating extends MutableWindowSkin {
//		public void maximize();
//		public void minimize();
//		public void restore();
	}
	public static interface Staged extends Floating {
		
	}

	public MutableWindow getControl();

	public void fill();
	public void release();

}