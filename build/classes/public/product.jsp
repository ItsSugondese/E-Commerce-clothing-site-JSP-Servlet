<%@page import="model.helper.ErrorHandler"%>
<%@page import="model.Dao.CartDao"%>
<%@page import="model.User"%>
<%@page import="model.helper.ProductRatingHelper"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="model.Dao.ProductDao"%>
<%@page import="model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
String productCode = (String) request.getAttribute("productCode");
ProductDao dao = new ProductDao(ConnectionProvider.getConnection());
Product product = null;
User user = (User) ((request.getSession()).getAttribute("loggedUser"));
CartDao cartDao = new CartDao(ConnectionProvider.getConnection());
boolean existInCart = false;
if (user != null)
	existInCart = cartDao.checkIfInCart(productCode, user.getEmail());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="public-css/product-page.css" />
<style>





.dialog {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border: 1px solid black;
}
</style>
</head>
<body bgcolor="black">
	
	<%@include file="homepage-navbar.jsp"%>
<dialog id="dialog"> <span id="message"></span> <br>
	<button id="login-btn" onclick="loginBtnAction()" style="display: none">Login</button>
	<button id="cancel-btn" onclick="cancelBtnAction()"
		style="display: none">Cancel</button>
	<button id="okey-btn" onclick="okeyBtnAction()" style="display: none">Okey</button>

	</dialog>
	
	
	
		<%
	if (!productCode.isBlank() && dao.checkProduct(productCode)) {
		product = dao.getSingleProduct(productCode);
		String ratingImgFile = ProductRatingHelper.getRatingImage(product);
	%>
	
	 <div class="product-container">
      <div class="product-image">
        <img src="<%=getServletContext().getInitParameter("productPicUrlFromPublicProductPage") 
					+ product.getProductPicture()%>" alt="Product Image">
      </div>
      <div class="product-details">
        <h1 class="product-name"><%=product.getProductName()%></h1>
        <p class="product-brand"><%= product.getBrand() %></p>
        <div class="product-rating">
          <span class="rating-value"><%=product.getRating() %></span>
          <span class="rating-max">/5</span>
          <img src="<%=getServletContext().getInitParameter("ratingPicUrlFromPublicProductPage")
					+ ratingImgFile%>" alt="star" class="star-icon">
     
        </div>
        <p class="product-stock">In Stock : <%=product.getInStock() %></p>
        <p class="product-price"><%= product.getProductPrice() %></p>
        <div class="add-to-cart">
          <label for="quantity">Quantity:</label>
          <form id="addingInCart">
						<input type="number" id="quantity"  name="quantity" min="1" max="<%= product.getInStock() %>" required> <button class="add-to-cart-btn"  <%if (product.getInStock() <=0) {%> disabled
						<%}%>>Add to cart</button>  <%
						if (product.getInStock() <=0) {
							out.println("Out of stock");
						}
						%>
						<input type="hidden" name="productCode" value="<%=productCode %>">
					</form>
          
        </div>
      </div>
      
    </div>
	
	<%
	} else {
		%>
		<h1>
			<%
			if (productCode.isBlank())
				out.print("Product code Is blank");
			else
				out.print("Product with that product code doesn't exitst");
			%>
			<br> <a href="homepage.jsp">Go to homepage</a>
		</h1>
		<%
		}
		%>

		<script>
		 document.getElementById('addingInCart').addEventListener('submit', function (event) {
			 event.preventDefault();
             let form = new FormData(this);
				var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
				var url = "addingCartServlet"; // URL of the servlet to send data to
				// create a new FormData object
				// add form data to the FormData object

				// set up the request
				xhr.open("POST", url, true);
				xhr.onreadystatechange = function() {
					// handle the response from the server
					if (xhr.readyState == 4 && xhr.status == 200) {
						console.log(xhr.responseText);
						if(xhr.responseText.trim() === "hasn't logged in"){
							document.getElementById("message").innerHTML = "Items can't be added to cart without logging in";
							document.getElementById("login-btn").style.display = "";
							document.getElementById("cancel-btn").style.display = "";
							document.getElementById("okey-btn").style.display = "none";
								window.dialog.show();
						}
						else if (xhr.responseText.trim() === 'done') {
							document.getElementById("message").innerHTML = "Successfully added to cart";
							document.getElementById("login-btn").style.display = "none";
							document.getElementById("cancel-btn").style.display = "none";
							document.getElementById("okey-btn").style.display = "";
								window.dialog.show();

						} else if(xhr.responseText.trim() === 'only users can buy items not admin'){
							window.location.href = "../error_page.jsp";
						}
						
						else {
							document.getElementById("message").innerHTML = "Failed to add in cart due to internal server error";
							document.getElementById("login-btn").style.display = "none";
							document.getElementById("cancel-btn").style.display = "none";
							document.getElementById("okey-btn").style.display = "";
								window.dialog.show();
						}
					}
				};
				// send the request
				xhr.send(form);
			});
		</script>

		<script>
		function loginBtnAction(){
			window.location.href = "../login.jsp?public/productDetailServlet?productCode=<%=productCode%>";
			}

			function cancelBtnAction() {
				window.dialog.close();
			}

			function okeyBtnAction() {
				window.dialog.close();
				window.location.href = "productDetailServlet?productCode=<%=productCode%>";
			}
		</script>
</body>
</html>