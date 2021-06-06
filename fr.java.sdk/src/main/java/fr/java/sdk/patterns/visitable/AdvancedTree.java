package fr.java.sdk.patterns.visitable;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.java.patterns.visitable.Treeable;

public class AdvancedTree<K extends Comparable<K>, V> implements Treeable<K, AdvancedTree<K, V>>, Comparable<AdvancedTree<K, V>>, Cloneable {
	private final Set<AdvancedTree<K, V>>	children = new LinkedHashSet<AdvancedTree<K, V>>(); //new TreeSet<AdvancedTree<K, V>>();
	private final K							key;

	private 	  AdvancedTree<K, V>		parent;
	private 	  V 						value;

	public  AdvancedTree(K _data) {
		super();
		parent = null;
		key   = _data;
	}
	public  AdvancedTree(K _key, V _value) {
		super();
		parent = null;
		key    = _key;
		value  = _value;
	}
	public AdvancedTree(K _key, V _value, AdvancedTree<K, V> _parent) {
		super();
		parent = _parent;
		key    = _key;
		value  = _value;
	}
	private AdvancedTree(K _data, AdvancedTree<K, V> _parent) {
		super();
		parent = _parent;
		key    = _data;
	}

	@Override
	public String							getName() { return "NoName"; }

	@Override
	public AdvancedTree<K, V> 				getParent() {
		return parent;
	}

	@Override
	public Set<AdvancedTree<K, V>> 			getChildren() {
		return children;
	}
	@Override
	public AdvancedTree<K, V> 				getChild(K _key, boolean _createIfMissing) {
		for(AdvancedTree<K, V> child : children)
			if(child.getKey().equals(_key))
				return child;

		return _createIfMissing ? addChild(new AdvancedTree<K, V>(_key, this)) : null;
	}

	@Override
	public AdvancedTree<K, V> 				addChild(AdvancedTree<K, V> _child) {
		for(AdvancedTree<K, V> child : children)
			if(child.getKey().equals(_child.getKey())) {
				children.remove(child);
//				throw new AlreadyExistingEntry();
			}

		children.add(_child);
		_child.parent = this;
		return _child;
	}
	@Override
	public AdvancedTree<K, V> 				removeChild(AdvancedTree<K, V> child) {
		if(children.remove(child)) {
			child.parent = null;
			return child;
		}
		return null;
	}

	@Override
	public K								getData() {
//		System.err.println("Use getKey() or getValue() to access 'data' in an AdvancedTree, for now returned 'getKey()'");
		return getKey();
//		throw new IllegalAccessError("Use key() or value() to access 'data' in a TreeValuable");
	}
	public K								getKey() {
		return key;
	}
	public V								getValue() {
		return value;
	}

	@Override
	public Stream<K> 						streamData() {
		throw new IllegalAccessError("Use key() or value() to access 'data' in a TreeValuable");
	}
	public Stream<K> 						streamKey() {
		if(getChildren().isEmpty()) {
			return Stream.of(getKey());
		} else {
			return getChildren().stream()
					.map(child -> child.streamKey())
					.reduce(Stream.of(getKey()), (s1, s2) -> Stream.concat(s1, s2));
		}
	}
	public Stream<V> 						streamValue() {
		if(getChildren().isEmpty()) {
			return Stream.of(getValue());
		} else {
			return getChildren().stream()
					.map(child -> child.streamValue())
					.reduce(Stream.of(getValue()), (s1, s2) -> Stream.concat(s1, s2));
		}
	}

	public void								sort(Comparator<? super K> _comparator) {
		
	}

	@Override
	public boolean 							add(AdvancedTree<K, V> e) {
		throw new IllegalAccessError("Tree requieres to use attach()");
	}
	@Override
	public boolean 							remove(Object o) {
		throw new IllegalAccessError("Tree requieres to use detach() on the concerned branch");
	}

	public AdvancedTree<K, V> 				attach(AdvancedTree<K, V> _branch) {
		if(_branch.parent != null)
			throw new IllegalAccessError("The branch is already attach to another tree");
		if(getChildren().stream().map(AdvancedTree<K, V>::getKey).anyMatch(key -> key.equals(_branch.key)))
			throw new IllegalAccessError("A similar branch already exist");

		children.add(_branch);
		_branch.parent = this;

		return this;
	}
	public AdvancedTree<K, V> 				attach(AdvancedTree<K, V> _branch, boolean _mergeIfExist) {
		if(_branch.parent != null)
			throw new IllegalAccessError("The branch is already attach to another tree");
		if(getChildren().stream().map(AdvancedTree<K, V>::getKey).anyMatch(key -> key.equals(_branch.key))) {
			AdvancedTree<K, V> existingBranch = getChild(_branch.key);
		    for(AdvancedTree<K, V> subBranch : _branch.getChildren())
		    	existingBranch.attach(subBranch.detach(), _mergeIfExist);
		} else {
			children.add(_branch);
			_branch.parent = this;
		}

		return this;
	}
	public AdvancedTree<K, V> 				detach() {
		if(parent != null) {
			parent.children.remove(this);
			parent = null;
		}

		return this;
	}

	@Override
	public int 								compareTo(AdvancedTree<K, V> o) {
		return key.compareTo(o.key);
	}

	@Override
	public AdvancedTree<K, V> 				clone() {
		AdvancedTree<K, V> clone = new AdvancedTree<K, V>(key);
		clone.parent = parent;
		clone.value  = value;
		for(AdvancedTree<K, V> child : children)
			clone.getChildren().add(child.clone());
		
		return clone;
	}

	@Override
	public Collection<AdvancedTree<K, V>> 	find(K _key) {
	    return stream()
//	            .peek(node -> System.out.println(node.data() + ": " + (node.parent() != null ? "*" : "")))
	            .filter(child -> child.getKey().equals(_key))
	            .collect(Collectors.toList()) ;
	}
	@Override
	public Collection<AdvancedTree<K, V>> 	findBranch(Predicate<AdvancedTree<K, V>> _predicate) {
	    return stream()
//	            .peek(node -> System.out.println(node.data() + ": " + (node.parent() != null ? "*" : "")))
	            .filter(_predicate)
	            .collect(Collectors.toList()) ;
	}
	@Override
	public Collection<AdvancedTree<K, V>> 	findLeaf(Predicate<K> _predicate) {
	    return stream()
//	            .peek(node -> System.out.println(node.data() + ": " + (node.parent() != null ? "*" : "")))
	            .filter(child -> _predicate.test(child.getData()))
	            .collect(Collectors.toList()) ;
	}

	@Override
	public boolean 							isEmpty() {
		return getChildren().size() == 0 && getValue() == null;
	}

	@Override
	public int 								size() {
		return getChildren().size();
	}

	@Override
	public boolean 							contains(Object o) {
		if(getData().equals(o))
			return true;
		if(getChildren().contains(o))
			return true;

		for(Treeable<K, AdvancedTree<K, V>> child : getChildren())
			if(child.contains(o))
				return true;

		return false;
	}
	@Override
	public boolean 							containsAll(Collection<?> c) {
		for(Object o : c)
			if(!contains(o))
				return false;
		return true;
	}
	

}