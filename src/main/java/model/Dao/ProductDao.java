package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.User;

/**
 * Data Access Object(DAO) class responsible for accessing data from "product"
 * table of the database
 *
 */

public class ProductDao {

	private Connection con;

	public ProductDao(Connection con) {
		this.con = con;
	}

	// for inserting data to the database. returns true if successfully inserted and
	// returns false in the case any error occurs
	public boolean saveProduct(Product product) {
		boolean f = false;
		try {
			// user -->database
			String query = "insert into product(productCode, productName, price, brand, "
					+ "category, type, productPic, inStock, rating) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, product.getProductCode());
			pstmt.setString(2, product.getProductName());
			pstmt.setString(3, product.getProductPrice());
			pstmt.setString(4, product.getBrand());
			pstmt.setString(5, product.getCategory());
			pstmt.setString(6, product.getType());
			pstmt.setString(7, product.getProductPicture());
			pstmt.setInt(8, product.getInStock());
			pstmt.setFloat(9, product.getRating());

			pstmt.executeUpdate();

			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}

	// method to get all products from the database and returns them by making list
	// of prodcut or List<Product>
	public List<Product> getProducts() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Product> products = null;

		try {
			// Execute SQL query
			String sql = "SELECT * FROM product";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			products = new ArrayList<>();
			// get values from the database
			while (rs.next()) {
				Product product = new Product();
				product.setProductCode(rs.getString("productCode"));
				product.setProductName(rs.getString("productName"));
				product.setProductPrice(rs.getString("price"));
				product.setBrand(rs.getString("brand"));
				product.setCategory(rs.getString("category"));
				product.setType(rs.getString("type"));
				product.setProductPicture(rs.getString("productPic"));
				product.setRating(Float.parseFloat(rs.getString("rating")));
				product.setInStock(Integer.parseInt(rs.getString("inStock")));

				products.add(product);

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}
		return products;
	}

	// method to get a product with specified product code from the database.
	// Product code is passed in parameter.
	public Product getSingleProduct(String productCode) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Product product = null;

		try {
			// Execute SQL query
			String sql = "SELECT * FROM product WHERE productCode=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, productCode);

			rs = stmt.executeQuery();

			// get values from the database
			while (rs.next()) {

				product = new Product();
				product.setProductCode(productCode);
				product.setProductName(rs.getString("productName"));
				product.setProductPrice(rs.getString("price"));
				product.setBrand(rs.getString("brand"));
				product.setCategory(rs.getString("category"));
				product.setProductPicture(rs.getString("productPic"));
				product.setType(rs.getString("type"));
				product.setRating(Float.parseFloat(rs.getString("rating")));
				product.setInStock(Integer.parseInt(rs.getString("inStock")));

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}

		return product;
	}

	// method to update product data in the database. Returns true if successfully
	// updated and returns false in the case of any error
	public boolean editProduct(Product product) {
		boolean f = false;
		try {

			String query = "update product set productName = '" + product.getProductName() + "', price = '"
					+ product.getProductPrice() + "', brand = '" + product.getBrand() + "', category = '"
					+ product.getCategory() + "', type = '" + product.getType() + "', rating = '" + product.getRating()
					+ "', inStock = '" + product.getInStock() + "', productPic = '" + product.getProductPicture()
					+ "' where productCode = '" + product.getProductCode() + "'";
			// user -->database
			// String query = "insert into product(productCode, productName, price, brand,
			// category, productPic) values (?,?,?,?,?,?)";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.executeUpdate();

			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}

	// remove a product with the product code send throgh parameter variable from
	// the database. reutrns true if removed successfully and false in the case of
	// error
	public boolean removeProduct(String productCode) {
		boolean f = false;
		try {

			String query = "DELETE FROM product WHERE productCode=?";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, productCode);
			pstmt.executeUpdate();

			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}

	// checking if product with a productCode exist
	public boolean checkProduct(String productCode) {
		Product product = null;

		product = getSingleProduct(productCode);

		if (product != null) {
			return true;
		} else {
			return false;
		}

	}
}
