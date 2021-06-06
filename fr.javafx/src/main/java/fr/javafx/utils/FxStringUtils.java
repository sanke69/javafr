package fr.javafx.utils;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FxStringUtils {

	public static double computeTextWidth(String text, Font font, double wrappingWidth) {
	    Text helper = new Text();
	    helper.setFont(font);
	    helper.setText(text);
	    // Note that the wrapping width needs to be set to zero before
	    // getting the text's real preferred width.
	    helper.setWrappingWidth(0);
	    helper.setLineSpacing(0);
	    double w = Math.min(helper.prefWidth(-1), wrappingWidth);
	    helper.setWrappingWidth((int)Math.ceil(w));
	    double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
	    return textWidth;
	}

	public static void reportSize(String s, Font myFont) {
	    Text text = new Text(s);
	    text.setFont(myFont);
	    Bounds tb = text.getBoundsInLocal();
	    Rectangle stencil = new Rectangle(
	            tb.getMinX(), tb.getMinY(), tb.getWidth(), tb.getHeight()
	    );

	    Shape intersection = Shape.intersect(text, stencil);

	    Bounds ib = intersection.getBoundsInLocal();
	    System.out.println(
	            "Text size: " + ib.getWidth() + ", " + ib.getHeight()
	    );
	}
	
	public static void main(String[] args) {
		reportSize("HelloFX", new Font(10));
		System.out.println( computeTextWidth("HelloFX", new Font(10), 100) );
		reportSize("HelloFX", new Font(12));
		reportSize("HelloFX", new Font(14));
		reportSize("HelloFX", new Font(16));
	}
	
}
