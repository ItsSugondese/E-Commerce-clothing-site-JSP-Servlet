<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="admin-css/navbar.css" />
</head>
<body>
<%
String url = request.getRequestURI().toString();
System.out.println(url);
%>

 <header>
      <h1>Admin Dashboard</h1>
      <a href="../logOut" class="logout-btn" >Log out</a>
    </header>
    <nav>
     <a href="admin_homepage.jsp" style="<% if(url.equals("/E-Commerce_coursework/admin/admin_homepage.jsp")){ %>background-color: blue <%} %>">Homepage</a>
<a href="add_product.jsp" style="<% if(url.equals("/E-Commerce_coursework/admin/add_product.jsp")){ %>background-color: blue <%} %>">Add products</a>
<a href="manage_products.jsp" style="<% if(url.equals("/E-Commerce_coursework/admin/manage_products.jsp")){ %>background-color: blue <%} %>">Manage products</a>
<a href="view-orders.jsp" style="<% if(url.equals("/E-Commerce_coursework/admin/view-orders.jsp")){ %>background-color: blue <%} %>">Track orders</a>
<a href="whole-transaction-history.jsp" style="<% if(url.equals("/E-Commerce_coursework/admin/whole-transaction-history.jsp")){ %>background-color: blue <%} %>">Track Transaction</a>

    </nav>
    



<br>
</body>
</html>