/**
 * OutBreak API
 * Copyright (C) 2007-?XYZ  Steve PECHBERTI <steve.pechberti@laposte.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.javafx.scene.properties;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;

public interface Selecter<T> {

	public void               	setMaxDisplayedItems(int _maxLines);

	public Region 				getNode();

	public ObservableList<T> 	itemsProperty();

	public interface Single<T> extends Selecter<T> {
	
		public ObservableValue<T> selectedProperty();
	
	}

	public interface Multi<T> extends Selecter<T> {
	
		public ObservableList<T> selectedProperty();
	
	}

}
