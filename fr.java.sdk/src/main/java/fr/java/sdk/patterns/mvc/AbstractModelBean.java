package fr.java.sdk.patterns.mvc;

import fr.java.beans.impl.AbstractBean;
import fr.java.beans.impl.BooleanBeanProperty;
import fr.java.beans.impl.ObjectBeanProperty;
import fr.java.beans.impl.SimpleBooleanBeanProperty;
import fr.java.beans.impl.SimpleObjectBeanProperty;
import fr.java.lang.properties.ID;
import fr.java.mvc.ViewRequest;
import fr.java.patterns.ModelBean;
import fr.java.patterns.ViewRequestBean;

public abstract class AbstractModelBean extends AbstractBean implements ModelBean {
	private static final long serialVersionUID = -8254411782251442612L;

	private ObjectBeanProperty<ID>			id;
	private final BooleanBeanProperty		visible;
	private ObjectBeanProperty<ViewRequest>	viewRequest;

	protected AbstractModelBean() {
		super();
		id      	= new SimpleObjectBeanProperty<ID>(this, "id", 		null);
		visible 	= new SimpleBooleanBeanProperty(this, "visible", 	true);
	}
	protected AbstractModelBean(final ID _id) {
		super();
		id      	= new SimpleObjectBeanProperty<ID>(this, "id", 		_id);
		visible 	= new SimpleBooleanBeanProperty(this, "visible", 	true);
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
	public final ObjectBeanProperty<ViewRequest> 	viewRequestProperty() {
		return _visualizationRequestProperty();
	}

//	@Override
	public void 									setViewRequest(final ViewRequestBean _viewRequest) {
		_visualizationRequestProperty().set(_viewRequest);
	}
//	@Override
	public final ViewRequest	 					getViewRequest() {
		return viewRequestProperty().get();
	}

    private ObjectBeanProperty<ViewRequest> 	_visualizationRequestProperty() {
        if (viewRequest == null)
        	viewRequest = new SimpleObjectBeanProperty<ViewRequest>	(this, "view-request", 	new SimpleViewRequestBean());

        if (viewRequest.get() == null)
        	viewRequest.set(new SimpleViewRequestBean());

        return viewRequest;
    }

}
