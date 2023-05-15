package controller.homepage;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import model.Dao.ProductDao;
import model.helper.ConnectionProvider;
import model.helper.SelectedSorter;

/**
 * This servlet is responsible for sorting product in "public/homepage.jsp"
 * where when user clicks on sorting options, they will be redirected to this
 * webpage and this page will return list of products.
 * 
 * The list of product will be send through SelectedSorter class using request.setAttribute("sort", sorter) where I
 * will also be sending the item selected when sorting in hompage.jsp file
 * 
 *
 */

@WebServlet(name = "sortProductServlet", urlPatterns = {"/public/sortProductServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class DisplayProductServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductDao dao = new ProductDao(ConnectionProvider.getConnection());
		List<Product> products = dao.getProducts();
		SelectedSorter sorter = new SelectedSorter();

		String sortBy = request.getParameter("sort-by");
		String subSort = request.getParameter("sub-sort");
		sorter.setSelected(sortBy);
		sorter.setSubSelected(subSort);

		if (subSort.equals("Price")) {
			if (sortBy.equals("Low to High")) {

				Collections.sort(products, new Comparator<Product>() {

					@Override
					public int compare(Product p1, Product p2) {
						// TODO Auto-generated method stub
						return Double.compare(Double.parseDouble((((p1.getProductPrice()).split(". "))[1]).trim()),
								Double.parseDouble((((p2.getProductPrice()).split(". "))[1]).trim()));
					}

				});

			} else {
				Collections.sort(products, new Comparator<Product>() {

					@Override
					public int compare(Product p1, Product p2) {
						// TODO Auto-generated method stub
						return Double.compare(Double.parseDouble((((p2.getProductPrice()).split(". "))[1]).trim()),
								Double.parseDouble((((p1.getProductPrice()).split(". "))[1]).trim()));
					}

				});
			}

		} else if (subSort.equals("Rating")) {
			if (sortBy.equals("Low to High")) {

				Collections.sort(products, new Comparator<Product>() {

					@Override
					public int compare(Product p1, Product p2) {
						// TODO Auto-generated method stub
						return Double.compare((double) (p1.getRating()), (double) (p2.getRating()));
					}

				});

			} else {
				Collections.sort(products, new Comparator<Product>() {

					@Override
					public int compare(Product p1, Product p2) {
						// TODO Auto-generated method stub
						return Double.compare((double) (p2.getRating()), (double) (p1.getRating()));
					}

				});
			}

		}

		else if (subSort.equals("Type")) {
			if (sortBy.equals("Menswear")) {
				products = products.stream().filter(prodct -> prodct.getType().equals("Menswear")).toList();
			} else {
				products = products.stream().filter(prodct -> prodct.getType().equals("Womenswear")).toList();
			}

		} else if ((request.getParameter("sub-sort")).equals("Category")) {
			if (sortBy.equals("Tops")) {
				products = products.stream().filter(prodct -> prodct.getCategory().equals("Tops")).toList();
			} else if (sortBy.equals("Bottoms")) {
				products = products.stream().filter(prodct -> prodct.getCategory().equals("Bottoms")).toList();
			} else if (sortBy.equals("Dresses")) {
				products = products.stream().filter(prodct -> prodct.getCategory().equals("Dresses")).toList();
			} else if (sortBy.equals("Underwear")) {
				products = products.stream().filter(prodct -> prodct.getCategory().equals("Underwear")).toList();
			} else if (sortBy.equals("Footwear")) {
				products = products.stream().filter(prodct -> prodct.getCategory().equals("Footwear")).toList();
			} else if (sortBy.equals("Accessories")) {
				products = products.stream().filter(prodct -> prodct.getCategory().equals("Accessories")).toList();
			}

		}

		sorter.setProducts(products);
		request.setAttribute("sort", sorter);
		request.getRequestDispatcher("homepage.jsp").forward(request, response);

	}
}
