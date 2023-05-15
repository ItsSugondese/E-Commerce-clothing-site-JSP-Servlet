<%@page import="model.User"%>
<%@page import="model.Transaction"%>
<%@page import="java.util.List"%>
<%@page import="model.helper.ConnectionProvider"%>
<%@page import="model.Dao.TransactionDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%
TransactionDao dao = new TransactionDao(ConnectionProvider.getConnection());
List<Transaction> transactions = dao.getTransactionsByEmail(((User) session.getAttribute("loggedUser")).getEmail());
float totalPrice = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="logged-css/transaction.css" />


</head>
<body>

<%@include file="logged-user-navbar.jsp" %> <br>


<center>
		
		
	<%
	if (!transactions.isEmpty()) {
%>
<div class="cart-container">
        <div class="cart-items-container">
        
        <%
     
        for (Transaction transaction : transactions) {
				

		%>
          <div class="cart-item">
            <div class="cart-item-image-container">
              <img src="<%=getServletContext().getInitParameter("productPicUrlFromLoggedUserPage")
		+ (transaction.getProduct()).getProductPicture()%>" alt="[product-name]" class="cart-item-image">
            </div>
            <div class="cart-item-details-container">
            <div class="cart-item-user">Ordered by :  <%=(transaction.getUser()).getUsername()%></div>
              <div class="cart-item-name"><%=(transaction.getProduct()).getProductName()%></div>
              <div class="cart-item-brand"><%=(transaction.getProduct()).getBrand()%></div>
              
              <div class="cart-item-quantity">Qty:  <%=transaction.getNoOfOrder()%></div>
              
            </div>
            <div class="cart-item-total">Rs. <%= transaction.getNoOfOrder() * Integer.parseInt(((((transaction.getProduct()).getProductPrice()).split(". "))[1]).trim()) %>
          </div>
         
         
        </div>
        <%
        totalPrice += transaction.getNoOfOrder() * Integer.parseInt(((((transaction.getProduct()).getProductPrice()).split(". "))[1]).trim());
			}
         %>
          
      </div>
       <div class="cart-total-container">
          <div class="cart-total-label">Total:</div>
          <div class="cart-total-price"><%= totalPrice %></div>
        </div>
      </div>
<%
			
		}
%>	
		
	</center>

















</body>
</html>