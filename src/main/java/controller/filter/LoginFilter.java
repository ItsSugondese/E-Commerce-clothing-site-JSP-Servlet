package controller.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.SendResult;

import model.User;
import model.helper.ErrorHandler;
import model.helper.LoginNotifier;

/**
 * This filter class is responsible for filtering non-logged user and
 * redirecting them to login page if they are not allowed to access that webpage
 */
public class LoginFilter implements Filter {
	private String adminEmail;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		adminEmail = filterConfig.getServletContext().getInitParameter("adminEmail");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String path = ((HttpServletRequest) request).getRequestURI();

		String queryString = ((HttpServletRequest) request).getQueryString();

		HttpSession session = ((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("loggedUser");

		if (user != null) {
			if (!user.getEmail().equals(adminEmail)) {
				chain.doFilter(request, response);
			} else {
				ErrorHandler handler = new ErrorHandler();
				handler.setMessage("Only Users can view this page");
				((HttpServletRequest) request).getSession().setAttribute("err", handler);
				((HttpServletResponse) response).sendRedirect("../error_page.jsp");
			}

		} else {
			LoginNotifier loginNotifier = new LoginNotifier("error", "Please log in first to access that url");

			if (queryString == null) {
				HttpServletRequest httpRequest = ((HttpServletRequest) request);
				httpRequest.getSession().setAttribute("loginMessage", loginNotifier);

				((HttpServletResponse) response).sendRedirect("../login.jsp");
			} else {
				((HttpServletResponse) response).sendRedirect("../login.jsp?" + queryString);
			}
		}

	}

}
