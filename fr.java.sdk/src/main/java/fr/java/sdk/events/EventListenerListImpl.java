package fr.java.sdk.events;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.EventListener;

import fr.java.events.EventListenerList;

// fork of javax.swing.event.EventListenerList
public class EventListenerListImpl implements EventListenerList, Serializable {
	private static final long serialVersionUID = 4331349673187684362L;

	/* A null array to be shared by all empty listener lists*/
    private final static Object[] NULL_ARRAY = new Object[0];
    /* The list of ListenerType - Listener pairs */
    protected transient Object[] listenerList = NULL_ARRAY;

    public Object[] getListenerList() {
        return listenerList;
    }

    public <T extends EventListener> T[] getListeners(Class<T> t) {
        Object[] lList = listenerList;
        int n = getListenerCount(lList, t);
        T[] result = (T[]) Array.newInstance(t, n);
        int j = 0;
        for (int i = lList.length-2; i>=0; i-=2) {
            if (lList[i] == t) {
                result[j++] = (T)lList[i+1];
            }
        }
        return result;
    }

    public int getListenerCount() {
        return listenerList.length/2;
    }

    public int getListenerCount(Class<?> t) {
        Object[] lList = listenerList;
        return getListenerCount(lList, t);
    }

    private int getListenerCount(Object[] list, Class t) {
        int count = 0;
        for (int i = 0; i < list.length; i+=2) {
            if (t == (Class)list[i])
                count++;
        }
        return count;
    }

    public synchronized <T extends EventListener> void add(Class<T> t, T l) {
        if (l==null) {
            // In an ideal world, we would do an assertion here
            // to help developers know they are probably doing
            // something wrong
            return;
        }
        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l + " is not of type " + t);
        }
      
        if (listenerList == NULL_ARRAY) {
            // if this is the first listener added,
            // initialize the lists
            listenerList = new Object[] { t, l };
        } else {
            // Otherwise copy the array and add the new listener
            int i = listenerList.length;
            Object[] tmp = new Object[i+2];
            System.arraycopy(listenerList, 0, tmp, 0, i);

            tmp[i] = t;
            tmp[i+1] = l;

            listenerList = tmp;
        }
    }

    /**
     * Removes the listener as a listener of the specified type.
     * @param t the type of the listener to be removed
     * @param l the listener to be removed
     */
    public synchronized <T extends EventListener> void remove(Class<T> t, T l) {
        if (l ==null) {
            // In an ideal world, we would do an assertion here
            // to help developers know they are probably doing
            // something wrong
            return;
        }
        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l +
                                         " is not of type " + t);
        }
        // Is l on the list?
        int index = -1;
        for (int i = listenerList.length-2; i>=0; i-=2) {
            if ((listenerList[i]==t) && (listenerList[i+1].equals(l) == true)) {
                index = i;
                break;
            }
        }

        // If so,  remove it
        if (index != -1) {
            Object[] tmp = new Object[listenerList.length-2];
            // Copy the list up to index
            System.arraycopy(listenerList, 0, tmp, 0, index);
            // Copy from two past the index, up to
            // the end of tmp (which is two elements
            // shorter than the old list)
            if (index < tmp.length)
                System.arraycopy(listenerList, index+2, tmp, index,
                                 tmp.length - index);
            // set the listener array to the new array or null
            listenerList = (tmp.length == 0) ? NULL_ARRAY : tmp;
            }
    }

    // Serialization support.
    private void writeObject(ObjectOutputStream s) throws IOException {
        Object[] lList = listenerList;
        s.defaultWriteObject();

        // Save the non-null event listeners:
        for (int i = 0; i < lList.length; i+=2) {
            Class<?> t = (Class)lList[i];
            EventListener l = (EventListener)lList[i+1];
            if ((l!=null) && (l instanceof Serializable)) {
                s.writeObject(t.getName());
                s.writeObject(l);
            }
        }

        s.writeObject(null);
    }

    public void removeAll() {
    	listenerList = NULL_ARRAY;
    }
    
    /**
     * Returns a string representation of the EventListenerList.
     */
    public String toString() {
        Object[] lList = listenerList;
        String s = "EventListenerList: ";
        s += lList.length/2 + " listeners: ";
        for (int i = 0 ; i <= lList.length-2 ; i+=2) {
            s += " type " + ((Class)lList[i]).getName();
            s += " listener " + lList[i+1];
        }
        return s;
    }

	public boolean isEmpty() {
		return listenerList == NULL_ARRAY;
	}
}
