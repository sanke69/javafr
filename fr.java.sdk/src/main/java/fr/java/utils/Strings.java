package fr.java.utils;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Strings {

/**/
	public static final String BASE[] = { "0","1","2","3","4","5","6","7","8","9",
		   "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
		   "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
	     };
/*/
	public static final char BASE[] = { '0','1','2','3','4','5','6','7','8','9',
		   'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
		   'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
	     };
/**/

	public static String lenientFormat(String template, Object... args) {
		template = String.valueOf(template); // null -> "null"

		args = args == null ? new Object[] { "(Object[])null" } : args;

		// start substituting the arguments into the '%s' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
		int templateStart = 0;
		int i = 0;
		while(i < args.length) {
			int placeholderStart = template.indexOf("%s", templateStart);
			if(placeholderStart == -1) {
				break;
			}
			builder.append(template, templateStart, placeholderStart);
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template, templateStart, template.length());

		// if we run out of placeholders, append the extra args in square braces
		if(i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while(i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}

	public static String 		toString(boolean[] _values) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(boolean b : _values)
			sb.append( (b?"T":"F") + " " );
		sb.append("]");
		
		return sb.toString();
	}
	public static String 		toString(short[] _values) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(short s : _values)
			sb.append(s + " ");
		sb.append("]");
		
		return sb.toString();
	}
	public static String 		toString(int[] _values) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(int i : _values)
			sb.append(i + " ");
		sb.append("]");
		
		return sb.toString();
	}
	public static String 		toString(long[] _values) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(long l : _values)
			sb.append(l + " ");
		sb.append("]");
		
		return sb.toString();
	}
	public static String 		toString(float[] _values) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(float f : _values)
			sb.append(f + " ");
		sb.append("]");
		
		return sb.toString();
	}
	public static String 		toString(double[] _values) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(double d : _values)
			sb.append(d + " ");
		sb.append("]");
		
		return sb.toString();
	}
	public static <T> String 	toString(T[] _objects) {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(T o : _objects)
			sb.append(o.toString() + " ");
		sb.append("]");
		
		return sb.toString();
	}

	public static String 		capitalize(String _string) {
		return upperFirst(_string);
	}
	public static String 		upperFirst(String _string) {
		if(_string.length() > 1)
			return _string.substring(0, 1).toUpperCase() + _string.substring(1);
		else
			return _string.substring(0, 1).toUpperCase();
	}
	public static String 		lowerFirst(String _string) {
		if(_string.length() > 1)
			return _string.substring(0, 1).toLowerCase() + _string.substring(1);
		else
			return _string.substring(0, 1).toLowerCase();
	}

	public static String 		getFormattedValue(double _d) {
		return String.format("%1.2f", _d);
	}

	// https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	public static boolean isInteger(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }

	    return true;
	}

	public static int 			indexOfFirstCapitalize(String _string) {
		Pattern pat = Pattern.compile("[A-Z][^A-Z]");
		Matcher match = pat.matcher(_string);

		if(match.find())
			return match.start();
		return -1;
	}
	public static int 			indexOfLastCapitalize(String _string) {
		Pattern pat = Pattern.compile("[A-Z][^A-Z]*$");
		Matcher match = pat.matcher(_string);

		int lastCapitalIndex = -1;
		while(match.find())
			lastCapitalIndex = match.start();
		return lastCapitalIndex;
	}

	public static String 		padRight(String _s, int _n) {
	     return String.format("%1$-" + _n + "s", _s);
	}
	public static String 		padRight(String _s, int _n, char _c) {
		return String.format("%s%s", _s, repeat(_c, ( _n - _s.length() )));
	}

	public static String 		padLeft(String _s, int _n) {
	    return String.format("%1$" + _n + "s", _s);  
	}
	public static String 		padLeft(String _s, int _n, char _c) {
		if(_n - _s.length() <= 0)
			return _s;
		return String.format("%s%s", repeat(_c, ( _n - _s.length() )), _s);
//		return String.format("%" + _c + "$" + _n + "s", _s);  
	}

	public static String 		padLeftZero(String _s, int _n) {
	    return String.format("%0$" + _n + "s", _s);
	}

	public static String 		center(String _s, int _n) {
	    String result = String.format("%1$-" + (_n+_s.length())/2 + "s", _s);
	    return String.format("%1$" + _n + "s", result);
	}

