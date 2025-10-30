package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;

@WebFilter(urlPatterns = {"/edit", "/setting"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request; //型変換
		HttpServletResponse httpResponse = (HttpServletResponse) response; //型変換
		List<String> errorMessages = new ArrayList<String>();

		User user = (User) httpRequest.getSession().getAttribute("loginUser");
		if(user != null) {
			chain.doFilter(httpRequest, httpResponse); // サーブレットを実行
		}else {
			errorMessages.add("ログインをしてください");
			httpRequest.getSession().setAttribute("errorMessages", errorMessages);
			httpResponse.sendRedirect("./login");
		}

	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}

}

