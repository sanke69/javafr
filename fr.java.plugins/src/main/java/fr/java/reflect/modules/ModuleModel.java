package fr.java.reflect.modules;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleReference;

import fr.java.patterns.valueable.ValueObject;

public class ModuleModel implements Comparable<ModuleModel> {
	public String 			name, version;
	public boolean         	loaded, found;

	public ModuleDescriptor descriptor;
	public ModuleReference 	reference;

	ModuleModel(String _name) {
		super();
		name   = _name;
		loaded = false;
	}
	ModuleModel(String _name, boolean _found) {
		super();
		name   = _name;
		found  = _found;
		loaded = false;
	}
	ModuleModel(String _name, boolean _found, boolean _loaded) {
		super();
		name   = _name;
		found  = _found;
		loaded = _loaded;
	}

	ModuleModel(Module _module) {
		super();
		name       = _module.getDescriptor().name();
		found      = true;
		loaded     = true;

		reference  = null;
		descriptor = _module.getDescriptor();
	}
	ModuleModel(ModuleReference _moduleRef) {
		super();
		name       = _moduleRef.descriptor().name();
		found      = _moduleRef.location().isPresent();
		loaded     = false;

		reference  = _moduleRef;
		descriptor = _moduleRef.descriptor();
	}
	ModuleModel(ModuleDescriptor _moduleDesc) {
		super();
		name       = _moduleDesc.name();
		found      = false;
		loaded     = false;

		reference  = null;
		descriptor = _moduleDesc;
	}
	ModuleModel(ModuleDescriptor.Requires _require) {
		super();
		name       = _require.name();
		found      = true;
		loaded     = true;

		reference  = null;
		descriptor = null;
	}

	public String toString() {
		return name + (version != null ? "[v. " + version + "]" : "");
	}

	public boolean equals(Object _o) {
		if(!(_o instanceof ModuleModel) && !(_o instanceof ValueObject) && !(_o instanceof String))
			return false;

		String modName = null;

		if(_o instanceof ValueObject)
			modName = ((ModuleModel) ((ValueObject) _o).getValue()).name;

		if(_o instanceof ModuleModel)
			modName = ((ModuleModel) _o).name;

		if(_o instanceof String)
			modName = (String) _o;

		return name.compareTo(modName) == 0;
	}

	@Override
	public int compareTo(ModuleModel _mm) {
		// TODO Auto-generated method stub
		return 0;
	}

}
