package controller.profile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Dao.UserDao;
import model.helper.ConnectionProvider;

/*
 * This serlvet class is responsible for checking whether the password user enter is correct or not. This method is called 
 * when changing password
 */

@WebServlet(name = "checkingPassword", urlPatterns = {"/logged-user/checkingPasswordServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
public class CheckPasswordServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserDao dao = new UserDao(ConnectionProvider.getConnection());

		User user = dao.verifyLogin(((User) ((request.getSession()).getAttribute("loggedUser"))).getEmail(), 
				request.getParameter("current-password"));

		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");
		if (user != null) {
			out.println("done");
        } else {
            out.println("wrong password");
        }
	}
}
