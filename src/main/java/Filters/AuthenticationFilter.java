package Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        String[] protectedPages = {"/product-list.jsp", "/create-product.jsp",
        "/create-user.jsp","/update-user.jsp","/users.jsp","/update-product.jsp"};
        String requestURI = httpRequest.getRequestURI();

        User auth = (User) httpRequest.getSession().getAttribute("auth");
        for (String page : protectedPages) {
            if (requestURI.contains(page)) {
                if (httpRequest.getSession().getAttribute("auth") == null || !auth.getRole().equals("Admin")) {
                    httpResponse.sendRedirect("login.jsp");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
