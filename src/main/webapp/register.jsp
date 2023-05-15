<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/login.css" />
</head>
<body>


<div class="login-page">
  <span id="message" style="color: #FFFF00; font-size : 30px" >Register Account </span>
  <div class="form">

    <form id="registration-form" method="post" action="registerServlet"
		enctype="multipart/form-data">
     Enter username : <input type="text" name="username" required /> <br>
		Enter Email address : <input type="email" name="email" required /> <br>
		Enter password : <input type='password' name="password" required /> <br>
		Address : <input type="text" name="address" required /> <br>
		Phone Number : <input type="text" name="phoneNo" required /> <br>
		Profile Pic : <input type="file" id="myFile" name="pfp" accept="image/*" required>
      <button>create</button>
      <p class="message">Already registered? <a href="login.jsp">Sign In</a></p>
    </form>
  </div>
</div>



	
	<script>
	document.addEventListener("DOMContentLoaded", function () {
         console.log("loaded........")
         
         document.getElementById('registration-form').addEventListener('submit', function (event) {
             event.preventDefault();
             let form = new FormData(this);
             //send register servlet:
            	 
             
             var xhr = new XMLHttpRequest(); // create an XMLHttpRequest object
             var url = "registerServlet"; // URL of the servlet to send data to
             
             // set up the request
             xhr.open("POST", url, true);
             xhr.onreadystatechange = function () {
                 // handle the response from the server
                 if (xhr.readyState == 4 && xhr.status == 200) {
                     console.log(xhr.responseText);
                     if (xhr.responseText.trim() === 'done') {
                         document.getElementById("message").innerHTML = 'Successfully registered';
                         setTimeout(function() {
                            window.location = "login.jsp"
                        }, 3000);
                     } else {
                    	 document.getElementById("message").innerHTML = xhr.responseText;
                    	 setTimeout(function() {
                    		 document.getElementById("message").innerHTML = 'Register Account';
                         }, 3000);
                        
                     }
                 }
             };
             // send the request
             xhr.send(form);
         });
     });

	</script>
</body>
</html>