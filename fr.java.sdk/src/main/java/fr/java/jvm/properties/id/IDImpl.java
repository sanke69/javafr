/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @file     ID.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.jvm.properties.id;

import java.util.EnumSet;

import fr.java.lang.enums.Primitive;
import fr.java.lang.properties.ID;
import fr.java.utils.Strings;

class IDImpl implements ID {
	private final Primitive primitive;
	private final byte[]    id;

	// Forbidden access
	@SuppressWarnings("unused")
	private IDImpl() {
		super();
		primitive = null;
		id        = null;
	}
	protected IDImpl(final byte[] _id) {
		super();
		primitive = null;
		id        = _id.clone();
	}
	protected IDImpl(final byte[] _id, Primitive _primitive) {
		super();
		primitive = _primitive;
		id        = _id.clone();
	}
	protected IDImpl(final short _id) {
		super();
		primitive = Primitive.SHORT;

		id = new byte[Primitive.SHORT.BYTES];
		id[0] = (byte) ( 0xFF & _id );
		id[1] = (byte) ( 0xFF & (_id >> 8) );
	}
	protected IDImpl(final int _id) {
		super();
		primitive = Primitive.INTEGER;

		id = new byte[Primitive.INTEGER.BYTES];
		id[0] = (byte) ( 0xFF & _id );
		id[1] = (byte) ( 0xFF & (_id >> 8) );
		id[2] = (byte) ( 0xFF & (_id >> 16) );
		id[3] = (byte) ( 0xFF & (_id >> 24) );
	}
	protected IDImpl(final long _id) {
		super();
		primitive = Primitive.LONG;

		id = new byte[Primitive.LONG.BYTES];
		id[0] = (byte) ( 0xFF & _id );
		id[1] = (byte) ( 0xFF & (_id >> 8) );
		id[2] = (byte) ( 0xFF & (_id >> 16) );
		id[3] = (byte) ( 0xFF & (_id >> 24) );
		id[4] = (byte) ( 0xFF & (_id >> 32) );
		id[5] = (byte) ( 0xFF & (_id >> 40) );
		id[6] = (byte) ( 0xFF & (_id >> 48) );
		id[7] = (byte) ( 0xFF & (_id >> 56) );
	}
	protected IDImpl(final String _id) {
		super();
		primitive = Primitive.STRING;
		id        = _id.getBytes(usedCharset).clone();
	}

	public final boolean 	isNumeric() {
		EnumSet<Primitive> numbers = EnumSet.of(Primitive.SHORT, Primitive.INTEGER, Primitive.LONG, Primitive.FLOAT, Primitive.DOUBLE);
		return numbers.contains(primitive);
	}
	public final boolean 	isAlphaNumeric() {
		EnumSet<Primitive> texts = EnumSet.of(Primitive.STRING);
		return texts.contains(primitive);
	}

	public final byte[] 	toBytes() {
		return id.clone();
	}
	public final short  	toShort() throws IllegalArgumentException {
		if(primitive != null)
			switch(primitive) {
			case SHORT:		return (short) ( (0xFF & id[0]) | ((0xFF & id[1]) << 8) );
			case INTEGER:	int toInt = toInt();
							if(toInt > Short.MIN_VALUE && toInt < Short.MAX_VALUE)
								return (short) toInt;
							break;
			case LONG:		long toLong = toLong();
							if(toLong > Short.MIN_VALUE && toLong < Short.MAX_VALUE)
								return (short) toLong;
							break;
			case STRING:	if(toString().matches("-?\\d*")) {
								long value = Long.parseLong( new String(id, usedCharset) );
								if(value > Short.MIN_VALUE && value < Short.MAX_VALUE)
									return (short) value;
							}
			default:
			}

		throw new IllegalArgumentException();
	}
	public final int    	toInt() throws IllegalArgumentException {
		if(primitive != null)
			switch(primitive) {
			case SHORT:		return (int) ( (0xFF & id[0]) | ((0xFF & id[1]) << 8) );
			case INTEGER:	return (int) ( (0xFF & id[0]) | ((0xFF & id[1]) << 8) | ((0xFF & id[2]) << 16) | ((0xFF & id[3]) << 24) );
			case LONG:		long toLong = toLong();
							if(toLong > Integer.MIN_VALUE && toLong < Integer.MAX_VALUE)
								return (int) toLong;
							break;
			case STRING:	if(toString().matches("-?\\d*")) {
								long value = Long.parseLong( new String(id, usedCharset) );
								if(value > Integer.MIN_VALUE && value < Integer.MAX_VALUE)
									return (int) value;
							}
			default:
			}

		throw new IllegalArgumentException();
	}
	public final long   	toLong() throws IllegalArgumentException {
		if(primitive != null)
			switch(primitive) {
			case SHORT:		return (long) ( (0xFF & id[0]) | ((0xFF & id[1]) << 8) );
			case INTEGER:	return (long) ( (0xFF & id[0]) | ((0xFF & id[1]) << 8) | ((0xFF & id[2]) << 16) | ((0xFF & id[3]) << 24) );
			case LONG:		return (long) ( (((long) id[0] & 0xff)) 
										  | (((long) id[1] & 0xff) << 8) 
										  | (((long) id[2] & 0xff) << 16) 
										  | (((long) id[3] & 0xff) << 24) 
										  | (((long) id[4] & 0xff) << 32) 
										  | (((long) id[5] & 0xff) << 40) 
										  | (((long) id[6] & 0xff) << 48) 
										  | (((long) id[7])        << 56) );
			case STRING:	if(toString().matches("-?\\d*"))
								return Long.parseLong( new String(id, usedCharset) );
			default:
			}

		throw new IllegalArgumentException();
	}
	@Override
	public final String 	toString() throws IllegalArgumentException {
		if(primitive != null)
			switch(primitive) {
			case STRING:	return new String(id, usedCharset);
			case SHORT:		return Short.toString( toShort() );
			case INTEGER:	return Integer.toString( toInt() );
			case LONG:		return Long.toString( toLong() );
			default:
			}

		return toHexString();
	}
	@Deprecated
	public final String 	toHexString() throws IllegalArgumentException {
		return Strings.getHex(id);
	}

