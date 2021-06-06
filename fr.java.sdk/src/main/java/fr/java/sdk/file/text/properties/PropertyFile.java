package fr.java.sdk.file.text.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Properties;

public class PropertyFile {

	public static final Properties load(Path _propFile) {
		try {
			return load( new FileInputStream(_propFile.toFile()) );
		} catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static final Properties load(InputStream _propertiesStream) {
    	Properties properties = new Properties();

		try {
			properties.load(_propertiesStream);
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			if(_propertiesStream != null) {
				try {
					_propertiesStream.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}

		return properties;
	}

	public static final void       save(Properties properties, Path _propertiesFile) {
		save(properties, _propertiesFile, null);
	}
	public static final void       save(Properties properties, Path _propertiesFile, String _comments) {
		OutputStream output = null;

		try {
			output = new FileOutputStream(_propertiesFile.toFile());

			properties.store(output, null);

		} catch(IOException io) {
			io.printStackTrace();
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}

		}
	}


}
