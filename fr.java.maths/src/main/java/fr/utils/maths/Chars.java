package fr.utils.maths;

import static fr.java.lang.enums.AsciiMap.*;
import static fr.java.lang.enums.CharType.*;

import java.awt.event.KeyEvent;

import fr.java.lang.enums.AsciiMap;
import fr.java.lang.enums.CharType;

public class Chars {

	public static CharType kindOf(byte c) {
        return kindOf((int) c);
    }
	public static CharType kindOf(int _bytes) {
        if(_bytes >= CAR_NUM_0.bytes && _bytes <= CAR_NUM_9.bytes)
            return NUMERIC;

        if(_bytes == CAR_DOT.bytes || _bytes == CAR_COMMA.bytes)
            return NUMERIC;

        if((_bytes >= CAR_CHAR_A.bytes && _bytes <= CAR_CHAR_Z.bytes) || (_bytes >= CAR_CHAR_a.bytes && _bytes <= CAR_CHAR_z.bytes))
            return ALPHANUM;

        if(_bytes == CAR_PERCEN.bytes || _bytes == CAR_LPAREN.bytes || _bytes == CAR_RPAREN.bytes || _bytes == CAR_PLUS.bytes || _bytes == CAR_MINUS.bytes || _bytes == CAR_ASTERI.bytes || _bytes == CAR_FSLASH.bytes || _bytes == CAR_CIRCUM.bytes)
            return OPERATOR;

        return UNKNOWN;
    }
	public static CharType kindOf(AsciiMap c) {
        return kindOf(c.bytes);
    }
	
	public static boolean isPrintable(char _c) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of( _c );
	    return (!Character.isISOControl(_c)) 
	    		&& _c != KeyEvent.CHAR_UNDEFINED 
	            && block != null 
	            && block != Character.UnicodeBlock.SPECIALS;
	}
	public static boolean isAsciiPrintable(char _ch) {
		return _ch >= 32 && _ch < 127;
	}
	
}
