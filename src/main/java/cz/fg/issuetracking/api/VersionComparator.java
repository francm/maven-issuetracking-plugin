package cz.fg.issuetracking.api;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Can compare two VersionDescriptor objects.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 */
public class VersionComparator implements Comparator<VersionDescriptor> {

    @Override
	public int compare(VersionDescriptor alfa, VersionDescriptor beta) {
		if (alfa == null && beta != null) return -1;
		if (beta == null) throw new IllegalArgumentException("Version to be compared against (second parameter) cannot be null!");

		Iterator itAlfa = alfa.getIdentificators().iterator();
		Iterator itBeta = beta.getIdentificators().iterator();

		while(itAlfa.hasNext()) {
			Object alfaVersion = itAlfa.next();
			Object betaVersion = itBeta.hasNext() ? itBeta.next() : null;

			if(betaVersion == null) {
				if (alfaVersion instanceof Integer) betaVersion = 0;
					else return -1;
			}

			if(alfaVersion instanceof Integer) {
				if(betaVersion instanceof Integer) {
					if((Integer) alfaVersion > (Integer) betaVersion) return 1;
					else if((Integer) alfaVersion < (Integer) betaVersion) return -1;
				}
				else {
					return 1;
				}
			}
			else {
				if (betaVersion instanceof Integer) {
					return -1;
				} else {
					int result = ((String)alfaVersion).compareToIgnoreCase((String)betaVersion);
					if (result > 0) return 1;
					if (result < 0) return -1;
				}
			}
		}

		if(itBeta.hasNext()) return -1;

		return 0;
	}
}
