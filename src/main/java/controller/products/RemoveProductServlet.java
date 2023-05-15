package controller.products;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import model.Dao.ProductDao;
import model.helper.ConnectionProvider;
import model.helper.ImageHandler;

/**
 * This servlet is responsible for removing the product from the "product" table
 * of the database. The product to be removed data is send thorugh
 * "admin/manage_product.jsp" webpage using "remove" button.
 *
 */

@WebServlet(name = "removeProductServlet", urlPatterns = {"/admin/removeProductServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class RemoveProductServlet extends HttpServlet {

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String productCode = request.getParameter("productCode");

		ProductDao dao = new ProductDao(ConnectionProvider.getConnection());
		Product product = dao.getSingleProduct(productCode);
		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");

		if (dao.removeProduct(productCode)) {
			// delete image from server
			String imagePath = ImageHandler.getUploadPath(request,
					getServletContext().getInitParameter("productsImagePath"));
			boolean status = ImageHandler.deleteProductImage(imagePath, product);

			// checkign if image is deleted successfully
			if (status) {
				System.out.println("Image deleted successfully.");
			} else {
				System.out.println("Failed to delete image.");
			}
			out.println("done");
		} else {
			out.println("Something wrong with the server");
		}
	}
}
