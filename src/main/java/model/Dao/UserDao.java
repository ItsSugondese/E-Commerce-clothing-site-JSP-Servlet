package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import model.AESEncryption;
import model.Product;
import model.User;

/**
 * Data Access Object(DAO) class responsible for accessing data from "user"
 * table of the database
 *
 */

public class UserDao {

	private Connection con;

	public UserDao(Connection con) {
		this.con = con;
	}

	// for inserting data to the database. returns true if successfully inserted and
	// returns false in the case any error occurs
	public boolean saveUser(User user) {
		boolean f = false;
		try {
			 // Hash the password using AES encryption
	        String encryptedPassword = AESEncryption.encrypt(user.getPassword());
			// user -->database
			String query = "insert into user(email, username ,password, address, phoneNumber, profilePic) values (?,?,?,?,?,?)";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, encryptedPassword);
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getPhoneNo());
			pstmt.setString(6, user.getPp());
			pstmt.executeUpdate();

			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}

	// method to verify user's login details. Gets email and password from parameter
	// variable and checks that email and password with the email and password of
	// the
	// users in the database and returns User details in the case of successful
	// verification and null in the case of unsuccessful one.
	public User verifyLogin(String email, String password) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			
			 String encryptedPassword = AESEncryption.encrypt(password);
			 
			 
			// Execute SQL query
			String sql = "SELECT * FROM user WHERE email=? AND password=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, encryptedPassword);

			rs = stmt.executeQuery();

			// get values from the database
			while (rs.next()) {
				 String storedPassword = rs.getString("password");
		            String decryptedPassword = AESEncryption.decrypt(storedPassword);
		            if (password.equals(decryptedPassword)) {
				user = new User();

				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAddress(rs.getString("address"));
				user.setPhoneNo(rs.getString("phoneNumber"));
				user.setPp(rs.getString("profilePic"));
		            }
			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}
		return user;
	}

	// method uses parameter email variable to serach for the user with the given email and returns the user if exits in database.
	public User getSingleUser(String email) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			// Execute SQL query
			String sql = "SELECT * FROM user WHERE email=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);

			rs = stmt.executeQuery();

			// get values from the database
			while (rs.next()) {

				user = new User();
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAddress(rs.getString("address"));
				user.setPhoneNo(rs.getString("phoneNumber"));
				user.setPp(rs.getString("profilePic"));

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}

		return user;
	}

	
	// has user object as parameter variable and uses variables from that object to update the user details in the database
	public boolean editProfileDetail(User user) {

		boolean f = false;
		try {
			// user -->database
			String query = "update user set username = '" + user.getUsername() + "', address = '" + user.getAddress()
					+ "', phoneNumber = '" + user.getPhoneNo() + "', profilePic = '" + user.getPp() + "' WHERE email='"
					+ user.getEmail() + "'";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.executeUpdate();

			f = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;

	}

	// has user object as parameter variable and uses password variable of that object to change the password of user in the database
	public boolean changePassword(User user) {

		boolean f = false;
		try {
			String encryptedPassword = AESEncryption.encrypt(user.getPassword());
			// user -->database
			String query = "update user set password = '" + encryptedPassword + "' WHERE email='" + user.getEmail()
					+ "'";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.executeUpdate();

			f = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;

	}
}
