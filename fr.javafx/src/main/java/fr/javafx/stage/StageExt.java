package fr.javafx.stage;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.javafx.lang.enums.StageAnchor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageExt extends Stage {

    public static StageExt create(Control _ctrl) {
    	StageExt stage = new StageExt();
    	stage.setScene(new Scene(new BorderPane(_ctrl)));
    	stage.sizeToScene();
    	stage.show();
    	return stage;
    }
    public static StageExt create(String _title, Control _ctrl) {
    	StageExt stage = new StageExt();
    	stage.setTitle(_title);
    	stage.setScene(new Scene(new BorderPane(_ctrl)));
    	stage.sizeToScene();
    	stage.show();
    	return stage;
    }
    public static StageExt create(String _title, Control _ctrl, int _width, int _height) {
    	StageExt stage = new StageExt();
    	stage.setTitle(_title);
    	stage.setScene(new Scene(new BorderPane(_ctrl), _width, _height));
    	stage.sizeToScene();
    	stage.show();
//    	stage.setSize(_width, _height);
    	return stage;
    }
    public static StageExt create(String _title, Control _ctrl, double _x, double _y, int _width, int _height) {
    	StageExt stage = new StageExt();
    	stage.setTitle(_title);
    	stage.setScene(new Scene(new BorderPane(_ctrl), _width, _height));
    	stage.sizeToScene();
    	stage.show();
//    	stage.setSize(_width, _height);
    	return stage;
    }
    public static StageExt create(Pane _pane) {
    	StageExt stage = new StageExt();
    	stage.setScene(new Scene(_pane));
    	stage.sizeToScene();
    	stage.show();
    	return stage;
    }
    public static StageExt create(Pane _pane, StageStyle _style) {
    	StageExt stage = new StageExt(_style);
    	stage.setScene(new Scene(_pane));
    	stage.sizeToScene();
    	stage.show();
    	return stage;
    }
    public static StageExt create(Pane _pane, StageStyle _style, StageAnchor _anchor) {
    	StageExt stage = new StageExt(_style, _anchor);
    	stage.setScene(new Scene(_pane));
    	stage.sizeToScene();
    	stage.show();
    	return stage;
    }
    public static StageExt create(Pane _pane, StageStyle _style, StageAnchor _anchor, Screen _screen) {
    	StageExt stage = new StageExt(_style, _anchor, _screen);
    	stage.setScene(new Scene(_pane));
    	stage.sizeToScene();
    	stage.show();
    	return stage;
    }
    public static StageExt create(Pane _pane, double _width, double _height, StageStyle _style, StageAnchor _anchor, Screen _screen) {
    	StageExt stage = new StageExt(_style, _anchor, _screen);
    	stage.setScene(new Scene(_pane, _width, _height));
    	stage.show();
    	return stage;
    }

    private final SimpleRectangle2D bBox;
    private Screen      			preferredScreen;
    private StageAnchor 			anchor;

    private SimpleDoubleProperty 	xProperty      = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setX(newValue);
        }

        @Override
        public double get() {
            return getX();
        }
    };
    private SimpleDoubleProperty 	yProperty      = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setY(newValue);
        }

        @Override
        public double get() {
            return getY();
        }
    };

    private SimpleDoubleProperty 	widthProperty  = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setWidth(newValue);
        }

        @Override
        public double get() {
            return getWidth();
        }
    };
    private SimpleDoubleProperty 	heightProperty = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setHeight(newValue);
        }

        @Override
        public double get() {
            return getHeight();
        }
    };
    
    private SimpleBooleanProperty 	showProperty  = new SimpleBooleanProperty() {
        @Override
        public void set(boolean newValue) {
            if(newValue) { show(); }
            else  { hide(); }
        }

        @Override
        public boolean get() {
            return isShowing();
        }
    };

    public StageExt() {
    	super();
        initStyle(StageStyle.DECORATED);
        bBox = new SimpleRectangle2D();
    }
    public StageExt(StageStyle _style) {
    	super();
        initStyle(_style);
        bBox            = new SimpleRectangle2D();
    	anchor          = null;
    	preferredScreen = null;
    }
    public StageExt(StageStyle _style, StageAnchor _anchor) {
    	this(_style);

        setPosition(anchor = _anchor);
    }
    public StageExt(StageStyle _style, StageAnchor _anchor, Screen _screen) {
    	this(_style);

        setPosition(anchor = _anchor, preferredScreen = _screen);
    }
    /*
    public StageExt(Pane _ap, StageStyle _style) {
    	this(_style);
   
    	if(_ap != null)
    		setSize(_ap.getPrefWidth(), _ap.getPrefHeight());
        setPosition(StageAnchor.SCREEN_CENTER);
    }
    public StageExt(Pane _ap, StageStyle _style, StageAnchor _anchor) {
    	this(_ap, _style);

    	if(_ap != null)
    		setSize(_ap.getPrefWidth(), _ap.getPrefHeight());
        setPosition(anchor = _anchor);
    }
    public StageExt(Pane _ap, StageStyle _style, StageAnchor _anchor, Screen _screen) {
    	this(_ap, _style);

    	if(_ap != null)
    		setSize(_ap.getPrefWidth(), _ap.getPrefHeight());
        setPosition(anchor = _anchor, preferredScreen = _screen);
    }
	*/

    public SimpleDoubleProperty xWritableProperty() {
        return xProperty;
    }
    public SimpleDoubleProperty yWritableProperty() {
        return yProperty;
    }

    public SimpleDoubleProperty widthWritableProperty() {
        return widthProperty;
    }
    public SimpleDoubleProperty heightWritablenProperty() {
        return heightProperty;
    }

    public SimpleBooleanProperty showProperty() {
        return showProperty;
    }

    public void setSize(double _width, double _height) {
    	bBox.setWidth(_width);
    	bBox.setHeight(_height);

        setWidth(_width);
        setHeight(_height);
    }

    public void setPosition(Point2D _p) {
    	setTopLeft(_p.getY(), _p.getX());
    }
    public void setPosition(double _left, double _top) {
    	setTopLeft(_top, _left);
    }
    public void setPosition(StageAnchor _anchor) {
        StageAnchor.setPosition(this, _anchor);
    }
    public void setPosition(StageAnchor _anchor, Screen _screen) {
        StageAnchor.setPosition(this, _anchor, _screen);
    }

    public Point2D getTopLeft() {
        return bBox.getTopLeft();
    }
    public void    setTopLeft(Point2D _p) {
    	setTopLeft(_p.getY(), _p.getX());
    }
    public void    setTopLeft(double _top, double _left) {
        bBox.setTopLeft(_top, _left);
        setX(bBox.getX());
        setY(bBox.getY());
    }

    public Point2D getTopRight() {
        return bBox.getTopRight();
    }
    public void    setTopRight(Point2D _p) {
    	setTopRight(_p.getY(), _p.getX());
    }
    public void    setTopRight(double _top, double _right) {
        bBox.setTopRight(_top, _right);
        setX(bBox.getX());
        setY(bBox.getY());
    }

    public Point2D getBottomRight() {
        return bBox.getBottomRight();
    }
    public void    setBottomRight(Point2D _p) {
    	setBottomRight(_p.getY(), _p.getX());
    }
    public void    setBottomRight(double _bottom, double _right) {
        bBox.setBottomRight(_bottom, _right);
        setX(bBox.getX());
        setY(bBox.getY());
    }

    public Point2D getBottomLeft() {
        return bBox.getBottomRight();
    }
    public void    setBottomLeft(Point2D _p) {
    	setBottomLeft(_p.getY(), _p.getX());
    }
    public void    setBottomLeft(double _bottom, double _left) {
        bBox.setBottomLeft(_bottom, _left);
        setX(bBox.getX());
        setY(bBox.getY());
    }

}
