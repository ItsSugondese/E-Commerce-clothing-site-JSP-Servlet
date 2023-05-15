package controller.profile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.Dao.UserDao;
import model.helper.ConnectionProvider;

/*
 * This servlet is responsible for changing the password of the user
 */

@WebServlet(name = "changePassword", urlPatterns = { "/logged-user/changePasswordServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class ChangePasswordServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserDao dao = new UserDao(ConnectionProvider.getConnection());

		User user = dao.getSingleUser(((User) ((request.getSession()).getAttribute("loggedUser"))).getEmail());
		user.setPassword(request.getParameter("new-password"));
		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");
		if (dao.changePassword(user)) {
			out.println("done");
			
			HttpSession session = request.getSession();
			(request.getSession()).removeAttribute("loggedUser");
			
			(request.getSession()).setAttribute("loggedUser", user);
		} else {
			out.println("Failed to change password");
		}
	}
}
