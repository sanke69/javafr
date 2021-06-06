package fr.java.sdk.patterns.visitable;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import fr.java.patterns.visitable.Treeable;

public class SimpleTree<T extends Comparable<T>> implements Treeable<T, SimpleTree<T>>, Comparable<SimpleTree<T>>, Cloneable {
	private 	  SimpleTree<T>			parent;
	private final Set<SimpleTree<T>>	children = new TreeSet<SimpleTree<T>>();
	private /*final*/ T					data;

	public  SimpleTree() {
		super();
		parent = null;
		data   = null;
	}
	public  SimpleTree(T _data) {
		super();
		parent = null;
		data   = _data;
	}
	private SimpleTree(T _data, SimpleTree<T> _parent) {
		super();
		parent = _parent;
		data   = _data;
	}

	@Override
	public String				getName() { return "NoName"; }
	
	@Override
	public SimpleTree<T> 		getParent() {
		return parent;
	}

	@Override
	public Set<SimpleTree<T>> 	getChildren() {
		return children;
	}
	@Override
	public SimpleTree<T> 		getChild(T data, boolean _createIfMissing) {
		for(SimpleTree<T> child : children)
			if(child.getData().equals(data))
				return child;

		return _createIfMissing ? addChild(new SimpleTree<T>(data, this)) : null;
	}

	@Override
	public SimpleTree<T>		addChild(SimpleTree<T> child) {
		children.add(child);
		child.parent = this;
		return child;
	}
	@Override
	public SimpleTree<T> 		removeChild(SimpleTree<T> child) {
		if(children.remove(child)) {
			child.parent = null;
			return child;
		}
		return null;
	}

	@Override
	public T					getData() {
		return data;
	}

	public void					sort(Comparator<? super T> _comparator) {
		
	}

	@Override
	public boolean 				add(SimpleTree<T> e) {
		throw new IllegalAccessError("Tree requieres to use attach()");
	}
	@Override
	public boolean 				remove(Object o) {
		throw new IllegalAccessError("Tree requieres to use detach() on the concerned branch");
	}

	public SimpleTree<T> 		attach(SimpleTree<T> _branch) {
		if(_branch.parent != null)
			throw new IllegalAccessError("The branch is already attach to another tree");
		if(getChildren().stream().map(SimpleTree<T>::getData).anyMatch(data -> data.equals(_branch.data)))
			throw new IllegalAccessError("A similar branch already exist");

		children.add(_branch);
		_branch.parent = this;

		return this;
	}
	public SimpleTree<T> 		attach(SimpleTree<T> _branch, boolean _mergeIfExist) {
		if(_branch.parent != null)
			throw new IllegalAccessError("The branch is already attach to another tree");
		if(getChildren().stream().map(SimpleTree<T>::getData).anyMatch(data -> data.equals(_branch.data))) {
			SimpleTree<T> existingBranch = getChild(_branch.data);
		    for(SimpleTree<T> subBranch : _branch.getChildren())
		    	existingBranch.attach(subBranch.detach(), _mergeIfExist);
		} else {
			children.add(_branch);
			_branch.parent = this;
		}

		return this;
	}
	public SimpleTree<T> 		detach() {
		if(parent != null) {
			parent.children.remove(this);
			parent = null;
		}

		return this;
	}

	@Override
	public int 					compareTo(SimpleTree<T> o) {
		return data.compareTo(o.data);
	}

	@Override
	public SimpleTree<T> 		clone() {
		SimpleTree<T> clone = new SimpleTree<T>(data);

		clone.parent = parent;
		for(SimpleTree<T> child : children)
			clone.getChildren().add(child.clone());
		
		return clone;
	}

	@Override
	public String toString() {
		if(getParent() != null)
			return getParent().toString() + " / " + data.toString();
		return data.toString();
	}

}
