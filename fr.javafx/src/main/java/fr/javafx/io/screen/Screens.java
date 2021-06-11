package fr.javafx.io.screen;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.java.maths.geometry.types.Points;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

//see. https://ixquick-proxy.com/do/spg/show_picture.pl?l=francais&rais=1&oiu=http%3A%2F%2Fblog.rifix.net%2Fwp-content%2Fuploads%2F2015%2F07%2FShow-My-Screen_Display-Resolution.png&sp=5b725e7850111e77d675b134b21ecfde

public class Screens {

	public static BoundingBox.TwoDims.Editable visualBounds = null;

	
	public enum ScreenResolution {
		CGA(320, 200),				// 8:5
		QVGA(320, 240),				// 4:3
		VGA(640, 480),				// 4:3
		PAL(768, 576),				// 4:3
		WVGA(800, 480),				// 
		SVGA(800, 600),				// 4:3
		FWVGA(854, 480),			//
		XGA(1024, 768),				// 4:3
		WSVGA(1024, 600),			//
		SXGA(1280, 1024),			// 4:3
		HD720(1280, 720),			//
		WXGA(1280, 768),			//
		WXGAAlt(1280, 800),			//
		SXGAPlus(1400, 1050),		// 4:3
		UXGA(1600, 1200),			// 4:3
		WSXGAPlus(1680, 1050),		//
		HD1080(1920, 1080),			//
		WUXGA(1920, 1200),			//
		QXGA(2048, 1536),			// 4:3
		TWOK(2040, 1800),			//
		QSXGA(2560, 2048),			// 4:3
		WQXGA(2560, 1600);			//
		
		int width, height;
		
		ScreenResolution(int _width, int _height) {
			width = _width;
			height = _height;
		}
		
		public int horizontalResolution() {
			return width;
		}
		public int verticalResolution() {
			return height;
		}
		public int pixelResolution() {
			return width * height;
		}
		public double format() {
			return width / height;
		}
		
		public String toString() {
			return width + "x" + height;
		}

	}

	static Map<Screen, Integer> screens = new LinkedHashMap<Screen, Integer>();
	static {
		updateDisplayInfos();
	}

	public static void updateDisplayInfos() {
		visualBounds = new SimpleRectangle2D();

		screens.clear();
		int id = 0;
		for(Screen scr : Screen.getScreens()) {
			screens.put(scr, id++);

			Rectangle2D visualBnds = scr.getVisualBounds();
			if(visualBounds.getMinX() > visualBnds.getMinX())
				visualBounds.setMinX(visualBnds.getMinX());
			if(visualBounds.getMinY() > visualBnds.getMinY())
				visualBounds.setMinY(visualBnds.getMinY());
			if(visualBounds.getMaxX() < visualBnds.getMaxX())
				visualBounds.setMaxX(visualBnds.getMaxX());
			if(visualBounds.getMaxY() < visualBnds.getMaxY())
				visualBounds.setMaxY(visualBnds.getMaxY());
		}
	}

	private static boolean isHeadless() {
		return GraphicsEnvironment.isHeadless();
	}

	private static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}

	private static ScreenAccessor accessor = null;

	public static class ScreenAccessor {
		private static Method getRenderScale = null;

		private Method getScaleMethod() throws NoSuchMethodException {
			if(getRenderScale == null) {
				getRenderScale = lookupScaleMethod();
			}

			return getRenderScale;
		}

		private Method lookupScaleMethod() throws NoSuchMethodException {
			Method scaleMethod;
			try {
				scaleMethod = Screen.class.getDeclaredMethod("getScale"); // until 8u51
			} catch(NoSuchMethodException e) {
				scaleMethod = Screen.class.getDeclaredMethod("getRenderScale");
			}
			scaleMethod.setAccessible(true);
			return scaleMethod;
		}

		public float getRenderScale(Screen screen) {
			try {
				Method m = getScaleMethod();
				return ((float) m.invoke(screen));
			} catch(ReflectiveOperationException e) {
				return 1f;
			}
		}
	}

	public static ScreenAccessor getScreenAccessor() {
		if(accessor == null)
			accessor = new ScreenAccessor();

		return accessor;
	}

	public static Set<Screen> getScreens() {
		return screens.keySet();
	}
	public static int getScreenCount() {
		return screens.size();
	}
	public static int getActiveScreenId() {
		Point2D cursor = Points.of(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
		return screens.get(Screen.getScreensForRectangle(cursor.getX(), cursor.getY(), 1, 1).get(0));
	}
	
	public static Screen getActiveScreen() {
		Point2D cursor = Points.of(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
		return Screen.getScreensForRectangle(cursor.getX(), cursor.getY(), 1, 1).get(0);
	}
	public static Screen getLeftestScreen() {
		return Screen.getScreensForRectangle(1, 1, 1, 1).get(0);
	}
	public static Screen getRightestScreen() {
		return Screen.getScreensForRectangle(visualBounds.getMaxX() - 1, visualBounds.getCenterY(), 1, 1).get(0);
	}

	public static Rectangle2D getScreenBounds(int _i) {
		return Screen.getScreens().get(_i).getBounds();
	}
	public static double getScreenDPI(int _i) {
		return Screen.getScreens().get(_i).getDpi();
	}
	public static Rectangle2D getScreenVisualBounds(int _i) {
		return Screen.getScreens().get(_i).getVisualBounds();
	}

	public static void centerOnScreen(Stage _stage, Screen _screen) {
		Rectangle2D primScreenBounds = _screen.getVisualBounds();
		_stage.setX(primScreenBounds.getMinX() + (primScreenBounds.getWidth() - _stage.getWidth()) / 2);
		_stage.setY((primScreenBounds.getHeight() - _stage.getHeight()) / 2);
	}
	public static void centerOnScreen(Stage _stage, int _i) {
		Rectangle2D primScreenBounds = getScreenVisualBounds(_i);
		_stage.setX(primScreenBounds.getMinX() + (primScreenBounds.getWidth() - _stage.getWidth()) / 2);
		_stage.setY((primScreenBounds.getHeight() - _stage.getHeight()) / 2);
	}

}