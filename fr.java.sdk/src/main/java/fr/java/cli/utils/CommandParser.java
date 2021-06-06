package fr.java.cli.utils;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
	char			divisor, quote, eol;
	List<String>	args;

	public CommandParser() {
		this(' ', '"', '\0');
	}
	public CommandParser(final char _divisor, final char _quote, final char _endOfLine) {
		super();
		divisor = _divisor;
		quote   = _quote;
		eol     = _endOfLine;
		args    = new ArrayList<String>();
	}

	public int 			argc() {
		return args.size();
	}
	public List<String> args() {
		return args;
//		return args.toArray(new String[0]);
	}
	public String[] 	argsArray() {
		return args.toArray(new String[0]);
	}
	public String 		argv(int num) {
		return (num < argc()) ? args.get(num) : null;
	}

	public void			shift() {
		args.remove(0);
	}

	public int 			argvAsInt(int num) {
		if(num > argc())
			return -1;

		return Integer.parseInt(args.get(num));
	}
	public float 		argvAsFloat(int num) {
		if(num > argc())
			return -1;

		return Float.parseFloat(args.get(num));
	}
	public double 		argvAsDouble(int num) {
		if(num > argc())
			return -1;

		return Double.parseDouble(args.get(num));
	}

	public int parse(String cmd) {
		int 	beg     = 0,
				size    = 0,
				argsize = 0;
		boolean isLast  = false,
				isQuote = false;

		if(cmd != null)
			while(size < cmd.length() - 1
					&& cmd.charAt(size) != '\0'
					&& cmd.charAt(size) != eol) ++size;

		if(size == 0)
			return size;
		size++;

		args.clear();

		while(!isLast) {
			while(cmd.charAt(beg) == divisor)
				++beg;

			if(cmd.charAt(beg) == quote) {
				argsize = 1;
				while(cmd.charAt(beg + argsize++) != quote) ;

				args.add(cmd.substring(beg, beg + argsize));

				beg    += argsize;
				argsize = 0;
				isQuote = true;

				if(beg == size)
					isLast = true;
			}

			if(!isQuote) {
				while((cmd.charAt(beg + argsize) != divisor)
						&& (cmd.charAt(beg + argsize) != quote)
						&& (cmd.charAt(beg + argsize) != eol)
						&& (cmd.charAt(beg + argsize) != '\0')
						&& ((beg + argsize) < cmd.length() - 1)) argsize++;

				if(cmd.charAt(beg + argsize) == divisor || cmd.charAt(beg + argsize) == quote) {
					args.add(cmd.substring(beg, beg + argsize));
					beg    += argsize + 1;
					argsize = 0;
				}

				if((beg + argsize) == cmd.length() - 1 || cmd.charAt(beg + argsize) == eol || cmd.charAt(beg + argsize) == '\0') {
					args.add(cmd.substring(beg, beg + argsize + 1));
					isLast = true;
				}
			}

			isQuote = false;
		}

		return size;
	}

}
