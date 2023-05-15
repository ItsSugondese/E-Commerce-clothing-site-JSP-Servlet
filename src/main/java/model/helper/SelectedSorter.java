package model.helper;

import java.util.List;

import model.Product;

/**
 * This model class is for "public/homepage.jsp" and will be used in
 * "homepage.DisplayProductServlet.java". Here, selected variable is for getting
 * the data regarding how the user wants products to be sorted, subSelected is
 * which which genre that sorted varibale falls. 
 * 
 * eg. "Menswear" is a sort type falls under "Type" genre. Where "Meswear is selected" and "Type is subSelected".
 * 
 * Last is products which will store list of sorted product 
 *
 */
public class SelectedSorter {

	private String selected;
	private String subSelected;
	private List<Product> products;

	public SelectedSorter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SelectedSorter(String selected, String subSelected, List<Product> products) {
		super();
		this.selected = selected;
		this.subSelected = subSelected;
		this.products = products;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getSubSelected() {
		return subSelected;
	}

	public void setSubSelected(String subSelected) {
		this.subSelected = subSelected;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
