package fr.java.sdk.file.text.cfg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Matcher;

import fr.java.file.text.config.CfgProperties;

public class CfgReader {

	public CfgReader() {
		super();
	}

	public CfgPropertyMap 	read(Path _path) {
		try(FileInputStream fis = new FileInputStream(_path.toFile())) 	{ return read_impl(fis); }
		catch (IOException e) 											{ e.printStackTrace(); }

		return null;
	}
	public CfgPropertyMap 	read(URL _url) {
//		try 															{ Path path = Paths.get(_url.toURI().getPath()); }
//		catch(URISyntaxException e) 									{ e.printStackTrace(); }

		try(InputStream is = _url.openStream()) 						{ return read_impl(is); }
		catch (IOException e) 											{ e.printStackTrace(); }

		return null;
	}

	private CfgPropertyMap	read_impl(InputStream _is) throws IOException {
		CfgPropertyMap entries = new CfgPropertyMap();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(_is, "UTF-8"))) {
			String line;
			String section = null;
			while((line  = br.readLine()) != null) {
				Matcher m = CfgProperties.SECTION_PATTERN.matcher(line);
				if(m.matches()) {
					section = m.group("SECTION").trim();
				} else if(section != null) {
					m = CfgProperties.KEY_PATTERN.matcher(line);
					if(m.matches()) {
						String key   = m.group("KEY").trim();
						String value = m.group("VALUE").trim();

						if(entries.getSection(section) == null)
							entries.addSection(section);
						entries.getSection(section).addKey(key, value);
					}
				}
			}
		}

		return entries;
	}

}
