<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="model.Dao.CartDao"%>
<%@page import="model.Product"%>
<%@page import="model.Cart"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
CartDao dao = new CartDao(ConnectionProvider.getConnection());
List<Cart> carts = dao.getCartDetailsByEmail(((User) session.getAttribute("loggedUser")).getEmail());
float totalPrice = 0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="logged-css/cart.css" />






</head>
<body>

	<%@include file="logged-user-navbar.jsp"%>
<br>

	<center>

		<dialog id="edit">
		<form id="edit-form">
		<h1 id="update-message"></h1>
	Available In-Stock : <span id="in-stock-message"></span> <br>
	Maximum you can purchase : <span id="max-purchase"></span> <br>
		Quantity : 	<input type="number" id="edit-quantity" name="quantity"/> 
			<button id="editBtn">Edit</button> <br>
			
			
			<input type="hidden" id="cartId" name="cartId" />
		</form>
		
		<button id="cancelBtn" onclick="cancelBtnAction()">Cancel</button>
			<button id="okeyBtn" onclick="okeyBtnAction()" style="display: none">Okey</button>
		</dialog>
		
		<dialog id="remove" class="centered-element">
		<div id="alerting">
			Are you sure you want to remove this item? <br>
			<button onclick="removeItem()">Yes</button>
			<button onclick="popDialog()">No</button>
		</div>
		
		<h3 id="remove-message"></h3>
		</dialog>

</center>


<center>

	<%
		if (!carts.isEmpty()) {
%>
<div class="CartContainer">
   	   <div class="Header">
   	   	<h3 class="Heading">Shopping Cart</h3>
   	   </div>
<%
			for (Cart cart : carts) {
				

		%>
   	   <div class="Cart-Items">
   	   	  <div class="image-box">
   	   	  	<img src="<%=getServletContext().getInitParameter("productPicUrlFromLoggedUserPage")
		+ (cart.getProduct()).getProductPicture()%>" style={{ height="120px" }} />
   	   	  </div>
   	   	  <div class="about">
   	   	  	<h1 class="title"><span class="toClick"
							onclick="redirectingToPage('<%=(cart.getProduct()).getProductCode()%>')"> <%=(cart.getProduct()).getProductName()%> </span></h1>
   	   	  	<h3 class="subtitle"><%=(cart.getProduct()).getBrand() %></h3>
   	   	  	
   	   	  </div>
   	   	  <div class="counter">
   	   	  	<div class="count">Quantity <br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=cart.getNoOfOrder()%> </div>
   	   	  </div>
   	   	  <div class="prices">
   	   	  	<div class="amount">Rs. <%= cart.getNoOfOrder() * Integer.parseInt(((((cart.getProduct()).getProductPrice()).split(". "))[1]).trim()) %></div>
   	   	  	<div class="editBtn" onclick='editClicked(this.value, 0, <%=cart.getNoOfOrder() + (cart.getProduct()).getInStock() %>,  "<%= cart.getCartId() %>", <%= (cart.getProduct()).getInStock() %>)' value="<%=cart.getNoOfOrder()%>">Edit</div>
   	   	  	<div class="removeBtn" onclick='removeProduct("<%=cart.getCartId()%>")' value="<%=cart.getCartId()%>">Remove</div>
   	   	  </div>
   	   </div>
<%
totalPrice += cart.getNoOfOrder() * Integer.parseInt(((((cart.getProduct()).getProductPrice()).split(". "))[1]).trim());
			}


		}
%>
   	 
   	 <hr> 
   	 <div class="checkout">
   	 <div class="total">
   	 	<div>
   	 		<div class="Subtotal">Sub-Total</div>
   	 		<div class="items"><%=dao.noOfItemsInCart(((User) session.getAttribute("loggedUser")).getEmail()) %> items</div>
   	 	</div>
   	 	<div class="total-amount">Rs. <%=totalPrice %></div>
   	 </div>
   </div>

