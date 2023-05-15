package model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.Product;
import model.Transaction;
import model.User;

public class TransactionDao {
	private Connection con;
	
	

	public TransactionDao(Connection con) {
		this.con = con;
	}



		//this method is responsible for inserting value 
		public boolean insertingInTransaction(Cart cart) {
			boolean f = false;
			try {
				// user -->database
				String query = "insert into transaction(uId, pId, quantity) values (?,?,?)";
				PreparedStatement pstmt = this.con.prepareStatement(query);
				pstmt.setString(1, (cart.getUser()).getEmail());
				pstmt.setString(2, (cart.getProduct()).getProductCode());
				pstmt.setInt(3, cart.getNoOfOrder());
				
				pstmt.executeUpdate();
				

				f = true;
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return f;
		}
		
		public List<Transaction> getTransactionsByEmail(String email) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			 List<Transaction> transactions = new ArrayList<>();

			 ProductDao productDao = new ProductDao(this.con);
			 UserDao userDao = new UserDao(this.con);
			try {
				// Execute SQL query
				String sql = "SELECT * from transaction WHERE uId=?";
				
				stmt = con.prepareStatement(sql);
				stmt.setString(1, email);
				rs = stmt.executeQuery();
				
				// get values from the database
				while (rs.next()) {
					
					Product product = productDao.getSingleProduct(rs.getString("pId"));
					User user = userDao.getSingleUser(rs.getString("uId"));
			

					Transaction transaction = new Transaction();
					
					transaction.settId((Integer.parseInt(rs.getString("tId"))));
					transaction.setProduct(product);
					transaction.setUser(user);
					transaction.setNoOfOrder((Integer.parseInt(rs.getString("quantity"))));
					
					transactions.add(transaction);
					

				}
			} catch (SQLException e) {
				// Handle database errors
				e.printStackTrace();

			}
			
			
			return transactions;
		}
		
		public List<Transaction> getAllTransactions() {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			 List<Transaction> transactions = new ArrayList<>();

			 ProductDao productDao = new ProductDao(this.con);
			 UserDao userDao = new UserDao(this.con);
			try {
				// Execute SQL query
				String sql = "SELECT * from transaction";
				
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				// get values from the database
				while (rs.next()) {
					
					Product product = productDao.getSingleProduct(rs.getString("pId"));
					User user = userDao.getSingleUser(rs.getString("uId"));
			

					Transaction transaction = new Transaction();
					
					transaction.settId((Integer.parseInt(rs.getString("tId"))));
					transaction.setProduct(product);
					transaction.setUser(user);
					transaction.setNoOfOrder((Integer.parseInt(rs.getString("quantity"))));
					
					transactions.add(transaction);
					

				}
			} catch (SQLException e) {
				// Handle database errors
				e.printStackTrace();

			}
			
			
			return transactions;
		}
}
