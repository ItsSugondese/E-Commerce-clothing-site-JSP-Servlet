<%@page import="model.Dao.CartDao"%>
<%@page import="model.User"%>
<%@page import="model.helper.ProductRatingHelper"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="model.Dao.ProductDao"%>
<%@page import="model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
User user = (User) ((request.getSession()).getAttribute("loggedUser"));


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style>


.container {
	display: flex;
	background-color: grey;
}

.image-container {
	background-color: white;
}

.product-details {
	font-size: 18px;
	display: flex;
	flex-direction: column;
	padding: 20px;
	padding-left: 10px;
	align-content: center;
}

.product-name {
	font-size: 25px;
}

.product-price {
	font-size: 30px;
	text-align: center;
	margin-top: 100px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 20px;
  font-size: 16px;
  font-weight: bold;
  text-transform: uppercase;
  cursor: pointer;
}

.edit-profile-button {
  background-color: #4CAF50;
  color: #fff;
}

.change-password-button {
  background-color: #f44336;
  color: #fff;
}

/* Hover styles */
button:hover {
  opacity: 0.8;
}
</style>
</head>
<body>
	
	<%@include file="logged-user-navbar.jsp"%>
	
	
	<center>
	

	<%
	if (user != null){
	%>

		
		<div class="container">
			<div class="image-container">
				<img
					src="<%=getServletContext().getInitParameter("profilePicUrlFromPublicUserPage") 
					+ user.getPp()%>"
					height="250">
			</div>

			<div class="product-details">
				<div class="product-name">Username : <%=user.getUsername()%> <br> Email : <%=user.getEmail() %></div>
				
				<div class="brand">
					Address :
					<%=user.getAddress()%></div>

				<div class="quantity">
					Phone No. :
					<%=user.getPhoneNo()%>
				</div>

								
				
				<div>

				<button onClick="editClicked()" class="edit-profile-button">Edit Profile</button>
					<button onClick="changePasswordBtn()" class="change-password-button">Change  Password</button>


				</div>
			</div>
		</div>
		<%
		}
		%>
		
		<div id="dialog-div">
			<dialog id="dialog" class="centered-element">
			<h3 id="messages"></h3>
			<form id="profile-edit-form" method="post" action="registerServlet"
		enctype="multipart/form-data">
		Enter username : <input type="text" name="username" id="username" /> <br>
		Address : <input type="text" name="address" id="address"  /> <br>
		Phone Number : <input type="text" name="phoneNo" id="phoneNo"  /> <br>
		<input type="file" id="myFile" name="pfp" accept="image/*" >
		<input type="submit" value="update" />
		
	</form>
			<button onclick="cancelling()">Cancel</button>
			</dialog>
		</div>
		
		<div id="change-password-div">
			<dialog id="changePassword" class="centered-element">
			<h3 id="change-messages"></h3>
			<form id="current-password-form" method="post" action="registerServlet"
		enctype="multipart/form-data">
		Enter Current Password : <input type="password" name="current-password" id="password" /> 
		
		<input type="submit" value="check" />
	</form>
	
	<form id="new-password-form" method="post" action="registerServlet"
		enctype="multipart/form-data" style="display : none">
		
		Enter New password : <input type="password" name="new-password" id="password" /> 
		
		<input type="submit" value="change" />
		
	</form>
			<button onclick="cancelling()">Cancel</button>
			</dialog>
		</div>
		
		
		</center>
		
		<script>
		let tempForm;
		
		
		function editClicked(value) {
			var li = value;

			
			document.getElementById("username").value = '<%= user.getUsername() %>';
			document.getElementById("address").value = '<%= user.getAddress() %>';
			document.getElementById("phoneNo").value = '<%= user.getPhoneNo() %>';

			
			
			
			tempForm = new FormData(document.getElementById('profile-edit-form'));
			
			
			
			window.dialog.show();
		}

		function cancelling() {
			window.dialog.close();
			window.changePassword.close();
		}
		</script>
		
		
		<script>
		document.getElementById('profile-edit-form').addEventListener('submit',function(event) {
					event.preventDefault();

					//send register servlet:

					
					
					let form = new FormData(document
							.getElementById('profile-edit-form'));
				

					
					 const fileInput = document.getElementById("myFile");
					  
					  console.log(fileInput.files.length);
					if(JSON.stringify([...tempForm.entries()]) === JSON.stringify([...form.entries()]) &&
							(document.getElementById("myFile")).files.length == 0){
						document.getElementById("messages").innerHTML = "You haven't updated anything";
					}else{
						//send register servlet:
						var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
						var url = "editProfile"; // URL of the servlet to send data to
						// create a new FormData object
						// add form data to the FormData object

						// set up the request
						xhr.open("POST", url, true);
						xhr.onreadystatechange = function() {
							// handle the response from the server
							if (xhr.readyState == 4 && xhr.status == 200) {
								console.log(xhr.responseText);
								if (xhr.responseText.trim() === 'done') {
									document.getElementById("messages").innerHTML = 'Profile Updated Successfully';
									setTimeout(function() {
			                             window.location = "profile.jsp"
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
		function changePasswordBtn(){
			window.changePassword.show();
		}
		
		document.getElementById('current-password-form').addEventListener('submit',function(event) {
			event.preventDefault();

			//send register servlet:

			
			
			let form = new FormData(this);
		

		
				//send register servlet:
				var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
				var url = "checkingPasswordServlet"; // URL of the servlet to send data to
				// create a new FormData object
				// add form data to the FormData object

				// set up the request
				xhr.open("POST", url, true);
				xhr.onreadystatechange = function() {
					// handle the response from the server
					if (xhr.readyState == 4 && xhr.status == 200) {
						console.log(xhr.responseText);
						if (xhr.responseText.trim() === 'done') {
							document.getElementById("change-messages").innerHTML = "";
							document.getElementById("current-password-form").style.display = "none";
							document.getElementById("new-password-form").style.display = "";
							
						} else {
							document.getElementById("change-messages").innerHTML = xhr.responseText;
						}
					}
				};
				// send the request
				xhr.send(form);
			

		});
		</script>
		
		<script>
		document.getElementById('new-password-form').addEventListener('submit',function(event) {
			event.preventDefault();

			//send register servlet:

			
			
			let form = new FormData(this);
		

		
				//send register servlet:
				var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
				var url = "changePasswordServlet"; // URL of the servlet to send data to
				// create a new FormData object
				// add form data to the FormData object

				// set up the request
				xhr.open("POST", url, true);
				xhr.onreadystatechange = function() {
					// handle the response from the server
					if (xhr.readyState == 4 && xhr.status == 200) {
						console.log(xhr.responseText);
						if (xhr.responseText.trim() === 'done') {
							document.getElementById("change-messages").innerHTML = "Password Changed Successfully";
							setTimeout(function() {
	                             window.location = "profile.jsp?logged-user/profile.jsp"
	                         }, 1000);
							
							
						} else {
							document.getElementById("change-messages").innerHTML = xhr.responseText;
						}
					}
				};
				// send the request
				xhr.send(form);
			

		});
		</script>

	
</body>
</html>