package fr.java.sdk.patterns.visitable.specials;

import fr.java.sdk.patterns.visitable.AdvancedTree;

public class StringTree<T> extends AdvancedTree<String, T> {
	public static final String DELIMITER = "/";

	public StringTree() {
		super("root");
	}
	public StringTree(String _key) {
		super(_key);
	}
	public StringTree(String _key, T _data) {
		super(_key, _data);
	}

	public void 			add(String _path, T _data) {
		if(_path.contains(DELIMITER)) {
			String nodePath = _path.substring(0, _path.lastIndexOf(DELIMITER));
			String nodeKey  = _path.substring(_path.lastIndexOf(DELIMITER) + 1);

			AdvancedTree<String, T> parent = find(nodePath);
			
			if(parent != null) {
				parent.addChild(new StringTree(nodeKey, _data));
				return ;
			} else {
				parent = this;

		        for(String data : nodePath.split(DELIMITER)) {
		        	AdvancedTree<String, T> child = parent.getChild(data);
		        	
		        	if(child == null)
		        		parent.addChild(child = new StringTree(data, null));
		        	
		        	parent = child;
		        }
		        find(nodePath).addChild(new StringTree(nodeKey, _data));
			}
		} else {
			addChild(new StringTree(_path, _data));
		}
	}
	public void 			add(String _parent, String _path, T _data) {
		add(_parent + DELIMITER + _path, _data);
	}

	public StringTree<T> 	find(String _path) {
		AdvancedTree<String, T> current = this;

        for(String key : _path.split(DELIMITER)) {
            current = current.getChild(key);
            if(current == null)
            	return null;
        }

        return (StringTree<T>) current;
	}

	public T getValue(String _path) {
		StringTree<T> child = find(_path); 
		return child != null ? child.getValue() : null;
	}

}
