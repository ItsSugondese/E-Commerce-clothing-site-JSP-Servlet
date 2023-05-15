package controller.ordering;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.User;
import model.Dao.CartDao;
import model.helper.ConnectionProvider;
import model.helper.ErrorHandler;


/*
 * Responsible for adding new cart details in the cart table of the datababse,
 */

@WebServlet(name = "addingToCartServlet", urlPatterns = { "/public/addingCartServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class CartServlet extends HttpServlet {
	

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = (User) ((request.getSession()).getAttribute("loggedUser"));

		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");
		if (user == null) {
			out.println("hasn't logged in");
		} else {
			String email = user.getEmail();
			System.out.println(email);
			System.out.println(getServletContext().getInitParameter("adminEmail"));
			if (email.equals(getServletContext().getInitParameter("adminEmail"))) {
				System.out.println("Here");
				String message = "only users can buy items not admin";
				out.println(message);
				ErrorHandler handler = new ErrorHandler();
				handler.setMessage(message);
				(request.getSession()).setAttribute("err", handler);
			} else {
				String productCode = request.getParameter("productCode");

				CartDao dao = new CartDao(ConnectionProvider.getConnection());

				if (dao.saveToCart(email, productCode, Integer.parseInt(request.getParameter("quantity")))) {
					out.println("done");
				} else {
					out.println("failed adding product to cart");
				}
			}
		}
	}




}
