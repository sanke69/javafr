package fr.java.sdk.file.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.file.text.TextFileX;
import fr.java.file.text.TextFileX.LineX;
import fr.java.lang.exceptions.InvalidArgumentException;

public class TextFile implements TextFileX.Editable {

	public class Line implements TextFileX.LineX.Editable {
		int index;
		
		Line(int _index) {
			super();
			index = _index;
		}
		
		public int 		getIndex() {
			return index;
		}

		public void   	set(String _string) {
			if(!validLineContent.test(_string))
				throw new InvalidArgumentException(invalidLineMessage);

			lines.set(index, _string);
		}
		public String 	get() {
			return lines.get(index);
		}

	}

	Predicate<String> 	validLineContent   = (string) -> !string.contains("\n");
	String				invalidLineMessage = "line must not contain '\n'";

	Path            path;
	List<String> 	lines;

	public TextFile(Path _path) {
		super();
		
		path = _path;
	}

	public void 		loadAndCache() throws IOException {
		lines = new ArrayList<String>();

		try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
			String line = null;
			while((line = br.readLine()) != null)
			    lines.add(line);
		}
	}
	public void 		save(Path _path) throws IOException {
		try(BufferedWriter br = new BufferedWriter(new FileWriter(_path.toFile()))) {
			for(String line : lines)
			    br.write(line + "\n");
		}
	}

	public int 			getLineCount() {
		return lines.size();
	}
	public List<LineX> 	getLines() {
/**/
		return IntStream.range(0, getLineCount())
				        .mapToObj(Line::new)
				        .collect(Collectors.toList());
/*/
		AtomicInteger index = new AtomicInteger();
		return lines.stream()
                      .filter(n -> n.length() <= index.incrementAndGet())
                      .map(s -> new Line(index.get()))
                      .collect(Collectors.toList());
/**/
	}

	public void 		setLine(int _line, String _newLine) throws IOException {
		if(lines == null)
			loadAndCache();

		while(_line > lines.size())
			lines.add("\n");

		lines.set(_line, _newLine);
	}
	public LineX.Editable 		getLine(int _line) throws IOException {
		if(lines == null)
			loadAndCache();

		while(_line > lines.size())
			lines.add("\n");

		return new Line(_line - 1);
	}

}
