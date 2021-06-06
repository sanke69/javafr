package fr.java.draw.tools;

import java.awt.Toolkit;

public class Fonts {
	static final double dpi;
	
	static {
		dpi = Toolkit.getDefaultToolkit().getScreenResolution();
	}

	// https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html#getSize()
	public static double getHeight(Font _font) {
		System.out.println("Text height ~ " + (72d * _font.getSize() / dpi) + "\t(" + _font.getSize() + ")");
		return 72d * _font.getSize() / dpi;
	}
	
	public static double getWidth(String _text, Font _font) {
		System.out.println("Text width ~ " + (_text.length() * getHeight(_font)));
		return _text.length() * getHeight(_font);
	}

}
