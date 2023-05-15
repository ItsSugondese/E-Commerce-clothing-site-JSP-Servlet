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
import javax.servlet.http.Part;

import model.Product;
import model.Dao.ProductDao;
import model.helper.ConnectionProvider;
import model.helper.ImageHandler;

/**
 * This servlet is responsible for editing value of certain product in the
 * database. The edit details was send through "admin/manage_products.jsp"
 * webpage using edit button.
 *
 */

@WebServlet(name = "editProductServlet", urlPatterns = {"/admin/editProductServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class EditProductServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String productCode = request.getParameter("productCode");
		ProductDao dao = new ProductDao(ConnectionProvider.getConnection());

		Product product = dao.getSingleProduct(productCode);

		Part filePart = request.getPart("productPic");
		String fileName = product.getProductPicture();
		if (filePart.getSize() > 0) {

			// upload path for image
			String uploadFilePath = ImageHandler.getUploadPath(request,
					getServletContext().getInitParameter("productsImagePath"));

			ImageHandler.deleteProductImage(uploadFilePath, product);
			// creates the save directory if it does not exists
			ImageHandler.createDirectory(uploadFilePath);
			
			//saving file in the storage
			fileName = ImageHandler.getProductFileName(filePart, request); 
			filePart.write(uploadFilePath + File.separator + fileName);
		}

		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");

		String productName = ((request.getParameter("productName")).isBlank()) ? product.getProductName()
				: request.getParameter("productName");
		String productPrice = "Rs. " + (((request.getParameter("productPrice")).isBlank()) ? product.getProductPrice()
				: request.getParameter("productPrice"));
		;
		String brand = ((request.getParameter("brand")).isBlank()) ? product.getBrand() : request.getParameter("brand");
		String categroy = ((request.getParameter("category")).isBlank()) ? product.getCategory()
				: request.getParameter("category");
		String type = request.getParameter("type");
		String productPicture = fileName;
		float rating = Float.parseFloat(request.getParameter("rating"));
		
		int quantity = product.getInStock();

		product.setProductName(productName);
		product.setProductPrice(productPrice);
		product.setBrand(brand);
		product.setCategory(categroy);
		product.setType(type);
		product.setInStock(quantity);
		product.setProductPicture(productPicture);
		product.setRating(rating);
		if (dao.editProduct(product)) {
			out.println("done");
		} else {
			out.println("Error when updating, check fields you fill up");
		}

	}

}
