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
 * @file     SimpleListBeanProperty.java
 * @version  0.0.0.1
 * @date     2018/04/27
 * 
**/
package fr.java.beans.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import fr.java.beans.impl.events.ListPropertyChangeEvent;

public class SimpleBeanPropertyList<T> extends BeanPropertyList<T> {

	public class InnerSubList implements List<T> {

		private final List<T> innerDelegate;
		private final int     from, to;

		public InnerSubList(int fromIndex, int toIndex) {
			super();
			innerDelegate = delegate.subList(fromIndex, toIndex);
			from = fromIndex;
			to   = toIndex;
		}

		@Override
		public int size() {
			return innerDelegate.size();
		}

		@Override
		public T set(int index, T element) {
	        T oldValue = innerDelegate.set(index, element);
			fireListPropertyChange(singleUpdateChange(from + index, oldValue));
	        return oldValue;
		}
		@Override
		public T get(int index) {
			return innerDelegate.get(index);
		}

		@Override
		public boolean isEmpty() {
			return innerDelegate.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return innerDelegate.contains(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return innerDelegate.containsAll(c);
		}

		@Override
		public int indexOf(Object o) {
			return innerDelegate.indexOf(o);
		}

		@Override
		public int lastIndexOf(Object o) {
			return innerDelegate.lastIndexOf(o);
		}

		@Override
		public Object[] toArray() {
			return innerDelegate.toArray();
		}

		@Override
		public <U> U[] toArray(U[] a) {
			return innerDelegate.toArray(a);
		}

		@Override
		public boolean add(T e) {
			boolean added = innerDelegate.add(e);
			if(added)
				fireListPropertyChange(singleAdditionChange(to));
			return added;
		}

		@Override
		public void add(int index, T element) {
			innerDelegate.add(index, element);
			fireListPropertyChange(singleAdditionChange(from + index));
		}

		@Override
		public boolean addAll(Collection<? extends T> c) {
			boolean added = innerDelegate.addAll(c);
			if(added)
				fireListPropertyChange(multipleAdditionChange(to, c.size()));
			return added;
		}

		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			boolean added = innerDelegate.addAll(index, c);
			if(added)
				fireListPropertyChange(multipleAdditionChange(from + index, c.size()));
			return added;
		}

		@Override
		public T remove(int index) {
			T oldValue = innerDelegate.remove(index);
			fireListPropertyChange(singleSuppressionChange(oldValue));
			return oldValue;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean remove(Object o) {
			boolean removed = innerDelegate.remove(o);
			if(removed)
				fireListPropertyChange(singleSuppressionChange((T) o));
			return removed;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			List<T> removedList = innerDelegate.stream().filter(v -> c.contains(v)).collect(Collectors.toList());
			boolean removed = innerDelegate.retainAll(c);
			if(removed)
				fireListPropertyChange(multipleSuppressionChange(removedList));
			return removed;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			List<T> removedList = innerDelegate.stream().filter(v -> !c.contains(v)).collect(Collectors.toList());
			boolean removed = innerDelegate.retainAll(c);
			if(removed)
				fireListPropertyChange(multipleSuppressionChange(removedList));
			return removed;
		}

		@Override
		public void clear() {
			List<T> removedList = new ArrayList<T>(innerDelegate);
			innerDelegate.clear();
			fireListPropertyChange(multipleSuppressionChange(removedList));
		}

		@Override
		public Iterator<T> iterator() {
			return new InnerIterator(from);
		}

		@Override
		public ListIterator<T> listIterator() {
			return new InnerIterator(from);
		}

		@Override
		public ListIterator<T> listIterator(int index) {
			return new InnerIterator(from + index);
		}

		@Override
		public List<T> subList(int fromIndex, int toIndex) {
			return new InnerSubList(from + fromIndex, from + toIndex);
		}
		
	}

	public class InnerIterator implements ListIterator<T> {

		private final ListIterator<T> innerDelegate;
		private T 			  		  lastResult;

		public InnerIterator() {
			super();
			innerDelegate = delegate.listIterator();
			lastResult    = null;
		}
		public InnerIterator(int index) {
			super();
			innerDelegate = delegate.listIterator(index);
			lastResult    = delegate.listIterator(index).next();
		}

		@Override
		public boolean hasNext() {
			return innerDelegate.hasNext();
		}

		@Override
		public T next() {
			return lastResult = innerDelegate.next();
		}

		@Override
		public boolean hasPrevious() {
			return innerDelegate.hasPrevious();
		}

		@Override
		public T previous() {
			return lastResult = innerDelegate.previous();
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
			fireListPropertyChange(singleSuppressionChange(lastResult));
		}

		@Override
		public void set(T e) {
			innerDelegate.set(e);
			fireListPropertyChange(singleUpdateChange(innerDelegate.previousIndex(), lastResult));
		}

