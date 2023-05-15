package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.Dao.UserDao;
import model.helper.ConnectionProvider;
import model.helper.LoginNotifier;

/**
 * This servlet is responsible for handling login details where the login
 * details is send through "login.jsp" webpage and then this servlet checks the
 * login details by extracting users from "user" table of the database and
 * compares all the users with login details send through "login.jsp" form.
 * 
 * After login is sucessful, if there is "url" query in the url, this serlvet
 * will redirect the page to that url. If there is no url query, the servlet
 * will redirect back to "login.jsp" with login message even if login fails.
 *
 */

@WebServlet(name = "login", urlPatterns = {"/loginServlet"})
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Its here hai");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserDao dao = new UserDao(ConnectionProvider.getConnection());

		User user = dao.verifyLogin(email, password);

		System.out.println(user);
		if (user == null) {
			LoginNotifier loginNotifier = new LoginNotifier("error", "User with that email address doesn't exist");
			request.setAttribute("loginMessage", loginNotifier);

		} else {
			HttpSession session = request.getSession();
			LoginNotifier loginNotifier = new LoginNotifier("success", "Successfully logged in");
			session.setAttribute("loginMessage", loginNotifier);
			session.setAttribute("loggedUser", user);

		}
		if ((request.getParameter("url")).isBlank())
			request.getRequestDispatcher("login.jsp").forward(request, response);
		else {
			response.sendRedirect(request.getParameter("url"));
		}
	}

}
