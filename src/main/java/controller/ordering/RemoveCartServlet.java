package controller.ordering;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.Product;
import model.Dao.CartDao;
import model.Dao.ProductDao;
import model.helper.ConnectionProvider;
import model.helper.ImageHandler;


/*
 * This servlet is responsible for removing items that are on the cart table from the database
 */
@WebServlet(name = "removingFromCartServlet", urlPatterns = {"/logged-user/removingCartServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
public class RemoveCartServlet extends HttpServlet {

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int cartId = Integer.parseInt(request.getParameter("cartId"));

		CartDao dao = new CartDao(ConnectionProvider.getConnection());
		
		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");

		if (dao.removeCartItemByUser(cartId)) {
			// delete image from server
			out.println("done");
		} else {
			out.println("Something wrong with the server");
		}
	
	}
 
	
	
}
