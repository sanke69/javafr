/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.beans.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.java.beans.impl.events.ListPropertyChangeEvent;
import fr.java.beans.properties.BeanProperty;

public class BeanPropertyListView<T> extends BeanPropertyList<T> implements BeanProperty<List<T>>, List<T> {

	public class InnerSubList  implements List<T> {

		private final List<T> innerDelegate;
		private final int     from;

		public InnerSubList(int fromIndex, int toIndex) {
			super();
			innerDelegate = delegate.subList(fromIndex, toIndex).stream().map(mapper).collect(Collectors.toList());
			from          = fromIndex;
		}

		@Override
		public int 				size() {
			return innerDelegate.size();
		}

		@Override
		public T 				set(int index, T element) {
			throw new UnsupportedOperationException();
		}
		@Override
		public T 				get(int index) {
			return mapper.apply( innerDelegate.get(index) );
		}

		@Override
		public boolean 			isEmpty() {
			return innerDelegate.isEmpty();
		}

		@Override
		public boolean 			contains(Object o) {
			return innerDelegate.contains(o);
		}
		@Override
		public boolean 			containsAll(Collection<?> c) {
			return innerDelegate.containsAll(c);
		}

		@Override
		public int 				indexOf(Object o) {
			return innerDelegate.indexOf(o);
		}
		@Override
		public int 				lastIndexOf(Object o) {
			return innerDelegate.lastIndexOf(o);
		}

		@Override
		public Object[] 		toArray() {
			return innerDelegate.toArray();
		}
		@Override
		public <U> U[] 			toArray(U[] a) {
			return innerDelegate.toArray(a);
		}

		@Override
		public boolean 			add(T e) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void 			add(int index, T element) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean 			addAll(Collection<? extends T> c) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean 			addAll(int index, Collection<? extends T> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public T 				remove(int index) {
			T oldValue = innerDelegate.remove(index);
			if(oldValue != null) {
				List<T> view = innerDelegate.stream().map(mapper).collect(Collectors.toList());
				BeanPropertyListView.this.fireListPropertyChange(singleSuppressionChange(view, oldValue));
			}
			return oldValue;
		}
		@Override
		public boolean 			remove(Object o) {
			boolean removed = innerDelegate.remove(o);
			if(removed) {
				List<T> view = innerDelegate.stream().map(mapper).collect(Collectors.toList());
				fireListPropertyChange(singleSuppressionChange(view, (T) o));
			}
			return removed;
		}
		@Override
		public boolean 			removeAll(Collection<?> c) {
			boolean removed = innerDelegate.retainAll(c);
			if(removed) {
				List<T> view        = innerDelegate.stream().map(mapper).collect(Collectors.toList());
				List<T> removedList = innerDelegate.stream().filter(v -> c.contains(v)).collect(Collectors.toList());
				fireListPropertyChange(multipleSuppressionChange(view, removedList));
			}
			return removed;
		}

		@Override
		public boolean 			retainAll(Collection<?> c) {
			boolean removed = innerDelegate.retainAll(c);
			if(removed) {
				List<T> view        = innerDelegate.stream().map(mapper).collect(Collectors.toList());
				List<T> removedList = innerDelegate.stream().filter(v -> !c.contains(v)).collect(Collectors.toList());
				fireListPropertyChange(multipleSuppressionChange(view, removedList));
			}
			return removed;
		}

		@Override
		public void 			clear() {
			List<T> removedList = new ArrayList<T>(innerDelegate);
			innerDelegate.clear();
			fireListPropertyChange(multipleSuppressionChange(new ArrayList<T>(), removedList));
		}

		@Override
		public Iterator<T> 		iterator() {
			return new InnerIterator(from);
		}

		@Override
		public ListIterator<T> 	listIterator() {
			return new InnerIterator(from);
		}

		@Override
		public ListIterator<T> 	listIterator(int index) {
			return new InnerIterator(from + index);
		}

		@Override
		public List<T> 			subList(int fromIndex, int toIndex) {
			return new InnerSubList(from + fromIndex, from + toIndex);
		}
		
	}

	public class InnerIterator implements ListIterator<T> {

		private final ListIterator<?> innerDelegate;
		private T 			  		  lastResult;

		public InnerIterator() {
			super();
			innerDelegate = delegate.listIterator();
			lastResult    = null;
		}
		public InnerIterator(int index) {
			super();
			innerDelegate = delegate.listIterator(index);
			lastResult    = mapper.apply( delegate.listIterator(index).next() );
		}

		@Override
		public boolean hasNext() {
			return innerDelegate.hasNext();
		}

		@Override
		public T next() {
			return lastResult = mapper.apply( innerDelegate.next() );
		}

		@Override
		public boolean hasPrevious() {
			return innerDelegate.hasPrevious();
		}

		@Override
		public T previous() {
			return lastResult =  mapper.apply( innerDelegate.previous() );
		}

		@Override
		public int nextIndex() {
			return innerDelegate.nextIndex();
		}

		@Override
		public int previousIndex() {
			return innerDelegate.previousIndex();
		}

		@Override
		public void remove() {
			innerDelegate.remove();
			List<T> view = delegate.stream().map(mapper).collect(Collectors.toList());
			fireListPropertyChange(singleSuppressionChange(view, lastResult));
		}

		@Override
		public void set(T e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(T e) {
			throw new UnsupportedOperationException();
		}

	}

	BeanPropertyList<?> 		delegate;
	final Function<Object, T> 	mapper;

