<%@page import="java.io.File"%>
<%@page import="model.helper.ProductRatingHelper"%>
<%@page import="model.helper.SortingVisibility"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.helper.SelectedSorter"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="model.Dao.ProductDao"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="public-css/homepage.css">
<link rel="stylesheet" href="public-css/product.css">






</head>
<body>

	<!--  for webpage navbar -->
	<%@include file="homepage-navbar.jsp"%>
	
	<!-- Contains conditions for displaying sorting dropbox -->
	<%@include file="helper/sort-condition.jsp"%>


	

	<div id="sorter">
		Sort By : <select name="sort-by" id="drop" onchange="choosingSort()">
			<option <%if (sortByIndex.get(0)) {%> selected <%}%>>None</option>
			<option <%if (sortByIndex.get(1)) {%> selected <%}%>>Price</option>
			<option <%if (sortByIndex.get(2)) {%> selected <%}%>>Type</option>
			<option <%if (sortByIndex.get(3)) {%> selected <%}%>>Category</option>
			<option <%if (sortByIndex.get(4)) {%> selected <%}%>>Rating</option>
		</select> <select name="sort-by-type" id="type-drop"
			onchange="choosingSortType()"
			style="display: <%if (!displaySub.equals("Type"))%> none">
			<option disabled="disabled" <%if (sortByTypeIndex.get(0)) {%>
				selected <%}%>>Pick a type!</option>
			<option <%if (sortByTypeIndex.get(1)) {%> selected <%}%>>Menswear</option>
			<option <%if (sortByTypeIndex.get(2)) {%> selected <%}%>>Womenswear</option>
		</select> <select name="sort-by-category" id="category-drop"
			onchange="choosingSortCategory()"
			style="display: <%if (!displaySub.equals("Category"))%> none">
			<option disabled="disabled" <%if (sortByCategoryIndex.get(0)) {%>
				selected <%}%>>Pick a category!</option>
			<option <%if (sortByCategoryIndex.get(1)) {%> selected <%}%>>Tops</option>
			<option <%if (sortByCategoryIndex.get(2)) {%> selected <%}%>>Bottoms</option>
			<option <%if (sortByCategoryIndex.get(3)) {%> selected <%}%>>Dresses</option>
			<option <%if (sortByCategoryIndex.get(4)) {%> selected <%}%>>Underwear</option>
			<option <%if (sortByCategoryIndex.get(5)) {%> selected <%}%>>Footwear</option>
			<option <%if (sortByCategoryIndex.get(6)) {%> selected <%}%>>Accessories</option>
		</select> <select name="sort-by-price" id="price-drop"
			onchange="choosingSortPrice()"
			style="display: <%if (!displaySub.equals("Price"))%> none">
			<option disabled="disabled" <%if (sortByPriceIndex.get(0)) {%>
				selected <%}%>>Pick price sort!</option>
			<option <%if (sortByPriceIndex.get(1)) {%> selected <%}%>>Low
				to High</option>
			<option <%if (sortByPriceIndex.get(2)) {%> selected <%}%>>High
				to Low</option>
		</select> <select name="sort-by-rating" id="rating-drop"
			onchange="choosingSortRating()"
			style="display: <%if (!displaySub.equals("Rating"))%> none">
			<option disabled="disabled" <%if (sortByRatingIndex.get(0)) {%>
				selected <%}%>>Pick price sort!</option>
			<option <%if (sortByRatingIndex.get(1)) {%> selected <%}%>>Low
				to High</option>
			<option <%if (sortByRatingIndex.get(2)) {%> selected <%}%>>High
				to Low</option>
		</select>
	</div>

<!-- for displaying products -->
	<%@include file="helper/all-products.jsp"%>




	<script src="public-js/sorting.js"> </script>
	<script src="public-js/search-function.js"></script> 


	<script>
	// will run when user click on a product
		function viewProduct(value) {
			window.location.href = "productDetailServlet?productCode=" + value;
		}
	</script>
</body>
</html>