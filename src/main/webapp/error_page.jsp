<%@page import="model.helper.ErrorHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
String errorMsg = "Something Went wrong";

if (session.getAttribute("err") != null){
	errorMsg = ((ErrorHandler) session.getAttribute("err")).getMessage();
	session.removeAttribute("err");
}
%>

<!DOCTYPE html>
<html>
<head>
	<title>Error Page</title>
	<style>
		body {
			background-color: #f2f2f2;
			font-family: Arial, sans-serif;
			font-size: 16px;
			line-height: 1.5;
			color: #333;
			text-align: center;
			padding: 50px 0;
		}

		h1 {
			font-size: 36px;
			margin-bottom: 20px;
		}

		p {
			font-size: 24px;
			margin-bottom: 40px;
		}

		button {
			display: inline-block;
			padding: 10px 20px;
			font-size: 16px;
			font-weight: bold;
			background-color: #333;
			color: #fff;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}

		button:hover {
			background-color: #555;
		}
	</style>
</head>
<body>
	<h1>Oops!</h1>
	<p><%=errorMsg %></p>
	<button onclick="window.location.href='public/homepage.jsp'">Go to Homepage</button>
</body>
</html>



