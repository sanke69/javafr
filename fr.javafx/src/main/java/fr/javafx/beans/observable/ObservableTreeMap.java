package fr.javafx.beans.observable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import fr.javafx.beans.observable.forks.MapListenerHelper;
import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;

public class ObservableTreeMap<K, P> extends TreeMap<K, P> implements ObservableSortedMap<K, P> {
	private static final long serialVersionUID = 3909018958203865697L;

	private ObservableEntrySet 			entrySet;
	private ObservableKeySet 			keySet;
	private ObservableValues 			values;

	private MapListenerHelper<K, P> 	listenerHelper;
	private final Map<K, P> 			backingMap;

	public ObservableTreeMap(Map<K, P> _map) {
		backingMap = _map;
	}

	@Override
	public int 					size() {
		return backingMap.size();
	}

	@Override
	public boolean 				isEmpty() {
		return backingMap.isEmpty();
	}

	@Override
	public boolean 				containsKey(Object key) {
		return backingMap.containsKey(key);
	}
	@Override
	public boolean 				containsValue(Object value) {
		return backingMap.containsValue(value);
	}

	@Override
	public Set<Entry<K, P>> 	entrySet() {
		if (entrySet == null) {
			entrySet = new ObservableEntrySet();
		}
		return entrySet;
	}
	@Override
	public Set<K> 				keySet() {
		if (keySet == null) {
			keySet = new ObservableKeySet();
		}
		return keySet;
	}
	@Override
	public Collection<P> 		values() {
		if (values == null) {
			values = new ObservableValues();
		}
		return values;
	}

	@Override
	public P 					get(Object key) {
		return backingMap.get(key);
	}
	@Override
	public P 					put(K key, P value) {
		P ret;
		if (backingMap.containsKey(key)) {
			ret = backingMap.put(key, value);
			if (ret == null && value != null || ret != null && !ret.equals(value)) {
				callObservers(new SimpleChange(key, ret, value, true, true));
			}
		} else {
			ret = backingMap.put(key, value);
			callObservers(new SimpleChange(key, ret, value, true, false));
		}
		return ret;
	}

