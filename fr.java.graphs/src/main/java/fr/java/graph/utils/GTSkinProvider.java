package fr.java.graph.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;

import fr.java.beans.reflect.utils.Parameters;
import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;
import fr.java.graph.viewer.GTSkin;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.patterns.identifiable.Identifiable;

public class GTSkinProvider<GO, GTO extends Identifiable, GTL extends GTLayout> implements GTSkin.Generator<GO, GTO, GTL> {

	Class<GTL> 									layoutClass;

	Class<? extends GTSkin<GO, GTO, GTL>> 		skinClass;
	Constructor<? extends GTSkin<GO, GTO, GTL>> skinConstructor;
	Object[] 									skinParameters;

	BiFunction<GTO, GTL, GTSkin<GO, GTO, GTL>> 	skinSupplier2;

	public GTSkinProvider(Class<GTL> _layoutClass, Class<? extends GTSkin<GO, GTO, GTL>> _skinClass) {
		super();
		layoutClass     = _layoutClass;
		skinClass       = _skinClass;
	}
	public GTSkinProvider(Class<GTL> _layoutClass, Class<? extends GTSkin<GO, GTO, GTL>> _skinClass, Object[] _skinParameters) {
		super();
		layoutClass     = _layoutClass;
		skinClass       = _skinClass;
		skinParameters  = _skinParameters;
	}
	public GTSkinProvider(Class<GTL> _layoutClass, Class<? extends GTSkin<GO, GTO, GTL>> _skinClass, Constructor<? extends GTSkin<GO, GTO, GTL>> _skinConstructor, Object[] _skinParameters) {
		super();
		layoutClass     = _layoutClass;
		skinClass       = _skinClass;
		skinConstructor = _skinConstructor;
		skinParameters  = _skinParameters;
	}
	public GTSkinProvider(Class<GTL> _layoutClass, BiFunction<GTO, GTL, GTSkin<GO, GTO, GTL>> _skinSupplier) {
		super();
		layoutClass     = _layoutClass;
		skinSupplier2   = _skinSupplier;
	}

	public GTSkin<GO, GTO, GTL>		newInstance(GTO _object, GTL _layout) {
		if(skinClass != null) {
			if(skinParameters == null && skinConstructor == null)
				return getViewFromClassConstructor			(_object, _layout, skinClass);
			if(skinParameters != null && skinConstructor == null)
				return getViewFromClassConstructorWithArgs	(_object, _layout, skinClass, skinParameters);
			if(skinParameters == null && skinConstructor != null)
				return getViewFromClassConstructorWithArgs	(_object, _layout, skinClass, skinConstructor, (Object[]) null);
			return getViewFromClassConstructorWithArgs		(_object, _layout, skinClass, skinConstructor, skinParameters);
		}

		if(skinSupplier2 != null)
			return getViewFromFunction(_object, _layout, skinSupplier2);
		
		throw new NotYetImplementedException();
	}

	private GTSkin<GO, GTO, GTL>	getViewFromClassConstructor			(GTO _object, GTL _layout, Class<? extends GTSkin<GO, GTO, GTL>> _skinClass) {
        try {
            Constructor<? extends GTSkin<GO, GTO, GTL>> constructor = _skinClass.getConstructor(getGTClass(_object), layoutClass);
            GTSkin<GO, GTO, GTL>              			skin        = (GTSkin<GO, GTO, GTL>) constructor.newInstance(_object, _layout);
            return skin;

        } catch(NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException _e) {
        	_e.printStackTrace();
            return null;
        }
	}
	private GTSkin<GO, GTO, GTL> 	getViewFromClassConstructorWithArgs	(GTO _object, GTL _layout, Class<? extends GTSkin<GO, GTO, GTL>> _skinClass, Constructor<? extends GTSkin<GO, GTO, GTL>> _skinConstructor, Object[] _skinParameters) {
        try {
            switch(_skinConstructor.getParameterCount()) {
            case 2  : return _skinConstructor.newInstance(_object, _layout);
            case 3  : if(_skinParameters.length > 0)
            			return _skinConstructor.newInstance(_object, _layout, _skinParameters[0]);
            case 4  : if(_skinParameters.length > 1)
    					return  _skinConstructor.newInstance(_object, _layout, _skinParameters[0], _skinParameters[1]);
            case 5  : if(_skinParameters.length > 2)
    					return _skinConstructor.newInstance(_object, _layout, _skinParameters[0], _skinParameters[1], _skinParameters[2]);
            case 6  : if(_skinParameters.length > 3)
    					return _skinConstructor.newInstance(_object, _layout, _skinParameters[0], _skinParameters[1], _skinParameters[2], _skinParameters[3]);
            case 7  : if(_skinParameters.length > 4)
    					return _skinConstructor.newInstance(_object, _layout, _skinParameters[0], _skinParameters[1], _skinParameters[2], _skinParameters[3], _skinParameters[4]);
            case 8  : if(_skinParameters.length > 5)
    					return _skinConstructor.newInstance(_object, _layout, _skinParameters[0], _skinParameters[1], _skinParameters[2], _skinParameters[3], _skinParameters[4], _skinParameters[5]);
            case 0  :
            default : throw new IllegalArgumentException("Must be in 2 and 8");
            }

        } catch(SecurityException 
	                | InstantiationException
	                | IllegalAccessException
	                | IllegalArgumentException 
	                | InvocationTargetException _e) {

        	_e.printStackTrace();
            return null;
        }
	}
	private GTSkin<GO, GTO, GTL> 	getViewFromClassConstructorWithArgs	(GTO _object, GTL _layout, Class<? extends GTSkin<GO, GTO, GTL>> _skinClass, Object[] _skinParameters) {
		try {
			Class<?>[] _paramClasses = Parameters.asClasses(_skinParameters);

			Class<?>[] _ctorClasses  = new Class<?>	[_paramClasses.length + 2];
			Object[]   _ctorParams   = new Object	[_paramClasses.length + 2];
			_ctorClasses[0] = getGTClass(_object);			_ctorParams[0]   = _object;
			_ctorClasses[1] = layoutClass;					_ctorParams[1]   = _layout;
			for(int k = 0; k < _paramClasses.length; ++k) {
				_ctorClasses[k+2] = _paramClasses[k];		_ctorParams[k+2] = _skinParameters[k];
			}

            Constructor<? extends GTSkin<GO, GTO, GTL>> constructor = _skinClass.getConstructor(_ctorClasses);
            return constructor.newInstance(_ctorParams);
  			
		} catch(NoSuchMethodException 
                | SecurityException 
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException 
				| InvocationTargetException _e) {
			_e.printStackTrace();
			return null;
		}
	}
	private GTSkin<GO, GTO, GTL> 	getViewFromFunction					(GTO _object, GTL _layout, BiFunction<GTO, GTL, GTSkin<GO, GTO, GTL>> _skinSupplier) {
        return _skinSupplier.apply(_object, _layout);
	}

	private Class<?> 				getGTClass(GTO _object) {
		if(_object instanceof GTNode)
			return GTNode.class;
		if(_object instanceof GTEdge)
			return GTEdge.class;
		if(_object instanceof GTGate)
			return GTGate.class;
		throw new RuntimeException();
	}
}