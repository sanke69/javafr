package fr.javafx.behavior;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import fr.javafx.scene.control.viewport.planar.implementations.tiles.TileViewportControl;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public final class AdvancedSkin<C extends Control> implements Skin<C> {
    private final C 			control;
    private final Visual<C> 	visual;
    private final Behavior<C>  	behavior;

    public AdvancedSkin(C _control, Visual<C> _visual) {
        super();

        control  = _control;
        visual   = _visual;
        behavior = null;
    }
    public AdvancedSkin(C _control, Visual<C> _visual, Behavior<C> _behavior) {
        super();

        control  = _control;
        visual   = _visual;
        behavior = _behavior;
    }
    public AdvancedSkin(C _control, Visual<C> _visual, BiFunction<? super C, ? super Visual<C>, ? extends Behavior<C>> _behaviorFactory) {
        super();

        control  = _control;
        visual   = _visual;
        behavior = _behaviorFactory.apply(control, visual);
    }
    public AdvancedSkin(C _control, Function<? super C, ? extends Visual<C>> _visualFactory) {
        super();

        control  = _control;
        visual   = _visualFactory.apply(control);
        behavior = null;
    }
    public AdvancedSkin(C _control, Function<? super C, ? extends Visual<C>> _visualFactory, BiFunction<? super C, ? super Visual<C>, ? extends Behavior<C>> _behaviorFactory) {
        super();

        control  = _control;
        visual   = _visualFactory.apply(control);
        behavior = _behaviorFactory.apply(control, visual);
    }

	//    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return visual.getCssMetaData();
    }

    public C 					getControl() {
    	return control;
    }
    public Visual<C> 			getVisual() {
    	return visual;
    }
    public Behavior<C> 			getBehavior() {
    	return behavior;
    }

	@Override
	public C 					getSkinnable() {
		return getControl();
	}
	@Override
	public Node 				getNode() {
		return getVisual().getNode();
	}
	
	@Override
    public void 				dispose() {
        if(behavior != null)
        	behavior.dispose();

        visual.dispose();
    }

}
