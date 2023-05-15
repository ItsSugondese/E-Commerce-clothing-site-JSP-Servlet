<%@page import="model.helper.ConnectionProvider"%>
<%@page import="model.Dao.CartDao"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
User loggedUser = (User) session.getAttribute("loggedUser");
    CartDao cart = new CartDao(ConnectionProvider.getConnection());
%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="public-css/navbar.css">
</head>
<body>

 <nav>
        <div id="left">
            <div class="logo">
                <a href="homepage.jsp">
              <img src="img/logo.png"/> 
            </a>
              <div class="slogan">Clothe Website to buy fashion items</div>

            </div>
          </div>
          
          
      
        <div id="center">
            <div class="home">
                <a href="homepage.jsp">
                <img src="img/home.png"/> <span class="tooltiptext">Homepage</span>
            </a>
            </div>
          <div class="search">
            <img src="img/search.png" />
            <input type="text" id="search-bar" onkeyup="search()"/>
          </div>
      
          <div class="cart ">
            <a href="../logged-user/cart.jsp?logged-user/cart.jsp">
            <img src="img/cart.png"> <span class="tooltiptext">cart</span>
            <%if(loggedUser!=null){ %>  <span class="cart-count"><%= cart.noOfItemsInCart(loggedUser.getEmail()) %></span> <% }  %>
        </a>
          </div>
        </div>
      
        <div id="right">
        <%
        if(loggedUser!=null){
        %>
            <a href="../logged-user/profile.jsp?logged-user/profile.jsp">
            <div class="profile">
                <img src="<%=getServletContext().getInitParameter("profilePicUrlFromPublicUserPage") 
					+ loggedUser.getPp()%>" />
                <span class="profile-name"><%=loggedUser.getUsername() %> </span>
              </div>

            </a>
              

          <div class="logout">
          <a href="../logOut" class="logout-btn">Logout</a>
        </div>
        <%
        }else{
        %>
        <div class="login">
          <a href="../login.jsp" class="login-btn">Login</a>
        </div>
        <%
        }
        %>
        </div>
      </nav>





<%
if(loggedUser!=null){
	
	if(loggedUser.getEmail().equals(getServletContext().getInitParameter("adminEmail"))){
		%>
		<a href="../admin/admin_homepage.jsp">Admin homepage </a> 
		<%
	}
}
%>
	



</body>
</html>