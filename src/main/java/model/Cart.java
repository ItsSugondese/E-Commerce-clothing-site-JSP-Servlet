package model;

public class Cart {

	private int cartId;
	private User user;
	private Product product;
	private int noOfOrder;
	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
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
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getNoOfOrder() {
		return noOfOrder;
	}

	public void setNoOfOrder(int noOfOrder) {
		this.noOfOrder = noOfOrder;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user=" + user + ", product=" + product + ", noOfOrder=" + noOfOrder + "]";
	}

	
	
	
	
	
	
	
	
}
