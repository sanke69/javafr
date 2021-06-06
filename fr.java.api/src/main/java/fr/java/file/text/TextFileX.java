package fr.java.file.text;

import java.io.IOException;
import java.util.List;

public interface TextFileX {

	public static interface Editable extends TextFileX {
		public void 	setLine(int _index, String _content) throws IOException;
	}

	public static interface LineX {

		public interface Editable extends LineX {
			public void set(String _string);
		}
		
		public int 		getIndex();
		public String 	get();

	}

	public int 			getLineCount();
	public List<LineX> 	getLines() throws IOException;

	public LineX 		getLine(int _line) throws IOException;

}
