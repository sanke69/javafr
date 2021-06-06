package fr.javafx.beans.observable;

import java.util.SortedMap;

import javafx.collections.ObservableMap;

public interface ObservableSortedMap<K, V> extends ObservableMap<K, V>, SortedMap<K, V> {
    // No additional methods.
}