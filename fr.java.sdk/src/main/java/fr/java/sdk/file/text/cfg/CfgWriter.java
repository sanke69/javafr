package fr.java.sdk.file.text.cfg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import fr.java.file.text.config.CfgProperties;
import fr.java.utils.LocalFiles;

public class CfgWriter {

	public CfgWriter() {
		super();
	}

	public void 		write(CfgProperties _properties, Path _path) throws IOException {
		if(_path.toFile().exists())
			write_update(_properties, _path);
		else
			write_new(_properties, _path);
	}

	private void 		write_new(CfgProperties _properties, Path _path) throws IOException {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(_path.toFile()))) {
			_properties.getSections().stream()
			   		  .forEach(section -> {
					   		try {
								bw.write("[" + section.getName() + "]\n");
							} catch (IOException e) {
								e.printStackTrace();
					            throw new UncheckedIOException(e);
							}
							 
							 section.getKeys()
							 		.stream()
							 		.forEach(key -> {
										try {
											bw.write(key.getName() + "=" + key.getValue() + "\n");
										} catch (IOException e) {
											e.printStackTrace();
								            throw new UncheckedIOException(e);
										}
									});
						});
		}
	}
	private void 		write_update(CfgProperties _properties, Path _path) throws IOException {
		Path newCfg = _path.toFile().exists() ? _path.resolveSibling("~cfg.tmp") : _path;

		Map<String, List<String>> savedKeys = new HashMap<String, List<String>>();

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(newCfg.toFile()))) {
			try (BufferedReader br = new BufferedReader(new FileReader(_path.toFile()))) {
				String line;
				String section = null;

				while ((line = br.readLine()) != null) {
					Matcher m = CfgProperties.SECTION_PATTERN.matcher(line);
					if (m.matches()) {
						// Check if previous SECTION was in saving mode
						if(section != null) {
							_properties.getSection(section).getKeys().stream().forEach(key -> {
								if(!savedKeys.get(key.getSection().getName()).contains(key.getName()))
									try {
										bw.write(key.getName() + "=" + key.getValue() + "\n");
									} catch (IOException e) {
										e.printStackTrace();
							            throw new UncheckedIOException(e);
									}
							});
						}
						
						
						section = m.group("SECTION").trim();

						bw.write(line + "\n");

						savedKeys.put(section, new ArrayList<String>());

					} else if(section != null) {
						m = CfgProperties.KEY_PATTERN.matcher(line);
						if (m.matches()) {
							String key   = m.group("KEY").trim();
							String value = m.group("VALUE").trim();
							
							String newValue = _properties.getSection(section).getKey(key).getValue();


							System.out.println("> " + section);
							System.out.println("> " + line);
							System.out.println("> " + value + " ? " + newValue);

							line.replaceFirst(value, newValue);

							bw.write(line + "\n");

							savedKeys.get(section).add(key);

						} else {
							// line is COMMENT
							bw.write(line + "\n");
						}
					} else {
						// line is COMMENT
						bw.write(line + "\n");
					}
				}

				// Check lastSection missing keys...
				_properties.getSection(section).getKeys().stream().forEach(key -> {
					if(!savedKeys.get(key.getSection().getName()).contains(key.getName()))
						try {
							bw.write(key.getName() + "=" + key.getValue() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
				            throw new UncheckedIOException(e);
						}
				});

				// Check createdSection not in previous file...
				_properties.getSections().stream().forEach(s -> {
					if(!savedKeys.containsKey(s.getName())) {
						try {
							bw.write("\n");
							bw.write("[" + s.getName() + "]" + "\n");
						} catch (IOException e) {
							e.printStackTrace();
				            throw new UncheckedIOException(e);
						}
	
						s.getKeys().stream().forEach(key -> {
							try {
								bw.write(key.getName() + "=" + key.getValue() + "\n");
							} catch (IOException e) {
								e.printStackTrace();
					            throw new UncheckedIOException(e);
							}
						});
					}
				});
				
			}
		}
		
		if(newCfg != _path) {
			LocalFiles.rm(_path);
			LocalFiles.copyFile(newCfg, _path);
			LocalFiles.rm(newCfg);
		}
	}

}
