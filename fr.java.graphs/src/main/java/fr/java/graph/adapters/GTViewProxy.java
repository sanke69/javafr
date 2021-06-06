package fr.java.graph.adapters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.graph.viewer.GTView;
import fr.java.graph.viewer.GTViewer.Graphics;
import fr.java.lang.properties.ID;
import fr.java.patterns.identifiable.Identifiable;

public class GTViewProxy<GO, GTO extends Identifiable, GTL extends GTLayout> implements InvocationHandler, GTView<GO, GTO, GTL> {
	GTO						object;
	GTL						layout;
	GTSkin<GO, GTO, GTL> 	skin;
	GO						graphics;

	GTView<GO, GTO, GTL> 	proxy;

	public GTViewProxy(GTO _object, GTL _layout, GTSkin<GO, GTO, GTL> _skin) {
		super();

		object = _object;
		layout = _layout;
		skin   = _skin;
		proxy  = (GTView<GO, GTO, GTL>) Proxy.newProxyInstance(GTView.class.getClassLoader(), new Class[] { GTView.class }, this );
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invocation de " + method.getName());

        switch(method.getName()) {
        case "getId"       : return getId();
        case "getModel"    : return getModel();
        case "getLayout"   : return getLayout();
        case "getGraphics" : if(args.length > 1)
        						return getGraphics( (Graphics<GO, ?, ?, ?>) args[0] );
        					 else
        						return getGraphics();
        default            : return method.invoke(proxy, args);
        }
	}

	@Override
	public ID 		getId() {
		return object.getId();
	}
	@Override
	public GTO 		getModel() {
		return object;
	}

	@Override
	public GTL 		getLayout() {
		return layout;
	}

	@Override
	public GO 		getGraphics() {
		if(graphics != null)
			return graphics;
		throw new RuntimeException();
	}

	@Override
	public GO 		getGraphics(Graphics<GO, ?, ?, ?> _viewer) {
		if(graphics != null)
			return graphics;
		
		graphics = skin.getGraphics(_viewer);
				
		return graphics;
	}

	@Override
	public void setVisible(boolean _enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSelectable(boolean _value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelectable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requestSelection(boolean b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMoveable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isResizing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isResizable() {
		// TODO Auto-generated method stub
		return false;
	}

}
