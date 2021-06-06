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
 * @file     Version.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.jvm.properties.version;

import java.util.Locale;
import java.util.Optional;

import fr.java.lang.properties.Version;

public final class VersionImpl implements Version {
	private static final long serialVersionUID = -3054349171116917643L;

	private transient int							major;
	private transient int							minor;
	private transient Optional<Integer>				patch;
	private transient Optional<Integer>				build;

	private transient Optional<Version.BuildType>	buildType;
	private transient Optional<String>				codename;
	private transient Optional<String>				date;

	VersionImpl() {
		this(0,0);
	}
	public VersionImpl(final int _major, final int _minor) {
		this(_major, _minor, null, null, null, null);
	}
	public VersionImpl(final Integer _major, final Integer _minor, final Integer _patch, final Integer _build) {
		this(_major, _minor, _patch, _build, null, null);
	}
	public VersionImpl(final Integer _major, final Integer _minor, final Integer _patch, final Integer _build, final String _codename, final Version.BuildType _buildType) {
		super();
		major     = _major;
		minor     = _minor;
		patch     = Optional.ofNullable(_patch);
		build     = Optional.ofNullable(_build);

		codename  = Optional.ofNullable(_codename);
		buildType = Optional.ofNullable(_buildType);
		date      = Optional.empty();
	}

	public int getMajor() {
		return major;
	}
	public int getMinor() {
		return minor;
	}
	public int getPatch() {
		return patch.get().intValue();
	}
	public int getBuild() {
		return build.get().intValue();
	}

	public String getName() {
		return codename.get();
	}
	public Version.BuildType getStatus() {
		return buildType.get();
	}
	public String getDate() {
		return date.get();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("" + major + SEPARATOR + minor);
		if(patch.isPresent())
			sb.append("" + SEPARATOR + patch.get());
		if(build.isPresent())
			sb.append("-" + build.get());

		if(codename.isPresent())
			sb.append(" [" + codename.get() + "]");

		return sb.toString();
	}

	public boolean isCompatibleWith(final Version other) { // TODO:: 
		return true;
		/*
		if(other == null)
			return false;
		if(major != other.major)
			return false;
		if(minor > other.minor)
			return true;
		if(minor < other.minor)
			return false;
		if(build.isPresent())
			if(build.get() >= other.build.get())
				return true;
		return false;
		*/
	}
	public boolean isGreaterThan(final Version other) {
		if(other == null)
			return false;
		if(major > other.getMajor())
			return true;
		if(major < other.getMajor())
			return false;
		if(minor > other.getMinor())
			return true;
		if(minor < other.getMinor())
			return false;
		if(build.get() > other.getBuild())
			return true;
		return false;

	}
	public boolean isGreaterOrEqualTo(final Version other) {
		if(other == null)
			return false;
		if(major > other.getMajor())
			return true;
		if((major == other.getMajor()) && (minor > other.getMinor()))
			return true;
		if((major == other.getMajor()) && (minor == other.getMinor()) && (build.get() > other.getBuild()))
			return true;
		if((major == other.getMajor()) && (minor == other.getMinor()) && (build.get() == other.getBuild()) && codename.get().equalsIgnoreCase(other.getName()))
			return true;
		return false;
	}


	public boolean isEquivalentTo(final Version other) {
		if(other == null)
			return false;
		if(major != other.getMajor())
			return false;
		if(minor != other.getMinor())
			return false;
		if(build.get() >= other.getBuild())
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj)
			return true;

		if(!(obj instanceof VersionImpl))
			return false;

		VersionImpl other = (VersionImpl) obj;
		if((major != other.major) || (minor != other.minor))
			return false;

		if(build.isPresent()) {
			if(!other.build.isPresent())
				return false;
			if(build.get() != other.build.get())
				return false;
		}

		if(codename.isPresent()) {
			if(!other.build.isPresent())
				return false;
			if(!codename.get().equalsIgnoreCase(other.codename.get()))
				return false;
		}
		
		return true;
	}

	public int compareTo(final Version obj) {
		if(equals(obj))
			return 0;

		if(major != obj.getMajor())
			return major - obj.getMajor();
		if(minor != obj.getMinor())
			return minor - obj.getMinor();
		if(build.get() != obj.getBuild())
			return build.get() - obj.getBuild();
		return codename.get().toLowerCase(Locale.ENGLISH).compareTo(obj.getName().toLowerCase(Locale.ENGLISH));
	}
	public int compareTo2(VersionImpl v) {
		if(v.getMajor() == major)
			if(v.getMinor() == minor)
				if(compareStatus(v.getStatus(), buildType.get()) == 0)
					// if(v.getPatchVersion()==patchVersion)
					return v.getPatch() - patch.get();
				// return compareStatus(v.getStatus(),statusString);
				else
					return compareStatus(v.getStatus(), buildType.get());
			// else return v.getPatchVersion()-patchVersion;
			else
				return v.getMinor() - minor;
		else
			return v.getMajor() - major;
	}
	
	private int compareStatus(BuildType s1, BuildType s2) {
		if(s1 == null || s2 == null)
			return -1;
		return s1.value() - s2.value();
	}

/*
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeUTF(toString());
	}

	private void readObject(final ObjectInputStream in) throws IOException {
		valueOf(in.readUTF());
	}
*/
}