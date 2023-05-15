package controller.profile;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * 
 * This servlet class is responsible for looging the user out by destroying user session
 */
@WebServlet(name = "loggingOutUserServlet", urlPatterns = { "/logOut" })
public class LoggingOutServlet extends HttpServlet{

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		(request.getSession()).removeAttribute("loggedUser");
		
		response.sendRedirect("public/homepage.jsp");
	}
}
