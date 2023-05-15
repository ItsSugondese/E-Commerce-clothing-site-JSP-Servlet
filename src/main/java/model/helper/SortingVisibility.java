package model.helper;

import java.util.List;

/**
 * This class will only be used in "public/homepage.jsp". This is responsible
 * for showing which item to select in <select> element
 *
 */

public class SortingVisibility {

	// index is for which item to select/ making visibility true, size is for how
	// many <options> element
	// <select> elements has and bool is the list of boolean send through
	// "public/homepage.jsp".

	// last is the method as a whole in which this method will only set bool list of
	// given index to true ad will set remaining elements to false
	public static List<Boolean> sort(int index, int size, List<Boolean> bool) {
		for (int i = 0; i < size; i++) {

			if (i == (index - 1)) {
				bool.set(index - 1, true);
			} else {
				bool.set(i, false);
			}
		}

		return bool;
	}
}
