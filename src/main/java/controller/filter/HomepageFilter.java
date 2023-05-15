package controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This filter class is responsilbe for filtering certain url and redirecting
 * them to "public/homepage.jsp" webpage
 */
public class HomepageFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String path = ((HttpServletRequest) request).getRequestURI();
		System.out.println(path);
		if (path.equals("/E-Commerce_coursework/"))
			((HttpServletResponse) response).sendRedirect("public/homepage.jsp");
		else if (path.equals("/E-Commerce_coursework/public/product.jsp"))
			((HttpServletResponse) response).sendRedirect("homepage.jsp");
	}

}