		@Override
		public void add(T e) {
			innerDelegate.add(e);
			fireListPropertyChange(singleAdditionChange(innerDelegate.previousIndex()));
		}

	}

	public SimpleBeanPropertyList() {
		super();
	}
	public SimpleBeanPropertyList(String _propertyName) {
		super(_propertyName);
	}
	public SimpleBeanPropertyList(List<T> _list) {
		super(_list);
	}
	public SimpleBeanPropertyList(String _propertyName, List<T> _list) {
		super(_propertyName, _list);
	}
	public SimpleBeanPropertyList(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public SimpleBeanPropertyList(Object _bean, String _propertyName, List<T> _list) {
		super(_bean, _propertyName, _list);
	}

/*
	@Override
	public void 				addListener(BeanPropertyListChangeListener<V> listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), listener);
	}
	@Override
	public void 				removeListener(BeanPropertyListChangeListener<V> listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), listener);
	}

	protected final void 		fireListPropertyChange(ListPropertyChangeEvent.Change<V> _changes) {
		propertyChangeSupport().firePropertyChange( new ListPropertyChangeEvent<V>(getBean() != null ? getBean() : this, getName(), _changes) );
	}
*/

	public void 				set(List<T> _value) {
		throw new IllegalAccessError();
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

	public boolean 				containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public int 					indexOf(Object o) {
		return delegate.indexOf(o);
	}

	public int 					lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	public Object[] 			toArray() {
		return delegate.toArray();
	}

	public <U> U[] 				toArray(U[] a) {
		return delegate.toArray(a);
	}

	public T 					get(int index) {
		return delegate.get(index);
	}

	public T 					set(int index, T element) {
        T oldValue = delegate.set(index, element);
		fireListPropertyChange(singleUpdateChange(index, oldValue));
        return oldValue;
	}

	public boolean 				add(T e) {
		boolean added = delegate.add(e);
		fireListPropertyChange(singleAdditionChange());
        return added;
	}

	public void 				add(int index, T element) {
		delegate.add(index, element);
		fireListPropertyChange(singleAdditionChange(index));
	}

	public boolean 				addAll(Collection<? extends T> c) {
		delegate.addAll(c);
		fireListPropertyChange(multipleAdditionChange(c.size()));
		return true;
	}

	public boolean 				addAll(int index, Collection<? extends T> c) {
		delegate.addAll(index, c);
		fireListPropertyChange(multipleAdditionChange(index, c.size()));
		return true;
	}

	public T 					remove(int index) {
		T oldValue = delegate.remove(index);
		fireListPropertyChange(singleSuppressionChange(oldValue));
		return oldValue;
	}

	@SuppressWarnings("unchecked")
	public boolean 				remove(Object o) {
		boolean removed = delegate.remove(o);
		if(removed)
			fireListPropertyChange(singleSuppressionChange((T) o));
		return removed;
	}

	public boolean 				removeAll(Collection<?> c) {
		List<T> removedList = delegate.stream().filter(v -> c.contains(v)).collect(Collectors.toList());
		boolean removed = delegate.removeAll(c);
		if(removed)
			fireListPropertyChange(multipleSuppressionChange(removedList));
		return removed;
	}

	public boolean 				retainAll(Collection<?> c) {
		List<T> removedList = delegate.stream().filter(v -> !c.contains(v)).collect(Collectors.toList());
		boolean removed = delegate.retainAll(c);
		if(removed)
			fireListPropertyChange(multipleSuppressionChange(removedList));
		return removed;
	}

	public void 				clear() {
		List<T> removedList = new ArrayList<T>(delegate);
		delegate.clear();
		fireListPropertyChange(multipleSuppressionChange(removedList));
	}

	public Iterator<T> 			iterator() {
		return new InnerIterator();
//		return delegate.iterator();
	}

	public ListIterator<T> 		listIterator() {
		return new InnerIterator();
//		return delegate.listIterator();
	}

	public ListIterator<T> 		listIterator(int index) {
		return new InnerIterator(index);
//		return delegate.listIterator(index);
	}

	public List<T> 				subList(int fromIndex, int toIndex) {
		return new InnerSubList(fromIndex, toIndex);
	}

	private ListPropertyChangeEvent.Change<T> singleAdditionChange() {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
	private ListPropertyChangeEvent.Change<T> singleAdditionChange(int _index) {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
	private ListPropertyChangeEvent.Change<T> multipleAdditionChange(int _count) {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
	private ListPropertyChangeEvent.Change<T> multipleAdditionChange(int _index, int _count) {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
	private ListPropertyChangeEvent.Change<T> singleSuppressionChange(T _oldValue) {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
	private ListPropertyChangeEvent.Change<T> multipleSuppressionChange(List<T> _removed) {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
	private ListPropertyChangeEvent.Change<T> singleUpdateChange(int _index, T _oldValue) {
		return new ListPropertyChangeEvent.Change<T>(delegate) {
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
