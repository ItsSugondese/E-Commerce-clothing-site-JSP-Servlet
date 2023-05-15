<%@page import="model.helper.SelectedSorter"%>
<%@page import="model.helper.ProductRatingHelper"%>
<%@page import="java.util.List"%>
<%@page import="model.Dao.ProductDao"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
ProductDao productDao = new ProductDao(ConnectionProvider.getConnection());

SelectedSorter productSorter = (SelectedSorter) (request.getAttribute("sort"));
List<Product> products = null;

if (productSorter == null) {
	products = productDao.getProducts();
} else {
	products = productSorter.getProducts();
}
%>
    <div id="container" class="container">
		<%
		for (Product product : products) {
			String ratingImgFile = ProductRatingHelper.getRatingImage(product);
		%>
		<div class="product">

			<div id="productImage" class="productImage">
				<img
					src="<%=getServletContext().getInitParameter("productPicUrlFromHomepage") + product.getProductPicture()%>"
					height="120">
			</div>



			<div id="productName" class="productName"
				onclick="viewProduct('<%=product.getProductCode()%>')">
				<%=product.getProductName()%>
			</div>

			<div class="brand">
				<%=product.getBrand()%></div>
			<div class="price">
				<%=product.getProductPrice()%></div>
			<div class="rating"><img
					src="<%=getServletContext().getInitParameter("ratingPicUrlFromHomepage") + ratingImgFile%>">
					<span class="rating-number">(<%= product.getRating() %>)</span>
					</div>
			



		</div>
		<%
		}
		%>
	</div>