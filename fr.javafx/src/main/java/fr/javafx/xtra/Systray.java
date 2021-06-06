package fr.javafx.xtra;

public interface Systray {

	public interface Icon {
	
		public void addToSystray();
		public void removeFromSystray();
	
	}

	public interface Menu {

		public void toggleShow();

	}

}
