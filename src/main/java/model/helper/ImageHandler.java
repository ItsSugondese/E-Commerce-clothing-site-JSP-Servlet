package model.helper;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import model.Product;
import model.User;

/**
 * This class is responsible for handling image related process like storing
 * image, getting name of the iamge send through form and so on.
 *
 */

public class ImageHandler {

	// get the image details from RegisterServlet and change name of the iamge to
	// "user-email.image_extention".
	public static String getRegisterFileName(Part part, HttpServletRequest req) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return req.getParameter("email") + token.substring(token.indexOf("."), token.length() - 1);
			}
		}
		return "";
	}

	//get the image details from "products.AddProductServlet" and change name of the iamge to "user-email.image_extention".
	public static String getProductFileName(Part part, HttpServletRequest req) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return req.getParameter("productCode") + token.substring(token.indexOf("."), token.length() - 1);
			}
		}
		return "";
	}

	// path where the image must be uploaded
	public static String getUploadPath(HttpServletRequest request, String UPLOAD_DIR) {
		String applicationPath = request.getServletContext().getRealPath("");
		return applicationPath + File.separator + UPLOAD_DIR;
	}

	// create a folder the case folder doesn't exist
	public static void createDirectory(String uploadFilePath) {
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
	}

	// deletes images from the folder
	public static boolean deleteProductImage(String imagePath, Product product) {
		File imageFile = new File(imagePath + File.separator + product.getProductPicture());
		return imageFile.delete();
	}
	
	// deletes images from the folder
		public static boolean deleteProfileImage(String imagePath, User user) {
			File imageFile = new File(imagePath + File.separator + user.getPp());
			return imageFile.delete();
		}
}
