package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

/**
 * After login is successful and there is no "url" query string attached,
 * "login.jsp" file will redirect user to this page and this page will filter
 * user based on their role and will redirect them to homepage depending on their role.
 *
 */

@WebServlet(name = "roleServlet", urlPatterns = {"/roleVerify"})
public class UserRoleVerificationServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) (request.getSession()).getAttribute("loggedUser");
		if (user.getEmail().equals(getServletContext().getInitParameter("adminEmail"))) {
			response.sendRedirect("admin/admin_homepage.jsp");
		} else {
			response.sendRedirect("public/homepage.jsp");
		}
	}
}
