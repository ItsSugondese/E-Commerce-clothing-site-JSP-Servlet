package model.helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is responsible for providing database connetion. There is static
 * connection method so that we don't have to create object of this class
 * everytime we need connection with database
 *
 */

public class ConnectionProvider {

	private static Connection con;

	public static Connection getConnection() {
		try {

			if (con == null) {
				// driver class load
				Class.forName("com.mysql.cj.jdbc.Driver");

				// create a connection..
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursework", "root", "admin");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

}
