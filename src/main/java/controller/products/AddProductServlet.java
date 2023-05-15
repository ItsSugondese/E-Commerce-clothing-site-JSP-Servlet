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
 * This servlet is responsible for adding product details inserted in
 * "admin/add_product.jsp" webpage to "product" table in the database
 *
 */

@WebServlet(name = "addProductServlet", urlPatterns = {"/admin/addProductServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class AddProductServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// upload path for image
		String uploadFilePath = ImageHandler.getUploadPath(request,getServletContext().getInitParameter("productsImagePath"));
		

		// creates the save directory if it does not exists
		ImageHandler.createDirectory(uploadFilePath);

		// saving file in the storage
		Part filePart = request.getPart("productPic");
		String fileName = ImageHandler.getProductFileName(filePart, request);
		filePart.write(uploadFilePath + File.separator + fileName);

		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");

		String productCode = request.getParameter("productCode");
		String productName = request.getParameter("productName");
		String productPrice = "Rs. " + request.getParameter("productPrice");
		String brand = request.getParameter("brand");
		String categroy = request.getParameter("category");
		String type = request.getParameter("type");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String productPicture = fileName;
		String rating = request.getParameter("rating");

		// creating object of product model
		Product product = new Product();
		product.setProductCode(productCode);
		product.setProductName(productName);
		product.setProductPicture(productPicture);
		product.setBrand(brand);
		product.setCategory(categroy);
		product.setProductPrice(productPrice);
		product.setType(type);
		product.setInStock(quantity);
		product.setRating(Float.parseFloat(rating));

		// Getting Dao Class of Product to store product details
		ProductDao dao = new ProductDao(ConnectionProvider.getConnection());

		if (dao.saveProduct(product)) {
			out.println("done");
		} else {
			out.println("Product with that product code already exists");
		}
	}

}
