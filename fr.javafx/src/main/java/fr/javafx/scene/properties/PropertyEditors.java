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

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Function;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.time.Time;
import fr.javafx.scene.control.list.ListSelecter;
import fr.javafx.scene.control.list.ListSelecter.Style;
import fr.javafx.scene.control.slider.SliderWithDisplay;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;

public final class PropertyEditors {

	public static final StringConverter<Double> tickDay         = new StringConverter<>() {

		@Override
		public String toString(Double _day) {
			return String.format("j. %02d", _day.intValue());
		}

		@Override
		public Double fromString(String string) {
			return null;
		}

	};
	public static final StringConverter<Double> tickDate        = new StringConverter<>() {

		@Override
		public String toString(Double timestamp) {
			Instant instant = Instant.ofEpochMilli(timestamp.longValue());
			LocalDate date = LocalDate.ofInstant(instant, Time.DEFAULT_ZONEID);

			return String.format("%02d/%02d", date.getDayOfMonth(), date.getMonthValue());
		}

		@Override
		public Double fromString(String string) {
			return null;
		}
	};

	public static final StringConverter<Double> displayDay      = new StringConverter<>() {

		@Override
		public String toString(Double _day) {
			return String.format("jour %02d", _day.intValue());
		}

		@Override
		public Double fromString(String string) {
			return null;
		}

	};
	public static final StringConverter<Double> displayDate     = new StringConverter<>() {

		@Override
		public String toString(Double timestamp) {
			Instant instant = Instant.ofEpochMilli(timestamp.longValue());
			LocalDate date = LocalDate.ofInstant(instant, Time.DEFAULT_ZONEID);

			return String.format("%02d/%02d/%04d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		}

		@Override
		public Double fromString(String string) {
			return null;
		}
	};

	public static final <T> Selecter.Single<T> 					newSingleSelecter(Class<T> _class) {
		return new ListSelecter.Single<T>(_class);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Class<T> _class, Function<T, String> _sc) {
		return new ListSelecter.Single<T>(_class, _sc);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Class<T> _class, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_class, _sc, _visual);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Class<T> _class, StringConverter<T> _sc) {
		return new ListSelecter.Single<T>(_class, _sc);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Class<T> _class, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_class, _sc, _visual);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(T[] _array) {
		return new ListSelecter.Single<T>(_array);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(T[] _array, Function<T, String> _sc) {
		return new ListSelecter.Single<T>(_array, _sc);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(T[] _array, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_array, _sc, _visual);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(T[] _array, StringConverter<T> _sc) {
		return new ListSelecter.Single<T>(_array, _sc);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(T[] _array, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_array, _sc, _visual);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Collection<T> _set) {
		return new ListSelecter.Single<T>(_set);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Collection<T> _set, StringConverter<T> _sc) {
		return new ListSelecter.Single<T>(_set, _sc);
	}
	public static final <T> Selecter.Single<T> 					newSingleSelecter(Collection<T> _set, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_set, _sc, _visual);
	}
	public static final <T extends Enum<T>> Selecter.Single<T> 	newSingleSelecter(EnumSet<T> _set) {
		return new ListSelecter.Single<T>(_set);
	}
	public static final <T extends Enum<T>> Selecter.Single<T> 	newSingleSelecter(EnumSet<T> _set, Function<T, String> _sc) {
		return new ListSelecter.Single<T>(_set, _sc);
	}
	public static final <T extends Enum<T>> Selecter.Single<T> 	newSingleSelecter(EnumSet<T> _set, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_set, _sc, _visual);
	}
	public static final <T extends Enum<T>> Selecter.Single<T> 	newSingleSelecter(EnumSet<T> _set, StringConverter<T> _sc) {
		return new ListSelecter.Single<T>(_set, _sc);
	}
	public static final <T extends Enum<T>> Selecter.Single<T> 	newSingleSelecter(EnumSet<T> _set, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Single<T>(_set, _sc, _visual);
	}

	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Class<T> _class) {
		return new ListSelecter.Multi<T>(_class);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Class<T> _class, Function<T, String> _sc) {
		return new ListSelecter.Multi<T>(_class, _sc);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Class<T> _class, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(_class, _sc, _visual);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Class<T> _class, StringConverter<T> _sc) {
		return new ListSelecter.Multi<T>(_class, _sc);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Class<T> _class, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(_class, _sc, _visual);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(T[] _array) {
		return new ListSelecter.Multi<T>(Arrays.asList(_array));
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(T[] _array, Function<T, String> _sc) {
		return new ListSelecter.Multi<T>(Arrays.asList(_array), _sc);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(T[] _array, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(Arrays.asList(_array), _sc, _visual);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(T[] _array, StringConverter<T> _sc) {
		return new ListSelecter.Multi<T>(Arrays.asList(_array), _sc);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(T[] _array, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(Arrays.asList(_array), _sc, _visual);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Collection<T> _set) {
		return new ListSelecter.Multi<T>(_set);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Collection<T> _set, Function<T, String> _sc) {
		return new ListSelecter.Multi<T>(_set, _sc);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Collection<T> _set, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(_set, _sc, _visual);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Collection<T> _set, StringConverter<T> _sc) {
		return new ListSelecter.Multi<T>(_set, _sc);
	}
	public static final <T> Selecter.Multi<T> 					newMultiSelecter(Collection<T> _set, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(_set, _sc, _visual);
	}
	public static final <T extends Enum<T>> Selecter.Multi<T> 	newMultiSelecter(EnumSet<T> _set) {
		return new ListSelecter.Multi<T>(_set);
	}
	public static final <T extends Enum<T>> Selecter.Multi<T> 	newMultiSelecter(EnumSet<T> _set, Function<T, String> _sc) {
		return new ListSelecter.Multi<T>(_set, _sc);
	}
	public static final <T extends Enum<T>> Selecter.Multi<T> 	newMultiSelecter(EnumSet<T> _set, Function<T, String> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(_set, _sc, _visual);
	}
	public static final <T extends Enum<T>> Selecter.Multi<T> 	newMultiSelecter(EnumSet<T> _set, StringConverter<T> _sc) {
		return new ListSelecter.Multi<T>(_set, _sc);
	}
	public static final <T extends Enum<T>> Selecter.Multi<T> 	newMultiSelecter(EnumSet<T> _set, StringConverter<T> _sc, Style _visual) {
		return new ListSelecter.Multi<T>(_set, _sc, _visual);
	}

	public  static final Editor<String> 						newStringEditor(String _defaultValue) {
		return newStringEditorV2(_defaultValue);
	}
	private static final Editor<String> 						newStringEditorV2(String _defaultValue) {
		return new Editor<String>() {
			TextArea editor = new TextArea(_defaultValue);
//			SliderWithDisplay<String> editor = SliderWithDisplay.forInteger(_min, _max, _step, _value);

			@Override
			public Region getNode() {
				return editor;
			}

			@Override
			public ObservableValue<String> valueProperty() {
				return editor.textProperty();
			}

			@Override
			public ObservableList<Plugin> getPlugins() {
				throw new NotYetImplementedException();
			}

		};
	}


	public  static final  Editor<Integer> 						newIntegerEditor(int _min, int _max, int _step, int _value) {
		return newIntegerEditorV2(_min, _max, _step, _value);
	}
	private static final Editor<Integer> 						newIntegerEditorV2(int _min, int _max, int _step, int _value) {
		return new Editor<Integer>() {
			SliderWithDisplay<Integer> editor = SliderWithDisplay.forInteger(_min, _max, _value, _step);

			@Override
			public Region getNode() {
				return editor;
			}

			@Override
			public ObservableValue<Integer> valueProperty() {
				return editor.objectProperty();
			}

			@Override
			public ObservableList<Plugin> getPlugins() {
				throw new NotYetImplementedException();
			}

		};
	}

	public  static final  Editor<Double> 						newFloatingEditor(double _min, double _max, double _value, double _step) {
		return newFloatingEditorV2(_min, _max, _value, _step);
	}
	private static final Editor<Double> 						newFloatingEditorV2(double _min, double _max, double _value, double _step) {
		return new Editor<Double>() {
			SliderWithDisplay<Double> editor = SliderWithDisplay.forDouble(_min, _max, _value, _step, "%02.1f");

			@Override
			public Region getNode() {
				return editor;
			}

			@Override
			public ObservableValue<Double> valueProperty() {
				return editor.objectProperty();
			}

			@Override
			public ObservableList<Plugin> getPlugins() {
				throw new NotYetImplementedException();
			}

		};
	}

	public static final Editor<LocalDate> 						newLocalDateEditor(LocalDate _from, LocalDate _to) {
		return newLocalDateEditorV2(_from, _to);
	}
	public static final Editor<LocalDate> 						newLocalDateEditorV2(LocalDate _from, LocalDate _to) {
		SliderWithDisplay<LocalDate> dateSelecter = SliderWithDisplay.forLocalDate(_from, _to);
		
		return new Editor<LocalDate>() {

			@Override
			public Region getNode() {
				return dateSelecter;
			}

			@Override
			public ObservableValue<LocalDate> valueProperty() {
				return dateSelecter.objectProperty();
			}

			@Override
			public ObservableList<Plugin> getPlugins() {
				throw new NotYetImplementedException();
			}

		};
	}

	public static final Editor<Integer> 						newDayEditor() {
		return newDayEditor(-60, 90);
	}
	public static final Editor<Integer> 						newDayEditor(int _min, int _max) {
		SliderWithDisplay<Integer> daySelecter = SliderWithDisplay.forDay(_min, _max);
		
		return new Editor<Integer>() {

			@Override
			public Region getNode() {
				return daySelecter;
			}

			@Override
			public ObservableValue<Integer> valueProperty() {
				return daySelecter.objectProperty();
			}

			@Override
			public ObservableList<Plugin> getPlugins() {
				throw new NotYetImplementedException();
			}

		};
	}

}
