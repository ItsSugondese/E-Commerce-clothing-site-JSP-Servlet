package model.helper;

import model.Product;


/**
 * This class is for helping with which product rating picture to show based off the rating on the product.
 *
 */
public class ProductRatingHelper {

	public static String getRatingImage(Product product) {
		float rating = product.getRating();
		if(rating >= 0 && rating< 0.5 ) {
			return "0.png";
		}else if(rating >= 0.5 && rating< 1 ) {
			return "0.5.png";
		}else if(rating >= 1  && rating< 1.5 ) {
			return "1.png";
		}else if(rating >= 1.5  && rating< 2 ) {
			return "1.5.png";
		}else if(rating >= 2  && rating< 2.5 ) {
			return "2.png";
		}else if(rating >= 2.5  && rating< 3 ) {
			return "2.5.png";
		}else if(rating >= 3  && rating< 3.5 ) {
			return "3.png";
		}else if(rating >= 3.5  && rating< 4 ) {
			return "3.5.png";
		}else if(rating >= 4  && rating< 4.5 ) {
			return "4.png";
		}else if(rating >= 4.5  && rating< 5 ) {
			return "4.5.png";
		}else if(rating == 5) {
			return "5.png";
		}else {
			return "Servler error";
		}
	
	}
}
