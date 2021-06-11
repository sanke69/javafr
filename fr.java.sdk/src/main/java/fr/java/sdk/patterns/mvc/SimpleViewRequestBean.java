package fr.java.sdk.patterns.mvc;

import java.util.Collection;
import java.util.Optional;

import fr.java.beans.properties.BeanPropertyStorage;
import fr.java.beans.properties.listeners.BeanPropertyMapChangeListener;
import fr.java.mvc.ViewRequest;
import fr.java.patterns.ViewRequestBean;
import fr.java.utils.PropertyStorages;

public class SimpleViewRequestBean implements ViewRequestBean {

	final BeanPropertyStorage storage = PropertyStorages.newStorage();

	@Override
    public void setStyle(String style) {
        if (style == null)
            style = "";

        set(ViewRequest.KEY_STYLE, style);
    }

    @Override
    public String getStyle() {
        return get(ViewRequest.KEY_STYLE).get().toString();
    }

    @Override
    public Collection<String> keys() {
		return storage.keys();
	}

    @Override
	public <T> void set(String key, T property) {
		storage.set(key, property);
	}

    @Override
	public <T> Optional<T> get(String key) {
		return storage.get(key);
	}

    @Override
	public boolean contains(String key) {
		return storage.contains(key);
	}

    @Override
	public void remove(String key) {
		storage.remove(key);
	}

	@Override
	public void addListener(BeanPropertyMapChangeListener<String, Object> vReqLister) {
		storage.addListener(vReqLister);
	}

	@Override
	public void removeListener(BeanPropertyMapChangeListener<String, Object> vReqLister) {
		storage.removeListener(vReqLister);
	}

}
