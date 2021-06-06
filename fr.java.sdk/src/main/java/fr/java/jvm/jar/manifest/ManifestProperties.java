package fr.java.jvm.jar.manifest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import fr.java.sdk.nio.file.FileObject;
import fr.java.utils.LocalFiles;

public class ManifestProperties {
	public static final String defaultPath = "META-INF/MANIFEST.MF";

	public enum ManifestFields {
		// Ckeck Validity
		MANIFEST_VERSION			("Manifest-Version"),
		ARCHIVER_VERSION			("Archiver-Version"),
		// Information
		NAME						("Name"),
		VERSION						("Version"),
		EXTENSION_NAME				("Extension-Name"),
		PACKAGE						("Package"),
		CREATED_BY					("Created-By"),
		BUILT_BY					("Built-By"),
		BUILD_JDK					("Build-Jdk"),
		IMPLEMENTATION_TITLE		("Implementation-Title"),
		IMPLEMENTATION_VERSION		("Implementation-Version"),
		IMPLEMENTATION_VENDOR		("Implementation-Vendor"),
		IMPLEMENTATION_VENDOR_ID	("Implementation-Vendor-Id"),
		IMPLEMENTATION_URL			("Implementation-URL"),
		// Ant
		ANT_VERSION					("Ant-Version"),
		// Specification
		SPECIFICATION_VERSION		("Specification-Version"),
		SPECIFICATION_VENDOR		("Specification-Vendor"),
		// OSGi Information

		// Configuration
		PLUGIN_ID					("PlugIn-Id"),
		PLUGIN_NAME					("PlugIn-Name"),
		PLUGIN_DESCRIPTION			("PlugIn-Description"),
		PLUGIN_CLASS				("PlugIn-Class"),
		PLUGIN_PROVIDER				("PlugIn-Provider"),
		PLUGIN_VERSION				("PlugIn-Version"),
		// Runtime
		MAIN_CLASS					("Main-Class"),
		PREMAIN_CLASS				("Premain-Class"),
		AGENT_CLASS					("Agent-Class"),
		
		PROPERTY_FILE				("Properties-File"),
		;

		String name;

		private ManifestFields(String _name) {
			name = _name;
		}

		public String key() { return name; }

		public static ManifestFields of(String _property) {
			for(ManifestFields mf : ManifestFields.values())
				if(mf.key().compareToIgnoreCase(_property) == 0)
					return mf;
			return null;
		}

	}

	public enum ManifestContext {
		JAR_FILE("JAR"), WAR_FILE("WAR"), EAR_FILE("EAR"), ZIP_FILE("ZIP"),
		FOLDER("FOLDER"),
		UNKNOWN("?")
		;
		
		String context;
		
		private ManifestContext(String _context) {
			context = _context;
		}
		 

		public static ManifestContext of(Path _path) {
			if(_path.toFile().isDirectory())
				return FOLDER;
			
			FileObject fo = new FileObject(_path); 
			
			if(".jar".compareToIgnoreCase(fo.getExtension()) == 0)
				return JAR_FILE;			
			if(".war".compareToIgnoreCase(fo.getExtension()) == 0)
				return WAR_FILE;
			if(".ear".compareToIgnoreCase(fo.getExtension()) == 0)
				return EAR_FILE;
			if(".zip".compareToIgnoreCase(fo.getExtension()) == 0)
				return ZIP_FILE;

			return UNKNOWN;
		}

	};
/*
	public static class ManifestField {
		private Optional<ManifestFields> field;
		private Optional<String>         key;
		private String                   value;
		
		public ManifestField() {
			this("undef", null);
			
		}
		public ManifestField(ManifestFields _key, String _value) {
			field = Optional.of(_key);
			key   = Optional.empty();
			value = _value;
		}
		public ManifestField(String _key, String _value) {
			field = (Optional<ManifestFields>) (ManifestFields.of(_key) != null ? Optional.of( ManifestFields.of(_key) ) : Optional.empty());
			key   = field.isPresent() ? null : Optional.of(_key);
			value = _value;
		}
		
	}
*/
	private Path				path;
	private ManifestContext		context;
	private Map<String, Object> properties;

	public static Manifest 			 getJarManifest(Path _path) throws IOException {
		Manifest manifest;
		try(JarInputStream jarStream = new JarInputStream(new FileInputStream(_path.toFile()))) {
			manifest = jarStream.getManifest();
			return manifest;
		} catch(IOException e) {
			return null;
		}
	}
	public static ManifestProperties getJarManifestProperties(Path _path) throws IOException {
		Manifest manifest = getJarManifest(_path);
		/*
		try(JarInputStream jarStream = new JarInputStream(new FileInputStream(_path.toFile()))) {
			manifest = jarStream.getManifest();
		} catch(IOException e) {
			return null;
		}
		/*
		Manifest manifest = null;
		try(JarFile jar = new JarFile(pluginRepository)) {
			manifest = jar.getManifest();
		} catch(IOException e) {
			return null;
		}
		 */
		Map<String, Object> props = new HashMap<String, Object>();

		for(Object entry : manifest.getMainAttributes().keySet()) {
			String key   = entry.toString();
			String value = manifest.getMainAttributes().getValue(entry.toString());
			props.put(key, value);
		}

		return new ManifestProperties(_path, props);
	}

	public static Manifest 			 getFolderManifest(Path _path) throws IOException {
		Manifest manifest;
		try(InputStream is = LocalFiles.getContent(_path.resolve(defaultPath))) {
			manifest = new Manifest(is);
			return manifest;
		} catch(IOException e) {
			return null;
		}
	}
	public static ManifestProperties getFolderManifestProperties(Path _path) throws IOException {
		Manifest manifest;
		try(InputStream is = LocalFiles.getContent(_path.resolve(defaultPath))) {
			manifest = new Manifest(is);
		} catch(IOException e) {
			return null;
		}

		Map<String, Object> props = new HashMap<String, Object>();

		for(Object entry : manifest.getMainAttributes().keySet()) {
			String key   = entry.toString();
			String value = manifest.getMainAttributes().getValue(entry.toString());
			props.put(key, value);
		}

		return new ManifestProperties(_path, props);
	}

	public static Manifest 			 getSourceManifest(Path _path) {
		Manifest manifest;
		try(InputStream is = LocalFiles.getContent(_path.resolve("src/main/resources").resolve(defaultPath))) {
			manifest = new Manifest(is);
			return manifest;
		} catch(IOException e) {
			return null;
		}
	}
	public static ManifestProperties getSourceManifestProperties(Path _path) throws IOException {
		Manifest manifest;
		try(InputStream is = LocalFiles.getContent(_path.resolve("src/main/resources").resolve(defaultPath))) {
			manifest = new Manifest(is);
		} catch(IOException e) {
			return null;
		}

		Map<String, Object> props = new HashMap<String, Object>();

		for(Object entry : manifest.getMainAttributes().keySet()) {
			String key   = entry.toString();
			String value = manifest.getMainAttributes().getValue(entry.toString());
			props.put(key, value);
		}

		return new ManifestProperties(_path, props);
	}

	protected ManifestProperties(Path _file, Map<String, Object> _properties) {
		super();
		path       = _file;
		context    = ManifestContext.of(_file);
		properties = _properties;
	}

	public Object getProperty(ManifestFields _property) {
		return properties.get(_property.key());
	}
	public Object getProperty(String _property) {
		return properties.get(_property);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("MANIFEST.MF: " + path + " (" + this.context + ")" + "\n");
		for(String key : properties.keySet())
			sb.append(" - " + key + ": " + properties.get(key) + "\n");

		return sb.toString();
	}
	
}
