<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="admin-css/product-form.css" />
<style>

/* set background color of form */



</style>
</head>

<body>
	<!-- including navbar -->
	<%@include file="admin_navbar.jsp"%>

<center>
	<h3 id="messages"></h3>
	
	<form id="product-form" method="post" action="addProductServlet"
		enctype="multipart/form-data">
		Enter product code : <input type="text" name="productCode" required />
		<br> Enter product name : <input type="text" name="productName"
			required /> <br> Enter product price : <input type="number"
			name="productPrice" required /> <br> Enter brand name : <input
			type='text' name="brand" required /> <br> Enter product
		category : <select name="category" id="drop">
			<option disabled="disabled" selected>Pick a category!</option>
			<option>Tops</option>
			<option>Bottoms</option>
			<option>Dresses</option>
			<option>Underwear</option>
			<option>Footwear</option>
			<option>Accessories</option>
		</select> <span id="name-error" style="display: none; color: red;">Please
			enter valid product category</span> <br> Cloth Type :  <input
			type="radio" id="menswear-type" name="type" value="Menswear" required>
		  <label for="MensWear">Menswear</label>   <input type="radio"
			id="womenswear-type" name="type" value="Womenswear" required>
		  <label for="Womenswear">Womenswear</label><br> <br> Quantity: <input
			type="number" name="quantity" required /> <br> 
			Rating: <input
			type="number" name="rating" max="5" min="0" step="0.1" required /> <br>
			 Product
		picture : <input type="file" id="myFile" name="productPic"
			accept="image/*" required> <br>  <input type="submit"
			value="Add Product">

	</form>

</center>

	<script type="text/javascript">
		document
				.addEventListener(
						"DOMContentLoaded",
						function() {

							document
									.getElementById('product-form')
									.addEventListener(
											'submit',
											function(event) {
												event.preventDefault();
												//send register servlet:

												var e = document
														.getElementById("drop");
												var value = e.value;

												if (value == "Pick a category!") {
													document
															.getElementById("name-error").style.display = "inline";
												} else {
													document
															.getElementById("name-error").style.display = "none";

													let form = new FormData(
															this);
													//send register servlet:
													var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
													var url = "addProductServlet"; // URL of the servlet to send data to
													// create a new FormData object
													// add form data to the FormData object

													// set up the request
													xhr.open("POST", url, true);
													xhr.onreadystatechange = function() {
														// handle the response from the server
														if (xhr.readyState == 4
																&& xhr.status == 200) {
															console
																	.log(xhr.responseText);
															if (xhr.responseText
																	.trim() === 'done') {
																document
																		.getElementById("messages").innerHTML = 'Product Addeded Successfully';

																document
																.getElementById("messages").style.color = "green"; 
															} else {
																document
																		.getElementById("messages").innerHTML = xhr.responseText;
																
																document
																.getElementById("messages").style.color = "red"; 
															}
														}
													};
													// send the request
													xhr.send(form);
												}
											});
						});
	</script>

</body>

</html>