	@Override
	public void 				putAll(Map<? extends K, ? extends P> m) {
		for (Map.Entry<? extends K, ? extends P> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void 				clear() {
		for (Iterator<Entry<K, P>> i = backingMap.entrySet().iterator(); i.hasNext();) {
			Entry<K, P> e = i.next();
			K key = e.getKey();
			P val = e.getValue();
			i.remove();
			callObservers(new SimpleChange(key, val, null, false, true));
		}
	}

	protected void 				callObservers(MapChangeListener.Change<K, P> change) {
		MapListenerHelper.fireValueChangedEvent(listenerHelper, change);
	}

	@Override
	public void 				addListener(InvalidationListener listener) {
		listenerHelper = MapListenerHelper.addListener(listenerHelper, listener);
	}
	@Override
	public void 				addListener(MapChangeListener<? super K, ? super P> observer) {
		listenerHelper = MapListenerHelper.addListener(listenerHelper, observer);
	}

	@Override
	public void 				removeListener(InvalidationListener listener) {
		listenerHelper = MapListenerHelper.removeListener(listenerHelper, listener);
	}
	@Override
	public void 				removeListener(MapChangeListener<? super K, ? super P> observer) {
		listenerHelper = MapListenerHelper.removeListener(listenerHelper, observer);
	}

	@Override
	public String 				toString() {
		return backingMap.toString();
	}

	@Override
	public int 					hashCode() {
		return backingMap.hashCode();
	}

	@Override
	public boolean 				equals(Object obj) {
		return backingMap.equals(obj);
	}

	private class SimpleChange extends MapChangeListener.Change<K, P> {

		private final K key;
		private final P old;
		private final P added;
		private final boolean wasAdded;
		private final boolean wasRemoved;

		public SimpleChange(K key, P old, P added, boolean wasAdded, boolean wasRemoved) {
			super(ObservableTreeMap.this);
			assert (wasAdded || wasRemoved);
			this.key = key;
			this.old = old;
			this.added = added;
			this.wasAdded = wasAdded;
			this.wasRemoved = wasRemoved;
		}

		@Override
		public boolean wasAdded() {
			return wasAdded;
		}

		@Override
		public boolean wasRemoved() {
			return wasRemoved;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public P getValueAdded() {
			return added;
		}

		@Override
		public P getValueRemoved() {
			return old;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			if (wasAdded) {
				if (wasRemoved) {
					builder.append(old).append(" replaced by ").append(added);
				} else {
					builder.append(added).append(" added");
				}
			} else {
				builder.append(old).append(" removed");
			}
			builder.append(" at key ").append(key);
			return builder.toString();
		}

	}

	private class ObservableKeySet implements Set<K> {

		@Override
		public int size() {
			return backingMap.size();
		}

		@Override
		public boolean isEmpty() {
			return backingMap.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return backingMap.keySet().contains(o);
		}

		@Override
		public Iterator<K> iterator() {
			return new Iterator<K>() {

				private Iterator<Entry<K, P>> entryIt = backingMap.entrySet().iterator();
				private K lastKey;
				private P lastValue;

				@Override
				public boolean hasNext() {
					return entryIt.hasNext();
				}

				@Override
				public K next() {
					Entry<K, P> last = entryIt.next();
					lastKey = last.getKey();
					lastValue = last.getValue();
					return last.getKey();
				}

				@Override
				public void remove() {
					entryIt.remove();
					callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
				}

			};
		}

		@Override
		public Object[] toArray() {
			return backingMap.keySet().toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return backingMap.keySet().toArray(a);
		}

		@Override
		public boolean add(K e) {
			throw new UnsupportedOperationException("Not supported.");
		}

		@Override
		public boolean remove(Object o) {
			return ObservableTreeMap.this.remove(o) != null;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return backingMap.keySet().containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException("Not supported.");
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return removeRetain(c, false);
		}

		private boolean removeRetain(Collection<?> c, boolean remove) {
			boolean removed = false;
			for (Iterator<Entry<K, P>> i = backingMap.entrySet().iterator(); i.hasNext();) {
				Entry<K, P> e = i.next();
				if (remove == c.contains(e.getKey())) {
					removed = true;
					K key = e.getKey();
					P value = e.getValue();
					i.remove();
					callObservers(new SimpleChange(key, value, null, false, true));
				}
			}
			return removed;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return removeRetain(c, true);
		}

		@Override
		public void clear() {
			ObservableTreeMap.this.clear();
		}

		@Override
		public String toString() {
			return backingMap.keySet().toString();
		}

		@Override
		public boolean equals(Object obj) {
			return backingMap.keySet().equals(obj);
		}

		@Override
		public int hashCode() {
			return backingMap.keySet().hashCode();
		}

	}

	private class ObservableValues implements Collection<P> {

		@Override
		public int size() {
			return backingMap.size();
		}

		@Override
		public boolean isEmpty() {
			return backingMap.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return backingMap.values().contains(o);
		}

		@Override
		public Iterator<P> iterator() {
			return new Iterator<P>() {

				private Iterator<Entry<K, P>> entryIt = backingMap.entrySet().iterator();
				private K lastKey;
				private P lastValue;

				@Override
				public boolean hasNext() {
					return entryIt.hasNext();
				}

				@Override
				public P next() {
					Entry<K, P> last = entryIt.next();
					lastKey = last.getKey();
					lastValue = last.getValue();
					return lastValue;
				}

				@Override
				public void remove() {
					entryIt.remove();
					callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
				}

			};
		}

		@Override
		public Object[] toArray() {
			return backingMap.values().toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return backingMap.values().toArray(a);
		}

		@Override
		public boolean add(P e) {
			throw new UnsupportedOperationException("Not supported.");
		}

		@Override
		public boolean remove(Object o) {
			for (Iterator<P> i = iterator(); i.hasNext();) {
				if (i.next().equals(o)) {
					i.remove();
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return backingMap.values().containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends P> c) {
			throw new UnsupportedOperationException("Not supported.");
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return removeRetain(c, true);
		}

		private boolean removeRetain(Collection<?> c, boolean remove) {
			boolean removed = false;
			for (Iterator<Entry<K, P>> i = backingMap.entrySet().iterator(); i.hasNext();) {
				Entry<K, P> e = i.next();
				if (remove == c.contains(e.getValue())) {
					removed = true;
					K key = e.getKey();
					P value = e.getValue();
					i.remove();
					callObservers(new SimpleChange(key, value, null, false, true));
				}
			}
			return removed;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return removeRetain(c, false);
		}

		@Override
		public void clear() {
			ObservableTreeMap.this.clear();
		}

		@Override
		public String toString() {
			return backingMap.values().toString();
		}

		@Override
		public boolean equals(Object obj) {
			return backingMap.values().equals(obj);
		}

		@Override
		public int hashCode() {
			return backingMap.values().hashCode();
		}

	}

	private class ObservableEntry implements Entry<K, P> {

		private final Entry<K, P> backingEntry;

		public ObservableEntry(Entry<K, P> backingEntry) {
			this.backingEntry = backingEntry;
		}

		@Override
		public K getKey() {
			return backingEntry.getKey();
		}

		@Override
		public P getValue() {
			return backingEntry.getValue();
		}

		@Override
		public P setValue(P value) {
			P oldValue = backingEntry.setValue(value);
			callObservers(new SimpleChange(getKey(), oldValue, value, true, true));
			return oldValue;
		}

		@Override
		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry)) {
				return false;
			}
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2))) {
					return true;
				}
			}
			return false;
		}

		@Override
		public final int hashCode() {
			return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
		}

		@Override
		public final String toString() {
			return getKey() + "=" + getValue();
		}

	}

	private class ObservableEntrySet implements Set<Entry<K, P>> {

		@Override
		public int size() {
			return backingMap.size();
		}

		@Override
		public boolean isEmpty() {
			return backingMap.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return backingMap.entrySet().contains(o);
		}

		@Override
		public Iterator<Entry<K, P>> iterator() {
			return new Iterator<Entry<K, P>>() {

				private Iterator<Entry<K, P>> backingIt = backingMap.entrySet().iterator();
				private K lastKey;
				private P lastValue;

				@Override
				public boolean hasNext() {
					return backingIt.hasNext();
				}

				@Override
				public Entry<K, P> next() {
					Entry<K, P> last = backingIt.next();
					lastKey = last.getKey();
					lastValue = last.getValue();
					return new ObservableEntry(last);
				}

				@Override
				public void remove() {
					backingIt.remove();
					callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
				}
			};
		}

		@Override
		@SuppressWarnings("unchecked")
		public Object[] toArray() {
			Object[] array = backingMap.entrySet().toArray();
			for (int i = 0; i < array.length; ++i) {
				array[i] = new ObservableEntry((Entry<K, P>) array[i]);
			}
			return array;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] a) {
			T[] array = backingMap.entrySet().toArray(a);
			for (int i = 0; i < array.length; ++i) {
				array[i] = (T) new ObservableEntry((Entry<K, P>) array[i]);
			}
			return array;
		}

		@Override
		public boolean add(Entry<K, P> e) {
			throw new UnsupportedOperationException("Not supported.");
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object o) {
			boolean ret = backingMap.entrySet().remove(o);
			if (ret) {
				Entry<K, P> entry = (Entry<K, P>) o;
				callObservers(new SimpleChange(entry.getKey(), entry.getValue(), null, false, true));
			}
			return ret;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return backingMap.entrySet().containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends Entry<K, P>> c) {
			throw new UnsupportedOperationException("Not supported.");
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return removeRetain(c, false);
		}

		private boolean removeRetain(Collection<?> c, boolean remove) {
			boolean removed = false;
			for (Iterator<Entry<K, P>> i = backingMap.entrySet().iterator(); i.hasNext();) {
				Entry<K, P> e = i.next();
				if (remove == c.contains(e)) {
					removed = true;
					K key = e.getKey();
					P value = e.getValue();
					i.remove();
					callObservers(new SimpleChange(key, value, null, false, true));
				}
			}
			return removed;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return removeRetain(c, true);
		}

		@Override
		public void clear() {
			ObservableTreeMap.this.clear();
		}

		@Override
		public String toString() {
			return backingMap.entrySet().toString();
		}

		@Override
		public boolean equals(Object obj) {
			return backingMap.entrySet().equals(obj);
		}

		@Override
		public int hashCode() {
			return backingMap.entrySet().hashCode();
		}

	}

}