</center>













	<script>
		function redirectingToPage(value) {
			window.location.href = "../public/productDetailServlet?productCode="
					+ value;
		}

		function editClicked(value, min, max, getCode, inStock) {
			(document.getElementById("edit-quantity")).value = value;
			(document.getElementById("edit-quantity")).min = min;
			(document.getElementById("edit-quantity")).max= max;
			
			(document.getElementById("in-stock-message")).innerHTML = inStock;
			(document.getElementById("max-purchase")).innerHTML = max;
			
			(document.getElementById("cartId")).value = getCode;
			
			
			window.edit.show();
		}
	
		/*
		function editClicked(value, min, max) {
			console.log("I'm here")
			(document.getElementById("edit-quantity")).value = value;
			(document.getElementById("edit-quantity")).min = min;
			(document.getElementById("edit-quantity")).max= max;
			
			window.edit.show();
		}
		*/
		
	</script>
	
	<script>
	 document.getElementById('edit-form').addEventListener('submit', function (event) {
         event.preventDefault();
         let form = new FormData(this);
         //send register servlet:
        	 
         
         var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
         var url = "editingCartServlet"; // URL of the servlet to send data to
         
         // set up the request
         xhr.open("POST", url, true);
         xhr.onreadystatechange = function () {
             // handle the response from the server
             if (xhr.readyState == 4 && xhr.status == 200) {
                 console.log(xhr.responseText);
                 if (xhr.responseText.trim() === 'done') {
                		document.getElementById("edit-quantity").disabled = true;
                		document.getElementById("editBtn").disabled = true;
						document.getElementById("cancelBtn").style.display = "none";
						document.getElementById("okeyBtn").style.display = "";
                     document.getElementById("update-messages").innerHTML = 'Successfully Updated';
                     
                 } else {
                     document.getElementById("messages").innerHTML = xhr.responseText;
                 }
             }
         };
         // send the request
         xhr.send(form);
     });
	</script>
	
	<script>
	function cancelBtnAction(){
		document.getElementById("edit-quantity").disabled = false;
		document.getElementById("editBtn").disabled = false;
		document.getElementById("cancelBtn").style.display = "";
		document.getElementById("okeyBtn").style.display = "none";
		
		window.edit.close();
	}
	
	function okeyBtnAction(){
		document.getElementById("edit-quantity").disabled = false;
		document.getElementById("editBtn").disabled = false;
		document.getElementById("cancelBtn").style.display = "none";
		document.getElementById("okeyBtn").style.display = "none";
		window.edit.close();
		window.location.href = "cart.jsp";
	}
	</script>
	
	<script>
	
	
	function removeProduct(value){
		console.log(value);
		window.remove.show();
		localStorage.setItem("cartId", value);
	}
	
	function popDialog(){
		window.remove.close();
		localStorage.removeItem("productCode");
		document.getElementById("remove-message").innerHTML = '';
	}
	
	function removeItem(){
		let form = new FormData();
		form.append("cartId", localStorage.getItem("cartId"));
		var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
		var url = "removingCartServlet"; // URL of the servlet to send data to
		// create a new FormData object
		// add form data to the FormData object

		
		// set up the request
		xhr.open("DELETE", url, true);
		xhr.onreadystatechange = function() {
			// handle the response from the server
			if (xhr.readyState == 4 && xhr.status == 200) {
				console.log(xhr.responseText);
				if (xhr.responseText.trim() === 'done') {
					(document.getElementById("alerting")).remove();
					document.getElementById("remove-message").innerHTML = 'Product removed Successfully';
					localStorage.removeItem("productCode");
					setTimeout(function() {
						window.location = "cart.jsp";
                    }, 1000);
					
					
				} else {
					document.getElementById("messages").innerHTML = xhr.responseText;
				}
			}
		};
		// send the request
		xhr.send(form);
	}
	</script>
</body>
</html>