package fr.java.sdk.patterns.mvc;

import fr.java.beans.impl.AbstractBean;
import fr.java.beans.impl.BooleanBeanProperty;
import fr.java.beans.impl.ObjectBeanProperty;
import fr.java.beans.impl.ReadOnlyBooleanBeanProperty;
import fr.java.beans.impl.SimpleBooleanBeanProperty;
import fr.java.beans.impl.SimpleObjectBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.lang.properties.ID;
import fr.java.patterns.ViewBean;
import fr.java.patterns.ViewRequestBean;
import fr.java.patterns.capabilities.Identifiable;

public /*abstract*/ class AbstractViewModelBean extends AbstractBean implements ViewBean.Editable {
	private static final long serialVersionUID = -8254411782251442612L;

	private final ObjectBeanProperty<ID>				id;
	@Deprecated
	private final ObjectBeanProperty<ViewRequestBean>	viewRequest;

	private final BooleanBeanProperty					visible;
	private final BooleanBeanProperty  					selectable, selected, selectionRequested;
	private final BooleanBeanProperty  					resizable,  resizing;
	private final BooleanBeanProperty  					moveable,   moving;

	protected AbstractViewModelBean() {
		this(null, true, true, true);
	}
	protected AbstractViewModelBean(final ID _id, boolean _selectable, boolean _moveable, boolean _resizable) {
		super();
		id      	= new SimpleObjectBeanProperty<ID>(this, "id", 			_id);

		visible 	= new SimpleBooleanBeanProperty(this, "visible", 		true);

		selectable 	= new SimpleBooleanBeanProperty(this, "selectable", 	_selectable);
		selected 	= new SimpleBooleanBeanProperty(this, "selected", 		false);

		moveable 	= new SimpleBooleanBeanProperty(this, "moveable", 		_moveable);
		moving 		= new SimpleBooleanBeanProperty(this, "moving", 		false);

		resizable 	= new SimpleBooleanBeanProperty(this, "resizable", 		_resizable);
		resizing 	= new SimpleBooleanBeanProperty(this, "resizing", 		false);

		selectionRequested = new SimpleBooleanBeanProperty(this,                 "selectionRequested", false);
		viewRequest	       = new SimpleObjectBeanProperty<ViewRequestBean>(this, "viewRequest", null);
	}

	@Override
	public ObjectBeanProperty<ID> 					idProperty() {
		return id;
	}
	public void 									setId(final ID _id) {
		if(id.get() != null)
			throw new IllegalAccessError("ID already set !");
		id.set(_id);
	}
	@Override
	public final ID	 								getId() {
		return id.get();
	}

	@Override
	public BooleanBeanProperty 						visibleProperty() {
		return visible;
	}

	@Override
	public void 									setVisible(boolean _enable) {
		visible.set(_enable);
	}
	@Override
	public boolean 									isVisible() {
		return visible.get();
	}

//	@Override
	public final boolean 							isViewRequestInitialized() {
		return viewRequest != null;
	}

//	@Override
	public final ObjectBeanProperty<ViewRequestBean> viewRequestProperty() {
		return _visualizationRequestProperty();
	}

//	@Override
	public void 									setViewRequest(final ViewRequestBean _viewRequest) {
		_visualizationRequestProperty().set(_viewRequest);
	}
	@Override
	public final ViewRequestBean 					getViewRequest() {
		return viewRequestProperty().get();
	}

    private ObjectBeanProperty<ViewRequestBean> 	_visualizationRequestProperty() {
        if (viewRequest.get() == null)
        	viewRequest.set(new SimpleViewRequestBean());

        return viewRequest;
    }

	@Override
	public void 									setMoveable(boolean _enabled) {
		if(isMoveable())
			moveable.set(_enabled);
	}
	@Override
	public boolean 									isMoveable() {
		return moveable.get();
	}

	@Override
	public void 									setResizable(boolean _enabled) {
		if(isResizable())
			resizable.set(_enabled);
	}
	@Override
	public boolean 									isResizable() {
		return resizable.get();
	}

	@Override
	public void 									setSelectable(boolean _enabled) {
		if(isSelectable())
			selectable.set(_enabled);
	}
	@Override
	public boolean 									isSelectable() {
		return selectable.get();
	}

	@Override
	public boolean 									isSelected() {
		return selected.get();
	}
	@Override
	public boolean 									isMoving() {
		return moving.get();
	}
	@Override
	public boolean 									isResizing() {
		return resizing.get();
	}

	@Override
	public BooleanBeanProperty 						movableProperty() {
		return moveable;
	}
	@Override
	public BeanProperty<Boolean> 					selectableProperty() {
		return selectable;
	}
	@Override
	public BooleanBeanProperty 						resizableProperty() {
		return resizable;
	}
	
	@Override
	public ReadOnlyBooleanBeanProperty 				movingProperty() {
		return moving;
	}
	@Override
	public ReadOnlyBooleanBeanProperty 				resizingProperty() {
		return resizing;
	}
	@Override
	public ReadOnlyBeanProperty<Boolean> 			selectedProperty() {
		return selected;
	}

	@Override
	public boolean requestSelection(boolean b) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCloseable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCloseRequested() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ReadOnlyBeanProperty<Boolean> closeRequestedProperty() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setCloseable(boolean _enable) {
		// TODO Auto-generated method stub
		
	}

}
