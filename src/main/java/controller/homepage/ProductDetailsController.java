package controller.homepage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet class is responsible for sending product code from
 * "homepage.jsp" to "product.jsp" using
 * request.getRequestDispatcher("product.jsp").forward(request, response);
 *
 */

@WebServlet(name = "productDetailServlet", urlPatterns = {"/public/productDetailServlet"})
public class ProductDetailsController extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("I'm here and working hai ta");
		String productCode = request.getParameter("productCode");
		request.setAttribute("productCode", productCode);
		request.getRequestDispatcher("../public/product.jsp").forward(request, response);
	}

}
