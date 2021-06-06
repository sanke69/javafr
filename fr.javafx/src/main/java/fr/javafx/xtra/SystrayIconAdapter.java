package fr.javafx.xtra;

import java.awt.Image;
import java.net.URL;

public abstract class SystrayIconAdapter implements Systray.Icon {
	protected Systray.Menu 	menu;
	protected Image  		icon;
	protected URL    		iconUrl;
	protected String 		label;

	public interface SystrayMenuMaker {
		public java.awt.PopupMenu make();
	}

	public SystrayIconAdapter(URL _iconUrl, String _label, Systray.Menu _menu) {
		super();
		
		icon    = null;
		iconUrl = _iconUrl;
		
		label   = _label;
		menu    = _menu;
	}
	public SystrayIconAdapter(Image _icon, String _label, Systray.Menu _menu) {
		super();

		icon	= _icon;
		iconUrl = null;

		label   = _label;
		menu    = _menu;
	}

	public Systray.Menu getMenu() {
		return menu;
	}

	public abstract void addToSystray();
	public abstract void removeFromSystray();
	
	public static java.awt.PopupMenu defaultAWTMenu() {
		java.awt.PopupMenu menu = new java.awt.PopupMenu();
		java.awt.MenuItem aboutItem = new java.awt.MenuItem("About");
		java.awt.MenuItem closeItem = new java.awt.MenuItem("Exit");

		aboutItem.addActionListener(event -> {
			System.out.println("Symbiot v.0.1.0.1-ALPHA TEST");
		});
		closeItem.addActionListener(event -> {
			System.out.println("EXIT");
			javafx.application.Platform.exit();
			System.exit(0);
		});

		menu.add(aboutItem);
		menu.addSeparator();
		menu.add(closeItem);

		return menu;
	}

}
