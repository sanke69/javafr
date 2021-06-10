package fr.utils.maths.strings;

public class StringBuffers {

	public static String getLastLine(String _sb) {
		int last = _sb.lastIndexOf("\n");
		if (last >= 0) { return _sb.substring(last, _sb.length()); }
		return "";
	}

	public static String getLastLine(StringBuilder _sb) {
		int last = _sb.lastIndexOf("\n");
		if (last >= 0) { return _sb.substring(last, _sb.length()); }
		return "";
	}

	public static StringBuilder removeLastLine(StringBuilder _sb) {
		int last = _sb.lastIndexOf("\n");
		if (last >= 0) { _sb.delete(last, _sb.length()); }
		return _sb;
	}
	public static StringBuilder removeLastLines(StringBuilder _sb, int _nLines) {
		do {
			int last = _sb.lastIndexOf("\n");
			if (last >= 0) { _sb.delete(last, _sb.length()); }
		} while(--_nLines > 0);
		return _sb;
	}
	
}
