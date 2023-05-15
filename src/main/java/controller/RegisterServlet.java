package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.User;
import model.Dao.UserDao;
import model.helper.ConnectionProvider;
import model.helper.ImageHandler;

/**
 * This servlet is responsible for handling register details where the register
 * details is send through "register.jsp" webpage and then this servlet checks the
 * login details by extracting users from "user" table of the database and
 * compares all the users with login details send through "login.jsp" form.
 *
 */

@WebServlet(name = "register", urlPatterns = {"/registerServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class RegisterServlet extends HttpServlet {

	private static final String UPLOAD_DIR = "img" + File.separator + "pfp";

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		//upload path for image 
		String uploadFilePath = ImageHandler.getUploadPath(request, UPLOAD_DIR);

		// creates the save directory if it does not exists
		ImageHandler.createDirectory(uploadFilePath);

		//saving file in the storage
		Part filePart = request.getPart("pfp");
		String fileName = ImageHandler.getRegisterFileName(filePart, request); 
		filePart.write(uploadFilePath + File.separator + fileName);

		//getting printwrite to write html content
		PrintWriter out = response.getWriter();
		response.setContentType("text/HTML");
		
		
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String address = request.getParameter("address");
			String phoneNo = request.getParameter("phoneNo");
			String pp = fileName;
			
			User user = new User();
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(password);
			user.setAddress(address);
			user.setPhoneNo(phoneNo);
			user.setPp(pp);
			
			UserDao dao = new UserDao(ConnectionProvider.getConnection());
			if (dao.saveUser(user)) {
                  out.println("done");
              } else {
                  out.println("User with that email already exists, please use different email");
              }
	}
}
