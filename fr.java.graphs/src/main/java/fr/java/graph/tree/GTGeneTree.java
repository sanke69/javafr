package fr.java.graph.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.java.graph.GTGraph;

public interface GTGeneTree<T, BRANCH extends GTGeneTree<T, BRANCH>> extends GTGraph, Collection<BRANCH> {

//	public default String getName() { return "must be implemented..."; }

	public BRANCH								getParent();
	public Set<? extends BRANCH> 				getChildren();

	public default boolean 						isRoot()   { return getParent() == null; }
	public default boolean 						isBranch() { return !getChildren().isEmpty(); }
	public default boolean 						isLeaf()   { return !isBranch(); }

	public T									getData();
	public default int							getDepth() { int depth = 0; BRANCH p = getParent(); while(p != null) { depth++; p = p.getParent(); } return depth; }

	public default BRANCH 						getChild(T data) { return getChild(data, false); }
	public BRANCH 								getChild(T data, boolean _createIfMissing);

	public default BRANCH 						addChild(BRANCH child) { throw new UnsupportedOperationException(); }
	public default BRANCH 						removeChild(BRANCH child) { throw new UnsupportedOperationException(); }
	public default boolean 						add(BRANCH child) { return addChild(child) != null; }
	@SuppressWarnings("unchecked")
	public default boolean 						remove(Object child) { return removeChild((BRANCH) child) != null; }

	public default Collection<? extends BRANCH> 	find(T _data) {
	    return stream()
//	            .peek(node -> System.out.println(node.data() + ": " + (node.parent() != null ? "*" : "")))
	            .filter(child -> child.getData().equals(_data))
	            .collect(Collectors.toList()) ;
	}
	public default Collection<? extends BRANCH> 	findBranch(Predicate<BRANCH> _predicate) {
	    return stream()
//	            .peek(node -> System.out.println(node.data() + ": " + (node.parent() != null ? "*" : "")))
	            .filter(_predicate)
	            .collect(Collectors.toList()) ;
	}
	public default Collection<? extends BRANCH> 	findLeaf(Predicate<T> _predicate) {
	    return stream()
//	            .peek(node -> System.out.println(node.data() + ": " + (node.parent() != null ? "*" : "")))
	            .filter(child -> _predicate.test(child.getData()))
	            .collect(Collectors.toList()) ;
	}

	public default Iterator<? extends BRANCH> 	treeIterator() {
		return getChildren().iterator();
	}

	public default Stream<T> 					streamData() {
		if(getChildren().isEmpty()) {
			return Stream.of(getData());
		} else {
			return getChildren().stream()
					.map(child -> child.streamData())
					.reduce(Stream.of(getData()), (s1, s2) -> Stream.concat(s1, s2));
		}
	}

	/**
	 * Collection API Implementation
	 */

	@Override
	public default boolean 						isEmpty() {
		return size() == 0 && getData() == null;
	}

	@Override
	public default int 							size() {
		return getChildren().size();
	}

	@Override
	public default boolean 						contains(Object o) {
		if(getData().equals(o))
			return true;
		if(getChildren().contains(o))
			return true;

		for(GTGeneTree<T, BRANCH> child : getChildren())
			if(child.contains(o))
				return true;

		return false;
	}
	@Override
	public default boolean 						containsAll(Collection<?> c) {
		for(Object o : c)
			if(!contains(o))
				return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	public default Stream<BRANCH> 				stream() {
		if(getChildren().isEmpty()) {
			return Stream.of((BRANCH) this);
		} else {
			return getChildren().stream()
					.map(child -> child.stream())
					.reduce(Stream.of((BRANCH) this), (s1, s2) -> Stream.concat(s1, s2));
		}
	}

	@Override
	public default boolean 						addAll(Collection<? extends BRANCH> c) {
		return addAll(c);
	}
	@Override
	public default boolean 						removeAll(Collection<?> c) {
		return getChildren().removeAll(c);
	}
	@Override
	public default boolean 						retainAll(Collection<?> c) {
		return getChildren().retainAll(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public default Iterator<BRANCH> 			iterator() {
		return (Iterator<BRANCH>) treeIterator();
	}

	@Override
	public default Object[] 					toArray() {
		return getChildren().toArray();
	}
	@Override
	public default <U> U[] 						toArray(U[] a) {
		return getChildren().toArray(a);
	}

	@Override
	public default void 						clear() {
		getChildren().clear();
	}

}