	public <U> BeanPropertyListView(BeanPropertyList<U> _setProperty, Function<U, T> _mapper) {
		this(_setProperty.getBean(), _setProperty.getName() + "-view", _setProperty, _mapper);
	}
	public <U> BeanPropertyListView(String _propertyName, BeanPropertyList<U> _setProperty, Function<U, T> _mapper) {
		this(_setProperty.getBean(), _propertyName, _setProperty, _mapper);
	}
	public <U> BeanPropertyListView(Object _bean, String _propertyName, BeanPropertyList<U> _listProperty, Function<U, T> _mapper) {
		super(_bean, _propertyName, null);
		delegate = _listProperty;
		mapper   = (Function<Object, T>) _mapper;

		_listProperty.addListener((prop, _changes) -> {
			List<T> view = _listProperty.stream().map(_mapper).collect(Collectors.toList());

			while(_changes.next()) {
				if(_changes.wasAdded()) {
					fireListPropertyChange(BeanPropertyListView.this.multipleAdditionChange(view, _changes.getFrom(), _changes.getAddedSubList().size()));

				} else if(_changes.wasRemoved()) {
					List<T> removed = _changes.getRemoved().stream().map(mapper).collect(Collectors.toList());

					fireListPropertyChange(BeanPropertyListView.this.multipleSuppressionChange(view, removed));

				} else if(_changes.wasPermutated()) {
					System.out.println("PERMUTE");
				} else if(_changes.wasReplaced()) {
					System.out.println("REPLACED");
				} else if(_changes.wasUpdated()) {
					System.out.println("UPDATE");
				}
			}
			
		});

	}

	@Override
	public void 				set(List<T> _value) {
		throw new UnsupportedOperationException();
	}
	@Override
	public T 					set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	public int 					size() { 
    	return delegate.size();
	}
    public boolean 				isEmpty() { 
    	return delegate.isEmpty();
	}
    public boolean 				contains(Object o) { 
    	return delegate.contains(o);
	}
    public Object[] 			toArray() { 
    	return delegate.toArray();
	}
    public <U> U[] 				toArray(U[] a) { 
    	return delegate.toArray(a);
	}
    public boolean 				containsAll(Collection<?> c) { 
    	return delegate.containsAll(c);
	}

    @Override
	public Iterator<T> 			iterator() {
    	return new InnerIterator();
	}

	@Override
	public boolean 				add(T element) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void 				add(int index, T element) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				addAll(int index, Collection<? extends T> collection) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T 					remove(int index) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				remove(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void 				clear() {
		throw new UnsupportedOperationException();
	}

	private ListPropertyChangeEvent.Change<T> singleAdditionChange			(List<T> _view) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 1;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

			@Override
			public int getFrom() {
				return iChange == 1 ? delegate.size() - 1 : 0;
			}

			@Override
			public int getTo() {
				return iChange == 1 ? delegate.size() : 0;
			}

			@Override
			public List<T> getRemoved() {
				return new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}

		};
	}
	private ListPropertyChangeEvent.Change<T> singleAdditionChange			(List<T> _view, int _index) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 2;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

			@Override
			public int getFrom() {
				return iChange == 1 ? _index : 0;
			}

			@Override
			public int getTo() {
				return iChange == 1 ? _index + 1 : 0;
			}

			@Override
			public List<T> getRemoved() {
				return new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}

		};
	}
	private ListPropertyChangeEvent.Change<T> multipleAdditionChange		(List<T> _view, int _count) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 1;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

			@Override
			public int getFrom() {
				return iChange == 1 ? delegate.size() - _count : 0;
			}

			@Override
			public int getTo() {
				return iChange == 1 ? delegate.size() : 0;
			}

			@Override
			public List<T> getRemoved() {
				return new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}
			
		};
	}
	private ListPropertyChangeEvent.Change<T> multipleAdditionChange		(List<T> _view, int _index, int _count) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 1;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

			@Override
			public int getFrom() {
				return iChange == 1 ? _index : 0;
			}

			@Override
			public int getTo() {
				return iChange == 1 ? _count - _index : 0;
			}

			@Override
			public List<T> getRemoved() {
				return new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}
			
		};
	}
	private ListPropertyChangeEvent.Change<T> singleSuppressionChange		(List<T> _view, T _oldValue) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 1;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

			@Override
			public int getFrom() {
				return 0;
			}

			@Override
			public int getTo() {
				return 0;
			}

			@Override
			public List<T> getRemoved() {
				return iChange == 1 ? Arrays.asList(_oldValue) : new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}

		};
	}
	private ListPropertyChangeEvent.Change<T> multipleSuppressionChange		(List<T> _view, List<T> _removed) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 1;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

			@Override
			public int getFrom() {
				return 0;
			}

			@Override
			public int getTo() {
				return 0;
			}

			@Override
			public List<T> getRemoved() {
				return iChange == 1 ? _removed : new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}

		};
	}
	private ListPropertyChangeEvent.Change<T> singleUpdateChange			(List<T> _view, int _index, T _oldValue) {
		return new ListPropertyChangeEvent.Change<T>(_view) {
			int nbChanges = 1;
			int iChange   = 0;

			@Override
			public boolean next() {
				return iChange++ <= nbChanges;
			}

			@Override
			public void reset() {
				iChange = 0;
			}

	        public boolean wasUpdated() {
	            return iChange == 1 ? true : false;
	        }

			@Override
			public int getFrom() {
				return iChange == 1 ? delegate.size() - 1 : 0;
			}

			@Override
			public int getTo() {
				return iChange == 1 ? delegate.size() : 0;
			}

			@Override
			public List<T> getRemoved() {
				return new ArrayList<T>();
			}

			@Override
			protected int[] getPermutation() {
				return new int[] {};
			}

		};
	}

}
