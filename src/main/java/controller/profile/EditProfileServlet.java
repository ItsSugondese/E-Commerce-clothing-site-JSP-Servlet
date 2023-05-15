package controller.profile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.Product;
import model.User;
import model.Dao.ProductDao;
import model.Dao.UserDao;
import model.helper.ConnectionProvider;
import model.helper.ImageHandler;


/*
 * 
 * This servlet class is responsible for performing the opeartion related to any changes
 * that user makes with their profile
 */

@WebServlet(name = "editProfileServlet", urlPatterns = {"/logged-user/editProfile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
public class EditProfileServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserDao dao = new UserDao(ConnectionProvider.getConnection());
		User user = dao.getSingleUser(((User) ((request.getSession()).getAttribute("loggedUser"))).getEmail());

		Part filePart = request.getPart("pfp");
		String fileName = user.getPp();
		if (filePart.getSize() > 0) {
			// upload path for image
			String uploadFilePath = ImageHandler.getUploadPath(request,
					getServletContext().getInitParameter("profilePicUrl"));

			ImageHandler.deleteProfileImage(uploadFilePath, user);
			// creates the save directory if it does not exists
			ImageHandler.createDirectory(uploadFilePath);

			//saving file in the storage
			fileName = ImageHandler.getRegisterFileName(filePart, request); 
			filePart.write(uploadFilePath + File.separator + fileName);
		}
		// getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");

		String username = ((request.getParameter("username")).isBlank()) ? user.getUsername()
				: request.getParameter("username");
		String address = (((request.getParameter("address")).isBlank()) ? user.getAddress()
				: request.getParameter("address"));
		String phoneNo = (((request.getParameter("phoneNo")).isBlank()) ? user.getAddress()
				: request.getParameter("phoneNo"));
		
		String profilePic = fileName;

		user.setUsername(username);
		user.setAddress(address);
		user.setPhoneNo(phoneNo);
		user.setPp(profilePic);
		
		if (dao.editProfileDetail(user)) {
			out.println("done");
			HttpSession session = request.getSession();
			(request.getSession()).removeAttribute("loggedUser");
			(request.getSession()).setAttribute("loggedUser", user);
		} else {
			out.println("Error when updating, check fields you fill up");
		}
	}

}
