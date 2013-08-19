package cz.fg.issuetracking.api;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Can hold varying library version.
 * Can work with infinite number of subversion numbering.
 * Can work either with alfa or numberic version numbers.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 */
public class VersionDescriptor {
	private List identificators = new ArrayList();
	private static final String VERSION_DELIMITER = ".";
	private static final String VERSION_DELIMITERS = "._-";
	private static final String SNAPSHOT_POSTFIX = "SNAPSHOT";

    public VersionDescriptor(String fullVersion) {
		for(StringTokenizer st = new StringTokenizer(fullVersion, VERSION_DELIMITERS, false); st.hasMoreTokens();) {
			String version = st.nextToken();
			try {
                //ignore snapshot postfix
                if (!SNAPSHOT_POSTFIX.equals(version))   {
					// parse value
					int numberVersion = Integer.parseInt(version);
					identificators.add(new Integer(numberVersion));
				}
			}
			catch(NumberFormatException ex) {
				identificators.add(version);
			}
		}

		int lastIndex = identificators.size();
		for(int i = identificators.size() - 1; i >= 0; i--) {
			Object version = identificators.get(i);
			if (version instanceof Integer) {
				if (((Integer)version).intValue() != 0) {
	                lastIndex = i + 1;
					break;
				}
			}
		}

		for(int i = lastIndex; i < identificators.size();) {
			if (identificators.get(i) instanceof Integer) {
				identificators.remove(i);
			} else {
				i++;
			}
		}
	}

	public VersionDescriptor addNumericVersion(int version) {
		identificators.add(new Integer(version));
		return this;
	}

	public VersionDescriptor addAlfaNumericVersion(String version) {
		identificators.add(version);
		return this;
	}

	public List getIdentificators() {
		return identificators;
	}

	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		VersionDescriptor that = (VersionDescriptor)o;

		if(identificators != null ? !identificators.equals(that.identificators) : that.identificators != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (identificators != null ? identificators.hashCode() : 0);
	}

	public String toString() {
		StringBuffer fullVersion = new StringBuffer();
		for(int i = 0; i < identificators.size(); i++) {
			Object version = identificators.get(i);
			fullVersion.append(version);
			if(i < identificators.size() - 1) fullVersion.append(VERSION_DELIMITER);
		}

		return fullVersion.toString();
	}

}