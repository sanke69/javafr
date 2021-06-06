package fr.java.sdk.file.text.cfg;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.file.text.config.CfgProperties;
import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;
import fr.java.sdk.file.text.cfg.CfgPropertyMap.CfgSectionMap;

public class CfgPropertyMap extends LinkedHashMap<String, CfgSectionMap> implements CfgProperties {
	private static final long serialVersionUID = 1L;

	class CfgSectionMap extends LinkedHashMap<String, String> implements CfgProperties.Section {
		private static final long serialVersionUID = 1L;

		final String name;

		public CfgSectionMap(String _sectionName) {
			super();
			name = _sectionName;
		}

		@Override
		public Composite 				getParent() {
			return CfgPropertyMap.this;
		}
		@Override
		public String 					getName() {
			return name;
		}

		@Override
		public Set<Key> 				getKeys() {
			return entrySet().stream().map(e -> asKey(e.getKey(), e.getValue())).collect(Collectors.toSet());
		}

		@Override
		public boolean 					hasKey(String _name) {
			return containsKey(_name);
		}

		public Key 						addKey(String _name, String _value) {
			put(_name, _value);
			return asKey(_name, get(_name));
		}
		@Override
		public Key 						getKey(String _name) {
			return asKey(_name, get(_name));
		}

		private Key 					asKey(String _key, String _value) {
			return new Key() {

				@Override
				public Section 		getSection() {
					return CfgSectionMap.this;
				}

				@Override
				public String 			getName() {
					return _key;
				}

				@Override
				public String 			getValue() {
					return _value;
				}

			};
		}

	}

	public CfgPropertyMap() {
		super();
	}

	@Override
	public Composite 					getParent() {
		return null;
	}

	@Override
	public Set<? extends Component> 	getChildren() {
		return getSections();
	}

	@Override
	public Set<Section> 				getSections() {
		return values().stream().collect(Collectors.toSet());
	}

	public Section 						addSection(String _name) {
		if(!containsKey(_name))
			put(_name, new CfgSectionMap(_name));
		return getSection(_name);
	}
	@Override
	public CfgSectionMap				getSection(String _name) {
		return get(_name);
	}

	@Override
	public String 						getValue(String _section, String _key) {
		return getSection(_section).getKey(_key).getValue();
	}

}
