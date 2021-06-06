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
 * @file     XFormatter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import fr.java.utils.Strings;

public class LogFormatter extends Formatter {
	private static final String fieldSeparator = " - ";
	private static final String lineSeparator  = "\n";

	private static final boolean debug  = true;

	private static final boolean useTimestamp  = true;
	private static final boolean useLocalized  = false;

	@Override
	public synchronized String format(LogRecord record) {
        Instant instant   = Instant.ofEpochMilli(record.getMillis());
        String  timestamp = "[" + Strings.padLeftZero(instant.minusMillis(LogInstance.start.toEpochMilli()).toEpochMilli() + " ms]", 12);
        String  daytime   = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format( LocalDateTime.ofInstant(instant, ZoneOffset.UTC) );

		StringBuilder sb = new StringBuilder();

		// Timestamp
		sb.append(useTimestamp ? timestamp : daytime);
		sb.append(fieldSeparator);

		// Level
		String level = Strings.center(useLocalized ? record.getLevel().getLocalizedName() : record.getLevel().getName(), 7);
		sb.append(level);
//		sb.append(" (" + record.getLevel().intValue() + ")");
		sb.append(fieldSeparator);

		String message = formatMessage(record);
		sb.append(message);
		

		// Class name
		if(debug) {
			sb.append(fieldSeparator);

			if(record.getSourceClassName() != null) {
				String className  = record.getSourceClassName();
				String methodName = record.getSourceMethodName();
				int    lineNumber = -1;
				try {
					lineNumber = Integer.valueOf(methodName.substring(methodName.indexOf(":") + 1));
					methodName = methodName.substring(0, methodName.indexOf(":"));
				} catch(Exception e) { }
				
				sb.append("(" + className + ".java:" + lineNumber + ")");
			} else {
				sb.append(record.getLoggerName());
			}
		}

		sb.append(lineSeparator);
		if(record.getThrown() != null)
			try {
				StringWriter sw = new StringWriter();
				PrintWriter  pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			} catch(Exception ex) { }

		return sb.toString();
	}

}