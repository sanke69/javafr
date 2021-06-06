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
 * @file     OperatingSystem.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.hosts;

public final class OperatingSystem {
	private static String OS = null;
	private static String ARCH = null;
	private static NameOS ENUM = null;

	static { getOsName(); }

	public static enum NameOS { UNKNOWN, WINDOWS, LINUX, OSX, SOLARIS, ANDROID; }
	public static enum ArchOS { UNKNOWN, _32_BITS, _64_BITS; }

	public static String getOsName() {
		if(OS == null) {
			OS   = System.getProperty("os.name");
			ARCH = System.getProperty("os.arch");
			if(isWindows()) {
				ENUM = NameOS.WINDOWS;
			} else if(isMac()) {
				ENUM = NameOS.OSX;
			} else if(isUnix()) {
				ENUM = NameOS.LINUX;
			} else if(isSolaris()) {
				ENUM = NameOS.SOLARIS;
			} else {
				ENUM = NameOS.UNKNOWN;
			}
		}
		return OS;
	}

	public static String getWorkingDirectory() {
		return System.getProperty("user.dir");
	}
	public static String getHomeDirectory() {
		return System.getProperty("user.home");
	}
	public static String getTemporaryFolder() {
		return System.getProperty("java.io.tmpdir");
	}

	public static NameOS getFamily() {
		return ENUM;
	}
	public static ArchOS getArch() {
        return (ARCH.indexOf("64") >= 0) ? ArchOS._64_BITS : ArchOS._32_BITS;
	}
	

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0 || OS.indexOf("Win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public static void main(String[] args) {
		System.out.println(OS);
		System.out.println(ARCH);

		if(isWindows()) {
			System.out.println("This is Windows");
		} else if(isMac()) {
			System.out.println("This is Mac");
		} else if(isUnix()) {
			System.out.println("This is Unix or Linux");
		} else if(isSolaris()) {
			System.out.println("This is Solaris");
		} else {
			System.out.println("Your OS is not support!!");
		}
	}

}