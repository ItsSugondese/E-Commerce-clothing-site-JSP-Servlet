package controller.delivering;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Dao.CartDao;
import model.helper.ConnectionProvider;

/*
 * Responsible for removing the items from the cart table of the database after admin  clicks on deliver product button
 */

@WebServlet(name = "deliveringCartItemServlet", urlPatterns = {"/admin/deliveringCartServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
public class DeliveringServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int cartId = Integer.parseInt(request.getParameter("cartId"));

		CartDao dao = new CartDao(ConnectionProvider.getConnection());
		
		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");

		if (dao.removeCartItemByAdmin(cartId)) {
			// delete image from server
			out.println("done");
		} else {
			out.println("Something wrong with the server");
		}
	}
	
}
