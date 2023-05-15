package model;

/**
 * Product model where productCode is for unique identification of products,
 * productName is for giving name of the product, productPrice is for setting
 * price of the product, brand is the brand name who made the product, category
 * is for which category the product belongs to, type is for whether the product
 * is menswear or womenswear, productPicutre is for giving the name of the
 * picture of the product and rating is for how much rating is given to the
 * product by the user.
 *
 */
public class Product {

	private String productCode;
	private String productName;
	private String productPrice;
	private String brand;
	private String category;
	private String type;
	private String productPicture;
	private float rating;
	private int inStock;

	public Product() {

	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	
	public void decreaseInStock(int number) {
		 if (number <= 0) {
		        this.inStock = this.inStock;
		    } else {
		        this.inStock = this.inStock - number;
		    }
	}
	
	public void increaseInStock(int number) {
		 if (number <= 0) {
		        this.inStock = this.inStock;
		    } else {
		        this.inStock = this.inStock + number;
		    }
	}

	@Override
	public String toString() {
		return "Product [productCode=" + productCode + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", brand=" + brand + ", category=" + category + ", type=" + type + ", productPicture="
				+ productPicture + "]";
	}

}
