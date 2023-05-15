package model;

public class Transaction {

	private int tId;
	private User user;
	private Product product;
	private int noOfOrder;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNoOfOrder() {
		return noOfOrder;
	}

	public void setNoOfOrder(int noOfOrder) {
		this.noOfOrder = noOfOrder;
	}

}
