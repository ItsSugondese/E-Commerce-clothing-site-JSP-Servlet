<%@page import="model.User"%>
<%@page import="java.util.TimerTask"%>
<%@page import="java.util.Timer"%>
<%@page import="model.helper.LoginNotifier"%>
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
	<%
	String queryString = request.getQueryString();
	String color;
	LoginNotifier notifier = null;
	if (session.getAttribute("loginMessage") != null) {
		notifier = (LoginNotifier) session.getAttribute("loginMessage");
		if (notifier.getMessageType().equals("error")) {
			color = "red";
		} else {
			color = "green";
		}
	%>
	<h3 style="color : <%=color%>">
		<%=notifier.getMessage()%></h3>
	<%
	session.removeAttribute("loginMessage");

	if (notifier.getMessageType().equals("success") && session.getAttribute("loggedUser") != null) {

		response.sendRedirect("roleVerify");

	}
	}
	request.removeAttribute("loginMessage");
	%>
	
	<!-- partial:index.partial.html -->
<div class="login-page">
<span style="color: #FFFF00; font-size : 30px" >Login</span>
  <div class="form">
    
    <form id="registration-form" action="loginServlet?<% if (queryString != null) out.print("extra=true&" + queryString); %>">
      Enter your email address: <input type="email" placeholder="email" name="email" required/>
      Password: <input type="password" placeholder="password" name="password" required/>
      <button>Login</button>
      <p class="message">Not registered? <a href="register.jsp">Create an account</a></p>
      
       <input type="hidden" name="url" value="<%if (queryString != null) out.print(queryString);%>">
    </form>
  </div>
</div>
	
	

</body>
</html>