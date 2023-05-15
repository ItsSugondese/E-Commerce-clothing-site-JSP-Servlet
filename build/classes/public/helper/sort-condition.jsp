<%@page import="model.helper.SortingVisibility"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.helper.SelectedSorter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
SelectedSorter sorter = (SelectedSorter) (request.getAttribute("sort"));
String displaySub = "";
List<Boolean> sortByIndex = new ArrayList(Arrays.asList(true, false, false, false, false));
List<Boolean> sortByPriceIndex = new ArrayList(Arrays.asList(true, false, false));
List<Boolean> sortByTypeIndex = new ArrayList(Arrays.asList(true, false, false));
List<Boolean> sortByCategoryIndex = new ArrayList(Arrays.asList(true, false, false, false, false, false, false));
List<Boolean> sortByRatingIndex = new ArrayList(Arrays.asList(true, false, false));

if (sorter != null) {
	displaySub = sorter.getSubSelected();
	if (displaySub.equals("Price")) {
		sortByIndex = SortingVisibility.sort(2, sortByIndex.size(), sortByIndex);

		if ((sorter.getSelected()).equals("High to Low")) {
	sortByPriceIndex = SortingVisibility.sort(3, sortByPriceIndex.size(), sortByPriceIndex);

		} else if ((sorter.getSelected()).equals("Low to High")) {
	sortByPriceIndex = SortingVisibility.sort(2, sortByPriceIndex.size(), sortByPriceIndex);

		} else {
	sortByPriceIndex = SortingVisibility.sort(1, sortByPriceIndex.size(), sortByPriceIndex);

		}
	} else if (displaySub.equals("Type")) {
		sortByIndex = SortingVisibility.sort(3, sortByIndex.size(), sortByIndex);

		if ((sorter.getSelected()).equals("Menswear")) {
	sortByTypeIndex = SortingVisibility.sort(2, sortByTypeIndex.size(), sortByTypeIndex);

		} else if ((sorter.getSelected()).equals("Womenswear")) {
	sortByTypeIndex = SortingVisibility.sort(3, sortByTypeIndex.size(), sortByTypeIndex);

		} else {
	sortByTypeIndex = SortingVisibility.sort(1, sortByTypeIndex.size(), sortByTypeIndex);

		}
	} else if (displaySub.equals("None")) {
		sortByIndex = SortingVisibility.sort(1, sortByIndex.size(), sortByIndex);

	} else if (displaySub.equals("Rating")) {
		sortByIndex = SortingVisibility.sort(5, sortByIndex.size(), sortByIndex);

		if ((sorter.getSelected()).equals("High to Low")) {
	sortByRatingIndex = SortingVisibility.sort(3, sortByRatingIndex.size(), sortByRatingIndex);

		} else if ((sorter.getSelected()).equals("Low to High")) {
	sortByRatingIndex = SortingVisibility.sort(2, sortByRatingIndex.size(), sortByRatingIndex);

		} else {
	sortByRatingIndex = SortingVisibility.sort(1, sortByRatingIndex.size(), sortByRatingIndex);

		}
	} else {
		sortByIndex = SortingVisibility.sort(4, sortByIndex.size(), sortByIndex);

		if ((sorter.getSelected()).equals("Tops")) {
	sortByCategoryIndex = SortingVisibility.sort(2, 7, sortByCategoryIndex);
		} else if ((sorter.getSelected()).equals("Bottoms")) {
	sortByCategoryIndex = SortingVisibility.sort(3, 7, sortByCategoryIndex);
		} else if ((sorter.getSelected()).equals("Dresses")) {
	sortByCategoryIndex = SortingVisibility.sort(4, 7, sortByCategoryIndex);
		} else if ((sorter.getSelected()).equals("Underwear")) {
	sortByCategoryIndex = SortingVisibility.sort(5, 7, sortByCategoryIndex);
		} else if ((sorter.getSelected()).equals("Footwear")) {
	sortByCategoryIndex = SortingVisibility.sort(6, 7, sortByCategoryIndex);
		} else if ((sorter.getSelected()).equals("Accessories")) {
	sortByCategoryIndex = SortingVisibility.sort(7, 7, sortByCategoryIndex);
		} else {
	sortByCategoryIndex = SortingVisibility.sort(1, 7, sortByCategoryIndex);
		}
	}
}
%>