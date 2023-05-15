package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.Product;
import model.User;

public class CartDao {

	private Connection con;

	public CartDao(Connection connection) {
		this.con = connection;
	}

	
	// for inserting cart data to cart table of the database. returns true if successfully inserted and
		// returns false in the case any error occurs
	public boolean insertingInCart(String email, String productCode, int quantity, Product product) {
		boolean f = false;
		try {
			// user -->database
			String query = "insert into cart(uId, pId, quantity) values (?,?,?)";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, productCode);
			pstmt.setInt(3, quantity);
			
			pstmt.executeUpdate();
			
			
			product.decreaseInStock(quantity);
			
			query = "Update product SET inStock='" + product.getInStock() + "' WHERE productCode='" + productCode + "'";
			pstmt = this.con.prepareStatement(query);

			pstmt.executeUpdate();
			f = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}
	
	
	// method to update cart table using cart data in the database. Returns true if successfully
		// updated and returns false in the case of any error
	public boolean updatingCart(Cart getCart, int quantity) {
		boolean f = false;
		try {
		
			Cart cart = getCart;
			
			String cartQuery = "update cart set quantity='" + (quantity  + cart.getNoOfOrder())+
					"' WHERE cId='" + cart.getCartId() +"'";
			PreparedStatement pstmt  = this.con.prepareStatement(cartQuery);
			pstmt.executeUpdate();

			Product product = cart.getProduct();
			
			product.decreaseInStock(quantity);
			String productQuery = "Update product SET inStock='" + product.getInStock() + 
					"' WHERE productCode='" + product.getProductCode() + "'";
			pstmt = this.con.prepareStatement(productQuery);

			pstmt.executeUpdate();
			f = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
	
	public boolean updatingByEditingCart(Cart getCart, int quantity ) {
		boolean f = false;
		try {
		
			Cart cart = getCart;
			int temp = cart.getNoOfOrder();
			int remainingItems = 0;
			String cartQuery = "update cart set quantity='" + quantity +"' WHERE cId='" + cart.getCartId() +"'";
			PreparedStatement pstmt  = this.con.prepareStatement(cartQuery);
			pstmt.executeUpdate();

			Product product = cart.getProduct();
			if(temp > quantity) {
				product.increaseInStock(temp-quantity);
			}else if(temp < quantity) {
				product.decreaseInStock(quantity - temp);
			}
			String productQuery = "Update product SET inStock='" + product.getInStock() + "' WHERE productCode='" + product.getProductCode() + "'";
			pstmt = this.con.prepareStatement(productQuery);

			pstmt.executeUpdate();
			f = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
	
	// for inserting data to the database. returns true if successfully inserted and
	// returns false in the case any error occurs
	public boolean saveToCart(String email, String productCode, int quantity) {
		boolean f = false;
		boolean isThere = checkIfInCart(productCode, email);
		
		ProductDao dao = new ProductDao(this.con);
		Product product = dao.getSingleProduct(productCode);
		
		if(!isThere) {
			f = insertingInCart(email, productCode, quantity, product);
		}else {
			Cart cart = getCartProductUserDetail(email, productCode);
		 f=	updatingCart(cart, quantity);
		}
		
		return f;
	}


	// method to get a product with specified product code from the database.
	// Product code is passed in parameter.
	public Cart getCartProductUserDetail(String email, String productCode) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Cart cart = null;
		
		 ProductDao productDao = new ProductDao(this.con);
		 UserDao userDao = new UserDao(this.con);

		try {
			// Execute SQL query
			String sql = "SELECT * FROM cart WHERE pId=? AND uId=?";
//			String sql = "SELECT c.cId, c.quantity, p.productCode, p.productName, p.price, p.brand, p.category, p.type, p.productPic, p.rating, p.inStock, u.email, u.username, u.password,  u.phoneNumber, u.address, u.profilePic FROM cart c JOIN product p ON c.pId = p.productCode JOIN user u ON c.uId = u.email WHERE pId=? and uId=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, productCode);
			stmt.setString(2, email);

			rs = stmt.executeQuery();

			// get values from the database
			while (rs.next()) {

				Product product = productDao.getSingleProduct(rs.getString("pId"));
				User user = userDao.getSingleUser(rs.getString("uId"));
		

				cart = new Cart();
				cart.setCartId(Integer.parseInt(rs.getString("cId")));
				cart.setProduct(product);
				cart.setUser(user);
				cart.setNoOfOrder(Integer.parseInt(rs.getString("quantity")));

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}
		return cart;
	}
	
	
	public Cart getCartDetailUsingCid(int cid) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 Cart cart = null;
		 
		 ProductDao productDao = new ProductDao(this.con);
		 UserDao userDao = new UserDao(this.con);
		try {
			// Execute SQL query
			String sql = "SELECT * FROM cart WHERE cid=?";
//			String sql = "SELECT c.cId, c.quantity, p.productCode, p.productName, p.price, p.brand, p.category, p.type, p.productPic, p.rating, p.inStock, u.email, u.username, u.password,  u.phoneNumber, u.address, u.profilePic FROM cart c JOIN product p ON c.pId = p.productCode JOIN user u ON c.uId = u.email WHERE cId=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, cid);
			

			rs = stmt.executeQuery();

			// get values from the database
			while (rs.next()) {

				Product product = productDao.getSingleProduct(rs.getString("pId"));
				User user = userDao.getSingleUser(rs.getString("uId"));
		

				cart = new Cart();
				cart.setCartId(Integer.parseInt(rs.getString("cId")));
				cart.setProduct(product);
				cart.setUser(user);
				cart.setNoOfOrder(Integer.parseInt(rs.getString("quantity")));
				

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}
		return cart;
	}
	
	// method to get all cart items from the database and returns them by making list
		// of cart or List<Cart>
	public List<Cart> getCartDetailsByEmail(String email) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 List<Cart> carts = new ArrayList<>();

		 ProductDao productDao = new ProductDao(this.con);
		 UserDao userDao = new UserDao(this.con);
		try {
			// Execute SQL query
			String sql = "SELECT * from cart WHERE uId=?";
//			String sql = "SELECT c.cId, c.quantity, p.productCode, p.productName, p.price, p.brand, p.category, p.type, p.productPic, p.rating, p.inStock, u.email, u.username, u.password, u.phoneNumber, u.address, u.profilePic FROM cart c JOIN product p ON c.pId = p.productCode JOIN user u ON c.uId = u.email where uId=?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			int i =0;
			rs = stmt.executeQuery();
			
			// get values from the database
			while (rs.next()) {
				
				Product product = productDao.getSingleProduct(rs.getString("pId"));
				User user = userDao.getSingleUser(rs.getString("uId"));
		

				Cart cart = new Cart();
				cart.setCartId(Integer.parseInt(rs.getString("cId")));
				cart.setProduct(product);
				cart.setUser(user);
				cart.setNoOfOrder(Integer.parseInt(rs.getString("quantity")));
				
				carts.add(cart);
				

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}
		
		
		return carts;
	}
	
	public List<Cart> getCartDetails() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 List<Cart> carts = new ArrayList<>();

		 ProductDao productDao = new ProductDao(this.con);
		 UserDao userDao = new UserDao(this.con);
		try {
			// Execute SQL query
			String sql = "SELECT * from cart";
//			String sql = "SELECT c.cId, c.quantity, p.productCode, p.productName, p.price, p.brand, p.category, p.type, p.productPic, p.rating, p.inStock, u.email, u.username, u.password, u.phoneNumber, u.address, u.profilePic FROM cart c JOIN product p ON c.pId = p.productCode JOIN user u ON c.uId = u.email where uId=?";
			
			stmt = con.prepareStatement(sql);
			
			int i =0;
			rs = stmt.executeQuery();
			
			// get values from the database
			while (rs.next()) {
				
				Product product = productDao.getSingleProduct(rs.getString("pId"));
				User user = userDao.getSingleUser(rs.getString("uId"));
		

				Cart cart = new Cart();
				cart.setCartId(Integer.parseInt(rs.getString("cId")));
				cart.setProduct(product);
				cart.setUser(user);
				cart.setNoOfOrder(Integer.parseInt(rs.getString("quantity")));
				
				carts.add(cart);
				

			}
		} catch (SQLException e) {
			// Handle database errors
			e.printStackTrace();

		}
		
		
		return carts;
	}

	public boolean removeCartItemByUser(int cId) {
		boolean f = false;
		ProductDao dao = new ProductDao(this.con);
		Cart cart = getCartDetailUsingCid(cId);
		
		
		
		try {

			String query = "DELETE FROM cart WHERE cId=?";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, cId);
			pstmt.executeUpdate();

			(cart.getProduct()).increaseInStock(cart.getNoOfOrder());
		String productQuery = "Update product SET inStock='" + (cart.getProduct()).getInStock() +
				"' WHERE productCode='" + (cart.getProduct()).getProductCode() + "'";
		pstmt = this.con.prepareStatement(productQuery);

		pstmt.executeUpdate();
		
			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}
	
	public boolean removeCartItemByAdmin(int cId) {
		boolean f = false;
		TransactionDao transactionDao = new TransactionDao(this.con);
		Cart cart = getCartDetailUsingCid(cId);
		
		
		
		
		try {

			String query = "DELETE FROM cart WHERE cId=?";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, cId);
			pstmt.executeUpdate();

			transactionDao.insertingInTransaction(cart);
		
			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}
	
	
	// to check if user have alerady added item to cart on not
	public boolean checkIfInCart(String productCode, String email) {
		Cart cart = getCartProductUserDetail(email, productCode);

		if (cart == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public int noOfItemsInCart(String email) {
		int count = 0;
		try {

			String query = "SELECT COUNT(*) FROM cart WHERE uId=?";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, email);
			
		
		      
		      
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			    count = rs.getInt(1);
			}

			System.out.println("value of count is " + count);
			
			
		
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return count;
	}
}
