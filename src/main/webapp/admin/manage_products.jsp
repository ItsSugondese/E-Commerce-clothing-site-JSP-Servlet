<%@page import="model.Dao.ProductDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="admin-css/manage-product.css" />
<link rel="stylesheet" href="admin-css/product-form.css" />
<style type="text/css">

#cancelingBtn {
  background-color: red;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right : 15px;
}

#cancelingBtn:hover {
  background-color: red;
}
</style>
</head>
<body>

	<%@include file="admin_navbar.jsp"%>

<center>
	<dialog id="dialog" class="centered-element">
	<h3 id="messages"></h3>


	<form id="product-edit-form" method="post" action="editProductServlet"
		enctype="multipart/form-data" >
		product code : <input type="text" id="productCode" name="productCode"
			disabled /> <br> Enter product name : <input type="text"
			id="productName" name="productName" /> <br> Enter product price
		: <input type="number" id="productPrice" name="productPrice" /> <br>
		Enter brand name : <input type='text' id="brand" name="brand" /> <br>
		Enter product category : <select id="category" name="category"
			id="drop">
			<option disabled="disabled" selected>Pick a category!</option>
			<option>Tops</option>
			<option>Bottoms</option>
			<option>Dresses</option>
			<option>Underwear</option>
			<option>Footwear</option>
			<option>Accessories</option>
		</select> <span id="name-error" style="display: none; color: red;">Please
			enter valid product category</span> <br> Cloth Type : <input
			type="radio" id="menswear-type" name="type" value="Menswear" required>
		  <label for="MensWear">Menswear</label>   <input type="radio"
			id="womenswear-type" name="type" value="Womenswear" required>
		  <label for="Womenswear">Womenswear</label><br> <br>
		Quantity: <input type="number" id="quantity" name="quantity" required />
		<br> Rating: <input type="number" id="rating" name="rating"
			max="5" min="0" step="0.1" required /> <br> Change Product
		Picture : <input type="file" id="myFile" name="productPic"
			accept="image/*" value="Change Product Pic"> <br> <br>
		<input type="submit" value="update" style="float: right"/>


	</form>






	<button onclick="cancelling()" id="cancelingBtn" style="float: right">Cancel</button>
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

	<%
	Connection con = ConnectionProvider.getConnection();
	ProductDao dao = new ProductDao(ConnectionProvider.getConnection());
	List<Product> products = dao.getProducts();
	%>
	<center>
		<div class="cart-container">
			<div class="cart-items-container">
				<%
				for (Product product : products) {
					int price = Integer.parseInt((((product.getProductPrice()).split(". "))[1]).trim());
					String sendValueForEdit = product.getProductCode() + "\n" + product.getProductName() + "\n" + price + "\n"
					+ product.getBrand() + "\n" + product.getCategory() + "\n" + product.getType() + "\n"
					+ product.getProductPicture() + "\n" + product.getInStock() + "\n" + product.getRating();
				%>


				<div class="cart-item">
					<div class="cart-item-image-container">
						<img
							src="<%=getServletContext().getInitParameter("productPicUrlFromAdminViewPage") + product.getProductPicture()%>"
							alt="[product-name]" class="cart-item-image">
					</div>
					<div class="cart-item-details-container">
						<div class="cart-item-name">
							<%=product.getProductName()%></div>
						<div class="cart-item-brand">
							Brand :
							<%=product.getBrand()%></div>
						<div class="cart-item-category">
							Category :
							<%=product.getCategory()%>
						</div>
						<div class="cart-item-type">
							Type :
							<%=product.getType()%>
						</div>
						<div class="cart-item-quantity">
							Quantity :
							<%=product.getInStock()%></div>
						<div class="cart-item-actions">
							<button class="cart-item-edit editBtn"
								onclick="editClicked(this.value)" value="<%=sendValueForEdit%>">Edit</button>
							<button class="cart-item-remove removeBtn"
								onclick="removeProduct(this.value)"
								value="<%=product.getProductCode()%>">Remove</button>
						</div>
					</div>
					<div class="cart-item-total"><%=product.getProductPrice()%></div>
				</div>
				<%
				}
				%>

			</div>

		</div>
	</center>

	



	<script>
		let tempForm;
		var items = document.getElementsByClassName("editBtn");
		var fillFormValue;
	
		function editClicked(value) {
			console.log(value);
			var li = value;

			for (i = 0, len = items.length; i < len; i++) {
				if (items[i].value == li) {
					var whole = li.split("\n")
					fillFormValue = {
						productCode : whole[0],
						productName : whole[1],
						productPrice : whole[2],
						brand : whole[3],
						category : whole[4],
						type : whole[5],
						picture : whole[6],
						quantity : whole[7],
						rating : whole[8]
					}

					break;
				}
			}
			console.log("quantity is : " + fillFormValue.quantity)
			console.log("rating is : " + fillFormValue.rating)
			document.getElementById("productCode").value = fillFormValue.productCode;
			document.getElementById("productName").value = fillFormValue.productName;
			document.getElementById("productPrice").value = fillFormValue.productPrice;
			document.getElementById("brand").value = fillFormValue.brand;
			document.getElementById("category").value = fillFormValue.category;
			document.getElementById("quantity").value = fillFormValue.quantity;
			document.getElementById("rating").value = fillFormValue.rating;

			if(whole[5] == "MensWear"){
				document.getElementById("menswear-type").checked = true;
			}else{
				document.getElementById("womenswear-type").checked = true;
			}
			
			document.getElementById("productCode").removeAttribute("disabled");
			tempForm = new FormData(document
					.getElementById('product-edit-form'))
			
			document.getElementById("productCode").setAttribute("disabled", "");
			
			window.dialog.show();
		}

		function cancelling() {
			window.dialog.close();
		}
	</script>

	<script>
		document.getElementById('product-edit-form').addEventListener(
				'submit',
				function(event) {
					event.preventDefault();

					//send register servlet:

					document.getElementById("productCode").removeAttribute(
							"disabled");
					
					
					let form = new FormData(document
							.getElementById('product-edit-form'));
					document.getElementById("productCode").setAttribute(
							"disabled", "");

					
					 const fileInput = document.getElementById("myFile");
					  
					  console.log(fileInput.files.length);
					if(JSON.stringify([...tempForm.entries()]) === JSON.stringify([...form.entries()]) &&
							(document.getElementById("myFile")).files.length == 0){
						document.getElementById("messages").innerHTML = "You haven't updated anything";
					}else{
						//send register servlet:
						var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
						var url = "editProductServlet"; // URL of the servlet to send data to
						// create a new FormData object
						// add form data to the FormData object

						// set up the request
						xhr.open("POST", url, true);
						xhr.onreadystatechange = function() {
							// handle the response from the server
							if (xhr.readyState == 4 && xhr.status == 200) {
								console.log(xhr.responseText);
								if (xhr.responseText.trim() === 'done') {
									document.getElementById("messages").innerHTML = 'Product Updated Successfully';
									setTimeout(function() {
			                             window.location = "manage_products.jsp"
			                         }, 1000);
								} else {
									document.getElementById("messages").innerHTML = xhr.responseText;
								}
							}
						};
						// send the request
						xhr.send(form);
					}

				});
	</script>

	<script>
	function removeProduct(value){
		window.remove.show();
		localStorage.setItem("productCode", value);
		
	}
	
	function popDialog(){
		window.remove.close();
		localStorage.removeItem("productCode");
		document.getElementById("remove-message").innerHTML = '';
	}
	
	function removeItem(){
		let form = new FormData();
		form.append("productCode", localStorage.getItem("productCode"));
		var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
		var url = "removeProductServlet"; // URL of the servlet to send data to
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
						window.location = "manage_products.jsp";
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