	@Override
    public int 				hashCode() {
//        return toString().hashCode();
        return toHexString().hashCode();
    }
/*
	public long 			toHashTag() {
		// cf. Jenkins One At a Time Hash - Bret Mulvey
	    long hash = 0;
	    for(byte b : id.getBytes()) {   
	        hash += b;
	        hash += (hash << 10);
	        hash ^= (hash >> 6);    
	    }

	    hash += (hash << 3);
	    hash ^= (hash >> 11);
	    hash += (hash << 15);

	    hash  = (long) (hash % 1e16);
	    hash *= hash > 0 ? 1 : -1;

		return hash;
	}
*/

	@Override
	public int      		compareTo(final Object o) {
		if(Primitive.of(o.getClass()) != null)
			switch(Primitive.of(o.getClass())) {
			case SHORT:		return compareTo((short) o);
			case INTEGER:	return compareTo((int) o);
			case LONG:		return compareTo((long) o);
			case STRING:	return compareTo((String) o);
			default:			
			}

		if(o instanceof byte[])
			return compareTo((byte[]) o);
		if(o instanceof ID)
			return compareTo((ID) o);

		return Integer.MAX_VALUE;
	}
	protected int 			compareTo(final byte[] _bytes) {
		if(id.length < _bytes.length)
			return -1;
		else if(id.length > _bytes.length)
			return  1;

		for(int i = 0; i < id.length; ++i)
			if(id[i] < _bytes[i])
				return -1;
			else if(id[i] > _bytes[i])
				return  1;

		return 0;
	}
	protected int 			compareTo(final short _short) {
		try {
			return toShort() - _short;
		} catch(IllegalArgumentException e) {
			return Integer.MAX_VALUE;
		}
	}
	protected int 			compareTo(final int _int) {
		try {
			return toInt() - _int;
		} catch(IllegalArgumentException e) {
			return Integer.MAX_VALUE;
		}
	}
	protected int 			compareTo(final long _long) {
		try {
			return (int) ( toLong() - _long );
		} catch(IllegalArgumentException e) {
			return Integer.MAX_VALUE;
		}
	}
	protected int 			compareTo(final String _string) {
		return toString().compareTo(_string);
	}
	public int 				compareTo(final ID _id) {
		if(_id.isNumeric())
			return (int) (toLong() - _id.toLong());
		else if(_id.isAlphaNumeric())
			return toString().compareTo(_id.toString());

		return compareTo(_id.toBytes());
	}

	@Override
	public boolean 			equals(Object _obj) {
		if(this == _obj)
			return true;

		if(_obj == null)
			return false;

		if(getClass() != _obj.getClass()) {
			if(_obj instanceof byte[]) {
				byte[] array = (byte[]) _obj;
				return (compareTo(array) == 0);
			}
			
			if(Primitive.of(_obj.getClass()) != null)
				switch(Primitive.of(_obj.getClass())) {
				case SHORT:		return compareTo((short) _obj) == 0;
				case INTEGER:	return compareTo((int) _obj) == 0;
				case LONG:		return compareTo((long) _obj) == 0;
				case STRING:	return compareTo((String) _obj) == 0;
				default:			
				}
		}

		if(!(_obj instanceof ID))
			return false;

		return (compareTo((ID) _obj) == 0);
	}

}