/*
    public static String center(String _str, int n_size, char c) {
        if(n_size < m_OffsetOut)
            memset(m_Buffer + n_size, 0, (size_t) (m_Capacity - n_size));
        else if(m_OffsetOut == 0) {
            _set (m_Buffer, c, n_size);
        } else {
            t_size space       = n_size - m_OffsetOut;
            t_size right_space = space / 2;
            t_size left_space  = space / 2;
            if(2 * right_space < space) ++left_space;
            t_size sz_tmp      = m_OffsetOut;
            char*  temp        = _clone(m_Buffer, sz_tmp);

            _set (m_Buffer, c, left_space);
            _copy(m_Buffer + left_space, temp, sz_tmp);

            _set (m_Buffer + left_space + sz_tmp, c, right_space);
        }

        m_OffsetOut = n_size;
        m_Buffer[m_OffsetOut] = "\0";

        return *this;
    }
    CString& CString::left(const t_size n_size, const char& c) {
        _defrag();
        reserve(n_size);

        if(n_size < m_OffsetOut)
            _set(m_Buffer + n_size, c, m_Capacity - n_size);
        else
            _set(m_Buffer + m_OffsetOut, c, m_Capacity - m_OffsetOut);

        m_OffsetOut = n_size;
        m_Buffer[m_OffsetOut] = "\0";

        return *this;
    }
    CString& CString::right(const t_size n_size, const char& c) {
        _defrag();
        reserve(n_size);

        if(n_size < m_OffsetOut)
            _set(m_Buffer + n_size, 0, m_Capacity - n_size);
        else {
            t_size space  = n_size - m_OffsetOut;
            t_size sz_tmp = m_OffsetOut;
            char*  temp   = _clone(m_Buffer, sz_tmp);

            _set (m_Buffer, c, space);
            _copy(m_Buffer + space, temp, sz_tmp);
        }

        m_OffsetOut = n_size;
        m_Buffer[m_OffsetOut] = "\0";

        return *this;
    }
	*/

	public static String getHex(byte[] raw) {
	    final StringBuilder hex = new StringBuilder(2 * raw.length);
	    for (final byte b : raw) {
	        hex.append(BASE[(b & 0xF0) >> 4]).append(BASE[(b & 0x0F)]);
	    }
	    return hex.toString();
	}
	public static String getHex(byte[] raw, char _separator) {
	    final StringBuilder hex = new StringBuilder(2 * raw.length + (raw.length-1));
	    for (final byte b : raw) {
	        hex.append(BASE[(b & 0xF0) >> 4]).append(BASE[(b & 0x0F)]).append(_separator);
	    }
	    return hex.substring(0, hex.length() - 1).toString();
	}

	public static boolean isValidUTF8(byte[] _bytes) {
		CharsetDecoder cs = Charset.forName("UTF-8").newDecoder();

	    try {
	        cs.decode(ByteBuffer.wrap(_bytes));
	        return true;
	    } catch(CharacterCodingException e){
	        return false;
	    }  
	}

	public static String repeat(char _c, int _n) {
	    return Stream.generate(() -> String.valueOf(_c)).limit(_n).collect(Collectors.joining()); 
//	    return String.join("", Collections.nCopies(_n, String.valueOf(_c)));
	}
	public static String repeat(String _pattern, int _n) {
	    return Stream.generate(() -> _pattern).limit(_n).collect(Collectors.joining());
	}

	public static String format(String _format, String _leftBracket, String _rightBracket, Map<String, Object> _values) {
	    StringBuilder convFormat = new StringBuilder(_format);
	    Set<String> keys = _values.keySet();
	    ArrayList<Object> valueList = new ArrayList<Object>();
	    int currentPos = 1;
	    for(String key : keys) {
	        String  formatKey = _leftBracket + key + _rightBracket,
	        		formatPos = "%" + Integer.toString(currentPos) + "$";
	        int index = -1;
	        while ((index = convFormat.indexOf(formatKey, index)) != -1) {
	            convFormat.replace(index, index + formatKey.length(), formatPos);
	            index += formatPos.length();
	        }
	        valueList.add(_values.get(key));
	        ++currentPos;
	    }
	    return String.format(convFormat.toString(), valueList.toArray());
	}
	/**
	 * format :
	 *   example: "Hello ${name}s ! Your account balance is ${account}.2f €"
	 * @param format
	 * @param values
	 * @return
	 */
	public static String format(String format, Map<String, Object> values) {
		return format(format, "${", "}", values);
	}
	public static String format(String format, Object... values) {
		assert(values.length % 2 == 0);

		Map<String, Object> vals = new HashMap<String, Object>();
		for(int k = 0; k < values.length / 2; ++k)
			vals.put((String) values[2*k], values[2*k+1]);
			
		return format(format, "${", "}", vals);
	}

	public static boolean isEmpty(String str) {
		return (str == null) || str.isEmpty();
	}

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
