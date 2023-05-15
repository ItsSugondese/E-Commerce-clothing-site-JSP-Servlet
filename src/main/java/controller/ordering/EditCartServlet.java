package controller.ordering;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.Dao.CartDao;
import model.helper.ConnectionProvider;

/*
 * This servlet is resonsible for editing the value in the cart table of database.
 */
@WebServlet(name = "editingInCartServlet", urlPatterns = {"/logged-user/editingCartServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class EditCartServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CartDao dao = new CartDao(ConnectionProvider.getConnection());

		Cart cart = dao.getCartDetailUsingCid(Integer.parseInt(request.getParameter("cartId")));

		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");
		if (cart != null) {
			if( dao.updatingByEditingCart(cart, Integer.parseInt(request.getParameter("quantity")))) {
				out.println("done");
			} else {
				out.println("Failed to edit");
			}
		} else {
			out.println("Failed to send rquired details to the server");
		}

	}
}
