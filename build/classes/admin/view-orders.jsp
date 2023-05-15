<%@page import="model.User"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="model.Dao.CartDao"%>
<%@page import="model.Product"%>
<%@page import="model.Cart"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
CartDao dao = new CartDao(ConnectionProvider.getConnection());
List<Cart> carts = dao.getCartDetails();
float totalPrice = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="admin-css/order.css" />



</head>
<body>

<%@include file="admin_navbar.jsp" %> 

<center>
<dialog id="remove" class="centered-element">
		<div id="alerting">
		<span id="message"></span>
			</div>
		</dialog>


<%
		if (!carts.isEmpty()) {
%>
<div class="cart-container">
        <div class="cart-items-container">
        
        <%
			for (Cart cart : carts) {
				

		%>
          <div class="cart-item">
            <div class="cart-item-image-container">
              <img src="<%=getServletContext().getInitParameter("productPicUrlFromLoggedUserPage")
		+ (cart.getProduct()).getProductPicture()%>" alt="[product-name]" class="cart-item-image">
            </div>
            <div class="cart-item-details-container">
            <div class="cart-item-user">Ordered by :  <%=(cart.getUser()).getUsername()%></div>
              <div class="cart-item-name"><%=(cart.getProduct()).getProductName()%></div>
              <div class="cart-item-brand"><%=(cart.getProduct()).getBrand()%></div>
              
              <div class="cart-item-quantity">Qty:  <%=cart.getNoOfOrder()%></div>
              <div class="cart-item-actions">
                <button class="cart-item-edit editBtn" onclick="deliverProduct(this.value)"
						value="<%=cart.getCartId()%>">Deliver</button>
                
              </div>
            </div>
            <div class="cart-item-total">Rs. <%= cart.getNoOfOrder() * Integer.parseInt(((((cart.getProduct()).getProductPrice()).split(". "))[1]).trim()) %></div>
          </div>
         
         <%
			}
         %>
          
        </div>
        
      </div>
<%
			
		}
%>




</center>







	
	<script type="text/javascript">
	function deliverProduct(value){
		
		
		let form = new FormData();
		form.append("cartId", value);
		var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
		var url = "deliveringCartServlet"; // URL of the servlet to send data to
		// create a new FormData object
		// add form data to the FormData object

		
		// set up the request
		xhr.open("DELETE", url, true);
		xhr.onreadystatechange = function() {
			// handle the response from the server
			if (xhr.readyState == 4 && xhr.status == 200) {
				console.log(xhr.responseText);
				if (xhr.responseText.trim() === 'done') {
					document.getElementById("message").innerHTML = "Product Delivered Successfully";
					window.remove.show();
					
					setTimeout(function() {
						window.location = "view-orders.jsp";
                    }, 1000);
					
					
				} else {
					document.getElementById("message").innerHTML = xhr.responseText;
				}
			}
		};
		// send the request
		xhr.send(form);
		
	}
	</script>
</body>
</html>